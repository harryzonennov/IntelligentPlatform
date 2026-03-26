package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class CorporateCustomerServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = CorporateCustomer.NODENAME, nodeInstId = CorporateCustomer.SENAME)
	protected CorporateCustomer corporateCustomer;

	@IServiceModuleFieldConfig(nodeName = CorporateContactPerson.NODENAME, nodeInstId = CorporateContactPerson.NODENAME)
	protected List<CorporateContactPersonServiceModel> corporateContactPersonList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = CorporateCustomerAttachment.NODENAME , nodeInstId = CorporateCustomerAttachment.NODENAME)
	protected List<ServiceEntityNode> corporateCustomerAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId = CorporateCustomerActionNode.NODEINST_ACTION_ACTIVE)
	protected CorporateCustomerActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId = CorporateCustomerActionNode.NODEINST_ACTION_APPROVE)
	protected CorporateCustomerActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId = CorporateCustomerActionNode.NODEINST_ACTION_SUBMIT)
	protected CorporateCustomerActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId =
			CorporateCustomerActionNode.NODEINST_ACTION_REINIT)
	protected CorporateCustomerActionNode reInitBy;

	@IServiceModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId =
			CorporateCustomerActionNode.NODEINST_ACTION_ARCHIVE)
	protected CorporateCustomerActionNode archivedBy;

	public List<CorporateContactPersonServiceModel> getCorporateContactPersonList() {
		return this.corporateContactPersonList;
	}

	public void setCorporateContactPersonList(
			List<CorporateContactPersonServiceModel> corporateContactPersonList) {
		this.corporateContactPersonList = corporateContactPersonList;
	}

	public CorporateCustomer getCorporateCustomer() {
		return this.corporateCustomer;
	}

	public void setCorporateCustomer(CorporateCustomer corporateCustomer) {
		this.corporateCustomer = corporateCustomer;
	}

	public List<ServiceEntityNode> getCorporateCustomerAttachmentList() {
		return corporateCustomerAttachmentList;
	}

	public void setCorporateCustomerAttachmentList(
			List<ServiceEntityNode> corporateCustomerAttachmentList) {
		this.corporateCustomerAttachmentList = corporateCustomerAttachmentList;
	}

	public CorporateCustomerActionNode getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(CorporateCustomerActionNode activeBy) {
		this.activeBy = activeBy;
	}

	public CorporateCustomerActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(CorporateCustomerActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public CorporateCustomerActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(CorporateCustomerActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public CorporateCustomerActionNode getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(CorporateCustomerActionNode reInitBy) {
		this.reInitBy = reInitBy;
	}

	public CorporateCustomerActionNode getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(CorporateCustomerActionNode archivedBy) {
		this.archivedBy = archivedBy;
	}
}
