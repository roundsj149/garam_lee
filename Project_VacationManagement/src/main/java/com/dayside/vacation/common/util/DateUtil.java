package com.dayside.vacation.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * String, Date, Calander 변환
 * @author April
 *
 */
public class DateUtil {
	
	/**
	 * Date -> String: yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToStringHypen(Date date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return transFormat.format(date);
	}
	
	/**
	 * Date -> String: yyyy-MM-dd HH:mm
	 * @param date
	 * @return
	 */
	public static String dateToStringHypen2(Date date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return transFormat.format(date);
	}

	/**
	 * Date -> String: yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String dateToStringNoHypen(Date date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		return transFormat.format(date);
	}
	
	/**
	 * Date -> String: yyyy
	 * @param date
	 * @return
	 */
	public static String dateToStringYear(Date date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy");
		return transFormat.format(date);
	}
	
	/**
	 * String -> Date: yyyy-MM-dd
	 * @param str
	 * @return
	 * @throws ParseException 
	 */
	public static Date stringToDate(String str) throws ParseException {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		return transFormat.parse(str);
	}
	
	/**
	 * Date -> Calendar
	 * @param date
	 * @return
	 */
	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static Calendar dateToCalendarTime(Date date, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		return calendar;
	}
	
	/**
	 * Calendar -> String(Year)
	 * @param calendar
	 * @return
	 */
	public static String getYearFromCalendar(Calendar calendar) {
		return Integer.toString(calendar.get(Calendar.YEAR));
	}
}
