package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.OrganizationAttachment;

@Component
public class OrganizationServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = Organization.NODENAME, nodeInstId = Organization.SENAME, convToUIMethod = OrganizationManager.METHOD_ConvOrganizationToUI, convUIToMethod = OrganizationManager.METHOD_ConvUIToOrganization)
	protected OrganizationUIModel organizationUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = OrganizationAttachment.NODENAME, nodeInstId = OrganizationAttachment.NODENAME)
	protected List<OrganizationAttachmentUIModel> organizationAttachmentUIModelList = new ArrayList<>();

	protected List<LogonUserOrgServiceUIModel> logonUserOrganizationUIModelList = new ArrayList<>();


	public OrganizationUIModel getOrganizationUIModel() {
		return this.organizationUIModel;
	}

	public void setOrganizationUIModel(OrganizationUIModel organizationUIModel) {
		this.organizationUIModel = organizationUIModel;
	}

	public List<OrganizationAttachmentUIModel> getOrganizationAttachmentUIModelList() {
		return organizationAttachmentUIModelList;
	}

	public void setOrganizationAttachmentUIModelList(
			List<OrganizationAttachmentUIModel> organizationAttachmentUIModelList) {
		this.organizationAttachmentUIModelList = organizationAttachmentUIModelList;
	}

	public List<LogonUserOrgServiceUIModel> getLogonUserOrganizationUIModelList() {
		return logonUserOrganizationUIModelList;
	}

	public void setLogonUserOrganizationUIModelList(List<LogonUserOrgServiceUIModel> logonUserOrganizationUIModelList) {
		this.logonUserOrganizationUIModelList = logonUserOrganizationUIModelList;
	}
}
