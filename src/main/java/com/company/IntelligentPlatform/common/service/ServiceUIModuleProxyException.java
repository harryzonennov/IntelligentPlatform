package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;


public class ServiceUIModuleProxyException extends ServiceEntityException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4441030207104619979L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_WRONG = 2;

	public static final int PARA_NOANNOTATION = 3;

	public static final int PARA_NOCOREMODULE = 4;

	public ServiceUIModuleProxyException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceUIModuleProxyException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceUIModuleProxyException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceModuleProxyException.class, type);
			this.errorMessage = String.format(errorMesTemplate, var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceUIModuleProxyException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceUIModuleProxyException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	

}
