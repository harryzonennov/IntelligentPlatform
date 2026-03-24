package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class ServiceExtensionException extends ServiceEntityException{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int PARA_SYSTEM_WRONG = 2;

	
	public static final int PARA_WRONG_STOREMODEL = 3;	
	

	public ServiceExtensionException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceExtensionException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = PARA_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceExtensionException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceExtensionException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceExtensionException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceExtensionException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
