package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class OutboundDeliveryServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = OutboundDelivery.NODENAME, nodeInstId = OutboundDelivery.SENAME)
	protected OutboundDeliveryUIModel outboundDeliveryUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId =
			OutboundDeliveryActionNode.NODEINST_ACTION_APPROVE)
	protected OutboundDeliveryActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId =
			OutboundDeliveryActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected OutboundDeliveryActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId = OutboundDeliveryActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected OutboundDeliveryActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryActionNode.NODENAME, nodeInstId =
			OutboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected OutboundDeliveryActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_PUR_ORG)
	protected OutboundDeliveryPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected OutboundDeliveryPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected OutboundDeliveryPartyUIModel soldToCustomerUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_SOLD_ORG)
	protected OutboundDeliveryPartyUIModel soldFromOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryParty.NODENAME, nodeInstId = OutboundDeliveryParty.PARTY_NODEINST_PROD_ORG)
	protected OutboundDeliveryPartyUIModel productionOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = OutboundItem.NODENAME, nodeInstId = OutboundItem.NODENAME)
	protected List<OutboundItemServiceUIModel> outboundItemUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = OutboundDeliveryAttachment.NODENAME, nodeInstId = OutboundDeliveryAttachment.NODENAME)
	protected List<OutboundDeliveryAttachmentUIModel> outboundDeliveryAttachmentUIModelList = new ArrayList<>();

	public OutboundDeliveryActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(OutboundDeliveryActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public OutboundDeliveryActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(OutboundDeliveryActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public OutboundDeliveryActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(OutboundDeliveryActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public OutboundDeliveryActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(OutboundDeliveryActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public List<OutboundItemServiceUIModel> getOutboundItemUIModelList() {
		return outboundItemUIModelList;
	}

	public void setOutboundItemUIModelList(List<OutboundItemServiceUIModel> outboundItemUIModelList) {
		this.outboundItemUIModelList = outboundItemUIModelList;
	}

	public OutboundDeliveryUIModel getOutboundDeliveryUIModel() {
		return this.outboundDeliveryUIModel;
	}

	public void setOutboundDeliveryUIModel(
			OutboundDeliveryUIModel outboundDeliveryUIModel) {
		this.outboundDeliveryUIModel = outboundDeliveryUIModel;
	}

	public List<OutboundDeliveryAttachmentUIModel> getOutboundDeliveryAttachmentUIModelList() {
		return outboundDeliveryAttachmentUIModelList;
	}

	public void setOutboundDeliveryAttachmentUIModelList(
			List<OutboundDeliveryAttachmentUIModel> outboundDeliveryAttachmentUIModelList) {
		this.outboundDeliveryAttachmentUIModelList = outboundDeliveryAttachmentUIModelList;
	}

	public OutboundDeliveryPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(OutboundDeliveryPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public OutboundDeliveryPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(OutboundDeliveryPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public OutboundDeliveryPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(OutboundDeliveryPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public OutboundDeliveryPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(OutboundDeliveryPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}

	public OutboundDeliveryPartyUIModel getProductionOrgUIModel() {
		return productionOrgUIModel;
	}

	public void setProductionOrgUIModel(OutboundDeliveryPartyUIModel productionOrgUIModel) {
		this.productionOrgUIModel = productionOrgUIModel;
	}

}
