package com.parkar.dateutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.utils.datetime.DateGeneratorUtilities;
import com.parkar.utils.datetime.DateParserUtilities;

/**
 * Date methods for adding and subtraction of dates to support date
 * modification. 
 * Input for these methods will come from text files, so they should receive and
 * return simple types.
 */
public class DateParser {

	private int startDayOfWeek;
	private SimpleDateFormat sdf;
	private DateParserUtilities dateParser;
	private DateGeneratorUtilities dateGenerator;

	/**
	 * Default constructor
	 */
	public DateParser() {
		this.dateParser = new DateParserUtilities();
		this.dateGenerator = new DateGeneratorUtilities();
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDayOfWeek = DateTimeConstants.SUNDAY;
	}

	/**
	 * Constructor with one Map parameter, the map contains the date format
	 * 
	 * @param options: Map
	 */
	public DateParser(Map<String, String> options) {
		this.dateParser = new DateParserUtilities();
		this.dateGenerator = new DateGeneratorUtilities();
		sdf = new SimpleDateFormat(options.containsKey("dateFormat") ? options.get("dateFormat") : "yyyy-MM-dd");
		startDayOfWeek = options.containsKey("startDayOfWeek") ? Integer.parseInt(options.get("startDayOfWeek"))
				: DateTimeConstants.SUNDAY;
	}

	/**
	 * Constructor with two parameters, Date date, Map parameter.
	 * This constructor can only be used for testing purpose NEVER use it for
	 * any other usage
	 * 
	 * @param date: Date 
	 * @param options: Map
	 */
	public DateParser(Date date, Map<String, String> options) {
		dateParser = new DateParserUtilities(date);
		dateGenerator = new DateGeneratorUtilities(date);

		sdf = new SimpleDateFormat(options.containsKey("dateFormat") ? options.get("dateFormat") : "yyyy-MM-dd");
		startDayOfWeek = options.containsKey("startDayOfWeek") ? Integer.parseInt(options.get("startDayOfWeek"))
				: DateTimeConstants.SUNDAY;
	}

	/**
	 * Parse the date pattern with only one parameter a string of date expression,
	 * this expression is the pattern of date, for example: today/day before yesterday/monday/thursday/-1y+5M-1dYesterda/+10MFriday
	 * 
	 * @param dateExpression: String 
	 * @return date in String
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public String parseDatePattern(String dateExpression) throws ParkarCoreCommonException {
		if (!dateParser.isValidPattern(dateExpression))
			throw new ParkarCoreCommonException("You date expression does not follow any pattern we have defined.");
		try {
			return parse(dateExpression, sdf.toLocalizedPattern(), dateParser.getCurrentDate(), dateGenerator);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParkarCoreCommonException(e.getMessage());
		}
	}

	/**
	 * Parse the date pattern with a string of date expression and a date, 
	 * this date is the given date and will be transfered to be a Date format and pass to parse function,
	 * the expression is the pattern of date, for example: today/day before yesterday/monday/thursday/-1y+5M-1dYesterda/+10MFriday
	 * 
	 * @param date: String 
	 * @param dateExpression: String 
	 * @return date in String
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public String parseDatePattern(String date, String dateExpression) throws ParkarCoreCommonException {
		if (!dateParser.isValidPattern(dateExpression))
			throw new ParkarCoreCommonException("You date expression does not follow any pattern we have defined.");
		try {
			//generate a date based on the date given by the user
			Date givenDate = new SimpleDateFormat(sdf.toLocalizedPattern()).parse(date);
			return parse(dateExpression, sdf.toLocalizedPattern(), dateParser.getDate(givenDate),
					new DateGeneratorUtilities(givenDate));
		} catch (ParseException e) {
			throw new ParkarCoreCommonException(e.getMessage());
		}
	}

	/**
	 * Parse the date pattern with a date expression, a date and a format, expression is the date pattern,
	 * the format will be a parameter of parse function of transfer the date, for example:  today/day before yesterday/monday/thursday/-1y+5M-1dYesterda/+10MFriday
	 * 
	 * 
	 * @param date: String 
	 * @param dateExpression: String 
	 * @param format : String
	 * @return date in String
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public String parseDatePattern(String date, String dateExpression, String format) throws ParkarCoreCommonException {
		if (!dateParser.isValidPattern(dateExpression))
			throw new ParkarCoreCommonException("You date expression does not follow any pattern we have defined.");
		try {
			//generate a date based on the date given by the user
			Date givenDate = new SimpleDateFormat(sdf.toLocalizedPattern()).parse(date);
			return parse(dateExpression, format, dateParser.getDate(givenDate), new DateGeneratorUtilities(givenDate));
		} catch (ParseException e) {
			throw new ParkarCoreCommonException(e.getMessage());
		}
	}

	/**
	 * check if the date is weekend or not
	 * 
	 * @param date: String 
	 * @return true or false in boolean
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	public boolean isWeekEnd(String date) throws ParkarCoreCommonException {
		try {
			Date givenDate = new SimpleDateFormat(sdf.toLocalizedPattern()).parse(date);
			int weekDay = dateParser.getDayOfWeek(givenDate);
			return (weekDay == DateTimeConstants.SUNDAY || weekDay == DateTimeConstants.SATURDAY);
		} catch (ParseException e) {
			throw new ParkarCoreCommonException(e.getMessage());
		}
	}
	
	/**
     * Compares two date strings for ordering.
     *
     * @param dateString0:   one date string
     * @param dateString1:   the other date string
     * @return  the value 0 if the dates are equal;
     *          a value less than 0 if the first date is before the second;
     *          and a value greater than 0 if the first date is before the second.
     *          If a date string cannot be parsed, the strings are compared lexicographically.
	 * @throws ParkarCoreCommonException 
	 * 		: customized Parkar core common exception
     */
    public int compareDateAsString(String dateString0, String dateString1) throws ParkarCoreCommonException {
        try {
            return sdf.parse(dateString0).compareTo(sdf.parse(dateString1));
        } catch (ParseException e) {
			throw new ParkarCoreCommonException(e.getMessage());
        }
    }
    
    /**
     *  Validate date pattern.
     * 
     * @param dateExpression: String
     * @return true or false in boolean
     */
    public boolean isValidatePattern(String dateExpression){
    	return dateParser.isValidPattern(dateExpression);
    }
    
    /**
	 * Parse date
	 * 
	 * @param dateExpression: String
	 * @param format: String
	 * @param dt: DateTime
	 * @param dg: DateGeneratorUtilities 
	 * @return parse result in String
	 * @throws ParseException
	 * @throws ParkarCoreCommonException
	 * 		: customized Parkar core common exception
	 */
	private String parse(String dateExpression, String format, DateTime dt, DateGeneratorUtilities dg)
			throws ParseException, ParkarCoreCommonException {
		if (dateParser.isWeekDayPatternPresents(dateExpression)) {
			dt = dateParser.calculateWeekDayPattern(dg, startDayOfWeek, dateExpression);
		}
		dt = dateParser.calculateDatePattern(dt, dateExpression);
		String arbitrary = dateParser.calculateArbitraryText(dateExpression);

		return arbitrary.trim().isEmpty() ? dt.toString(format) : dt.toString(format) + arbitrary;
	}
}
