package com.company.IntelligentPlatform.common.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.common.model.*;

public class ServiceEntityDateHelper {

	/**
	 * Get the differential days between startDate & endDate
	 * 
	 * @param startDate
	 *            :earlier date
	 * @param endDate
	 *            :later date
	 * @return
	 */
	public static long getDiffDays(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate) {
		if (startDate == null || endDate == null) return 0L;
		return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
	}

	public static long getDiffDays(Date startDate, Date endDate) {
		Date lateDate = new Date();
		Date earlyDate = new Date();
		// for the null data
		if (startDate == null) {
			earlyDate = new Date();
		} else {
			earlyDate = startDate;
		}
		if (endDate == null) {
			lateDate = new Date();
		} else {
			lateDate = endDate;
		}
		long diff = lateDate.getTime() - earlyDate.getTime();
		long days = diff / (24 * 60 * 60 * 1000);
		return days;
	}

	public static Date getEarliestDate(List<Date> dateList) {
		if (dateList == null || dateList.size() == 0) {
			return null;
		}
		Date strDate = dateList.get(0);
		for (int i = 1; i < dateList.size(); i++) {
			long diff = dateList.get(i).getTime() - strDate.getTime();
			if (diff < 0) {
				strDate = dateList.get(i);
			}
		}
		return strDate;
	}

	public static Date getLatestDate(List<Date> dateList) {
		if (dateList == null || dateList.size() == 0) {
			return null;
		}
		Date strDate = dateList.get(0);
		for (int i = 1; i < dateList.size(); i++) {
			long diff = dateList.get(i).getTime() - strDate.getTime();
			if (diff > 0) {
				strDate = dateList.get(i);
			}
		}
		return strDate;
	}
	
	/**
	 * Get the mill Seconds from 1970,01,01 to specified date
	 * @param date
	 * @return
	 */
	public static long getMillSeconds(Date date) {
	    Calendar calendar1 = Calendar.getInstance();
	    Calendar calendar2 = Calendar.getInstance();
	    calendar1.set(1970, 01, 01);
	    calendar2.setTime(date);
	    long milliseconds1 = calendar1.getTimeInMillis();
	    long milliseconds2 = calendar2.getTimeInMillis();
	    long diff = milliseconds2 - milliseconds1;	    
	    return diff;
	}

	/**
	 * Get the differential days between startDate & endDate
	 * 
	 * @param startDate
	 *            :later date
	 * @param endDate
	 *            :earlier date
	 * @return
	 */
	public static long getDiffWeeks(Date startDate, Date endDate) {
		Date lateDate = new Date();
		Date earlyDate = new Date();
		// for the null data
		if (startDate == null) {
			earlyDate = new Date();
		} else {
			earlyDate = startDate;
		}
		if (endDate == null) {
			lateDate = new Date();
		} else {
			lateDate = endDate;
		}
		long diff = lateDate.getTime() - earlyDate.getTime();
		long days = diff / (24 * 60 * 60 * 1000 * 7);
		return days;
	}

