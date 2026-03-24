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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;

@Scope("session")
@Controller(value = "serviceDocConfigureParaGroupListController")
@RequestMapping(value = "/serviceDocConfigureParaGroup")
public class ServiceDocConfigureParaGroupListController extends
		SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;
	
	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;


	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	protected Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = Locale.getDefault();
		String path = ServiceDocConfigureParaGroupUIModel.class.getResource("")
				.getPath();
		String resFileName = ServiceDocConfigureParaGroup.NODENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "ServiceDocConfigureParaGroupList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "ServiceDocConfigureParaGroupChooser";
		}
		return "ServiceDocConfigureParaGroupList";
	}

	protected List<ServiceDocConfigureParaGroupUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupList = new ArrayList<ServiceDocConfigureParaGroupUIModel>();		
		for (ServiceEntityNode rawNode : rawList) {
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel = new ServiceDocConfigureParaGroupUIModel();
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) rawNode;
			serviceDocConfigureManager.convServiceDocConfigureParaGroupToUI(
					serviceDocConfigureParaGroup,
					serviceDocConfigureParaGroupUIModel);
			serviceDocConfigureParaGroupList
					.add(serviceDocConfigureParaGroupUIModel);
		}
		return serviceDocConfigureParaGroupList;
	}

	
	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService(String baseUUID) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceDocConfigureManager
					.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());			
			String result = ServiceJSONParser
					.genDefOKJSONArray(rawList);
			return result;
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService(String baseUUID) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = serviceDocConfigureManager
					.getEntityNodeListByKey(baseUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							ServiceDocConfigureParaGroup.NODENAME,
							logonUser.getClient(), null);
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceDocConfigureParaGroupList);
			return result;
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}


}
