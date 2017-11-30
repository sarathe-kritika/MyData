package com.parkar.utils.datetime;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.parkar.exception.ParkarCoreCommonException;

public class DateParserUtilities {
	
	private Date currentDate;
	private static final Pattern symbolicDayPattern = Pattern.compile("(today|yesterday|tomorrow|dayaftertomorrow|daybeforeyesterday)",Pattern.CASE_INSENSITIVE);
	private static final Pattern dayPattern = Pattern.compile("(-?|\\+?)(\\d+)d");
	private static final Pattern monthPattern = Pattern.compile("(-?|\\+?)(\\d+)M");
	private static final Pattern yearPattern = Pattern.compile("(-?|\\+?)(\\d+)y");
	private static final Pattern weekPattern = Pattern.compile("(-?|\\+?)(\\d+)w");
	private static final Pattern weekDayPattern = Pattern.compile("(-?|\\+?)(\\d+)?(monday|tuesday|wednesday|thursday|friday|saturday|sunday)",Pattern.CASE_INSENSITIVE);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final SimpleEntry[] WEEKDAYS = new SimpleEntry[] {new SimpleEntry("monday",DateTimeConstants.MONDAY) ,
			new SimpleEntry("tuesday",DateTimeConstants.TUESDAY) ,
			new SimpleEntry("wednesday",DateTimeConstants.WEDNESDAY) ,
			new SimpleEntry("thursday",DateTimeConstants.THURSDAY) ,
			new SimpleEntry("friday",DateTimeConstants.FRIDAY) ,
			new SimpleEntry("saturday",DateTimeConstants.SATURDAY) ,
			new SimpleEntry("sunday",DateTimeConstants.SUNDAY) };

	/**
	 * Constructor of DateParserUtilities
	 */
	public DateParserUtilities(){
		this.currentDate = new Date();
	}
	
	/**
	 * Constructor of DateParserUtilities with one parameter
	 * @param date: Date
	 */
	public DateParserUtilities(Date date) {
		this.currentDate = date;
	}

	/**
	 * Get current date
	 * 
	 * @return current date time
	 */
	public DateTime getCurrentDate() {
		return new DateTime(currentDate);
	}
	
	/**
	 * Get current date
	 * 
	 * @param date: Date
	 * @return current date time 
	 */
	public DateTime getDate(Date date) {
		return new DateTime(date);
	}
	

	/**
	 * Get day of the week
	 * 
	 * @return day of the week
	 */
	public int getDayOfWeek(){
		return new DateTime(currentDate).getDayOfWeek();
	}
	
	/**
	 * Get day of the week
	 * 
	 * @param date: DATE
	 * @return day of the week
	 */
	public int getDayOfWeek(Date date){
		return new DateTime(date).getDayOfWeek();
	}
	
	/**
	 * Check a date expression is valid or not
	 * 
	 * @param dateExpression: String
	 * @return true or false
	 */
	public boolean isValidPattern(String dateExpression){
		return (weekDayPattern.matcher(dateExpression).find() ||
				dayPattern.matcher(dateExpression).find() ||
				monthPattern.matcher(dateExpression).find() ||
				weekPattern.matcher(dateExpression).find() ||
				yearPattern.matcher(dateExpression).find() ||
				symbolicDayPattern.matcher(dateExpression).find());
	}
	
	/**
	 * Check if a week day pattern is present or not
	 * 
	 * @param dateExpression: String
	 * @return true or false
	 */
	public boolean isWeekDayPatternPresents(String dateExpression){
		return weekDayPattern.matcher(dateExpression).find();
	}
	
