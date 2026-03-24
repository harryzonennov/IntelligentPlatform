package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class MessageTemplate extends ServiceEntityNode {

    public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

    public static final String SENAME = IServiceModelConstants.MessageTemplate;

    public MessageTemplate() {
        super.nodeName = NODENAME;
        super.serviceEntityName = SENAME;
    }

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String searchModelClass;

    protected String searchModelName;

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_2000)
    protected String searchDataUrl;

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String handlerClass;

    protected int processIndex;

    protected String referenceModelName;

    protected String navigationSourceId;

    protected String searchProxyName;

    protected String refProxyConfigUUID;

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_200)
    protected String messageTitle;

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_2000)
    protected String messageContent;

    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String actionCodeArray;

    public String getSearchModelClass() {
        return searchModelClass;
    }

    public void setSearchModelClass(String searchModelClass) {
        this.searchModelClass = searchModelClass;
    }

    public String getSearchDataUrl() {
        return searchDataUrl;
    }

    public void setSearchDataUrl(String searchDataUrl) {
        this.searchDataUrl = searchDataUrl;
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }

    public int getProcessIndex() {
        return processIndex;
    }

    public void setProcessIndex(int processIndex) {
        this.processIndex = processIndex;
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
}
