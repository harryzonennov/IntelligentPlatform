package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.ISystemConfigureSwitch;

@Service
public class InboundDeliveryApproveProcess implements ISystemConfigureSwitch{
	
    public static final int AUTO_APPROVE = 2;
	
	public static final int MANUAL_APPROVE = 3;
	
	public static final String ELEMENT_ID = "InboundApproveProcess";

	/**
	 * [Pay attention] key value should not use value 1, starting from 2
	 * @return
	 */
	@Override
	public Map<Integer, String> getSwitchMap() {
		Map<Integer, String> switchMap = new HashMap<Integer, String>();
		switchMap.put(AUTO_APPROVE, "Auto approve");
		switchMap.put(MANUAL_APPROVE, "Manual approve");
		return switchMap;
	}

}
