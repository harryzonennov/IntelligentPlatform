package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO-LEGACY: import org.flowable.engine.TaskService;
// TODO-LEGACY: import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceFlowInvolveTaskProxy;
import com.company.IntelligentPlatform.common.service.SystemLogicValueCalculator;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceBasicFieldValueUnion;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxy;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceFlowRuntimeEngine {

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected FlowRouterManager flowRouterManager;

    @Autowired
    protected SystemLogicValueCalculator systemLogicValueCalculator;

    @Autowired
    protected DocFlowProxyFactory docFlowProxyFactory;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected FlowableProcessManager flowableProcessManager;

    @Autowired


        protected TaskService taskService;

    public static final String BPMN_RUNPRC_ACTIONCODE = "actionCode";

    protected Logger logger = LoggerFactory.getLogger(ServiceFlowRuntimeEngine.class);

    protected static Logger staticLogger = LoggerFactory.getLogger(ServiceFlowRuntimeEngine.class);

    public List<Task> getInvolveTaskList(String businessKey){
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
    }

    public InvolveTaskResult checkInvolveTaskResult(String businessKey, String logonUserUUID){
        List<Task> taskList = getInvolveTaskList(businessKey);
        return checkInvolveTaskResult(businessKey, logonUserUUID, taskList);
    }

    public InvolveTaskResult checkInvolveTaskResult(String businessKey, String logonUserUUID, List<Task> rawTaskList){
        if(ServiceCollectionsHelper.checkNullList(rawTaskList)){
            return new InvolveTaskResult(ServiceFlowInvolveTaskProxy.STATUS_NONE, null, null);
        }
        List<Task> hitTaskList = rawTaskList.stream().filter(task-> logonUserUUID.equals(task.getAssignee())).collect(Collectors.toList());
        if(ServiceCollectionsHelper.checkNullList(hitTaskList)){
            return new InvolveTaskResult(ServiceFlowInvolveTaskProxy.STATUS_BLOCK_OTHER, null, rawTaskList);
        } else {
            return new InvolveTaskResult(ServiceFlowInvolveTaskProxy.STATUS_HIT, hitTaskList, null);
        }
    }

    /**
     * Main Entrance to submit/start document flow, will
     * @param serviceFlowInputPara
     */
    public void submitFlow(ServiceFlowInputPara serviceFlowInputPara)  throws ServiceFlowRuntimeException {
        docFlowProxyFactory.submitFlow(serviceFlowInputPara);
    }

    public boolean defCheckBlockAndDoneTask(int documentType, String uuid, ServiceJSONRequest serviceJSONRequest,
                                            SerialLogonInfo serialLogonInfo, Integer actionCode){
        Map<String, Object> variables = new HashMap<>();
        variables.put(BPMN_RUNPRC_ACTIONCODE, actionCode);
        variables.put(LogonInfo.MODELID_LOGONINFO, serialLogonInfo);
        variables.put(ServiceJSONRequest.MODELID_REQUEST, serviceJSONRequest);
        return defCheckBlockAndDoneTask(documentType, uuid, serialLogonInfo, variables);
    }

    public boolean defCheckBlockAndDoneTask(int documentType, String uuid,
                                            SerialLogonInfo serialLogonInfo, Map<String,
                                                                  Object> variables){
        String businessKey = ServiceFlowRuntimeEngine.encodeBusinessKey(documentType,
                uuid);
        ServiceFlowRuntimeEngine.InvolveTaskResult involveTaskResult = checkInvolveTaskResult(businessKey
                , serialLogonInfo.getRefUserUUID());
        // in case just block
        if(involveTaskResult.getInvolveTaskStatus() == ServiceFlowInvolveTaskProxy.STATUS_BLOCK_OTHER){
            return false;
        }
        // in case just pass to direct action
        if(involveTaskResult.getInvolveTaskStatus() == ServiceFlowInvolveTaskProxy.STATUS_NONE){
            return true;
        }
        // In case hit: complete task, and block
        if(involveTaskResult.getInvolveTaskStatus() == ServiceFlowInvolveTaskProxy.STATUS_HIT){
            List<Task> taskList = involveTaskResult.getInvolveActiveTaskList();
            if(!ServiceCollectionsHelper.checkNullList(taskList)){
                for(Task task: taskList){
                    taskService.complete(task.getId(), variables);
                }
            }
        }
        return false;
    }

    public void clearInvolveProcessIns(
                                   int documentType, String uuid){
        String businessKey = ServiceFlowRuntimeEngine.encodeBusinessKey(documentType,
                uuid);
        flowableProcessManager.deleteInvProcessInsByBusinessKey(businessKey);
    }

    public boolean checkByEachModel(ServiceFlowModelServiceModel serviceFlowModelServiceModel,
                               ServiceUIModule serviceUIModule){
        List<ServiceFlowCondGroupServiceModel> serviceFlowCondGroupList =
                serviceFlowModelServiceModel.getServiceFlowCondGroupList();
        if(ServiceCollectionsHelper.checkNullList(serviceFlowCondGroupList)){
            return false;
        }
        List<SystemLogicValueCalculator.InputGroup> inputGroupList = new ArrayList<>();
        for(ServiceFlowCondGroupServiceModel serviceFlowCondGroupServiceModel:serviceFlowCondGroupList){
            List<ServiceEntityNode> serviceFlowCondFieldList =
                    serviceFlowCondGroupServiceModel.getServiceFlowCondFieldList().stream().map(ServiceFlowCondFieldServiceModel::getServiceFlowCondField).collect(Collectors.toList());
            if(ServiceCollectionsHelper.checkNullList(serviceFlowCondFieldList)){
                continue;
            }
            ServiceFlowCondGroup serviceFlowCondGroup = serviceFlowCondGroupServiceModel.getServiceFlowCondGroup();
            SystemLogicValueCalculator.InputGroup inputGroup =
                    new SystemLogicValueCalculator.InputGroup(serviceFlowCondGroup.getUuid(),
                            serviceFlowCondGroup.getInnerLogicOperator(), serviceFlowCondGroup.getExternalLogicOperator());
            List<SystemLogicValueCalculator.InputField> inputFieldList = new ArrayList<>();
            for(ServiceEntityNode serviceEntityNode: serviceFlowCondFieldList){
                ServiceFlowCondField serviceFlowCondField = (ServiceFlowCondField) serviceEntityNode;
                try {
                    ServiceBasicFieldValueUnion serviceBasicFieldValueUnion = getServiceUIModelValue(serviceUIModule,
                            serviceFlowCondField.getNodeInstId(), serviceFlowCondField.getFieldName());
                    Object fieldValue = serviceBasicFieldValueUnion.getValue();
                    SystemLogicValueCalculator.InputField inputField =
                            new SystemLogicValueCalculator.InputField(fieldValue,
                    serviceFlowCondField.getTargetValue(), serviceFlowCondField.getValueOperator(),
                                    serviceBasicFieldValueUnion.getField());
                    inputFieldList.add(inputField);
                }catch(IllegalAccessException e){
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, serviceFlowCondField.getFieldName()));
                }
            }
            if(ServiceCollectionsHelper.checkNullList(inputFieldList)){
                return false;
            }
            inputGroup.setInputFieldList(inputFieldList);
            inputGroupList.add(inputGroup);
        }
        /*
         * [Step3] Execute check
         */
        return systemLogicValueCalculator.executeBatch(inputGroupList);
    }

    public static ServiceBasicFieldValueUnion getServiceUIModelValue(ServiceUIModule serviceUIModule, String nodeInstId, String fieldName) throws IllegalAccessException {
        ServiceBasicFieldValueUnion serviceBasicFieldValueUnion = ServiceUIModuleProxy.getUIModelNodeFieldValue(serviceUIModule,
                nodeInstId);
        Object rawValue = serviceBasicFieldValueUnion.getValue();
        if(rawValue != null){
            if(serviceBasicFieldValueUnion.getField().getType().isAssignableFrom(List.class)){
                // List type, just mapping with field
                List rawValueList = (List) rawValue;
                List fieldValueList = new ArrayList();
                Field singleValueField = null;
                for(Object valueObject: rawValueList){
                    if(valueObject == null){
                        continue;
                    }
                    try {
                        ServiceBasicFieldValueUnion tmpFieldValueUnion =
                                ServiceEntityFieldsHelper.getObjectFieldValueUnion(valueObject, fieldName);
                        Object fieldValue = tmpFieldValueUnion.getValue();
                        if(fieldValue != null){
                            fieldValueList.add(fieldValue);
                        }
                        singleValueField = tmpFieldValueUnion.getField();
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        staticLogger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                }
                return new ServiceBasicFieldValueUnion(singleValueField, fieldValueList);
            } else {
                // Non-list, just return value directly
                try {
                    return ServiceEntityFieldsHelper.getObjectFieldValueUnion(rawValue, fieldName);
                } catch (NoSuchFieldException ignored) {
                }
            }
        }
        return null;
    }

    /**
     * Pluck List of Service UI Model to List of Pure UI Model
     * @param listServiceUIField
     * @param parentServiceUIModel
     * @param nodeInstId
     * @return
     * @throws IllegalAccessException
     */
    private List<?> pluckToUIModelList(Field listServiceUIField, ServiceUIModule parentServiceUIModel,
                                    String nodeInstId) throws IllegalAccessException {
        listServiceUIField.setAccessible(true);
        List<?> subListValue = (List<?>) listServiceUIField
                .get(parentServiceUIModel);
        if (ServiceCollectionsHelper.checkNullList(subListValue)) {
            return null;
        }
        List resultList = new ArrayList<>();
        for (Object object : subListValue) {
            ServiceUIModule subServiceUIModule = (ServiceUIModule) object;
            Class<?> subServiceUIModuleClass = ServiceEntityFieldsHelper
                    .getListSubType(listServiceUIField);
            Field coreField = ServiceUIModuleProxy.getUIModuleFieldByNodeInstId(subServiceUIModuleClass,
                    nodeInstId);
            if(coreField == null){
                // record and continue;
                logger.error(nodeInstId + " can't find field");
            }
            coreField.setAccessible(true);
            resultList.add(coreField.get(subServiceUIModule));
        }
        return resultList;
    }

    public static String encodeBusinessKey(int documentType, String uuid){
        return ServiceEntityStringHelper.convListToString(ServiceCollectionsHelper.asList(Integer.toString(documentType),
                uuid));
    }

    public static FlowBusinessKey decodeBusinessKey(String businessKey){
        List<String> strList = ServiceEntityStringHelper.convStringToStrList(businessKey);
        assert strList != null;
        int documentType = Integer.parseInt(strList.get(0));
        String uuid = strList.get(1);
        return new FlowBusinessKey(documentType, uuid);
    }

    public Map<Integer, List<ServiceEntityNode>> getDocumentListBatchByBusinessKey(List<String> businessKeyList,
                                                                                   String client){
        if(ServiceCollectionsHelper.checkNullList(businessKeyList)){
            return null;
        }
        Map<Integer, List<String>> docUUIDMap = new HashMap<>();
        for(String businessKey: businessKeyList){
            FlowBusinessKey flowBusinessKey = decodeBusinessKey(businessKey);
            if(docUUIDMap.containsKey(flowBusinessKey.getDocumentType())){
                List<String> docUUIDList = docUUIDMap.get(flowBusinessKey.getDocumentType());
                ServiceCollectionsHelper.mergeToList(docUUIDList, flowBusinessKey.getUuid());
                docUUIDMap.put(flowBusinessKey.getDocumentType(), docUUIDList);
            } else {
                docUUIDMap.put(flowBusinessKey.getDocumentType(), ServiceCollectionsHelper.asList(flowBusinessKey.getUuid()));
            }
        }
        Map<Integer, List<ServiceEntityNode>> resultMap = new HashMap<>();
        Set<Integer> keySet = docUUIDMap.keySet();
        for(Integer documentType: keySet ){
            List<ServiceEntityNode> documentList = docFlowProxy.getDirectDocContentNodeList(documentType,
                    docUUIDMap.get(documentType), client);
            if(!ServiceCollectionsHelper.checkNullList(documentList)){
                resultMap.put(documentType, documentList);
            }
        }
        return resultMap;
    }

    public static class InvolveTaskResult{

        private int involveTaskStatus;

        private List<Task> involveActiveTaskList;

        private List<Task> blockByOtherTaskList;

        public InvolveTaskResult() {
        }

        public InvolveTaskResult(int involveTaskStatus, List<Task> involveActiveTaskList,
                                 List<Task> blockByOtherTaskList) {
            this.involveTaskStatus = involveTaskStatus;
            this.involveActiveTaskList = involveActiveTaskList;
            this.blockByOtherTaskList = blockByOtherTaskList;
        }

        public int getInvolveTaskStatus() {
            return involveTaskStatus;
        }

        public void setInvolveTaskStatus(int involveTaskStatus) {
            this.involveTaskStatus = involveTaskStatus;
        }

        public List<Task> getInvolveActiveTaskList() {
            return involveActiveTaskList;
        }

        public void setInvolveActiveTaskList(List<Task> involveActiveTaskList) {
            this.involveActiveTaskList = involveActiveTaskList;
        }

        public List<Task> getBlockByOtherTaskList() {
            return blockByOtherTaskList;
        }

        public void setBlockByOtherTaskList(List<Task> blockByOtherTaskList) {
            this.blockByOtherTaskList = blockByOtherTaskList;
        }
    }

    public static class FlowBusinessKey{

        public FlowBusinessKey() {
        }

        public FlowBusinessKey(int documentType, String uuid) {
            this.documentType = documentType;
            this.uuid = uuid;
        }

        private int documentType;

        private String uuid;

        public int getDocumentType() {
            return documentType;
        }

        public void setDocumentType(int documentType) {
            this.documentType = documentType;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

    }

    public static class ServiceFlowInputPara{

        private ServiceUIModule serviceUIModule;

        private int documentType;

        private String uuid;

        private int actionCode;

        private LogonInfo logonInfo;

        private SerialLogonInfo serialLogonInfo;

        public ServiceFlowInputPara() {
        }

        public ServiceFlowInputPara(ServiceUIModule serviceUIModule, int documentType, String uuid, int actionCode,
                                    LogonInfo logonInfo) {
            this.serviceUIModule = serviceUIModule;
            this.documentType = documentType;
            this.uuid = uuid;
            this.actionCode = actionCode;
            this.logonInfo = logonInfo;
        }

        public ServiceFlowInputPara(ServiceUIModule serviceUIModule, int documentType, String uuid, int actionCode,
                                    SerialLogonInfo serialLogonInfo) {
            this.serviceUIModule = serviceUIModule;
            this.documentType = documentType;
            this.uuid = uuid;
            this.actionCode = actionCode;
            this.serialLogonInfo = serialLogonInfo;
        }

        public ServiceUIModule getServiceUIModule() {
            return serviceUIModule;
        }

        public void setServiceUIModule(ServiceUIModule serviceUIModule) {
            this.serviceUIModule = serviceUIModule;
        }

        public int getDocumentType() {
            return documentType;
        }

        public void setDocumentType(int documentType) {
            this.documentType = documentType;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getActionCode() {
            return actionCode;
        }

        public void setActionCode(int actionCode) {
            this.actionCode = actionCode;
        }

        public LogonInfo getLogonInfo() {
            return logonInfo;
        }

        public void setLogonInfo(LogonInfo logonInfo) {
            this.logonInfo = logonInfo;
        }

        public SerialLogonInfo getSerialLogonInfo() {
            return serialLogonInfo;
        }

        public void setSerialLogonInfo(SerialLogonInfo serialLogonInfo) {
            this.serialLogonInfo = serialLogonInfo;
        }
    }

}