	public static Date getEarliestDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(1900, 1, 1);
		return calendar.getTime();
	}

	/**
	 * Get start date by calendar model
	 * 
	 * @param calendarModel
	 * @return
	 */
	public static Date getStartDate(CalendarModel calendarModel) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendarModel.getYear());
		if (calendarModel.getUnit() == Calendar.MONTH) {
			calendar.set(Calendar.MONTH, calendarModel.getIndex() - 1);
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			// Set hour to 0
			calendar.set(Calendar.HOUR, 0);
			// Set minute to 0
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		}
		if (calendarModel.getUnit() == Calendar.WEEK_OF_YEAR) {
			calendar.set(Calendar.WEEK_OF_YEAR, calendarModel.getIndex() - 1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			// Set hour to 0
			calendar.set(Calendar.HOUR, 0);
			// Set minute to 0
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * Adjust the current date by [diffDays]
	 * 
	 * @param rawDate
	 * @param diffDays
	 *            : if diffDays > 0, then adjust date later, if diffDays < 0,
	 *            then adjust the date earlier.
	 * @return
	 */
	public static Date adjustDays(Date rawDate, int diffDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rawDate);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + diffDays);
		return calendar.getTime();
	}

	public static java.time.LocalDateTime adjustDays(java.time.LocalDateTime rawDate, double diffDays) {
		if (rawDate == null) return null;
		int leftIntDay = ServiceEntityDoubleHelper.getLeftInteger(diffDays);
		double leftFractDay = ServiceEntityDoubleHelper.getLeftDeciFraction(diffDays);
		int diffHoursInt = (int) Math.round(leftFractDay * 24);
		return rawDate.plusDays(leftIntDay).plusHours(diffHoursInt);
	}

	public static java.time.LocalDateTime adjustDays(java.time.LocalDateTime rawDate, int diffDays) {
		if (rawDate == null) return null;
		return rawDate.plusDays(diffDays);
	}
	
	/**
	 * Adjust the current date by [diffYears]
	 * 
	 * @param rawDate
	 * @param diffYears
	 *            : if diffYears > 0, then adjust date later, if diffYears < 0,
	 *            then adjust the date earlier.
	 * @return
	 */
	public static Date adjustYears(Date rawDate, int diffYears) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rawDate);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year + diffYears);
		return calendar.getTime();
	}
	
	/**
	 * Try all the format to parse date to String
	 * @param dateStr
	 * @return
	 */
	public static Date parseStringToDate(String dateStr){
		if(ServiceEntityStringHelper.checkNullString(dateStr)){
			return null;
		}
		Date resultDate = null;
		List<SimpleDateFormat> allDateFormatList = DefaultDateFormatConstant.getAllDateFormat();
		if(!ServiceCollectionsHelper.checkNullList(allDateFormatList)){
			for(SimpleDateFormat dateFormat:allDateFormatList){
				try {
					resultDate = dateFormat.parse(dateStr);
				} catch (ParseException e) {
					continue;
				}
				return resultDate;
			}
		}
		return null;
	}

	/**
	 * Adjust the current date by double value[diffDays]
	 * 
	 * @param rawDate
	 * @param diffDays
	 *            : if diffDays > 0, then adjust date later, if diffDays < 0,
	 *            then adjust the date earlier.
	 * @return
	 */
	public static Date adjustDays(Date rawDate, double diffDays) {
		double leftFractDay = ServiceEntityDoubleHelper
				.getLeftDeciFraction(diffDays);
		int leftIntDay = ServiceEntityDoubleHelper.getLeftInteger(diffDays);
		// Adjust the days
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rawDate);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + leftIntDay);
		// Adjust hours
		double diffHours = leftFractDay * 24;
		double leftFractHours = ServiceEntityDoubleHelper
				.getLeftDeciFraction(diffHours);
		int leftIntHours = ServiceEntityDoubleHelper.getLeftInteger(diffHours);
		int hour = calendar.get(Calendar.HOUR);
		calendar.set(Calendar.HOUR, hour + leftIntHours);
		// Adjust minutes
		double diffMins = leftFractHours * 60;
		int leftIntMins = ServiceEntityDoubleHelper
				.getLeftInteger(diffMins);
		int minute = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, minute + leftIntMins);
		return calendar.getTime();
	}

	/**
	 * Adjust the current date by [diffHours]
	 * 
	 * @param rawDate
	 * @param diffHours
	 *            : if diffHours > 0, then adjust time later, if diffHours < 0,
	 *            then adjust the time earlier.
	 * @return
	 */
	public static Date adjustHours(Date rawDate, int diffHours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rawDate);
		int hour = calendar.get(Calendar.HOUR);
		calendar.set(Calendar.HOUR, hour + diffHours);
		return calendar.getTime();
	}

	/**
	 * Get end date by calendar model
	 * 
	 * @param calendarModel
	 * @return
	 */
	public static Date getEndDate(CalendarModel calendarModel) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendarModel.getYear());
		if (calendarModel.getUnit() == Calendar.MONTH) {
			calendar.set(Calendar.MONTH, calendarModel.getIndex());
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			// Set hour to 0
			calendar.set(Calendar.HOUR, 0);
			// Set minute to 0
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		}
		if (calendarModel.getUnit() == Calendar.WEEK_OF_YEAR) {
			calendar.set(Calendar.WEEK_OF_YEAR, calendarModel.getIndex());
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			// Set hour to 0
			calendar.set(Calendar.HOUR, 0);
			// Set minute to 0
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		}
		return null;
	}

	public static int getDiffYear(Calendar startCalendar, Calendar endCalendar) {
		return endCalendar.get(Calendar.YEAR)
				- startCalendar.get(Calendar.YEAR);
	}

	public static int getDiffMonth(Calendar startCalendar, Calendar endCalendar) {
		int diffYear = getDiffYear(startCalendar, endCalendar);
		int startMonth = startCalendar.get(Calendar.MONTH);
		int endMonth = endCalendar.get(Calendar.MONTH);
		return 12 * diffYear + endMonth - startMonth;
	}

}
