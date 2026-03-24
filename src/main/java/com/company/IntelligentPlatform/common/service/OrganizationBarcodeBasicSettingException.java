package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class OrganizationBarcodeBasicSettingException extends ServiceEntityException{
	
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

	
	public static final int PARA_NOSETTING_ORGANIZATION = 3;
	

	public OrganizationBarcodeBasicSettingException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					OrganizationBarcodeBasicSettingException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = PARA_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public OrganizationBarcodeBasicSettingException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					OrganizationBarcodeBasicSettingException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public OrganizationBarcodeBasicSettingException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					OrganizationBarcodeBasicSettingException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate,var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
