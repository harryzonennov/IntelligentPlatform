package com.company.IntelligentPlatform.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.*;
import com.company.IntelligentPlatform.platform.dto.SerExtendPageSettingSearchModel;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.SerExtendPageSettingManager;
import com.company.IntelligentPlatform.platform.service.SerExtendPageSettingServiceModel;
import com.company.IntelligentPlatform.platform.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.platform.service.ServiceExtensionSettingSearchProxy;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "serExtendPageSettingListController")
@RequestMapping(value = "/serExtendPageSetting")
public class SerExtendPageSettingListController extends SEListController {

	public static final String AOID_RESOURCE = SerExtendPageSettingEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SerExtendPageSettingServiceUIModelExtension serExtendPageSettingServiceUIModelExtension;

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceExtensionSettingSearchProxy serviceExtensionSettingSearchProxy;

	protected List<SerExtendPageSettingServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(SerExtendPageSettingServiceUIModel.class,
				SerExtendPageSettingServiceModel.class, rawList,
				serviceExtensionSettingManager, serExtendPageSettingServiceUIModelExtension, logonActionController.getLogonInfo());
	}
	
	@RequestMapping(value = "/searchLeanModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchLeanModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				SerExtendPageSettingSearchModel.class, searchContext -> serviceExtensionSettingSearchProxy.searchPageSettingList(searchContext), null);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, SerExtendPageSettingSearchModel.class, searchContext -> serviceExtensionSettingSearchProxy.searchPageSettingList(searchContext),
				 this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				SerExtendPageSettingSearchModel.class, searchContext -> serviceExtensionSettingSearchProxy.searchPageSettingList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				SerExtendPageSettingSearchModel.class, searchContext -> serviceExtensionSettingSearchProxy.searchPageSettingList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				SerExtendPageSettingSearchModel.class, searchContext -> serviceExtensionSettingSearchProxy.searchPageSettingList(searchContext));
	}

}
