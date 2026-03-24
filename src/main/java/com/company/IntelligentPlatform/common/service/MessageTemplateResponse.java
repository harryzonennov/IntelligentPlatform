package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

public class MessageTemplateResponse {

    protected String templateUUID;

    protected String templateId;

    protected String templateName;

    protected String messageTitle;

    protected String navigationSourceId;

    protected String actionCode;

    protected int documentType;

    protected String documentTypeValue;

    protected String messageContent;

    protected int messageLevelCode;

    protected String messageLevelCodeValue;

    protected int dataNum;

    protected List<ServiceEntityNode> rawSEList;

    public MessageTemplateResponse() {
    }

    public MessageTemplateResponse(String templateUUID, String templateId, String templateName,
                                   String navigationSourceId) {
        this.templateUUID = templateUUID;
        this.templateId = templateId;
        this.templateName = templateName;
        this.navigationSourceId = navigationSourceId;
    }

    public String getTemplateUUID() {
        return templateUUID;
    }

    public void setTemplateUUID(String templateUUID) {
        this.templateUUID = templateUUID;
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

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getNavigationSourceId() {
        return navigationSourceId;
    }

    public void setNavigationSourceId(String navigationSourceId) {
        this.navigationSourceId = navigationSourceId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
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

    public void setMessageLevelCode(int messageLevelCode) {
        this.messageLevelCode = messageLevelCode;
    }

    public String getMessageLevelCodeValue() {
        return messageLevelCodeValue;
    }

    public void setMessageLevelCodeValue(String messageLevelCodeValue) {
        this.messageLevelCodeValue = messageLevelCodeValue;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }

    public List<ServiceEntityNode> getRawSEList() {
        return rawSEList;
    }

    public void setRawSEList(List<ServiceEntityNode> rawSEList) {
        this.rawSEList = rawSEList;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeValue() {
        return documentTypeValue;
    }

    public void setDocumentTypeValue(String documentTypeValue) {
        this.documentTypeValue = documentTypeValue;
    }
}
