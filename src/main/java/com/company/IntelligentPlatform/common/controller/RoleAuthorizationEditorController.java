package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.RoleAuthorizationServiceUIModel;
import com.company.IntelligentPlatform.common.dto.RoleAuthorizationServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.RoleSubAuthorizationUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.RoleAuthorizationManager;
import com.company.IntelligentPlatform.common.service.RoleAuthorizationServiceModel;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.RoleSpecifier;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("session")
@Controller(value = "roleAuthorizationEditorController")
@RequestMapping(value = "/roleAuthorization")
public class RoleAuthorizationEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ROLE;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected RoleAuthorizationServiceUIModelExtension roleAuthorizationServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected RoleSpecifier roleSpecifier;

    @Autowired
    protected RoleAuthorizationManager roleAuthorizationManager;

    protected Logger logger = LoggerFactory.getLogger(RoleAuthorizationEditorController.class);

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        List<ServiceModuleConvertPara> serviceModuleConvertParaList = null;
        try {
            serviceModuleConvertParaList = roleAuthorizationManager.genActionCodeParaList();
        } catch (ServiceEntityConfigureException e) {
            // do nothing
            logger.error(e.getMessage(), e);
        }
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                RoleAuthorizationServiceUIModel.class,
                RoleAuthorizationServiceModel.class, AOID_RESOURCE,
                RoleAuthorization.NODENAME,
                RoleAuthorization.SENAME, roleAuthorizationServiceUIModelExtension,
                roleManager, serviceModuleConvertParaList
        );
    }

    private RoleAuthorizationServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("roleSubAuthorizationUIModelList",
                RoleSubAuthorizationUIModel.class);
        return (RoleAuthorizationServiceUIModel) JSONObject
                .toBean(jsonObject,
                        RoleAuthorizationServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> roleAuthorizationManager.getPageHeaderModelList(request1, client));
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        RoleAuthorizationServiceUIModel roleAuthorizationServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                roleAuthorizationServiceUIModel,
                roleAuthorizationServiceUIModel.getRoleAuthorizationUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/resetRoleSubAuthorization", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String resetRoleSubAuthorization(@RequestBody String request) {
        RoleAuthorizationServiceUIModel roleAuthorizationServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                roleAuthorizationServiceUIModel, null,
                (ServiceBasicUtilityController.IGetServiceModuleExecutor<RoleAuthorizationServiceModel>) roleAuthorizationServiceModel -> {
                    roleAuthorizationManager.batchResetRoleSubAuthorization(roleAuthorizationServiceModel.getRoleAuthorization(),
                            true, logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
                    return roleAuthorizationServiceModel;
                },null,
                roleAuthorizationServiceUIModel.getRoleAuthorizationUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(RoleAuthorization.SENAME, RoleAuthorization.NODENAME,
                        null, Role.NODENAME, request.getBaseUUID(), null, roleSpecifier, request, null),
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
