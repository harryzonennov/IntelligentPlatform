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
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

/**
 * This is System AO implementation class for the AO Need to access the data for
 * the own user.
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class ExternalOrgData extends SysAODeterminer {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	LogonUserDAO logonUserDAO;

	@Autowired
	LogonUserManager logonUserManager;

	@Override
	public  boolean hitTarget(String logonUserUUID, ServiceEntityNode target, Organization homeOrganization,
			List<ServiceEntityNode> allOrganizationList)
			throws ServiceEntityConfigureException {
		if (logonUserUUID == null || logonUserUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			return false;
		}
		if (homeOrganization.getUuid().equals(target.getResOrgUUID())) {
			return false;
		}
		for(ServiceEntityNode seNode:allOrganizationList) {
			if (target.getResOrgUUID() == null || target.getResOrgUUID().equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				return false;
			}
			if (target.getResOrgUUID().equals(seNode.getUuid())) {
				return false;
			}
		}
		// have to get all system organization list
		return false;
	}

	@Override
	public ServiceBasicKeyStructure genKeyValueStructure(String logonUserUUID,  Organization homeOrganization,
														 List<ServiceEntityNode> allOrganizationList) {
		if (ServiceCollectionsHelper.checkNullList(allOrganizationList)) {
			return null;
		}
		// full access
//		List<String> allOrgUUIDList = allOrganizationList.stream().map(ServiceEntityNode::getUuid).collect(Collectors.toList());
		return new ServiceBasicKeyStructure(null,
				IServiceEntityNodeFieldConstant.RESORG_UUID, ServiceBasicKeyStructure.OPERATOR_OR);
	}

}
