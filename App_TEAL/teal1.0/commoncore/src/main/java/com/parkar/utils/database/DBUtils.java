package com.parkar.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;
import com.parkar.utils.common.FileUtils;

public class DBUtils {
	
	final static Logger logger = Logger.getLogger(DBUtils.class);
	
	private static final String PROPERTIES_FILE = "dbCredentials.properties";
	
	public static Connection oracleConnection = null;
	public static Connection mySQLConnection = null;
	public static Connection postgreSQLConnection=null;

	/**
	 * Decides which connection has to be established.
	 * 
	 * @param propertyFile: String
	 * @return connection
	 * @throws ParkarCoreCommonException 
	 */
	private static Connection getConnection(String propertyFile, String dbType) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		Properties prop = FileUtils.readProperties(propertyFile);
		
		dbType = dbType.toLowerCase().trim();

		String driver = prop.getProperty(dbType + "Driver");
		String url = prop.getProperty(dbType + "Url");
		String user = prop.getProperty(dbType + "User");
		String password = prop.getProperty(dbType + "Password");
		
		try {
			Class.forName(driver);
			if (dbType.equalsIgnoreCase("Oracle")) {
				if (oracleConnection == null) {
					synchronized (DBUtils.class) {
						if (oracleConnection == null) {
							oracleConnection = DriverManager.getConnection(url, user, password);
							logger.info("oracleConnection created.");
						}
					}
				}
				return oracleConnection;
			} else if (dbType.equalsIgnoreCase("MySQL")) {
				if (mySQLConnection == null) {
					synchronized (DBUtils.class) {
						if (mySQLConnection == null) {
							mySQLConnection = DriverManager.getConnection(url, user, password);
							logger.info("mySQLConnection created.");
						}
					}
				}
				return mySQLConnection;
			} else if (dbType.equalsIgnoreCase("PostgreSQL")) {
				if (postgreSQLConnection == null) {
					synchronized (DBUtils.class) {
						if (postgreSQLConnection == null) {
							postgreSQLConnection = DriverManager.getConnection(url, user, password);
							logger.info("postgreSQLConnection created.");
						}
					}
				}
				return postgreSQLConnection;
			}
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException occured while reading class name " + e);
			throw new ParkarCoreCommonException("ClassNotFoundException occured while reading class name " + e);
		} catch (SQLException e) {
			logger.error("SQLException occured while getting database connection: "	+ e);
			throw new ParkarCoreCommonException("SQLException occured while getting database connection: " + e);
		}
		logger.info("Invalid DB name, connection not created.");
		ParkarLogger.traceLeave();
		return null;
	}
	
	/**
	 * Gets the list of column names in a table.
	 * 
	 * @param resultSet
	 * @return list
	 */
	private static List<String> getColumnNames(ResultSet resultSet) throws ParkarCoreCommonException{
		ParkarLogger.traceEnter();
		List<String> listOfColumnName = new ArrayList<String>();
		try {
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				listOfColumnName.add(resultSetMetaData.getColumnName(i));
			}
		} catch (SQLException e) {
			logger.error("SQLException occured while reading resultSet " + e);
			throw new ParkarCoreCommonException("SQLException occured while reading resultSet " + e);
		}
		ParkarLogger.traceLeave();
		return listOfColumnName;
	}
	
	/**
	 * Gets database connection using property file
	 * Executes the database query provided by user
	 * Iterates through resultSet and puts each row into a map.
	 * 
	 * @param dbType : mySQL/Oracle
	 * @param query : database query
	 * @return list of map containing the rows
	 * @throws ParkarCoreCommonException
	 */
	public static List<Map<String, String>> readDB(String dbType, String query) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		List<Map<String, String>> content = new ArrayList<Map<String, String>>();
		PreparedStatement preState = null;
		ResultSet resultSet = null;
		Connection connection = getConnection(FileUtils.getAbsolutePath(PROPERTIES_FILE), dbType);
		
		try {
			preState = connection.prepareStatement(query);
			resultSet = preState.executeQuery();
			List<String> columnsList = getColumnNames(resultSet);
			while (resultSet.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < columnsList.size(); j++) {
					map.put(columnsList.get(j), resultSet.getString(columnsList.get(j)));
				}
				content.add(map);
			}
		} catch (SQLException e) {
			logger.error("SQLException occured while executing query or iterating resultSet " + e);
			throw new ParkarCoreCommonException("SQLException occured while executing query or iterating resultSet " + e);	
		} finally {
			try {
				resultSet.close();
				preState.close();
			} catch (SQLException e) {
				logger.error("SQLException occured while closing resultSet or preparedStatement " + e);
				throw new ParkarCoreCommonException("SQLException occured while closing resultSet or preparedStatement " + e);	
			}
		}
		ParkarLogger.traceLeave();
		return content;
	}
	
}
