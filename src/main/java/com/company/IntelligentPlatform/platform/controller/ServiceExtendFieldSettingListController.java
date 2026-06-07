package com.company.IntelligentPlatform.platform.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.ServiceExtendFieldSettingSearchModel;
import com.company.IntelligentPlatform.platform.dto.ServiceExtendFieldSettingUIModel;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.ServiceExtendFieldSettingManager;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceExtendFieldSetting;

@Scope("session")
@Controller(value = "serviceExtendFieldSettingListController")
@RequestMapping(value = "/serviceExtendFieldSetting")
public class ServiceExtendFieldSettingListController extends SEListController {

	public static final String AOID_RESOURCE = ServiceExtendFieldSettingEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;

	protected List<ServiceExtendFieldSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceExtendFieldSettingUIModel> serviceExtendFieldSettingList = new ArrayList<ServiceExtendFieldSettingUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel = new ServiceExtendFieldSettingUIModel();
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) rawNode;
			serviceExtendFieldSettingManager.convServiceExtendFieldSettingToUI(
					serviceExtendFieldSetting,
					serviceExtendFieldSettingUIModel, logonInfo);
			serviceExtendFieldSettingList.add(serviceExtendFieldSettingUIModel);
		}
		return serviceExtendFieldSettingList;
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceExtendFieldSettingSearchModel serviceExtendFieldSettingSearchModel = new ServiceExtendFieldSettingSearchModel();
			List<ServiceEntityNode> rawList = serviceExtendFieldSettingManager
					.searchInternal(serviceExtendFieldSettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			String result = ServiceJSONParser.genDefOKJSONArray(rawList);
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
			ServiceExtendFieldSettingSearchModel serviceExtendFieldSettingSearchModel = new ServiceExtendFieldSettingSearchModel();
			List<ServiceEntityNode> rawList = serviceExtendFieldSettingManager
					.searchInternal(serviceExtendFieldSettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceExtendFieldSettingUIModel> serviceExtendFieldSettingUIModelList = getModuleListCore(
					rawList, logonActionController.getLogonInfo());
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceExtendFieldSettingUIModelList);
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
			ServiceExtendFieldSettingSearchModel serviceExtendFieldSettingSearchModel = (ServiceExtendFieldSettingSearchModel) JSONObject
					.toBean(jsonObject,
							ServiceExtendFieldSettingSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceExtendFieldSettingManager
					.searchInternal(serviceExtendFieldSettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceExtendFieldSettingUIModel> serviceExtendFieldSettingUIModelList = getModuleListCore(
					rawList, logonActionController.getLogonInfo());
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceExtendFieldSettingUIModelList);
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

	@RequestMapping(value = "/searchLeanModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchLeanModuleService(
			@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			ServiceExtendFieldSettingSearchModel serviceExtendFieldSettingSearchModel = (ServiceExtendFieldSettingSearchModel) JSONObject
					.toBean(jsonObject,
							ServiceExtendFieldSettingSearchModel.class);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceExtendFieldSettingManager
					.searchInternal(serviceExtendFieldSettingSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			String result = ServiceJSONParser.genDefOKJSONArray(rawList);
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

}
