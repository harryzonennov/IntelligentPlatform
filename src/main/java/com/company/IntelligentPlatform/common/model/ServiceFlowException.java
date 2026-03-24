package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceFlowException extends ServiceEntityException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1303199763058601180L;

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_ERROR = 2;

	public static final int TYPE_NULL_SERVICEUIMODEL_ID = 3;

	public static final int PARA_NULL_SERVICEUIMODEL = 4;

	public static final int TYPE_NULL_NODEINST_ID = 5;

	public static final int PARA_NULL_NODEINST = 6;

	public static final int PARA_NO_TARGET_ORG = 7;

	public static final int PARA_NO_TARGET_USER = 8;

	public ServiceFlowException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceFlowException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceFlowException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						ServiceFlowException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var);
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ServiceFlowException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceFlowException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceFlowException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	
}
