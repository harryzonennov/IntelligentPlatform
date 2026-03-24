package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.RegisteredProductActionLog;
import com.company.IntelligentPlatform.common.controller.DocActionNodeUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class RegisteredProductActionLogUIModel extends DocActionNodeUIModel {

	@ISEUIModelMapping(fieldName = "switchFlag", seName = RegisteredProductActionLog.SENAME, nodeName = RegisteredProductActionLog.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "MaterialStockKeepUnit_actionCode", valueFieldName = "actionCodeValue")
	protected int actionCode;
	
	protected String actionCodeValue;
	
	protected String updateFieldsArray;


	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionCodeValue() {
		return actionCodeValue;
	}

	public void setActionCodeValue(String actionCodeValue) {
		this.actionCodeValue = actionCodeValue;
	}

	public String getUpdateFieldsArray() {
		return updateFieldsArray;
	}

	public void setUpdateFieldsArray(String updateFieldsArray) {
		this.updateFieldsArray = updateFieldsArray;
	}


}
