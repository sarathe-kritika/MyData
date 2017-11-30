package com.parkar.api.rest.json;

import java.io.File;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.parkar.api.rest.exception.ParkarCoreAPIException;
import com.parkar.logging.ParkarLogger;

public class RequestBuilder {
	protected final static Logger logger = Logger.getLogger(RequestBuilder.class);
	
	/**
	 * This method loads the schema file from the projects resource directory present at the root level and 
		generates the json with the data from the Hierarchical CSV files.
     *   
	 * @param schemaFile: Json schema
	 * 
	 * @param parentCSVFile: Parent CSV file
	 * 
	 * @param keyRow: csv row
	 * 
	 * @return Request
	 * @throws ParkarCoreAPIException : ParkarCoreAPIException
	 */
	
	public Request build(String schemaFile, String parentCSVFile, String keyRow) throws ParkarCoreAPIException
	{
		ParkarLogger.traceEnter();
		//RequestGenerator ds = new RequestGenerator();
		JsonNode rootNode = null;
	/*	try {
			if (checkFileExistence(parentCSVFile) && checkFileExistence(schemaFile) && StringUtils.isNotBlank(keyRow))
					rootNode = ds.transformCSVData(schemaFile, parentCSVFile, keyRow);
		}
		catch (RequestGeneratorException e){	
			String errMsg = "Error while transforming CSV to JSON";
			logger.error(errMsg, e);
			throw new ParkarCoreAPIException(errMsg , e); 
		}*/
		ParkarLogger.traceLeave();
		return new Request(rootNode);
	}
	
	/**
	 * This method checks if file exists or not.

	 * @param file: File path
	 * 
	 * @return true or false based on existence status
	 */
	private Boolean checkFileExistence(String file)
	{
		File f = new File(file);
		return (f.exists());
	}
}


