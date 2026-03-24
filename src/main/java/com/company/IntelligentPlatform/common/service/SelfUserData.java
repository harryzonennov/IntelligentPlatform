package com.company.IntelligentPlatform.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * This is System AO implementation class for the AO Need to access the data for
 * the own user.
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class SelfUserData extends SysAODeterminer {

	@Override
	public  boolean hitTarget(String logonUserUUID, ServiceEntityNode target, Organization homeOrganization,
			List<ServiceEntityNode> allOrganizationList)
			throws ServiceEntityConfigureException {
		if (logonUserUUID == null || logonUserUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			return false;
		}
		if (logonUserUUID.equals(target.getResEmployeeUUID())) {
			return true;
		}
		return false;
	}

	@Override
	public ServiceBasicKeyStructure genKeyValueStructure(String logonUserUUID, Organization homeOrganization,
														 List<ServiceEntityNode> allOrganizationList) {
		return new ServiceBasicKeyStructure(ServiceCollectionsHelper.asList(logonUserUUID),
				IServiceEntityNodeFieldConstant.RESPONSIBLE_EMPLOYEEUUID, ServiceBasicKeyStructure.OPERATOR_OR);
	}


}
