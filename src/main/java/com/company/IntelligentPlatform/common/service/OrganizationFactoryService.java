package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * [Organization] factory service CLASS responsible for service for detailed
 * organization speicified sub class
 * 
 * @author
 * @dateSun Nov 25 17:42:54 CST 2012
 * 
 *          This class is generated automatically by platform automation
 *          register tool
 */
@Service
@Transactional
public class OrganizationFactoryService {

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected OrganizationManager organizationManager;

	public OrganizationFactoryService() {

	}

	/**
	 * The Logic to get all the possible organization by logonUser reference
	 * node
	 *
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Organization getRefOrganization(ReferenceNode referenceNode)
			throws ServiceEntityConfigureException {
		if (referenceNode != null) {			
			ServiceEntityManager serviceEntityManager = serviceEntityManagerFactoryInContext
					.getManagerBySEName(referenceNode.getRefSEName());
			if(serviceEntityManager == null){
				// In case service entity manager is null, then set the default
				serviceEntityManager = organizationManager;				
			}
			Organization organization = (Organization) serviceEntityManager
					.getEntityNodeByKey(referenceNode.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							HostCompany.NODENAME, referenceNode.getClient(),
							null);
			return organization;

		}
		return null;
	}

	/**
	 * Get all possible Organization by UUID
	 * 
	 * @param uuid
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Organization getOrganizationByUUID(String uuid, String client)
			throws ServiceEntityConfigureException {
		Organization organization = (Organization) organizationManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						Organization.NODENAME, client, null);
		return organization;
	}
	
	/**
	 * Get all possible Organization by ID
	 * 
	 * @param id
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Organization getOrganizationById(String id, String client)
			throws ServiceEntityConfigureException {
		Organization organization = (Organization) organizationManager
				.getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
						Organization.NODENAME, client, null, true);
		return organization;
	}

	/**
	 * Get all possible organization by uuid
	 * 
	 * @param uuid
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Organization getAllOrganization(String uuid, String client,
			int organizationFunction) throws ServiceEntityConfigureException {
		ServiceEntityManager serviceEntityManager = null;
		if (organizationFunction == Organization.ORGFUNCTION_HOSTCOMPANY) {
			serviceEntityManager = serviceEntityManagerFactoryInContext
					.getManagerBySEName(IServiceModelConstants.HostCompany);
			Organization hostCompany = (Organization) serviceEntityManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityNode.NODENAME_ROOT, client, null);
			if (hostCompany != null) {
				return hostCompany;
			}
		}
		if (organizationFunction == Organization.ORGFUNCTION_TRANSSITE) {
			serviceEntityManager = serviceEntityManagerFactoryInContext
					.getManagerBySEName(IServiceModelConstants.TransSite);
			Organization transSite = (Organization) serviceEntityManager
					.getEntityNodeByKey(uuid,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityNode.NODENAME_ROOT, client, null);
			if (transSite != null) {
				return transSite;
			}
		}
		serviceEntityManager = serviceEntityManagerFactoryInContext
				.getManagerBySEName(IServiceModelConstants.Organization);
		Organization organization = (Organization) serviceEntityManager
				.getEntityNodeByKey(uuid, IServiceEntityNodeFieldConstant.UUID,
						ServiceEntityNode.NODENAME_ROOT, client, null);
		if (organization != null) {
			return organization;
		}
		return null;
	}

}