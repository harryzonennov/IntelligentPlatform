package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO-LEGACY: import org.flowable.engine.RepositoryService;
// TODO-LEGACY: import org.flowable.engine.RuntimeService;
// TODO-LEGACY: import org.flowable.engine.TaskService;
// TODO-LEGACY: import org.flowable.engine.repository.Deployment;
// TODO-LEGACY: import org.flowable.engine.repository.DeploymentBuilder;
// TODO-LEGACY: import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;

import java.util.List;

/**
 * Manage the flowable process & process instance
 */
@Service
public class FlowableProcessManager {

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired


        protected TaskService taskService;

    @Autowired


        protected RepositoryService repositoryService;

    @Autowired


        protected RuntimeService runtimeService;

    protected Logger logger = LoggerFactory.getLogger(FlowableProcessManager.class);

    public void suspendProcessInstance(String processInstanceId){
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    public void activateProcessInstance(String processInstanceId){
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

    public void deleteInvProcessInsByBusinessKey(String businessKey){
        List<ProcessInstance> processInstanceList =
                runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
        if(ServiceCollectionsHelper.checkNullList(processInstanceList)){
            return;
        }
        // suspend them firstly
        for(ProcessInstance processInstance: processInstanceList){
            if(!processInstance.isSuspended()){
                suspendProcessInstance(processInstance.getProcessInstanceId());
                runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(), "");
            }
        }
    }

    public void deleteProcessInstance(String processInstanceId) {
        List<ProcessInstance> processInstanceList =
                runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list();
        if(!ServiceCollectionsHelper.checkNullList(processInstanceList)){
            for(ProcessInstance processInstance: processInstanceList){
                if(!processInstance.isSuspended()){
                    suspendProcessInstance(processInstance.getProcessInstanceId());
                }
            }
        }
        runtimeService.deleteProcessInstance(processInstanceId, "");
    }

    public void admDeleteAllProcessInstance(){
        List<ProcessInstance> processInstanceList =
                runtimeService.createProcessInstanceQuery().list();
        if(ServiceCollectionsHelper.checkNullList(processInstanceList)){
            return;
        }
        // suspend them firstly
        for(ProcessInstance processInstance: processInstanceList){
            if(!processInstance.isSuspended()){
                suspendProcessInstance(processInstance.getProcessInstanceId());
                runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(), "");
            }
        }
    }

    public Deployment deployDefDocFlow() {
        return deployFlow("coreFunction/process/DefDocApprove.bpmn20.xml");
    }

    public Deployment deployFlow(String filePath) {
        try {
            DeploymentBuilder deploymentBuilder = repositoryService
                    .createDeployment()
                    .addClasspathResource(filePath);
            return deploymentBuilder.deploy();
        } catch (Exception e) {
            return null;
        }
    }

}
