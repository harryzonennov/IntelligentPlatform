package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
// TODO-DAO: import platform.foundation.DAO.PageSplitHelper;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocConsumerUnionManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;

@Scope("session")
@Controller(value = "serviceDocConsumerUnionListController")
@RequestMapping(value = "/serviceDocConsumerUnion")
public class ServiceDocConsumerUnionListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ServiceDocConsumerUnionUIModel.class.getResource("")
				.getPath();
		String resFileName = ServiceDocConsumerUnion.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "ServiceDocConsumerUnionList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "ServiceDocConsumerUnionChooser";
		}
		return "ServiceDocConsumerUnionList";
	}

	protected List<ServiceDocConsumerUnionUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceDocConsumerUnionUIModel> serviceDocConsumerUnionList = new ArrayList<ServiceDocConsumerUnionUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel = new ServiceDocConsumerUnionUIModel();
			ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) rawNode;
			serviceDocConsumerUnionManager.convServiceDocConsumerUnionToUI(
					serviceDocConsumerUnion, serviceDocConsumerUnionUIModel);
			serviceDocConsumerUnionList.add(serviceDocConsumerUnionUIModel);
		}
		return serviceDocConsumerUnionList;
	}

	@RequestMapping(value = "/loadList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadList() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			ServiceDocConsumerUnionSearchModel serviceDocConsumerUnionSearchModel = new ServiceDocConsumerUnionSearchModel();
			List<ServiceEntityNode> rawList = serviceDocConsumerUnionManager.searchInternal(
					serviceDocConsumerUnionSearchModel, logonUser.getClient());
//			List<ServiceSelect2Model> resultList = ServiceSelect2ModelProxy
//					.convSeNodeToSelect2ModelWithName(rawList);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
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
			ServiceDocConsumerUnionSearchModel searchModel = new ServiceDocConsumerUnionSearchModel();
			List<ServiceEntityNode> rawList = serviceDocConsumerUnionManager.searchInternal(searchModel,
					logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());			
			String result = ServiceJSONParser
					.genDefOKJSONArray(rawList);
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
			ServiceDocConsumerUnionSearchModel searchModel = new ServiceDocConsumerUnionSearchModel();
			List<ServiceEntityNode> rawList = serviceDocConsumerUnionManager.searchInternal(searchModel,
					logonUser.getClient());
			List<ServiceDocConsumerUnionUIModel> serviceDocConsumerUnionList = new ArrayList<ServiceDocConsumerUnionUIModel>();
			for (ServiceEntityNode rawNode : rawList) {
				ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel = new ServiceDocConsumerUnionUIModel();
				ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) rawNode;
				serviceDocConsumerUnionManager
						.convServiceDocConsumerUnionToUI(
								serviceDocConsumerUnion,
								serviceDocConsumerUnionUIModel);
				serviceDocConsumerUnionList.add(serviceDocConsumerUnionUIModel);
			}
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceDocConsumerUnionList);
			return result;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(
			@RequestBody ServiceDocConsumerUnionSearchModel searchModel) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceDocConsumerUnionManager.searchInternal(searchModel,
					logonUser.getClient());
			List<ServiceDocConsumerUnionUIModel> serviceDocConsumerUnionList = new ArrayList<ServiceDocConsumerUnionUIModel>();
			for (ServiceEntityNode rawNode : rawList) {
				ServiceDocConsumerUnionUIModel serviceDocConsumerUnionUIModel = new ServiceDocConsumerUnionUIModel();
				ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) rawNode;
				serviceDocConsumerUnionManager
						.convServiceDocConsumerUnionToUI(
								serviceDocConsumerUnion,
								serviceDocConsumerUnionUIModel);
				serviceDocConsumerUnionList.add(serviceDocConsumerUnionUIModel);
			}
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceDocConsumerUnionList);
			return result;
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}


}
