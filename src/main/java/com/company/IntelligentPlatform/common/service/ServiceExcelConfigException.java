package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ServiceExcelConfigException extends ServiceEntityException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4363405859281461084L;
	
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_WRONG = 2;

	/**
	 * ERROR CODE:can not find configure XML file
	 */
	public static final int PARA_NO_CONFIGURE = 3;
	/**
	 * ERROR CODE:configure folder path is wrongly configured
	 */
	public static final int WRONG_CONFIG_PATH = 4;
	/**
	 * ERROR CODE:error happen when writing excel report
	 */
	public static final int WRONG_WRITE_REPORT = 5;
	
	public static final int TYPE_NOTSUPPORT_EXCELUPPORT = 6;

	public ServiceExcelConfigException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceExcelConfigException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceExcelConfigException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						ServiceExcelConfigException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var);
			} else {
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ServiceExcelConfigException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	

}
