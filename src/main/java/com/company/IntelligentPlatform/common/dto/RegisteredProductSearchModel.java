package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;


@Component
public class RegisteredProductSearchModel extends SEUIComModel {
	
	public static final String NODE_InstId_PurchaseOrgParty = "purchaseOrgParty";
	
	public static final String NODE_InstId_SalesOrgParty = "salesOrgParty";
	
	public static final String NODE_InstId_CustomerParty = "customerParty";
	
	public static final String NODE_InstId_SupplierParty = "supplierParty";
	
	public static final String NODE_InstId_ProductOrgParty = "productOrgParty";
	
	public static final String NODE_InstId_SupportOrgParty = "supportOrgParty";
	
	public static final String NODE_InstId_PurchaseOrgContact = "purchaseOrgContact";
	
	public static final String NODE_InstId_SalesOrgContact = "salesOrgContact";
	
	public static final String NODE_InstId_CustomerContact = "customerContact";
	
	public static final String NODE_InstId_SupplierContact = "supplierContact";
	
	public static final String NODE_InstId_PurchaseOrganization = "purchaseOrganization";
	
	public static final String NODE_InstId_SalesOrganization = "salesOrganization";
	
	public static final String NODE_InstId_CorporateCustomer = "corporateCustomer";
	
	public static final String NODE_InstId_CorporateSupplier = "corporateSupplier";
	
	@BSearchFieldConfig(fieldName = "serialId", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, nodeInstID = RegisteredProduct.SENAME)
	protected String serialId;
	
