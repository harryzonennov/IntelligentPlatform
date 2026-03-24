package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinProduction - ProductionResourceUnion (extends ServiceEntityNode)
 * Table: ProductionResourceUnion (schema: production)
 *
 * Represents a resource (machine, person, tool) attached to a work center.
 */
@Entity
@Table(name = "ProductionResourceUnion", schema = "production")
public class ProductionResourceUnion extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProductionResourceUnion;

	public static final int RESOURCE_TYPE_MACHINE = 1;

	public static final int RESOURCE_TYPE_PERSON  = 2;

	public static final int RESOURCE_TYPE_TOOL    = 3;

	@Column(name = "utilizationRate")
	protected double utilizationRate;

	@Column(name = "efficiency")
	protected double efficiency;

	@Column(name = "resourceType")
	protected int resourceType;

	@Column(name = "dailyShift")
	protected double dailyShift;

	@Column(name = "costInhour")
	protected double costInhour;

	@Column(name = "refCostCenterUUID")
	protected String refCostCenterUUID;

	@Column(name = "keyResourceFlag")
	protected int keyResourceFlag;

	@Column(name = "workHoursInShift")
	protected double workHoursInShift;

	public double getUtilizationRate() {
		return utilizationRate;
	}

	public void setUtilizationRate(double utilizationRate) {
		this.utilizationRate = utilizationRate;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public double getDailyShift() {
		return dailyShift;
	}

	public void setDailyShift(double dailyShift) {
		this.dailyShift = dailyShift;
	}

	public double getCostInhour() {
		return costInhour;
	}

	public void setCostInhour(double costInhour) {
		this.costInhour = costInhour;
	}

	public String getRefCostCenterUUID() {
		return refCostCenterUUID;
	}

	public void setRefCostCenterUUID(String refCostCenterUUID) {
		this.refCostCenterUUID = refCostCenterUUID;
	}

	public int getKeyResourceFlag() {
		return keyResourceFlag;
	}

	public void setKeyResourceFlag(int keyResourceFlag) {
		this.keyResourceFlag = keyResourceFlag;
	}

	public double getWorkHoursInShift() {
		return workHoursInShift;
	}

	public void setWorkHoursInShift(double workHoursInShift) {
		this.workHoursInShift = workHoursInShift;
	}

}
