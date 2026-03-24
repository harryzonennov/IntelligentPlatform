package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class QualityInspectOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = QualityInspectOrder.NODENAME, nodeInstId = QualityInspectOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected QualityInspectOrder qualityInspectOrder;

	@IServiceModuleFieldConfig(nodeName = QualityInsOrderActionNode.NODENAME, nodeInstId =
			QualityInsOrderActionNode.NODEINST_ACTION_STRATTEST, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected QualityInsOrderActionNode inProcessBy;

	@IServiceModuleFieldConfig(nodeName = QualityInsOrderActionNode.NODENAME, nodeInstId =
			QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected QualityInsOrderActionNode testDoneBy;

	@IServiceModuleFieldConfig(nodeName = QualityInsOrderActionNode.NODENAME, nodeInstId =
			QualityInsOrderActionNode.NODEINST_ACTION_PROCESS_DONE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected QualityInsOrderActionNode processDoneBy;

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItem.NODENAME, nodeInstId = QualityInspectMatItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<QualityInspectMatItemServiceModel> qualityInspectMatItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = QualityInsOrderAttachment.NODENAME, nodeInstId =
			QualityInsOrderAttachment.NODENAME,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> qualityInsOrderAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME,
			nodeInstId = QualityInspectOrderParty.PARTY_NODEINST_PUR_SUPPLIER,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectOrderParty corporateSupplierParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME,
			nodeInstId = QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectOrderParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId =
			QualityInspectOrderParty.PARTY_NODEINST_PROD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectOrderParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId =
			QualityInspectOrderParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectOrderParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectOrderParty.NODENAME, nodeInstId =
			QualityInspectOrderParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectOrderParty salesOrganizationParty;

	public List<QualityInspectMatItemServiceModel> getQualityInspectMatItemList() {
		return this.qualityInspectMatItemList;
	}

	public void setQualityInspectMatItemList(
			List<QualityInspectMatItemServiceModel> qualityInspectMatItemList) {
		this.qualityInspectMatItemList = qualityInspectMatItemList;
	}

	public List<ServiceEntityNode> getQualityInsOrderAttachmentList() {
		return this.qualityInsOrderAttachmentList;
	}

	public void setQualityInsOrderAttachmentList(
			List<ServiceEntityNode> qualityInsOrderAttachmentList) {
		this.qualityInsOrderAttachmentList = qualityInsOrderAttachmentList;
	}

	public QualityInspectOrder getQualityInspectOrder() {
		return this.qualityInspectOrder;
	}

	public void setQualityInspectOrder(QualityInspectOrder qualityInspectOrder) {
		this.qualityInspectOrder = qualityInspectOrder;
	}

	public QualityInsOrderActionNode getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(QualityInsOrderActionNode inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public QualityInsOrderActionNode getTestDoneBy() {
		return testDoneBy;
	}

	public void setTestDoneBy(QualityInsOrderActionNode testDoneBy) {
		this.testDoneBy = testDoneBy;
	}

	public QualityInsOrderActionNode getProcessDoneBy() {
		return processDoneBy;
	}

	public void setProcessDoneBy(QualityInsOrderActionNode processDoneBy) {
		this.processDoneBy = processDoneBy;
	}

	public QualityInspectOrderParty getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(QualityInspectOrderParty corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public QualityInspectOrderParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(QualityInspectOrderParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public QualityInspectOrderParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(QualityInspectOrderParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public QualityInspectOrderParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(QualityInspectOrderParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public QualityInspectOrderParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(QualityInspectOrderParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
