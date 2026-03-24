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
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceDocActConfigureItemManager;
import com.company.IntelligentPlatform.common.service.ServiceDocActConfigureItemServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceDocActConfigureItem;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;

import java.util.HashMap;
import java.util.Map;


@Scope("session")
@Controller(value = "serviceDocActConfigureItemEditorController")
@RequestMapping(value = "/serviceDocActConfigureItem")
public class ServiceDocActConfigureItemEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceDocActConfigureItemServiceUIModelExtension serviceDocActConfigureItemServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceDocActConfigureItemManager serviceDocActConfigureItemManager;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceDocActConfigureItemServiceUIModel.class,
                ServiceDocActConfigureItemServiceModel.class, AOID_RESOURCE,
                ServiceDocActConfigureItem.NODENAME,
                ServiceDocActConfigureItem.SENAME, serviceDocActConfigureItemServiceUIModelExtension,
                serviceDocumentSettingManager, null
        );
    }

    private ServiceDocActConfigureItemServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (ServiceDocActConfigureItemServiceUIModel) JSONObject
                .toBean(jsonObject, ServiceDocActConfigureItemServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        ServiceDocActConfigureItemServiceUIModel serviceDocActConfigureItemServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceDocActConfigureItemServiceUIModel,
                serviceDocActConfigureItemServiceUIModel.getServiceDocActConfigureItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceDocActConfigureItemManager.getPageHeaderModelList(request1, logonActionController.getSerialLogonInfo()));
    }

    @RequestMapping(value = "/getDocStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocStatusMap(int documentType) {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> docActionExecutionProxyFactory.getStatusMapByDocType(documentType, lanCode));
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ServiceDocActConfigureItem.SENAME, ServiceDocActConfigureItem.NODENAME,
                        null, ServiceDocActionConfigure.NODENAME, request.getBaseUUID(), null, null, request, null),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
                getServiceUIModelRequest());
    }

    @RequestMapping(value = "/getDocActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionCodeMap(int documentType) {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> {
                    try {
                        return docActionExecutionProxyFactory.getDocActionMapByDocType(documentType, lanCode);
                    } catch (DocActionException e) {
                        throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
                    }
                });
    }

    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }


}
