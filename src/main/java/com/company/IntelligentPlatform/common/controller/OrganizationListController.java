package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.OrganizationServiceModel;
import com.company.IntelligentPlatform.common.dto.OrganizationServiceUIModel;
import com.company.IntelligentPlatform.common.dto.OrganizationServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.OrganizationSearchModel;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;

@Scope("session")
@Controller(value = "organizationListController")
@RequestMapping(value = "/organization")
public class OrganizationListController extends SEListController {

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected OrganizationServiceUIModelExtension organizationServiceUIModelExtension;

	public static final String AOID_RESOURCE = IServiceModelConstants.Organization;

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, OrganizationSearchModel.class, searchContext -> organizationManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				OrganizationSearchModel.class, searchContext -> organizationManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				OrganizationSearchModel.class, searchContext -> organizationManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/loadLeanModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadLeanModuleListService() {
		return serviceBasicUtilityController.loadLeanModuleListWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, null,
				OrganizationSearchModel.class, searchContext -> organizationManager
						.getSearchProxy().searchDocList(searchContext));
	}

	protected List<OrganizationServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(OrganizationServiceUIModel.class,
				OrganizationServiceModel.class, rawList,
				organizationManager, organizationServiceUIModelExtension, logonActionController.getLogonInfo());
	}

}
