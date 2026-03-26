package com.company.IntelligentPlatform.common.dto;

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
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Scope("session")
@Controller(value = "messageTempSearchConditionEditorController")
@RequestMapping(value = "/messageTempSearchCondition")
public class MessageTempSearchConditionEditorController extends
        SEEditorController {

    public static final String AOID_RESOURCE = IServiceModelConstants.MessageTemplate;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected MessageTempSearchConditionServiceUIModelExtension messageTempSearchConditionServiceUIModelExtension;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected MessageTemplateManager messageTemplateManager;

    @Autowired
    protected MessageTempSearchConditionManager messageTempSearchConditionManager;

    @RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> messageTempSearchConditionManager.getPageHeaderModelList(request1, client));
    }

    public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
        return new ServiceBasicUtilityController.ServiceUIModelRequest(
                MessageTempSearchConditionServiceUIModel.class,
                MessageTempSearchConditionServiceModel.class, AOID_RESOURCE,
                MessageTempSearchCondition.NODENAME,
                MessageTempSearchCondition.SENAME, messageTempSearchConditionServiceUIModelExtension,
                messageTemplateManager
        );
    }

    public MessageTempSearchConditionServiceUIModel parseToServiceUIModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (MessageTempSearchConditionServiceUIModel) JSONObject
                .toBean(jsonObject, MessageTempSearchConditionServiceUIModel.class, classMap);
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String saveModuleService(@RequestBody String request) {
        MessageTempSearchConditionServiceUIModel messageTempSearchConditionServiceUIModel = parseToServiceUIModel(request);
        return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
                messageTempSearchConditionServiceUIModel,
                messageTempSearchConditionServiceUIModel.getMessageTempSearchConditionUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody String newModuleService(
            @RequestBody SimpleSEJSONRequest request) {

        return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
                new ServiceBasicUtilityController.InitServiceEntityRequest(
                        MessageTempSearchCondition.SENAME, MessageTempSearchCondition.NODENAME, MessageTemplate.NODENAME,
                        request.getBaseUUID(),
                        null), ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody String checkDuplicateID(
            @RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super
                .checkDuplicateIDCore(simpleRequest, messageTemplateManager);
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

    @RequestMapping(value = "/getSearchFieldNameList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getSearchFieldNameList(String baseUUID) {
        return serviceBasicUtilityController.getListMeta(() -> {
            try {
                MessageTemplate messageTemplate = (MessageTemplate) messageTemplateManager
                        .getEntityNodeByUUID(baseUUID,
                                MessageTemplate.NODENAME,
                                logonActionController.getClient());
                return messageTemplateManager.getRawSearchFieldList(messageTemplate);
            } catch (MessageTemplateException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
    }

    @RequestMapping(value = "/getLogicOperator", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getLogicOperator() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> messageTemplateManager.initLogicOperatorMap(lanCode));
    }

}
