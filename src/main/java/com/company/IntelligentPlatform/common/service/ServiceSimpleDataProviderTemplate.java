package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.LogonInfo;

import java.util.HashMap;
import java.util.Map;

public abstract class ServiceSimpleDataProviderTemplate {
	
	public static final int OFFSETDIRECT_INCREASE = 1;
	
	public static final int OFFSETDIRECT_DECREASE = 2;
	
	public abstract Object getStandardData(LogonInfo logonInfo) throws ServiceSimpleDataProviderException;
	
	public abstract String getStandardDataToString(Object data) throws ServiceSimpleDataProviderException;
	
	public abstract Object getResultData(SimpleDataOffsetUnion simpleDataOffsetUnion, LogonInfo logonInfo) throws ServiceSimpleDataProviderException;
	
	public abstract String getResultDataToString(Object data) throws ServiceSimpleDataProviderException;
	
	public abstract Map<?, ?> getOffsetDirectionDropdown()  throws ServiceSimpleDataProviderException;
	
	public abstract Map<?, ?> getOffsetUnitDropdown() throws ServiceSimpleDataProviderException;

	public abstract Map<?, ?> getOffsetDirectionTemplate()  throws ServiceSimpleDataProviderException;

	public abstract Map<?, ?> getOffsetUnitTemplate() throws ServiceSimpleDataProviderException;
	
	public abstract String getDataProviderComment();
	
	public Map<Integer, String> getDefaultOffsetDirectionDropdown() {
		Map<Integer, String> offsetDirectionMap = new HashMap<>();
		offsetDirectionMap.put(OFFSETDIRECT_INCREASE, "增加");
		offsetDirectionMap.put(OFFSETDIRECT_DECREASE, "减少");
		return offsetDirectionMap;
	}

}
