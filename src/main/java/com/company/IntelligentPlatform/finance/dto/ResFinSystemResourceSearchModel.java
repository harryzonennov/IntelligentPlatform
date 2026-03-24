package com.company.IntelligentPlatform.finance.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * systemResource UI Model
 ** 
 * @author
 * @date Sun Jul 13 18:36:07 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class ResFinSystemResourceSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = "ROOT", seName = "SystemResource", nodeInstID = "SystemResource")
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = "ROOT", seName = "SystemResource", nodeInstID = "SystemResource")
	protected String name;

	@BSearchFieldConfig(fieldName = "viewType", nodeName = "ROOT", seName = "SystemResource", nodeInstID = "SystemResource")
	protected String viewType;

	@BSearchFieldConfig(fieldName = "enableFlag", nodeName = "ROOT", seName = "AuthorizationObject", nodeInstID = "AuthorizationObject")
	protected boolean enableFlag;

	@BSearchFieldConfig(fieldName = "authorizationObjectId", nodeName = "ROOT", seName = "AuthorizationObject", nodeInstID = "AuthorizationObject")
	protected String authorizationObjectId;

	@BSearchFieldConfig(fieldName = "authorizationObjectName", nodeName = "ROOT", seName = "AuthorizationObject", nodeInstID = "AuthorizationObject")
	protected String authorizationObjectName;
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

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public boolean isEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getAuthorizationObjectId() {
		return authorizationObjectId;
	}

	public void setAuthorizationObjectId(String authorizationObjectId) {
		this.authorizationObjectId = authorizationObjectId;
	}

	public String getAuthorizationObjectName() {
		return authorizationObjectName;
	}

	public void setAuthorizationObjectName(String authorizationObjectName) {
		this.authorizationObjectName = authorizationObjectName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
