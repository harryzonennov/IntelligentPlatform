package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.model.Account;

public class FinAccountObjectTypeSelect {
	
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountObject_type", valueFieldName = "")
	protected int objectType;
	
	public FinAccountObjectTypeSelect(){
		// set default one
		objectType = Account.ACCOUNTTYPE_IND_CUSTOMER;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

}
