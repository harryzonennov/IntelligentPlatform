package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.*;

@Component
public class CorporateSupplierServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomer.NODENAME, nodeInstId = CorporateCustomer.SENAME)
	protected CorporateSupplierUIModel corporateSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomerAttachment.NODENAME, nodeInstId = CorporateCustomerAttachment.NODENAME)
	protected List<CorporateCustomerAttachmentUIModel> corporateCustomerAttachmentUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = CorporateContactPerson.NODENAME, nodeInstId = CorporateContactPerson.NODENAME, 
			convToUIMethod = CorporateCustomerManager.METHOD_ConvCorporateContactPersonToUI, 
			convUIToMethod = CorporateCustomerManager.METHOD_ConvUIToCorporateContactPerson)
	protected List<CorporateContactPersonServiceUIModel> corporateContactPersonUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId = CorporateCustomerActionNode.NODEINST_ACTION_ACTIVE)
	protected CorporateCustomerActionNodeUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId =
			CorporateCustomerActionNode.NODEINST_ACTION_APPROVE)
	protected CorporateCustomerActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId =
			CorporateCustomerActionNode.NODEINST_ACTION_SUBMIT)
	protected CorporateCustomerActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId =
			CorporateCustomerActionNode.NODEINST_ACTION_REINIT)
	protected CorporateCustomerActionNodeUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = CorporateCustomerActionNode.NODENAME, nodeInstId =
			CorporateCustomerActionNode.NODEINST_ACTION_ARCHIVE)
	protected CorporateCustomerActionNodeUIModel archivedBy;


	public CorporateSupplierUIModel getCorporateSupplierUIModel() {
		return corporateSupplierUIModel;
	}

	public void setCorporateSupplierUIModel(
			CorporateSupplierUIModel corporateSupplierUIModel) {
		this.corporateSupplierUIModel = corporateSupplierUIModel;
	}

	public List<CorporateContactPersonServiceUIModel> getCorporateContactPersonUIModelList() {
		return this.corporateContactPersonUIModelList;
	}

	public void setCorporateContactPersonUIModelList(
			List<CorporateContactPersonServiceUIModel> corporateContactPersonUIModelList) {
		this.corporateContactPersonUIModelList = corporateContactPersonUIModelList;
	}

	public List<CorporateCustomerAttachmentUIModel> getCorporateCustomerAttachmentUIModelList() {
		return corporateCustomerAttachmentUIModelList;
	}

	public void setCorporateCustomerAttachmentUIModelList(
			List<CorporateCustomerAttachmentUIModel> corporateCustomerAttachmentUIModelList) {
		this.corporateCustomerAttachmentUIModelList = corporateCustomerAttachmentUIModelList;
	}

	public CorporateCustomerActionNodeUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(CorporateCustomerActionNodeUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public CorporateCustomerActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(CorporateCustomerActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}


	public CorporateCustomerActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(CorporateCustomerActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public CorporateCustomerActionNodeUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(CorporateCustomerActionNodeUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public CorporateCustomerActionNodeUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(CorporateCustomerActionNodeUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}
}
