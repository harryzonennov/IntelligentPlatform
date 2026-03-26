package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.FlowRouterExtendClassUIModel;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.SystemCheckResultProxy;
import com.company.IntelligentPlatform.common.service.FlowTaskPartyHandlerRepository;
import com.company.IntelligentPlatform.common.service.IFlowTaskPartyHandler;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class FlowRouterExtendClassManager {

    @Autowired
    protected FlowRouterManager flowRouterManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected FlowTaskPartyHandlerRepository flowTaskPartyHandlerRepository;

    @Autowired
    protected SystemCheckResultProxy systemCheckResultProxy;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String METHOD_ConvFlowRouterToExtendUI = "convFlowRouterToExtendUI";

    public static final String METHOD_ConvFlowRouterExtendClassToUI = "convFlowRouterExtendClassToUI";

    public static final String METHOD_ConvUIToFlowRouterExtendClass = "convUIToFlowRouterExtendClass";

    public static final String METHOD_ConvLogonUserToUI = "convLogonUserToUI";

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), FlowRouter.NODENAME,
                        request.getUuid(), FlowRouterExtendClass.NODENAME, flowRouterManager);
        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<FlowRouter>) flowRouter -> {
            // How to get the base page header model list
            List<PageHeaderModel> pageHeaderModelList =
                    docPageHeaderModelProxy.getDocPageHeaderModelList(flowRouter, null);
            return pageHeaderModelList;
        });

        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<FlowRouterExtendClass>)
                (flowRouterExtendClass, pageHeaderModel) -> {
                    pageHeaderModel.setHeaderName(flowRouterExtendClass.getName());
                    return pageHeaderModel;
                });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public FlowRouterExtendClass initExtendClass(FlowRouterExtendClass flowRouterExtendClass) throws ServiceEntityConfigureException {
        int step = 1;
        int existedProcessIndex = getBiggestProcessIndex(flowRouterExtendClass.getRootNodeUUID(), flowRouterExtendClass.getClient());
        flowRouterExtendClass.setProcessIndex(existedProcessIndex + step);
        return flowRouterExtendClass;
    }

    public static class CheckExtClassResult {

        private int checkResult;

        private String checkResultValue;

        private String checkResultComment;

        public CheckExtClassResult() {
        }

        public CheckExtClassResult(int checkResult, String checkResultValue, String checkResultComment) {
            this.checkResult = checkResult;
            this.checkResultValue = checkResultValue;
            this.checkResultComment = checkResultComment;
        }

        public int getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(int checkResult) {
            this.checkResult = checkResult;
        }

        public String getCheckResultValue() {
            return checkResultValue;
        }

        public void setCheckResultValue(String checkResultValue) {
            this.checkResultValue = checkResultValue;
        }

        public String getCheckResultComment() {
            return checkResultComment;
        }

        public void setCheckResultComment(String checkResultComment) {
            this.checkResultComment = checkResultComment;
        }
    }

    public void postLoadUIModel(FlowRouterExtendClassUIModel flowRouterExtendClassUIModel,
                                  SerialLogonInfo serialLogonInfo) {
        try {
            CheckExtClassResult checkExtClassResult =
                    this.checkExtendClassResult(flowRouterExtendClassUIModel.getExtendClassId(),
                    serialLogonInfo);
            flowRouterExtendClassUIModel.setCheckResult(checkExtClassResult.getCheckResult());
            flowRouterExtendClassUIModel.setCheckResultValue(checkExtClassResult.getCheckResultValue());
            flowRouterExtendClassUIModel.setCheckResultComment(checkExtClassResult.getCheckResultComment());
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
        }
    }

    public CheckExtClassResult checkExtendClassResult(String extendClassId, SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceEntityInstallationException {
        try {
            String targetUser = calculateTargetUserUUID(extendClassId,
                    serialLogonInfo);
            if (targetUser == null) {
                throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER,
                        extendClassId);
            }
        } catch (ServiceFlowException e) {
            return buildErrorResult(e.getErrorMessage(), serialLogonInfo.getLanguageCode());
        }
        return buildSuccessResult("", serialLogonInfo.getLanguageCode());
    }

    private CheckExtClassResult buildSuccessResult(String comment, String languageCode) throws ServiceEntityInstallationException {
        String successResult =
                systemCheckResultProxy.getSystemCheckResultMap(languageCode).get(SystemCheckResultProxy.CHECKRESULT_OK);
        return new CheckExtClassResult(SystemCheckResultProxy.CHECKRESULT_OK, successResult, comment);
    }

    private CheckExtClassResult buildInitResult(String comment, String languageCode) throws ServiceEntityInstallationException {
        String initResult =
                systemCheckResultProxy.getSystemCheckResultMap(languageCode).get(SystemCheckResultProxy.CHECKRESULT_INIT);
        return new CheckExtClassResult(SystemCheckResultProxy.CHECKRESULT_INIT, initResult, comment);
    }

    private CheckExtClassResult buildErrorResult(String comment, String languageCode) throws ServiceEntityInstallationException {
        String errorResult =
                systemCheckResultProxy.getSystemCheckResultMap(languageCode).get(SystemCheckResultProxy.CHECKRESULT_ERROR);
        return new CheckExtClassResult(SystemCheckResultProxy.CHECKRESULT_ERROR, errorResult, comment);
    }

    /**
     * Core Logic to caculate Target User from FflowRouterExtendClass
     *
     * @param flowRouterExtendClass
     * @return
     */
    public String calculateTargetUserUUID(FlowRouter flowRouter, FlowRouterExtendClass flowRouterExtendClass,
                                          SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        /*
         * [Step1] Get Direct Assignee
         */
        if (!ServiceEntityStringHelper.checkNullString(flowRouterExtendClass.getRefDirectAssigneeUUID())) {
            return flowRouterExtendClass.getRefDirectAssigneeUUID();
        }
        /*
         * [Step2] Get Target User by
         */
        String extendClassId = flowRouterExtendClass.getExtendClassId();
        return calculateTargetUserUUID(extendClassId, serialLogonInfo);
    }

    public String calculateTargetUserUUID(String extendClassId, SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        if (ServiceEntityStringHelper.checkNullString(extendClassId)) {
            throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, extendClassId);
        }
        try {
            IFlowTaskPartyHandler flowTaskPartyHandler =
                    flowTaskPartyHandlerRepository.getFlowTaskPartyHandlerById(extendClassId);
            if (flowTaskPartyHandler == null) {
                throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, extendClassId);
            }
            LogonUser logonUser = flowTaskPartyHandler.getTargetUser(serialLogonInfo);
            if (logonUser == null) {
                throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, extendClassId);
            }
            return logonUser.getUuid();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, extendClassId);
        }
    }

    public void moveUpward(FlowRouterExtendClass flowRouterExtendClass, String logonUserUUID,
                           String organziationUUID) throws ServiceEntityConfigureException {
        if (flowRouterExtendClass.getProcessIndex() == 1) {
            return;
        }
        int curIndex = flowRouterExtendClass.getProcessIndex();
        int targetIndex = flowRouterExtendClass.getProcessIndex() - 1;
        List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(targetIndex, "processIndex");
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(flowRouterExtendClass.getParentNodeUUID(),
                IServiceEntityNodeFieldConstant.PARENTNODEUUID);
        FlowRouterExtendClass targetFlowRouterExtendClass =
                (FlowRouterExtendClass) flowRouterManager.getEntityNodeByKeyList(ServiceCollectionsHelper.asList(key1
                        , key2),
                        FlowRouterExtendClass.NODENAME, flowRouterExtendClass.getClient(), null);
        if (targetFlowRouterExtendClass == null) {
            return;
        }
        targetFlowRouterExtendClass.setProcessIndex(curIndex);
        flowRouterExtendClass.setProcessIndex(targetIndex);
        flowRouterManager.updateSENode(targetFlowRouterExtendClass, logonUserUUID, organziationUUID);
        flowRouterManager.updateSENode(flowRouterExtendClass, logonUserUUID, organziationUUID);
    }

    public int getBiggestProcessIndex(String rootNodeUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> rawSENodeList = flowRouterManager.getEntityNodeListByKey(rootNodeUUID,
                IServiceEntityNodeFieldConstant.ROOTNODEUUID, FlowRouterExtendClass.NODENAME, client, null);
        int processIndex = 0;
        if (ServiceCollectionsHelper.checkNullList(rawSENodeList)) {
            return processIndex;
        }
        for (ServiceEntityNode serviceEntityNode : rawSENodeList) {
            FlowRouterExtendClass flowRouterExtendClass = (FlowRouterExtendClass) serviceEntityNode;
            if (flowRouterExtendClass.getProcessIndex() > processIndex) {
                processIndex = flowRouterExtendClass.getProcessIndex();
            }
        }
        return processIndex;
    }

    public Map<String, String> loadExtendClassMap(String languageCode) throws ServiceEntityInstallationException {
        return flowTaskPartyHandlerRepository.loadFlowTaskPartyHandlerMap(languageCode);
    }

    public ExtendClassMeta loadExtendClassMeta(String extendClassId, String languageCode) throws ServiceEntityInstallationException {
        Map<String, String> handlerClassMap = loadExtendClassMap(languageCode);
        String extendClassName = handlerClassMap.get(extendClassId);
        return new ExtendClassMeta(extendClassId, extendClassName);
    }

    public static class ExtendClassMeta {

        private String id;

        private String name;

        public ExtendClassMeta() {
        }

        public ExtendClassMeta(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convFlowRouterExtendClassToUI(FlowRouterExtendClass flowRouterExtendClass,
                                              FlowRouterExtendClassUIModel flowRouterExtendClassUIModel) {
        convFlowRouterExtendClassToUI(flowRouterExtendClass, flowRouterExtendClassUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convFlowRouterExtendClassToUI(FlowRouterExtendClass flowRouterExtendClass,
                                              FlowRouterExtendClassUIModel flowRouterExtendClassUIModel,
                                              LogonInfo logonInfo) {
        if (flowRouterExtendClass != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(flowRouterExtendClass, flowRouterExtendClassUIModel);
            flowRouterExtendClassUIModel.setProcessIndex(flowRouterExtendClass.getProcessIndex());
            flowRouterExtendClassUIModel.setExtendClassId(flowRouterExtendClass.getExtendClassId());
            if (logonInfo != null) {
                try {
                    Map<String, String> extendClassMap = this.loadExtendClassMap(logonInfo.getLanguageCode());
                    flowRouterExtendClassUIModel.setExtendClassName(extendClassMap.get(flowRouterExtendClass.getExtendClassId()));
                    flowRouterExtendClassUIModel.setName(extendClassMap.get(flowRouterExtendClass.getExtendClassId()));
                    flowRouterExtendClassUIModel.setId(flowRouterExtendClass.getExtendClassId());
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "extendClassName"));
                }
            }
            flowRouterExtendClassUIModel.setRefDirectAssigneeUUID(flowRouterExtendClass.getRefDirectAssigneeUUID());
            flowRouterExtendClassUIModel.setExtendClassFullName(flowRouterExtendClass.getExtendClassFullName());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:flowRouterExtendClass
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToFlowRouterExtendClass(FlowRouterExtendClassUIModel flowRouterExtendClassUIModel,
                                              FlowRouterExtendClass rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(flowRouterExtendClassUIModel, rawEntity);
        rawEntity.setProcessIndex(flowRouterExtendClassUIModel.getProcessIndex());
        rawEntity.setExtendClassId(flowRouterExtendClassUIModel.getExtendClassId());
        rawEntity.setId(flowRouterExtendClassUIModel.getId());
        rawEntity.setParentNodeUUID(flowRouterExtendClassUIModel.getParentNodeUUID());
        rawEntity.setRefDirectAssigneeUUID(flowRouterExtendClassUIModel.getRefDirectAssigneeUUID());
        rawEntity.setExtendClassFullName(flowRouterExtendClassUIModel.getExtendClassFullName());
    }

    public void convFlowRouterToExtendUI(FlowRouter flowRouter,
                                         FlowRouterExtendClassUIModel flowRouterExtendClassUIModel) {
        if (flowRouter != null) {
            flowRouterExtendClassUIModel.setRootDocId(flowRouter.getId());
            flowRouterExtendClassUIModel.setRootDocName(flowRouter.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convLogonUserToUI(LogonUser logonUser, FlowRouterExtendClassUIModel flowRouterExtendClassUIModel) {
        if (logonUser != null) {
            flowRouterExtendClassUIModel.setRefDirectAssigneeName(logonUser.getName());
            flowRouterExtendClassUIModel.setRefDirectAssigneeId(logonUser.getId());
            // set property name & id;
            flowRouterExtendClassUIModel.setName(logonUser.getName());
            flowRouterExtendClassUIModel.setId(logonUser.getId());
        }
    }

}
