package com.parkar.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.parkar.dateutils.DateParser;
import com.parkar.exception.ParkarCoreCommonException;

public class DateParserUtilsTest {
	private static final String FORMAT = "yyyy-MM-dd";
	DateParser dp;
	SimpleDateFormat sdf, sdfForExpected;

	@Before
	public void setUp() throws Exception {
		sdf = new SimpleDateFormat(FORMAT);
		sdfForExpected = new SimpleDateFormat("MM/dd/yyyy");
	}

	@Test
	public void testBasic() throws ParseException, ParkarCoreCommonException {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("dateFormat", FORMAT);
		dp = new DateParser(sdfForExpected.parse("06/01/2016"), options);

		assertEquals(sdf.format(sdfForExpected.parse("06/02/2016")), dp.parseDatePattern("1d"));
		assertEquals(sdf.format(sdfForExpected.parse("06/02/2016")) + "08:00:00", dp.parseDatePattern("1d08:00:00"));
		assertEquals(sdf.format(sdfForExpected.parse("06/11/2016")), dp.parseDatePattern("10d"));
		assertEquals(sdf.format(sdfForExpected.parse("09/09/2016")), dp.parseDatePattern("+100d"));
		assertEquals(sdf.format(sdfForExpected.parse("05/22/2016")), dp.parseDatePattern("-10d"));

		assertEquals(sdf.format(sdfForExpected.parse("06/08/2016")), dp.parseDatePattern("+1w"));
		assertEquals(sdf.format(sdfForExpected.parse("06/22/2016")), dp.parseDatePattern("3w"));
		assertEquals(sdf.format(sdfForExpected.parse("05/18/2016")), dp.parseDatePattern("-2w"));

		assertEquals(sdf.format(sdfForExpected.parse("07/01/2016")), dp.parseDatePattern("+1M"));

		assertEquals(sdf.format(sdfForExpected.parse("08/01/2015")), dp.parseDatePattern("-10M"));
		assertEquals(sdf.format(sdfForExpected.parse("06/01/2017")), dp.parseDatePattern("+12M"));

		assertEquals(sdf.format(sdfForExpected.parse("06/01/2028")), dp.parseDatePattern("+12y"));

		assertEquals(sdf.format(sdfForExpected.parse("05/30/2016")), dp.parseDatePattern("MONDAY"));
		assertEquals(sdf.format(sdfForExpected.parse("05/31/2016")), dp.parseDatePattern("Tuesday"));
		assertEquals(sdf.format(sdfForExpected.parse("06/01/2016")), dp.parseDatePattern("Wednesday"));
		assertEquals(sdf.format(sdfForExpected.parse("06/02/2016")), dp.parseDatePattern("thursDAY"));
		assertEquals(sdf.format(sdfForExpected.parse("06/03/2016")), dp.parseDatePattern("fridaY"));
		assertEquals(sdf.format(sdfForExpected.parse("06/04/2016")), dp.parseDatePattern("Saturday"));
		assertEquals(sdf.format(sdfForExpected.parse("05/29/2016")), dp.parseDatePattern("SUNDAY"));
		assertEquals(sdf.format(sdfForExpected.parse("06/07/2016")), dp.parseDatePattern("+1tuesday"));
		assertEquals(sdf.format(sdfForExpected.parse("05/12/2016")), dp.parseDatePattern("-3thursday"));

		assertEquals(sdf.format(sdfForExpected.parse("10/31/2015")), dp.parseDatePattern("-1y+5M-1d"));
		assertEquals(sdf.format(sdfForExpected.parse("11/01/2015")), dp.parseDatePattern("Tomorrow-1y+5M-1d"));
		assertEquals(sdf.format(sdfForExpected.parse("10/30/2015")), dp.parseDatePattern("-1y+5M-1dYesterday"));
		assertEquals(sdf.format(sdfForExpected.parse("10/29/2015")), dp.parseDatePattern("-1y+5M-1ddayBeforeYesterday"));
		assertEquals(sdf.format(sdfForExpected.parse("07/22/2018")) + "T23:59:00", dp.parseDatePattern("2M-10d2yT23:59:00"));
		assertEquals(sdf.format(sdfForExpected.parse("07/20/2018")) + "T23:59:00", dp.parseDatePattern("Monday2M-10d2yT23:59:00"));
		assertEquals(sdf.format(sdfForExpected.parse("05/28/2016")), dp.parseDatePattern("SUNDAY-1d"));
		assertEquals(sdf.format(sdfForExpected.parse("04/03/2017")), dp.parseDatePattern("+10MFriday"));

		assertEquals(sdf.format(sdfForExpected.parse("05/24/2018")) + "T11:24:20", dp.parseDatePattern("2016-04-03", "2M-10d2yT11:24:20"));
		assertEquals(sdf.format(sdfForExpected.parse("03/07/2018")) + "T11:24:20", dp.parseDatePattern("2016-01-03", "+2SUNDAY2M-10d2yT11:24:20"));

		// pass different format than the default format
		assertEquals("5/24/2018T11:24:20", dp.parseDatePattern("2016-04-03", "2M-10d2yT11:24:20", "M/dd/yyyy"));

		assertTrue(dp.isWeekEnd("2016-05-29"));
		assertTrue(dp.isWeekEnd("2016-05-28"));
		assertFalse(dp.isWeekEnd("2016-05-25"));

	}

	@Test
	public void testBasicNegative() throws ParseException {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("dateFormat", FORMAT);
		dp = new DateParser(sdfForExpected.parse("06/01/2016"), options);

		String rs = null;
		try {
			rs = dp.parseDatePattern("MONsDAY");
		} catch (ParkarCoreCommonException e) {
		}
		assertEquals(null, rs);

		try {
			rs = dp.parseDatePattern("");
		} catch (ParkarCoreCommonException e) {
		}
		assertEquals(null, rs);

		try {
			rs = dp.parseDatePattern("+10mFsriday");
		} catch (ParkarCoreCommonException e) {
		}
		assertEquals(null, rs);

		try {
			rs = dp.parseDatePattern("tsday");
		} catch (ParkarCoreCommonException e) {
		}
		assertEquals(null, rs);
	}
}
