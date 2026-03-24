package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "resFinSystemResourceListController")
@RequestMapping(value = "/resFinSystemResource")
public class ResFinSystemResourceListController extends SEListController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonInfoManager logonInfoManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected SystemResourceManager systemResourceManager;

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ResFinSystemResourceSearchModel resFinSystemResourceSearchModel = new ResFinSystemResourceSearchModel();
			List<ServiceEntityNode> rawList = systemResourceManager
					.searchInternal(resFinSystemResourceSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ResFinSystemResourceUIModel> systemResourceUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(systemResourceUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			ResFinSystemResourceSearchModel resFinSystemResourceSearchModel = (ResFinSystemResourceSearchModel) JSONObject
					.toBean(jsonObject, ResFinSystemResourceSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = systemResourceManager
					.searchInternal(resFinSystemResourceSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ResFinSystemResourceUIModel> systemResourceUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(systemResourceUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	protected List<ResFinSystemResourceUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ResFinSystemResourceUIModel> systemResourceList = new ArrayList<ResFinSystemResourceUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ResFinSystemResourceUIModel systemResourceUIModel = new ResFinSystemResourceUIModel();
			SystemResource systemResource = (SystemResource) rawNode;
			convSystemResourceToUI(systemResource, systemResourceUIModel);
			AuthorizationObject authorizationObject = (AuthorizationObject) authorizationObjectManager
					.getEntityNodeByKey(
							systemResource.getRefSimAuthorObjectUUID(), "uuid",
							AuthorizationObject.NODENAME, null);
			convAuthorizationObjectToUI(authorizationObject,
					systemResourceUIModel);
			systemResourceList.add(systemResourceUIModel);
		}
		return systemResourceList;
	}

	protected List<ServiceEntityNode> searchInternal(
			ResFinSystemResourceSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemResource]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemResource.SENAME);
		searchNodeConfig0.setNodeName(SystemResource.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemResource.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[authorizationObject]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(AuthorizationObject.SENAME);
		searchNodeConfig1.setNodeName(AuthorizationObject.NODENAME);
		searchNodeConfig1.setNodeInstID(AuthorizationObject.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig1.setMapBaseFieldName("refSimAuthorObjectUUID");
		searchNodeConfig1.setMapSourceFieldName("uuid");
		searchNodeConfig1.setBaseNodeInstID(SystemResource.SENAME);
		searchNodeConfigList.add(searchNodeConfig1);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	protected void convSystemResourceToUI(SystemResource systemResource,
			ResFinSystemResourceUIModel systemResourceUIModel)
			throws ServiceEntityInstallationException {
		if (systemResource != null) {
			systemResourceUIModel.setUuid(systemResource.getUuid());
			systemResourceUIModel.setId(systemResource.getId());
			systemResourceUIModel.setName(systemResource.getName());
			systemResourceUIModel.setNote(systemResource.getNote());
			systemResourceUIModel.setUrl(systemResource.getUrl());
			systemResourceUIModel.setAbsURL(systemResource.getAbsURL());
			systemResourceUIModel.setRegSEName(systemResource.getRegSEName());
			systemResourceUIModel.setRegNodeName(systemResource
					.getRegNodeName());
			systemResourceUIModel.setViewType(systemResource.getViewType());
			Map<Integer, String> viewTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(ResFinSystemResourceUIModel.class,
							"viewType");
			systemResourceUIModel.setViewTypeValue(viewTypeMap
					.get(systemResource.getViewType()));
			systemResourceUIModel.setUiModelClassName(systemResource
					.getUiModelClassName());
			systemResourceUIModel.setControllerClassName(systemResource
					.getControllerClassName());
			systemResourceUIModel.setRefSimAuthorObjectUUID(systemResource
					.getRefSimAuthorObjectUUID());
		}
	}

	protected void convAuthorizationObjectToUI(
			AuthorizationObject authorizationObject,
			ResFinSystemResourceUIModel systemResourceUIModel)
			throws ServiceEntityInstallationException {
		if (authorizationObject != null) {
			Map<Integer, String> authorizationObjectTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(ResFinSystemResourceUIModel.class,
							"authorizationObjectType");
			systemResourceUIModel
					.setAuthorizationObjectTypeValue(authorizationObjectTypeMap
							.get(authorizationObject
									.getAuthorizationObjectType()));
			systemResourceUIModel
					.setAuthorizationObjectType(authorizationObject
							.getAuthorizationObjectType());
			systemResourceUIModel.setAuthorizationObjectId(authorizationObject
					.getId());
			systemResourceUIModel
					.setAuthorizationObjectUuid(authorizationObject.getUuid());
			systemResourceUIModel
					.setAuthorizationObjectName(authorizationObject.getName());
		}
	}

}
