package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigParaFactory;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConsumerUnionManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "serviceDocConfigureListController")
@RequestMapping(value = "/serviceDocConfigure")
public class ServiceDocConfigureListController extends SEListController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_SYSTEMCONFIG;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceDocConfigParaFactory serviceDocConfigParaFactory;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected SearchProxyConfigManager searchProxyConfigManager;


	public List<ServiceDocConfigureUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ServiceDocConfigureUIModel> serviceDocConfigureList = new ArrayList<>();
		for (ServiceEntityNode rawNode : rawList) {
			ServiceDocConfigureUIModel serviceDocConfigureUIModel = new ServiceDocConfigureUIModel();
			ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) rawNode;
			serviceDocConfigureManager.convServiceDocConfigureToUI(
					serviceDocConfigure, serviceDocConfigureUIModel);
			SearchProxyConfig searchProxyConfig = (SearchProxyConfig) searchProxyConfigManager
					.getEntityNodeByKey(serviceDocConfigure.getResourceID(),
							"uuid", SearchProxyConfig.NODENAME, null);
			serviceDocConfigureManager.convSearchProxyConfigToUI(
					searchProxyConfig, serviceDocConfigureUIModel);
			ServiceDocConsumerUnion inputModule = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(
							serviceDocConfigure.getInputUnionUUID(), "uuid",
							ServiceDocConsumerUnion.NODENAME, null);
			serviceDocConfigureManager.convInputConsumerUnionToUI(inputModule, serviceDocConfigureUIModel);
			ServiceDocConsumerUnion outputModule = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
					.getEntityNodeByKey(
							serviceDocConfigure.getConsumerUnionUUID(), "uuid",
							ServiceDocConsumerUnion.NODENAME, null);
			serviceDocConfigureManager.convOutputModuleToUI(outputModule, serviceDocConfigureUIModel);
			serviceDocConfigureList.add(serviceDocConfigureUIModel);
		}
		return serviceDocConfigureList;
	}


	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				ServiceDocConfigureSearchModel.class, searchContext -> serviceDocConfigureManager
						.getSearchProxy().searchDocList(searchContext));
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, ServiceDocConfigureSearchModel.class, searchContext -> serviceDocConfigureManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				ServiceDocConfigureSearchModel.class, searchContext -> serviceDocConfigureManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				ServiceDocConfigureSearchModel.class, searchContext -> serviceDocConfigureManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}




}
