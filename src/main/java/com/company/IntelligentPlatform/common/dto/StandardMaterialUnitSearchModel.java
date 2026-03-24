package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * StandardMaterialUnit UI Model
 **
 * @author
 * @date Sun Aug 28 15:46:30 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class StandardMaterialUnitSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = StandardMaterialUnit.NODENAME, seName = StandardMaterialUnit.SENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = StandardMaterialUnit.NODENAME, seName = StandardMaterialUnit.SENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "languageCode", nodeName = StandardMaterialUnit.NODENAME, seName = StandardMaterialUnit.SENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected String languageCode;

	@BSearchFieldConfig(fieldName = "unitType", nodeName = StandardMaterialUnit.NODENAME, seName = StandardMaterialUnit.SENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected int unitType;

	@BSearchFieldConfig(fieldName = "unitCategory", nodeName = StandardMaterialUnit.NODENAME, seName = StandardMaterialUnit.SENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected int unitCategory;
	
	@BSearchFieldConfig(fieldName = "systemCategory", nodeName = StandardMaterialUnit.NODENAME, seName = StandardMaterialUnit.SENAME, nodeInstID = StandardMaterialUnit.SENAME)
	protected int systemCategory;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public int getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(int unitCategory) {
		this.unitCategory = unitCategory;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
