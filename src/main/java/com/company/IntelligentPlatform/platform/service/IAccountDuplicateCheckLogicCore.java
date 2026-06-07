package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.Account;

public interface IAccountDuplicateCheckLogicCore {
	
	public boolean checkDuplicateCore(Account account, Account rawAccount) throws AccountDuplicateCheckException;

}
