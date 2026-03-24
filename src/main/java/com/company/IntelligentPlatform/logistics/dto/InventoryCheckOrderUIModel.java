package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * Inventory Check order UI Model
 *
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class InventoryCheckOrderUIModel extends DocumentUIModel {

	protected String refWarehouseUUID;
	
	protected String refWarehouseId;
	
	protected String refWarehouseName;

	protected String refWarehouseAreaUUID;
	
	protected String refWarehouseAreaId;

	protected double grossUpdateValue;

	@ISEDropDownResourceMapping(resouceMapping = "InventoryCheckItem_inventCheckResult", valueFieldName = "grossCheckResultValue")
	protected int grossCheckResult;

	protected String grossCheckResultValue;

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getRefWarehouseAreaId() {
		return refWarehouseAreaId;
	}

	public void setRefWarehouseAreaId(String refWarehouseAreaId) {
		this.refWarehouseAreaId = refWarehouseAreaId;
	}

	public double getGrossUpdateValue() {
		return grossUpdateValue;
	}

	public void setGrossUpdateValue(double grossUpdateValue) {
		this.grossUpdateValue = grossUpdateValue;
	}

	public int getGrossCheckResult() {
		return grossCheckResult;
	}

	public void setGrossCheckResult(int grossCheckResult) {
		this.grossCheckResult = grossCheckResult;
	}

	public String getGrossCheckResultValue() {
		return grossCheckResultValue;
	}

	public void setGrossCheckResultValue(String grossCheckResultValue) {
		this.grossCheckResultValue = grossCheckResultValue;
	}
	
}
