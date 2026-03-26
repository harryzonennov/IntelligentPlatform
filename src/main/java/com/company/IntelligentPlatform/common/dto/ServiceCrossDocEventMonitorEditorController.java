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
import com.company.IntelligentPlatform.common.service.ServiceCrossDocEventMonitorManager;
import com.company.IntelligentPlatform.common.service.ServiceCrossDocEventMonitorServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "serviceCrossDocEventMonitorEditorController")
@RequestMapping(value = "/serviceCrossDocEventMonitor")
public class ServiceCrossDocEventMonitorEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceCrossDocEventMonitorServiceUIModelExtension serviceCrossDocEventMonitorServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceCrossDocEventMonitorManager serviceCrossDocEventMonitorManager;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceCrossDocEventMonitorServiceUIModel.class,
                ServiceCrossDocEventMonitorServiceModel.class, AOID_RESOURCE,
                ServiceCrossDocEventMonitor.NODENAME,
                ServiceCrossDocEventMonitor.SENAME, serviceCrossDocEventMonitorServiceUIModelExtension,
                serviceDocumentSettingManager, null
        );
    }
    
    private ServiceCrossDocEventMonitorServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (ServiceCrossDocEventMonitorServiceUIModel) JSONObject
                .toBean(jsonObject, ServiceCrossDocEventMonitorServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        ServiceCrossDocEventMonitorServiceUIModel serviceCrossDocEventMonitorServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceCrossDocEventMonitorServiceUIModel,
                serviceCrossDocEventMonitorServiceUIModel.getServiceCrossDocEventMonitorUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceCrossDocEventMonitorManager.getPageHeaderModelList(request1,
                        logonActionController.getSerialLogonInfo()));
    }

    @RequestMapping(value = "/getTriggerDocActionScenarioMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getTriggerDocActionScenarioMap() {
        return serviceBasicUtilityController.getMapMeta(lanCode -> serviceCrossDocEventMonitorManager.initTriggerDocActionScenarioMap(lanCode));
    }

    @RequestMapping(value = "/getTriggerParentModeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getTriggerParentModeMap() {
        return serviceBasicUtilityController.getMapMeta(lanCode -> serviceCrossDocEventMonitorManager.initTriggerParentModeMap(lanCode));
    }

    @RequestMapping(value = "/getDocActionMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getDocActionMap(int docType) {
        return serviceBasicUtilityController.getMapMeta(lanCode -> {
            try {
                return serviceCrossDocEventMonitorManager.getDocActionMap(docType,
                        logonActionController.getLanguageCode());
            } catch (DocActionException e) {
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG,e.getErrorMessage());
            }
        });
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

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ServiceCrossDocEventMonitor.SENAME, ServiceCrossDocEventMonitor.NODENAME,
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

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

}
