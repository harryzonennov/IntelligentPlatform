package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

public class MessageTemplateUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "searchDataUrl", seName = MessageTemplate.SENAME, nodeName = MessageTemplate.NODENAME, nodeInstID = MessageTemplate.SENAME)
	protected String searchDataUrl;

	@ISEUIModelMapping(fieldName = "processIndex", seName = MessageTemplate.SENAME, nodeName = MessageTemplate.NODENAME, nodeInstID = MessageTemplate.SENAME)
	protected int processIndex;

	@ISEUIModelMapping(fieldName = "handlerClass", seName = MessageTemplate.SENAME, nodeName = MessageTemplate.NODENAME, nodeInstID = MessageTemplate.SENAME)
	protected String handlerClass;

	@ISEUIModelMapping(fieldName = "searchModelClass", seName = MessageTemplate.SENAME, nodeName = MessageTemplate.NODENAME, nodeInstID = MessageTemplate.SENAME)
	protected String searchModelClass;
	
	protected String searchModelLabel;

	protected String searchModelName;

	protected String referenceModelName;

	protected String navigationSourceId;

	protected String searchProxyName;

	protected String searchProxyLabel;

	protected String refProxyConfigUUID;

	protected String messageTitle;

	protected String messageContent;

	protected String actionCodeArray;

	protected int messageLevelCode;

	protected String messageLevelCodeValue;

	public String getSearchDataUrl() {
		return this.searchDataUrl;
	}

	public void setSearchDataUrl(String searchDataUrl) {
		this.searchDataUrl = searchDataUrl;
	}

	public int getProcessIndex() {
		return this.processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getHandlerClass() {
		return this.handlerClass;
	}

	public void setHandlerClass(String handlerClass) {
		this.handlerClass = handlerClass;
	}

	public String getSearchModelClass() {
		return this.searchModelClass;
	}

	public void setSearchModelClass(String searchModelClass) {
		this.searchModelClass = searchModelClass;
	}

	public String getSearchModelName() {
		return searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public String getSearchModelLabel() {
		return searchModelLabel;
	}

	public void setSearchModelLabel(String searchModelLabel) {
		this.searchModelLabel = searchModelLabel;
	}

	public String getSearchProxyLabel() {
		return searchProxyLabel;
	}

	public void setSearchProxyLabel(String searchProxyLabel) {
		this.searchProxyLabel = searchProxyLabel;
	}

	public String getReferenceModelName() {
		return referenceModelName;
	}

	public void setReferenceModelName(String referenceModelName) {
		this.referenceModelName = referenceModelName;
	}

	public String getNavigationSourceId() {
		return navigationSourceId;
	}

	public void setNavigationSourceId(String navigationSourceId) {
		this.navigationSourceId = navigationSourceId;
	}

	public String getSearchProxyName() {
		return searchProxyName;
	}

	public void setSearchProxyName(String searchProxyName) {
		this.searchProxyName = searchProxyName;
	}

	public String getRefProxyConfigUUID() {
		return refProxyConfigUUID;
	}

	public void setRefProxyConfigUUID(String refProxyConfigUUID) {
		this.refProxyConfigUUID = refProxyConfigUUID;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getActionCodeArray() {
		return actionCodeArray;
	}

	public void setActionCodeArray(String actionCodeArray) {
		this.actionCodeArray = actionCodeArray;
	}

	public int getMessageLevelCode() {
		return messageLevelCode;
	}

	public void setMessageLevelCode(int messageLevelCode) {
		this.messageLevelCode = messageLevelCode;
	}

	public String getMessageLevelCodeValue() {
		return messageLevelCodeValue;
	}

	public void setMessageLevelCodeValue(String messageLevelCodeValue) {
		this.messageLevelCodeValue = messageLevelCodeValue;
	}
}
