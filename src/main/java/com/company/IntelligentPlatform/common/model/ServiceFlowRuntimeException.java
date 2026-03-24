package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceFlowRuntimeException extends ServiceEntityRuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1303199763058601180L;

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_ERROR = 2;

	public static final int PARA_NOPARA_LOGONINFO = 3;

	public static final int PARA_NO_FLOWROUTER = 4;

	public static final int PARA_NO_TARGET_USER = 5;

	public ServiceFlowRuntimeException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceFlowRuntimeException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceFlowRuntimeException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						ServiceFlowRuntimeException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var);
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ServiceFlowRuntimeException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceFlowRuntimeException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceFlowRuntimeException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	
}
