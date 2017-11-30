package com.parkar.testng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class Configurator {

	private static Configurator instance;
	private static final String TIME_STAMP = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
	private static HashMap<String, String> directoryMap;
	private static HashMap<String, String> parameterMap;
	// private CaseUsageTracker tracker;
	private final static Logger logger = Logger.getLogger(Configurator.class);

	/**
	 * Constructor for Configurator
	 * 
	 * @throws ParkarCoreCommonException
	 *             : ParkarCoreCommonException
	 */
	private Configurator() {
		ParkarLogger.traceEnter();
		try {
			directoryMap = new HashMap<String, String>();
			parameterMap = new HashMap<String, String>();
		} catch (Exception e) {
			String message = "Configurator create Failed";
			logger.error(message, e);
			// throw new ParkarCoreCommonException(message, e);
		}
		logger.info("Configurator Created. ");
		ParkarLogger.traceLeave();
	}


	/**
	 * Creates an instance of Configurator
	 * 
	 * @return instance: Configurator
	 */
	public static Configurator getInstance() {
		if (instance == null) {
			ParkarLogger.traceEnter();
			instance = new Configurator();
			ParkarLogger.traceLeave();
			logger.info("Configurator Created. ");

		}
		return instance;
	}

	/**
	 * Initializes global parameterMap using testng context.
	 * 
	 * @param params:Map
	 * @throws ParkarCoreCommonException
	 * 			: customized Parkar core common exception
	 */
	public void initializeParameters(Map<String, String> params) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		if (params.containsKey("Frontend_Server")) {
			parameterMap.put("Frontend_Server", params.get("Frontend_Server"));
		} else if (params.containsKey("Backend_Server") ) {
			parameterMap.put("Backend_Server", params.get("Backend_Server"));
		} else {
			String message = "Server detail is mandatory. Please set this value and run again";
			logger.error(message);
			throw new ParkarCoreCommonException(message);
		}
		parameterMap.put("dateFormat", params.containsKey("dateFormat") ? params.get("dateFormat") : "MM/dd/yyyy");
		parameterMap.put("timeout", params.containsKey("timeout") ? params.get("timeout") : "30");
		parameterMap.put("deepReporting", params.containsKey("deepReporting") ? params.get("deepReporting") : "True");
		setDirectoryInformation(params);
		ParkarLogger.traceLeave();
	}

	/**
	 * Get a parameter value based on key
	 * 
	 * @param key:String
	 * @return value of key: String
	 */
	public String getParameter(String key) {
		if (parameterMap.containsKey(key))
			return parameterMap.get(key);
		else
			return "";
	}

	/**
	 * 
	 */

	/**
	 * usage tracker functions
	 * 
	 * @param caseName
	 * @throws ParkarCoreCommonException
	 */
	/*
	 * public void resetTrackerCollection(String caseName) throws
	 * ParkarCoreCommonException{ if(tracker!=null)
	 * tracker.resetTrackerCollection(caseName); }
	 * 
	 *//**
		 * usage tracker functions
		 * 
		 * @throws ParkarCoreCommonException
		 */

	/*
	 * public void saveUsage() throws ParkarCoreCommonException{
	 * if(tracker!=null) tracker.saveUsage(); }
	 * 
	 *//**
	 * Get directory value based on the key.
	 * 
	 * @param key
	 *            : key in map
	 * @return String : value of key
	 */
	public String getDirectoryParameter(String key) {
		return directoryMap.containsKey(key) ? directoryMap.get(key) : null;
	}

	/**
	 * This function should be called at the beginning of the session execution
	 * to set up the folder structure
	 * 
	 * @param params : Map
	 * @throws IOException
	 *             : IOException
	 * @throws ParkarSeleniumException
	 *             : ParkarSeleniumException
	 */
	private static void setDirectoryInformation(Map<String, String> params) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		String root = "";
		String seperator=File.separator;
		try {
			root = new java.io.File(".").getCanonicalPath();
			directoryMap.put("Report_Loc", params.containsKey("Report_Loc") ? params.get("Report_Loc") : root);
			directoryMap.put("root", root + seperator);
			directoryMap.put("resources", root + seperator+"resources"+seperator);
			directoryMap.put("chrome_driver", root + seperator+"resources"+seperator+"drivers"+seperator+"chromedriver.exe");
			directoryMap.put("ie_driver", root + seperator+"resources"+seperator+"drivers"+seperator+"IEDriverServer.exe");
			directoryMap.put("report", directoryMap.get("Report_Loc") + seperator+"test-output"+seperator);
			directoryMap.put("current_report", directoryMap.get("Report_Loc") + seperator+"test-output"+seperator+"extent"+seperator + TIME_STAMP);
			directoryMap.put("current_sc_report",
			directoryMap.get("Report_Loc") + seperator+"test-output"+seperator+"extent"+seperator + TIME_STAMP + seperator+"sc");
			directoryMap.values().stream().forEach(f -> new File(f).mkdirs());
			logger.info("Direcotry is : " + root);
		} catch (IOException e) {
			String message = "Failed to set direcotry ";
			logger.error(message, e);
		}
		ParkarLogger.traceLeave();
	}
}
