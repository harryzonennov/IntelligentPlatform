package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.controller.DocActionNodeUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocActionNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.*;

/**
 * Logic Proxy Class for manage the Doc Action Node
 *
 * @author Zhang, Hang
 */
@Service
public class DocActionNodeProxy {

    public static final String METHOD_ConvDocActionNodeToUI = "convDocActionNodeToUI";

    public static final String METHOD_ConvLogonUserToActionNodeUI = "convLogonUserToActionNodeUI";

    public static final String METHOD_ConvDocumentToActionNodeUI = "convDocumentToActionNodeUI";

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected SystemDefDocActionCodeProxy systemDefDocActionCodeProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    protected Logger logger = LoggerFactory.getLogger(DocActionNodeProxy.class);

    public static class DocActionNodeInputPara extends DocConfigureUIModelInputPara {

        protected int docActionCode;

        public DocActionNodeInputPara() {
            super();
        }

        public DocActionNodeInputPara(String seName, String nodeName, String nodeInstId,
                                      ServiceEntityManager serviceEntityManager,
                                      int docActionCode) {
            super(seName, nodeName, nodeInstId);
            this.serviceEntityManager = serviceEntityManager;
            this.docActionCode = docActionCode;
        }

        public DocActionNodeInputPara(String seName, String nodeName, String nodeInstId,
                                      Class<?>[] convToUIMethodParas, String convToUIMethod,
                                      Class<?>[] convUIToMethodParas, String convUIToMethod,
                                      ServiceEntityManager serviceEntityManager, Object logicManager,
                                      int docActionCode) {
            super(seName, nodeName, nodeInstId, convToUIMethodParas, convToUIMethod, convUIToMethodParas,
                    convUIToMethod, serviceEntityManager, logicManager);
            this.docActionCode = docActionCode;
        }

        public int getDocActionCode() {
            return docActionCode;
        }

        public void setDocActionCode(int docActionCode) {
            this.docActionCode = docActionCode;
        }
    }

