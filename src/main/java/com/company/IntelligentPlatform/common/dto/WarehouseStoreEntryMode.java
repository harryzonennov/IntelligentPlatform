package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;

@Service
public class WarehouseStoreEntryMode implements ISystemConfigureSwitch{
	
	public static final int DIRECT = 2;
	
	public static final int FROM_WAREHOUSELIST = 3;
	
	public static final String ELEMENT_ID = "BookingStandardItemChoose";

	/**
	 * [Pay attention] key value should not use value 1, starting from 2
	 * @return
	 */
	@Override
	public Map<Integer, String> getSwitchMap() {
		Map<Integer, String> switchMap = new HashMap<Integer, String>();
		switchMap.put(DIRECT, "Direct entry");
		switchMap.put(FROM_WAREHOUSELIST, "From warehouse list");
		return switchMap;
	}

}
