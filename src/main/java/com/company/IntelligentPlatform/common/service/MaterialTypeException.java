package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class MaterialTypeException extends ServiceEntityRuntimeException{

    private static final long serialVersionUID = 1L;
	
	public static final int TYPE_SYSTEM_ERROR = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_NON_FOUND_MATERIALTYPE = 3;	
	
	public MaterialTypeException(int errorCode) {
		super(errorCode);	
		try {
			String errorMessage = ServiceExceptionHelper.getErrorMessage(
					MaterialTypeException.class, errorCode) ;
			this.errorMessage = errorMessage;
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public MaterialTypeException(int errorCode, Object var1){
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					MaterialTypeException.class, errorCode) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public MaterialTypeException(int errorCode, Object var1, Object var2){
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					MaterialTypeException.class, errorCode) ;
			if(var2 == null){
				this.errorMessage = String.format(errorMesTemplate,var1);
			}else{
				this.errorMessage = String.format(errorMesTemplate,var1,var2);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}


}
