package com.company.IntelligentPlatform.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Parent class of AO Determiner
 * @author Zhang,Hang
 *
 */
@Service
public abstract class SysAODeterminer {

	/**
	 * Should implement in sub class
	 * @param logonUserUUID
	 * @param target
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public abstract boolean hitTarget(String logonUserUUID,
			ServiceEntityNode target, Organization homeOrganization, List<ServiceEntityNode> allOrganizationList) throws ServiceEntityConfigureException;
	
	public abstract ServiceBasicKeyStructure genKeyValueStructure(String logonUserUUID,
																  Organization homeOrganization, List<ServiceEntityNode> allOrganizationList);

}
