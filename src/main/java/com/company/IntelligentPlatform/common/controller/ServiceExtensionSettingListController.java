package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ServiceExtensionSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceExtensionSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.ServiceExtensionSettingUIModel;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "serviceExtensionSettingListController")
@RequestMapping(value = "/serviceExtensionSetting")
public class ServiceExtensionSettingListController extends SEListController {

	public static final String AOID_RESOURCE = ServiceExtensionSettingEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceExtensionSettingServiceUIModelExtension serviceExtensionSettingServiceUIModelExtension;

	protected List<ServiceExtensionSettingUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(ServiceExtensionSettingUIModel.class, rawList,
				serviceExtensionSettingManager, serviceExtensionSettingServiceUIModelExtension, null);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, ServiceExtensionSettingSearchModel.class, searchContext -> serviceExtensionSettingManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				ServiceExtensionSettingSearchModel.class, searchContext -> serviceExtensionSettingManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				ServiceExtensionSettingSearchModel.class, searchContext -> serviceExtensionSettingManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

}
