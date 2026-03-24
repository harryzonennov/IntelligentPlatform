package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.OrganizationAttachment;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class OrganizationServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = Organization.NODENAME, nodeInstId = Organization.SENAME)
	protected Organization organization;

	@IServiceModuleFieldConfig(nodeName = OrganizationAttachment.NODENAME, nodeInstId = OrganizationAttachment.NODENAME)
	protected List<ServiceEntityNode> organizationAttachmentList = new ArrayList<>();

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<ServiceEntityNode> getOrganizationAttachmentList() {
		return organizationAttachmentList;
	}

	public void setOrganizationAttachmentList(
			List<ServiceEntityNode> organizationAttachmentList) {
		this.organizationAttachmentList = organizationAttachmentList;
	}

}
