package com.company.IntelligentPlatform.common.service;


// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus; // replaced by local HttpStatus stub

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class AuthorizationException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7613998540753081240L;

	public static final String MSG_WRONG_AO_TYPE = "Wrong authorization type";
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int TYPE_NO_AUTHORIZATION = 2;
	
	public static final int TYPE_WRONG_AO_TYPE = 3;	

	protected int exceptionType;

	public AuthorizationException(int type) {
		super(type);
		try {
			this.errorCode = HttpStatus.SC_UNAUTHORIZED;
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					AuthorizationException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public AuthorizationException(int type,Object var) {
		super(type);
		try {
			this.errorCode = HttpStatus.SC_UNAUTHORIZED;
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						AuthorizationException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			} else {
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						AuthorizationException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
