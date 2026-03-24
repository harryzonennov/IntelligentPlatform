package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

import java.util.Date;

/**
 * MaterialStockKeepUnit UI Model
 ** 
 * @author
 * @date Tue Sep 01 22:05:20 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class MaterialStockKeepUnitSearchSubModel extends SEUIComModel {

	protected String refMaterialUUID;

	protected int materialCategory;

	protected String mainProductionPlace;	

	protected int switchFlag;

	protected String supplierName;

	protected String packageStandard;

	protected String productionBatchNumber;
	
	@BSearchFieldConfig(fieldName = "productionDate", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date productionDateHigh;
	
	@BSearchFieldConfig(fieldName = "productionDate", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date productionDateLow;
	
	@BSearchFieldConfig(fieldName = "traceLevel", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int traceLevel;
	
	@BSearchFieldConfig(fieldName = "traceMode", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int traceMode;
	
	@BSearchFieldConfig(fieldName = "operationMode", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int operationMode;

	@BSearchFieldConfig(fieldName = "qualityInspectFlag", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int qualityInspectFlag;
	
	@BSearchFieldConfig(fieldName = "supplyType", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected int supplyType;

	public String getRefMaterialUUID() {
		return refMaterialUUID;
	}

	public void setRefMaterialUUID(String refMaterialUUID) {
		this.refMaterialUUID = refMaterialUUID;
	}

	public int getMaterialCategory() {
		return materialCategory;
	}

	public void setMaterialCategory(int materialCategory) {
		this.materialCategory = materialCategory;
	}

	public String getMainProductionPlace() {
		return mainProductionPlace;
	}

	public void setMainProductionPlace(String mainProductionPlace) {
		this.mainProductionPlace = mainProductionPlace;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public Date getProductionDateHigh() {
		return productionDateHigh;
	}

	public void setProductionDateHigh(Date productionDateHigh) {
		this.productionDateHigh = productionDateHigh;
	}

	public Date getProductionDateLow() {
		return productionDateLow;
	}

	public void setProductionDateLow(Date productionDateLow) {
		this.productionDateLow = productionDateLow;
	}

	public int getTraceLevel() {
		return traceLevel;
	}

	public void setTraceLevel(int traceLevel) {
		this.traceLevel = traceLevel;
	}

	public int getTraceMode() {
		return traceMode;
	}

	public void setTraceMode(int traceMode) {
		this.traceMode = traceMode;
	}

	public int getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(int operationMode) {
		this.operationMode = operationMode;
	}

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

	public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}
}
