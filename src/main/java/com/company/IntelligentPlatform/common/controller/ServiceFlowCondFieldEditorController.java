package com.company.IntelligentPlatform.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceFlowCondFieldManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowCondFieldServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceFlowModelManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceFieldMeta;

import java.util.Map;

@Scope("session")
@Controller(value = "serviceFlowCondFieldEditorController")
@RequestMapping(value = "/serviceFlowCondField")
public class ServiceFlowCondFieldEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceFlowModel;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceFlowCondFieldServiceUIModelExtension serviceFlowCondFieldServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected ServiceFlowCondFieldManager serviceFlowCondFieldManager;

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceFlowCondFieldManager.getPageHeaderModelList(request1, client));
    }

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceFlowCondFieldServiceUIModel.class,
                ServiceFlowCondFieldServiceModel.class, AOID_RESOURCE,
                ServiceFlowCondField.NODENAME,
                ServiceFlowCondField.SENAME, serviceFlowCondFieldServiceUIModelExtension,
                serviceFlowModelManager
        );
    }

    @RequestMapping(value = "/getValueOperator", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getValueOperator() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowCondFieldManager.initValueOperatorMap(lanCode));
    }

    @RequestMapping(value = "/getFieldType", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getFieldType() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceFlowCondFieldManager.initFieldTypeMap());
    }

    @RequestMapping(value = "/getNodeInstMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getNodeInstMap(String serviceUIModuleId) {
        try {
            Map<String, String> nodeInstMap = serviceFlowCondFieldManager.getNodeInstMap(serviceUIModuleId,
                    logonActionController.getLanguageCode());
            return serviceFlowModelManager.getDefaultStrSelectMap(nodeInstMap, false);
        } catch (ServiceFlowException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/getFieldNameMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getFieldNameMap(String nodeInstId, String serviceUIModuleId) {
        try {
            Map<String, String> fieldNameMap = serviceFlowCondFieldManager.getFieldNameMap(nodeInstId, serviceUIModuleId,
                    logonActionController.getLanguageCode());
            return serviceFlowModelManager.getDefaultStrSelectMap(fieldNameMap, false);
        } catch (ServiceFlowException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/getFieldMeta", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getFieldMeta(String fieldName, String nodeInstId, String serviceUIModuleId) {
        try {
            ServiceFieldMeta serviceFieldMeta = serviceFlowCondFieldManager.getFieldMeta(fieldName, nodeInstId, serviceUIModuleId,
                    logonActionController.getLanguageCode());
            return ServiceJSONParser.genDefOKJSONArray(ServiceCollectionsHelper.asList(serviceFieldMeta));
        } catch (ServiceFlowException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(ServiceFlowCondField.SENAME, ServiceFlowCondField.NODENAME,
                        ServiceFlowCondGroup.NODENAME, request.getBaseUUID()).build(),
                ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, serviceFlowModelManager);
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
        return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
