package com.company.IntelligentPlatform.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

@Service
public class ServiceDocParaConsumerValueModeProxy {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	public Map<Integer, String> getFullScopeMap()
			throws ServiceEntityInstallationException {
		Map<Integer, String> consumerValueModeMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceDocConfigureParaUIModel.class,
						"consumerValueMode");
		return consumerValueModeMap;
	}

	public Map<Integer, String> getStandardScopeMap()
			throws ServiceEntityInstallationException {
		Map<Integer, String> rawModeMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceDocConfigureParaUIModel.class,
						"consumerValueMode");
		Map<Integer, String> consumerValueModeMap = new HashMap<Integer, String>();
		consumerValueModeMap
				.put(ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE,
						rawModeMap
								.get(ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE));
		consumerValueModeMap
		.put(ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE,
				rawModeMap
						.get(ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE));
		return consumerValueModeMap;
	}
	
	public String getValueModeLabel(int key) throws ServiceEntityInstallationException{
		Map<Integer, String> rawModeMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceDocConfigureParaUIModel.class,
						"consumerValueMode");
		return rawModeMap.get(key);		
	}
}
