package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ActionCodeManager;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.NavigationSystemSettingManager;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "navigationSystemSettingListController")
@RequestMapping(value = "/navigationSystemSetting")
public class NavigationSystemSettingListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected NavigationSystemSettingServiceUIModelExtension navigationSystemSettingServiceUIModelExtension;

	@Autowired
	protected ActionCodeManager actionCodeManager;

	@RequestMapping(value = "/loadNavigationItemSettingSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadNavigationItemSettingSelectList(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				NavigationSystemSettingSearchModel.class, searchContext -> navigationSystemSettingManager
						.getSearchProxy().searchItemList(searchContext),  null);
	}

	protected List<NavigationSystemSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(NavigationSystemSettingUIModel.class, rawList,
				navigationSystemSettingManager, navigationSystemSettingServiceUIModelExtension);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				NavigationSystemSettingSearchModel.class, searchContext -> navigationSystemSettingManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				NavigationSystemSettingSearchModel.class, searchContext -> navigationSystemSettingManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, NavigationSystemSettingSearchModel.class, searchContext -> navigationSystemSettingManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadGroupAuthorizationObjectSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadGroupAuthorizationObjectSelectList() {
		return serviceBasicUtilityController.getListMeta(() -> authorizationObjectManager
				.getEntityNodeListByKey(null, null,
						AuthorizationObject.NODENAME, null), AOID_RESOURCE, ISystemActionCode.ACID_LIST);
	}

	@RequestMapping(value = "/loadGroupActionCodeSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadGroupActionCodeSelectList() {
		return serviceBasicUtilityController.getListMeta(() -> actionCodeManager
				.getEntityNodeListByKey(null, null,
						AuthorizationObject.NODENAME, null), AOID_RESOURCE, ISystemActionCode.ACID_LIST);
	}

}
