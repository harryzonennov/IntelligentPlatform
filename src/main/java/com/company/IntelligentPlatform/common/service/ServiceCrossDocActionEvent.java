package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.io.Serializable;
import java.util.List;

/**
 * Event for cross document action
 */
public class ServiceCrossDocActionEvent implements Serializable {

    protected int sourceDocType;

    protected List<ServiceEntityNode> sourceDocMatItemList;

    protected int sourceActionCode;

    public ServiceCrossDocActionEvent() {
    }

    public ServiceCrossDocActionEvent(int sourceDocType, List<ServiceEntityNode> sourceDocMatItemList,
                                      int sourceActionCode) {
        this.sourceDocType = sourceDocType;
        this.sourceDocMatItemList = sourceDocMatItemList;
        this.sourceActionCode = sourceActionCode;
    }

    public int getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocType(int sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public List<ServiceEntityNode> getSourceDocMatItemList() {
        return sourceDocMatItemList;
    }

    public void setSourceDocMatItemList(List<ServiceEntityNode> sourceDocMatItemList) {
        this.sourceDocMatItemList = sourceDocMatItemList;
    }

    public int getSourceActionCode() {
        return sourceActionCode;
    }

    public void setSourceActionCode(int sourceActionCode) {
        this.sourceActionCode = sourceActionCode;
    }
}
