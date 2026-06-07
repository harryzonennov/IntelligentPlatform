package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.platform.model.Account;

public class AccountTypeSelect {
	
	@ISEDropDownResourceMapping(resouceMapping = "Account_type", valueFieldName = "")
	protected int accountType;
	
	protected String accountTypeComment;
	
	public AccountTypeSelect(){
		// set default one
		accountType = Account.ACCOUNTTYPE_IND_CUSTOMER;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getAccountTypeComment() {
		return accountTypeComment;
	}

	public void setAccountTypeComment(String accountTypeComment) {
		this.accountTypeComment = accountTypeComment;
	}

}
