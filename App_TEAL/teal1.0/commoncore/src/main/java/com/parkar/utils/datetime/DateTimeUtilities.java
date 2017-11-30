package com.parkar.utils.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

import java.net.InetAddress;

public class DateTimeUtilities {
	
	final static Logger logger = Logger.getLogger(DateTimeUtilities.class);
	/**
	 * Customize DateFormat in (HH-mm-ss_dd-MM-yy)
	 * 
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 * @return Time-date in the format of String
	 */
	public static String ymdhmsTime() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			String ymdhmsTime = null;
			DateFormat ymdhms = new SimpleDateFormat("HH-mm-ss_dd-MM-yy");
			Date date = new Date();
			ymdhmsTime = ymdhms.format(date);
			logger.info("Operation output is: " + ymdhmsTime);
			ParkarLogger.traceLeave();
			return (ymdhmsTime);
		} catch (Exception e) {
			String message = "Get current date failed: ";
			logger.error(message, e);
			throw new ParkarCoreCommonException(message,e);
		}
	}

	
	/**
	 * Customize Time in Format(HH:mm:ss)
	 * 
	 * @throws ParkarCoreCommonException
	 * 			: customized krons core common exception
	 * @return time in the format of String
	 */
	public static String hmsTime() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			String hmsTime = null;
			DateFormat hms = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			hmsTime = hms.format(date);
			logger.info("Operation output is: " + hmsTime);
			ParkarLogger.traceLeave();
			return (hmsTime);
		} catch (Exception e) {
			String message = "Get current time failed : ";
			logger.error(message ,e);
			throw new ParkarCoreCommonException(message,e);
		}
	}

	/**
	 * Format a date time
	 * 
	 * @return time in the format of String
	 */
	public static String dateTime() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	/**
	public static String Computername() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName().replace("-", "")
				.substring(0, 6);
	}
	*/
	
	/**
	 * Get Computer name
	 * 
	 * @throws ParkarCoreCommonException
	 * : customized krons core common exception
	 * @return computer name in String
	 */
	public static String Computername() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try{
			String str = InetAddress.getLocalHost().getHostName().replace("-", "")
					.substring(0, 6);
			logger.info("Operation output is: " + str);
			ParkarLogger.traceLeave();
			return str;
		}catch(Exception e){
			String message = "Get hostName failed :  ";
			logger.error(message ,e);
			throw new ParkarCoreCommonException(message,e);
		}
	}
	
	/**
	 * Calculating difference between the startTime and endTime
	 * 
	 * @param startTime: String
	 * @param endTime: String
	 * @throws ParkarCoreCommonException
	 * 			: customized Parkar core common exception
	 * @return time-difference in the format of double
	 */
	public static double timeDifference(String startTime, String endTime) throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date time1 = dateFormat.parse(startTime);
			Date time2 = dateFormat.parse(endTime);
			long t1 = time1.getTime();
			long t2 = time2.getTime();
			double diffTime = (Math.abs(t2 - t1) / 1000);
			logger.info("Operation input String is: " + startTime+"Operation input String is: "+endTime);
			ParkarLogger.traceLeave();
			return ((double) diffTime);
		} catch (Exception e) {
			String message = "Failed to process data: ";
			logger.error(message ,e);
			throw new ParkarCoreCommonException(message,e);
		}

	}
	/**
	 * Customize DateFormat in (yyyyMMDDHHmmss)
	 * 
	 * @throws ParkarCoreCommonException
	 * 			: customized krons core common exception
	 * @return date in the format of String yyyyMMDDHHmmss
	 */
	public static String yyyyMMDDHHmmssTime() throws ParkarCoreCommonException   {
		ParkarLogger.traceEnter();
		try {
			String ymdhmsTime = null;
			DateFormat ymdhms = new SimpleDateFormat("yyyyMMDDHHmmss");
			Date date = new Date();
			ymdhmsTime = ymdhms.format(date);
			logger.info("Operation output is: " + ymdhmsTime);
			ParkarLogger.traceLeave();
			return (ymdhmsTime);
		} catch (Exception e) {
			String message = "Get current dateTime failed : ";
			logger.error(message ,e);
			throw new ParkarCoreCommonException(message,e);
		}
	}

	/**
	 * Get day of the date
	 * 
	 * @throws ParkarCoreCommonException
	 * 			: customized krons core common exception
	 * @return day in String
	 */
	public static String getDayofTheDate() throws ParkarCoreCommonException {
		ParkarLogger.traceEnter();
		try {
			Date d1 = new Date();
			String day = null;
			DateFormat f = new SimpleDateFormat("EEEE");
			day = f.format(d1);
			logger.info("Operation output is: " + day);
			ParkarLogger.traceLeave();
			return day;
		} catch (Exception e) {
			String message = "Get date failed:";
			logger.error(message ,e);
			throw new ParkarCoreCommonException(message,e);
		}
	}
	
	/**
	 * Find the leap year
	 * 
	 * @param year: int
	 * @return boolean value
	 */	
	public static boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			return true;
		}
		return false;
	}
	/**
	 * Get number of days in the month
	 * 
	 * @return no of days
	 */	
	public static int daysInMonth() {
		Calendar c1 = Calendar.getInstance();
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31 };
		daysInMonths[1] += isLeapYear(year) ? 1 : 0;
		return daysInMonths[month];
	}
	

	/**
	 * Calculate the minutes
	 * 
	 * @param startTime: long
	 * @return minutes in the form of String
	 */
	public static String getMinutes(long startTime) {
		return (new Date().getTime() - startTime) / 1000 / 60 + " minutes";
	}
}
