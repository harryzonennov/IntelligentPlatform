package com.company.IntelligentPlatform.common.dto;

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
import com.company.IntelligentPlatform.common.service.ServiceCrossDocConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceCrossDocConfigureServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocConfigure;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "serviceCrossDocConfigureEditorController")
@RequestMapping(value = "/serviceCrossDocConfigure")
public class ServiceCrossDocConfigureEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceCrossDocConfigureServiceUIModelExtension serviceCrossDocConfigureServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceCrossDocConfigureManager serviceCrossDocConfigureManager;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected StandardDocFlowDirectionProxy standardDocFlowDirectionProxy;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceCrossDocConfigureServiceUIModel.class,
                ServiceCrossDocConfigureServiceModel.class, AOID_RESOURCE,
                ServiceCrossDocConfigure.NODENAME,
                ServiceCrossDocConfigure.SENAME, serviceCrossDocConfigureServiceUIModelExtension,
                serviceDocumentSettingManager, null
        );
    }

    private ServiceCrossDocConfigureServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("serviceCrossDocEventMonitorUIModelList",
                ServiceCrossDocEventMonitorUIModel.class);
        return (ServiceCrossDocConfigureServiceUIModel) JSONObject
                .toBean(jsonObject, ServiceCrossDocConfigureServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceCrossDocConfigureManager.getPageHeaderModelList(request1, logonActionController.getSerialLogonInfo()));
    }

    @RequestMapping(value = "/getDocFlowDirectMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocFlowDirectMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> standardDocFlowDirectionProxy.getDocFlowDirectMap(lanCode));
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        ServiceCrossDocConfigureServiceUIModel serviceCrossDocConfigureServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceCrossDocConfigureServiceUIModel,
                serviceCrossDocConfigureServiceUIModel.getServiceCrossDocConfigureUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }


    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ServiceCrossDocConfigure.SENAME, ServiceCrossDocConfigure.NODENAME,
                        null, Role.NODENAME, request.getBaseUUID(), null, null, request, null),
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

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody String deleteModule(
            String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }


}
