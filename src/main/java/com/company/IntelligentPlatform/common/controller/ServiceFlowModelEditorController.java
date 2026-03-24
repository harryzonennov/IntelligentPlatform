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
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelServiceModel;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;


@Scope("session")
@Controller(value = "serviceFlowModelEditorController")
@RequestMapping(value = "/serviceFlowModel")
public class ServiceFlowModelEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceFlowModel;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceFlowModelServiceUIModelExtension serviceFlowModelServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceFlowModelServiceUIModel.class,
                ServiceFlowModelServiceModel.class, AOID_RESOURCE,
                ServiceFlowModel.NODENAME,
                ServiceFlowModel.SENAME, serviceFlowModelServiceUIModelExtension,
                serviceFlowModelManager
        );
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

    private ServiceFlowModelServiceUIModel parseToServiceUIModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("serviceFlowCondFieldUIModelList", ServiceFlowCondFieldUIModel.class);
        classMap.put("serviceFlowCondGroupUIModelList", ServiceFlowCondGroupServiceUIModel.class);
        return (ServiceFlowModelServiceUIModel) JSONObject.toBean(jsonObject, ServiceFlowModelServiceUIModel.class,
                classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        ServiceFlowModelServiceUIModel serviceFlowModelServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceFlowModelServiceUIModel,
                serviceFlowModelServiceUIModel.getServiceFlowModelUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(ServiceFlowModel.SENAME, ServiceFlowModel.NODENAME).processServiceModule(serviceModule -> {
                    try {
                        return serviceFlowModelManager.initServiceFlowModel(logonActionController.getLogonInfo());
                    } catch (ServiceFlowException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }).build(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, serviceFlowModelManager);
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

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getActionCode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getActionCode() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowModelManager.initActionCodeMap(lanCode));
    }

    @RequestMapping(value = "/getServiceUIModelId", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getServiceUIModelId() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowModelManager.initServiceUIModelIdMap(lanCode));
    }

    @RequestMapping(value = "/getDocumentType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocumentType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowModelManager.initDocumentTypeMap(lanCode));
    }


}
