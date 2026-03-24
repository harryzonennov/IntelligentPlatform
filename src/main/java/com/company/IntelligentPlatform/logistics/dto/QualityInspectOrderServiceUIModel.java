package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class QualityInspectOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectMatItem.NODENAME, nodeInstId = QualityInspectMatItem.NODENAME)
	protected List<QualityInspectMatItemServiceUIModel> qualityInspectMatItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = QualityInsOrderAttachment.NODENAME, nodeInstId = QualityInsOrderAttachment.NODENAME)
	protected List<QualityInsOrderAttachmentUIModel> qualityInsOrderAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = QualityInsOrderActionNode.NODENAME, nodeInstId =
			QualityInsOrderActionNode.NODEINST_ACTION_STRATTEST)
	protected QualityInsOrderActionNodeUIModel inProcessBy;

	@IServiceUIModuleFieldConfig(nodeName = QualityInsOrderActionNode.NODENAME, nodeInstId =
			QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE)
	protected QualityInsOrderActionNodeUIModel testDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = QualityInsOrderActionNode.NODENAME, nodeInstId =
			QualityInsOrderActionNode.NODEINST_ACTION_PROCESS_DONE)
	protected QualityInsOrderActionNodeUIModel processDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectOrder.NODENAME, nodeInstId = QualityInspectOrder.SENAME)
	protected QualityInspectOrderUIModel qualityInspectOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId = QualityInspectOrderParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected QualityInspectOrderPartyUIModel corporateSupplierParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId = QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG)
	protected QualityInspectOrderPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId =
			QualityInspectOrderParty.PARTY_NODEINST_PROD_ORG)
	protected QualityInspectOrderPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId =
			QualityInspectOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected QualityInspectOrderPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId =
			QualityInspectOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected QualityInspectOrderPartyUIModel salesOrganizationParty;

	public List<QualityInspectMatItemServiceUIModel> getQualityInspectMatItemUIModelList() {
		return this.qualityInspectMatItemUIModelList;
	}

	public void setQualityInspectMatItemUIModelList(
			List<QualityInspectMatItemServiceUIModel> qualityInspectMatItemUIModelList) {
		this.qualityInspectMatItemUIModelList = qualityInspectMatItemUIModelList;
	}

	public List<QualityInsOrderAttachmentUIModel> getQualityInsOrderAttachmentUIModelList() {
		return this.qualityInsOrderAttachmentUIModelList;
	}

	public void setQualityInsOrderAttachmentUIModelList(
			List<QualityInsOrderAttachmentUIModel> qualityInsOrderAttachmentUIModelList) {
		this.qualityInsOrderAttachmentUIModelList = qualityInsOrderAttachmentUIModelList;
	}

	public QualityInspectOrderUIModel getQualityInspectOrderUIModel() {
		return this.qualityInspectOrderUIModel;
	}

	public void setQualityInspectOrderUIModel(
			QualityInspectOrderUIModel qualityInspectOrderUIModel) {
		this.qualityInspectOrderUIModel = qualityInspectOrderUIModel;
	}

	public QualityInsOrderActionNodeUIModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(QualityInsOrderActionNodeUIModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public QualityInsOrderActionNodeUIModel getTestDoneBy() {
		return testDoneBy;
	}

	public void setTestDoneBy(QualityInsOrderActionNodeUIModel testDoneBy) {
		this.testDoneBy = testDoneBy;
	}

	public QualityInsOrderActionNodeUIModel getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(QualityInsOrderActionNodeUIModel processDoneBy) {
		this.processDoneBy = processDoneBy;
	}

	public QualityInspectOrderPartyUIModel getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(QualityInspectOrderPartyUIModel corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public QualityInspectOrderPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(QualityInspectOrderPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public QualityInspectOrderPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(QualityInspectOrderPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public QualityInspectOrderPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(QualityInspectOrderPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public QualityInspectOrderPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(QualityInspectOrderPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
