package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ServiceEntityExceptionContainer extends ServiceEntityException {

	private ServiceEntityException coreException;

	private static final long serialVersionUID = 1303199763058601180L;

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_ERROR = 2;

	public static final int PARA_SYSTEM_CONTAIN_CORE = 3;

	public ServiceEntityExceptionContainer(ServiceEntityException coreException) {
		super(PARA_SYSTEM_CONTAIN_CORE);
		this.coreException = coreException;
	}

	public ServiceEntityException getCoreException() {
		return coreException;
	}

	public void setCoreException(ServiceEntityException coreException) {
		this.coreException = coreException;
	}

	public ServiceEntityExceptionContainer(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceEntityExceptionContainer.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceEntityExceptionContainer(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						ServiceEntityExceptionContainer.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var);
			} else {
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ServiceEntityExceptionContainer.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceEntityExceptionContainer(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceEntityExceptionContainer.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	
}
