package com.company.IntelligentPlatform.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.controller.SalesContractAttachmentUIModel;
import com.company.IntelligentPlatform.sales.service.SalesContractManager;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class SalesContractServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SalesContract.NODENAME, nodeInstId = SalesContract.SENAME, convToUIMethod = SalesContractManager.METHOD_ConvSalesContractToUI, convUIToMethod = SalesContractManager.METHOD_ConvUIToSalesContract)
	protected SalesContractUIModel salesContractUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractParty.NODENAME, nodeInstId = SalesContractParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected SalesContractPartyUIModel soldToCustomerUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractParty.NODENAME, nodeInstId =
			SalesContractParty.PARTY_NODEINST_SOLD_ORG)
	protected SalesContractPartyUIModel soldFromOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_APPROVE)
	protected SalesContractActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId = SalesContractActionNode.NODEINST_ACTION_INPLAN)
	protected SalesContractActionNodeUIModel inPlanBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_SUBMIT)
	protected SalesContractActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected SalesContractActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractActionNode.NODENAME, nodeInstId =
			SalesContractActionNode.NODEINST_ACTION_PROCESS_DONE)
	protected SalesContractActionNodeUIModel processDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractMaterialItem.NODENAME, nodeInstId = SalesContractMaterialItem.NODENAME)
	protected List<SalesContractMaterialItemServiceUIModel> salesContractMaterialItemUIModelList = new ArrayList<SalesContractMaterialItemServiceUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = SalesContractAttachment.NODENAME, nodeInstId = SalesContractAttachment.NODENAME)
	protected List<SalesContractAttachmentUIModel> salesContractAttachmentUIModelList = new ArrayList<SalesContractAttachmentUIModel>();

	public SalesContractUIModel getSalesContractUIModel() {
		return this.salesContractUIModel;
	}

	public void setSalesContractUIModel(
			SalesContractUIModel salesContractUIModel) {
		this.salesContractUIModel = salesContractUIModel;
	}

	public SalesContractPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(SalesContractPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public SalesContractPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(SalesContractPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}

	public SalesContractActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(SalesContractActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SalesContractActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(SalesContractActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public SalesContractActionNodeUIModel getInPlanBy() {
		return inPlanBy;
	}

	public void setInPlanBy(SalesContractActionNodeUIModel inPlanBy) {
		this.inPlanBy = inPlanBy;
	}

	public SalesContractActionNodeUIModel getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(SalesContractActionNodeUIModel processDoneBy) {
		this.processDoneBy = processDoneBy;
	}

	public List<SalesContractMaterialItemServiceUIModel> getSalesContractMaterialItemUIModelList() {
		return this.salesContractMaterialItemUIModelList;
	}

	public void setSalesContractMaterialItemUIModelList(
			List<SalesContractMaterialItemServiceUIModel> salesContractMaterialItemUIModelList) {
		this.salesContractMaterialItemUIModelList = salesContractMaterialItemUIModelList;
	}

	public List<SalesContractAttachmentUIModel> getSalesContractAttachmentUIModelList() {
		return this.salesContractAttachmentUIModelList;
	}

	public void setSalesContractAttachmentUIModelList(
			List<SalesContractAttachmentUIModel> salesContractAttachmentUIModelList) {
		this.salesContractAttachmentUIModelList = salesContractAttachmentUIModelList;
	}

	public SalesContractActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(SalesContractActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}
}