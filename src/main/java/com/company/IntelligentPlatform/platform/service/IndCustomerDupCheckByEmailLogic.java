package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.AccountDuplicateCheckException;
import com.company.IntelligentPlatform.platform.service.IAccountDuplicateCheckLogicCore;
import com.company.IntelligentPlatform.platform.model.Account;
import com.company.IntelligentPlatform.platform.model.IndividualCustomer;

/**
 * Customer Account duplicate check logic:
 * Check corporate customer duplicate by post code 
 * @author Zhang,hang
 *
 */
@Service
public class IndCustomerDupCheckByEmailLogic implements IAccountDuplicateCheckLogicCore{	
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException{
		if(account == null || rawAccount == null){
			return false;
		}
		IndividualCustomer customer1 = (IndividualCustomer) account;
		IndividualCustomer customer2 = (IndividualCustomer) rawAccount;
		if(customer1.getEmail() != null){
			if(customer1.getEmail().equals(customer2.getEmail())){
				return true;
			}
		}
		return false;
	}

}
