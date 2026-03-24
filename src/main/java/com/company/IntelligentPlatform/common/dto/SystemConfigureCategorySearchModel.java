package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * SystemConfigureCategory UI Model
 ** 
 * @author
 * @date Sat Aug 15 23:23:39 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class SystemConfigureCategorySearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = SystemConfigureCategory.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureCategory.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = SystemConfigureCategory.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureCategory.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "scenarioMode", nodeName = SystemConfigureCategory.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureCategory.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureCategory_scenarioMode", valueFieldName = "")
	protected int scenarioMode;
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

	public int getScenarioMode() {
		return scenarioMode;
	}

	public void setScenarioMode(int scenarioMode) {
		this.scenarioMode = scenarioMode;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
