package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * SystemConfigureElemen UI Model
 ** 
 * @author
 * @date Sat Aug 15 13:58:52 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class SystemConfigureElementSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = SystemConfigureElement.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureElement.NODENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = SystemConfigureElement.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureElement.NODENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "scenarioMode", nodeName = SystemConfigureElement.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureElement.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureElementSearch_scenarioMode", valueFieldName = "")
	protected int scenarioMode;
	

	@BSearchFieldConfig(fieldName = "elementType", nodeName = SystemConfigureElement.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureElement.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureElementSearch_elementType", valueFieldName = "")
	protected int elementType;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = SystemConfigureCategory.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureCategory.SENAME)
	protected String categoryID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = SystemConfigureCategory.NODENAME, seName = SystemConfigureCategory.SENAME, nodeInstID = SystemConfigureCategory.SENAME)
	protected String categoryName;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = SystemConfigureResource.NODENAME, seName = SystemConfigureResource.SENAME, nodeInstID = SystemConfigureResource.NODENAME)
	protected String resourceID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = SystemConfigureResource.NODENAME, seName = SystemConfigureResource.SENAME, nodeInstID = SystemConfigureResource.NODENAME)
	protected String resourceName;
	
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

	public int getElementType() {
		return elementType;
	}

	public void setElementType(int elementType) {
		this.elementType = elementType;
	}	

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getResourceID() {
		return resourceID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
