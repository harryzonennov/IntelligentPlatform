package com.company.IntelligentPlatform.platform.controller;

import java.util.Map;

import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

public interface IFinanceControllerResource {

	Map<Integer, String> getFinAccObjectMap();
	
	Map<Integer, String> getProcessCodeMap();

	ServiceEntityNode getFinAccObjectByKey(String baseUUID, String client, int key)
			throws ServiceEntityConfigureException;

	ServiceEntityNode getDocument(String baseUUID, String client)
			throws ServiceEntityConfigureException;

	int getDocumentType() throws ServiceEntityConfigureException;

}
