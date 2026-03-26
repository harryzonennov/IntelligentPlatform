package com.company.IntelligentPlatform.finance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.FinAccountExcelModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountServiceUIModelExtension;
import com.company.IntelligentPlatform.finance.dto.FinAccountSettleCenterUIModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountExcelReportProxy;
import com.company.IntelligentPlatform.finance.service.FinAccountException;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "financeAccontListController")
@RequestMapping(value = "/finAccount")
public class FinanceAccountListController extends SEListController {

	@Autowired
	FinAccountManager finAccountManager;

	@Autowired
	FinAccountTitleManager finAccountTitleManager;

	@Autowired
	LogonUserManager logonUserManager;

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	LogonActionController logonActionController;

	@Autowired
	FinanceAccountMessageHelper financeAccountMessageHelper;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected FinAccountExcelReportProxy finAccountExcelReportProxy;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	@Autowired
	protected FinAccountServiceUIModelExtension finAccountServiceUIModelExtension;
	
	protected Logger logger = LoggerFactory.getLogger(FinanceAccountListController.class);

	public final static String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_FINACCOUNT;

	protected List<FinAccountUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(FinAccountUIModel.class, rawList,
				finAccountManager, finAccountServiceUIModelExtension);
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
		return serviceBasicUtilityController.searchModuleWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				FinAccountSearchModel.class, searchContext -> finAccountManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/searchTableService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String searchTableService(@RequestBody String request) {
		return serviceBasicUtilityController.searchTableServiceWrapper(AOID_RESOURCE, ISystemActionCode.ACID_LIST, request,
				this, FinAccountSearchModel.class, searchContext -> finAccountManager
						.getSearchProxy().searchDocList(searchContext),  this::getModuleListCore);
	}

	@RequestMapping(value = "/loadFinAccountObjectSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadFinAccountObjectSelectList() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = null;
			// List<ServiceEntityNode> rawList = corporateCustomerManager
			// .getEntityNodeListByKey(null, null,
			// CorporateCustomer.NODENAME, null);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadCashierSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadCashierSelectList() {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_LIST);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = logonUserManager
					.getEntityNodeListByKey(null, null, LogonUser.NODENAME,
							null);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadFinAccountTitleSelectList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadFinAccountTitleSelectList() {
		try {
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> rawList = finAccountTitleManager
					.getEntityNodeListByKey(null, null,
							FinAccountTitle.NODENAME, null);
			return ServiceJSONParser.genDefOKJSONArray(rawList);
		} catch (LogonInfoException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
	}

	@RequestMapping(value = "/loadFinAccObjectSettleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadFinAccObjectSettleService(String uuid) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			List<ServiceEntityNode> allFinTitleList = finAccountTitleManager
					.getEntityNodeListByKey(null, null,
							FinAccountTitle.NODENAME, logonUser.getClient(),
							null);
			Account account = (Account) accountManager.getEntityNodeByKey(uuid,
					IServiceEntityNodeFieldConstant.UUID, Account.NODENAME,
					logonUser.getClient(), null);
			FinAccountSearchModel finAccountSearchModel = new FinAccountSearchModel();
			finAccountSearchModel.setAccountObjectUUID(uuid);
			finAccountSearchModel.setRecordStatus(FinAccount.RECORDED_UNDONE);
			List<ServiceEntityNode> rawAccountList = null;
			// TODO replace this logic
//			List<ServiceEntityNode> rawAccountList = finAccountManager
//					.getSearchProxy().searchDocList(finAccountSearchModel,
//							logonUser.getClient());
			List<FinAccountUIModel> accountUIModelsList = new ArrayList<>();
			for (ServiceEntityNode rawNode : rawAccountList) {
				try {
					FinAccountUIModel finAccountUIModel = finAccountManager
							.convToUIModelUnion(rawNode, logonUser,
									allFinTitleList, true);
					accountUIModelsList.add(finAccountUIModel);
				} catch (FinAccountException e) {
					continue;
				}
			}
			FinAccountSettleCenterUIModel finAccountSettleCenterUIModel = finAccountManager
					.genFinAccountSettleCenterUIModel(rawAccountList, true,
							logonUser.getClient(), allFinTitleList);
			convAccObjectToSettleUIModel(account, finAccountSettleCenterUIModel);
			return ServiceJSONParser
					.genDefOKJSONObject(finAccountSettleCenterUIModel);
		} catch (LogonInfoException | AuthorizationException e) {
			return e.generateSimpleErrorJSON();
		} catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		}
    }

	public void convAccObjectToSettleUIModel(Account account,
			FinAccountSettleCenterUIModel finAccountSettleCenterUIModel)
			throws ServiceEntityInstallationException {
		if (account != null && finAccountSettleCenterUIModel != null) {
			finAccountSettleCenterUIModel.setAccountObjectId(account.getId());
			finAccountSettleCenterUIModel.setAccountObjectName(account
					.getName());
			finAccountSettleCenterUIModel.setAccountObjectType(account
					.getAccountType());
			finAccountSettleCenterUIModel.setAccountObjectUUID(account
					.getUuid());			
			Map<Integer, String> accountTypeMap = accountManager
					.getAccountTypeMap(logonActionController.getLanguageCode(), false);
			finAccountSettleCenterUIModel
					.setAccountObjectTypeValue(accountTypeMap.get(account
							.getAccountType()));
		}
	}
	

	@RequestMapping(value = "/preGenExcelReport", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preGenExcelReport() {
		String response = serviceBasicUtilityController.preCheckResourceAccess(
				AOID_RESOURCE, ISystemActionCode.ACID_EXCEL);
		return response;
	}

	
	public void convToExcelUIModel(FinAccountUIModel finAccountUIModel,
			FinAccountExcelModel finAccountExcelModel) {
		finAccountExcelModel.setId(finAccountUIModel.getId());
		finAccountExcelModel.setAccountObjectName(finAccountUIModel
				.getAccountObjectName());
		finAccountExcelModel.setPaymentTypeValue(finAccountUIModel
				.getPaymentTypeValue());
		finAccountExcelModel.setAccountTitleName(finAccountUIModel
				.getAccountTitleName());
		finAccountExcelModel.setDocumentId(finAccountUIModel.getDocumentId());
		finAccountExcelModel.setDateStr(finAccountUIModel.getDateStr());
		finAccountExcelModel.setFinAccountType(finAccountUIModel
				.getFinAccountType());
		finAccountExcelModel.setFinAccountTypeValue(finAccountUIModel
				.getFinAccountTypeValue());
		finAccountExcelModel.setDocumentTypeValue(finAccountUIModel
				.getDocumentTypeValue());
		finAccountExcelModel.setAuditStatusValue(finAccountUIModel
				.getAuditStatusValue());
		finAccountExcelModel.setVerifyStatusValue(finAccountUIModel
				.getVerifyStatusValue());
		finAccountExcelModel.setRecordStatusValue(finAccountUIModel
				.getRecordStatusValue());
		finAccountExcelModel.setRecordedAmount(finAccountUIModel
				.getRecordedAmount());
		finAccountExcelModel.setToRecordAmount(finAccountUIModel
				.getToRecordAmount());
		finAccountExcelModel.setAmount(finAccountUIModel.getAmount());
	}

}
