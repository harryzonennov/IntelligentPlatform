package com.company.IntelligentPlatform.platform.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO-LEGACY: import platform.foundation.Controller.Basic.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.platform.controller.ISEDropDownResourceMapping;

public class DefaultDateFormatConstant {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat(
			"yyyy-MM");

	public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat(
			"yyyy-MM");

	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat DATE_MIN_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static final SimpleDateFormat DATE_HOUR_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH");

	public static final SimpleDateFormat HOUR_MIN_FORMAT = new SimpleDateFormat(
			"HH:mm");

	private static final DateTimeFormatter DT_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final DateTimeFormatter DT_DATE_MIN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private static final DateTimeFormatter DT_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Format an Object that may be a {@link Date}, {@link LocalDate}, {@link LocalDateTime},
	 * or {@link Instant} using the DATE_FORMAT pattern (yyyy-MM-dd).
	 * Hibernate 6 may return any of these for a DATETIME/DATE column declared as java.util.Date
	 * without @Temporal — all are handled safely here.
	 */
	public static String formatDate(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof LocalDateTime) {
			return ((LocalDateTime) value).format(DT_DATE);
		}
		if (value instanceof LocalDate) {
			return ((LocalDate) value).format(DT_DATE);
		}
		if (value instanceof Instant) {
			return LocalDateTime.ofInstant((Instant) value, ZoneId.systemDefault()).format(DT_DATE);
		}
		if (value instanceof Date) {
			return DATE_FORMAT.format((Date) value);
		}
		return value.toString();
	}

	/**
	 * Format an Object that may be a {@link Date}, {@link LocalDate}, {@link LocalDateTime},
	 * or {@link Instant} using the DATE_MIN_FORMAT pattern (yyyy-MM-dd HH:mm).
	 */
	public static String formatDateMin(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof LocalDateTime) {
			return ((LocalDateTime) value).format(DT_DATE_MIN);
		}
		if (value instanceof LocalDate) {
			return ((LocalDate) value).format(DT_DATE);
		}
		if (value instanceof Instant) {
			return LocalDateTime.ofInstant((Instant) value, ZoneId.systemDefault()).format(DT_DATE_MIN);
		}
		if (value instanceof Date) {
			return DATE_MIN_FORMAT.format((Date) value);
		}
		return value.toString();
	}

	/**
	 * Format an Object that may be a {@link Date}, {@link LocalDate}, {@link LocalDateTime},
	 * or {@link Instant} using the DATE_TIME_FORMAT pattern (yyyy-MM-dd HH:mm:ss).
	 */
	public static String formatDateTime(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof LocalDateTime) {
			return ((LocalDateTime) value).format(DT_DATE_TIME);
		}
		if (value instanceof LocalDate) {
			return ((LocalDate) value).format(DT_DATE);
		}
		if (value instanceof Instant) {
			return LocalDateTime.ofInstant((Instant) value, ZoneId.systemDefault()).format(DT_DATE_TIME);
		}
		if (value instanceof Date) {
			return DATE_TIME_FORMAT.format((Date) value);
		}
		return value.toString();
	}

	@ISEDropDownResourceMapping(resouceMapping = "DefaultDateFormat", valueFieldName = "")
	protected int dateFormat;
	
    public static final int PLACEHOLDER = -1;
	
    public static final int FORT_NONE = 0;
	
	public static final int FORT_YYYY_MM_DD = 1;
	
	public static final int FORT_YYYYMMDD = 2;
	
	public static final int FORT_YY_MM_DD = 3;
	
	public static final int FORT_YYMMDD = 4;

	public static final int FORT_YY_MM = 5;
	
	public static final int FORT_YYMM = 6;
	
	public static final int FORT_YY = 7;

	public int getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(int dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public static List<SimpleDateFormat> getAllDateFormat(){
		List<SimpleDateFormat> resultList = new ArrayList<>();
		resultList.add(DATE_MIN_FORMAT);
		resultList.add(DATE_HOUR_FORMAT);
		resultList.add(DATE_FORMAT);
		resultList.add(MONTH_FORMAT);
		resultList.add(YEAR_FORMAT);
		resultList.add(DATE_TIME_FORMAT);
		resultList.add(DATE_HOUR_FORMAT);
		resultList.add(HOUR_MIN_FORMAT);
		return resultList;
	}

}
