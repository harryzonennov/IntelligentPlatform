package com.company.IntelligentPlatform.common.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

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
import com.company.IntelligentPlatform.common.service.ServiceFlowCondGroupManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowCondGroupServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;


@Scope("session")
@Controller(value = "serviceFlowCondGroupEditorController")
@RequestMapping(value = "/serviceFlowCondGroup")
public class ServiceFlowCondGroupEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceFlowModel;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceFlowCondGroupServiceUIModelExtension serviceFlowCondGroupServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected ServiceFlowCondGroupManager serviceFlowCondGroupManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceFlowCondGroupServiceUIModel.class,
                ServiceFlowCondGroupServiceModel.class, AOID_RESOURCE,
                ServiceFlowCondGroup.NODENAME,
                ServiceFlowCondGroup.SENAME, serviceFlowCondGroupServiceUIModelExtension,
                serviceFlowModelManager
        );
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceFlowCondGroupManager.getPageHeaderModelList(request1, client));
    }

    private ServiceFlowCondGroupServiceUIModel parseToServiceUIModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("serviceFlowCondFieldUIModelList", ServiceFlowCondFieldUIModel.class);
        return (ServiceFlowCondGroupServiceUIModel) JSONObject.toBean(jsonObject,
                ServiceFlowCondGroupServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        ServiceFlowCondGroupServiceUIModel serviceFlowCondGroupServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceFlowCondGroupServiceUIModel,
                serviceFlowCondGroupServiceUIModel.getServiceFlowCondGroupUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    
    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(ServiceFlowCondGroup.SENAME, ServiceFlowCondGroup.NODENAME,
                        ServiceFlowModel.NODENAME, request.getBaseUUID()).build(),
                ISystemActionCode.ACID_EDIT);
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

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, serviceFlowModelManager);
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getLogicOperator", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getLogicOperator() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowCondGroupManager.initLogicOperatorMap(lanCode));
    }

    @RequestMapping(value = "/getLogicOperatorExpress", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getLogicOperatorExpress() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowCondGroupManager.initLogicOperatorExpressMap(lanCode));
    }

}
