package com.parkar.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.parkar.dateutils.DateGenerator;

public class DateUtilsTest {
	private static final String FORMAT = "yyyy-MM-dd";
	DateGenerator dg;
	
	SimpleDateFormat sdf;
	Calendar cal;
	Date now;
	
	@Before
	public void setUp() throws Exception {
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("dateFormat", FORMAT);
		dg = new DateGenerator(options);
		
		//set up objects that generates expected date
		sdf = new SimpleDateFormat(FORMAT);
		cal = new GregorianCalendar();
		now = new Date();
	}

	@Test
	public void testGetDate() {
		// test current day
		cal.setTime(now);
		assertEquals(sdf.format(cal.getTime()), dg.getDate(0));
		assertEquals(sdf.format(cal.getTime()), dg.getToday());
		
		// test 10 day in the future
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 10);
		assertEquals(sdf.format(cal.getTime()), dg.getDate(10));

		// test 30 day in the past
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		assertEquals(sdf.format(cal.getTime()), dg.getDate(-30));
		
		// test tomorrow
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		assertEquals(sdf.format(cal.getTime()), dg.getTomorrow());
		
		
		// test day after tomorrow
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 2);
		assertEquals(sdf.format(cal.getTime()), dg.getDayAfterTomorrow());
		
		// test yesterday
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		assertEquals(sdf.format(cal.getTime()), dg.getYesterday());
		
		//test day before yesterday
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, -2);
		assertEquals(sdf.format(cal.getTime()), dg.getDayBeforeYesterday());
	}
	
	@Test
	public void testGetYear() {
		//test day in next year
		cal.setTime(now);
		cal.add(Calendar.YEAR, 1);
		assertEquals(sdf.format(cal.getTime()), dg.getYearAs(1));
		assertEquals(sdf.format(cal.getTime()), dg.getNextYear());

		//test date in past year
		cal.setTime(now);
		cal.add(Calendar.YEAR, -10);
		assertEquals(sdf.format(cal.getTime()), dg.getYearAs(-10));
		
		cal.setTime(now);
		assertEquals(sdf.format(cal.getTime()), dg.getYearAs(0));
		assertEquals(sdf.format(cal.getTime()), dg.getCurrentYear());

		cal.setTime(now);
		cal.add(Calendar.YEAR, -1);
		assertEquals(sdf.format(cal.getTime()), dg.getLastYear());
	}
	
	@Test
	public void testGetMonth() {
		cal.setTime(now);
		assertEquals(sdf.format(cal.getTime()), dg.getMonthAs(0));
		assertEquals(sdf.format(cal.getTime()), dg.getCurrentMonth());
		
		//test day in next month
		cal.setTime(now);
		cal.add(Calendar.MONTH, 1);
		assertEquals(sdf.format(cal.getTime()), dg.getMonthAs(1));
		assertEquals(sdf.format(cal.getTime()), dg.getNextMonth());

		//test date in past month
		cal.setTime(now);
		cal.add(Calendar.MONTH, -10);
		assertEquals(sdf.format(cal.getTime()), dg.getMonthAs(-10));
		
		cal.setTime(now);
		cal.add(Calendar.MONTH, -1);
		assertEquals(sdf.format(cal.getTime()), dg.getLastMonth());
	}
	
	
	@Test
	public void testGetWeek() {
		cal.setTime(now);
		assertEquals(sdf.format(cal.getTime()), dg.getWeekAs(0));
		assertEquals(sdf.format(cal.getTime()), dg.getCurrentWeek());
		
		//test date in next week
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 1*7);
		assertEquals(sdf.format(cal.getTime()), dg.getWeekAs(1));
		assertEquals(sdf.format(cal.getTime()), dg.getNextWeek());

		//test date in past week
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, -10*7);
		assertEquals(sdf.format(cal.getTime()), dg.getWeekAs(-10));
		
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, -1*7);
		assertEquals(sdf.format(cal.getTime()), dg.getLastWeek());
	}
}
