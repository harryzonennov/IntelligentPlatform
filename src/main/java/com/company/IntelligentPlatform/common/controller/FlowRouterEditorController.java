package com.company.IntelligentPlatform.common.controller;

import java.lang.SuppressWarnings;
import java.util.*;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceEncodeException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.service.FlowRouterServiceModel;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Scope("session")
@Controller(value = "flowRouterEditorController")
@RequestMapping(value = "/flowRouter")
public class FlowRouterEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.FlowRouter;

    @Autowired
    protected LogonActionController logonActionController;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected FlowRouterServiceUIModelExtension flowRouterServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected FlowRouterManager flowRouterManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                FlowRouterServiceUIModel.class,
                FlowRouterServiceModel.class, AOID_RESOURCE,
                FlowRouter.NODENAME,
                FlowRouter.SENAME, (ServiceUIModelExtension) null /* TODO-LEGACY: flowRouterServiceUIModelExtension */,
                flowRouterManager
        );
    }

    private FlowRouterServiceUIModel parseToServiceUIModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("flowRouterExtendClassUIModelList", FlowRouterExtendClassUIModel.class);
        return (FlowRouterServiceUIModel) JSONObject.toBean(jsonObject,
                FlowRouterServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        FlowRouterServiceUIModel flowRouterServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                flowRouterServiceUIModel,
                flowRouterServiceUIModel.getFlowRouterUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(
                        FlowRouter.SENAME, FlowRouter.NODENAME,
                        null), ISystemActionCode.ACID_EDIT);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLockService(
            @RequestBody SimpleSEJSONRequest request) {
        return preLock(request.getUuid());
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLock(String uuid) {
        return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, flowRouterManager);
    }

    @RequestMapping(value = "/checkExtendClassValid", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkExtendClassValid(@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
        return serviceBasicUtilityController.voidExecuteWrapper(() -> {
            try {
                FlowRouter flowRouter =
                        (FlowRouter) flowRouterManager.getEntityNodeByKey(simpleSEJSONRequest.getUuid(),
                                IServiceEntityNodeFieldConstant.UUID, FlowRouter.NODENAME, logonActionController.getClient(),
                                null);
                List<String> targetUserList = flowRouterManager.calulateTargetUserUUIDList(flowRouter,
                        logonActionController.getSerialLogonInfo());
                if(ServiceCollectionsHelper.checkNullList(targetUserList)){
                    throw new ServiceFlowException(ServiceFlowException.PARA_NO_TARGET_USER,
                            flowRouter.getId());
                }
            } catch (ServiceEntityConfigureException | ServiceModuleProxyException | ServiceFlowException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            } catch (ServiceEncodeException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
                (ServiceBasicUtilityController.IServiceUIModuleExecutor<FlowRouterServiceUIModel>) (flowRouterServiceUIModel, flowRouterServiceModel) -> {
                    flowRouterManager.postLoadServiceUIModel(flowRouterServiceUIModel, logonActionController.getSerialLogonInfo());
                    return flowRouterServiceUIModel;
                }, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
                (ServiceBasicUtilityController.IServiceUIModuleExecutor<FlowRouterServiceUIModel>) (flowRouterServiceUIModel, flowRouterServiceModel) -> {
                    flowRouterManager.postLoadServiceUIModel(flowRouterServiceUIModel, logonActionController.getSerialLogonInfo());
                    return flowRouterServiceUIModel;
                }, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/getSerialParallelMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getSerialParallelMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> flowRouterManager.initSerialParallelMap(lanCode));
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
