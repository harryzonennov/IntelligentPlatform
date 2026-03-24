package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class SystemConfigureResourceException extends ServiceEntityException{
	
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

	/**
	 * ERROR CODE:wrong UI model full name
	 */
	public static final int PARA_WRG_UI_MODEL = 3;
	/**
	 * ERROR CODE:wrong controller class full name
	 */
	public static final int PARA_WRG_CONTROLLER_CLASS = 4;
	/**
	 * ERROR CODE:controller class not interface implemented
	 */
	public static final int PARA2_WRG_CONTROL_INTERFACE = 5;
	/**
	 * ERROR CODE:Wrong fin account proxy class
	 */
	public static final int PARA_WRG_FINACCPROXY_CLASS = 6;
	/**
	 * ERROR CODE:target class not extend the right super class
	 */
	public static final int PARA2_WRG_SUPER_CLASS = 7;
	/**
	 * ERROR CODE:target class not exist or anything wrong
	 */
	public static final int PARA_WRG_TARGET_CLASS = 8;
	

	public SystemConfigureResourceException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					SystemConfigureResourceException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = PARA_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public SystemConfigureResourceException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					SystemConfigureResourceException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public SystemConfigureResourceException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					SystemConfigureResourceException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
