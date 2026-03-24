package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProcessRouteProcessItemUIModel extends SEUIComModel implements Comparable<ProcessRouteProcessItemUIModel> {

	protected String parentNodeUUID;

	protected int keyProcessFlag;
	
	protected String keyProcessValue;

	@ISEDropDownResourceMapping(resouceMapping = "ProcessRouteProcessItem_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	protected int processIndex;

	protected double productionBatchSize;

	protected double moveBatchSize;

    protected double fixedExecutionTime;

    protected double varExecutionTime;

	protected double prepareTime;
	
	protected double queueTime;

	protected double varMoveTime;

	protected double fixedMoveTime;

	protected String refWorkCenterUUID;

	protected String refWorkCenterName;

	protected String refWorkCenterId;

	protected String prodProcessId;

	protected String prodProcessName;
	
	protected String refUUID;
	
	protected String documentId;

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public double getProductionBatchSize() {
		return productionBatchSize;
	}

	public void setProductionBatchSize(double productionBatchSize) {
		this.productionBatchSize = productionBatchSize;
	}

	public double getMoveBatchSize() {
		return moveBatchSize;
	}

	public void setMoveBatchSize(double moveBatchSize) {
		this.moveBatchSize = moveBatchSize;
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

	public double getVarMoveTime() {
		return varMoveTime;
	}

	public void setVarMoveTime(double varMoveTime) {
		this.varMoveTime = varMoveTime;
	}

	public double getFixedMoveTime() {
		return fixedMoveTime;
	}

	public void setFixedMoveTime(double fixedMoveTime) {
		this.fixedMoveTime = fixedMoveTime;
	}

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

	public String getProdProcessId() {
		return prodProcessId;
	}

	public void setProdProcessId(String prodProcessId) {
		this.prodProcessId = prodProcessId;
	}

	public String getProdProcessName() {
		return prodProcessName;
	}

	public void setProdProcessName(String prodProcessName) {
		this.prodProcessName = prodProcessName;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public int compareTo(ProcessRouteProcessItemUIModel other) {
		Integer thisProcessIndex = this.getProcessIndex();
		Integer otherProcessIndex = other.getProcessIndex();
		return thisProcessIndex.compareTo(otherProcessIndex);
	}

}
