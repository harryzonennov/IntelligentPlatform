package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProcessBOMItemUIModel extends SEUIComModel {

	protected String refProssRouteProcessItemUUID;
	
	protected int keyRouteFlag; 
	
	protected String keyRouteValue;

	protected String refWorkCenterName;
	
	protected String refWorkCenterID;
	
	protected String prodProcessId;

	protected String prodProcessName;
	
	protected String refProcessItemID;

    protected int processIndex;

    protected double productionBatchSize;

    protected double moveBatchSize;

    protected double executionTime;
	
    protected double prepareTime;
	
    protected double queueTime;
	
    protected double moveTime;	
	
	public ProcessBOMItemUIModel(){
		
	}

	public String getRefProssRouteProcessItemUUID() {
		return refProssRouteProcessItemUUID;
	}

	public void setRefProssRouteProcessItemUUID(String refProssRouteProcessItemUUID) {
		this.refProssRouteProcessItemUUID = refProssRouteProcessItemUUID;
	}

	public int getKeyRouteFlag() {
		return keyRouteFlag;
	}

	public void setKeyRouteFlag(int keyRouteFlag) {
		this.keyRouteFlag = keyRouteFlag;
	}

	public String getKeyRouteValue() {
		return keyRouteValue;
	}

	public void setKeyRouteValue(String keyRouteValue) {
		this.keyRouteValue = keyRouteValue;
	}

	public void setRefProcessItemID(String refProcessItemID) {
		this.refProcessItemID = refProcessItemID;
	}

	public String getRefWorkCenterName() {
		return refWorkCenterName;
	}

	public void setRefWorkCenterName(String refWorkCenterName) {
		this.refWorkCenterName = refWorkCenterName;
	}

	public String getRefWorkCenterID() {
		return refWorkCenterID;
	}

	public void setRefWorkCenterID(String refWorkCenterID) {
		this.refWorkCenterID = refWorkCenterID;
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

	public String getRefProcessItemID() {
		return refProcessItemID;
	}

	public void setRefProcessItemID2(String refProcessItemID) {
		this.refProcessItemID = refProcessItemID;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
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

	public double getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(double executionTime) {
		this.executionTime = executionTime;
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

	public double getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(double moveTime) {
		this.moveTime = moveTime;
	}

}
