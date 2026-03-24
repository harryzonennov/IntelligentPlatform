package com.company.IntelligentPlatform.common.service;

import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonInfo;

public interface IEvent {

    Date getTimeStamp();

    void setTimeStamp(Date timeStamp);

    ServiceEntityNode getServiceEntityNode();

    void setServiceEntityNode(ServiceEntityNode serviceEntityNode);

    List<ServiceEntityNode> getSeNodeList();

    void setSeNodeList(List<ServiceEntityNode> seNodeList);

    int getDocumentType();

    void setDocumentType(int documentType);

    String getUuid();

    void setUuid(String uuid);

    int getStatus();

    void setStatus(int status);

    LogonInfo getLogonInfo();

    void setLogonInfo(LogonInfo logonInfo);

    String getLogonUserUUID();

    void setLogonUserUUID(String logonUserUUID);

    String getOrganizationUUID();

    void setOrganizationUUID(String organizationUUID);
}
