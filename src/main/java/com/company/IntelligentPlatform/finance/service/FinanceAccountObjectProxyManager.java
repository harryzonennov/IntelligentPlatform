package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

public class FinanceAccountObjectProxyManager extends ServiceEntityManager {
	
	public ServiceEntityNode getFinAccObject(String baseUUID) throws FinanceAccountObjectProxyException{
		// Should implemented in sub class.
		return null;
	}
	
	public void saveIndToAccountObject(
			IndividualCustomer individualCustomer, String logonUserUUID,
			String organizationUUID, String client)
			throws ServiceEntityConfigureException{
		// Should implemented in sub class.
	}
	
}
