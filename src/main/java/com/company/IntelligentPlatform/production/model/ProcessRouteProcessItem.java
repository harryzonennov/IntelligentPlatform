package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.service.StandardKeyFlagProxy;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProcessRouteProcessItem extends ReferenceNode {

	public final static String NODENAME = IServiceModelConstants.ProcessRouteProcessItem;

	public final static String SENAME = ProcessRouteOrder.SENAME;

	public final static int START_INDEX = 1;

	public final static int STEP_INDEX = 1;

	protected int keyProcessFlag;

	protected int status;

	protected int processIndex;

	protected double productionBatchSize;

	protected double moveBatchSize;

	protected double fixedExecutionTime;

	protected double varExecutionTime;

	protected double prepareTime;

	protected double queueTime;

	protected double fixedMoveTime;

	protected double varMoveTime;

	protected String refWorkCenterUUID;

	public ProcessRouteProcessItem(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.keyProcessFlag = StandardKeyFlagProxy.NON_KEY;
	}

	public int getKeyProcessFlag() {
		return keyProcessFlag;
	}

	public void setKeyProcessFlag(int keyProcessFlag) {
		this.keyProcessFlag = keyProcessFlag;
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

	public double getProductionBatchSize() {
		return productionBatchSize;
	}

	public void setProductionBatchSize(double productionBatchSize) {
		this.productionBatchSize = productionBatchSize;
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

	public double getMoveBatchSize() {
		return moveBatchSize;
	}

	public void setMoveBatchSize(double moveBatchSize) {
		this.moveBatchSize = moveBatchSize;
	}

	public String getRefWorkCenterUUID() {
		return refWorkCenterUUID;
	}

	public void setRefWorkCenterUUID(String refWorkCenterUUID) {
		this.refWorkCenterUUID = refWorkCenterUUID;
	}

}
