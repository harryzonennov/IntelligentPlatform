package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ISQLSepcifyAttribute;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class Warehouse extends Organization {


	public final static String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public final static String SENAME = IServiceModelConstants.Warehouse;

	public final static String FIELD_refMaterialCategory = "refMaterialCategory";

	public final static String FIELD_operationType = "operationType";

	public final static String FIELD_operationMode = "operationMode";

	public final static String FIELD_systemDefault = "systemDefault";

	public final static int OPERTYPE_SELFUSE = 1;

	public final static int OPERTYPE_LEASE = 2;

    public final static int OPERMODE_HOME = 1;

	public final static int OPERMODE_CHANNEL= 2;

	public final static int REFMAT_CATEGORY_NORMAL = 1;

	public final static int REFMAT_CATEGORY_WASTE = 2;

	protected int warehouseType;

	protected int operationType;

	protected int switchFlag;

	protected double length;

	protected double width;

	protected double height;

	protected double area;

	protected double volume;

	protected boolean hasFootStep;

	protected boolean restrictedGoodsFlag;

	protected boolean forbiddenGoodsFlag;

	protected int operationMode;

	protected int systemDefault;

	protected int refMaterialCategory;

	public Warehouse(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
		this.operationMode = OPERMODE_HOME;
		this.refMaterialCategory = REFMAT_CATEGORY_NORMAL;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	public int getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(int warehouseType) {
		this.warehouseType = warehouseType;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean getRestrictedGoodsFlag() {
		return restrictedGoodsFlag;
	}

	public void setRestrictedGoodsFlag(boolean restrictedGoodsFlag) {
		this.restrictedGoodsFlag = restrictedGoodsFlag;
	}

	public boolean getForbiddenGoodsFlag() {
		return forbiddenGoodsFlag;
	}

	public void setForbiddenGoodsFlag(boolean forbiddenGoodsFlag) {
		this.forbiddenGoodsFlag = forbiddenGoodsFlag;
	}

	public int getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(int operationMode) {
		this.operationMode = operationMode;
	}

	public boolean getHasFootStep() {
		return hasFootStep;
	}

	public void setHasFootStep(boolean hasFootStep) {
		this.hasFootStep = hasFootStep;
	}

	public int getSystemDefault() {
		return systemDefault;
	}

	public void setSystemDefault(int systemDefault) {
		this.systemDefault = systemDefault;
	}

	public int getRefMaterialCategory() {
		return refMaterialCategory;
	}

	public void setRefMaterialCategory(int refMaterialCategory) {
		this.refMaterialCategory = refMaterialCategory;
	}

}
