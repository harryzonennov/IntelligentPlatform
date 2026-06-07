package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.ServiceFlowRuntimeException;

public interface IDocFlowProxy {

    public void submitFlow(ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara) throws ServiceFlowRuntimeException;

    public String getProcessDefId();
}
