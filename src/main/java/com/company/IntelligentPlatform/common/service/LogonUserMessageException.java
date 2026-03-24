package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class LogonUserMessageException extends ServiceEntityException {

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int TYPE_NO_CATEGORY_FORUSER = 2;

	public static final int TYPE_WRONG_CATEGORYTITLE_CONFIG = 3;

	public static final int TYPE_SERVICE_INNER_CONFIG = 4;
	
	public static final int TYPE_WRONG_CONFIG = 5;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7865849096735477029L;

	public LogonUserMessageException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					LogonUserMessageException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public LogonUserMessageException(int type, String var) {
		super(type);
		try {
			if (var != null
					& !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				String errorMesTamplate = ServiceExceptionHelper
						.getErrorMessage(LogonUserMessageException.class,
								errorCode);
				this.errorMessage = String.format(errorMesTamplate,
						var.toString());
			} else {
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						LogonUserMessageException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
