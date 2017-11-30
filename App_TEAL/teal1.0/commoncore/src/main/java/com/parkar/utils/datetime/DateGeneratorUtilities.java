package com.parkar.utils.datetime;

import java.time.DayOfWeek;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class DateGeneratorUtilities {
	
	private Date currentDate;
	

	/**
	 * Constructor of Generator utility for date
	 */
	public DateGeneratorUtilities(){
		this.currentDate = new Date();
	}
	
	/**
	 * Constructor of Generator utility for date with one parameter
	 * 
	 * @param date: Date
	 */
	public DateGeneratorUtilities(Date date) {
		this.currentDate = date;
	}

	/**
	 * Get year with year as offset
	 * 
	 * @param yearOffset: int
	 * @return year time
	 */
	public DateTime getYearWithOffset(int yearOffset){
		return new DateTime(currentDate).plusYears(yearOffset);	
	}
	
	/**
	 * Get month with month as offset
	 * 
	 * @param monthOffset: int
	 * @return month time
	 */
	public DateTime getMonthWithOffset(int monthOffset){
		return new DateTime(currentDate).plusMonths(monthOffset);	
	}
	
	/**
	 * Get week with week as offset
	 * 
	 * @param weekOffset: int
	 * @return week time
	 */
	public DateTime getWeekWithOffset(int weekOffset){
		return new DateTime(currentDate).plusWeeks(weekOffset);
	}
	
	/**
	 * Get day with day as offset
	 * 
	 * @param dayOffset: int
	 * @return day time
	 */
	public DateTime getDayWithOffset(int dayOffset){
		return new DateTime(currentDate).plusDays(dayOffset);
	}

	/*
	 * Week day functions
	 * 
	 */
	
	/**
	 * Get the Nth week day
	 * 
	 * @param startDayOfWeek: int
	 * @param weekOffset: int
	 * @param weekDay: int
	 * @return return the Nth week day time
	 */
	public DateTime getNthWeekDayAs(int startDayOfWeek, int weekOffset, int weekDay) {
		int currentWeekDay = getDayWithOffset(0).dayOfWeek().get();
		
		if(startDayOfWeek == DateTimeConstants.SUNDAY){
			if(currentWeekDay == DateTimeConstants.SUNDAY){
				if(weekDay == DateTimeConstants.SUNDAY){
						return getWeekDayAs(weekOffset, weekDay);
				}else{
					//because joda time start the week in Monday. If the current date is Sunday
					//we need to go back less one week
					if(weekOffset <= 0){
						return getWeekDayAs(weekOffset+1, weekDay);
					}
				}
			}else{
				if(weekDay == DateTimeConstants.SUNDAY){
					return getWeekDayAs(weekOffset - 1, weekDay);
				}
			}
		}else if (startDayOfWeek == DateTimeConstants.MONDAY){
			return getWeekDayAs(weekOffset, weekDay);
		}else{
			if(currentWeekDay == DateTimeConstants.SUNDAY){
				if(weekDay < startDayOfWeek){
					return getWeekDayAs(weekOffset + 1, weekDay);
				}
			}else{
				//If the currentWeekDay behind startDayOfWeek. it means a new week has not started, if a weekDay is ahead of startDayOfWeek, we need to go back 1 more week
				if(currentWeekDay < startDayOfWeek){
					if(weekDay >= startDayOfWeek){
						if(weekOffset == 0){
							return getWeekDayAs(weekOffset - 1, weekDay);
						}
					}
				}else if(currentWeekDay >= startDayOfWeek){
					//If currentWeekDay ahead startDayOfWeek, it means a new week is started already and if the weekDay is behind startDayOfWeek, we need advance 1 more week
					if(weekDay < startDayOfWeek){
						return getWeekDayAs(weekOffset + 1, weekDay);
					}
				}
			}
		}
		
		return getWeekDayAs(weekOffset, weekDay);
	}
	
	/**
	 * Get week DateTime with week as offset
	 * 
	 * @param weekOffset: int
	 * @param weekDay: int
	 * @return return week day time
	 */
	public DateTime getWeekDayAs(int weekOffset, int weekDay){
		return new DateTime(currentDate).plusWeeks(weekOffset).withDayOfWeek(weekDay);	
	}
	
	/**
	 * Get the day of the week with day as an offset
	 * 
	 * @param dayOffset: int
	 * @return return the day of the week
	 */
	public DayOfWeek getDayOfWeekAs(int dayOffset){
		return DayOfWeek.of(getDayWithOffset(dayOffset).dayOfWeek().get());	
	}
	/**
	 * Get the current time
	 * 
	 * @return current time
	 */
	public DateTime getCurrentTime(){
		return new DateTime(currentDate);
	}
}
