package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.platform.model.LogonUser;

public class LogonUserPasswordSetUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "uuid", seName = LogonUser.SENAME, nodeName = LogonUser.NODENAME, showOnList = false, hiddenFlag = true)
	protected String baseUUID;
	
	protected String newPassword;
	
	protected String confirmPassword;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}	

}
