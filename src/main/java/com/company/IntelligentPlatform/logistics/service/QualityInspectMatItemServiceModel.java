package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.QualityInsMatItemAttachment;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItem;
import com.company.IntelligentPlatform.logistics.model.QualityInspectMatItemParty;
import com.company.IntelligentPlatform.logistics.model.QualityInspectPropertyItem;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class QualityInspectMatItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItem.NODENAME, nodeInstId = QualityInspectMatItem.NODENAME,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected QualityInspectMatItem qualityInspectMatItem;

	@IServiceModuleFieldConfig(nodeName = QualityInsMatItemAttachment.NODENAME, nodeInstId = QualityInsMatItemAttachment.NODENAME,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> qualityInsMatItemAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = QualityInspectPropertyItem.NODENAME, nodeInstId = QualityInspectPropertyItem.NODENAME)
	protected List<QualityInspectPropertyItemServiceModel> qualityInspectPropertyItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId = QualityInspectMatItemParty.PARTY_NODEINST_PUR_SUPPLIER,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectMatItemParty corporateSupplierParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId = QualityInspectMatItemParty.PARTY_NODEINST_PUR_ORG,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectMatItemParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId =
			QualityInspectMatItemParty.PARTY_NODEINST_PROD_ORG,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectMatItemParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId =
			QualityInspectMatItemParty.PARTY_NODEINST_SOLD_CUSTOMER,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectMatItemParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = QualityInspectMatItemParty.NODENAME, nodeInstId =
			QualityInspectMatItemParty.PARTY_NODEINST_SOLD_ORG,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected QualityInspectMatItemParty salesOrganizationParty;

	public List<ServiceEntityNode> getQualityInsMatItemAttachmentList() {
		return this.qualityInsMatItemAttachmentList;
	}

	public void setQualityInsMatItemAttachmentList(
			List<ServiceEntityNode> qualityInsMatItemAttachmentList) {
		this.qualityInsMatItemAttachmentList = qualityInsMatItemAttachmentList;
	}

	public QualityInspectMatItem getQualityInspectMatItem() {
		return this.qualityInspectMatItem;
	}

	public void setQualityInspectMatItem(
			QualityInspectMatItem qualityInspectMatItem) {
		this.qualityInspectMatItem = qualityInspectMatItem;
	}

	public List<QualityInspectPropertyItemServiceModel> getQualityInspectPropertyItemList() {
		return this.qualityInspectPropertyItemList;
	}

	public void setQualityInspectPropertyItemList(
			List<QualityInspectPropertyItemServiceModel> qualityInspectPropertyItemList) {
		this.qualityInspectPropertyItemList = qualityInspectPropertyItemList;
	}

	public QualityInspectMatItemParty getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(QualityInspectMatItemParty corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public QualityInspectMatItemParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(QualityInspectMatItemParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public QualityInspectMatItemParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(QualityInspectMatItemParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public QualityInspectMatItemParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(QualityInspectMatItemParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public QualityInspectMatItemParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(QualityInspectMatItemParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}
