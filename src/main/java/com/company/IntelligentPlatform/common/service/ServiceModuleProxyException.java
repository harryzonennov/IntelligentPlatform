package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class ServiceModuleProxyException extends ServiceEntityException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4441030207104619979L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_WRONG = 2;

	public static final int PARA_NOANNOTATION = 3;

	public static final int PARA_NOCOREMODULE = 4;

	public static final int PARA_NONSUPPORT_CREATE_NODE = 5;

	public static final int PARA_NOFIELD_BYNAME = 6;

	public static final int PARA_NO_STARTCOFIG_NODE = 7;

	public ServiceModuleProxyException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceModuleProxyException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceModuleProxyException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceModuleProxyException.class, type);		
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ServiceModuleProxyException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceModuleProxyException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
