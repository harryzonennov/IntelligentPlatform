package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceCalendarHelper;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * 
 * @author Zhang,hang
 *
 */
@Service
public class CurrentTimeDataProvider extends ServiceSimpleDataProviderTemplate{

	public static final int OFFSETDIRECT_FORWARD = 1;

	public static final int OFFSETDIRECT_BACKWARD = 2;
	
	@Autowired
	protected ServiceCalendarHelper serviceCalendarHelper;

	@Override
	public Object getStandardData(LogonInfo logonInfo) {
		return new Date();
	}

	@Override
	public Map<Integer, String> getOffsetDirectionDropdown() {
		Map<Integer, String> offsetDirectionMap = new HashMap<Integer, String>();
		offsetDirectionMap.put(OFFSETDIRECT_FORWARD, "往后");
		offsetDirectionMap.put(OFFSETDIRECT_BACKWARD, "往前");
		return offsetDirectionMap;

	}

	@Override
	public Map<Integer, String> getOffsetUnitDropdown() throws ServiceSimpleDataProviderException {
		try {
			return serviceCalendarHelper.getCalendarUnitMap();
		} catch (ServiceEntityInstallationException | IOException e) {
			throw new ServiceSimpleDataProviderException(ServiceSimpleDataProviderException.TYPE_SYSTEM_WRONG);
		}
	}

	@Override
	public Map<Integer, String> getOffsetDirectionTemplate() throws ServiceSimpleDataProviderException {
		Map<Integer, String> offsetDirectionMap = new HashMap<Integer, String>();
		offsetDirectionMap.put(OFFSETDIRECT_FORWARD, "md md-fast-forward content-lightblue");
		offsetDirectionMap.put(OFFSETDIRECT_BACKWARD, "md md-fast-rewind content-lightblue");
		return offsetDirectionMap;
	}

	@Override
	public Map<Integer, String> getOffsetUnitTemplate() throws ServiceSimpleDataProviderException {
		Map<Integer, String> offsetUnitMap = new HashMap<Integer, String>();
		offsetUnitMap.put(ServiceCalendarHelper.UNIT_DAY, "nmd nmd-brightness-4 content-darkblue");
		offsetUnitMap.put(ServiceCalendarHelper.UNIT_HOUR, "nmd nmd-watch content-orange");
		offsetUnitMap.put(ServiceCalendarHelper.UNIT_WEEK, "nmd nmd-filter-7 content-lightblue");
		offsetUnitMap.put(ServiceCalendarHelper.UNIT_MONTH, "nmd nmd-event content-green");
		offsetUnitMap.put(ServiceCalendarHelper.UNIT_YEAR, "nmd nmd-date-range content-darkblue");
		return offsetUnitMap;
	}

	@Override
	public String getDataProviderComment() {		
		return "数据源-当前时间";
	}

	@Override
	public String getStandardDataToString(Object data) throws ServiceSimpleDataProviderException {
		Date curDate = (Date) data;
		return DefaultDateFormatConstant.DATE_MIN_FORMAT.format(curDate);
	}

	@Override
	public String getResultDataToString(Object data)
			throws ServiceSimpleDataProviderException {
		Date curDate = (Date) data;
		return DefaultDateFormatConstant.DATE_MIN_FORMAT.format(curDate);		
	}

	@Override
	public Object getResultData(SimpleDataOffsetUnion simpleDataOffsetUnion, LogonInfo logonInfo) throws ServiceSimpleDataProviderException {
		Date curDate = (Date) getStandardData(logonInfo);
		Calendar curCalendar = Calendar.getInstance();
		curCalendar.setTime(curDate);
		int offsetDirection = simpleDataOffsetUnion.getOffsetDirection();
		int offsetUnit = 0;
		if (!ServiceEntityStringHelper.checkNullString(simpleDataOffsetUnion.getOffsetUnit().toString())){
			offsetUnit = Integer.parseInt(simpleDataOffsetUnion.getOffsetUnit().toString()) ;
		}
		double offsetValue = ServiceEntityDoubleHelper.trancateDouble(simpleDataOffsetUnion.getOffsetValue(), 0);
		int offsetValueInt = (int)offsetValue;
		if(offsetUnit == ServiceCalendarHelper.UNIT_MONTH){
			// Incase offset unit is month
			if(offsetDirection == ServiceSimpleDataProviderTemplate.OFFSETDIRECT_INCREASE){
				curCalendar.add(Calendar.MONTH, offsetValueInt);
			} else {
				curCalendar.add(Calendar.MONTH, -offsetValueInt);
			}
		}
		if(offsetUnit == ServiceCalendarHelper.UNIT_WEEK){
			// Incase offset unit is month
			if(offsetDirection == ServiceSimpleDataProviderTemplate.OFFSETDIRECT_INCREASE){
				curCalendar.add(Calendar.WEEK_OF_YEAR, offsetValueInt);
			} else {
				curCalendar.add(Calendar.WEEK_OF_YEAR, -offsetValueInt);
			}
		}
		if(offsetUnit == ServiceCalendarHelper.UNIT_DAY){
			// Incase offset unit is month
			if(offsetDirection == ServiceSimpleDataProviderTemplate.OFFSETDIRECT_INCREASE){
				curCalendar.add(Calendar.DATE, offsetValueInt);
			} else {
				curCalendar.add(Calendar.DATE, -offsetValueInt);
			}
		}
		if(offsetUnit == ServiceCalendarHelper.UNIT_HOUR){
			// Incase offset unit is month
			if(offsetDirection == ServiceSimpleDataProviderTemplate.OFFSETDIRECT_INCREASE){
				curCalendar.add(Calendar.HOUR, offsetValueInt);
			} else {
				curCalendar.add(Calendar.HOUR, -offsetValueInt);
			}
		}
		return curCalendar.getTime();
	}

}
