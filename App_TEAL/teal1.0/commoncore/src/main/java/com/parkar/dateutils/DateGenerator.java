package com.parkar.dateutils;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTimeConstants;

import com.parkar.utils.datetime.DateGeneratorUtilities;

public class DateGenerator {
	private String dateFormat;
	private int startDayOfWeek;

	private DateGeneratorUtilities dateUtil;

	/**
	 * Default constructor
	 */
	public DateGenerator() {
		this.dateUtil = new DateGeneratorUtilities();
	}

	/**
	 * Constructor with one Map parameter, this map saves the format of  date.
	 * 
	 * @param options: Map, format of date
	 */
	public DateGenerator(Map<String, String> options) {
		this();
		this.dateFormat = options.containsKey("dateFormat") ? options.get("dateFormat") : "yyyy-MM-dd";
		this.startDayOfWeek = options.containsKey("startDayOfWeek") ? Integer.parseInt(options.get("startDayOfWeek"))
				: DateTimeConstants.SUNDAY;
	}

	/**
	 * Constructor with two parameters. This constructor can only be used for testing purpose
	 * 
	 * @param date: Date 
	 * @param options: Map
	 */
	public DateGenerator(Date date, Map<String, String> options) {
		this.dateUtil = new DateGeneratorUtilities(date);
		this.dateFormat = options.containsKey("dateFormat") ? options.get("dateFormat") : "yyyy-MM-dd";
		this.startDayOfWeek = options.containsKey("startDayOfWeek") ? Integer.parseInt(options.get("startDayOfWeek"))
				: DateTimeConstants.SUNDAY;

	}

	/**
	 * Get date with offset
	 * 
	 * @param dayOffset:int 
	 * @return date in String
	 */
	public String getDate(int dayOffset) {
		return dateUtil.getDayWithOffset(dayOffset).toString(dateFormat);
	}

	/**
	 * Get date with offset and an arbitrary time
	 * 
	 * @param dayOffset: int 
	 * @param arbitraryTime: String 
	 * @return date in String
	 */
	public String getDateTime(int dayOffset, String arbitraryTime) {
		return dateUtil.getDayWithOffset(dayOffset).toString(dateFormat) + arbitraryTime;
	}

	/**
	 * Get year with year as an offset.
	 * 
	 * @param yearOffset:int 
	 * @return year in String
	 */
	public String getYearAs(int yearOffset) {
		return dateUtil.getYearWithOffset(yearOffset).toString(dateFormat);
	}

	/**
	 * Get month with month as an offset.
	 * 
	 * @param monthOffset: int 
	 * @return month in String
	 */
	public String getMonthAs(int monthOffset) {
		return dateUtil.getMonthWithOffset(monthOffset).toString(dateFormat);
	}

	/**
	 * Get week with week as an offset.
	 * 
	 * @param weekOffset: int 
	 * @return week in String
	 */
	public String getWeekAs(int weekOffset) {
		return dateUtil.getWeekWithOffset(weekOffset).toString(dateFormat);
	}

	/**
	 * Get today's date.
	 * 
	 * @return today in String
	 */
	public String getToday() {
		return getDate(0);
	}

	/**
	 * Get tomorrow's date.
	 * 
	 * @return tomorrow in String
	 */
	public String getTomorrow() {
		return getDate(1);
	}

	/**
	 * Get the day after tomorrow
	 * 
	 * @return the day after tomorrow in String
	 */
	public String getDayAfterTomorrow() {
		return getDate(2);
	}

	/**
	 * Get yesterday's date
	 * 
	 * @return yesterday in String
	 */
	public String getYesterday() {
		return getDate(-1);
	}

	/**
	 * Get the day before yesterday
	 * 
	 * @return the day before yesterday in String
	 */
	public String getDayBeforeYesterday() {
		return getDate(-2);
	}

	/**
	 * Get current year
	 * 
	 * @return current year in String
	 */
	public String getCurrentYear() {
		return getYearAs(0);
	}

	/**
	 * Get next year
	 * 
	 * @return next year in String
	 */
	public String getNextYear() {
		return getYearAs(1);
	}

	/**
	 * Get last year
	 * 
	 * @return last year in String
	 */
	public String getLastYear() {
		return getYearAs(-1);
	}

	/**
	 * Get current month
	 * 
	 * @return current month in String
	 */
	public String getCurrentMonth() {
		return getMonthAs(0);
	}

	/**
	 * Get next month
	 * 
	 * @return next month in String
	 */
	public String getNextMonth() {
		return getMonthAs(1);
	}

	/**
	 * Get last month
	 * 
	 * @return last month in String
	 */
	public String getLastMonth() {
		return getMonthAs(-1);
	}

	/**
	 * Get current week
	 * 
	 * @return current week in String
	 */
	public String getCurrentWeek() {
		return getWeekAs(0);
	}

