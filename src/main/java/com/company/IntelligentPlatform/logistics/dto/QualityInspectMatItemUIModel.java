package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;

public class QualityInspectMatItemUIModel extends DocMatItemUIModel {

	protected int checkTimes;
	
	protected String sampleUnitUUID;
	
	protected String checkDate;

	protected double sampleAmount;
	
	protected String sampleAmountLabel;

	protected double failAmount; 
	
	protected String failAmountLabel;
	
	protected String failRefUnitUUID;

	protected double successAmount; 
	
	protected String successAmountLabel;
	
	protected String successRefUnitUUID;
	
	protected String refWasteWarehouseUUID;
	
	protected String refWasteWarehouseId;
	
	protected String refWasteWarehouseName;
	
	protected String refWasteWareAreaUUID;
	
	protected String refWasteWareAreaId;
	
	protected String itemCheckResult;
	
	protected double sampleRate;
	
	protected String sampleRateValue;
	
	protected int itemCheckStatus;
	
	protected String itemCheckStatusValue;
	
	protected int itemInspectType;
	
	protected String itemInspectTypeValue;

	protected String refUUID;
	
	protected int category;
	
	protected String refWarehouseAreaUUID;
	
	protected String refWarehouseAreaId;
	
	protected String refWarehouseUUID;
	
	protected int splitEnableFlag;
	
	public QualityInspectMatItemUIModel(){
		this.editSerialIdFlag = StandardSwitchProxy.SWITCH_OFF;
		this.splitEnableFlag = StandardSwitchProxy.SWITCH_OFF;
	}

	public int getCheckTimes() {
		return this.checkTimes;
	}

	public void setCheckTimes(int checkTimes) {
		this.checkTimes = checkTimes;
	}

	public String getSampleUnitUUID() {
		return this.sampleUnitUUID;
	}

	public void setSampleUnitUUID(String sampleUnitUUID) {
		this.sampleUnitUUID = sampleUnitUUID;
	}

	public String getSampleAmountLabel() {
		return sampleAmountLabel;
	}

	public void setSampleAmountLabel(String sampleAmountLabel) {
		this.sampleAmountLabel = sampleAmountLabel;
	}

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	public double getSampleAmount() {
		return this.sampleAmount;
	}

	public void setSampleAmount(double sampleAmount) {
		this.sampleAmount = sampleAmount;
	}
	
	public String getItemCheckResult() {
		return this.itemCheckResult;
	}

	public void setItemCheckResult(String itemCheckResult) {
		this.itemCheckResult = itemCheckResult;
	}

	public double getSampleRate() {
		return this.sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public String getSampleRateValue() {
		return sampleRateValue;
	}

	public void setSampleRateValue(String sampleRateValue) {
		this.sampleRateValue = sampleRateValue;
	}

	public int getItemCheckStatus() {
		return this.itemCheckStatus;
	}

	public void setItemCheckStatus(int itemCheckStatus) {
		this.itemCheckStatus = itemCheckStatus;
	}

	public String getItemCheckStatusValue() {
		return itemCheckStatusValue;
	}

	public void setItemCheckStatusValue(String itemCheckStatusValue) {
		this.itemCheckStatusValue = itemCheckStatusValue;
	}

	public String getItemInspectTypeValue() {
		return itemInspectTypeValue;
	}

	public void setItemInspectTypeValue(String itemInspectTypeValue) {
		this.itemInspectTypeValue = itemInspectTypeValue;
	}

	public int getItemInspectType() {
		return this.itemInspectType;
	}

	public void setItemInspectType(int itemInspectType) {
		this.itemInspectType = itemInspectType;
	}

	public String getRefUUID() {
		return this.refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public double getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(double failAmount) {
		this.failAmount = failAmount;
	}

	public String getFailAmountLabel() {
		return failAmountLabel;
	}

	public void setFailAmountLabel(String failAmountLabel) {
		this.failAmountLabel = failAmountLabel;
	}

	public String getFailRefUnitUUID() {
		return failRefUnitUUID;
	}

	public void setFailRefUnitUUID(String failRefUnitUUID) {
		this.failRefUnitUUID = failRefUnitUUID;
	}

	public String getRefWasteWarehouseUUID() {
		return refWasteWarehouseUUID;
	}

	public void setRefWasteWarehouseUUID(String refWasteWarehouseUUID) {
		this.refWasteWarehouseUUID = refWasteWarehouseUUID;
	}

	public String getRefWasteWarehouseId() {
		return refWasteWarehouseId;
	}

	public void setRefWasteWarehouseId(String refWasteWarehouseId) {
		this.refWasteWarehouseId = refWasteWarehouseId;
	}

	public String getRefWasteWareAreaUUID() {
		return refWasteWareAreaUUID;
	}

	public void setRefWasteWareAreaUUID(String refWasteWareAreaUUID) {
		this.refWasteWareAreaUUID = refWasteWareAreaUUID;
	}

	public String getRefWasteWareAreaId() {
		return refWasteWareAreaId;
	}

	public void setRefWasteWareAreaId(String refWasteWareAreaId) {
		this.refWasteWareAreaId = refWasteWareAreaId;
	}

	public double getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(double successAmount) {
		this.successAmount = successAmount;
	}

	public String getSuccessAmountLabel() {
		return successAmountLabel;
	}

	public void setSuccessAmountLabel(String successAmountLabel) {
		this.successAmountLabel = successAmountLabel;
	}

	public String getSuccessRefUnitUUID() {
		return successRefUnitUUID;
	}

	public void setSuccessRefUnitUUID(String successRefUnitUUID) {
		this.successRefUnitUUID = successRefUnitUUID;
	}

	public String getRefWasteWarehouseName() {
		return refWasteWarehouseName;
	}

	public void setRefWasteWarehouseName(String refWasteWarehouseName) {
		this.refWasteWarehouseName = refWasteWarehouseName;
	}

	public int getSplitEnableFlag() {
		return splitEnableFlag;
	}

	public void setSplitEnableFlag(int splitEnableFlag) {
		this.splitEnableFlag = splitEnableFlag;
	}

}
