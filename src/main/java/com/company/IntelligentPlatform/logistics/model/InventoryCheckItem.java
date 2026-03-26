package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InventoryCheckItem (extends DocMatItemNode)
 * Table: InventoryCheckItem (schema: logistics)
 */
@Entity
@Table(name = "InventoryCheckItem", catalog = "logistics")
public class InventoryCheckItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InventoryCheckItem;

	public static final int CHECK_RESULT_BALANCE = 1;

	public static final int CHECK_RESULT_PROFIT = 2;

	public static final int CHECK_RESULT_LOSS = 3;

	@Column(name = "refWarehouseStoreItemUUID")
	protected String refWarehouseStoreItemUUID;

	@Column(name = "declaredValue")
	protected double declaredValue;

	@Column(name = "resultAmount")
	protected double resultAmount;

	@Column(name = "resultUnitUUID")
	protected String resultUnitUUID;

	@Column(name = "resultDeclaredValue")
	protected double resultDeclaredValue;

	@Column(name = "inventCheckResult")
	protected int inventCheckResult;

	@Column(name = "updateAmount")
	protected double updateAmount;

	@Column(name = "updateDeclaredValue")
	protected double updateDeclaredValue;

	@Column(name = "updateUnitUUID")
	protected String updateUnitUUID;

	public String getRefWarehouseStoreItemUUID() {
		return refWarehouseStoreItemUUID;
	}

	public void setRefWarehouseStoreItemUUID(String refWarehouseStoreItemUUID) {
		this.refWarehouseStoreItemUUID = refWarehouseStoreItemUUID;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
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

	public double getResultDeclaredValue() {
		return resultDeclaredValue;
	}

	public void setResultDeclaredValue(double resultDeclaredValue) {
		this.resultDeclaredValue = resultDeclaredValue;
	}

	public int getInventCheckResult() {
		return inventCheckResult;
	}

	public void setInventCheckResult(int inventCheckResult) {
		this.inventCheckResult = inventCheckResult;
	}

	public double getUpdateAmount() {
		return updateAmount;
	}

	public void setUpdateAmount(double updateAmount) {
		this.updateAmount = updateAmount;
	}

	public double getUpdateDeclaredValue() {
		return updateDeclaredValue;
	}

	public void setUpdateDeclaredValue(double updateDeclaredValue) {
		this.updateDeclaredValue = updateDeclaredValue;
	}

	public String getUpdateUnitUUID() {
		return updateUnitUUID;
	}

	public void setUpdateUnitUUID(String updateUnitUUID) {
		this.updateUnitUUID = updateUnitUUID;
	}

}
