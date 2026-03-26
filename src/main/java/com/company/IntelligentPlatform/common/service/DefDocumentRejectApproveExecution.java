package com.company.IntelligentPlatform.common.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

@Service
public class DefDocumentRejectApproveExecution implements JavaDelegate {

    @Autowired
    protected DefDocApproveFlowProxy defDocApproveFlowProxy;

    @Override
    public void execute(DelegateExecution execution){
        String businessKey = execution.getProcessInstanceBusinessKey();
        SerialLogonInfo serialLogonInfo = execution.getVariable(LogonInfo.MODELID_LOGONINFO, SerialLogonInfo.class);
        ServiceJSONRequest serviceJSONRequest = execution.getVariable(ServiceJSONRequest.MODELID_REQUEST, ServiceJSONRequest.class);
        defDocApproveFlowProxy.rejectApproveEnd(businessKey, serialLogonInfo, serviceJSONRequest);
    }
}
