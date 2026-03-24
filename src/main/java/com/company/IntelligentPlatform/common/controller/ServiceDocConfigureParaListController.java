package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

@Scope("session")
@Controller(value = "serviceDocConfigureParaListController")
@RequestMapping(value = "/serviceDocConfigurePara")
public class ServiceDocConfigureParaListController extends SEListController {

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
	
	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	protected List<ServiceDocConfigureParaUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceDocConfigureParaUIModel> serviceDocConfigureParaList = new ArrayList<ServiceDocConfigureParaUIModel>();
		Map<Integer, String> documentTypeMap = serviceDocumentComProxy
				.getSearchDocumentTypeMap();
		Map<Integer, String> switchMap = standardSwitchProxy.getSimpleSwitchMap();
		for (ServiceEntityNode rawNode : rawList) {
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel = new ServiceDocConfigureParaUIModel();
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) rawNode;
			serviceDocConfigureManager.convServiceDocConfigureParaToUI(
					serviceDocConfigurePara, serviceDocConfigureParaUIModel, documentTypeMap, switchMap);
			serviceDocConfigureParaList.add(serviceDocConfigureParaUIModel);
		}
		return serviceDocConfigureParaList;
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
							ServiceDocConfigurePara.NODENAME,
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
							ServiceDocConfigurePara.NODENAME,
							logonUser.getClient(), null);
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<ServiceDocConfigureParaUIModel> serviceDocConfigureParaList = getModuleListCore(rawList);
			String result = ServiceJSONParser
					.genDefOKJSONArray(serviceDocConfigureParaList);
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