	/**
	 * Calculate the date pattern
	 * 
	 * @param dt: DateTime
	 * @param dateExpression: String
	 * @return a date time
	 */
	public DateTime calculateDatePattern(DateTime dt, String dateExpression){
		Matcher symDayMatcher = symbolicDayPattern.matcher(dateExpression);
		Matcher dayMatcher = dayPattern.matcher(dateExpression);
		Matcher weekMatcher = weekPattern.matcher(dateExpression);
		Matcher monthMatcher = monthPattern.matcher(dateExpression);
		Matcher yearMatcher = yearPattern.matcher(dateExpression);
		
		if (symDayMatcher.find()) {
			String symDay = symDayMatcher.group(1);
			if (symDay.equalsIgnoreCase("today")){
				dt = dt.plusDays(0);
			}else if(symDay.equalsIgnoreCase("yesterday")){
				dt = dt.plusDays(-1);
			}else if(symDay.equalsIgnoreCase("tomorrow")){
				dt = dt.plusDays(1);
			}else if(symDay.equalsIgnoreCase("dayaftertomorrow")){
				dt = dt.plusDays(2);
			}else if(symDay.equalsIgnoreCase("daybeforeyesterday")){
				dt = dt.plusDays(-2);
			}
		}
		
		if (dayMatcher.find()) {
			dt = (dayMatcher.group(1).isEmpty() || dayMatcher.group(1).equals("+"))
					? dt.plusDays(Integer.parseInt(dayMatcher.group(2)))
					: dt.minusDays(Integer.parseInt(dayMatcher.group(2)));
		}
		
		if (weekMatcher.find()) {
			dt = (weekMatcher.group(1).isEmpty() || weekMatcher.group(1).equals("+"))
					? dt.plusWeeks(Integer.parseInt(weekMatcher.group(2)))
					: dt.minusWeeks(Integer.parseInt(weekMatcher.group(2)));
		}
		
		if (monthMatcher.find()) {
			dt = (monthMatcher.group(1).isEmpty() || monthMatcher.group(1).equals("+"))
					? dt.plusMonths(Integer.parseInt(monthMatcher.group(2)))
					: dt.minusMonths(Integer.parseInt(monthMatcher.group(2)));
		}
		if (yearMatcher.find()) {
			dt = (yearMatcher.group(1).isEmpty() || yearMatcher.group(1).equals("+"))
					? dt.plusYears(Integer.parseInt(yearMatcher.group(2)))
					: dt.minusYears(Integer.parseInt(yearMatcher.group(2)));
		}
		return dt;
	}
	
	/**
	 * Calculate the arbitrary text
	 * 
	 * @param dateExpression: String
	 * @return the calculated text
	 */
	
	public String calculateArbitraryText(String dateExpression){
		Matcher symDayMatcher = symbolicDayPattern.matcher(dateExpression);
		Matcher dayMatcher = dayPattern.matcher(dateExpression);
		Matcher weekMatcher = weekPattern.matcher(dateExpression);
		Matcher monthMatcher = monthPattern.matcher(dateExpression);
		Matcher yearMatcher = yearPattern.matcher(dateExpression);
		Matcher weekDayMatcher = weekDayPattern.matcher(dateExpression);
		
		String arbitrary = dateExpression;
		if (symDayMatcher.find()) {
			arbitrary = arbitrary.replace(symDayMatcher.group(0), "");
		}
		if (dayMatcher.find()) {
			arbitrary = arbitrary.replace(dayMatcher.group(0), "");
		}
		if (weekMatcher.find()) {
			arbitrary = arbitrary.replace(weekMatcher.group(0), "");
		}
		if (monthMatcher.find()) {
			arbitrary = arbitrary.replace(monthMatcher.group(0), "");
		}
		if (yearMatcher.find()) {
			arbitrary = arbitrary.replace(yearMatcher.group(0), "");
		}
		if (weekDayMatcher.find()) {
			arbitrary = arbitrary.replace(weekDayMatcher.group(0), "");
		}
		return arbitrary;
	}

	/**
	 * Calculate the week day pattern
	 * 
	 * @param dg: DateGeneratorUtilities
	 * @param startDayOfWeek: int
	 * @param dateExpression: String
	 * @return a date time
	 * @throws ParkarCoreCommonException
	 * 			: cutomized Parkar core common exception
	 */
	public DateTime calculateWeekDayPattern(DateGeneratorUtilities dg, int startDayOfWeek, String dateExpression) throws ParkarCoreCommonException {
		Matcher weekDayMatcher = weekDayPattern.matcher(dateExpression);
		if (weekDayMatcher.find()) {
			String weekDayInString = weekDayMatcher.group(3).toLowerCase();
			int weekDay = (int) Arrays.asList(WEEKDAYS).stream().filter(x -> ((String) x.getKey()).equalsIgnoreCase(weekDayInString)).findFirst().get().getValue();
	
			if (weekDayMatcher.group(1).isEmpty()){
				return dg.getNthWeekDayAs(startDayOfWeek, 0, weekDay);
			}else if (weekDayMatcher.group(1).equals("+")){
				return dg.getNthWeekDayAs(startDayOfWeek, Integer.parseInt(weekDayMatcher.group(2)), weekDay);
			}else{
				return dg.getNthWeekDayAs(startDayOfWeek, -Integer.parseInt(weekDayMatcher.group(2)), weekDay);
			}
		}else{
			throw new ParkarCoreCommonException("Cannot find proper Week symblic day for the date expression " + dateExpression);
		}
	}
	
}