	@BSearchFieldConfig(fieldName = "referenceDate", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, 
			nodeInstID = RegisteredProduct.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date referenceDateLow;
	
	@BSearchFieldConfig(fieldName = "referenceDate", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, 
			nodeInstID = RegisteredProduct.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date referenceDateHigh;

	@BSearchGroupConfig(groupInstId = RegisteredProduct.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = MaterialType.SENAME)
	protected MaterialTypeSearchSubModel materialType;

	@BSearchGroupConfig(groupInstId = RegisteredProduct.NODENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchFieldConfig(fieldName = "traceStatus", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, nodeInstID = RegisteredProduct.SENAME)
	protected int traceStatus;

	@BSearchFieldConfig(fieldName = "productionBatchNumber", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, nodeInstID = RegisteredProduct.SENAME)
	protected String productionBatchNumber;

	@BSearchFieldConfig(fieldName = "productionDate", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, 
			nodeInstID = RegisteredProduct.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date productionDateLow;
	
	@BSearchFieldConfig(fieldName = "productionDate", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, 
			nodeInstID = RegisteredProduct.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date productionDateHigh;	

	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = RegisteredProduct.NODENAME, seName = RegisteredProduct.SENAME, nodeInstID = RegisteredProduct.SENAME)
	protected String refMaterialSKUUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;
	
	@BSearchFieldConfig(fieldName = "cargoType", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int cargoType;

	@BSearchFieldConfig(fieldName = "supplyType", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int supplyType;
	
	@BSearchFieldConfig(fieldName = "packageStandard", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String packageStandard;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_SUBMIT)
	protected DocActionNodeSearchModel activeBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel inProcessBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ACTIVE)
	protected DocActionNodeSearchModel wasteBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_REINIT)
	protected DocActionNodeSearchModel deleteBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ARCHIVE)
	protected DocActionNodeSearchModel archivedBy;

	// compound search field
	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_PUR_ORG)
	protected DocInvolvePartySearchModel purchaseParty;


	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_PUR_ORG)
	protected DocInvolvePartySearchModel purchaseOrganization;

	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_SOLD_ORG)
	protected DocInvolvePartySearchModel salesOrganization;

	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected DocInvolvePartySearchModel corporateCustomer;

	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected DocInvolvePartySearchModel corporateSupplier;

	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_PROD_ORG)
	protected DocInvolvePartySearchModel productOrganization;

	@BSearchGroupConfig(groupInstId = RegisteredProductInvolveParty.PARTY_NODEINST_SUPPORT_ORG)
	protected DocInvolvePartySearchModel supportOrganization;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public int getTraceStatus() {
		return traceStatus;
	}

	public void setTraceStatus(int traceStatus) {
		this.traceStatus = traceStatus;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public Date getReferenceDateLow() {
		return referenceDateLow;
	}

	public void setReferenceDateLow(Date referenceDateLow) {
		this.referenceDateLow = referenceDateLow;
	}

	public Date getReferenceDateHigh() {
		return referenceDateHigh;
	}

	public void setReferenceDateHigh(Date referenceDateHigh) {
		this.referenceDateHigh = referenceDateHigh;
	}

	public Date getProductionDateLow() {
		return productionDateLow;
	}

	public void setProductionDateLow(Date productionDateLow) {
		this.productionDateLow = productionDateLow;
	}

	public Date getProductionDateHigh() {
		return productionDateHigh;
	}

	public void setProductionDateHigh(Date productionDateHigh) {
		this.productionDateHigh = productionDateHigh;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public int getCargoType() {
		return cargoType;
	}

	public void setCargoType(int cargoType) {
		this.cargoType = cargoType;
	}

	public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public ServiceDocSearchHeaderModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
		this.headerModel = headerModel;
	}

	public MaterialTypeSearchSubModel getMaterialType() {
		return materialType;
	}

	public void setMaterialType(MaterialTypeSearchSubModel materialType) {
		this.materialType = materialType;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public DocActionNodeSearchModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(DocActionNodeSearchModel activeBy) {
		this.activeBy = activeBy;
	}

	public DocActionNodeSearchModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(DocActionNodeSearchModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public DocActionNodeSearchModel getWasteBy() {
		return wasteBy;
	}

	public void setWasteBy(DocActionNodeSearchModel wasteBy) {
		this.wasteBy = wasteBy;
	}

	public DocActionNodeSearchModel getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(DocActionNodeSearchModel deleteBy) {
		this.deleteBy = deleteBy;
	}

	public DocActionNodeSearchModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(DocActionNodeSearchModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}

	public DocInvolvePartySearchModel getPurchaseParty() {
		return purchaseParty;
	}

	public void setPurchaseParty(DocInvolvePartySearchModel purchaseParty) {
		this.purchaseParty = purchaseParty;
	}

	public DocInvolvePartySearchModel getPurchaseOrganization() {
		return purchaseOrganization;
	}

	public void setPurchaseOrganization(DocInvolvePartySearchModel purchaseOrganization) {
		this.purchaseOrganization = purchaseOrganization;
	}

	public DocInvolvePartySearchModel getSalesOrganization() {
		return salesOrganization;
	}

	public void setSalesOrganization(DocInvolvePartySearchModel salesOrganization) {
		this.salesOrganization = salesOrganization;
	}

	public DocInvolvePartySearchModel getCorporateCustomer() {
		return corporateCustomer;
	}

	public void setCorporateCustomer(DocInvolvePartySearchModel corporateCustomer) {
		this.corporateCustomer = corporateCustomer;
	}

	public DocInvolvePartySearchModel getCorporateSupplier() {
		return corporateSupplier;
	}

	public void setCorporateSupplier(DocInvolvePartySearchModel corporateSupplier) {
		this.corporateSupplier = corporateSupplier;
	}

	public DocInvolvePartySearchModel getProductOrganization() {
		return productOrganization;
	}

	public void setProductOrganization(DocInvolvePartySearchModel productOrganization) {
		this.productOrganization = productOrganization;
	}

	public DocInvolvePartySearchModel getSupportOrganization() {
		return supportOrganization;
	}

	public void setSupportOrganization(DocInvolvePartySearchModel supportOrganization) {
		this.supportOrganization = supportOrganization;
	}
}
