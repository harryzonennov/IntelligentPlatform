package com.company.IntelligentPlatform.common.service;

import java.util.Comparator;

import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;

public class CalendarTempItemPeriodTypeCompare implements Comparator<CalendarTemplateItem>{

	@Override
	public int compare(CalendarTemplateItem item1, CalendarTemplateItem item2) {
		int periodType1 = item1.getPeriodType();
		int periodType2 = item2.getPeriodType();
		if(periodType1 == periodType2){
			return 0; 
		}
		return periodType1 - periodType2;
	}

	
}
