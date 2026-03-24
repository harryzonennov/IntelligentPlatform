package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.Account;

public interface IAccountDuplicateCheckLogicCore {
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException;

}
