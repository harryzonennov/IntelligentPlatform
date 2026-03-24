package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinPlatform OrganizationManager (ServiceEntityManager subclass)
 */
@Service
@Transactional
public class OrganizationService extends ServiceEntityService {

	@Autowired
	protected OrganizationRepository organizationRepository;

	public Organization create(Organization org, String userUUID, String orgUUID) {
		return insertSENode(organizationRepository, org, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public Organization getByUuid(String uuid) {
		return getEntityNodeByUUID(organizationRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<Organization> getByClient(String client) {
		return organizationRepository.findByClient(client);
	}

	@Transactional(readOnly = true)
	public List<Organization> getChildren(String parentOrganizationUUID) {
		return organizationRepository.findByParentOrganizationUUID(parentOrganizationUUID);
	}

	public Organization update(Organization org, String userUUID, String orgUUID) {
		return updateSENode(organizationRepository, org, userUUID, orgUUID);
	}

	public void delete(String uuid) {
		deleteSENode(organizationRepository, uuid);
	}

}
