package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.*;

/**
 * Migrated from: ThorsteinPlatform - Organization.java
 * Old table: Organization (single shared DB)
 * New table: Organization (schema: platform)
 * Hierarchy: ServiceEntityNode → Account → CorporateAccount → Organization
 */
@Entity
@Table(name = "Organization", schema = "platform")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Organization extends CorporateAccount {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.Organization;

	public static final int ORGFUNCTION_HOSTCOMPANY = 1;

	public static final int ORGFUNCTION_SUBSIDIARY = 2;

	public static final int ORGFUNCTION_SALES_DEPT = 3;

	public static final int ORGFUNCTION_PROD_DEPT = 4;

	public static final int ORGFUNCTION_DEV_DEPT = 5;

	public static final int ORGFUNCTION_TRANSSITE = 6;

	public static final int ORGFUNCTION_SUPPORT_DEPT = 7;

	public static final int ORGFUNCTION_PURCHASE = 8;

	public static final int ORGFUNCTION_FINANCE = 9;

	public static final int ORGAN_TYPE_TRANSSITE = 5;

	public static final String PARENT_ORG_UUID = "parentOrganizationUUID";

	@Column(name = "parentOrganizationUUID")
	protected String parentOrganizationUUID;

	@Column(name = "organType")
	protected int organType;

	@Column(name = "organLevel")
	protected int organLevel;

	@Column(name = "organizationFunction")
	protected int organizationFunction;

	@Column(name = "refOrganizationFunction")
	protected String refOrganizationFunction;

	@Column(name = "mainContactUUID")
	protected String mainContactUUID;

	// Cross-module refs — UUID only, no FK
	@Column(name = "refCashierUUID")
	protected String refCashierUUID;

	@Column(name = "refAccountantUUID")
	protected String refAccountantUUID;

	@Column(name = "refFinOrgUUID")
	protected String refFinOrgUUID;

	public String getParentOrganizationUUID() {
		return parentOrganizationUUID;
	}

	public void setParentOrganizationUUID(String parentOrganizationUUID) {
		this.parentOrganizationUUID = parentOrganizationUUID;
	}

	public int getOrganType() {
		return organType;
	}

	public void setOrganType(int organType) {
		this.organType = organType;
	}

	public int getOrganLevel() {
		return organLevel;
	}

	public void setOrganLevel(int organLevel) {
		this.organLevel = organLevel;
	}

	public int getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(int organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

	public String getRefOrganizationFunction() {
		return refOrganizationFunction;
	}

	public void setRefOrganizationFunction(String refOrganizationFunction) {
		this.refOrganizationFunction = refOrganizationFunction;
	}

	public String getMainContactUUID() {
		return mainContactUUID;
	}

	public void setMainContactUUID(String mainContactUUID) {
		this.mainContactUUID = mainContactUUID;
	}

	public String getRefCashierUUID() {
		return refCashierUUID;
	}

	public void setRefCashierUUID(String refCashierUUID) {
		this.refCashierUUID = refCashierUUID;
	}

	public String getRefAccountantUUID() {
		return refAccountantUUID;
	}

	public void setRefAccountantUUID(String refAccountantUUID) {
		this.refAccountantUUID = refAccountantUUID;
	}

	public String getRefFinOrgUUID() {
		return refFinOrgUUID;
	}

	public void setRefFinOrgUUID(String refFinOrgUUID) {
		this.refFinOrgUUID = refFinOrgUUID;
	}

}
