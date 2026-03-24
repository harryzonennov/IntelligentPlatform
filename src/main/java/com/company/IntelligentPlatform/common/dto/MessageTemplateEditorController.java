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
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceSimpleDataProviderException;
import com.company.IntelligentPlatform.common.service.SimpleDataProviderFactory;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Scope("session")
@Controller(value = "messageTemplateEditorController")
@RequestMapping(value = "/messageTemplate")
public class MessageTemplateEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MessageTemplate;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected MessageTemplateServiceUIModelExtension messageTemplateServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MessageTemplateManager messageTemplateManager;

	@Autowired
	protected SearchProxyConfigManager searchProxyConfigManager;

	@Autowired
	protected SimpleDataProviderFactory simpleDataProviderFactory;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				MessageTemplateServiceUIModel.class,
				MessageTemplateServiceModel.class, AOID_RESOURCE,
				MessageTemplate.NODENAME,
				MessageTemplate.SENAME, messageTemplateServiceUIModelExtension,
				messageTemplateManager
		);
	}

	private MessageTemplateServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("messageTempPrioritySettingUIModelList",
				MessageTempPrioritySettingServiceUIModel.class);
		classMap.put("messageTempSearchConditionUIModelList",
				MessageTempSearchConditionServiceUIModel.class);
		return (MessageTemplateServiceUIModel) JSONObject
				.toBean(jsonObject, MessageTemplateServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		MessageTemplateServiceUIModel messageTemplateServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				messageTemplateServiceUIModel,
				messageTemplateServiceUIModel.getMessageTemplateUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(MessageTemplate.SENAME, MessageTemplate.NODENAME).build(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, messageTemplateManager);
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
	
	@RequestMapping(value = "/getSearchModelNameMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSearchModelNameMap(String baseUUID) {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				return searchProxyConfigManager.getAllSearchModelList(logonActionController.getLogonInfo());
			} catch (SearchProxyConfigureException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		});
	}

	@RequestMapping(value = "/getDataProviderMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDataProviderMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> simpleDataProviderFactory.getDataProviderLabelMap(lanCode));
	}

	@RequestMapping(value = "/getProviderOffsetDirection", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProviderOffsetDirection(String providerId) {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> {
                    try {
                        return messageTemplateManager.getProviderOffsetDirectionMap(providerId);
                    } catch (ServiceSimpleDataProviderException e) {
                        throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
                    }
                });
	}

	@RequestMapping(value = "/getProviderOffsetUnit", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProviderOffsetUnit(String providerId) {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> {
					try {
						return messageTemplateManager.getProviderOffsetUnitMap(providerId);
					} catch (ServiceSimpleDataProviderException e) {
						throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
					}
				});
	}

	@RequestMapping(value = "/getProviderOffsetUnitTemplate", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProviderOffsetUnitTemplate(String providerId) {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> {
					try {
						return messageTemplateManager.getProviderOffsetUnitTemplate(providerId);
					} catch (ServiceSimpleDataProviderException e) {
						throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
					}
				});
	}

	@RequestMapping(value = "/getAllHandlerList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAllHandlerList() {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				return messageTemplateManager.getAllHandlerList();
			} catch (MessageTemplateException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		});
	}

	@RequestMapping(value = "/getProviderOffsetDirectionTemplate", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getProviderOffsetDirectionTemplate(String providerId) {
		return serviceBasicUtilityController.getMapMeta(lanCode -> {
            try {
                return messageTemplateManager.getProviderOffsetDirectionTemplate(providerId);
            } catch (ServiceSimpleDataProviderException e) {
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
            }
        });
	}

}
