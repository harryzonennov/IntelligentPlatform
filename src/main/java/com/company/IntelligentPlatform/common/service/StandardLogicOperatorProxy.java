package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

@Service
public class StandardLogicOperatorProxy {

	public static final int OPERATOR_AND = 1;

	public static final int OPERATOR_OR = 2;

	protected Map<Integer, String> logicOperatorMap;

	protected Map<String, Map<Integer, String>> logicOperatorMapLan;

	protected Map<String, Map<Integer, String>> logicOperatorExpressMapLan;

	public static final String PROPERTIES_RESOURCE = "StandardLogicOperator";

	public static final String PROPERTIES_EXPRESS_RESOURCE = "StandardLogicOperatorExpress";

	public Map<Integer, String> getLogicOperatorMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.logicOperatorMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
	}

	public Map<Integer, String> getLogicOperatorExpressMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.logicOperatorExpressMapLan, this.getClass().getResource("").getPath() + PROPERTIES_EXPRESS_RESOURCE);
	}



}
