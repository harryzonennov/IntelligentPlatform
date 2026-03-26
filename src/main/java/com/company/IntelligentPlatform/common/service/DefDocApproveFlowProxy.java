package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.SystemSerialParallelProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefDocApproveFlowProxy implements IDocFlowProxy {

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected FlowRouterManager flowRouterManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected RuntimeService runtimeService;

    protected Logger logger = LoggerFactory.getLogger(DefDocApproveFlowProxy.class);

    /*
     * BPMN Constants: should be consistence with relative bpmn20.xml file
     */
    public static final String BPMN_PRC_ID = "DefDocApprove";

    public static final String BPMN_PRO_serialFlag = "serialFlag";

    public static final String BPMN_PRO_sequentialList = "sequentialList";

    public static final String BPMN_PRO_parallelList = "parallelList";

    public static final String BPMN_RUNPRO_APPROVE = "approve";

    public static final String BPMN_RUNPRO_REJECTAPPROVE = "rejectApprove";

    private ServiceFlowModelServiceModel selectMatchServiceFlow(ServiceUIModule serviceUIModule,
                                                                List<ServiceFlowModelServiceModel> serviceFlowModelServiceModelList) {
        List<ServiceFlowModelServiceModel> matchList = new ArrayList<>();
        for (ServiceFlowModelServiceModel serviceFlowModelServiceModel : serviceFlowModelServiceModelList) {
            boolean eachResult = serviceFlowRuntimeEngine.checkByEachModel(serviceFlowModelServiceModel,
                    serviceUIModule);
            if (eachResult) {
                if (ServiceEntityStringHelper.checkNullString(serviceFlowModelServiceModel.getServiceFlowModel().getRefRouterUUID())) {
                    continue;
                }
                matchList.add(serviceFlowModelServiceModel);
            }
        }
        if (ServiceCollectionsHelper.checkNullList(matchList)) {
            return null;
        }
        if (matchList.size() == 1) {
            return matchList.get(0);
        }
        // In case multiple cases matches
        int numberField = 0;
        ServiceFlowModelServiceModel matchResult = null;
        for (ServiceFlowModelServiceModel serviceFlowModelServiceModel : serviceFlowModelServiceModelList) {
            List<ServiceEntityNode> fieldList = new ArrayList<>();
            serviceFlowModelServiceModel.getServiceFlowCondGroupList().forEach(serviceFlowCondGroupServiceModel -> {
                List<ServiceEntityNode> tmpfieldList =
                        serviceFlowCondGroupServiceModel.getServiceFlowCondFieldList().stream().map(ServiceFlowCondFieldServiceModel::getServiceFlowCondField).collect(Collectors.toList());
                if (!ServiceCollectionsHelper.checkNullList(tmpfieldList)) {
                    fieldList.addAll(tmpfieldList);
                }
            });
            if(fieldList.size() > numberField){
                numberField = fieldList.size();
                matchResult = serviceFlowModelServiceModel;
            }
        }
        return matchResult;
    }

    @Override
    public void submitFlow(ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara) throws ServiceFlowRuntimeException{
        try {
            /*
             * [Step1] filter out all the service flow models with actionCode, docType,
             */
            List<ServiceFlowModelServiceModel> serviceFlowModelServiceModelList =
                    serviceFlowModelManager.loadServiceFlowModelList(serviceFlowInputPara.getDocumentType(),
                    serviceFlowInputPara.getActionCode(), serviceFlowInputPara.getSerialLogonInfo().getClient());
            if (ServiceCollectionsHelper.checkNullList(serviceFlowModelServiceModelList)) {
                // in case no doc flow definition found, just return
                return;
            }
            /*
             * [Step2] Check by each service flow model
             */
            String refRouterUUID = ServiceEntityStringHelper.EMPTYSTRING;
            String serviceFlowId = ServiceEntityStringHelper.EMPTYSTRING;
            ServiceFlowModelServiceModel matchResult =
                    this.selectMatchServiceFlow(serviceFlowInputPara.getServiceUIModule(),
                            serviceFlowModelServiceModelList);
            if(matchResult == null){
                // in case no matched condition found, just return
                return;
            }
            refRouterUUID = matchResult.getServiceFlowModel().getRefRouterUUID();
            serviceFlowId = matchResult.getServiceFlowModel().getId();
            /*
             * [Step3] get correct flow and trigger start process
             */
            FlowRouter flowRouter = (FlowRouter) flowRouterManager.getEntityNodeByKey(refRouterUUID,
                    IServiceEntityNodeFieldConstant.UUID, FlowRouter.NODENAME,
                    serviceFlowInputPara.getSerialLogonInfo().getClient(),
                    null);
            if (flowRouter == null) {
                throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_NO_FLOWROUTER, serviceFlowId);
            }
            Map<String, Object> variables = new HashMap<>();
            variables.put(BPMN_PRO_serialFlag, flowRouter.getSerialFlag());
            /*
             * [Step4] get processor list
             */
            List<String> logonUserUUIDList = null;
            try {
                logonUserUUIDList = flowRouterManager.calulateTargetUserUUIDList(flowRouter,
                        serviceFlowInputPara.getSerialLogonInfo());
            } catch (ServiceFlowException e) {
                throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_NO_TARGET_USER,
                        flowRouter.getId());
            }
            if(ServiceCollectionsHelper.checkNullList(logonUserUUIDList)){
                // in case can't get logon user list in run time
                throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER, flowRouter.getId());
            }
            if (flowRouter.getSerialFlag() == SystemSerialParallelProxy.OP_SERIAL) {
                variables.put(BPMN_PRO_sequentialList, logonUserUUIDList);
            } else {
                variables.put(BPMN_PRO_parallelList, logonUserUUIDList);
            }
            variables.put(LogonInfo.MODELID_LOGONINFO, serviceFlowInputPara.getSerialLogonInfo());
            String businessKey = ServiceFlowRuntimeEngine.encodeBusinessKey(serviceFlowInputPara.getDocumentType(),
                    serviceFlowInputPara.getUuid());
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(getProcessDefId(),
                    businessKey, variables);
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException | ServiceFlowException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    @Override
    public String getProcessDefId() {
        return BPMN_PRC_ID;
    }

    public void approveEnd(String businessKey, SerialLogonInfo serialLogonInfo, ServiceJSONRequest serviceJSONRequest) {
        ServiceFlowRuntimeEngine.FlowBusinessKey flowBusinessKey =
                ServiceFlowRuntimeEngine.decodeBusinessKey(businessKey);
        ServiceEntityManager refDocumentManager = serviceDocumentComProxy
                .getDocumentManager(flowBusinessKey.getDocumentType());
        refDocumentManager.exeFlowActionEnd(flowBusinessKey.getDocumentType(), flowBusinessKey.getUuid(),
                SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE, serviceJSONRequest, serialLogonInfo);
    }

    public void rejectApproveEnd(String businessKey, SerialLogonInfo serialLogonInfo, ServiceJSONRequest serviceJSONRequest) {
        ServiceFlowRuntimeEngine.FlowBusinessKey flowBusinessKey =
                ServiceFlowRuntimeEngine.decodeBusinessKey(businessKey);
        ServiceEntityManager refDocumentManager = serviceDocumentComProxy
                .getDocumentManager(flowBusinessKey.getDocumentType());
        refDocumentManager.exeFlowActionEnd(flowBusinessKey.getDocumentType(), flowBusinessKey.getUuid(),
                SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE, serviceJSONRequest, serialLogonInfo);
    }
}
