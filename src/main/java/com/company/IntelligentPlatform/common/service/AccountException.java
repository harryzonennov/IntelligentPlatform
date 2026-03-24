package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class AccountException extends ServiceEntityException {

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int TYPE_CANNOT_DEL_FINACC = 2;
	
	public static final int TYPE_NO_CUSTOMER = 3;

	public AccountException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					AccountException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
