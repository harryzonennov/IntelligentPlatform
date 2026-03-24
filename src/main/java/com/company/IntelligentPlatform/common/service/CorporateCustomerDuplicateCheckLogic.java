package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.AccountDuplicateCheckCoreTool;
import com.company.IntelligentPlatform.common.service.AccountDuplicateCheckException;
import com.company.IntelligentPlatform.common.service.IAccountDuplicateCheckLogicCore;
import com.company.IntelligentPlatform.common.model.Account;

/**
 * System standard Account duplicate check logic:
 * Check corporate customer duplicate 
 * @author Zhang,hang
 *
 */
@Service
public class CorporateCustomerDuplicateCheckLogic implements IAccountDuplicateCheckLogicCore{	
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException{
		if(account == null || rawAccount == null){
			return false;
		}
		boolean checkDupFlagByName = AccountDuplicateCheckCoreTool.checkDefaultByName(account, rawAccount);
		boolean checkDupByTelephone = AccountDuplicateCheckCoreTool.checkDefaultByTelephone(account, rawAccount);
		if(checkDupFlagByName || checkDupByTelephone){
			return true;
		}else{
			return false;
		}
	}

}
