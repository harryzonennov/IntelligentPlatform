package com.company.IntelligentPlatform.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.dto.AccountServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;
import com.company.IntelligentPlatform.common.dto.AccountUIModel;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Scope("session")
@Controller(value = "accountListController")
@RequestMapping(value = "/account")
public class AccountListController extends SEListController {

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	@Autowired
	protected AccountServiceUIModelExtension accountServiceUIModelExtension;

	//TODO to check this ao id next.
	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_COR_CUSTOMER;
	
	@RequestMapping(value = "/getAccountTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAccountTypeMap() {
		try {			
			Map<Integer, String> accountTypeMap = accountManager.getAccountTypeMap(logonActionController.getLanguageCode(), true);
			return accountManager.getDefaultSelectMap(
					accountTypeMap, false);
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	protected List<AccountUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(AccountUIModel.class, rawList,
				accountManager, accountServiceUIModelExtension);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, AccountSearchModel.class, searchContext -> accountManager
						.searchAccount(searchContext),  this::getModuleListCore);
	}
	
	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				AccountSearchModel.class, searchContext -> accountManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				AccountSearchModel.class, searchContext -> accountManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

}
