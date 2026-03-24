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
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "navigationItemSettingListController")
@RequestMapping(value = "/navigationItemSetting")
public class NavigationItemSettingListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected NavigationItemSettingManager navigationItemSettingManager;
	
	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	@Autowired
	protected NavigationItemSettingServiceUIModelExtension navigationItemSettingServiceUIModelExtension;

	@RequestMapping(value = "/loadNavigationItemSettingSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadNavigationItemSettingSelectList() {
		return serviceBasicUtilityController.getListMeta(() -> navigationSystemSettingManager
				.getEntityNodeListByKey(null, null,
						NavigationItemSetting.NODENAME, null), AOID_RESOURCE, ISystemActionCode.ACID_LIST);
	}

	protected List<NavigationItemSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(NavigationItemSettingUIModel.class, rawList,
				navigationSystemSettingManager, navigationItemSettingServiceUIModelExtension);
	}
	
	@RequestMapping(value = "/searchAllTopItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchAllTopItemList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getListMeta(() -> navigationItemSettingManager
				.getAllTopItemList(request.getBaseUUID(), logonActionController.getClient()), AOID_RESOURCE, ISystemActionCode.ACID_LIST,  this::getModuleListCore);
	}

	@RequestMapping(value = "/getAllChildItemList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAllChildItemList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getListMeta(() -> navigationItemSettingManager
				.getAllChildItemList(request.getBaseUUID(), logonActionController.getClient()), AOID_RESOURCE, ISystemActionCode.ACID_LIST,  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				NavigationItemSettingSearchModel.class, searchContext -> navigationSystemSettingManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				NavigationItemSettingSearchModel.class, searchContext -> navigationSystemSettingManager
						.getSearchProxy().searchItemList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchActiveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchActiveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				NavigationItemSettingSearchModel.class, searchContext -> {
                    NavigationItemSettingSearchModel navigationItemSettingSearchModel = (NavigationItemSettingSearchModel) searchContext.getSearchModel();
                    navigationItemSettingSearchModel.setStatus(NavigationSystemSetting.STATUS_ACTIVE);
                    return navigationSystemSettingManager
                            .getSearchProxy().searchItemList(searchContext);
                }, this::getModuleListCore);
	}

	@RequestMapping(value = "/searchKeywordsService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchKeywordsService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				NavigationItemSettingSearchModel.class, searchContext -> navigationItemSettingManager
                        .getSearchProxy().searchKeywords(searchContext), this::getModuleListCore);
	}

}
