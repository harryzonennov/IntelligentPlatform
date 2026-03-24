package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinProduction - ProdProcess (extends ServiceEntityNode)
 * Table: ProdProcess (schema: production)
 */
@Entity
@Table(name = "ProdProcess", schema = "production")
public class ProdProcess extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProdProcess;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_INUSE   = 2;

	public static final int STATUS_RETIRED = 3;

	@Column(name = "keyProcessFlag")
	protected int keyProcessFlag;

	@Column(name = "status")
	protected int status;

	@Column(name = "refWorkCenterUUID")
	protected String refWorkCenterUUID;

	@Column(name = "productionBatchSize")
	protected double productionBatchSize;

	@Column(name = "moveBatchSize")
	protected double moveBatchSize;

	@Column(name = "fixedExecutionTime")
	protected double fixedExecutionTime;

	@Column(name = "varExecutionTime")
	protected double varExecutionTime;

	@Column(name = "prepareTime")
	protected double prepareTime;

	@Column(name = "queueTime")
	protected double queueTime;

	@Column(name = "fixedMoveTime")
	protected double fixedMoveTime;

	@Column(name = "varMoveTime")
	protected double varMoveTime;

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

	public String getRefWorkCenterUUID() {
		return refWorkCenterUUID;
	}

	public void setRefWorkCenterUUID(String refWorkCenterUUID) {
		this.refWorkCenterUUID = refWorkCenterUUID;
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

}
