package com.company.IntelligentPlatform.finance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.finance.dto.AccountObjectFinUIModel;
import com.company.IntelligentPlatform.finance.dto.AccountToFinanceSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountUIModel;
import com.company.IntelligentPlatform.finance.service.AccountObjectFinManager;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.platform.dto.AccountServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.service.CustomerManager;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.dto.AccountUIModel;
import com.company.IntelligentPlatform.platform.service.AccountManager;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.ServiceJSONParser;
import com.company.IntelligentPlatform.platform.service.BsearchService;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.Account;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNodeLastUpdateTimeCompare;
import com.company.IntelligentPlatform.platform.model.LogonUser;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "accountInFinListController")
@RequestMapping(value = "/accountFin")
public class AccountListController extends SEListController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected CustomerManager customerManager;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected AccountObjectFinManager accountObjectFinManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected AccountServiceUIModelExtension accountServiceUIModelExtension;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String AOID_RESOURCE = FinanceAccountListController.AOID_RESOURCE;

	protected List<FinAccountUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(FinAccountUIModel.class, rawList,
				accountManager, accountServiceUIModelExtension);
	}

	@RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleListService() {
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST,
				ServiceEntityStringHelper.EMPTYSTRING,
				FinAccountSearchModel.class, searchContext -> finAccountManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchModuleService(@RequestBody String request) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(request);
			AccountToFinanceSearchModel accountSearchModel = (AccountToFinanceSearchModel) JSONObject
					.toBean(jsonObject, AccountToFinanceSearchModel.class);
			accountSearchModel.setRecordStatus(FinAccount.RECORDED_UNDONE);
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = accountObjectFinManager
					.searchToFinAccount(accountSearchModel,
							logonUser.getClient());
			Collections.sort(rawList,
					new ServiceEntityNodeLastUpdateTimeCompare());
			List<AccountObjectFinUIModel> finAccountUIModelList = accountObjectFinManager
					.getPayReceiveListCore(rawList, null,
							logonActionController.getLogonInfo());
			String result = ServiceJSONParser
					.genDefOKJSONArray(finAccountUIModelList);
			return result;
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			String result = this
					.searchTableCore(
							request,
							logonUser.getClient(),
							new ServiceEntityNodeLastUpdateTimeCompare(),
							AccountToFinanceSearchModel.class,
							searchModel -> {
								AccountToFinanceSearchModel accountToFinanceSearchModel = (AccountToFinanceSearchModel) searchModel;
								try {
									List<ServiceEntityNode> rawList = accountObjectFinManager
											.searchAccount(
													accountToFinanceSearchModel,
													logonUser.getClient());
									return rawList;
								} catch (SearchConfigureException | ServiceEntityInstallationException e) {
									throw e;
								} catch (NodeNotFoundException | ServiceEntityConfigureException e) {
									throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
								}
							},
							rawList -> {
								try {
									AccountToFinanceSearchModel accountToFinanceSearchModel =
									parseRequestToSearchModel(
													request,
													AccountToFinanceSearchModel.class);
									List<ServiceEntityNode> rawFinAccountList = accountObjectFinManager
											.getRawFinAccListFromAccSearch(
													accountToFinanceSearchModel,
													logonUser.getClient());
									List<AccountObjectFinUIModel> finAccountUIModelList = accountObjectFinManager
											.getPayReceiveListCore(rawList,
													rawFinAccountList,
													logonActionController
															.getLogonInfo());
									return finAccountUIModelList;
								} catch (Exception e) {
									logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
									return null;
								}
							});
			return result;
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | IOException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch ( ServiceModuleProxyException | SearchConfigureException  e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (DocActionException e) {
            throw new RuntimeException(e);
        }
    }
}
