package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO-DAO: import platform.foundation.DAO.LogonUserDAO;
import com.company.IntelligentPlatform.common.service.LogonUserManager;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * This is System AO implementation class for the AO Need to access the data for
 * the own user.
 * 
 * @author Zhang,Hang
 * 
 */
@Service
public class LowerOrgData extends SysAODeterminer {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected LogonUserDAO logonUserDAO;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Override
	public boolean hitTarget(String logonUserUUID, ServiceEntityNode target, Organization homeOrganization,
			List<ServiceEntityNode> allOrganizationList)
			throws ServiceEntityConfigureException {
		if (target.getResOrgUUID() == null){
			return false;
		}
		if(allOrganizationList == null || allOrganizationList.size() == 0){
			return false;
		}
		for(ServiceEntityNode seNode:allOrganizationList){
			if(target.getResOrgUUID().equals(seNode.getUuid())){
				return true;
			}
		}
		return false;
	}

	@Override
	public ServiceBasicKeyStructure genKeyValueStructure(String logonUserUUID, Organization homeOrganization,
														 List<ServiceEntityNode> allOrganizationList) {
		if( ServiceCollectionsHelper.checkNullList( allOrganizationList ) ){
			return null;
		}
		List<String> allOrgUUIDList = allOrganizationList.stream().map(ServiceEntityNode::getUuid).collect(Collectors.toList());
		return new ServiceBasicKeyStructure(allOrgUUIDList,
				IServiceEntityNodeFieldConstant.RESORG_UUID, ServiceBasicKeyStructure.OPERATOR_OR);
	}


}
