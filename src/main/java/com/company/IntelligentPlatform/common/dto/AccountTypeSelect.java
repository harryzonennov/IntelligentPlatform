package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.model.Account;

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
