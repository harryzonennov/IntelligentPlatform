package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;

public interface IDocFlowProxy {

    public void submitFlow(ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara) throws ServiceFlowRuntimeException;

    public String getProcessDefId();
}
