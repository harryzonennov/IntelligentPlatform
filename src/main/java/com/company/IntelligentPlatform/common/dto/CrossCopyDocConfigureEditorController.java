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
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectFailureException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.CrossCopyDocConfigureManager;
import com.company.IntelligentPlatform.common.service.CrossCopyDocConfigureServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.*;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "crossCopyDocConfigureEditorController")
@RequestMapping(value = "/crossCopyDocConfigure")
public class CrossCopyDocConfigureEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected LogonActionController logonActionController;
    
    @Autowired
    protected CrossCopyDocConfigureServiceUIModelExtension crossCopyDocConfigureServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected CrossCopyDocConfigureManager crossCopyDocConfigureManager;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected StandardDocFlowDirectionProxy standardDocFlowDirectionProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                CrossCopyDocConfigureServiceUIModel.class,
                CrossCopyDocConfigureServiceModel.class, AOID_RESOURCE,
                CrossCopyDocConfigure.NODENAME,
                CrossCopyDocConfigure.SENAME, crossCopyDocConfigureServiceUIModelExtension,
                serviceDocumentSettingManager
        );
    }

    private CrossCopyDocConfigureServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("serviceCrossDocEventMonitorUIModelList",
                ServiceCrossDocEventMonitorUIModel.class);
        return (CrossCopyDocConfigureServiceUIModel) JSONObject
                .toBean(jsonObject, CrossCopyDocConfigureServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        final String pageHeaderModelList = serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> crossCopyDocConfigureManager.getPageHeaderModelList(request1, logonActionController.getSerialLogonInfo()));
        return pageHeaderModelList;
    }

    @RequestMapping(value = "/getDocFlowDirectMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocFlowDirectMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> standardDocFlowDirectionProxy.getDocFlowDirectMap(lanCode));
    }

    @RequestMapping(value = "/getCloneAttachmentFlag", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getCloneAttachmentFlag() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> standardSwitchProxy.getSimpleSwitchMap(lanCode));
    }

    @RequestMapping(value = "/getActiveCustomSwitch", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActiveCustomSwitch() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> standardSwitchProxy.getSimpleSwitchMap(lanCode));
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        CrossCopyDocConfigureServiceUIModel crossCopyDocConfigureServiceUIModel =
                parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                crossCopyDocConfigureServiceUIModel,
                crossCopyDocConfigureServiceUIModel.getCrossCopyDocConfigureUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }


    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(CrossCopyDocConfigure.SENAME, CrossCopyDocConfigure.NODENAME,
                        null, ServiceDocumentSetting.NODENAME, request.getBaseUUID(), null, null, request, null),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                serviceDocumentSettingManager);
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
