package com.company.IntelligentPlatform.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.CorporateSupplierUIModel;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.City;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ISystemCodeValueCollectConstants;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

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
public class CorporateSupplierManager extends ServiceEntityManager{

	public static final String METHOD_ConvSupplierUIToCorporateCustomer = "convSupplierUIToCorporateCustomer";

	public static final String METHOD_ConvCorporateCustomerToSupplierUI = "convCorporateCustomerToSupplierUI";

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;
	
	@Autowired
	protected CorporateSupplierSearchProxy corporateSupplierSearchProxy;
	
	@Autowired
	protected CorporateSupplierIdHelper corporateSupplierIdHelper;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;
	
	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected List<ServiceEntityNode> supplierLevelMap;

	public Map<Integer, String> initCustomerTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return corporateCustomerManager.initCustomerTypeMap(languageCode);
	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, CorporateSupplierUIModel.class,
				"status");
	}

	public CorporateCustomer newSupplier(CorporateCustomer rawInstance) throws ServiceEntityConfigureException{
        rawInstance
				.setStatus(CorporateCustomer.STATUS_INITIAL);
		rawInstance
				.setCustomerType(CorporateCustomer.CUSTOMERTYPE_SUPPLIER);
		String supplierId = corporateSupplierIdHelper.genDefaultId(rawInstance.getClient());
		rawInstance.setId(supplierId);
		return rawInstance;
	}
	
	public void convCorporateCustomerToUI(CorporateCustomer corporateCustomer,
			CorporateSupplierUIModel corporateSupplierUIModel) throws ServiceEntityInstallationException{
		convCorporateCustomerToUI(corporateCustomer, corporateSupplierUIModel, null);
	}

	public void convCorporateCustomerToUI(CorporateCustomer corporateCustomer,
			CorporateSupplierUIModel corporateSupplierUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (corporateCustomer != null) {
			accountManager.convAccountToUI(corporateCustomer, corporateSupplierUIModel, logonInfo);
			corporateSupplierUIModel.setCustomerType(corporateCustomer
					.getCustomerType());
			corporateSupplierUIModel.setStatus(corporateCustomer
					.getStatus());
			if(logonInfo!= null){
				Map<Integer, String> customerTypeMap = this.initCustomerTypeMap(logonInfo.getLanguageCode());
				corporateSupplierUIModel.setCustomerTypeValue(customerTypeMap
						.get(corporateCustomer.getCustomerType()));
				Map<Integer, String> statusMap = initStatusMap(logonInfo.getLanguageCode());
				corporateSupplierUIModel
						.setStatusValue(statusMap
								.get(corporateCustomer.getStatus()));
			}
			corporateSupplierUIModel.setWeiboID(corporateCustomer.getWeiboID());
			corporateSupplierUIModel.setWeiXinID(corporateCustomer
					.getWeiXinID());
			corporateSupplierUIModel.setFaceBookID(corporateCustomer
					.getFaceBookID());
			corporateSupplierUIModel.setPostcode(corporateCustomer
					.getPostcode());
			corporateSupplierUIModel.setTags(corporateCustomer.getTags());
			corporateSupplierUIModel.setBankAccount(corporateCustomer
					.getBankAccount());
			corporateSupplierUIModel.setTaxNumber(corporateCustomer
					.getTaxNumber());
			corporateSupplierUIModel.setDepositBank(corporateCustomer
					.getDepositBank());
		}
	}
	
	public void convCorporateCustomerToSupplierUI(
			CorporateCustomer corporateCustomer,
			CorporateSupplierUIModel corporateSupplierUIModel)
			throws ServiceEntityInstallationException {
		convCorporateCustomerToSupplierUI(corporateCustomer, corporateSupplierUIModel, null);
	}
	
	public void convCorporateCustomerToSupplierUI(
			CorporateCustomer corporateCustomer,
			CorporateSupplierUIModel corporateSupplierUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (corporateCustomer != null) {
			accountManager.convAccountToUI(corporateCustomer, corporateSupplierUIModel, logonInfo);
			corporateSupplierUIModel.setWebPage(corporateCustomer.getWebPage());
			corporateSupplierUIModel.setCustomerType(corporateCustomer
					.getCustomerType());
			if(logonInfo!= null){
				Map<Integer, String> customerTypeMap = this.initCustomerTypeMap(logonInfo.getLanguageCode());
				corporateSupplierUIModel.setCustomerTypeValue(customerTypeMap
						.get(corporateCustomer.getCustomerType()));
				Map<Integer, String> statusMap = initStatusMap(logonInfo.getLanguageCode());
				corporateSupplierUIModel
						.setStatusValue(statusMap
								.get(corporateCustomer.getStatus()));
			}
			corporateSupplierUIModel.setWeiboID(corporateCustomer.getWeiboID());
			corporateSupplierUIModel.setWeiXinID(corporateCustomer
					.getWeiXinID());
			corporateSupplierUIModel.setFaceBookID(corporateCustomer
					.getFaceBookID());
			corporateSupplierUIModel.setSupplierLevel(corporateCustomer
					.getCustomerLevel());
			Map<Integer, String> supplierLevelMap = this.initSupplierLevelMap(corporateCustomer.getClient(), false);
			corporateSupplierUIModel.setSupplierLevelValue(supplierLevelMap.get(corporateCustomer.getCustomerLevel()));
			corporateSupplierUIModel.setStatus(corporateCustomer.getStatus());
			corporateSupplierUIModel.setTags(corporateCustomer.getTags());
			corporateSupplierUIModel.setBankAccount(corporateCustomer
					.getBankAccount());
			corporateSupplierUIModel.setTaxNumber(corporateCustomer
					.getTaxNumber());
			corporateSupplierUIModel.setDepositBank(corporateCustomer
					.getDepositBank());

		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:corporateCustomer
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSupplierUIToCorporateCustomer(
			CorporateSupplierUIModel corporateSupplierUIModel,
			CorporateCustomer rawEntity) {
		docFlowProxy.convUIToServiceEntityNode(corporateSupplierUIModel, rawEntity);
		rawEntity.setTelephone(corporateSupplierUIModel.getTelephone());
		rawEntity.setWeiXinID(corporateSupplierUIModel.getWeiXinID());
		rawEntity.setAddress(corporateSupplierUIModel.getAddress());
		rawEntity.setBaseCityUUID(corporateSupplierUIModel.getBaseCityUUID());
		rawEntity.setId(corporateSupplierUIModel.getId());
		rawEntity.setName(corporateSupplierUIModel.getName());
		rawEntity.setNote(corporateSupplierUIModel.getNote());
		rawEntity.setWeiboID(corporateSupplierUIModel.getWeiboID());
		rawEntity.setCustomerType(corporateSupplierUIModel.getCustomerType());
		rawEntity.setFaceBookID(corporateSupplierUIModel.getFaceBookID());
		rawEntity.setCustomerLevel(corporateSupplierUIModel.getSupplierLevel());
		rawEntity.setFax(corporateSupplierUIModel.getFax());
		rawEntity.setRefCityUUID(corporateSupplierUIModel.getRefCityUUID());
		rawEntity.setCityName(corporateSupplierUIModel.getCityName());
		rawEntity.setCountryName(corporateSupplierUIModel.getCountryName());
		rawEntity.setStateName(corporateSupplierUIModel.getStateName());
		rawEntity.setTownZone(corporateSupplierUIModel.getTownZone());
		rawEntity.setSubArea(corporateSupplierUIModel.getSubArea());
		rawEntity.setStreetName(corporateSupplierUIModel.getStreetName());
		rawEntity.setHouseNumber(corporateSupplierUIModel.getHouseNumber());
		rawEntity.setTags(corporateSupplierUIModel.getTags());
		rawEntity.setBankAccount(corporateSupplierUIModel.getBankAccount());
		rawEntity.setTaxNumber(corporateSupplierUIModel.getTaxNumber());
		rawEntity.setDepositBank(corporateSupplierUIModel.getDepositBank());
	}

	public void convResUserToUIModel(LogonUser logonUser,
			CorporateSupplierUIModel corporateSupplierUIModel) {
		if (logonUser != null && corporateSupplierUIModel != null) {
			corporateSupplierUIModel.setResUserUUID(logonUser.getUuid());
			corporateSupplierUIModel.setResUserName(logonUser.getName());
		}
	}

	public void convResOrgToUIModel(Organization organization,
			CorporateSupplierUIModel corporateSupplierUIModel) {
		if (organization != null && corporateSupplierUIModel != null) {
			corporateSupplierUIModel.setResOrgName(organization.getName());
			corporateSupplierUIModel.setResOrgID(organization.getId());
		}
	}

	public void convCityToUI(City city,
			CorporateSupplierUIModel corporateSupplierUIModel) {
		if (city != null) {
			if (ServiceEntityStringHelper
					.checkNullString(corporateSupplierUIModel.getCityName())) {
				corporateSupplierUIModel.setCityName(city.getName());
			}
		}
	}

	public Map<Integer, String> initSupplierLevelMap(String client, boolean refreshFlag)
			throws ServiceEntityInstallationException {
		List<ServiceEntityNode> systemCodeValueUnionList = this
				.initRawSupplierLevelMap(client, refreshFlag);
		return systemCodeValueCollectionManager
				.convertIntCodeValueUnionMap(systemCodeValueUnionList);
	}

	public List<ServiceEntityNode> initRawSupplierLevelMap(String client, boolean refreshFlag)
			throws ServiceEntityInstallationException {
		if (refreshFlag || ServiceCollectionsHelper.checkNullList(this.supplierLevelMap)) {
			try {
				this.supplierLevelMap = systemCodeValueCollectionManager
						.loadRawCodeValueUnionList(
								ISystemCodeValueCollectConstants.ID_SUPPLIER_LEVEL,
								client);
			} catch (ServiceModuleProxyException
					| ServiceEntityConfigureException e) {
				// just ignore
			}
		}
		return this.supplierLevelMap;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.CorporateCustomer;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return corporateSupplierSearchProxy;
	}

}
