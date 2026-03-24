package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class BillOfMaterialExcelModel extends SEUIComModel {	
	
	protected String uuid;
	
	protected String id;
	
	protected String name;
	
	protected String note;	
	
	protected String refMaterialSKUId;
	
	protected String refMaterialSKUName;
	
	protected String packageStandard;	
	
	protected double amount;	
	
	protected String refUnitName;
	
	protected int layer;
	
	protected String parentItemId;
	
	protected String parentItemMaterialName;
	
	protected String parentItemMaterialSKUId;
	
	protected int supplyType;
	
	protected String supplyTypeValue;
	
	protected String parentItemMaterialSKUName;
	
	protected int itemCategory;
	
	protected String itemCategoryValue;
	
	protected double fixLeadTime;
	
	protected double leadTimeOffset;
	
	protected double theoLossRate;
	
	protected String theoLossRateValue;
	
	protected String refSubBOMUUID;
	
	protected String refSubBOMId;
	
	protected String parentItemAmountLabel;
	
	protected String amountLabel;
    
    protected int status;
    
    protected String statusValue; 
	
	protected String refWocId;
	
	protected String refWocName;
	
	public BillOfMaterialExcelModel(){
		
	} 
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	public String getParentItemMaterialName() {
		return parentItemMaterialName;
	}

	public void setParentItemMaterialName(String parentItemMaterialName) {
		this.parentItemMaterialName = parentItemMaterialName;
	}

	public String getParentItemMaterialSKUId() {
		return parentItemMaterialSKUId;
	}

	public void setParentItemMaterialSKUId(String parentItemMaterialSKUId) {
		this.parentItemMaterialSKUId = parentItemMaterialSKUId;
	}

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}

	public String getSupplyTypeValue() {
		return supplyTypeValue;
	}

	public void setSupplyTypeValue(String supplyTypeValue) {
		this.supplyTypeValue = supplyTypeValue;
	}

	public String getParentItemMaterialSKUName() {
		return parentItemMaterialSKUName;
	}

	public void setParentItemMaterialSKUName(String parentItemMaterialSKUName) {
		this.parentItemMaterialSKUName = parentItemMaterialSKUName;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemCategoryValue() {
		return itemCategoryValue;
	}

	public void setItemCategoryValue(String itemCategoryValue) {
		this.itemCategoryValue = itemCategoryValue;
	}

	public double getFixLeadTime() {
		return fixLeadTime;
	}

	public void setFixLeadTime(double fixLeadTime) {
		this.fixLeadTime = fixLeadTime;
	}

	public double getLeadTimeOffset() {
		return leadTimeOffset;
	}

	public void setLeadTimeOffset(double leadTimeOffset) {
		this.leadTimeOffset = leadTimeOffset;
	}

	public double getTheoLossRate() {
		return theoLossRate;
	}

	public void setTheoLossRate(double theoLossRate) {
		this.theoLossRate = theoLossRate;
	}

	public String getTheoLossRateValue() {
		return theoLossRateValue;
	}

	public void setTheoLossRateValue(String theoLossRateValue) {
		this.theoLossRateValue = theoLossRateValue;
	}

	public String getRefSubBOMUUID() {
		return refSubBOMUUID;
	}

	public void setRefSubBOMUUID(String refSubBOMUUID) {
		this.refSubBOMUUID = refSubBOMUUID;
	}

	public String getRefSubBOMId() {
		return refSubBOMId;
	}

	public void setRefSubBOMId(String refSubBOMId) {
		this.refSubBOMId = refSubBOMId;
	}

	public String getParentItemAmountLabel() {
		return parentItemAmountLabel;
	}

	public void setParentItemAmountLabel(String parentItemAmountLabel) {
		this.parentItemAmountLabel = parentItemAmountLabel;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getRefWocId() {
		return refWocId;
	}

	public void setRefWocId(String refWocId) {
		this.refWocId = refWocId;
	}

	public String getRefWocName() {
		return refWocName;
	}

	public void setRefWocName(String refWocName) {
		this.refWocName = refWocName;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
