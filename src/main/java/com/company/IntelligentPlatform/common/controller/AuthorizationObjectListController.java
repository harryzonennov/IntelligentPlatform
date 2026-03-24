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
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;

@Scope("session")
@Controller(value = "authorizationObjectListController")
@RequestMapping(value = "/authorizationObject")
public class AuthorizationObjectListController extends SEListController {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected LogonActionController logonActionController;
	
	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected AuthorizationObjectServiceUIModelExtension authorizationObjectServiceUIModelExtension;
	
	public static final String AOID_RESOURCE = IServiceModelConstants.AuthorizationObject;

	protected List<AuthorizationObjectServiceUIModel> getServiceModuleListCore(List<ServiceEntityNode> rawList) throws ServiceEntityConfigureException {
		return serviceBasicUtilityController.convServiceUIModuleList(AuthorizationObjectServiceUIModel.class,
				AuthorizationObjectServiceModel.class, rawList,
				authorizationObjectManager, authorizationObjectServiceUIModelExtension, logonActionController.getLogonInfo());
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				AuthorizationObjectSearchModel.class, searchContext -> authorizationObjectManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	//TODO to check if we need to use this on AO select UI: only select cross user AO list
	@RequestMapping(value = "/getCrossUserSysAuthorizationObjectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCrossUserSysAuthorizationObjectList() {
		return serviceBasicUtilityController.getListMeta(() -> {
			try {
				return authorizationObjectManager.getCrossUserSysAuthorizationObjectList(logonActionController.getClient());
			} catch (ServiceEntityConfigureException e) {
				throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
		},AOID_RESOURCE, ISystemActionCode.ACID_LIST);
	}
	
	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				AuthorizationObjectSearchModel.class, searchContext -> authorizationObjectManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, AuthorizationObjectSearchModel.class, searchContext -> authorizationObjectManager
						.getSearchProxy().searchDocList(searchContext),  this::getServiceModuleListCore);
	}

}
