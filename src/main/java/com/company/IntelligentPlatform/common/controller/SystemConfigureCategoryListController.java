package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.SystemConfigureCategorySearchModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureCategoryUIModel;
import com.company.IntelligentPlatform.common.dto.SystemConfigureElementSearchModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "systemConfigureCategoryListController")
@RequestMapping(value = "/systemConfigureCategory")
public class SystemConfigureCategoryListController extends SEListController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SystemConfigureCategory;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	@Autowired
	protected SystemConfigureCategoryEditorController systemConfigureCategoryEditorController;

	@Autowired
	protected SystemConfigureResourceEditorController systemConfigureResourceEditorController;

	@Autowired
	protected SystemConfigureElementEditorController systemConfigureElementEditorController;

	@Autowired
	protected SystemConfigureResourceProxy systemConfigureResourceProxy;

	protected List<SystemConfigureCategoryUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<SystemConfigureCategoryUIModel> systemConfigureCategoryList = new ArrayList<SystemConfigureCategoryUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			SystemConfigureCategoryUIModel systemConfigureCategoryUIModel = new SystemConfigureCategoryUIModel();
			SystemConfigureCategory systemConfigureCategory = (SystemConfigureCategory) rawNode;
			systemConfigureCategoryManager.convSystemConfigureCategoryToUI(
					systemConfigureCategory, systemConfigureCategoryUIModel);
			systemConfigureCategoryList.add(systemConfigureCategoryUIModel);
		}
		return systemConfigureCategoryList;
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		try {			
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			SystemConfigureCategorySearchModel systemConfigureCategorySearchModel = new SystemConfigureCategorySearchModel();
			List<ServiceEntityNode> rawList = systemConfigureCategoryManager
					.searchInternal(systemConfigureCategorySearchModel,
							logonUser.getClient());
			List<SystemConfigureCategoryUIModel> systemConfigureCategoryUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(systemConfigureCategoryUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
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
			SystemConfigureCategorySearchModel systemConfigureCategorySearchModel = (SystemConfigureCategorySearchModel) JSONObject
					.toBean(jsonObject,
							SystemConfigureCategorySearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = systemConfigureCategoryManager
					.searchInternal(systemConfigureCategorySearchModel,
							logonUser.getClient());
			List<SystemConfigureCategoryUIModel> systemConfigureCategoryUIModelList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(systemConfigureCategoryUIModelList);
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

	public List<ServiceEntityNode> searchSystemConfigureElementTree(
			SystemConfigureElementSearchModel searchModel, String client)
			throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		List<ServiceEntityNode> rawList = systemConfigureCategoryManager
				.searchConfigureElementInternal(searchModel, client);
		List<ServiceEntityNode> allRawList = getAllSystemConfigure(client);
		if (rawList == null || rawList.size() == 0) {
			return null;
		}
		addToRootConfigureList(rawList, allRawList);
		return rawList;
	}

	public List<ServiceEntityNode> addToRootConfigureList(
			List<ServiceEntityNode> rawList, List<ServiceEntityNode> allRawList)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawList) {
			SystemConfigureElement systemConfigureElement = (SystemConfigureElement) seNode;
			resultList.add(systemConfigureElement);
			addToRootConfigureUnion(systemConfigureElement, resultList,
					allRawList);
		}
		return resultList;
	}

	protected void addToRootConfigureUnion(
			SystemConfigureElement systemConfigureElement,
			List<ServiceEntityNode> resultList,
			List<ServiceEntityNode> allRawList)
			throws ServiceEntityConfigureException {
		ServiceEntityNode systemConfigureResource = getSystemConfigureOnLine(
				systemConfigureElement.getParentNodeUUID(), allRawList);
		bsearchService.mergeToRawList(resultList, systemConfigureResource);
		if (systemConfigureResource != null) {
			ServiceEntityNode systemConfigureCategory = getSystemConfigureOnLine(
					systemConfigureResource.getParentNodeUUID(), allRawList);
			bsearchService.mergeToRawList(resultList, systemConfigureCategory);
		}
	}

	protected ServiceEntityNode getSystemConfigureOnLine(String uuid,
			List<ServiceEntityNode> allRawList) {
		for (ServiceEntityNode seNode : allRawList) {
			if (uuid.equals(seNode.getUuid())) {
				return seNode;
			}
		}
		return null;
	}

	/**
	 * [Internal method] get all the system configure resources list
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected List<ServiceEntityNode> getAllSystemConfigure(String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<ServiceEntityNode> systemConfigureCategoryList = systemConfigureCategoryManager
				.getEntityNodeListByKey(null, null,
						SystemConfigureCategory.NODENAME, client, null);
		if (systemConfigureCategoryList != null
				&& systemConfigureCategoryList.size() > 0) {
			resultList.addAll(systemConfigureCategoryList);
		}
		List<ServiceEntityNode> systemConfigureResourceList = systemConfigureCategoryManager
				.getEntityNodeListByKey(null, null,
						SystemConfigureResource.NODENAME, client, null);
		if (systemConfigureResourceList != null
				&& systemConfigureResourceList.size() > 0) {
			resultList.addAll(systemConfigureResourceList);
		}
		List<ServiceEntityNode> systemConfigureElementList = systemConfigureCategoryManager
				.getEntityNodeListByKey(null, null,
						SystemConfigureElement.NODENAME, client, null);
		if (systemConfigureElementList != null
				&& systemConfigureElementList.size() > 0) {
			resultList.addAll(systemConfigureElementList);
		}
		return resultList;
	}


}
