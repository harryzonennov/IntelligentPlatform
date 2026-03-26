package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class InboundDeliveryServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InboundDelivery.NODENAME, nodeInstId = InboundDelivery.SENAME)
	protected InboundDeliveryUIModel inboundDeliveryUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId =
			InboundDeliveryActionNode.NODEINST_ACTION_APPROVE)
	protected InboundDeliveryActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId =
			InboundDeliveryActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected InboundDeliveryActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId = InboundDeliveryActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected InboundDeliveryActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryActionNode.NODENAME, nodeInstId =
			InboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected InboundDeliveryActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected InboundDeliveryPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId = InboundDeliveryParty.PARTY_NODEINST_PUR_ORG)
	protected InboundDeliveryPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId =
			InboundDeliveryParty.PARTY_NODEINST_PROD_ORG)
	protected InboundDeliveryPartyUIModel productionOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId =
			InboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected InboundDeliveryPartyUIModel soldToCustomerUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryParty.NODENAME, nodeInstId =
			InboundDeliveryParty.PARTY_NODEINST_SOLD_ORG)
	protected InboundDeliveryPartyUIModel soldFromOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InboundItem.NODENAME, nodeInstId = InboundItem.NODENAME)
	protected List<InboundItemServiceUIModel> inboundItemUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = InboundDeliveryAttachment.NODENAME, nodeInstId = InboundDeliveryAttachment.NODENAME)
	protected List<InboundDeliveryAttachmentUIModel> inboundDeliveryAttachmentUIModelList = new ArrayList<>();

	public InboundDeliveryUIModel getInboundDeliveryUIModel() {
		return this.inboundDeliveryUIModel;
	}

	public void setInboundDeliveryUIModel(
			InboundDeliveryUIModel inboundDeliveryUIModel) {
		this.inboundDeliveryUIModel = inboundDeliveryUIModel;
	}

	public InboundDeliveryActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InboundDeliveryActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InboundDeliveryActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(InboundDeliveryActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public InboundDeliveryActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(InboundDeliveryActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public InboundDeliveryActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(InboundDeliveryActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public List<InboundItemServiceUIModel> getInboundItemUIModelList() {
		return inboundItemUIModelList;
	}

	public void setInboundItemUIModelList(List<InboundItemServiceUIModel> inboundItemUIModelList) {
		this.inboundItemUIModelList = inboundItemUIModelList;
	}

	public List<InboundDeliveryAttachmentUIModel> getInboundDeliveryAttachmentUIModelList() {
		return inboundDeliveryAttachmentUIModelList;
	}

	public void setInboundDeliveryAttachmentUIModelList(
			List<InboundDeliveryAttachmentUIModel> inboundDeliveryAttachmentUIModelList) {
		this.inboundDeliveryAttachmentUIModelList = inboundDeliveryAttachmentUIModelList;
	}

	public InboundDeliveryPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(InboundDeliveryPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public InboundDeliveryPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(InboundDeliveryPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public InboundDeliveryPartyUIModel getProductionOrgUIModel() {
		return productionOrgUIModel;
	}

	public void setProductionOrgUIModel(InboundDeliveryPartyUIModel productionOrgUIModel) {
		this.productionOrgUIModel = productionOrgUIModel;
	}

	public InboundDeliveryPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(InboundDeliveryPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public InboundDeliveryPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(InboundDeliveryPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}
}
