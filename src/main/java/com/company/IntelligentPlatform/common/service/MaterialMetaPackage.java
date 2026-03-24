package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Meta package class to contain the meta data
 * @author Zhang,Hang
 *
 */
public class MaterialMetaPackage {
	
	protected Map<Integer, String> materialCategoryMap;
	
	protected Map<Integer, String> supplyTypeMap;

	protected Map<Integer, String> switchFlagMap;
	
	protected Map<Integer, String> operationModeMap;
	
	protected List<ServiceEntityNode> standardMaterialUnitList;

	public MaterialMetaPackage() {
		super();
	}
	
	public MaterialMetaPackage(Map<Integer, String> materialCategoryMap,
			Map<Integer, String> supplyTypeMap,
			Map<Integer, String> switchFlagMap,
			Map<Integer, String> operationModeMap,
			List<ServiceEntityNode> standardMaterialUnitList) {
		super();
		this.materialCategoryMap = materialCategoryMap;
		this.supplyTypeMap = supplyTypeMap;
		this.switchFlagMap = switchFlagMap;
		this.operationModeMap = operationModeMap;
		this.standardMaterialUnitList = standardMaterialUnitList;
	}

	public Map<Integer, String> getMaterialCategoryMap() {
		return materialCategoryMap;
	}

	public void setMaterialCategoryMap(Map<Integer, String> materialCategoryMap) {
		this.materialCategoryMap = materialCategoryMap;
	}

	public Map<Integer, String> getSupplyTypeMap() {
		return supplyTypeMap;
	}

	public void setSupplyTypeMap(Map<Integer, String> supplyTypeMap) {
		this.supplyTypeMap = supplyTypeMap;
	}

	public Map<Integer, String> getSwitchFlagMap() {
		return switchFlagMap;
	}

	public void setSwitchFlagMap(Map<Integer, String> switchFlagMap) {
		this.switchFlagMap = switchFlagMap;
	}

	public Map<Integer, String> getOperationModeMap() {
		return operationModeMap;
	}

	public void setOperationModeMap(Map<Integer, String> operationModeMap) {
		this.operationModeMap = operationModeMap;
	}

	public List<ServiceEntityNode> getStandardMaterialUnitList() {
		return standardMaterialUnitList;
	}

	public void setStandardMaterialUnitList(
			List<ServiceEntityNode> standardMaterialUnitList) {
		this.standardMaterialUnitList = standardMaterialUnitList;
	}

}
