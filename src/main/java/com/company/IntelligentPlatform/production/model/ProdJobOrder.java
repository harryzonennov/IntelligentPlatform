package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinProduction - ProdJobOrder (extends ServiceEntityNode)
 * Table: ProdJobOrder (schema: production)
 */
@Entity
@Table(name = "ProdJobOrder", catalog = "production")
public class ProdJobOrder extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProdJobOrder;

	public static final int STATUS_INITIAL    = 1;

	public static final int STATUS_INPROCESS  = 2;

	public static final int STATUS_FINISHED   = 3;

	public static final int STATUS_BLOCKED    = 4;

	public static final int CATEGORY_STANDARD = 1;

	public static final int CATEGORY_REPAIR   = 2;

	public static final int CATEGORY_PROD     = 3;

	@Column(name = "refProductionOrderUUID")
	protected String refProductionOrderUUID;

	@Column(name = "refProdRouteProcessItemUUID")
	protected String refProdRouteProcessItemUUID;

	@Column(name = "refWorkCenterUUID")
	protected String refWorkCenterUUID;

	@Column(name = "category")
	protected int category;

	@Column(name = "status")
	protected int status;

	@Column(name = "planStartDate")
	protected LocalDateTime planStartDate;

	@Column(name = "startDate")
	protected LocalDateTime startDate;

	@Column(name = "planEndDate")
	protected LocalDateTime planEndDate;

	@Column(name = "endDate")
	protected LocalDateTime endDate;

	@Column(name = "planNeedTime")
	protected double planNeedTime;

	public String getRefProductionOrderUUID() {
		return refProductionOrderUUID;
	}

	public void setRefProductionOrderUUID(String refProductionOrderUUID) {
		this.refProductionOrderUUID = refProductionOrderUUID;
	}

	public String getRefProdRouteProcessItemUUID() {
		return refProdRouteProcessItemUUID;
	}

	public void setRefProdRouteProcessItemUUID(String refProdRouteProcessItemUUID) {
		this.refProdRouteProcessItemUUID = refProdRouteProcessItemUUID;
	}

	public String getRefWorkCenterUUID() {
		return refWorkCenterUUID;
	}

	public void setRefWorkCenterUUID(String refWorkCenterUUID) {
		this.refWorkCenterUUID = refWorkCenterUUID;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(LocalDateTime planStartDate) {
		this.planStartDate = planStartDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(LocalDateTime planEndDate) {
		this.planEndDate = planEndDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public double getPlanNeedTime() {
		return planNeedTime;
	}

	public void setPlanNeedTime(double planNeedTime) {
		this.planNeedTime = planNeedTime;
	}

}
