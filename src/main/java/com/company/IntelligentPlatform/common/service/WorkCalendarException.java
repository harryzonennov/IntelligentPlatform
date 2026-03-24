package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class WorkCalendarException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_NO_TEMPLATE = 3;
	
	public static final int PARA_NO_YEAR = 4;

	public WorkCalendarException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					WorkCalendarException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public WorkCalendarException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						WorkCalendarException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						WorkCalendarException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public WorkCalendarException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					WorkCalendarException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	
}
