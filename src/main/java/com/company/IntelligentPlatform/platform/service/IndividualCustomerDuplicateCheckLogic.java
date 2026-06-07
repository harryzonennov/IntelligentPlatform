package com.company.IntelligentPlatform.platform.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.service.AccountDuplicateCheckCoreTool;
import com.company.IntelligentPlatform.platform.service.AccountDuplicateCheckException;
import com.company.IntelligentPlatform.platform.service.IAccountDuplicateCheckLogicCore;
import com.company.IntelligentPlatform.platform.model.Account;

/**
 * System standard Account duplicate check logic:
 * Check individual customer duplicate 
 * @author Zhang,hang
 *
 */
@Service
public class IndividualCustomerDuplicateCheckLogic implements IAccountDuplicateCheckLogicCore{
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException{
		if(account == null || rawAccount == null){
			return false;
		}
		boolean checkDupByMobile = AccountDuplicateCheckCoreTool.checkDefaultByTelephone(account, rawAccount);
		return checkDupByMobile;
	}

}
