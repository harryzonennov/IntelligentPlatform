package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;

@Service
public class DefaultDateFormatSwitchProxy {
	
    public static final int SWITCH_ON = 1;
	
    public static final int SWITCH_OFF = 2;
    
    public static final int SWITCH_INITIAL = 3;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	public Map<Integer, String> getFormatMap() throws ServiceEntityInstallationException{
		Map<Integer, String> dateFormatMap = serviceDropdownListHelper
				.getUIDropDownMap(DefaultDateFormatConstant.class,
						"dateFormat");
		return dateFormatMap;
	}
	
	
	public String getFormatLabel(int key) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getFormatMap();
		return switchMap.get(key);		
	}

}