	/**
	 * Get next week
	 * 
	 * @return next week in String
	 */
	public String getNextWeek() {
		return getWeekAs(1);
	}

	/**
	 * Get last week
	 * 
	 * @return last week in String
	 */
	public String getLastWeek() {
		return getWeekAs(-1);
	}

	/**
	 * If weekOffSet is 0, current Monday will be returned 
	 * If weekOffSet is 10, the next 10th Monday will be returned
	 * If weekOffSet is -1, previous Monday will be returned
	 * 
	 * @param weekOffset: int
	 * @return date in the given format
	 */
	public String getNthMonday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.MONDAY).toString(dateFormat);
	}

	/**
	 * Get Current Monday of the week 
	 * 
	 * @return date in the given format
	 */
	public String getCurrentMonday() {
		return getNthMonday(0);
	}

	/**
	 * Get next Monday of the week.
	 * 
	 * @return date in the given format
	 */
	public String getNextMonday() {
		return getNthMonday(1);
	}

	/**
	 * Get last Monday of the week.
	 * 
	 * @return date in the given format
	 */
	public String getLastMonday() {
		return getNthMonday(-1);
	}

	/**
	 * Get the nth Tuesday of the week
	 * 
	 * @param weekOffset: int 
	 * @return date in the given format
	 */
	public String getNthTuesday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.TUESDAY).toString(dateFormat);
	}

	/**
	 * Get current Tuesday of the week.
	 * 
	 * @return date in the given format
	 */
	public String getCurrentTuesday() {
		return getNthTuesday(0);
	}

	/**
	 * Get next Tuesday of the week.
	 * 
	 * @return date in the given format
	 */
	public String getNextTuesday() {
		return getNthTuesday(1);
	}

	/**
	 * Get last Tuesday of the week
	 * 
	 * @return date in the given format
	 */
	public String getLastTuesday() {
		return getNthTuesday(-1);
	}

	/**
	 * Get the nth Wednesday of the week 
	 * 
	 * @param weekOffset: int 
	 * @return date in the given format
	 */
	public String getNthWednesday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.WEDNESDAY).toString(dateFormat);
	}

	/**
	 * Get current Wednesday of the week you are in
	 * 
	 * @return date in the given format
	 */
	public String getCurrentWednesday() {
		return getNthWednesday(0);
	}

	/**
	 * Get the nth Thursday of the week you are in
	 * 
	 * @param weekOffset: int 
	 * @return date in the given format
	 */
	public String getNthThursday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.THURSDAY).toString(dateFormat);
	}

	/**
	 * Get current Thursday of the week you are in
	 * 
	 * @return date in the given format
	 */
	public String getCurrentThursday() {
		return getNthThursday(0);
	}

	/**
	 * Get the nth Friday of the week you are in
	 * 
	 * @param weekOffset: int 
	 * @return date in the given format
	 */
	public String getNthFriday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.FRIDAY).toString(dateFormat);
	}

	/**
	 * Get current Friday of the week you are in
	 * 
	 * @return date in the given format
	 */
	public String getCurrentFriday() {
		return getNthFriday(0);
	}

	/**
	 * Get the nth Saturday of the week you are in
	 * 
	 * @param weekOffset: int 
	 * @return date in the given format
	 */
	public String getNthSaturday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.SATURDAY).toString(dateFormat);
	}

	/**
	 * Get current Saturday of the week you are in
	 * 
	 * @return date in the given format
	 */
	public String getCurrentSaturday() {
		return getNthSaturday(0);
	}

	/**
	 * Get the nth Sunday of the week you are in
	 * 
	 * @param weekOffset: int 
	 * @return date in the given format
	 */
	public String getNthSunday(int weekOffset) {
		return dateUtil.getNthWeekDayAs(startDayOfWeek, weekOffset, DateTimeConstants.SUNDAY).toString(dateFormat);
	}

	/**
	 * Get current Sunday of the week you are in
	 * 
	 * @return date in the given format
	 */
	public String getCurrentSunday() {
		return getNthSunday(0);
	}

	/**
	 * Get today's week day in text
	 * 
	 * @return date in the given format
	 */
	public String getTodayWeekDayInText() {
		return getWeekDayInText(0);
	}

	/**
	 * Get week day via day offset in text
	 * 
	 * @param dayoffset: int 
	 * @return date in the given format
	 */
	public String getWeekDayInText(int dayoffset) {
		return dateUtil.getDayOfWeekAs(dayoffset).name();
	}

	/**
	 * Get week day via day offset in int value
	 * 
	 * @param dayoffset: int 
	 * @return date in int format
	 */
	public int getWeekDayInValue(int dayoffset) {
		return dateUtil.getDayOfWeekAs(dayoffset).getValue();
	}
}
