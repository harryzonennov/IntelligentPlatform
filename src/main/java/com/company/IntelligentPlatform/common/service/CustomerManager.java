package com.company.IntelligentPlatform.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateCustomerUIModel;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class CustomerManager {
	
	@Autowired
	protected IndividualCustomerManager individualCustomerManager;
	
	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;
	
	@Autowired
	protected AccountManager accountManager;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	/**
	 * Get all possible customer by customer uuid and customer type
	 * @param customerUUID
	 * @param accountObjectType
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllCustomer(String customerUUID, String client,
			int accountObjectType) throws ServiceEntityConfigureException {
		if (accountObjectType == Account.ACCOUNTTYPE_IND_CUSTOMER) {
			IndividualCustomer individualCustomer = (IndividualCustomer) individualCustomerManager
					.getEntityNodeByKey(customerUUID,
							IServiceEntityNodeFieldConstant.UUID,
							IndividualCustomer.NODENAME, client,  null);
			return individualCustomer;
		}
		if (accountObjectType ==  Account.ACCOUNTTYPE_COR_CUSTOMER) {
			CorporateCustomer corporateCustomer = (CorporateCustomer) corporateCustomerManager
					.getEntityNodeByKey(customerUUID,
							IServiceEntityNodeFieldConstant.UUID,
							CorporateCustomer.NODENAME, client, null);
			return corporateCustomer;
		}
		return null;
	}
	
	public Map<Integer, String> getCorporateCustomerTypeMap() throws ServiceEntityInstallationException{
		Map<Integer, String> customerTypeMap = serviceDropdownListHelper.getUIDropDownMap(CorporateCustomerUIModel.class, "customerType");
		return customerTypeMap;
	}
	
	@Deprecated
	public Map<Integer, String> getCustomerTypeMap() throws ServiceEntityInstallationException{
		Map<Integer, String> accountTypeMap = accountManager.getAccountTypeMap();
		Map<Integer, String> customerTypeMap = new HashMap<Integer, String>();
		customerTypeMap.put(Account.ACCOUNTTYPE_COR_CUSTOMER, accountTypeMap.get(Account.ACCOUNTTYPE_COR_CUSTOMER));
		customerTypeMap.put(Account.ACCOUNTTYPE_IND_CUSTOMER, accountTypeMap.get(Account.ACCOUNTTYPE_IND_CUSTOMER));
		return customerTypeMap;
	}
	
	/**
	 * Get all possible customer by customer uuid 
	 * @param customerUUID
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public Account getAllCustomer(String customerUUID) throws ServiceEntityConfigureException {
		IndividualCustomer individualCustomer = (IndividualCustomer) individualCustomerManager
				.getEntityNodeByKey(customerUUID,
						IServiceEntityNodeFieldConstant.UUID,
						IndividualCustomer.NODENAME, null);
		if(individualCustomer != null){
			return individualCustomer;
		}
		CorporateCustomer corporateCustomer = (CorporateCustomer) corporateCustomerManager
				.getEntityNodeByKey(customerUUID,
						IServiceEntityNodeFieldConstant.UUID,
						CorporateCustomer.NODENAME, null);
		if(corporateCustomer != null){
			return corporateCustomer;
		}
		return null;
	}
	
	

}
