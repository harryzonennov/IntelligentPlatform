package com.company.IntelligentPlatform.common.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ServiceEntityDoubleHelper {
	
	public static double trancateDouble(double rawValue, int scale){
		BigDecimal bigDecimalVolume = new BigDecimal(rawValue);
		double newValue = bigDecimalVolume.setScale(scale,
                RoundingMode.HALF_UP).doubleValue();
		return newValue;
	}
	
	public static double trancateDoubleScale2(double rawValue){
		BigDecimal bigDecimalVolume = new BigDecimal(rawValue);
		double newValue = bigDecimalVolume.setScale(2,
                RoundingMode.HALF_UP).doubleValue();
		return newValue;
	}
	
	public static double trancateDoubleScale4(double rawValue){
		BigDecimal bigDecimalVolume = new BigDecimal(rawValue);
		double newValue = bigDecimalVolume.setScale(4,
                RoundingMode.HALF_UP).doubleValue();
		return newValue;
	}
	
	/**
	 * Get the left decimal fraction from double value
	 * @param rawValue
	 * @return
	 */
	public static double getLeftDeciFraction(double rawValue){
		 int integerValue = (int)Math.floor(rawValue);
		 return rawValue - integerValue;
	}
	
	/**
	 * Get the left Integer value from double value
	 * @param rawValue
	 * @return
	 */
	public static int getLeftInteger(double rawValue){
		 int integerValue = (int)Math.floor(rawValue);
		 return integerValue;
	}

}
