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
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceDocInitInvolvePartyManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitInvolvePartyServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "serviceDocServiceDocInitInvolvePartyEditorController")
@RequestMapping(value = "/serviceDocServiceDocInitInvolveParty")
public class ServiceDocInitInvolvePartyEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected LockObjectManager lockObjectManager;

    @Autowired
    protected ServiceDocInitInvolvePartyServiceUIModelExtension serviceDocServiceDocInitInvolvePartyServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceDocInitInvolvePartyManager serviceDocServiceDocInitInvolvePartyManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                ServiceDocInitInvolvePartyServiceUIModel.class,
                ServiceDocInitInvolvePartyServiceModel.class, AOID_RESOURCE,
                ServiceDocInitInvolveParty.NODENAME,
                ServiceDocInitInvolveParty.SENAME, serviceDocServiceDocInitInvolvePartyServiceUIModelExtension,
                serviceDocumentSettingManager
        );
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serviceDocServiceDocInitInvolvePartyManager.getPageHeaderModelList(request1,
                        logonActionController.getSerialLogonInfo()));
    }

    @RequestMapping(value = "/getPartyRoleMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPartyRoleMap(int docType) {
        try {
            Map<Integer, String> partyRoleMap =
                    serviceDocServiceDocInitInvolvePartyManager.getPartyRoleMap(docType,
                            logonActionController.getLanguageCode());
            return serviceDocumentSettingManager.getDefaultSelectMap(partyRoleMap, true);
        } catch (ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }


    @RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteModule(String uuid) {
        return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest());
    }


    @RequestMapping(value = "/getLogonPartyFlagMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getLogonPartyFlagMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serviceDocServiceDocInitInvolvePartyManager.getLogonPartyFlagMap(lanCode));
    }


    private ServiceDocInitInvolvePartyServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (ServiceDocInitInvolvePartyServiceUIModel) JSONObject
                .toBean(jsonObject, ServiceDocInitInvolvePartyServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        ServiceDocInitInvolvePartyServiceUIModel serviceDocServiceDocInitInvolvePartyServiceUIModel =
                parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serviceDocServiceDocInitInvolvePartyServiceUIModel,
                serviceDocServiceDocInitInvolvePartyServiceUIModel.getServiceDocInitInvolvePartyUIModel().getUuid(), ISystemActionCode.ACID_EDIT);

    }


    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(ServiceDocInitInvolveParty.SENAME, ServiceDocInitInvolveParty.NODENAME,
                        null, ServiceDocInitConfigure.NODENAME, request.getBaseUUID(), null, null, request, null),
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
