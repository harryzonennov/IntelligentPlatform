package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.OrganizationUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * TransSite UI Model
 ** 
 * @author
 * @date Mon Feb 11 22:36:50 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class WarehouseUIModel extends OrganizationUIModel {
	
	protected String contactEmployeeName;
	
	protected String contactEmployeeID;

	protected int switchFlag;
	
	protected String switchFlagValue;
	
	protected double length;
	
	protected double width;
	
	protected double height;
	
	protected double area;
	
	protected double volume;
	
	protected String addressOnMap;
	
	protected boolean hasFootStep;
	
	protected boolean restrictedGoodsFlag;
	
	protected boolean forbiddenGoodsFlag;
	
	@ISEDropDownResourceMapping(resouceMapping = "Warehouse_operationMode", valueFieldName = "operationModeValue")
	protected int operationMode;
	
	protected String operationModeValue;
	
    protected int errorType;
	
	protected String errorTypeValue;
	
	protected int systemDefault;
	
	protected String systemDefaultValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "Warehouse_refMaterialCategory", valueFieldName = "refMaterialCategoryValue")
	protected int refMaterialCategory;
	
	protected String refMaterialCategoryValue;	
	
	public WarehouseUIModel(){

	}

	public String getContactEmployeeName() {
		return contactEmployeeName;
	}

	public void setContactEmployeeName(String contactEmployeeName) {
		this.contactEmployeeName = contactEmployeeName;
	}

	public String getContactEmployeeID() {
		return contactEmployeeID;
	}

	public void setContactEmployeeID(String contactEmployeeID) {
		this.contactEmployeeID = contactEmployeeID;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getSwitchFlagValue() {
		return switchFlagValue;
	}

	public void setSwitchFlagValue(String switchFlagValue) {
		this.switchFlagValue = switchFlagValue;
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

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getAddressOnMap() {
		return addressOnMap;
	}

	public void setAddressOnMap(String addressOnMap) {
		this.addressOnMap = addressOnMap;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean getHasFootStep() {
		return hasFootStep;
	}

	public void setHasFootStep(boolean hasFootStep) {
		this.hasFootStep = hasFootStep;
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

	public String getOperationModeValue() {
		return operationModeValue;
	}

	public void setOperationModeValue(String operationModeValue) {
		this.operationModeValue = operationModeValue;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getErrorTypeValue() {
		return errorTypeValue;
	}

	public void setErrorTypeValue(String errorTypeValue) {
		this.errorTypeValue = errorTypeValue;
	}

	public int getSystemDefault() {
		return systemDefault;
	}

	public void setSystemDefault(int systemDefault) {
		this.systemDefault = systemDefault;
	}

	public String getSystemDefaultValue() {
		return systemDefaultValue;
	}

	public void setSystemDefaultValue(String systemDefaultValue) {
		this.systemDefaultValue = systemDefaultValue;
	}

	public int getRefMaterialCategory() {
		return refMaterialCategory;
	}

	public void setRefMaterialCategory(int refMaterialCategory) {
		this.refMaterialCategory = refMaterialCategory;
	}

	public String getRefMaterialCategoryValue() {
		return refMaterialCategoryValue;
	}

	public void setRefMaterialCategoryValue(String refMaterialCategoryValue) {
		this.refMaterialCategoryValue = refMaterialCategoryValue;
	}
	
}
