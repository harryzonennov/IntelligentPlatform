package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Component
public class MessageTemplateSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = MessageTemplate.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = MessageTemplate.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchFieldConfig(fieldName = "messageTitle", nodeName = MessageTemplate.NODENAME, seName = MessageTemplate.SENAME, nodeInstID = MessageTemplate.SENAME)
	protected String messageTitle;

	@BSearchFieldConfig(fieldName = "messageContent", nodeName = MessageTemplate.NODENAME, seName = MessageTemplate.SENAME, nodeInstID = MessageTemplate.SENAME)
	protected String messageContent;

	@BSearchFieldConfig(fieldName = "searchModelName", nodeName = MessageTemplate.NODENAME, seName = MessageTemplate.SENAME, nodeInstID = MessageTemplate.SENAME)
	protected String searchModelName;

	@BSearchFieldConfig(fieldName = "referenceModelName", nodeName = MessageTemplate.NODENAME, seName = MessageTemplate.SENAME, nodeInstID = MessageTemplate.SENAME)
	protected String referenceModelName;

	@BSearchFieldConfig(fieldName = "navigationSourceId", nodeName = MessageTemplate.NODENAME, seName = MessageTemplate.SENAME, nodeInstID = MessageTemplate.SENAME)
	protected String navigationSourceId;

	@BSearchFieldConfig(fieldName = "searchProxyName", nodeName = MessageTemplate.NODENAME, seName = MessageTemplate.SENAME, nodeInstID = MessageTemplate.SENAME)
	protected String searchProxyName;

	public ServiceDocSearchHeaderModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
		this.headerModel = headerModel;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
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

	public String getSearchModelName() {
		return searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
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
}
