package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

/**
 * In-bound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class InventoryCheckItemUIModel extends DocMatItemUIModel {
	
	protected int grossCheckResult;
	
	protected String grossCheckResultValue;
	
	protected int orderStatus;
	
	protected String orderStatusValue;
	
	protected int orderApproveStatus;
	
	protected String processDate;
	
	@ISEUIModelMapping(fieldName = "uuid", seName = WarehouseStoreItem.SENAME, nodeName = WarehouseStoreItem.NODENAME, nodeInstID = WarehouseStoreItem.SENAME, hiddenFlag = true)
	protected String refWarehouseStoreItemUUID;
	
	@ISEUIModelMapping(fieldName = "declaredValue",  seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected double declaredValue;

	@ISEUIModelMapping(fieldName = "resultAmount",seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected double resultAmount;
	
	protected String resultAmountLabel;
	  
	@ISEUIModelMapping(fieldName = "resultUnitUUID", seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected String resultUnitUUID;
	
	@ISEUIModelMapping(fieldName = "name", seName = StandardMaterialUnit.SENAME, nodeName = StandardMaterialUnit.NODENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected String resultUnitName;	
	
	@ISEUIModelMapping(fieldName = "resultDeclaredValue", seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected double resultDeclaredValue;
	
	@ISEUIModelMapping(fieldName = "inventCheckResult", seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "InventoryCheckItem_inventCheckResult", valueFieldName = "inventCheckResultValue")
	protected int inventCheckResult;
	
	@ISEUIModelMapping(seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME, showOnEditor = false)
	protected String inventCheckResultValue;
	
	@ISEUIModelMapping(fieldName = "updateAmount", seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected double updateAmount;
	
	protected String updateAmountLabel;
	
	@ISEUIModelMapping(fieldName = "updateDeclaredValue", seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected double updateDeclaredValue;
	
	@ISEUIModelMapping(fieldName = "updateUnitUUID", seName = InventoryCheckItem.SENAME, nodeName = InventoryCheckItem.NODENAME, nodeInstID = InventoryCheckItem.NODENAME)
	protected String updateUnitUUID;

	public String getRefWarehouseStoreItemUUID() {
		return refWarehouseStoreItemUUID;
	}

	public void setRefWarehouseStoreItemUUID(String refWarehouseStoreItemUUID) {
		this.refWarehouseStoreItemUUID = refWarehouseStoreItemUUID;
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

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public double getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(double resultAmount) {
		this.resultAmount = resultAmount;
	}

	public String getResultUnitUUID() {
		return resultUnitUUID;
	}

	public void setResultUnitUUID(String resultUnitUUID) {
		this.resultUnitUUID = resultUnitUUID;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public double getResultDeclaredValue() {
		return resultDeclaredValue;
	}

	public void setResultDeclaredValue(double resultDeclaredValue) {
		this.resultDeclaredValue = resultDeclaredValue;
	}

	public double getUpdateDeclaredValue() {
		return updateDeclaredValue;
	}

	public void setUpdateDeclaredValue(double updateDeclaredValue) {
		this.updateDeclaredValue = updateDeclaredValue;
	}

	public int getInventCheckResult() {
		return inventCheckResult;
	}

	public void setInventCheckResult(int inventCheckResult) {
		this.inventCheckResult = inventCheckResult;
	}

	public String getInventCheckResultValue() {
		return inventCheckResultValue;
	}

	public void setInventCheckResultValue(String inventCheckResultValue) {
		this.inventCheckResultValue = inventCheckResultValue;
	}

	public double getUpdateAmount() {
		return updateAmount;
	}

	public void setUpdateAmount(double updateAmount) {
		this.updateAmount = updateAmount;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public String getResultAmountLabel() {
		return resultAmountLabel;
	}

	public void setResultAmountLabel(String resultAmountLabel) {
		this.resultAmountLabel = resultAmountLabel;
	}

	public String getUpdateAmountLabel() {
		return updateAmountLabel;
	}

	public void setUpdateAmountLabel(String updateAmountLabel) {
		this.updateAmountLabel = updateAmountLabel;
	}

	public String getUpdateUnitUUID() {
		return updateUnitUUID;
	}

	public void setUpdateUnitUUID(String updateUnitUUID) {
		this.updateUnitUUID = updateUnitUUID;
	}

	
}
