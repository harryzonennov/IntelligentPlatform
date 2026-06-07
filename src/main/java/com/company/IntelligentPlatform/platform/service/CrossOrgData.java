package com.company.IntelligentPlatform.platform.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO-DAO: import platform.foundation.DAO.LogonUserDAO;
import com.company.IntelligentPlatform.platform.service.LogonUserManager;
import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.Organization;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

/**
 * This is System AO implementation class for the AO Need to access the data for
 * the own user.
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class CrossOrgData extends SysAODeterminer {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	LogonUserDAO logonUserDAO;

	@Autowired
	LogonUserManager logonUserManager;

	@Override
	public boolean hitTarget(String logonUserUUID, ServiceEntityNode target, Organization homeOrganization,
			List<ServiceEntityNode> allOrganizationList)
			throws ServiceEntityConfigureException {
		return true;
	}

	@Override
	public ServiceBasicKeyStructure genKeyValueStructure(String logonUserUUID,  Organization homeOrganization,
														 List<ServiceEntityNode> allOrganizationList) {
		if (ServiceCollectionsHelper.checkNullList(allOrganizationList)) {
			return null;
		}
		//  full system access
//		// List<String> allOrgUUIDList =
//				allOrganizationList.stream().map(ServiceEntityNode::getUuid).collect(Collectors.toList());
		return new ServiceBasicKeyStructure(null,
				IServiceEntityNodeFieldConstant.RESORG_UUID, ServiceBasicKeyStructure.OPERATOR_OR);
	}

}
