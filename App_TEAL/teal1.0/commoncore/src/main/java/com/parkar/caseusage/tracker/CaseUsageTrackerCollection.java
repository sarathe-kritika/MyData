package com.parkar.caseusage.tracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class CaseUsageTrackerCollection {
	final static Logger log = Logger.getLogger(CaseUsageTrackerCollection.class);
	private List<UsageTracker> trackers;
	private String caseName;
	
	/**
	 * Constructor with one parameter
	 * 
	 * @param caseName: String
	 */
	public CaseUsageTrackerCollection(String caseName) {
		this.caseName = caseName;
		this.trackers = new ArrayList<UsageTracker>();
	}

	/**
	 * Add tracker
	 * @param tracker: UsageTracker
	 */
	public void addTracker(UsageTracker tracker){
		this.trackers.add(tracker);
	}

	/**
	 * Get case name
	 * 
	 * @return case name
	 */
	public String getCaseName() {
		return caseName;
	}

	

	/**
	 * Get Tracker database object.
	 * 
	 * @return case name: List
	 * @throws ParkarCoreCommonException
	 * 			:  customized Parkar core common exception
	 */
	public List<DBObject> getTrackersAsDBObject() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try
		{
			List<DBObject> tempList = new ArrayList<DBObject>();
			int order = 1;
			for(UsageTracker ut: this.trackers){
				DBObject t = new BasicDBObject();
				t.put("access_order", order++);
				t.put("name", ut.getLocatorName());
				t.put("value", ut.getLocatorValue());
				t.put("navigation", ut.getNavigation());
				t.put("action", ut.getAction());
				tempList.add(t);
			}
			log.info("Successfully Get Trackers As DBObjec.");
			return tempList;
		}catch (Exception e)
		{
			String message = "Get Trackers As DBObject failed.";
			log.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}
	}

}