    public List<ServiceEntityNode> getDocActionNodeListWrapper(int docActionCode, String nodeName,
                                                               ServiceEntityManager serviceEntityManager,
                                                               String parentNodeUUID,
                                                               String client) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(parentNodeUUID,
                IServiceEntityNodeFieldConstant.PARENTNODEUUID), new ServiceBasicKeyStructure(
                docActionCode, DocActionNode.FIELD_DOCACTIONCODE));
        return serviceEntityManager.getEntityNodeListByKeyList(keyList, nodeName, client, null);
    }

    public DocActionNode getDocActionNodeWrapper(int docActionCode, int documentType, String parentNodeUUID,
                                                 String client)
            throws ServiceEntityConfigureException, DocActionException {
        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
                docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
        return getDocActionNodeWrapper(docActionCode,
                documentContentSpecifier.getDocNodeName(),
                documentContentSpecifier.getDocumentManager(), parentNodeUUID, client);
    }

    public DocActionNode getDocActionNodeWrapper(int docActionCode, String nodeName,
                                                 ServiceEntityManager serviceEntityManager, String parentNodeUUID,
                                                 String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> allActionCodeList = this.getDocActionNodeListWrapper(docActionCode, nodeName,
                serviceEntityManager, parentNodeUUID, client);
        if (ServiceCollectionsHelper.checkNullList(allActionCodeList)) {
            return null;
        }
        for (ServiceEntityNode seNode : allActionCodeList) {
            DocActionNode tmpActionNode = (DocActionNode) seNode;
            if (tmpActionNode.getDocActionCode() == StandardSwitchProxy.SWITCH_ON) {
                return tmpActionNode;
            }
        }
        return null;
    }

    public void updateDocActionWrapper(int docActionCode, String nodeName, String refDocMatItemUUID,
                                       int documentType,
                                       ServiceEntityManager serviceEntityManager, ServiceModule serviceModule,
                                       ServiceEntityNode parentNode, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException {
        /*
         * [Step1] Trying to get the existed doc action node
         */
        DocActionNode docActionNode = getDocActionNodeWrapper(docActionCode, nodeName, serviceEntityManager,
                parentNode.getUuid(), parentNode.getClient());
        if (docActionNode != null) {
            if (docActionNode.getExecutionTime() == null) {
                // update existed
                Date executionDate = new Date();
                if(serviceModule.getServiceJSONRequest() != null){
                    Date setExecutionDate = serviceModule.getServiceJSONRequest().getExecuteDate();
                    if(setExecutionDate != null){
                        executionDate = setExecutionDate;
                    }
                }
                docActionNode.setExecutionTime(executionDate);
                docActionNode.setExecutedByUUID(logonUserUUID);
                updateMetaInfo(docActionNode, serviceModule);
                serviceEntityManager.updateSENode(docActionNode, logonUserUUID, organizationUUID);
                return;
            }
        }
        /*
         * [Step2] In other case insert new doc action
         */
        insertNewDocAction(docActionCode, nodeName, refDocMatItemUUID, documentType, serviceEntityManager,
                serviceModule,
                parentNode, logonUserUUID,
                organizationUUID);

    }

    public void initDefExecutionTime(ServiceUIModule serviceUIModule){
        ServiceJSONRequest serviceJSONRequest = serviceUIModule.getServiceJSONRequest();
        if(serviceJSONRequest == null){
            serviceJSONRequest = new ServiceJSONRequest();
            serviceUIModule.setServiceJSONRequest(serviceJSONRequest);
        }
        initDefExecutionTime(serviceJSONRequest);
    }

    public void initDefExecutionTime(ServiceJSONRequest serviceJSONRequest){
        Date executeDate = serviceJSONRequest.getExecuteDate();
        if(executeDate == null){
            serviceJSONRequest.setExecuteDate(new Date());
            ServiceUIModuleProxy.convServiceJSONRequestToUI(serviceJSONRequest);
        }
    }

    /**
     * Internal method update meta info to DocActionNode instance from serviceModule
     * @param docActionNode
     * @param serviceModule
     */
    public void updateMetaInfo(DocActionNode docActionNode, ServiceModule serviceModule){
       // check  meta information from service model
        if(serviceModule != null && serviceModule.getServiceJSONRequest() != null){
            // parse content into note
            docActionNode.setNote(serviceModule.getServiceJSONRequest().getContent());
            if(serviceModule.getServiceJSONRequest().getExecuteDate() != null){
                docActionNode.setExecutionTime(serviceModule.getServiceJSONRequest().getExecuteDate());
            }
        }
    }

    public void insertNewDocAction(int docActionCode, String nodeName,String refDocMatItemUUID,
                                   int documentType,
                                   ServiceEntityManager serviceEntityManager,ServiceModule serviceModule,
                                   ServiceEntityNode parentNode, String logonUserUUID,
                                   String organizationUUID) throws ServiceEntityConfigureException {
        /*
         * [Step1] Initialize DocActionNode instance
         */
        DocActionNode docActionNode = (DocActionNode) serviceEntityManager.newEntityNode(parentNode, nodeName);
        docActionNode.setExecutedByUUID(logonUserUUID);
        Date executionDate = new Date();
        if(serviceModule.getServiceJSONRequest() != null){
            Date setExecutionDate = serviceModule.getServiceJSONRequest().getExecuteDate();
            if(setExecutionDate != null){
                executionDate = setExecutionDate;
            }
        }
        docActionNode.setExecutionTime(executionDate);
        docActionNode.setDocActionCode(docActionCode);
        docActionNode.setRefDocumentUUID(refDocMatItemUUID);
        docActionNode.setDocumentType(documentType);
        docActionNode.setFlatNodeSwitch(StandardSwitchProxy.SWITCH_ON);
        updateMetaInfo(docActionNode, serviceModule);
        // Update info in existed Action Code
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        /*
         * [Step2] Update old action code list
         */
        List<ServiceEntityNode> sameCodeList = this.getDocActionNodeListWrapper(docActionCode, nodeName,
                serviceEntityManager, parentNode.getUuid(), parentNode.getClient());
        // Set all the old action old with action node flatNodeSwith to OFF
        if (!ServiceCollectionsHelper.checkNullList(sameCodeList)) {
            for (ServiceEntityNode seNode : sameCodeList) {
                DocActionNode tmpActionNode = (DocActionNode) seNode;
                tmpActionNode.setFlatNodeSwitch(StandardSwitchProxy.SWITCH_OFF);
            }
            serviceEntityManager.updateSENodeList(sameCodeList, logonUserUUID, organizationUUID);
        }
        /*
         * [Step3] Trigger update
         */
        serviceEntityManager.updateSENode(docActionNode, logonUserUUID, organizationUUID);
    }

    public List<ServiceEntityNode> getSubActionCodeList(String parentNodeUUID, String nodeName, int actionCode,
                                                        ServiceEntityManager serviceEntityManager,  String client) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = ServiceCollectionsHelper.asList(new
                ServiceBasicKeyStructure(parentNodeUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID));
        if(actionCode > 0){
            // in case need to filter action code
            keyList.add(new ServiceBasicKeyStructure(actionCode,
                    DocActionNode.FIELD_DOCACTIONCODE));
        }
        return serviceEntityManager.getEntityNodeListByKeyList(keyList, nodeName,
                client, null);
    }

    public List<DocActionNodeUIModel> getSubActionCodeUIModelList(String parentNodeUUID, String seName, String nodeName,
                                                                  String actionCode,
                                                        ServiceEntityManager serviceEntityManager,
                                                                  Map<Integer, String> actionCodeMap,
                                                                  LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        return getSubActionCodeUIModelList(parentNodeUUID, seName, nodeName, actionCode, docActionNode -> {
            int actionCodeInt = ServiceEntityStringHelper.checkNullString(actionCode)? 0: Integer.parseInt(actionCode);
            ServiceUIModelExtension defServiceUIModelExtension =
                    genDefServiceUIModelExtension(new DocActionNodeInputPara(seName, nodeName, nodeName,
                            serviceEntityManager, actionCodeInt));
            DocActionNodeUIModel docActionNodeUIModel = null;
            try {
                docActionNodeUIModel = (DocActionNodeUIModel) serviceEntityManager
                        .genUIModelFromUIModelExtension(
                                DocActionNodeUIModel.class,
                                defServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                docActionNode, logonInfo,
                                null);
                if(actionCodeMap != null){
                    if(actionCodeMap.get(docActionNodeUIModel.getDocActionCode()) != null){
                        docActionNodeUIModel.setDocActionCodeLabel(actionCodeMap.get(docActionNodeUIModel.getDocActionCode()));
                    }
                }
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
               logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
            return docActionNodeUIModel;
        }, serviceEntityManager, logonInfo);
    }

    public List<DocActionNodeUIModel> getSubActionCodeUIModelList(String parentNodeUUID, String seName, String nodeName,
                                                                  String actionCode, IActionNodeConvertExecutor convertCallback, ServiceEntityManager serviceEntityManager,
                                                                  LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        int actionCodeInt = ServiceEntityStringHelper.checkNullString(actionCode)? 0: Integer.parseInt(actionCode);
        List<ServiceEntityNode> rawActionCodeList = this.getSubActionCodeList(parentNodeUUID, nodeName, actionCodeInt,
                serviceEntityManager, logonInfo.getClient());
        if(ServiceCollectionsHelper.checkNullList(rawActionCodeList)){
            return null;
        }
        rawActionCodeList.sort(new ServiceEntityNodeLastUpdateTimeCompare());
        List<DocActionNodeUIModel> actionNodeUIModelList = new ArrayList<>();
        for(ServiceEntityNode serviceEntityNode:rawActionCodeList){
            DocActionNode docActionNode = (DocActionNode) serviceEntityNode;
            DocActionNodeUIModel docActionNodeUIModel = convertCallback.execute(docActionNode);
            if(docActionNodeUIModel != null){
                actionNodeUIModelList.add(docActionNodeUIModel);
            }
        }
        if(ServiceCollectionsHelper.checkNullList(actionNodeUIModelList)){
            return null;
        }
        actionNodeUIModelList.sort((actionNode1, actionNode2) -> {
            if(actionNode2.getExecutionTime() == null){
                return -1;
            }
            if(actionNode1.getExecutionTime() == null){
                return 1;
            }
            return actionNode2
                    .getExecutionTime().compareTo(
                            actionNode1.getExecutionTime());
        });
        return actionNodeUIModelList;
    }

    public ServiceUIModelExtension genDefServiceUIModelExtension(DocActionNodeInputPara docActionNodeInputPara)
            throws ServiceEntityConfigureException {
        ServiceUIModelExtension serviceUIModelExtension = new ServiceUIModelExtension();
        serviceUIModelExtension.setChildUIModelExtensions(null);
        serviceUIModelExtension.setUIModelExtensionUnion(genDefUIModelExtensionUnion(docActionNodeInputPara));
        return serviceUIModelExtension;
    }

    public List<ServiceUIModelExtensionUnion> genDefUIModelExtensionUnion(DocActionNodeInputPara docActionNodeInputPara)
            throws ServiceEntityConfigureException {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        ServiceUIModelExtensionUnion docActionNodeExtensionUnion = new ServiceUIModelExtensionUnion();
        docActionNodeExtensionUnion
                .setNodeInstId(docActionNodeInputPara.getNodeInstId());
        docActionNodeExtensionUnion
                .setNodeName(docActionNodeInputPara.getNodeName());
        UIModelNodeMapConfigure toParentModelNodeMapConfigure = new UIModelNodeMapConfigure();
        toParentModelNodeMapConfigure
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
        toParentModelNodeMapConfigure
                .setNodeInstID(docActionNodeInputPara.getNodeInstId());
        toParentModelNodeMapConfigure
                .setNodeName(docActionNodeInputPara.getNodeName());
        toParentModelNodeMapConfigure.setSeName(docActionNodeInputPara.getSeName());
        List<UIModelNodeConfigPreCondition> docActionCodeConditions = new ArrayList<>();
        UIModelNodeConfigPreCondition docActionNodeCondtion0 = new UIModelNodeConfigPreCondition();
        docActionNodeCondtion0.setFieldName(DocActionNode.FIELD_DOCACTIONCODE);
        docActionNodeCondtion0
                .setFieldValue(docActionNodeInputPara.getDocActionCode());
        docActionCodeConditions.add(docActionNodeCondtion0);
        UIModelNodeConfigPreCondition docActionNodeCondtion1 = new UIModelNodeConfigPreCondition();
        docActionNodeCondtion1.setFieldName(DocActionNode.FIELD_FLATNODE_SWITCH);
        docActionNodeCondtion1
                .setFieldValue(StandardSwitchProxy.SWITCH_ON);
        docActionCodeConditions.add(docActionNodeCondtion1);
        toParentModelNodeMapConfigure
                .setPreConditions(docActionCodeConditions);
        docActionNodeExtensionUnion
                .setToParentModelNodeMapConfigure(toParentModelNodeMapConfigure);

        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();

        // UI Model Configure of node:[ActionCodeUnion]
        uiModelNodeMapList.addAll(getDefActionNodeMapConfigureList(docActionNodeInputPara));
        uiModelNodeMapList.addAll(getDefActionExecuteByConfigureList(docActionNodeInputPara.getNodeInstId()));
        uiModelNodeMapList.addAll(getDefInvolveDocMapConfigureList(docActionNodeInputPara));

        docActionNodeExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(docActionNodeExtensionUnion);
        return resultList;
    }

    /**
     * Logic to generate Configure map for target Party
     *
     * @param docActionNodeInputPara
     * @return
     */
    public List<UIModelNodeMapConfigure> getDefActionNodeMapConfigureList(
            DocActionNodeInputPara docActionNodeInputPara) {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        // UI Model Configure of node:[TargetParty]
        UIModelNodeMapConfigure docActionNodeMap = new UIModelNodeMapConfigure();
        docActionNodeMap
                .setSeName(docActionNodeInputPara.getSeName());
        docActionNodeMap
                .setNodeName(docActionNodeInputPara.getNodeName());
        docActionNodeMap.setNodeInstID(docActionNodeInputPara.getNodeInstId());
        docActionNodeMap
                .setServiceEntityManager(docActionNodeInputPara.getServiceEntityManager());
        if (docActionNodeInputPara.getConvToUIMethodParas() == null) {
            Class<?>[] localConvToUIParas = {DocActionNode.class,
                    DocActionNodeUIModel.class};
            docActionNodeMap.setConvToUIMethodParas(localConvToUIParas);
        } else {
            docActionNodeMap.setConvToUIMethodParas(docActionNodeInputPara.getConvToUIMethodParas());
        }
        if(docActionNodeInputPara.getLogicManager() != null){
            docActionNodeMap.setLogicManager(docActionNodeInputPara.getLogicManager());
        } else {
            docActionNodeMap.setLogicManager(this);
        }
        if (docActionNodeInputPara.getConvToUIMethod() == null) {
            docActionNodeMap
                    .setConvToUIMethod(METHOD_ConvDocActionNodeToUI);
        } else {
            docActionNodeMap.setConvToUIMethod(docActionNodeInputPara.getConvToUIMethod());
        }
        if (docActionNodeInputPara.getConvUIToMethodParas() == null) {
//            Class<?>[] localConvUIToParas = {DocActionNodeUIModel.class,
//                    DocActionNode.class};
//            docActionNodeMap.setConvUIToMethodParas(localConvUIToParas);
        } else {
            docActionNodeMap.setConvUIToMethodParas(docActionNodeInputPara.getConvUIToMethodParas());
        }
        if (docActionNodeInputPara.getConvUIToMethod() == null) {
//            docActionNodeMap.setLogicManager(this);
//            docActionNodeMap
//                    .setConvUIToMethod(METHOD_ConvUIToDocActionNode);
        } else {
            docActionNodeMap.setConvUIToMethod(docActionNodeInputPara.getConvUIToMethod());
        }

        uiModelNodeMapList.add(docActionNodeMap);
        return uiModelNodeMapList;
    }

    /**
     * Logic to generate Configure map for executed by logon user
     *
     * @param baseNodeId
     * @return
     */
    public List<UIModelNodeMapConfigure> getDefActionExecuteByConfigureList(String baseNodeId) {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        // UI Model Configure of node:[TargetContact]
        UIModelNodeMapConfigure executedByMap = new UIModelNodeMapConfigure();
        executedByMap
                .setSeName(LogonUser.SENAME);
        executedByMap
                .setNodeName(LogonUser.NODENAME);
        executedByMap.setNodeInstID("DocActionNodeExecutedBy");
        executedByMap.setBaseNodeInstID(baseNodeId);
        executedByMap
                .setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
        List<SearchConfigConnectCondition> executedByConditionList = new ArrayList<>();
        SearchConfigConnectCondition executedByCondition0 = new SearchConfigConnectCondition();
        executedByCondition0.setSourceFieldName(DocActionNode.FIELD_EXECUTEBY_UUID);
        executedByCondition0
                .setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
        executedByConditionList.add(executedByCondition0);
        executedByMap.setConnectionConditions(executedByConditionList);
        executedByMap
                .setServiceEntityManager(logonUserManager);
        Class<?>[] localConvToUIParas = {LogonUser.class,
                DocActionNodeUIModel.class};
        executedByMap.setConvToUIMethodParas(localConvToUIParas);
        executedByMap.setLogicManager(this);
        executedByMap
                .setConvToUIMethod(METHOD_ConvLogonUserToActionNodeUI);
        uiModelNodeMapList.add(executedByMap);
        return uiModelNodeMapList;
    }

    /**
     * Utility Method to Convert
     */
    public void convDocActionNodeToUI(DocActionNode docActionNode,
                                      DocActionNodeUIModel docActionNodeUIModel) {
        convDocActionNodeToUI(docActionNode, docActionNodeUIModel, null);
    }

    /**
     * Utility Method to Convert
     */
    public void convDocActionNodeToUI(DocActionNode docActionNode,
                                      DocActionNodeUIModel docActionNodeUIModel, LogonInfo logonInfo) {
        DocFlowProxy.convServiceEntityNodeToUIModel(docActionNode, docActionNodeUIModel);
        docActionNodeUIModel.setProcessIndex(docActionNode.getProcessIndex());
        docActionNodeUIModel.setFlatNodeSwitch(docActionNode.getFlatNodeSwitch());
        docActionNodeUIModel.setDocActionCode(docActionNode.getDocActionCode());
        if(logonInfo != null){
            try {
                Map<Integer, String> docActionCodeMap =
                        systemDefDocActionCodeProxy.getActionCodeMap(logonInfo.getLanguageCode());
                docActionNodeUIModel.setDocActionCodeLabel(docActionCodeMap.get(docActionNode.getDocActionCode()));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
        docActionNodeUIModel.setExecutedByUUID(docActionNode.getExecutedByUUID());
        if (docActionNode.getExecutionTime() != null) {
            docActionNodeUIModel
                    .setExecutionTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
                            .format(docActionNode.getExecutionTime()));
        }
        docActionNodeUIModel.setRefDocMatItemUUID(docActionNode.getRefDocMatItemUUID());
        docActionNodeUIModel.setDocumentType(docActionNode.getDocumentType());
        if(logonInfo != null){
            try {
                String documentTypeValue = serviceDocumentComProxy
                        .getDocumentTypeValue(docActionNode.getDocumentType(), logonInfo.getLanguageCode());
                docActionNodeUIModel.setDocumentTypeValue(documentTypeValue);
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
    }

    /**
     * Utility Method to Convert
     */
    public void convUIToDocActionNode(
            DocActionNodeUIModel docActionNodeUIModel,
            DocActionNode rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(docActionNodeUIModel, rawEntity);
        rawEntity.setProcessIndex(docActionNodeUIModel.getProcessIndex());
    }

    /**
     * Utility Method to Convert Corporate Customer to Party UI
     */
    public void convLogonUserToActionNodeUI(LogonUser logonUser,
                                            DocActionNodeUIModel docActionNodeUIModel) {
        if (logonUser != null) {
            docActionNodeUIModel.setExecutedByUserName(logonUser.getName());
            docActionNodeUIModel.setExecutedByUserId(logonUser.getId());
        }
    }

    /**
     * Wrapper structure to parse action service in Controller layer
     *
     * @param <S>
     */
    public interface IActionCodeServiceWrapper<S extends ServiceModule, T extends ServiceUIModule> {

        S parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException,
                ServiceUIModuleProxyException;

        boolean preExecute(S serviceModule, int actionCode);

        void executeService(S serviceModule, LogonInfo logonInfo) throws DocActionException,
                ServiceModuleProxyException, ServiceEntityConfigureException;

        T refreshServiceUIModel(S serviceModule, String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException, LogonInfoException,
                ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException, DocActionException;

        void postHandle(T serviceUIModule, int actionCode, SerialLogonInfo logonInfo) throws DocActionException;
    }

    /**
     * Wrapper structure to parse action service in Controller layer
     *
     * @param <R>
     */
    public interface IActionCodeServiceExecutor<R extends ServiceModule, T extends ServiceUIModule> {

        R parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException,
                ServiceUIModuleProxyException;

        boolean preExecute(R serviceModule, int actionCode);

        void executeService(R serviceModule, int actionCode,  LogonInfo logonInfo) throws DocActionException,
                ServiceModuleProxyException, ServiceEntityConfigureException;

        T refreshServiceUIModel(R serviceModule, String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException, LogonInfoException,
                ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException, DocActionException;

        void postHandle(T serviceUIModule, int actionCode, SerialLogonInfo logonInfo) throws DocActionException;
    }

    public interface IActionExecute<S extends ServiceModule> {

        void executeService(S serviceModule, LogonInfo logonInfo) throws DocActionException,
                ServiceModuleProxyException, ServiceEntityConfigureException;

    }

    public interface IActionExecutor<S extends ServiceModule> {

        void executeService(S serviceModule, int actionCode, LogonInfo logonInfo) throws DocActionException,
                ServiceModuleProxyException, ServiceEntityConfigureException;

    }

    public interface IActionNodeConvertExecutor {

        DocActionNodeUIModel execute(DocActionNode docActionNode) throws ServiceEntityConfigureException;

    }

    public static boolean checkCurStatus(List<Integer> curStatusList, int status) {
        if (ServiceCollectionsHelper.checkNullList(curStatusList)) {
            // in case need to check cur status
            Integer filterResult = ServiceCollectionsHelper.filterOnline(curStatusList, temStatus -> {
                return temStatus == status;
            });
            return filterResult != null && filterResult != 0;
        } else {
            return true;
        }
    }

    /**
     * Logic to generate Configure map for target Party
     *
     * @param docActionNodeInputPara
     * @return
     */
    public List<UIModelNodeMapConfigure> getDefInvolveDocMapConfigureList(
            DocActionNodeProxy.DocActionNodeInputPara docActionNodeInputPara) throws ServiceEntityConfigureException {
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        Class<?>[] involveDocItemToUIParas = {DocumentContent.class,
                DocActionNodeUIModel.class};
        String convDocumentToUIMethod = METHOD_ConvDocumentToActionNodeUI;
        DocFlowProxy.SimpleDocConfigurePara simpleDocConfigurePara = new DocFlowProxy.SimpleDocConfigurePara(
                docActionNodeInputPara.getNodeInstId(),
                convDocumentToUIMethod,
                involveDocItemToUIParas, docActionNodeInputPara.getServiceEntityManager());
        simpleDocConfigurePara.setLogicManager(this);
        simpleDocConfigurePara
                .setDocMatItemGetCallback(rawSENode -> {
                    DocActionNode docActionNode = (DocActionNode) rawSENode;
                    int refDocumentType = docActionNode.getDocumentType();
                    ServiceEntityNode specDocMatItemNode = docFlowProxy
                            .getDefDocItemNode(refDocumentType,
                                    docActionNode.getRefDocMatItemUUID(),
                                    docActionNode.getClient());
                    return specDocMatItemNode;
                });
        uiModelNodeMapList.addAll(docFlowProxy
                .getSpecNodeMapConfigureList(simpleDocConfigurePara));
        return uiModelNodeMapList;
    }

    public void convDocumentToActionNodeUI(DocumentContent documentContent,
                                           DocActionNodeUIModel docActionNodeUIModel,
                                           LogonInfo logonInfo) {
        if (docActionNodeUIModel != null) {
            docActionNodeUIModel.setDocumentId(documentContent.getId());
            docActionNodeUIModel.setDocumentName(documentContent.getName());
            docActionNodeUIModel.setDocumentStatus(documentContent.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = null;
                try {
                    statusMap = docFlowProxy.getStatusMap(docActionNodeUIModel.getDocumentType(),
                            logonInfo.getLanguageCode());
                    if (statusMap != null) {
                        docActionNodeUIModel.setDocumentStatusValue(statusMap.get(documentContent.getStatus()));
                    }
                } catch (ServiceEntityInstallationException | DocActionException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
                }
            }
        }
    }

}
