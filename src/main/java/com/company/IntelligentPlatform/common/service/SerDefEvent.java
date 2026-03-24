package com.company.IntelligentPlatform.common.service;

import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonInfo;

public class SerDefEvent implements IEvent {

    Date timeStamp;

    int documentType;

    String uuid;

    int status;

    ServiceEntityNode serviceEntityNode;

    List<ServiceEntityNode> seNodeList;

    LogonInfo logonInfo;

    String logonUserUUID;

    String organizationUUID;

    public SerDefEvent() {
        this.timeStamp = new Date();
    }

    public SerDefEvent(int documentType, String uuid, int status,
                       ServiceEntityNode serviceEntityNode,
                       List<ServiceEntityNode> seNodeList, LogonInfo logonInfo) {
        super();
        this.timeStamp = new Date();
        this.documentType = documentType;
        this.uuid = uuid;
        this.status = status;
        this.serviceEntityNode = serviceEntityNode;
        this.seNodeList = seNodeList;
        this.logonInfo = logonInfo;
    }

    public SerDefEvent(int documentType, String uuid, int status,
                       ServiceEntityNode serviceEntityNode,
                       List<ServiceEntityNode> seNodeList, String logonUserUUID,
                       String organizationUUID) {
        super();
        this.timeStamp = new Date();
        this.documentType = documentType;
        this.uuid = uuid;
        this.status = status;
        this.serviceEntityNode = serviceEntityNode;
        this.seNodeList = seNodeList;
        this.logonUserUUID = logonUserUUID;
        this.organizationUUID = organizationUUID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ServiceEntityNode getServiceEntityNode() {
        return serviceEntityNode;
    }

    public void setServiceEntityNode(ServiceEntityNode serviceEntityNode) {
        this.serviceEntityNode = serviceEntityNode;
    }

    public List<ServiceEntityNode> getSeNodeList() {
        return seNodeList;
    }

    public void setSeNodeList(List<ServiceEntityNode> seNodeList) {
        this.seNodeList = seNodeList;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LogonInfo getLogonInfo() {
        return logonInfo;
    }

    public void setLogonInfo(LogonInfo logonInfo) {
        this.logonInfo = logonInfo;
    }

    public String getLogonUserUUID() {
        return logonUserUUID;
    }

    public void setLogonUserUUID(String logonUserUUID) {
        this.logonUserUUID = logonUserUUID;
    }

    public String getOrganizationUUID() {
        return organizationUUID;
    }

    public void setOrganizationUUID(String organizationUUID) {
        this.organizationUUID = organizationUUID;
    }

}
