package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.model.IServiceJSONBasicErrorCode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
@Transactional
public class SystemExecutorTemplate {
		
	public String execute(LogonInfo logonInfo){
		return null;
	}
	
	public int checkResult(LogonInfo logonInfo){
		return IServiceJSONBasicErrorCode.DEF_OK;
	}
	
	public String getId(){
		return ServiceEntityStringHelper.headerToLowerCase(this.getClass().getSimpleName());
	}
	
	public String getName(){
		return this.getId();
	}

	public String getDescription(){
		return this.getId();
	}

}
