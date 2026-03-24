package com.company.IntelligentPlatform.common.dto;

import java.util.Map;


public interface ISystemConfigureSwitch {
	
	/**
	 * [Pay attention] key value should not use value 1, starting from 2
	 * @return
	 */
    Map<Integer, String> getSwitchMap();

}
