package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class CommonPictureSettingException extends ServiceEntityException{

    private static final long serialVersionUID = 1L;
	
	public static final int TYPE_SYSTEM_ERROR = 1;	
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_OVER_SIZE = 3;	
	
	public CommonPictureSettingException(int errorCode) {
		super(errorCode);	
		try {
			String errorMessage = ServiceExceptionHelper.getErrorMessage(
					CommonPictureSettingException.class, errorCode) ;
			this.errorMessage = errorMessage;
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public CommonPictureSettingException(int errorCode, Object var1){
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					CommonPictureSettingException.class, errorCode) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public CommonPictureSettingException(int errorCode, Object var1, Object var2){
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					CommonPictureSettingException.class, errorCode) ;
			if(var2 == null){
				this.errorMessage = String.format(errorMesTemplate,var1);
			} else {
				this.errorMessage = String.format(errorMesTemplate,var1,var2);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}


}
