package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - BillOfMaterialOrder (extends DocumentContent)
 * Table: BillOfMaterialOrder (schema: production)
 */
@Entity
@Table(name = "BillOfMaterialOrder", schema = "production")
public class BillOfMaterialOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.BillOfMaterialOrder;

	public static final int STATUS_INITIAL  = 1;

	public static final int STATUS_INUSE    = 2;

	public static final int STATUS_RETIRED  = 3;

	public static final int LEAD_CAL_MODE_MAT     = 1;

	public static final int LEAD_CAL_MODE_PROCESS = 2;

	public static final String FIELD_RefTemplateUUID = "refTemplateUUID";

	@Column(name = "refMaterialSKUUUID")
	protected String refMaterialSKUUUID;

	@Column(name = "amount")
	protected double amount;

	@Column(name = "refUnitUUID")
	protected String refUnitUUID;

	@Column(name = "itemCategory")
	protected int itemCategory;

	@Column(name = "refRouteOrderUUID")
	protected String refRouteOrderUUID;

	@Column(name = "leadTimeCalMode")
	protected int leadTimeCalMode;

	@Column(name = "refWocUUID")
	protected String refWocUUID;

	@Column(name = "versionNumber")
	protected int versionNumber;

	@Column(name = "patchNumber")
	protected int patchNumber;

	@Column(name = "refTemplateUUID")
	protected String refTemplateUUID;

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getRefRouteOrderUUID() {
		return refRouteOrderUUID;
	}

	public void setRefRouteOrderUUID(String refRouteOrderUUID) {
		this.refRouteOrderUUID = refRouteOrderUUID;
	}

	public int getLeadTimeCalMode() {
		return leadTimeCalMode;
	}

	public void setLeadTimeCalMode(int leadTimeCalMode) {
		this.leadTimeCalMode = leadTimeCalMode;
	}

	public String getRefWocUUID() {
		return refWocUUID;
	}

	public void setRefWocUUID(String refWocUUID) {
		this.refWocUUID = refWocUUID;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public int getPatchNumber() {
		return patchNumber;
	}

	public void setPatchNumber(int patchNumber) {
		this.patchNumber = patchNumber;
	}

	public String getRefTemplateUUID() {
		return refTemplateUUID;
	}

	public void setRefTemplateUUID(String refTemplateUUID) {
		this.refTemplateUUID = refTemplateUUID;
	}

}
