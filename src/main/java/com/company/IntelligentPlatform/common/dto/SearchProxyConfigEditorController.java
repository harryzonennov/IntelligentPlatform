package com.company.IntelligentPlatform.common.dto;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigServiceModel;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

@Scope("session")
@Controller(value = "searchProxyConfigEditorController")
@RequestMapping(value = "/searchProxyConfig")
public class SearchProxyConfigEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.SearchProxyConfig;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected SearchProxyConfigServiceUIModelExtension searchProxyConfigServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected SearchProxyConfigManager searchProxyConfigManager;

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                SearchProxyConfigServiceUIModel.class,
                SearchProxyConfigServiceModel.class, AOID_RESOURCE,
                SearchProxyConfig.NODENAME,
                SearchProxyConfig.SENAME, searchProxyConfigServiceUIModelExtension,
                searchProxyConfigManager
        );
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        SearchProxyConfigServiceUIModel searchProxyConfigServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                searchProxyConfigServiceUIModel,
                searchProxyConfigServiceUIModel.getSearchProxyConfigUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService() {
        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(SearchProxyConfig.SENAME, SearchProxyConfig.NODENAME).build(), ISystemActionCode.ACID_EDIT);
    }

    protected SearchProxyConfigServiceUIModel parseToServiceUIModel(
            @RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("searchFieldConfigUIModelList",
                SearchFieldConfigUIModel.class);
        return (SearchProxyConfigServiceUIModel) JSONObject
                .toBean(jsonObject, SearchProxyConfigServiceUIModel.class,
                        classMap);
    }

    @RequestMapping(value = "/genDefSearchFieldConfigureBatch", produces = "text/html;charset=UTF-8")
    public @ResponseBody String genDefSearchFieldConfigureBatch(
            @RequestBody String request) {
        SearchProxyConfigServiceUIModel searchProxyConfigServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                searchProxyConfigServiceUIModel, null, serviceModule -> {
                    SearchProxyConfigServiceModel searchProxyConfigServiceModel = (SearchProxyConfigServiceModel) serviceModule;
                    try {
                        searchProxyConfigManager.genDefSearchFieldConfigureBatch(
                                searchProxyConfigServiceModel, false, logonActionController.getResUserUUID(),
                                logonActionController.getResOrgUUID());
                    } catch (ServiceModuleProxyException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                    return searchProxyConfigServiceModel;
                },null,
                searchProxyConfigServiceUIModel.getSearchProxyConfigUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest,
                searchProxyConfigManager);
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

    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody String preLock(String uuid) {
        return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
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

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody String exitEditor(
            @RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/getDocumentType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocumentType() {
        return serviceBasicUtilityController.getMapMeta(lanCode -> searchProxyConfigManager
                .initDocumentTypeMap());
    }

    @RequestMapping(value = "/getProxyType", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getProxyType() {
        return serviceBasicUtilityController.getMapMeta(lanCode -> searchProxyConfigManager
                .initProxyTypeMap());
    }

    @RequestMapping(value = "/getAllSearchModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getAllSearchModelList() {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                return searchProxyConfigManager.getAllSearchModelList(logonActionController.getLogonInfo());
            } catch (SearchProxyConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    @RequestMapping(value = "/getRawSearchModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getRawSearchModelList(String baseUUID) {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                SearchProxyConfig searchProxyConfig = (SearchProxyConfig) searchProxyConfigManager
                        .getEntityNodeByUUID(baseUUID,
                                SearchProxyConfig.NODENAME, logonActionController.getClient());
                List<Class<?>> rawSearchModelClsList = searchProxyConfigManager.getRawSearchModelList(searchProxyConfig);
                if (ServiceCollectionsHelper.checkNullList(rawSearchModelClsList)) {
                    return null;
                }
                return rawSearchModelClsList.stream().map(Class::getSimpleName).collect(Collectors.toList());
            } catch (SearchProxyConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    @RequestMapping(value = "/getRawFieldList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getRawFieldList(String baseUUID, String searchModelName) {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                SearchProxyConfig searchProxyConfig = (SearchProxyConfig) searchProxyConfigManager
                        .getEntityNodeByUUID(baseUUID,
                                SearchProxyConfig.NODENAME, logonActionController.getClient());
                return searchProxyConfigManager
                        .getRawSearchFieldList(searchProxyConfig, searchModelName);
            } catch (SearchProxyConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    @RequestMapping(value = "/checkBeanValid", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkBeanValid(
            @RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.voidExecuteWrapper(() -> {
            searchProxyConfigManager.checkBeanValid(request.getContent());
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getConfigureSearchLogicFlag", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getConfigureSearchLogicFlag() {
        return serviceBasicUtilityController.getMapMeta(lanCode -> searchProxyConfigManager
                .initConfigureSearchLogicFlagMap());
    }

}
