package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ProcessBOMOrderException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_ERROR = 2;

	public static final int PARA_NO_PROCESSROUTE = 3;

	public static final int PARA_NO_MATERIALSKU = 4;

	public static final int PARA_NO_BILLOFMATERIAL = 5;

	public static final int TYPE_CREATE_NO_PROCESSROUTE = 6;

	public static final int TYPE_CREATE_NO_MATERIALSKU = 7;

	public static final int TYPE_CREATE_NO_BILLOFMATERIAL = 8;

	public ProcessBOMOrderException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ProcessBOMOrderException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ProcessBOMOrderException(int errorCode, Object var) {
		super(errorCode);
		try {
			if (var != null
					& !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				String errorMesTamplate = ServiceExceptionHelper
						.getErrorMessage(ProcessBOMOrderException.class,
								errorCode);
				this.errorMessage = String.format(errorMesTamplate,
						var.toString());
			} else {
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ProcessBOMOrderException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ProcessBOMOrderException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ProcessBOMOrderException.class, type);
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
