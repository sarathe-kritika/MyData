package com.parkar.factory;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.mongodb.diagnostics.logging.Logger;
import org.apache.log4j.Logger;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;
import com.parkar.testng.Configurator;
import com.relevantcodes.extentreports.ExtentReports;

public class ParkarReportFactory {
	
	private final static Logger logger = Logger.getLogger(ParkarReportFactory.class);
	
	private static Configurator configurator =Configurator.getInstance();
	private static final String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
	/**
	 * Constructor of ParkarReportFactory
	 */
	public ParkarReportFactory() {
	}

	/**
	 * Create extent report
	 * 
	 * @return ExtentReports
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public static ExtentReports createExtentReport() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter(); // enter ---
		String file= configurator.getDirectoryParameter("current_report") + File.separator+ "extent_report_" + timeStamp + ".html";
		ExtentReports report = null;
		//setup environment variables
		InetAddress iAddress = null;
		try {
			report = new ExtentReports(file, true);
			iAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			String message = "Failed to get local host " ;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}catch (Exception e) {
			String message = "Failed to initilaize extent reports" ;
			logger.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}
		logger.info("Host Name: " + iAddress.getHostName()+ " Address: " + iAddress.getHostAddress());
		String hostName="NA";
		String canonicalHostName="NA";
		if(iAddress != null) {
			hostName = iAddress.getHostName();
	        canonicalHostName = iAddress.getCanonicalHostName();
	        report.addSystemInfo("Host Name", hostName);
	        report.addSystemInfo("Host Name (Canonical)", canonicalHostName);
		}
		ParkarLogger.traceLeave(); // leave ---
		return report;
	}
}
