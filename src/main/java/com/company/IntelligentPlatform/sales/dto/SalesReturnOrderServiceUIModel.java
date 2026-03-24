package com.company.IntelligentPlatform.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.service.SalesReturnOrderManager;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class SalesReturnOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrder.NODENAME, nodeInstId = SalesReturnOrder.SENAME, convToUIMethod = SalesReturnOrderManager.METHOD_ConvSalesReturnOrderToUI, convUIToMethod = SalesReturnOrderManager.METHOD_ConvUIToSalesReturnOrder)
	protected SalesReturnOrderUIModel salesReturnOrderUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderParty.NODENAME, nodeInstId = SalesReturnOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected SalesReturnOrderPartyUIModel soldToCustomerUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderParty.NODENAME, nodeInstId = SalesReturnOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected SalesReturnOrderPartyUIModel soldFromOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME, nodeInstId =
			SalesReturnOrderActionNode.NODEINST_ACTION_APPROVE)
	protected SalesReturnOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME, nodeInstId = SalesReturnOrderActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected SalesReturnOrderActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME, nodeInstId =
			SalesReturnOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected SalesReturnOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderActionNode.NODENAME, nodeInstId =
			SalesReturnOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected SalesReturnOrderActionNodeUIModel rejectApprovedBy;

	// update this node manually
	protected SalesContractUIModel salesContractUIModel;

	// update this node manually
	protected SalesContractActionNodeUIModel salesDeliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnMaterialItem.NODENAME, nodeInstId = SalesReturnMaterialItem.NODENAME)
	protected List<SalesReturnMaterialItemServiceUIModel> salesReturnMaterialItemUIModelList = new ArrayList<SalesReturnMaterialItemServiceUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnOrderAttachment.NODENAME, nodeInstId = SalesReturnOrderAttachment.NODENAME)
	protected List<SalesReturnOrderAttachmentUIModel> salesReturnOrderAttachmentUIModelList = new ArrayList<SalesReturnOrderAttachmentUIModel>();

	public List<SalesReturnMaterialItemServiceUIModel> getSalesReturnMaterialItemUIModelList() {
		return salesReturnMaterialItemUIModelList;
	}

	public void setSalesReturnMaterialItemUIModelList(
			List<SalesReturnMaterialItemServiceUIModel> salesReturnMaterialItemUIModelList) {
		this.salesReturnMaterialItemUIModelList = salesReturnMaterialItemUIModelList;
	}

	public List<SalesReturnOrderAttachmentUIModel> getSalesReturnOrderAttachmentUIModelList() {
		return salesReturnOrderAttachmentUIModelList;
	}

	public void setSalesReturnOrderAttachmentUIModelList(
			List<SalesReturnOrderAttachmentUIModel> salesReturnOrderAttachmentUIModelList) {
		this.salesReturnOrderAttachmentUIModelList = salesReturnOrderAttachmentUIModelList;
	}

	public SalesReturnOrderUIModel getSalesReturnOrderUIModel() {
		return salesReturnOrderUIModel;
	}

	public void setSalesReturnOrderUIModel(
			SalesReturnOrderUIModel salesReturnOrderUIModel) {
		this.salesReturnOrderUIModel = salesReturnOrderUIModel;
	}

	public SalesReturnOrderPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(SalesReturnOrderPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public SalesReturnOrderPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(SalesReturnOrderPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}

	public SalesContractUIModel getSalesContractUIModel() {
		return salesContractUIModel;
	}

	public void setSalesContractUIModel(SalesContractUIModel salesContractUIModel) {
		this.salesContractUIModel = salesContractUIModel;
	}

	public SalesContractActionNodeUIModel getSalesDeliveryDoneBy() {
		return salesDeliveryDoneBy;
	}

	public void setSalesDeliveryDoneBy(SalesContractActionNodeUIModel salesDeliveryDoneBy) {
		this.salesDeliveryDoneBy = salesDeliveryDoneBy;
	}

	public SalesReturnOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(SalesReturnOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SalesReturnOrderActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(SalesReturnOrderActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public SalesReturnOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(SalesReturnOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public SalesReturnOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(SalesReturnOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}
}