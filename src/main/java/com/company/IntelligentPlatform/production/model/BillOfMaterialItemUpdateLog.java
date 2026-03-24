package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class BillOfMaterialItemUpdateLog extends ServiceEntityNode {


	public final static String NODENAME = IServiceModelConstants.BillOfMaterialItemUpdateLog;

	public final static String SENAME = BillOfMaterialOrder.SENAME;

	protected String refMaterialSKUUUID;

	protected double updateAmount;

	protected String updateRefUnitUUID;

	protected int updateAmountMode;

	protected int layer;

	protected String refParentItemUUID;

	protected String refBeforeItemUUID;

	protected String refAftertemUUID;

	protected int itemCategory;

	protected double leadTimeOffset;

	protected double updateTheoLossRate;

	protected int updateTheoLossRateMode;

	protected String refSubBOMUUID;

	protected String refRouteProcessItemUUID;

	protected String refWocUUID;

	public BillOfMaterialItemUpdateLog() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getUpdateAmount() {
		return updateAmount;
	}

	public void setUpdateAmount(double updateAmount) {
		this.updateAmount = updateAmount;
	}

	public String getUpdateRefUnitUUID() {
		return updateRefUnitUUID;
	}

	public void setUpdateRefUnitUUID(String updateRefUnitUUID) {
		this.updateRefUnitUUID = updateRefUnitUUID;
	}

	public int getUpdateAmountMode() {
		return updateAmountMode;
	}

	public void setUpdateAmountMode(int updateAmountMode) {
		this.updateAmountMode = updateAmountMode;
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

	public double getUpdateTheoLossRate() {
		return updateTheoLossRate;
	}

	public void setUpdateTheoLossRate(double updateTheoLossRate) {
		this.updateTheoLossRate = updateTheoLossRate;
	}

	public int getUpdateTheoLossRateMode() {
		return updateTheoLossRateMode;
	}

	public void setUpdateTheoLossRateMode(int updateTheoLossRateMode) {
		this.updateTheoLossRateMode = updateTheoLossRateMode;
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

	public String getRefWocUUID() {
		return refWocUUID;
	}

	public void setRefWocUUID(String refWocUUID) {
		this.refWocUUID = refWocUUID;
	}

	public String getRefBeforeItemUUID() {
		return refBeforeItemUUID;
	}

	public void setRefBeforeItemUUID(String refBeforeItemUUID) {
		this.refBeforeItemUUID = refBeforeItemUUID;
	}

	public String getRefAftertemUUID() {
		return refAftertemUUID;
	}

	public void setRefAftertemUUID(String refAftertemUUID) {
		this.refAftertemUUID = refAftertemUUID;
	}
}
