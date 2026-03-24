package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.SystemResource;


public class ResFinSystemResourceUIModel extends SEUIComModel{

	@ISEUIModelMapping(fieldName = "url",seName = SystemResource.SENAME,  nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String url;

	@ISEUIModelMapping(fieldName = "absURL",seName = SystemResource.SENAME,  nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String absURL;

	@ISEUIModelMapping(fieldName = "regSEName",seName = SystemResource.SENAME,  nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String regSEName;

	@ISEUIModelMapping(fieldName = "regNodeName",seName = SystemResource.SENAME,  nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String regNodeName;

	@ISEUIModelMapping(fieldName = "viewType",seName = SystemResource.SENAME,  nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "SystemResource_viewType", valueFieldName = "viewTypeValue")
	protected int viewType;
	
	protected String viewTypeValue;

	@ISEUIModelMapping(fieldName = "uiModelClassName",seName = SystemResource.SENAME, textAreaFlag = true,  nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String uiModelClassName;
	
	@ISEUIModelMapping(fieldName = "controllerClassName",seName = SystemResource.SENAME,  textAreaFlag = true, nodeName = SystemResource.NODENAME,  nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String controllerClassName;

	@ISEUIModelMapping(fieldName = "refSimAuthorObjectUUID",seName = SystemResource.SENAME,  nodeName = SystemResource.NODENAME, hiddenFlag = true, nodeInstID = SystemResource.SENAME, secId = SystemResource.SENAME, tabId = TABID_BASIC)
	protected String refSimAuthorObjectUUID;

	@ISEUIModelMapping(fieldName = "authorizationObjectType",seName = AuthorizationObject.SENAME,  nodeName = AuthorizationObject.NODENAME,  nodeInstID = AuthorizationObject.SENAME, showOnList = false, readOnlyFlag = true, secId = AuthorizationObject.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationObject_authorizationObjectType", valueFieldName = "authorizationObjectTypeValue")
	protected int authorizationObjectType;

	@ISEUIModelMapping(seName = AuthorizationObject.SENAME,  nodeName = AuthorizationObject.NODENAME,  nodeInstID = AuthorizationObject.SENAME, showOnEditor = false, secId = AuthorizationObject.SENAME, tabId = TABID_BASIC)
	protected String authorizationObjectTypeValue;

	@ISEUIModelMapping(fieldName = "id",seName = AuthorizationObject.SENAME,  nodeName = AuthorizationObject.NODENAME,  nodeInstID = AuthorizationObject.SENAME, readOnlyFlag = true, secId = AuthorizationObject.SENAME, tabId = TABID_BASIC)
	protected String authorizationObjectId;

	@ISEUIModelMapping(fieldName = "uuid",seName = AuthorizationObject.SENAME,  nodeName = AuthorizationObject.NODENAME,  nodeInstID = AuthorizationObject.SENAME, hiddenFlag = true, searchFlag = false, readOnlyFlag = true, secId = AuthorizationObject.SENAME, tabId = TABID_BASIC)
	protected String authorizationObjectUuid;

	@ISEUIModelMapping(fieldName = "name",seName = AuthorizationObject.SENAME,  nodeName = AuthorizationObject.NODENAME,  nodeInstID = AuthorizationObject.SENAME, readOnlyFlag = true, secId = AuthorizationObject.SENAME, tabId = TABID_BASIC)
	protected String authorizationObjectName;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAbsURL() {
		return absURL;
	}

	public void setAbsURL(String absURL) {
		this.absURL = absURL;
	}

	public String getRegSEName() {
		return regSEName;
	}

	public void setRegSEName(String regSEName) {
		this.regSEName = regSEName;
	}

	public String getRegNodeName() {
		return regNodeName;
	}

	public void setRegNodeName(String regNodeName) {
		this.regNodeName = regNodeName;
	}

	public int getViewType() {
		return viewType;
	}


	public void setViewType(int viewType) {
		this.viewType = viewType;
	}


	public String getViewTypeValue() {
		return viewTypeValue;
	}


	public void setViewTypeValue(String viewTypeValue) {
		this.viewTypeValue = viewTypeValue;
	}


	public String getUiModelClassName() {
		return uiModelClassName;
	}


	public void setUiModelClassName(String uiModelClassName) {
		this.uiModelClassName = uiModelClassName;
	}


	public String getRefSimAuthorObjectUUID() {
		return refSimAuthorObjectUUID;
	}


	public void setRefSimAuthorObjectUUID(String refSimAuthorObjectUUID) {
		this.refSimAuthorObjectUUID = refSimAuthorObjectUUID;
	}


	public int getAuthorizationObjectType() {
		return authorizationObjectType;
	}


	public void setAuthorizationObjectType(int authorizationObjectType) {
		this.authorizationObjectType = authorizationObjectType;
	}


	public String getAuthorizationObjectTypeValue() {
		return authorizationObjectTypeValue;
	}


	public void setAuthorizationObjectTypeValue(String authorizationObjectTypeValue) {
		this.authorizationObjectTypeValue = authorizationObjectTypeValue;
	}

	public String getAuthorizationObjectId() {
		return authorizationObjectId;
	}


	public void setAuthorizationObjectId(String authorizationObjectId) {
		this.authorizationObjectId = authorizationObjectId;
	}


	public String getAuthorizationObjectUuid() {
		return authorizationObjectUuid;
	}


	public void setAuthorizationObjectUuid(String authorizationObjectUuid) {
		this.authorizationObjectUuid = authorizationObjectUuid;
	}


	public String getAuthorizationObjectName() {
		return authorizationObjectName;
	}


	public void setAuthorizationObjectName(String authorizationObjectName) {
		this.authorizationObjectName = authorizationObjectName;
	}


	public String getControllerClassName() {
		return controllerClassName;
	}


	public void setControllerClassName(String controllerClassName) {
		this.controllerClassName = controllerClassName;
	}

}
