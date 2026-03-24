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
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataServiceUIModel;
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.SerExtendPageMetadataUIModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.SerExtendPageMetadataManager;
import com.company.IntelligentPlatform.common.service.SerExtendPageMetadataServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "serExtendPageMetadataEditorController")
@RequestMapping(value = "/serExtendPageMetadata")
public class SerExtendPageMetadataEditorController extends
        SEEditorController {

    public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected StandardSystemCategoryProxy standardSystemCategoryProxy;

    @Autowired
    protected SerExtendPageMetadataServiceUIModelExtension serExtendPageMetadataServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceExtensionSettingManager serviceExtensionSettingManager;

    @Autowired
    protected SerExtendPageMetadataManager serExtendPageMetadataManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                SerExtendPageMetadataServiceUIModel.class,
                SerExtendPageMetadataServiceModel.class, AOID_RESOURCE,
                SerExtendPageMetadata.NODENAME,
                SerExtendPageMetadata.SENAME, serExtendPageMetadataServiceUIModelExtension,
                serviceExtensionSettingManager
        );
    }

    private SerExtendPageMetadataServiceUIModel parseToServiceUIModel(
            String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        JSONObject serExtendPageMetadataUIModelJson = jsonObject.getJSONObject("serExtendPageMetadataUIModel");
        Object pageMetaObj = serExtendPageMetadataUIModelJson.get("pageMeta");
        String pageMeta = pageMetaObj != null ? pageMetaObj.toString() : ServiceJSONParser.emptyJson();
        serExtendPageMetadataUIModelJson.remove("pageMeta");
        jsonObject.put("serExtendPageMetadataUIModel", serExtendPageMetadataUIModelJson);
        SerExtendPageMetadataServiceUIModel serExtendPageMetadataServiceUIModel = (SerExtendPageMetadataServiceUIModel) JSONObject
                .toBean(jsonObject,
                        SerExtendPageMetadataServiceUIModel.class, classMap);
        SerExtendPageMetadataUIModel serExtendPageMetadataUIModel =
                serExtendPageMetadataServiceUIModel.getSerExtendPageMetadataUIModel();
        serExtendPageMetadataUIModel.setPageMeta(pageMeta);
        return serExtendPageMetadataServiceUIModel;
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        SerExtendPageMetadataServiceUIModel serExtendPageMetadataServiceUIModel =
                parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                serExtendPageMetadataServiceUIModel, null, serviceModule -> serExtendPageMetadataManager.updateUniqueActivePage((SerExtendPageMetadataServiceModel) serviceModule, logonActionController.getSerialLogonInfo()), null,
                serExtendPageMetadataServiceUIModel.getSerExtendPageMetadataUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> serExtendPageMetadataManager.getPageHeaderModelList(request1, client));
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(SerExtendPageSetting.SENAME, SerExtendPageSetting.NODENAME,
                        null, SerExtendPageSetting.NODENAME, request.getBaseUUID(), null, null, request, serviceEntityNode -> {
                    SerExtendPageMetadata serExtendPageMetadata = (SerExtendPageMetadata) serviceEntityNode;
                    return serExtendPageMetadataManager.newPageMetadata(serExtendPageMetadata);
                }),
                ISystemActionCode.ACID_EDIT);
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
    public @ResponseBody String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getSystemCategoryMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getSystemCategoryMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serExtendPageMetadataManager.initSystemCategoryMap(lanCode));
    }

    @RequestMapping(value = "/getItemStatusMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getItemStatusMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> serExtendPageMetadataManager.initItemStatus(lanCode));
    }

}
