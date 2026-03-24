package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;

/**
 * Provide template model for input parameter for special node UI Model extension configuration
 */
public class DocConfigureUIModelInputPara {

    protected String seName;

    protected String nodeName;

    protected String nodeInstId;

    protected Class<?>[] convToUIMethodParas;

    protected String convToUIMethod;

    protected Class<?>[] convUIToMethodParas;

    protected String convUIToMethod;

    protected ServiceEntityManager serviceEntityManager;

    protected Object logicManager;

    public DocConfigureUIModelInputPara() {
    }

    public DocConfigureUIModelInputPara(String seName, String nodeName, String nodeInstId) {
        this.seName = seName;
        this.nodeName = nodeName;
        this.nodeInstId = nodeInstId;
    }

    public DocConfigureUIModelInputPara(String seName, String nodeName, String nodeInstId,
                                        Class<?>[] convToUIMethodParas, String convToUIMethod,
                                        Class<?>[] convUIToMethodParas, String convUIToMethod,
                                        ServiceEntityManager serviceEntityManager, Object logicManager) {
        this.seName = seName;
        this.nodeName = nodeName;
        this.nodeInstId = nodeInstId;
        this.convToUIMethodParas = convToUIMethodParas;
        this.convToUIMethod = convToUIMethod;
        this.convUIToMethodParas = convUIToMethodParas;
        this.convUIToMethod = convUIToMethod;
        this.serviceEntityManager = serviceEntityManager;
        this.logicManager = logicManager;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public Class<?>[] getConvToUIMethodParas() {
        return convToUIMethodParas;
    }

    public void setConvToUIMethodParas(Class<?>[] convToUIMethodParas) {
        this.convToUIMethodParas = convToUIMethodParas;
    }

    public String getConvToUIMethod() {
        return convToUIMethod;
    }

    public void setConvToUIMethod(String convToUIMethod) {
        this.convToUIMethod = convToUIMethod;
    }

    public Class<?>[] getConvUIToMethodParas() {
        return convUIToMethodParas;
    }

    public void setConvUIToMethodParas(Class<?>[] convUIToMethodParas) {
        this.convUIToMethodParas = convUIToMethodParas;
    }

    public String getConvUIToMethod() {
        return convUIToMethod;
    }

    public void setConvUIToMethod(String convUIToMethod) {
        this.convUIToMethod = convUIToMethod;
    }

    public ServiceEntityManager getServiceEntityManager() {
        return serviceEntityManager;
    }

    public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
        this.serviceEntityManager = serviceEntityManager;
    }

    public Object getLogicManager() {
        return logicManager;
    }

    public void setLogicManager(Object logicManager) {
        this.logicManager = logicManager;
    }
}
