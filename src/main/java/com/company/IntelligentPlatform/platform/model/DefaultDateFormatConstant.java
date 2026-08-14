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
	 * Automatically format any date/time value using the most appropriate pattern.
	 *
	 * <p>This is the preferred method to use at call sites where you don't want
	 * to think about which formatter to choose — the output pattern is decided
	 * by the actual runtime type of {@code value}:
	 * <pre>
	 *   LocalDate       → "2026-08-12"           (date only, no time)
	 *   LocalDateTime   → "2026-08-12 11:07"     (date + HH:mm)
	 *   Instant         → "2026-08-12 11:07"     (converted via system timezone)
	 *   java.util.Date  → "2026-08-12 11:07"
	 * </pre>
	 *
	 * <p>Use the explicit methods when you need a specific format regardless of type:
	 * {@link #formatDate} for date-only, {@link #formatDateMin} for HH:mm,
	 * {@link #formatDateTime} for HH:mm:ss.
	 */
	public static String formatAuto(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof LocalDate) {
			return ((LocalDate) value).format(DT_DATE);
		}
		if (value instanceof LocalDateTime) {
			return ((LocalDateTime) value).format(DT_DATE_MIN);
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
	 * Safely format any date/time value as a date-only string (yyyy-MM-dd).
	 *
	 * <p>Use this method when the field represents a <b>date only</b>, e.g.
	 * {@code signDate}, {@code planExecutionDate}, {@code requireExecutionDate}.
	 *
	 * <p>Accepted input types and example output:
	 * <pre>
	 *   LocalDate       "2026-08-11"
	 *   LocalDateTime   "2026-08-11"          (time part is dropped)
	 *   Instant         "2026-08-11"          (converted via system timezone)
	 *   java.util.Date  "2026-08-11"
	 * </pre>
	 *
	 * <p><b>Why these overloads exist:</b> Hibernate 6 maps a {@code java.util.Date}
	 * field that lacks {@code @Temporal} to {@link LocalDateTime} at runtime.
	 * Calling {@code SimpleDateFormat.format()} directly on a {@code LocalDateTime}
	 * throws {@code IllegalArgumentException: Cannot format given Object as a Date}.
	 * Always use these wrapper methods instead of {@code DATE_FORMAT.format(value)}.
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
	 * Safely format any date/time value as a date + hour:minute string (yyyy-MM-dd HH:mm).
	 *
	 * <p>Use this method when the field represents a <b>timestamp without seconds</b>,
	 * e.g. {@code executionTime}, {@code createdTime} displayed in a list or form.
	 * This is the most common choice for UI datetime fields in this codebase.
	 *
	 * <p>Accepted input types and example output:
	 * <pre>
	 *   LocalDateTime   "2026-08-11 11:07"
	 *   LocalDate       "2026-08-11"          (no time component — date only returned)
	 *   Instant         "2026-08-11 11:07"    (converted via system timezone)
	 *   java.util.Date  "2026-08-11 11:07"
	 * </pre>
	 *
	 * <p><b>Difference from {@link #formatDate}:</b> includes HH:mm in the output
	 * for LocalDateTime/Instant/Date inputs. For a LocalDate input both methods
	 * return the same date-only string.
	 *
	 * <p><b>Difference from {@link #formatDateTime}:</b> omits seconds (HH:mm vs HH:mm:ss).
	 * Use {@code formatDateMin} for general UI display; use {@code formatDateTime} only
	 * when second-level precision is meaningful (e.g. audit logs).
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
	 * Safely format any date/time value as a full timestamp string (yyyy-MM-dd HH:mm:ss).
	 *
	 * <p>Use this method when <b>second-level precision</b> is meaningful, e.g. audit logs
	 * or precise event timestamps. For general UI datetime display prefer
	 * {@link #formatDateMin} (HH:mm is usually sufficient and cleaner).
	 *
	 * <p>Accepted input types and example output:
	 * <pre>
	 *   LocalDateTime   "2026-08-11 11:07:27"
	 *   LocalDate       "2026-08-11"          (no time component — date only returned)
	 *   Instant         "2026-08-11 11:07:27" (converted via system timezone)
	 *   java.util.Date  "2026-08-11 11:07:27"
	 * </pre>
	 *
	 * <p><b>Difference from {@link #formatDateMin}:</b> includes seconds in the output
	 * (HH:mm:ss vs HH:mm) for LocalDateTime/Instant/Date inputs.
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
