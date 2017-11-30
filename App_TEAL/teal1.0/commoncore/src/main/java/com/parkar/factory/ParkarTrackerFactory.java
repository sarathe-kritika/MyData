package com.parkar.factory;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.parkar.caseusage.tracker.CaseUsageTracker;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class ParkarTrackerFactory {
	private static final String trackerDBName = "Parkar_action_usage_tracker";
	private final static Logger logger = Logger.getLogger(ParkarTrackerFactory.class);
	
	/**
	 * Default constructor
	 */
	public ParkarTrackerFactory() {
	}

	/**
	 * Create an element usage tracker
	 * 
	 * @param name: String
	 * @param  port: String
	 * @return CaseUsageTracker
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	@SuppressWarnings("resource")
	public CaseUsageTracker createReportTracker(String name, String port) throws ParkarCoreCommonException{
		ParkarLogger.traceEnter();// enter ---
		try{
			MongoDatabase dataBase = new MongoClient(name , Integer.valueOf(port)).getDatabase(trackerDBName);
			logger.info("DataBase Connection is OK. ");
			Document document = new Document()
	        .append("user", System.getProperty("user.name"))
	        .append("os", System.getProperty("os.name"))
	        .append("java", System.getProperty("java.version"));
			logger.info("Document Created. ");
			ParkarLogger.traceLeave(); // leave ---
			return new CaseUsageTracker(dataBase,document);
		}catch(Exception e){
			//System.out.println("No DataBase connection. No tracking information will be stored");
			
			String message = "No DataBase connection. No tracking information will be stored";
			logger.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}
	}
}
