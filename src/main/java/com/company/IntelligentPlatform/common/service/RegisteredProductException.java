package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisteredProductException extends MaterialException{

    private static final long serialVersionUID = 1L;
	
	public static final int TYPE_SYSTEM_ERROR = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_ACTIVE_INVALID_STATUS = 3;
	
	public static final int PARA2_DUPLICATE_SERIALID = 4;

	public static final int PARA_NO_TEMPLATE_MAT = 5;

	List<SimpleSEMessageResponse> errorMessageList = new ArrayList<>();
	
	public RegisteredProductException(int errorCode) {
		super(errorCode);	
		try {
			String errorMessage = ServiceExceptionHelper.getErrorMessage (
					RegisteredProductException.class, errorCode) ;
			this.errorMessage = errorMessage;
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public RegisteredProductException(int errorCode, Object var1) {
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage (
					RegisteredProductException.class, errorCode) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public RegisteredProductException(int errorCode, Object var1, Object var2) {
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage (
					RegisteredProductException.class, errorCode) ;
			if (var2 == null) {
				this.errorMessage = String.format(errorMesTemplate,var1);
			}else{
				this.errorMessage = String.format(errorMesTemplate,var1,var2);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	public RegisteredProductException(int errorCode, Map<String, List<SimpleSEMessageResponse>> errorMessageMap) {
		super(errorCode);
		this.errorMessageList =  ServiceExceptionHelper.handleErrorMessageMap(errorMessageMap, RegisteredProductException.class, errorCode);
	}


}
