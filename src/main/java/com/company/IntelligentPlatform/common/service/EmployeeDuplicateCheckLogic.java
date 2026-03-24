package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.AccountDuplicateCheckCoreTool;
import com.company.IntelligentPlatform.common.service.AccountDuplicateCheckException;
import com.company.IntelligentPlatform.common.service.IAccountDuplicateCheckLogicCore;
import com.company.IntelligentPlatform.common.model.Account;

/**
 * System standard Account duplicate check logic:
 * Check employee duplicate by mobile phone
 * @author Zhang,hang
 *
 */
@Service
public class EmployeeDuplicateCheckLogic implements IAccountDuplicateCheckLogicCore{
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException{
		if(account == null || rawAccount == null){
			return false;
		}
		boolean checkDupByMobile = AccountDuplicateCheckCoreTool.checkDefaultByTelephone(account, rawAccount);
		return checkDupByMobile;
	}

}
