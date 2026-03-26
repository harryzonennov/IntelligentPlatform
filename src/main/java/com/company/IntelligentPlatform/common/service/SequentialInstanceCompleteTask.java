package com.company.IntelligentPlatform.common.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

import java.io.Serializable;

/**
 * Execution Listener to decide weather to break and stop whole process
 */
@Service
public class SequentialInstanceCompleteTask implements Serializable {

    public boolean accessCondition(DelegateExecution execution){
        int sum = (int) execution.getVariable("nrOfInstances");
        int completeInstance = (int) execution.getVariable("nrOfCompletedInstances");
        SerialLogonInfo serialLogonInfo = execution.getVariable(LogonInfo.MODELID_LOGONINFO, SerialLogonInfo.class);
        execution.setVariable(LogonInfo.MODELID_LOGONINFO, serialLogonInfo);
        if(execution.getVariable(DefDocApproveFlowProxy.BPMN_RUNPRO_REJECTAPPROVE) != null){
            int rejectCount = (int)execution.getVariable(DefDocApproveFlowProxy.BPMN_RUNPRO_REJECTAPPROVE);
            if(rejectCount > 0 ){
                execution.setVariable(ServiceFlowRuntimeEngine.BPMN_RUNPRC_ACTIONCODE,
                        SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE);
                // break whole process
                return true;
            }
        }
        // in case no reject
        if(completeInstance < sum){
            return false;
        } else {
            execution.setVariable(ServiceFlowRuntimeEngine.BPMN_RUNPRC_ACTIONCODE, SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE);
            return true;
        }
    }
}
