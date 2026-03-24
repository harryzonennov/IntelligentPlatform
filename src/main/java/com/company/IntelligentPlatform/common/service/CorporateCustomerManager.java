package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.repository.CorporateCustomerRepository;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.CustomerParentOrgReference;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.CorporateCustomerConfigureProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ISystemCodeValueCollectConstants;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDate;

/**
 * Logic Manager CLASS FOR Service Entity [CorporateCustomer]
 * 
 * @author
 * @date Tue Aug 13 01:57:22 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class CorporateCustomerManager extends ServiceEntityManager {


	public static final String METHOD_ConvCorporateContactPersonToUI = "convCorporateContactPersonToUI";

	public static final String METHOD_ConvUIToCorporateContactPerson = "convUIToCorporateContactPerson";

	public static final String METHOD_ConvCorporateCustomerToUI = "convCorporateCustomerToUI";

	public static final String METHOD_ConvUIToCorporateCustomer = "convUIToCorporateCustomer";

	public static final String METHOD_ConvCustomerParentOrgReferenceToUI = "convCustomerParentOrgReferenceToUI";

	public static final String METHOD_ConvUIToCustomerParentOrgReference = "convUIToCustomerParentOrgReference";

	public static final String METHOD_ConvSupplierUIToCustomerParentOrgReference = "convSupplierUIToCustomerParentOrgReference";

	public static final String METHOD_ConvContactPersonToUI = "convContactPersonToUI";

	public static final String METHOD_ConvCorporateCustomerToContactUI = "convCorporateCustomerToContactUI";

	public static final String METHOD_ConvContactPersonUIToIndividualCustomer = "convContactPersonUIToIndividualCustomer";

	@Autowired
	protected CorporateCustomerRepository corporateCustomerDAO;

	@Autowired
	protected CorporateCustomerConfigureProxy corporateCustomerConfigureProxy;

	@Autowired
	protected CorporateCustomerSearchProxy corporateCustomerSearchProxy;

	@Autowired
	protected CorporateCustomerIdHelper corporateCustomerIdHelper;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CustomerManager customerManager;

	@Autowired
	protected CorporateSupplierManager corporateSupplierManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected BsearchService bsearchService;

	//TODO to clean this in the future
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected AccountManager accountManager;

	protected Map<String, Map<Integer, String>> customerTypeMapLan;

	protected Map<Integer, String> subDistributorTypeMap;

	protected List<ServiceEntityNode> rawCustomerLevelMap;

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<Integer, String> initCustomerTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.customerTypeMapLan, CorporateSupplierUIModel.class, "customerType");
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, CorporateCustomerUIModel.class,
				"status");
	}

	public CorporateCustomerManager() {
		super.seConfigureProxy = new CorporateCustomerConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new CorporateCustomerDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(corporateCustomerDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(corporateCustomerConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		CorporateCustomer corporateCustomer = (CorporateCustomer) super
				.newRootEntityNode(client);
		String corporateCustomerId = corporateCustomerIdHelper
				.genDefaultId(client);
		corporateCustomer.setId(corporateCustomerId);
		return corporateCustomer;
	}

	/**
	 * Get corporate contact person instance from persistency by baseUUID and
	 * target individual customer uuid
	 * 
	 * @param baseUUID
	 * @param indCustomerUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public CorporateContactPerson getContactPerson(String baseUUID,
			String indCustomerUUID) throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
		key1.setKeyName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		key1.setKeyValue(baseUUID);
		keyList.add(key1);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
		key2.setKeyName(IReferenceNodeFieldConstant.REFUUID);
		key2.setKeyValue(indCustomerUUID);
		keyList.add(key2);
		CorporateContactPerson corporateContactPerson = (CorporateContactPerson) getEntityNodeByKeyList(
				keyList, CorporateContactPerson.NODENAME, null);
		return corporateContactPerson;
	}



	/**
	 * [Internal method] Convert from UI model to se model:corporateCustomer
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToCorporateCustomer(
			CorporateCustomerUIModel corporateCustomerUIModel,
			CorporateCustomer rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(corporateCustomerUIModel, rawEntity);
		if (corporateCustomerUIModel.getCustomerType() != 0) {
			rawEntity.setCustomerType(corporateCustomerUIModel
					.getCustomerType());
		}
		rawEntity.setTelephone(corporateCustomerUIModel.getTelephone());
		rawEntity.setWeiXinID(corporateCustomerUIModel.getWeiXinID());
		rawEntity.setBaseCityUUID(corporateCustomerUIModel.getBaseCityUUID());
		rawEntity.setRetireDate(corporateCustomerUIModel.getRetireDate() != null ? corporateCustomerUIModel.getRetireDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null);
		rawEntity.setNote(corporateCustomerUIModel.getNote());
		rawEntity.setAddress(corporateCustomerUIModel.getAddress());
		rawEntity.setCountryName(corporateCustomerUIModel.getCountryName());
		rawEntity.setStateName(corporateCustomerUIModel.getStateName());
		rawEntity.setCityName(corporateCustomerUIModel.getCityName());
		rawEntity.setTownZone(corporateCustomerUIModel.getTownZone());
		rawEntity.setSubArea(corporateCustomerUIModel.getSubArea());
		rawEntity.setCustomerLevel(corporateCustomerUIModel.getCustomerLevel());
		rawEntity.setFullName(corporateCustomerUIModel.getFullName());
		rawEntity.setStreetName(corporateCustomerUIModel.getStreetName());
		rawEntity.setHouseNumber(corporateCustomerUIModel.getHouseNumber());
		rawEntity.setWeiboID(corporateCustomerUIModel.getWeiboID());
		rawEntity.setEmail(corporateCustomerUIModel.getEmail());
		rawEntity.setFax(corporateCustomerUIModel.getFax());
		rawEntity.setCustomerType(corporateCustomerUIModel.getCustomerType());
		rawEntity.setLaunchReason(corporateCustomerUIModel.getLaunchReason());
		rawEntity.setRetireReason(corporateCustomerUIModel.getRetireReason());
		rawEntity.setSubDistributorType(corporateCustomerUIModel
				.getSubDistributorType());
		rawEntity.setLaunchDate(corporateCustomerUIModel.getLaunchDate() != null ? corporateCustomerUIModel.getLaunchDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null);
		rawEntity.setRefSalesAreaUUID(corporateCustomerUIModel
				.getRefSalesAreaUUID());
		rawEntity.setFaceBookID(corporateCustomerUIModel.getFaceBookID());
		rawEntity.setSystemDefault(corporateCustomerUIModel.getSystemDefault());
		rawEntity.setTags(corporateCustomerUIModel.getTags());
		rawEntity.setBankAccount(corporateCustomerUIModel.getBankAccount());
		rawEntity.setTaxNumber(corporateCustomerUIModel.getTaxNumber());
		rawEntity.setDepositBank(corporateCustomerUIModel.getDepositBank());
	}

	public void convCorporateCustomerToUI(CorporateCustomer corporateCustomer,
			CorporateCustomerUIModel corporateCustomerUIModel)
			throws ServiceEntityInstallationException {
		convCorporateCustomerToUI(corporateCustomer, corporateCustomerUIModel,
				null);
	}

	public void convCorporateCustomerToUI(CorporateCustomer corporateCustomer,
			CorporateCustomerUIModel corporateCustomerUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (corporateCustomer != null) {
			accountManager.convAccountToUI(corporateCustomer,
					corporateCustomerUIModel, logonInfo);
			corporateCustomerUIModel.setNote(corporateCustomer.getNote());
			corporateCustomerUIModel.setWebPage(corporateCustomer.getWebPage());
			corporateCustomerUIModel.setCustomerType(corporateCustomer
					.getCustomerType());
			corporateCustomerUIModel.setStatus(corporateCustomer.getStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> customerTypeMap = initCustomerTypeMap(logonInfo
							.getLanguageCode());
					corporateCustomerUIModel
							.setCustomerTypeValue(customerTypeMap
									.get(corporateCustomer.getCustomerType()));
					Map<Integer, String> statusMap = initStatusMap(logonInfo.getLanguageCode());
					corporateCustomerUIModel
							.setStatusValue(statusMap
									.get(corporateCustomer.getStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "customerType"));
				}
			}
			corporateCustomerUIModel.setWeiboID(corporateCustomer.getWeiboID());
			corporateCustomerUIModel.setWeiXinID(corporateCustomer
					.getWeiXinID());
			corporateCustomerUIModel.setFaceBookID(corporateCustomer
					.getFaceBookID());
			corporateCustomerUIModel.setStatus(corporateCustomer.getStatus());
			corporateCustomerUIModel.setLaunchReason(corporateCustomer
					.getLaunchReason());
			corporateCustomerUIModel.setLaunchDate(corporateCustomer.getLaunchDate() != null ? java.util.Date.from(corporateCustomer.getLaunchDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()) : null);
			corporateCustomerUIModel.setFullName(corporateCustomer.getFullName());
			corporateCustomerUIModel.setCustomerLevel(corporateCustomer
					.getCustomerLevel());
			Map<Integer, String> customerLevelMap = this.initCustomerLevelMap(
					corporateCustomer.getClient(), false);
			corporateCustomerUIModel.setCustomerLevelValue(customerLevelMap
					.get(corporateCustomer.getCustomerLevel()));
			corporateCustomerUIModel.setRetireReason(corporateCustomer
					.getRetireReason());
			corporateCustomerUIModel.setRetireDate(corporateCustomer.getRetireDate() != null ? java.util.Date.from(corporateCustomer.getRetireDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()) : null);
			corporateCustomerUIModel.setSubDistributorType(corporateCustomer
					.getSubDistributorType());
			corporateCustomerUIModel.setSystemDefault(corporateCustomer
					.getSystemDefault());
			corporateCustomerUIModel.setTags(corporateCustomer.getTags());
			corporateCustomerUIModel.setBankAccount(corporateCustomer
					.getBankAccount());
			corporateCustomerUIModel.setTaxNumber(corporateCustomer
					.getTaxNumber());
			corporateCustomerUIModel.setDepositBank(corporateCustomer
					.getDepositBank());
		}
	}



	/**
	 * [Internal method] Convert from UI model to se
	 * model:corporateContactPerson
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToCorporateContactPerson(
			CorporateContactPersonUIModel corporateContactPersonUIModel,
			CorporateContactPerson rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(corporateContactPersonUIModel, rawEntity);
		rawEntity
				.setContactRole(corporateContactPersonUIModel.getContactRole());
		rawEntity.setContactRoleNote(corporateContactPersonUIModel
				.getContactRoleNote());
		if (!ServiceEntityStringHelper
				.checkNullString(corporateContactPersonUIModel.getRefUUID())) {
			rawEntity.setRefUUID(corporateContactPersonUIModel.getRefUUID());
		}
		rawEntity.setContactPosition(corporateContactPersonUIModel
				.getContactPosition());
		rawEntity.setContactPositionNote(corporateContactPersonUIModel
				.getContactPositionNote());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convCorporateContactPersonToUI(
			CorporateContactPerson corporateContactPerson,
			CorporateContactPersonUIModel corporateContactPersonUIModel)
			throws ServiceEntityInstallationException {
		if (corporateContactPerson != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(corporateContactPerson,corporateContactPersonUIModel );
			corporateContactPersonUIModel
					.setContactPosition(corporateContactPerson
							.getContactPosition());
			corporateContactPersonUIModel
					.setContactPositionNote(corporateContactPerson
							.getContactPositionNote());
			corporateContactPersonUIModel.setContactRole(corporateContactPerson
					.getContactRole());
			corporateContactPersonUIModel
					.setContactRoleNote(corporateContactPerson
							.getContactRoleNote());
			Map<Integer, String> contactRoleMap = serviceDropdownListHelper
					.getUIDropDownMap(IndividualCustomerUIModel.class,
							"contactRole");
			corporateContactPersonUIModel.setContactRoleValue(contactRoleMap
					.get(corporateContactPerson.getContactRole()));
			Map<Integer, String> contactPositionMap = serviceDropdownListHelper
					.getUIDropDownMap(IndividualCustomerUIModel.class,
							"contactPosition");
			corporateContactPersonUIModel
					.setContactPositionValue(contactPositionMap
							.get(corporateContactPerson.getContactPosition()));
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convCustomerParentOrgReferenceToUI(
			CustomerParentOrgReference customerParentOrgReference,
			CorporateCustomerUIModel corporateCustomerUIModel) {
		if (customerParentOrgReference != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(customerParentOrgReference,corporateCustomerUIModel );
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:customerParentOrgReference
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSupplierUIToCustomerParentOrgReference(
			CorporateSupplierUIModel corporateSupplierUIModel,
			CustomerParentOrgReference rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(corporateSupplierUIModel, rawEntity);
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:customerParentOrgReference
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToCustomerParentOrgReference(
			CorporateCustomerUIModel corporateCustomerUIModel,
			CustomerParentOrgReference rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(corporateCustomerUIModel, rawEntity);
	}

	/**
	 * Logic to get system default customer, when update batch, no customer is
	 * specified
	 * 
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public CorporateCustomer getDefaultCustomer(String client)
			throws ServiceEntityConfigureException {

		List<ServiceEntityNode> rawCustomerList = this.getEntityNodeListByKey(
				null, null, CorporateCustomer.NODENAME, client, null);
		CorporateCustomer systemDefaultCustomer = null;
		if (!ServiceCollectionsHelper.checkNullList(rawCustomerList)) {
			systemDefaultCustomer = (CorporateCustomer) rawCustomerList.get(0);
			for (ServiceEntityNode rawSENode : rawCustomerList) {
				CorporateCustomer corporateCustomer = (CorporateCustomer) rawSENode;
				if (corporateCustomer.getSystemDefault() == true) {
					return corporateCustomer;
				}
			}
		}
		return systemDefaultCustomer;
	}


	/**
	 * [Internal method] Convert from SE model individual to UI model
	 * CorporateContactPersonUIModel
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convContactPersonUIToIndividualCustomer(
			CorporateContactPersonUIModel corporateContactPersonUIModel, IndividualCustomer individualCustomer) {
		if (corporateContactPersonUIModel != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(corporateContactPersonUIModel.getRefUUID())) {
				// Pay special logic to set the uuid, parentNodeUUID,
				// rootNodeUUID.
				individualCustomer.setUuid(corporateContactPersonUIModel
						.getRefUUID());
				individualCustomer
						.setRootNodeUUID(corporateContactPersonUIModel
								.getRefUUID());
				individualCustomer
						.setParentNodeUUID(corporateContactPersonUIModel
								.getRefUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(corporateContactPersonUIModel.getClient())) {
				individualCustomer.setClient(corporateContactPersonUIModel
						.getClient());
			}
			DocFlowProxy.convUIToServiceEntityNode(corporateContactPersonUIModel, individualCustomer);
			individualCustomer.setAddress(corporateContactPersonUIModel
					.getAddress());
			individualCustomer.setWeiXinID(corporateContactPersonUIModel
					.getWeixinId());
			individualCustomer.setMobile(corporateContactPersonUIModel
					.getMobile());
			individualCustomer.setRefCityUUID(corporateContactPersonUIModel
					.getRefCityUUID());
			individualCustomer.setCityName(corporateContactPersonUIModel
					.getCityName());
			individualCustomer.setEmail(corporateContactPersonUIModel
					.getEmail());
			individualCustomer.setStreetName(corporateContactPersonUIModel
					.getStreetName());
			individualCustomer.setSubArea(corporateContactPersonUIModel
					.getSubArea());
			individualCustomer.setHouseNumber(corporateContactPersonUIModel
					.getHouseNumber());
			individualCustomer.setCustomerType(corporateContactPersonUIModel
					.getCustomerType());
			individualCustomer.setId(corporateContactPersonUIModel.getId());
		}
	}

	public void convCorporateCustomerToContactUI(
			CorporateCustomer corporateCustomer,
			CorporateContactPersonUIModel corporateContactPersonUIModel,
			LogonInfo logonInfo) {
		if (corporateCustomer != null) {
			corporateContactPersonUIModel
					.setRefCorporateCustomerId(corporateCustomer.getId());
			corporateContactPersonUIModel
					.setRefCorporateCustomerName(corporateCustomer.getName());
			corporateContactPersonUIModel.setCustomerType(corporateCustomer
					.getCustomerType());
			if (logonInfo != null) {
				try {
					Map<Integer, String> customerTypeMap = initCustomerTypeMap(logonInfo
							.getLanguageCode());
					corporateContactPersonUIModel
							.setCustomerTypeValue(customerTypeMap
									.get(corporateCustomer.getCustomerType()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, "customerType"));
				}
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model individual to UI model
	 * CorporateContactPersonUIModel
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convContactPersonToUI(IndividualCustomer contactPerson,
			CorporateContactPersonUIModel corporateContactPersonUIModel) {
		if (contactPerson != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(contactPerson, corporateContactPersonUIModel);
			corporateContactPersonUIModel
					.setAddress(contactPerson.getAddress());
			corporateContactPersonUIModel.setWeixinId(contactPerson
					.getWeiXinID());
			corporateContactPersonUIModel.setMobile(contactPerson.getMobile());
			corporateContactPersonUIModel.setRefCityUUID(contactPerson
					.getRefCityUUID());
			corporateContactPersonUIModel.setRefUUID(contactPerson.getUuid());
			corporateContactPersonUIModel.setCityName(contactPerson
					.getCityName());
			corporateContactPersonUIModel.setStreetName(contactPerson
					.getStreetName());
			corporateContactPersonUIModel.setEmail(contactPerson.getEmail());
			corporateContactPersonUIModel
					.setSubArea(contactPerson.getSubArea());
			corporateContactPersonUIModel.setHouseNumber(contactPerson
					.getHouseNumber());
			corporateContactPersonUIModel.setCustomerType(contactPerson
					.getCustomerType());
		}
	}

	public Map<Integer, String> initCustomerLevelMap(String client,
			boolean refreshFlag) throws ServiceEntityInstallationException {
		List<ServiceEntityNode> systemCodeValueUnionList = this
				.initRawCustomerLevelMap(client, refreshFlag);
		return systemCodeValueCollectionManager
				.convertIntCodeValueUnionMap(systemCodeValueUnionList);
	}

	public List<ServiceEntityNode> initRawCustomerLevelMap(String client,
			boolean refreshFlag) throws ServiceEntityInstallationException {
		if (refreshFlag
				|| ServiceCollectionsHelper
						.checkNullList(this.rawCustomerLevelMap)) {
			try {
				this.rawCustomerLevelMap = systemCodeValueCollectionManager
						.loadRawCodeValueUnionList(
								ISystemCodeValueCollectConstants.ID_CUSTOMER_LEVEL,
								client);
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				// just ignore
			}
		}
		return this.rawCustomerLevelMap;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.CorporateCustomer;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return corporateCustomerSearchProxy;
	}

}
