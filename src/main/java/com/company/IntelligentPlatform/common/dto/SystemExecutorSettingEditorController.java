package com.company.IntelligentPlatform.common.dto;

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
import com.company.IntelligentPlatform.common.dto.SystemExecutorSettingServiceUIModel;
import com.company.IntelligentPlatform.common.dto.SystemExecutorSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;

@Scope("session")
@Controller(value = "systemExecutorSettingEditorController")
@RequestMapping(value = "/systemExecutorSetting")
public class SystemExecutorSettingEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected SystemExecutorSettingServiceUIModelExtension systemExecutorSettingServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected SystemExecutorSettingManager systemExecutorSettingManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                SystemExecutorSettingServiceUIModel.class,
                SystemExecutorSettingServiceModel.class, AOID_RESOURCE,
                SystemExecutorSetting.NODENAME,
                SystemExecutorSetting.SENAME, systemExecutorSettingServiceUIModelExtension,
                systemExecutorSettingManager
        );
    }

    private SystemExecutorSettingServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("systemExecutorLogUIModelList",
                SystemExecutorLogUIModel.class);
        return (SystemExecutorSettingServiceUIModel) JSONObject
                .toBean(jsonObject, SystemExecutorSettingServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        SystemExecutorSettingServiceUIModel systemExecutorSettingServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                systemExecutorSettingServiceUIModel,
                systemExecutorSettingServiceUIModel.getSystemExecutorSettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(SystemExecutorSetting.SENAME, SystemExecutorSetting.NODENAME).build(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getAllExecutorList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getAllExecutorList(String type) {
        return serviceBasicUtilityController.getListMeta(() -> {
            int executionType = Integer.parseInt(type);
            try {
                return systemExecutorSettingManager
                        .getAllExecutorList(ISystemExecutorAttr.STATUS_ACTIVE,
                                executionType);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
    }

    @RequestMapping(value = "/loadExecutorTemplate", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadExecutorTemplate(String id) {
        return serviceBasicUtilityController.getObjectMeta(() -> {
            try {
                return systemExecutorSettingManager
                        .getExecutorTemplateById(id);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        },AOID_RESOURCE, ISystemActionCode.ACID_VIEW);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                systemExecutorSettingManager);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLockService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/checkClassValid", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkClassValid(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.voidExecuteWrapper(
                () -> {
                    try {
                        systemExecutorSettingManager.checkProxyClassValid(request
                                .getContent());
                    } catch (ClassNotFoundException | SystemConfigureResourceException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                    }
                }
                , AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/execute", produces = "text/html;charset=UTF-8")
    public @ResponseBody String execute(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.voidExecuteWrapper(
                () -> systemExecutorSettingManager.executeWrapper(request.getUuid(), logonActionController.getLogonInfo())
                , AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getExecutionType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getExecutionType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> systemExecutorSettingManager.initExecutionTypeMap(lanCode));
    }

}
