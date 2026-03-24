package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class ServiceDocConfigureException extends ServiceEntityException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4363405859281461084L;
	
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int PARA_SYSTEM_WRONG = 2;

	public static final int PARA_INVALID_DOCTYPE = 3;
	
	public static final int PARA_NOT_IMPLEMENTED = 4;
	
	public static final int PARA_NOTFOUND_CONSUMERTYPE = 5;
	
	public static final int PARA_NOTFOUND_CONFIG= 6;
	
	public static final int PARA_NOTFOUND_INPUTPARA= 7;
	
	public static final int PARA_NOTFOUND_INPUTFIELD= 8;

	public ServiceDocConfigureException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceDocConfigureException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = PARA_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceDocConfigureException(int type, Object var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceDocConfigureException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceDocConfigureException(int type, Object var1, Object var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceDocConfigureException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
