package com.parkar.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

public class FileUtils {
	
	final static Logger logger = Logger.getLogger(FileUtils.class);

	/**
	 * Get the absolute path from relative path
	 * 
	 * @param relativePath:
	 *            String
	 * @return absolute path in String
	 */
	public static String getAbsolutePath(String relativePath) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		File file = new File(FileUtils.class.getResource("/").getPath(), "../resources/" + relativePath);
		if (!file.exists()) {
			file = new File("resources/" + relativePath);
		}
		String absolutePath = null;
		try {
			absolutePath = file.getCanonicalPath();
		} catch (IOException e) {
			logger.error("IOException occured when get absolute path of " + relativePath, e);
			throw new ParkarCoreCommonException("IOException occured when get absolute path of " + relativePath, e);
		}
		ParkarLogger.traceLeave();
		return absolutePath;
	}
	
	/**
	 * Reads properties file.
	 * 
	 * @param propertyFile
	 * @return the properties object loaded with the content
	 */
	public static Properties readProperties(String propertyFile) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(propertyFile)) {
			prop.load(input);
		} catch (IOException e) {
			logger.error("IOException occured while reading properties file: " + propertyFile, e);
			throw new ParkarCoreCommonException("IOException occured while reading properties file: " + propertyFile, e);
		}
		ParkarLogger.traceLeave();
		return prop;
	}
	
}
