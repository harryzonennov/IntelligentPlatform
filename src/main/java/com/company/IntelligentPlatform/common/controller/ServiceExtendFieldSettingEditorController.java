package com.company.IntelligentPlatform.common.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.List;
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
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.ServiceExtendFieldSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceExtendFieldSettingServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Scope("session")
@Controller(value = "serviceExtendFieldSettingEditorController")
@RequestMapping(value = "/serviceExtendFieldSetting")
public class ServiceExtendFieldSettingEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected ServiceExtendFieldSettingServiceUIModelExtension serviceExtendFieldSettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ServiceExtendFieldSettingServiceUIModel.class,
				ServiceExtendFieldSettingServiceModel.class, AOID_RESOURCE,
				ServiceExtendFieldSetting.NODENAME,
				ServiceExtendFieldSetting.SENAME, serviceExtendFieldSettingServiceUIModelExtension,
				serviceExtensionSettingManager
		);
	}

	private ServiceExtendFieldSettingServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("serviceExtendFieldI18nSettingUIModelList",
				ServiceExtendFieldI18nSettingUIModel.class);
		classMap.put("serExtendUIControlSetUIModelList",
				SerExtendUIControlSetUIModel.class);
		return (ServiceExtendFieldSettingServiceUIModel) JSONObject
				.toBean(jsonObject,
						ServiceExtendFieldSettingServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		ServiceExtendFieldSettingServiceUIModel serviceExtendFieldSettingServiceUIModel =
				parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				serviceExtendFieldSettingServiceUIModel,
				(ServiceBasicUtilityController.IGetServiceModuleExecutor<ServiceExtendFieldSettingServiceModel>) serviceExtendFieldSettingServiceModel -> {
					serviceExtendFieldSettingManager
							.assignStoreModelName(serviceExtendFieldSettingServiceModel.getServiceExtendFieldSetting());
					return serviceExtendFieldSettingServiceModel;
				}, null,null,
				serviceExtendFieldSettingServiceUIModel.getServiceExtendFieldSettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> serviceExtendFieldSettingManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(ServiceExtendFieldSetting.SENAME, ServiceExtendFieldSetting.NODENAME,
						null, ServiceExtensionSetting.NODENAME, request.getBaseUUID(), null, null, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid){
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid){
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/calculateDefFieldName", produces = "text/html;charset=UTF-8")
	public @ResponseBody String calculateDefFieldName(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getObjectMeta(() -> {
			List<ServiceEntityNode> rawExtendFieldSettingList = serviceExtensionSettingManager
					.getEntityNodeListByKey(request.getBaseUUID(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ServiceExtendFieldSetting.NODENAME,
							logonActionController.getClient(), null);
			try {
				return serviceExtendFieldSettingManager
						.calculateDefFieldName(request.getContent(),
								rawExtendFieldSettingList);
			} catch (ServiceEntityInstallationException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		}, AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/removeFieldService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String removeFieldService(
			@RequestBody SimpleSEJSONRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) serviceExtensionSettingManager
					.getEntityNodeByKey(request.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							ServiceExtendFieldSetting.NODENAME,
							logonUser.getClient(), null);
			String resOrgUUID = ServiceEntityStringHelper.EMPTYSTRING;
			Organization organization = logonActionController
					.getOrganizationByUser(logonUser.getUuid());
			if (organization != null) {
				resOrgUUID = organization.getUuid();
			}
			if (serviceExtendFieldSetting != null) {
				
				List<ServiceEntityNode> serExtendUIControlSetList = serviceExtensionSettingManager
						.getEntityNodeListByKey(serviceExtendFieldSetting.getUuid(),
								SerExtendUIControlSet.FIELD_REFFEILDUUID,
								SerExtendUIControlSet.NODENAME,
								logonUser.getClient(), null);
				if(!ServiceCollectionsHelper.checkNullList(serExtendUIControlSetList)){
					for(ServiceEntityNode seNode:serExtendUIControlSetList){
						serviceExtensionSettingManager.deleteSENode(seNode, logonUser.getUuid(), null);
					}
				}	
				serviceExtensionSettingManager.archiveDeleteEntityByKey(
						serviceExtendFieldSetting.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						logonUser.getClient(),
						ServiceExtendFieldSetting.NODENAME,
						logonUser.getUuid(), resOrgUUID);
			}
			return ServiceJSONParser.genDeleteOKResponse();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getFieldType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFieldType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serviceExtendFieldSettingManager.initFieldTypeMap(lanCode));
	}

	@RequestMapping(value = "/getSystemCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSystemCategory() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardSystemCategoryProxy.getSystemCategoryMap(lanCode));
	}

	@RequestMapping(value = "/getFieldMaxLengthMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFieldMaxLengthMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serviceExtendFieldSettingManager.initFieldMaxLengthMap());
	}

	@RequestMapping(value = "/getLanKey", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLanKey() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serviceExtensionSettingManager.initLanKeyMap());
	}

	@RequestMapping(value = "/getStoreModelName", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStoreModelName() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serviceExtendFieldSettingManager.initStoreModelNameMap());
	}

}
