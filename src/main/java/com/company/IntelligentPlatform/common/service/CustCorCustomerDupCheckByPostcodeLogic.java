package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.AccountDuplicateCheckException;
import com.company.IntelligentPlatform.common.service.IAccountDuplicateCheckLogicCore;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

/**
 * Customer Account duplicate check logic:
 * Check corporate customer duplicate by post code 
 * @author Zhang,hang
 *
 */
@Service
public class CustCorCustomerDupCheckByPostcodeLogic implements IAccountDuplicateCheckLogicCore{	
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException{
		if(account == null || rawAccount == null){
			return false;
		}
		CorporateCustomer customer1 = (CorporateCustomer) account;
		CorporateCustomer customer2 = (CorporateCustomer) rawAccount;
		if(customer1.getPostcode() != null){
			if(customer1.getPostcode().equals(customer2.getPostcode())){
				return true;
			}
		}
		return false;
	}

}
