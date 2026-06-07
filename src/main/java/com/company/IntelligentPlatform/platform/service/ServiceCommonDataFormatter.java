package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.ServiceEntityDoubleHelper;

/**
 * Formatter class for format some common data
 * 
 * @author Zhang,Hang
 * 
 */
public class ServiceCommonDataFormatter {
	
	public static String formatPercentageValue(double rawValue){
		double percentageValue = ServiceEntityDoubleHelper.trancateDoubleScale2(rawValue * 100);
		return percentageValue + "%";
	}

}
