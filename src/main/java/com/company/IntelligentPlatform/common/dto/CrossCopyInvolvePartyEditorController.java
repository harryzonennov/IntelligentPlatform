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
import com.company.IntelligentPlatform.common.service.CrossCopyInvolvePartyManager;
import com.company.IntelligentPlatform.common.service.CrossCopyInvolvePartyServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.CrossCopyDocConfigure;
import com.company.IntelligentPlatform.common.model.CrossCopyInvolveParty;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "crossCopyInvolvePartyEditorController")
@RequestMapping(value = "/crossCopyInvolveParty")
public class CrossCopyInvolvePartyEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.ServiceDocumentSetting;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected CrossCopyInvolvePartyServiceUIModelExtension crossCopyInvolvePartyServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected CrossCopyInvolvePartyManager crossCopyInvolvePartyManager;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                CrossCopyInvolvePartyServiceUIModel.class,
                CrossCopyInvolvePartyServiceModel.class, AOID_RESOURCE,
                CrossCopyInvolveParty.NODENAME,
                CrossCopyInvolveParty.SENAME, crossCopyInvolvePartyServiceUIModelExtension,
                serviceDocumentSettingManager
        );
    }

    private CrossCopyInvolvePartyUIModel parseToUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (CrossCopyInvolvePartyUIModel) JSONObject
                .toBean(jsonObject, CrossCopyInvolvePartyUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> crossCopyInvolvePartyManager.getPageHeaderModelList(request1,
                        logonActionController.getSerialLogonInfo()));
    }

    @RequestMapping(value = "/getPartyRoleMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPartyRoleMap(int docType) {
        try {
            Map<Integer, String> partyRoleMap =
                    crossCopyInvolvePartyManager.getPartyRoleMap(docType,
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
                lanCode -> crossCopyInvolvePartyManager.getLogonPartyFlagMap(lanCode));
    }


    private CrossCopyInvolvePartyServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (CrossCopyInvolvePartyServiceUIModel) JSONObject
                .toBean(jsonObject, CrossCopyInvolvePartyServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        CrossCopyInvolvePartyServiceUIModel crossCopyInvolvePartyServiceUIModel =
                parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                crossCopyInvolvePartyServiceUIModel,
                crossCopyInvolvePartyServiceUIModel.getCrossCopyInvolvePartyUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }


    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(CrossCopyInvolveParty.SENAME, CrossCopyInvolveParty.NODENAME,
                        null, CrossCopyDocConfigure.NODENAME, request.getBaseUUID(), null, null, request, null),
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
