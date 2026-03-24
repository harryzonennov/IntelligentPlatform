package com.company.IntelligentPlatform.common.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// TODO-LEGACY: import platform.foundation.Controller.Basic.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

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
