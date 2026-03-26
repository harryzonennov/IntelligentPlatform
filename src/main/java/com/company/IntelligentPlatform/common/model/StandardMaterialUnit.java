package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
@Entity
@Table(name = "StandardMaterialUnit", schema = "platform")
public class StandardMaterialUnit extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.StandardMaterialUnit;

	public static final int UNIT_TYPE_STANDARD = 1;

	public static final int UNIT_TYPE_SELF = 2;

	public static final int UNIT_TYPE_SYSTEM = 3;

	public static final int CATEGORY_PACKAGE = 1;

	public static final int CATEGORY_WEIGHT = 2;

	public static final int CATEGORY_VOLUME = 3;

	public static final int CATEGORY_LENGTH = 4;

    public static final int SYSCATEGORY_PHYSICAL = 1;

	public static final int SYSCATEGORY_TRADING = 2;

	public static final String STAND_WEIGHT_ID = "kilogram";

	public static final String STAND_VOLUME_ID = "liter";

	protected String languageCode;

	protected int unitType;

	protected int unitCategory;

	protected double toReferUnitFactor;

	protected String referUnitUUID;

	protected double toReferUnitOffset;

	protected int systemCategory;

	public StandardMaterialUnit() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.unitType = UNIT_TYPE_STANDARD;
		this.unitCategory = CATEGORY_PACKAGE;
		this.systemCategory = SYSCATEGORY_TRADING;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

	public int getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(int unitCategory) {
		this.unitCategory = unitCategory;
	}

	public double getToReferUnitFactor() {
		return toReferUnitFactor;
	}

	public void setToReferUnitFactor(double toReferUnitFactor) {
		this.toReferUnitFactor = toReferUnitFactor;
	}

	public String getReferUnitUUID() {
		return referUnitUUID;
	}

	public void setReferUnitUUID(String referUnitUUID) {
		this.referUnitUUID = referUnitUUID;
	}

	public double getToReferUnitOffset() {
		return toReferUnitOffset;
	}

	public void setToReferUnitOffset(double toReferUnitOffset) {
		this.toReferUnitOffset = toReferUnitOffset;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

}
