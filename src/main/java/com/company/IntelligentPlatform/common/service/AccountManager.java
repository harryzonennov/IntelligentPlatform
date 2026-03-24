package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.CorporateCustomerSearchModel;
import com.company.IntelligentPlatform.common.dto.CorporateSupplierSearchModel;
import com.company.IntelligentPlatform.common.dto.EmployeeSearchModel;
import com.company.IntelligentPlatform.common.dto.IndividualCustomerSearchModel;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.CorporateSupplierManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.OrganizationSearchModel;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;
import com.company.IntelligentPlatform.common.dto.AccountTypeSelect;
import com.company.IntelligentPlatform.common.dto.AccountUIModel;
// TODO-DAO: import platform.foundation.DAO.AccountDAO;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.BSearchResponse;
import com.company.IntelligentPlatform.common.service.SearchContext;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.AccountConfigureProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [Account]
 * 
 * @author
 * @date Fri Aug 09 11:40:52 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class AccountManager extends ServiceEntityManager {

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected AccountDAO accountDAO;

	@Autowired
	protected AccountConfigureProxy accountConfigureProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected CorporateSupplierManager corporateSupplierManager;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected OrganizationManager organizationManager;

	public static final String METHOD_ConvAccountToUI = "convAccountToUI";

	public static final String METHOD_ConvUIToAccount = "convUIToAccount";

	public static final String PROPERTIES_RESOURCE = "";

	private Map<Integer, String> accountTypeMap;

	private Map<String, Map<Integer, String>> accountTypeMapLan;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AccountManager() {
		super.seConfigureProxy = new AccountConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new AccountDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(accountDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(accountConfigureProxy);
	}

	/**
	 * Common Conversion method from Account to Account UI Model
	 * 
	 * @param account
	 * @param accountUIModel
	 * @throws ServiceEntityInstallationException
	 */
	public void convAccountToUI(Account account, AccountUIModel accountUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (account != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(account, accountUIModel);
			accountUIModel.setAccountType(account.getAccountType());
			if (logonInfo != null) {
				Map<Integer, String> accountTypeMap = this.getAccountTypeMap(
						logonInfo.getLanguageCode(), false);
				if (accountTypeMap != null) {
					accountUIModel.setAccountTypeValue(accountTypeMap
							.get(account.getAccountType()));
				}
			}
			accountUIModel.setAddress(account.getAddress());
			accountUIModel.setTelephone(account.getTelephone());
			accountUIModel.setFax(account.getFax());
			accountUIModel.setSubArea(account.getSubArea());
			accountUIModel.setPostcode(account.getPostcode());
			accountUIModel.setEmail(account.getEmail());
			accountUIModel.setCountryName(account.getCountryName());
			accountUIModel.setStateName(account.getStateName());
			accountUIModel.setCityName(account.getCityName());
			accountUIModel.setTownZone(account.getTownZone());
			accountUIModel.setHouseNumber(account.getHouseNumber());
		}
	}

	/**
	 * Common Conversion method from Account to Account UI Model
	 *
	 * @param accountUIModel
	 * @throws ServiceEntityInstallationException
	 */
	public void convUIToAccount(AccountUIModel accountUIModel, Organization rawEntity)  {
		DocFlowProxy.convUIToServiceEntityNode(accountUIModel, rawEntity);
		rawEntity.setAddress(accountUIModel.getAddress());
		rawEntity.setSubArea(accountUIModel.getSubArea());
		rawEntity.setPostcode(accountUIModel.getPostcode());
		rawEntity.setTelephone(accountUIModel.getTelephone());
		rawEntity.setFax(accountUIModel.getFax());
		rawEntity.setEmail(accountUIModel.getEmail());
		rawEntity.setCountryName(accountUIModel.getCountryName());
		rawEntity.setStateName(accountUIModel.getStateName());
		rawEntity.setCityName(accountUIModel.getCityName());
		rawEntity.setTownZone(accountUIModel.getTownZone());
		rawEntity.setHouseNumber(accountUIModel.getHouseNumber());
	}

	public void checkPreDeleteCondition(String accountUUID, String client)
			throws ServiceEntityConfigureException, AccountException {
		/**
		 * Check whether fin account relative to this account
		 */
		ServiceEntityManager finAccountManager = serviceEntityManagerFactoryInContext
				.getManagerBySEName(IServiceModelConstants.FinAccount);
		List<ServiceEntityNode> finAccObjectRefList = finAccountManager
				.getEntityNodeListByKey(accountUUID,
						IReferenceNodeFieldConstant.REFUUID,
						IServiceModelConstants.FinAccountObjectRef, client,
						null);
		if (finAccObjectRefList != null && finAccObjectRefList.size() > 0) {
			throw new AccountException(AccountException.TYPE_CANNOT_DEL_FINACC);
		}
	}

	public Map<Integer, String> getAccountTypeMap(String languageCode,
			boolean filterFlag) throws ServiceEntityInstallationException {
		if (this.accountTypeMapLan == null) {
			this.accountTypeMapLan = new HashMap<>();
		}
		if (filterFlag && this.accountTypeMapLan.containsKey(languageCode)) {
			this.accountTypeMapLan.put(languageCode, null);
		}
		return ServiceLanHelper
				.initDefaultLanguageMap(
						languageCode,
						this.accountTypeMapLan,
						lanCode -> {
							try {
								Map<Integer, String> tempAccountTypeMap = getAccountTypeMapCore(
										lanCode, filterFlag);
								return tempAccountTypeMap;
							} catch (ServiceEntityInstallationException e) {
								logger.error(ServiceEntityStringHelper
										.genDefaultErrorMessage(e,
												"getAccountTypeMap"));
								return null;
							}
						});
	}

	private Map<Integer, String> getAccountTypeMapCore(String lanCode,
			boolean filterFlag) throws ServiceEntityInstallationException {
		Map<Integer, String> tempAccountTypeMap = serviceDropdownListHelper
				.getUIDropDownMap(AccountTypeSelect.class, "accountType",
						lanCode);
		if (!filterFlag) {
			return tempAccountTypeMap;
		}
		Map<Integer, String> resultMap = new HashMap<>();
		if (tempAccountTypeMap == null) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_CONFIG_DROPDOWN);
		}
		Set<Integer> keySet = tempAccountTypeMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			Integer accountType = it.next();
			addSubAccountTypeMap(accountType, tempAccountTypeMap, resultMap);
		}
		return resultMap;
	}

	private void addSubAccountTypeMap(int accountType,
			Map<Integer, String> sourceAccountTypeMap,
			Map<Integer, String> targetMap)
			throws ServiceEntityInstallationException {
		if (sourceAccountTypeMap == null) {
			throw new ServiceEntityInstallationException(
					ServiceEntityInstallationException.PARA_CONFIG_DROPDOWN);
		}
		String accountObjectName = getAllPossibleAccountObjectSEName(accountType);
		ServiceEntityManager accountObjectManager = serviceEntityManagerFactoryInContext
				.getManagerBySEName(accountObjectName);
		// filter by installation
		if (accountObjectManager != null) {
			if (sourceAccountTypeMap.containsKey(accountType)) {
				targetMap.put(accountType,
						sourceAccountTypeMap.get(accountType));
			}
		}
	}

	public Map<Integer, String> getAccountTypeMap()
			throws ServiceEntityInstallationException {
		if (this.accountTypeMap == null) {
			this.accountTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					AccountTypeSelect.class, "accountType");
		}
		return this.accountTypeMap;
	}

	public String getAccountTypeValue(int accountType)
			throws ServiceEntityInstallationException {
		this.getAccountTypeMap();
		if (this.accountTypeMap != null) {
			return accountTypeMap.get(accountType);
		} else {
			return null;
		}
	}

	/**
	 * Core Logic to search account object
	 *
	 * @param searchContext
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 * @throws SearchConfigureException
	 */
	public BSearchResponse searchAccount(
			SearchContext searchContext)
            throws SearchConfigureException, ServiceEntityConfigureException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException {
		AccountSearchModel accountSearchModel = (AccountSearchModel) searchContext.getSearchModel();
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_COR_CUSTOMER) {
			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
					accountSearchModel, CorporateCustomerSearchModel.class,
					SEUIComModel.class);
			searchContext.setSearchModel(accountSearchModelImp);
			return corporateCustomerManager.getSearchProxy().searchDocList(
					searchContext);
		}
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_COR_CUSTOMER) {
			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
					accountSearchModel, IndividualCustomerSearchModel.class,
					SEUIComModel.class);
			searchContext.setSearchModel(accountSearchModelImp);
			return individualCustomerManager.getSearchProxy().searchDocList(
					searchContext);
		}
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_TRANSIT_PARTNER) {
			return null;
		}
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_SUPPLIER) {
			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
					accountSearchModel, CorporateSupplierSearchModel.class,
					SEUIComModel.class);
			searchContext.setSearchModel(accountSearchModelImp);
			return corporateSupplierManager.getSearchProxy().searchDocList(
					searchContext);
		}
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_EMPLOYEE) {
			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
					accountSearchModel, EmployeeSearchModel.class,
					SEUIComModel.class);
			searchContext.setSearchModel(accountSearchModelImp);
			return employeeManager.getSearchProxy().searchDocList(
					searchContext);
		}
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_EXTER_DRIVER) {
			return null;
		}
		if (accountSearchModel.getAccountType() == Account.ACCOUNTTYPE_ORGANIZATION) {
			AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
					accountSearchModel, OrganizationSearchModel.class,
					SEUIComModel.class);
			searchContext.setSearchModel(accountSearchModelImp);
			return organizationManager.getSearchProxy().searchDocList(
					searchContext);
		}
		AccountSearchModel accountSearchModelImp = genImpAccountSearchModel(
				accountSearchModel, CorporateCustomerSearchModel.class,
				CorporateCustomerSearchModel.class);
		searchContext.setSearchModel(accountSearchModelImp);
		return corporateCustomerManager.getSearchProxy().searchDocList(
				searchContext);
	}


	public AccountSearchModel genImpAccountSearchModel(
			AccountSearchModel accountSearchModel, Class<?> impClass,
			Class<?> baseClass) {
		if (impClass == null || accountSearchModel == null) {
			return null;
		}
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getServiceSelfDefinedFieldsList(impClass, baseClass);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		try {
			AccountSearchModel accountSearchModelImp = (AccountSearchModel) impClass
					.newInstance();
			for (Field field : fieldList) {
				field.setAccessible(true);
				BSearchFieldConfig bSearchFieldConfig = field
						.getAnnotation(BSearchFieldConfig.class);
				if (bSearchFieldConfig != null) {
					try {
						Field targetField = ServiceEntityFieldsHelper
								.getServiceField(impClass, field.getName());
						if (targetField != null) {
							targetField.setAccessible(true);
							targetField.set(accountSearchModelImp,
									field.get(accountSearchModel));
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| NoSuchFieldException e) {
						logger.error(ServiceEntityStringHelper
								.genDefaultErrorMessage(e, field.getName()));
						continue;
					}
				}
			}
			return accountSearchModelImp;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
					"genImpAccountSearchModel"));
			return null;
		}

	}

	public String getAllPossibleAccountObjectSEName(int accountType) {
		if (accountType == Account.ACCOUNTTYPE_IND_CUSTOMER) {
			return IndividualCustomer.SENAME;
		}
		if (accountType == Account.ACCOUNTTYPE_COR_CUSTOMER) {
			return IServiceModelConstants.CorporateCustomer;
		}
		if (accountType == Account.ACCOUNTTYPE_TRANSIT_PARTNER) {
			return IServiceModelConstants.TransitPartner;
		}
		if (accountType == Account.ACCOUNTTYPE_SUPPLIER) {
			return CorporateCustomer.SENAME;
		}
		if (accountType == Account.ACCOUNTTYPE_EMPLOYEE) {
			return Employee.SENAME;
		}
		if (accountType == Account.ACCOUNTTYPE_EXTER_DRIVER) {
			return IServiceModelConstants.ExternalDriver;
		}
		if (accountType == Account.ACCOUNTTYPE_ORGANIZATION) {
			return Organization.SENAME;
		}
		return IndividualCustomer.SENAME;
	}

	/**
	 * Get all the possible account object instance
	 * 
	 * @param accountObjectUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllPossibleAccountObject(String accountObjectUUID,
			int accountType, String client)
			throws ServiceEntityConfigureException {
		if (accountType == Account.ACCOUNTTYPE_IND_CUSTOMER) {
			IndividualCustomer individualCustomer = (IndividualCustomer) individualCustomerManager
					.getEntityNodeByKey(accountObjectUUID,
							IServiceEntityNodeFieldConstant.UUID,
							IndividualCustomer.NODENAME, client, null);
			return individualCustomer;
		}
		if (accountType == Account.ACCOUNTTYPE_COR_CUSTOMER
				|| accountType == Account.ACCOUNTTYPE_SUPPLIER) {
			CorporateCustomer corporateCustomer = (CorporateCustomer) corporateCustomerManager
					.getEntityNodeByKey(accountObjectUUID,
							IServiceEntityNodeFieldConstant.UUID,
							CorporateCustomer.NODENAME, client, null);
			return corporateCustomer;
		}
		if (accountType == Account.ACCOUNTTYPE_TRANSIT_PARTNER) {
			ServiceEntityManager transitPartnerManager = serviceEntityManagerFactoryInContext
					.getManagerBySEName(IServiceModelConstants.ExternalDriver);
			Account transitPartner = (Account) transitPartnerManager
					.getEntityNodeByKey(accountObjectUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityNode.NODENAME_ROOT, client, null);
			return transitPartner;

		}
		if (accountType == Account.ACCOUNTTYPE_EMPLOYEE) {
			Employee employee = (Employee) employeeManager.getEntityNodeByKey(
					accountObjectUUID, IServiceEntityNodeFieldConstant.UUID,
					Employee.NODENAME, client, null);
			return employee;
		}
		if (accountType == Account.ACCOUNTTYPE_EXTER_DRIVER) {
			ServiceEntityManager externalDriverManager = serviceEntityManagerFactoryInContext
					.getManagerBySEName(IServiceModelConstants.ExternalDriver);

			Account externalDriver = (Account) externalDriverManager
					.getEntityNodeByKey(accountObjectUUID,
							IServiceEntityNodeFieldConstant.UUID,
							ServiceEntityNode.NODENAME_ROOT, client, null);
			return externalDriver;
		}
		if (accountType == Account.ACCOUNTTYPE_ORGANIZATION) {
			Organization organization = (Organization) organizationManager
					.getEntityNodeByKey(accountObjectUUID,
							IServiceEntityNodeFieldConstant.UUID,
							Organization.NODENAME, client, null);
			return organization;
		}
		return null;
	}



	/**
	 * The logic to define Account Object type
	 * 
	 * @param accountObject
	 * @return
	 */
	public int getAccountType(ServiceEntityNode accountObject) {
		if (IndividualCustomer.SENAME.equals(accountObject
				.getServiceEntityName())) {
			return Account.ACCOUNTTYPE_IND_CUSTOMER;
		}
		if (CorporateCustomer.SENAME.equals(accountObject
				.getServiceEntityName())) {
			return Account.ACCOUNTTYPE_COR_CUSTOMER;
		}
		if (Employee.SENAME.equals(accountObject.getServiceEntityName())) {
			return Account.ACCOUNTTYPE_EMPLOYEE;
		}
		if (IServiceModelConstants.TransitPartner.equals(accountObject
				.getServiceEntityName())) {
			return Account.ACCOUNTTYPE_TRANSIT_PARTNER;
		}
		if (IServiceModelConstants.ExternalDriver.equals(accountObject
				.getServiceEntityName())) {
			return Account.ACCOUNTTYPE_EXTER_DRIVER;
		}
		return 0;
	}

	/**
	 * The logic to define Account Object type
	 * 
	 * @param refSEName
	 * @return
	 */
	public int getAccountType(String refSEName) {
		if (IndividualCustomer.SENAME.equals(refSEName)) {
			return Account.ACCOUNTTYPE_IND_CUSTOMER;
		}
		if (CorporateCustomer.SENAME.equals(refSEName)) {
			return Account.ACCOUNTTYPE_COR_CUSTOMER;
		}
		if (Employee.SENAME.equals(refSEName)) {
			return Account.ACCOUNTTYPE_EMPLOYEE;
		}
		if (IServiceModelConstants.TransitPartner.equals(refSEName)) {
			return Account.ACCOUNTTYPE_TRANSIT_PARTNER;
		}
		if (IServiceModelConstants.ExternalDriver.equals(refSEName)) {
			return Account.ACCOUNTTYPE_EXTER_DRIVER;
		}
		return 0;
	}

	/**
	 * Get all the possible account object instance
	 * 
	 * @param referenceNode
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllPossibleAccountObject(ReferenceNode referenceNode)
			throws ServiceEntityConfigureException {
		if (referenceNode != null) {
			if (IndividualCustomer.SENAME.equals(referenceNode.getRefSEName())) {
				IndividualCustomer individualCustomer = (IndividualCustomer) individualCustomerManager
						.getEntityNodeByKey(referenceNode.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								IndividualCustomer.NODENAME,
								referenceNode.getClient(), null);
				return individualCustomer;
			}
			if (CorporateCustomer.SENAME.equals(referenceNode.getRefSEName())) {
				CorporateCustomer corporateCustomer = (CorporateCustomer) corporateCustomerManager
						.getEntityNodeByKey(referenceNode.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								CorporateCustomer.NODENAME,
								referenceNode.getClient(), null);
				return corporateCustomer;
			}
			if (IServiceModelConstants.ExternalDriver.equals(referenceNode
					.getRefSEName())) {
				ServiceEntityManager transitPartnerManager = serviceEntityManagerFactoryInContext
						.getManagerBySEName(IServiceModelConstants.ExternalDriver);
				Account transitPartner = (Account) transitPartnerManager
						.getEntityNodeByKey(referenceNode.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ServiceEntityNode.NODENAME_ROOT,
								referenceNode.getClient(), null);
				return transitPartner;

			}
			if (Employee.SENAME.equals(referenceNode.getRefSEName())) {
				Employee employee = (Employee) employeeManager
						.getEntityNodeByKey(referenceNode.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								Employee.NODENAME, referenceNode.getClient(),
								null);
				return employee;
			}
			if (IServiceModelConstants.ExternalDriver.equals(referenceNode
					.getRefSEName())) {
				ServiceEntityManager externalDriverManager = serviceEntityManagerFactoryInContext
						.getManagerBySEName(IServiceModelConstants.ExternalDriver);
				Account externalDriver = (Account) externalDriverManager
						.getEntityNodeByKey(referenceNode.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								ServiceEntityNode.NODENAME_ROOT,
								referenceNode.getClient(), null);
				return externalDriver;
			}
			if (Organization.SENAME.equals(referenceNode.getRefSEName())) {
				Organization organization = (Organization) organizationManager
						.getEntityNodeByKey(referenceNode.getRefUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								Organization.NODENAME,
								referenceNode.getClient(), null);
				return organization;
			}
		}
		return null;
	}

	public void convAccountToUI(Account account, AccountUIModel accountUIModel,
			Map<Integer, String> accountTypeMap) {
		if (account != null) {
			accountUIModel.setUuid(account.getUuid());
			accountUIModel.setId(account.getId());
			accountUIModel.setName(account.getName());
			accountUIModel.setAddress(account.getAddress());
			accountUIModel.setTelephone(account.getTelephone());
			accountUIModel.setAccountType(account.getAccountType());
			String accountTypeValue = accountTypeMap.get(account
					.getAccountType());
			accountUIModel.setAccountTypeValue(accountTypeValue);
			accountUIModel.setFax(account.getFax());
			accountUIModel.setEmail(account.getEmail());
			accountUIModel.setWebPage(account.getWebPage());
			accountUIModel.setPostcode(account.getPostcode());
			accountUIModel.setCityName(account.getCityName());
			accountUIModel.setRefCityUUID(account.getRefCityUUID());
			accountUIModel.setTownZone(account.getTownZone());
			accountUIModel.setSubArea(account.getSubArea());
			accountUIModel.setStreetName(account.getStreetName());
			accountUIModel.setHouseNumber(account.getHouseNumber());
			accountUIModel.setRegularType(account.getRegularType());
		}
	}

}
