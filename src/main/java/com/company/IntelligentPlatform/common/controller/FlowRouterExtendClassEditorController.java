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
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.FlowRouterExtendClassManager;
import com.company.IntelligentPlatform.common.service.FlowRouterManager;
import com.company.IntelligentPlatform.common.service.FlowRouterExtendClassServiceModel;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.*;

@Scope("session")
@Controller(value = "flowRouterExtendClassEditorController")
@RequestMapping(value = "/flowRouterExtendClass")
public class FlowRouterExtendClassEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected FlowRouterExtendClassServiceUIModelExtension flowRouterExtendClassServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected FlowRouterManager flowRouterManager;

    @Autowired
    protected FlowRouterExtendClassManager flowRouterExtendClassManager;

    @Autowired
    protected LogonUserManager logonUserManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                FlowRouterExtendClassServiceUIModel.class,
                FlowRouterExtendClassServiceModel.class, AOID_RESOURCE,
                FlowRouterExtendClass.NODENAME,
                FlowRouterExtendClass.SENAME, flowRouterExtendClassServiceUIModelExtension,
                flowRouterManager
        );
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> flowRouterExtendClassManager.getPageHeaderModelList(request1, client));
    }

    @RequestMapping(value = "/getHandlerClassMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getHandlerClassMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> flowRouterExtendClassManager.loadExtendClassMap(lanCode));
    }

    @RequestMapping(value = "/getHandlerClassMeta", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getHandlerClassMeta(String extendClassId) {
        return serviceBasicUtilityController.getObjectMeta(
                () -> {
                    try {
                        return flowRouterExtendClassManager.loadExtendClassMeta(extendClassId,
                                logonActionController.getLanguageCode());
                    } catch (ServiceEntityInstallationException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
    }

    private FlowRouterExtendClassServiceUIModel parseToServiceUIModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (FlowRouterExtendClassServiceUIModel) JSONObject.toBean(jsonObject,
                FlowRouterExtendClassServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        FlowRouterExtendClassServiceUIModel flowRouterExtendClassServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                flowRouterExtendClassServiceUIModel,
                flowRouterExtendClassServiceUIModel.getFlowRouterExtendClassUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/moveUpward", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String moveUpward(@RequestBody String request) {
        FlowRouterExtendClassServiceUIModel flowRouterExtendClassServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                flowRouterExtendClassServiceUIModel, null, serviceModule -> {
                    FlowRouterExtendClassServiceModel flowRouterExtendClassServiceModel = (FlowRouterExtendClassServiceModel) serviceModule;
                    flowRouterExtendClassManager.moveUpward(flowRouterExtendClassServiceModel.getFlowRouterExtendClass(),
                            logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
                    return flowRouterExtendClassServiceModel;
                }, null,
                flowRouterExtendClassServiceUIModel.getFlowRouterExtendClassUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkExtendClassValid", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkExtendClassValid(@RequestBody SimpleSEJSONRequest simpleSEJSONRequest) {
        return serviceBasicUtilityController.getObjectMeta(
                () -> {
                    try {
                        return flowRouterExtendClassManager.checkExtendClassResult(simpleSEJSONRequest.getId(),
                                logonActionController.getSerialLogonInfo());
                    } catch (ServiceEntityInstallationException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(FlowRouterExtendClass.SENAME,
                        FlowRouterExtendClass.NODENAME, FlowRouter.NODENAME, request.getBaseUUID()).inputRequest(request).processServiceEntityNode(
                        (ServiceBasicUtilityController.IProcessServiceEntityNode<FlowRouterExtendClass>) flowRouterExtendClass -> flowRouterExtendClassManager.initExtendClass(flowRouterExtendClass)
                ).build(),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, flowRouterManager);
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleEditService(String uuid){
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleViewService(String uuid){
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
