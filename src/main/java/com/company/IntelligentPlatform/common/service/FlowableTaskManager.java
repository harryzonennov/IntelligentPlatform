package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.FlowableProcessInsUIModel;
import com.company.IntelligentPlatform.common.controller.FlowableTaskUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FlowableTaskManager {

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected RuntimeService runtimeService;

    protected Logger logger = LoggerFactory.getLogger(FlowableTaskManager.class);

    public List<FlowableTaskUIModel> getModuleListCore(List<Task> rawList, LogonInfo logonInfo) throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, ServiceModuleProxyException {
        List<FlowableTaskUIModel> flowableTaskUIModelList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        /*
         * Prepare raw data
         */
        List<String> processInstanceIdList = new ArrayList<>();
        List<String> refAssigneeUserUUIDList = new ArrayList<>();
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().active().list();
        for (Task task : rawList) {
            if (!ServiceEntityStringHelper.checkNullString(task.getAssignee())) {
                ServiceCollectionsHelper.mergeToList(refAssigneeUserUUIDList, task.getAssignee());
            }
            if (!ServiceEntityStringHelper.checkNullString(task.getProcessInstanceId())) {
                ServiceCollectionsHelper.mergeToList(processInstanceIdList, task.getProcessInstanceId());
            }
        }
        List<String> businessKeyList = new ArrayList<>();
        for(ProcessInstance processInstance:processInstanceList){
            if (!ServiceEntityStringHelper.checkNullString(processInstance.getBusinessKey())) {
                ServiceCollectionsHelper.mergeToList(businessKeyList, processInstance.getBusinessKey());
            }
        }
        /*
         * Get Document List Map
         */
        Map<Integer, List<ServiceEntityNode>> documentMap =
                serviceFlowRuntimeEngine.getDocumentListBatchByBusinessKey(businessKeyList, logonInfo.getClient());
        List<ServiceEntityNode> logonUserList =
                null;
        if (!ServiceCollectionsHelper.checkNullList(refAssigneeUserUUIDList)) {
            logonUserList = logonUserManager.getEntityNodeListByMultipleKey(refAssigneeUserUUIDList,
                    IServiceEntityNodeFieldConstant.UUID, LogonUser.NODENAME, logonInfo.getClient(), null);
        }
        for(Task task: rawList){
            ProcessInstance processInstance = ServiceCollectionsHelper.filterOnline(processInstanceList, processIns->{
                return processIns.getProcessInstanceId().equals(task.getProcessInstanceId());
            });
            if(processInstance == null){
                continue;
            }
            if(ServiceEntityStringHelper.checkNullString(processInstance.getBusinessKey())){
                continue;
            }
            ServiceFlowRuntimeEngine.FlowBusinessKey flowBusinessKey =
                    ServiceFlowRuntimeEngine.decodeBusinessKey(processInstance.getBusinessKey());
            ServiceEntityNode documentContent = filterOutDocument(processInstance.getBusinessKey(), documentMap);
            FlowableProcessInsUIModel flowableProcessInsUIModel =
                    this.convertToFlowableProcessUIModel(processInstance, documentContent,
                            flowBusinessKey.getDocumentType(), logonInfo);
            LogonUser logonUser = (LogonUser) ServiceCollectionsHelper.filterOnline(logonUserList, seNode->{
                return seNode.getUuid().equals(task.getAssignee());
            });
            FlowableTaskUIModel flowableTaskUIModel = this.convertToFlowableTaskUIModel(task, flowableProcessInsUIModel,
                    logonUser);
            flowableTaskUIModelList.add(flowableTaskUIModel);
        }
        return flowableTaskUIModelList;
    }

    private ServiceEntityNode filterOutDocument(String businessKey, Map<Integer, List<ServiceEntityNode>> documentMap){
        ServiceFlowRuntimeEngine.FlowBusinessKey flowBusinessKey =
                ServiceFlowRuntimeEngine.decodeBusinessKey(businessKey);
        List<ServiceEntityNode> documentList = documentMap.get(flowBusinessKey.getDocumentType());
        if(ServiceCollectionsHelper.checkNullList(documentList)){
            return null;
        }
        return ServiceCollectionsHelper.filterOnline(documentList, serviceEntityNode -> {
            return serviceEntityNode.getUuid().equals(flowBusinessKey.getUuid());
        });
    }

    public FlowableTaskUIModel convertToFlowableTaskUIModel(Task task,
                                                            FlowableProcessInsUIModel flowableProcessInsUIModel,
                                                            LogonUser logonUser){
        FlowableTaskUIModel flowableTaskUIModel = new FlowableTaskUIModel();
        flowableTaskUIModel.setTaskId(task.getId());
        if(logonUser != null){
            flowableTaskUIModel.setAssigneeUserUUID(logonUser.getUuid());
            flowableTaskUIModel.setAssigneeUserId(logonUser.getId());
            flowableTaskUIModel.setAssigneeUserName(logonUser.getName());
        }
        flowableTaskUIModel.setRefDocumentId(flowableProcessInsUIModel.getRefDocumentId());
        flowableTaskUIModel.setRefDocumentName(flowableProcessInsUIModel.getRefDocumentName());
        flowableTaskUIModel.setRefDocumentUUID(flowableProcessInsUIModel.getRefDocumentUUID());
        flowableTaskUIModel.setDocumentType(flowableProcessInsUIModel.getDocumentType());
        flowableTaskUIModel.setProcessInsId(flowableProcessInsUIModel.getProcessInsId());
        flowableTaskUIModel.setProcessDefId(flowableProcessInsUIModel.getProcessDefId());
        flowableTaskUIModel.setDocumentTypeValue(flowableProcessInsUIModel.getDocumentTypeValue());
        return flowableTaskUIModel;
    }

    public FlowableProcessInsUIModel convertToFlowableProcessUIModel(ProcessInstance processInstance,
                                                                     ServiceEntityNode documentContent,
                                                                     int documentType, LogonInfo logonInfo)  {
        FlowableProcessInsUIModel flowableProcessInsUIModel = new FlowableProcessInsUIModel();
        flowableProcessInsUIModel.setProcessInsId(processInstance.getProcessInstanceId());
        flowableProcessInsUIModel.setProcessInsName(processInstance.getName());
        flowableProcessInsUIModel.setProcessDefId(processInstance.getProcessDefinitionId());
        flowableProcessInsUIModel.setProcessDefName(processInstance.getProcessDefinitionName());
        flowableProcessInsUIModel.setDocumentType(documentType);
        try {
            Map<Integer, String> documentTypeMap = serviceDocumentComProxy.getDocumentTypeMap(true,
                    logonInfo.getLanguageCode());
            flowableProcessInsUIModel.setDocumentTypeValue(documentTypeMap.get(documentType));
        } catch (ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "documentType"));
        }
        if(documentContent != null){
            flowableProcessInsUIModel.setRefDocumentUUID(documentContent.getUuid());
            flowableProcessInsUIModel.setRefDocumentId(documentContent.getId());
            flowableProcessInsUIModel.setRefDocumentName(documentContent.getName());
        }
        return flowableProcessInsUIModel;
    }
}
