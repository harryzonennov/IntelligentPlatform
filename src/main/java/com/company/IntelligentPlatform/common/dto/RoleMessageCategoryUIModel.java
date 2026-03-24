package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class RoleMessageCategoryUIModel extends SEUIComModel {

    protected String refUUID;

    protected String templateId;

    protected String templateName;

    protected String searchModelName;

    protected String searchProxyName;

    protected String navigationSourceId;

    protected String messageTitle;

    protected String messageContent;

    protected int messageLevelCode;

    protected String messageLevelCodeValue;

    public String getRefUUID() {
        return refUUID;
    }

    public void setRefUUID(String refUUID) {
        this.refUUID = refUUID;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSearchModelName() {
        return searchModelName;
    }

    public void setSearchModelName(String searchModelName) {
        this.searchModelName = searchModelName;
    }

    public String getSearchProxyName() {
        return searchProxyName;
    }

    public void setSearchProxyName(String searchProxyName) {
        this.searchProxyName = searchProxyName;
    }

    public String getNavigationSourceId() {
        return navigationSourceId;
    }

    public void setNavigationSourceId(String navigationSourceId) {
        this.navigationSourceId = navigationSourceId;
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

    public int getMessageLevelCode() {
        return messageLevelCode;
    }

    public void setMessageLevelCode(int messageLevelCodeValue) {
        this.messageLevelCode = messageLevelCodeValue;
    }

    public String getMessageLevelCodeValue() {
        return messageLevelCodeValue;
    }

    public void setMessageLevelCodeValue(String messageLevelCodeValue) {
        this.messageLevelCodeValue = messageLevelCodeValue;
    }
}
