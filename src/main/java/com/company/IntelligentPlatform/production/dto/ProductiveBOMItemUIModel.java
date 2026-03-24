package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class ProductiveBOMItemUIModel extends DocMatItemUIModel {

	protected int supplyType;
	
	protected String supplyTypeValue;

	protected double amount;
	
    protected double amountWithLossRate;
	
	protected String amountLossRateLabel;

	protected String refUnitUUID;

	protected String refUnitName;

	protected int layer;

	protected String refParentItemUUID;

	protected String parentItemId;
	
	protected String parentItemMaterialName;

	protected String parentItemMaterialSKUId;

	protected String parentItemMaterialSKUName;

	protected int itemCategory;
	
	protected String itemCategoryValue;

	protected double fixLeadTime;
	
	protected double leadTimeOffset;
	
	protected double theoLossRate;
	
	protected String refSubBOMUUID;
	
	protected String refSubBOMId;
	
	protected String parentItemAmountLabel;
	
	protected String amountLabel;

    protected int tabFlag;
    
    protected int viewType;

    protected String refRouteProcessItemUUID;
    
    protected int refRouteProcessIndex;
    
    protected String refProdProcessId;
    
    protected String refProdProcessName;
    
    protected String refRouteOrderUUID;
	
	public ProductiveBOMItemUIModel(){
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmountWithLossRate() {
		return amountWithLossRate;
	}

	public void setAmountWithLossRate(double amountWithLossRate) {
		this.amountWithLossRate = amountWithLossRate;
	}

	public String getAmountLossRateLabel() {
		return amountLossRateLabel;
	}

	public void setAmountLossRateLabel(String amountLossRateLabel) {
		this.amountLossRateLabel = amountLossRateLabel;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
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

	public String getRefParentItemUUID() {
		return refParentItemUUID;
	}

	public void setRefParentItemUUID(String refParentItemUUID) {
		this.refParentItemUUID = refParentItemUUID;
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

	public int getTabFlag() {
		return tabFlag;
	}

	public void setTabFlag(int tabFlag) {
		this.tabFlag = tabFlag;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public double getFixLeadTime() {
		return fixLeadTime;
	}

	public void setFixLeadTime(double fixLeadTime) {
		this.fixLeadTime = fixLeadTime;
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

	public String getRefRouteProcessItemUUID() {
		return refRouteProcessItemUUID;
	}

	public void setRefRouteProcessItemUUID(String refRouteProcessItemUUID) {
		this.refRouteProcessItemUUID = refRouteProcessItemUUID;
	}

	public int getRefRouteProcessIndex() {
		return refRouteProcessIndex;
	}

	public void setRefRouteProcessIndex(int refRouteProcessIndex) {
		this.refRouteProcessIndex = refRouteProcessIndex;
	}

	public String getRefProdProcessId() {
		return refProdProcessId;
	}

	public void setRefProdProcessId(String refProdProcessId) {
		this.refProdProcessId = refProdProcessId;
	}

	public String getRefProdProcessName() {
		return refProdProcessName;
	}

	public void setRefProdProcessName(String refProdProcessName) {
		this.refProdProcessName = refProdProcessName;
	}

	public String getRefRouteOrderUUID() {
		return refRouteOrderUUID;
	}

	public void setRefRouteOrderUUID(String refRouteOrderUUID) {
		this.refRouteOrderUUID = refRouteOrderUUID;
	}

}
