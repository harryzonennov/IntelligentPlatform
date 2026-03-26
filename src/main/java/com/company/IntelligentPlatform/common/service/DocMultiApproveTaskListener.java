package com.company.IntelligentPlatform.common.service;

import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

public class DocMultiApproveTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        Integer targetActionCode = (Integer) delegateTask.getVariable(ServiceFlowRuntimeEngine.BPMN_RUNPRC_ACTIONCODE);
        Object approveVar = delegateTask.getVariable(DefDocApproveFlowProxy.BPMN_RUNPRO_APPROVE);
        Object rejectApproveVar = delegateTask.getVariable(DefDocApproveFlowProxy.BPMN_RUNPRO_REJECTAPPROVE);
        int approveCount = approveVar == null? 0: (int)approveVar;
        int rejectCount = rejectApproveVar == null? 0: (int)rejectApproveVar;
        SerialLogonInfo serialLogonInfo = delegateTask.getVariable(LogonInfo.MODELID_LOGONINFO, SerialLogonInfo.class);
        if(targetActionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE){
            delegateTask.setVariable(DefDocApproveFlowProxy.BPMN_RUNPRO_APPROVE, approveCount + 1);
        }
        if(targetActionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE){
            delegateTask.setVariable(DefDocApproveFlowProxy.BPMN_RUNPRO_REJECTAPPROVE, rejectCount + 1);
        }
        delegateTask.setVariable(LogonInfo.MODELID_LOGONINFO, serialLogonInfo);
    }
}
