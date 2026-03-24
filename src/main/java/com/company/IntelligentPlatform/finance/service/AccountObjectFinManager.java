package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.company.IntelligentPlatform.finance.dto.AccountObjectFinUIModel;
import com.company.IntelligentPlatform.finance.dto.AccountToFinanceSearchModel;
import com.company.IntelligentPlatform.finance.dto.CorporateCustomerToFinSearchModel;
import com.company.IntelligentPlatform.finance.dto.EmployeeToFinSearchModel;
import com.company.IntelligentPlatform.finance.dto.FinAccountSearchModel;
import com.company.IntelligentPlatform.finance.dto.IndividualCustomerToFinSearchModel;
import com.company.IntelligentPlatform.finance.dto.OrganizationToFinSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateCustomerSearchModel;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class AccountObjectFinManager extends AccountManager {

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected OrganizationToFinSearchProxy organizationToFinSearchProxy;

	@Autowired
	protected CorporateCustomerToFinSearchProxy corporateCustomerToFinSearchProxy;

	@Autowired
	protected IndividualCustomerToFinSearchProxy individualCustomerToFinSearchProxy;

	@Autowired
	protected EmployeeToFinSearchProxy employeeToFinSearchProxy;

	@Autowired
	protected CorporateSupplierToFinSearchProxy corporateSupplierToFinSearchProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<ServiceEntityNode> getFinAccountInfoByAccObject(
			List<ServiceEntityNode> rawFinAccountList,
			List<ServiceEntityNode> rawFinAccReferenceList, Account account) {
		if (ServiceCollectionsHelper.checkNullList(rawFinAccountList)) {
			return null;
		}
		List<ServiceEntityNode> finAccReferenceList = ServiceCollectionsHelper
				.filterSENodeListOnline(account.getUuid(),
						IReferenceNodeFieldConstant.REFUUID,
						rawFinAccReferenceList, false);
		if (ServiceCollectionsHelper.checkNullList(finAccReferenceList)) {
			return null;
		}
		List<ServiceEntityNode> finAccountList = new ArrayList<>();
		for (ServiceEntityNode seNode : finAccReferenceList) {
			FinAccountObjectRef finAccountObjectRef = (FinAccountObjectRef) seNode;
			FinAccount finAccount = (FinAccount) ServiceCollectionsHelper
					.filterSENodeOnline(
							finAccountObjectRef.getParentNodeUUID(),
							rawFinAccountList);
			if (finAccount != null) {
				finAccountList.add(finAccount);
			}
		}
		if (ServiceCollectionsHelper.checkNullList(finAccountList)) {
			return null;
		}
		return finAccountList;
	}

	private List<ServiceEntityNode> getAllFinAccReferenceList(
			List<ServiceEntityNode> rawFinAccountList)
			throws ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(rawFinAccountList)) {
			return null;
		}
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		List<String> multipleValueList = new ArrayList<>();
		rawFinAccountList.forEach(seNode -> {
			multipleValueList.add(seNode.getUuid());
		});
		key1.setMultipleValueList(multipleValueList);
		return finAccountManager.getEntityNodeListByKeyList(
				ServiceCollectionsHelper.asList(key1), FinAccountObjectRef.NODENAME, null);
	}

	/**
	 * Batch Convert account object list into UI Model
	 * 
	 * @param rawList
	 * @param rawFinAccountList
	 * @param rawFinAccReferenceList
	 * @param logonInfo
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public List<AccountObjectFinUIModel> getPayReceiveListCore(
			List<ServiceEntityNode> rawList,
			List<ServiceEntityNode> rawFinAccountList, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		List<AccountObjectFinUIModel> accountList = new ArrayList<AccountObjectFinUIModel>();
		List<ServiceEntityNode> rawFinAccObjectList = null;
		if (!ServiceCollectionsHelper.checkNullList(rawFinAccountList)) {
			try {
				rawFinAccObjectList = getAllFinAccReferenceList(rawFinAccountList);
			} catch (ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, "getPayReceiveListCore"));
			}
		}
		for (ServiceEntityNode rawNode : rawList) {
			AccountObjectFinUIModel accountObjectFinUIModel = new AccountObjectFinUIModel();
			Account account = (Account) rawNode;
			accountManager.convAccountToUI(account, accountObjectFinUIModel,
					logonInfo);
			if (!ServiceCollectionsHelper.checkNullList(rawFinAccountList)) {
				// In case need to merge finAccount information
				List<ServiceEntityNode> finAccountList = getFinAccountInfoByAccObject(
						rawFinAccountList, rawFinAccObjectList, account);
				mergeFinAccListToAccountObjectUIModel(accountObjectFinUIModel,
						finAccountList);
			}
			accountList.add(accountObjectFinUIModel);
		}
		return accountList;
	}

	private void mergeFinAccListToAccountObjectUIModel(
			AccountObjectFinUIModel accountObjectFinUIModel,
			List<ServiceEntityNode> finAccountList) {
		if (ServiceCollectionsHelper.checkNullList(finAccountList)) {
			return;
		}
		double grossAmount = 0, grossRecordedAmount = 0, grossToRecorededAmount = 0;
		for (ServiceEntityNode rawSENode : finAccountList) {
			FinAccount finAccount = (FinAccount) rawSENode;
			grossAmount += finAccount.getAmount();
			grossRecordedAmount += finAccount.getRecordedAmount();
			grossToRecorededAmount += finAccount.getToRecordAmount();
		}
		accountObjectFinUIModel.setFinAccountRecord(finAccountList.size());
		accountObjectFinUIModel.setGrossAmount(grossAmount);
		accountObjectFinUIModel.setGrossRecordedAmount(grossRecordedAmount);
		accountObjectFinUIModel
				.setGrossToRecorededAmount(grossToRecorededAmount);
	}

	/**
	 * Search Fin Account list by AccountToFinanceSearchModel
	 * 
	 * @param accountToFinanceSearchModel
	 * @return
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> getRawFinAccListFromAccSearch(
			AccountToFinanceSearchModel accountToFinanceSearchModel,
			String client) throws SearchConfigureException,
			ServiceEntityInstallationException,
			ServiceEntityConfigureException, NodeNotFoundException {
		FinAccountSearchModel finAccountSearchModel = genFinAccountSearch(accountToFinanceSearchModel);
		List<ServiceEntityNode> rawFinAccountList = finAccountManager
				.searchFinRootCore(finAccountSearchModel, client);
		return rawFinAccountList;
	}

	/**
	 * [Internal method] Generate / convert accountToFinanceSearchModel to
	 * FinAccountSearchModel
	 * 
	 * @param accountToFinanceSearchModel
	 * @return
	 */
	private FinAccountSearchModel genFinAccountSearch(
			AccountToFinanceSearchModel accountToFinanceSearchModel) {
		FinAccountSearchModel finAccountSearchModel = new FinAccountSearchModel();
		finAccountSearchModel.setAuditStatus(accountToFinanceSearchModel
				.getAuditStatus());
		finAccountSearchModel.setVerifyStatus(accountToFinanceSearchModel
				.getVerifyStatus());
		finAccountSearchModel.setRecordStatus(accountToFinanceSearchModel
				.getRecordStatus());
		finAccountSearchModel.setAccountObjectId(accountToFinanceSearchModel
				.getId());
		finAccountSearchModel.setAccountantName(accountToFinanceSearchModel
				.getName());
		finAccountSearchModel.setAccountObjectType(accountToFinanceSearchModel
				.getAccountType());
		finAccountSearchModel.setAccountObjectUUID(accountToFinanceSearchModel
				.getUuid());
		return finAccountSearchModel;
	}

	/**
	 * Core Logic to search account object
	 *
	 * @param client
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 */
	public List<ServiceEntityNode> searchAccount(
			AccountToFinanceSearchModel accountToFinSearchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_COR_CUSTOMER) {
//			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
//					accountToFinSearchModel,
//					CorporateCustomerToFinSearchModel.class, AccountSearchModel.class);
//			return corporateCustomerToFinSearchProxy.searchDocList(
//					accountSearchModelImp, client);
//		}
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_IND_CUSTOMER) {
//			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
//					accountToFinSearchModel, IndividualCustomerToFinSearchModel.class, AccountSearchModel.class);
//			return individualCustomerToFinSearchProxy.searchDocList(
//					accountSearchModelImp, client);
//		}
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_TRANSIT_PARTNER) {
//			return null;
//		}
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_SUPPLIER) {
//			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
//					accountToFinSearchModel, CorporateCustomerToFinSearchModel.class, AccountSearchModel.class);
//			return corporateSupplierToFinSearchProxy.searchDocList(
//					accountSearchModelImp, client);
//		}
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_EMPLOYEE) {
//			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
//					accountToFinSearchModel, EmployeeToFinSearchModel.class, AccountSearchModel.class);
//			return employeeToFinSearchProxy.searchDocList(
//					accountSearchModelImp, client);
//		}
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_EXTER_DRIVER) {
//			return null;
//		}
//		if (accountToFinSearchModel.getAccountType() == Account.ACCOUNTTYPE_ORGANIZATION) {
//			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
//					accountToFinSearchModel, OrganizationToFinSearchModel.class, AccountSearchModel.class);
//			return organizationToFinSearchProxy.searchDocList(
//					accountSearchModelImp, client);
//		}
//		AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
//				accountToFinSearchModel, CorporateCustomerSearchModel.class, AccountSearchModel.class);
//		return corporateCustomerToFinSearchProxy.searchDocList(
//				accountSearchModelImp, client);
		return null;
	}

	public List<ServiceEntityNode> searchToFinAccount(
			AccountToFinanceSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[account]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(Account.SENAME);
		searchNodeConfig0.setNodeName(Account.NODENAME);
		searchNodeConfig0.setNodeInstID(Account.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[account object reference]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(FinAccountObjectRef.SENAME);
		searchNodeConfig1.setNodeName(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setNodeInstID(FinAccountObjectRef.NODENAME);
		searchNodeConfig1.setBaseNodeInstID(Account.SENAME);
		searchNodeConfig1
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_REFTO_TARGET);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfigList.add(searchNodeConfig1);
		// Search node:[account object reference]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(FinAccount.SENAME);
		searchNodeConfig2.setNodeName(FinAccount.NODENAME);
		searchNodeConfig2.setNodeInstID(FinAccount.SENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2.setBaseNodeInstID(FinAccountObjectRef.NODENAME);
		searchNodeConfig2
				.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_CHILD);
		searchNodeConfigList.add(searchNodeConfig2);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

}
