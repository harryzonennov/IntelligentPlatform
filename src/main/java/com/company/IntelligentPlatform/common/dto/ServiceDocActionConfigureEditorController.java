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
import com.company.IntelligentPlatform.common.service.ServiceDocActionConfigureServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocActionConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "serviceDocActionConfigureEditorController")
@RequestMapping(value = "/serviceDocActionConfigure")
public class ServiceDocActionConfigureEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceDocActionConfigureServiceUIModelExtension serviceDocActionConfigureServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceDocActionConfigureManager serviceDocActionConfigureManager;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;


    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceDocActionConfigureServiceUIModel.class,
                ServiceDocActionConfigureServiceModel.class, AOID_RESOURCE,
                ServiceDocActionConfigure.NODENAME,
                ServiceDocActionConfigure.SENAME, serviceDocActionConfigureServiceUIModelExtension,
                serviceDocumentSettingManager, null
        );
    }

    private ServiceDocActionConfigureServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("serviceDocActConfigureItemUIModelList",
                ServiceDocActConfigureItemUIModel.class);
        return (ServiceDocActionConfigureServiceUIModel) JSONObject
                .toBean(jsonObject, ServiceDocActionConfigureServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceDocActionConfigureManager.getPageHeaderModelList(request1,
                        logonActionController.getSerialLogonInfo()));
    }

    @RequestMapping(value = "/getCustomSwitchMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getCustomSwitchMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceDocActionConfigureManager.initCustomSwitchMap(lanCode));
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        ServiceDocActionConfigureServiceUIModel serviceDocActionConfigureServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceDocActionConfigureServiceUIModel,
                serviceDocActionConfigureServiceUIModel.getServiceDocActionConfigureUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ServiceDocActionConfigure.SENAME, ServiceDocActionConfigure.NODENAME,
                        null, ServiceDocumentSetting.NODENAME, request.getBaseUUID(), null, null, request, null),
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
