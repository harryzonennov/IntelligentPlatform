package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.RoleMessageCategoryServiceUIModel;
import com.company.IntelligentPlatform.common.dto.RoleMessageCategoryServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.RoleMessageCategoryManager;
import com.company.IntelligentPlatform.common.service.RoleMessageCategoryServiceModel;
import com.company.IntelligentPlatform.common.service.RoleSpecifier;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "roleMessageCategoryEditorController")
    @RequestMapping(value = "/roleMessageCategory")
public class RoleMessageCategoryEditorController extends SEEditorController {

    public final static String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ROLE;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected RoleMessageCategoryManager roleMessageCategoryManager;

    @Autowired
    protected RoleSpecifier roleSpecifier;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected RoleMessageCategoryServiceUIModelExtension roleMessageCategoryServiceUIModelExtension;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                RoleMessageCategoryServiceUIModel.class,
                RoleMessageCategoryServiceModel.class, AOID_RESOURCE,
                RoleMessageCategory.NODENAME,
                RoleMessageCategory.SENAME, roleMessageCategoryServiceUIModelExtension,
                roleManager
        );
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> roleMessageCategoryManager.getPageHeaderModelList(request1, client));
    }

    private RoleMessageCategoryServiceUIModel parseToServiceUIModel(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        Map<String, Class<?>> classMap = new HashMap<>();
        return (RoleMessageCategoryServiceUIModel) JSONObject
                .toBean(jsonObject, RoleMessageCategoryServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        RoleMessageCategoryServiceUIModel roleMessageCategoryServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                roleMessageCategoryServiceUIModel,
                roleMessageCategoryServiceUIModel.getRoleMessageCategoryUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(RoleMessageCategory.SENAME, RoleMessageCategory.NODENAME,
                        null, Role.NODENAME, request.getBaseUUID(), null, roleSpecifier, request, null),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody String deleteModule(
            String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleEditService(String uuid){
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleViewService(String uuid){
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

}
