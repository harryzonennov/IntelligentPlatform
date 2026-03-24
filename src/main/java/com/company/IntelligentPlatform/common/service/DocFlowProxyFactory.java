package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;

@Service
public class DocFlowProxyFactory {

    @Autowired
    protected DefDocApproveFlowProxy defDocApproveFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(DocFlowProxyFactory.class);

    public IDocFlowProxy getDocFlowInstance(int documentType, int actionCode) {
        return defDocApproveFlowProxy;
    }

    public void submitFlow(ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara) throws ServiceFlowRuntimeException {
        getDocFlowInstance(serviceFlowInputPara.getDocumentType(), serviceFlowInputPara.getActionCode()).submitFlow(serviceFlowInputPara);
    }
}
