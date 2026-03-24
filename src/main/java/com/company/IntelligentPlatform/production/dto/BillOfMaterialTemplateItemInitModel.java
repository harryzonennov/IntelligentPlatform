package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class BillOfMaterialTemplateItemInitModel extends SEUIComModel {
	
	protected String uuid;
	
	protected String id;
	
	protected String refMaterialSKUUUID;	
	
	protected double amount;
	
	protected String refUnitUUID;	
	
	protected String refParentItemUUID;	
	
	protected int itemCategory;	
	
	protected double leadTimeOffset;	
	
	protected double theoLossRate;	

	protected String refSubBOMUUID;

	protected int status;
	
    protected String refRouteProcessItemUUID;
    
    protected int refRouteProcessIndex; 

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefParentItemUUID() {
		return refParentItemUUID;
	}

	public void setRefParentItemUUID(String refParentItemUUID) {
		this.refParentItemUUID = refParentItemUUID;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
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

	public String getRefSubBOMUUID() {
		return refSubBOMUUID;
	}

	public void setRefSubBOMUUID(String refSubBOMUUID) {
		this.refSubBOMUUID = refSubBOMUUID;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
