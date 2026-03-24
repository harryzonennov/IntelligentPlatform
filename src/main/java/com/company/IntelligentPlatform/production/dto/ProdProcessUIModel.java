package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdProcessUIModel extends SEUIComModel {

	protected int keyProcessFlag;
	
	protected String keyProcessValue;

	@ISEDropDownResourceMapping(resouceMapping = "ProdProcess_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	protected String refWorkCenterUUID;	

	protected String refWorkCenterName;	

	protected String refWorkCenterId;	

	protected double ProductionBatchSize;

	protected double moveBatchSize;

	protected double fixedExecutionTime;

	protected double varExecutionTime;
	
	protected double prepareTime;
	
	protected double queueTime;
	
	protected double fixedMoveTime;
	
	protected double varMoveTime;

	public int getKeyProcessFlag() {
		return keyProcessFlag;
	}

	public void setKeyProcessFlag(int keyProcessFlag) {
		this.keyProcessFlag = keyProcessFlag;
	}

	public String getKeyProcessValue() {
		return keyProcessValue;
	}

	public void setKeyProcessValue(String keyProcessValue) {
		this.keyProcessValue = keyProcessValue;
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

	public String getRefWorkCenterUUID() {
		return refWorkCenterUUID;
	}

	public void setRefWorkCenterUUID(String refWorkCenterUUID) {
		this.refWorkCenterUUID = refWorkCenterUUID;
	}

	public String getRefWorkCenterName() {
		return refWorkCenterName;
	}

	public void setRefWorkCenterName(String refWorkCenterName) {
		this.refWorkCenterName = refWorkCenterName;
	}

	public String getRefWorkCenterId() {
		return refWorkCenterId;
	}

	public void setRefWorkCenterId(String refWorkCenterId) {
		this.refWorkCenterId = refWorkCenterId;
	}

	public double getMoveBatchSize() {
		return moveBatchSize;
	}

	public void setMoveBatchSize(double moveBatchSize) {
		this.moveBatchSize = moveBatchSize;
	}

	public double getFixedExecutionTime() {
		return fixedExecutionTime;
	}

	public void setFixedExecutionTime(double fixedExecutionTime) {
		this.fixedExecutionTime = fixedExecutionTime;
	}

	public double getVarExecutionTime() {
		return varExecutionTime;
	}

	public void setVarExecutionTime(double varExecutionTime) {
		this.varExecutionTime = varExecutionTime;
	}

	public double getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(double prepareTime) {
		this.prepareTime = prepareTime;
	}

	public double getQueueTime() {
		return queueTime;
	}

	public void setQueueTime(double queueTime) {
		this.queueTime = queueTime;
	}

	public double getFixedMoveTime() {
		return fixedMoveTime;
	}

	public void setFixedMoveTime(double fixedMoveTime) {
		this.fixedMoveTime = fixedMoveTime;
	}

	public double getVarMoveTime() {
		return varMoveTime;
	}

	public void setVarMoveTime(double varMoveTime) {
		this.varMoveTime = varMoveTime;
	}

	public double getProductionBatchSize() {
		return ProductionBatchSize;
	}

	public void setProductionBatchSize(double productionBatchSize) {
		ProductionBatchSize = productionBatchSize;
	}

}
