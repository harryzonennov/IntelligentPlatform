package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;

public interface IServiceEntityException {
	
	int getErrorCode();

	void setErrorCode(int errorCode);

	String getErrorMessage() ;

	void setErrorMessage(String errorMessage) ;

	int getExceptionCategory(int errorCode) ;

	List<SimpleSEMessageResponse> getErrorMessageList();

	void setErrorMessageList(List<SimpleSEMessageResponse> errorMessageList);


}
