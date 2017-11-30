package com.parkar.caseusage.tracker;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.client.MongoDatabase;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class CaseUsageTracker {
	private static final String collectionNameSessions = "falcon_selenium_usage_session";
	private static final String collectionNameCases = "falcon_selenium_usage_cases";
	final static Logger log = Logger.getLogger(CaseUsageTracker.class);
	
	private MongoDatabase trackerDB;
	private Document trackerSession;
	private CaseUsageTrackerCollection trackerCollection;
	
	/**
	 * Constructor with two parameters
	 * 
	 * @param trackerDB: MongoDatabase
	 * @param trackerSession: Document
	 */
	public CaseUsageTracker(MongoDatabase trackerDB, Document trackerSession) {
		this.trackerDB = trackerDB;
		this.trackerSession = trackerSession;
		this.trackerDB.getCollection(collectionNameSessions).insertOne(this.trackerSession);

	}
	
	/**
	 * Add the usage tracker using MongoDB
	 * 
	 * @param tracker: UsageTracker
	 */
	public void addUsageTracker(UsageTracker tracker){
		if(trackerDB!=null){
			trackerCollection.addTracker(tracker);
		}
	}
	
	/**
	 * Save the usage 
	 * 
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public void saveUsage() throws ParkarCoreCommonException{
		ParkarLogger.traceEnter();
		try
		{
			if(trackerDB!=null){
				trackerDB.getCollection(collectionNameCases).insertOne(
						new Document()
						.append("session", trackerSession.get("_id"))
						.append("case_name", trackerCollection.getCaseName())
						.append("trackers", trackerCollection.getTrackersAsDBObject()));
			}
		}catch (Exception e)
		{
			String message = "Save usage failed.";
			log.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}
		log.info("Save usage successful");
		ParkarLogger.traceLeave();
	}
	
	/**
	 * Reset the Tracker
	 * 
	 * @param caseName: String
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public void resetTrackerCollection(String caseName) throws ParkarCoreCommonException{
		ParkarLogger.traceEnter();
		try
		{
			if(trackerDB!=null){
				trackerCollection = new CaseUsageTrackerCollection(caseName);
			}
		}catch (Exception e)
		{
			String message = "Reset usage failed.";
			log.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}
		log.info("Reset usage successful");
		ParkarLogger.traceLeave();
	} 
	
	

}
