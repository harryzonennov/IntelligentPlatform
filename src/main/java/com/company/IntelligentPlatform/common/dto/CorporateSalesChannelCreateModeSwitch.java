package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;

@Service
public class CorporateSalesChannelCreateModeSwitch implements ISystemConfigureSwitch{
	
	public static final int STANDLONE = 1;
	
	public static final int WITH_DEF_WAREHOUSE = 2;	
	

	/**
	 * [Pay attention] key value should not use value 1, starting from 2
	 * @return
	 */
	@Override
	public Map<Integer, String> getSwitchMap() {
		Map<Integer, String> switchMap = new HashMap<Integer, String>();
		switchMap.put(STANDLONE, "Standalone");
		switchMap.put(WITH_DEF_WAREHOUSE, "With One Def warehouse");		
		return switchMap;
	}

}
