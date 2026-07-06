package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - ProdPickingOrder (extends DocumentContent)
 * Table: ProdPickingOrder (schema: production)
 */
@Entity
@Table(name = "ProdPickingOrder", catalog = "production")
public class ProdPickingOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ProdPickingOrder;

	public static final int STATUS_INITIAL         = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED        = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_INPROCESS       = DocumentContent.STATUS_INPROCESS;

	public static final int STATUS_DELIVERYDONE    = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_PROCESSDONE     = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int CATEGORY_MANUAL         = 1;

	public static final int CATEGORY_PRODORDER      = 2;

	public static final int CATEGORY_PRODORDERBATCH = 3;

	public static final int PROCESSTYPE_INPLAN    = 1;

	public static final int PROCESSTYPE_REPLENISH = 2;

	public static final int PROCESSTYPE_RETURN    = 3;

	@Column(name = "category")
	protected int category;

	@Column(name = "processType")
	protected int processType;

	@Column(name = "approveBy")
	protected String approveBy;

	@Column(name = "approveDate")
	protected LocalDate approveDate;

	@Column(name = "approveType")
	protected int approveType;

	@Column(name = "processBy")
	protected String processBy;

	@Column(name = "processDate")
	protected LocalDate processDate;

	@Column(name = "grossCost")
	protected double grossCost;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public LocalDate getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(LocalDate approveDate) {
		this.approveDate = approveDate;
	}

	public int getApproveType() {
		return approveType;
	}

	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}

	public String getProcessBy() {
		return processBy;
	}

	public void setProcessBy(String processBy) {
		this.processBy = processBy;
	}

	public LocalDate getProcessDate() {
		return processDate;
	}

	public void setProcessDate(LocalDate processDate) {
		this.processDate = processDate;
	}

	public double getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(double grossCost) {
		this.grossCost = grossCost;
	}

}
