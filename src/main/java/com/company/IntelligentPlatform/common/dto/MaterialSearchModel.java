package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialActionLog;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Material UI Model
 ** 
 * @author
 * @date Tue Sep 01 22:05:20 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class MaterialSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = Material.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = MaterialType.SENAME)
	protected MaterialTypeSearchSubModel materialType;

	@BSearchGroupConfig(groupInstId = Material.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchFieldConfig(fieldName = "materialCategory", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected int materialCategory;

	@BSearchFieldConfig(fieldName = "mainProductionPlace", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected String mainProductionPlace;

	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected int switchFlag;

	@BSearchFieldConfig(fieldName = "mainSupplierName", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected String mainSupplierName;
	
	@BSearchFieldConfig(fieldName = "packageStandard", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected String packageStandard;

	@BSearchFieldConfig(fieldName = "supplyType", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected int supplyType;

	@BSearchFieldConfig(fieldName = "qualityInspectFlag", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected int qualityInspectFlag;

	@BSearchFieldConfig(fieldName = "operationMode", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected int operationMode;

	@BSearchFieldConfig(fieldName = "cargoType", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
	protected int cargoType;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_SUBMIT)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ACTIVE)
	protected DocActionNodeSearchModel activeBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_REINIT)
	protected DocActionNodeSearchModel reInitBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ARCHIVE)
	protected DocActionNodeSearchModel archivedBy;

	public ServiceDocSearchHeaderModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
		this.headerModel = headerModel;
	}

	public MaterialTypeSearchSubModel getMaterialType() {
		return materialType;
	}

	public void setMaterialType(MaterialTypeSearchSubModel materialType) {
		this.materialType = materialType;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
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

	public String getMainSupplierName() {
		return mainSupplierName;
	}

	public void setMainSupplierName(String mainSupplierName) {
		this.mainSupplierName = mainSupplierName;
	}

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

	public int getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(int operationMode) {
		this.operationMode = operationMode;
	}

	public DocActionNodeSearchModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public DocActionNodeSearchModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public DocActionNodeSearchModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(DocActionNodeSearchModel activeBy) {
		this.activeBy = activeBy;
	}

	public DocActionNodeSearchModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(DocActionNodeSearchModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public DocActionNodeSearchModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(DocActionNodeSearchModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public int getCargoType() {
		return cargoType;
	}

	public void setCargoType(int cargoType) {
		this.cargoType = cargoType;
	}

	public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}
}
