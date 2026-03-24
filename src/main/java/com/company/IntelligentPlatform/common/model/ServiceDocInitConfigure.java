package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocInitConfigure extends ServiceEntityNode {

    public String getConfigMeta() {
        return configMeta;
    }

    public void setConfigMeta(String configMeta) {
        this.configMeta = configMeta;
    }

    public static final String NODENAME = IServiceModelConstants.ServiceDocInitConfigure;

    public static final String SENAME = ServiceDocumentSetting.SENAME;

    @ISQLSepcifyAttribute(subType = ISQLSepcifyAttribute.SUBTYPE_JSON)
    protected String configMeta;

    protected String refServiceEntityName;

    protected String refNodeName;

    protected String nodeInstId;

    public ServiceDocInitConfigure() {
        super.nodeName = NODENAME;
        super.serviceEntityName = SENAME;
    }

    public String getRefServiceEntityName() {
        return refServiceEntityName;
    }

    public void setRefServiceEntityName(String refServiceEntityName) {
        this.refServiceEntityName = refServiceEntityName;
    }

    public String getRefNodeName() {
        return refNodeName;
    }

    public void setRefNodeName(String refNodeName) {
        this.refNodeName = refNodeName;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }
}
