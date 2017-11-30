package com.parkar.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.parkar.dateutils.DateGenerator;

public class DateUtilsStartDayOfWeekTest {
	private static final String FORMAT = "yyyy-MM-dd";
	DateGenerator dg;
	SimpleDateFormat sdf;
	Calendar cal;
	Date now;
	
	@Before
	public void setUp() throws Exception {
		//set up objects that generates expected date
		sdf = new SimpleDateFormat(FORMAT);
		cal = new GregorianCalendar();
		now = new Date();
	}

	@Test
	public void testSundayAsDefaultStartDay() throws ParseException {
		//Sunday as the default start date
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("dateFormat", FORMAT);
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/21/2016")), dg.getNthMonday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/2016")), dg.getNthTuesday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/23/2016")), dg.getNthWednesday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/24/2016")), dg.getNthThursday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/25/2016")), dg.getNthFriday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/26/2016")), dg.getNthSaturday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/20/2016")), dg.getNthSunday(-10));
		
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/30/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016")), dg.getCurrentSunday());
		
		
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/16/2016")), dg.getNthMonday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/17/2016")), dg.getNthTuesday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/18/2016")), dg.getNthWednesday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/19/2016")), dg.getNthThursday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/20/2016")), dg.getNthFriday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/21/2016")), dg.getNthSaturday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/15/2016")), dg.getNthSunday(-2));
		
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/30/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016")), dg.getCurrentSunday());
		
		
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/16/2016")), dg.getNthMonday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/17/2016")), dg.getNthTuesday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/18/2016")), dg.getNthWednesday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/19/2016")), dg.getNthThursday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/20/2016")), dg.getNthFriday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/21/2016")), dg.getNthSaturday(-2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/15/2016")), dg.getNthSunday(-2));
		
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/30/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016")), dg.getCurrentSunday());
	}
	
	@Test
	public void testMondayAsDefaultStartDay() throws ParseException {
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("dateFormat", FORMAT);
		//set startDayOfWeek to Monday, 1 is Monday so on so forth
		options.put("startDayOfWeek", "1");
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/21/2016")), dg.getNthMonday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/2016")), dg.getNthTuesday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/23/2016")), dg.getNthWednesday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/24/2016")), dg.getNthThursday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/25/2016")), dg.getNthFriday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/26/2016")), dg.getNthSaturday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/27/2016")), dg.getNthSunday(-10));
		
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/30/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/05/2016")), dg.getCurrentSunday());
	}


	@Test
	public void testManyWeekDayAsDefaultStartDay() throws ParseException {
		HashMap<String,String> options = new HashMap<String,String>();
		options.put("dateFormat", FORMAT);
		//set startDayOfWeek to Thursday, 1 is Monday so on so forth
		options.put("startDayOfWeek", "4");
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/24/2016")), dg.getNthThursday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/16/2016")), dg.getNthThursday(2));

		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/30/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/26/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/27/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/28/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016")), dg.getCurrentSunday());
		
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("03/17/2016")), dg.getNthThursday(-10));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/09/2016")), dg.getNthThursday(2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/12/2016")), dg.getNthSunday(2));

		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/30/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/26/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/27/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/28/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/29/2016")), dg.getCurrentSunday());
		
		//Set it to Tuesday
		options.put("startDayOfWeek", "2");
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("04/26/2016")), dg.getNthTuesday(-5));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2016")), dg.getNthSunday(-5));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/14/2016")), dg.getNthTuesday(2));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/19/2016")), dg.getNthSunday(2));

		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/06/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/31/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/05/2016")), dg.getCurrentSunday());
		
		//Set it to WEDNESDAY
		options.put("startDayOfWeek", "3");
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016"),options);	
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/11/2016")), dg.getNthWednesday(-3));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("05/15/2016")), dg.getNthSunday(-3));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/29/2016")), dg.getNthWednesday(4));
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("07/03/2016")), dg.getNthSunday(4));
		
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/06/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/07/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/05/2016")), dg.getCurrentSunday());
		
		dg = new DateGenerator(new SimpleDateFormat("MM/dd/yyyy").parse("06/05/2016"),options);
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/06/2016")), dg.getCurrentMonday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/07/2016")), dg.getCurrentTuesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2016")), dg.getCurrentWednesday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/02/2016")), dg.getCurrentThursday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/03/2016")), dg.getCurrentFriday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/04/2016")), dg.getCurrentSaturday());
		assertEquals(sdf.format(new SimpleDateFormat("MM/dd/yyyy").parse("06/05/2016")), dg.getCurrentSunday());
	}
}
