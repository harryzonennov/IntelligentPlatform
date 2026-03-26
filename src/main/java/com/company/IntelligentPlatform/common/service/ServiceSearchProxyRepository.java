package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Repository to manage all the search proxy instance
 */
@Service
public class ServiceSearchProxyRepository {

	/**
	 * Registration Area to register executable Units here
	 */
	@Qualifier("corporateCustomerSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy corporateCustomerSearchProxy;

	@Qualifier("corporateSupplierSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy corporateSupplierSearchProxy;

	@Qualifier("employeeSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy employeeSearchProxy;

	@Qualifier("organizationSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy organizationSearchProxy;

	@Qualifier("warehouseStoreSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy warehouseStoreSearchProxy;

	@Qualifier("logonUserSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy logonUserSearchProxy;

	@Qualifier("serviceDocConfigureSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy serviceDocConfigureSearchProxy;

	@Qualifier("corporateCustomerToFinSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy corporateCustomerToFinSearchProxy;

	@Qualifier("finAccountSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy finAccountSearchProxy;

	@Qualifier("employeeToFinSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy employeeToFinSearchProxy;

	@Qualifier("organizationToFinSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy organizationToFinSearchProxy;

	@Qualifier("individualCustomerToFinSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy individualCustomerToFinSearchProxy;

	@Qualifier("inboundDeliverySearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy inboundDeliverySearchProxy;

	@Qualifier("inventoryTransferOrderSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy inventoryTransferOrderSearchProxy;

	@Qualifier("outboundDeliverySearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy outboundDeliverySearchProxy;

	@Qualifier("inquirySearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy inquirySearchProxy;

	@Qualifier("purchaseContractSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy purchaseContractSearchProxy;

	@Qualifier("purchaseRequestSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy purchaseRequestSearchProxy;

	@Qualifier("purchaseReturnOrderSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy purchaseReturnOrderSearchProxy;

	@Qualifier("qualityInspectOrderSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy qualityInspectOrderSearchProxy;

	@Qualifier("salesContractSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy salesContractSearchProxy;

	@Qualifier("salesReturnOrderSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy salesReturnOrderSearchProxy;

	@Qualifier("salesForcastSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy salesForcastSearchProxy;

	@Qualifier("productionOrderSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy productionOrderSearchProxy;

	@Qualifier("productionOrderItemSearchProxy")
	@Autowired(required = false)
	protected ServiceSearchProxy productionOrderItemSearchProxy;

	/**
	 * ===============end of register area===============
	 */

	protected Logger logger = LoggerFactory.getLogger(ServiceSearchProxyRepository.class);

	protected Map<String, Map<String, String>> serviceProxyMapLan = new HashMap<>();

	protected Map<String, Map<String, String>> serviceModelMapLan = new HashMap<>();

	public Map<String, String> loadServiceModelMap(String languageCode)
			throws ServiceEntityInstallationException {
		String resourcePath = this.getClass().getResource("").getPath() + "SearchModelName";
		return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
				this.serviceModelMapLan, resourcePath);
	}

	public Map<String, String> loadServiceProxyMap(String languageCode)
			throws ServiceEntityInstallationException {
		String resourcePath = this.getClass().getResource("").getPath() + "ServiceSearchProxyConfigure";
		return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
				this.serviceProxyMapLan, resourcePath);
	}

	/**
	 * Logic to get search proxy instance by registereed id
	 * @param searchProxyId
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public ServiceSearchProxy getSearchProxyById(String searchProxyId)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		Field field = ServiceEntityFieldsHelper.getServiceField(this.getClass(), searchProxyId);
		if(field != null){
			field.setAccessible(true);
			return (ServiceSearchProxy)field.get(this);
		}
		return null;
	}

	/**
	 * Get all the active (not nul) search proxy instance list
	 * @return
	 */
	public List<ServiceSearchProxy> getAllSearchProxyList(){
		return ServiceEntityFieldsHelper.getDefRepoInstList(
				ServiceSearchProxy.class, this);
	}

	public Map<String, Map<String, String>> loadServiceModelMap(List<Locale> localeList) throws ServiceEntityInstallationException {
		if (ServiceCollectionsHelper.checkNullList(localeList)) {
			return null;
		}
		Map<String, Map<String, String>> resultMap = new HashMap<>();
		for (Locale locale : localeList) {
			String lanKey = ServiceLanHelper.getLocaleKeyStr(locale);
			String resourcePath = this.getClass().getResource("").getPath() + "SearchModelName";
			resultMap.put(lanKey, ServiceLanHelper.initDefLanguageStrMapResource(lanKey,
					this.serviceModelMapLan, resourcePath));
		}
		return resultMap;
	}

}
