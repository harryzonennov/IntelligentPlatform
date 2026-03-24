package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;

public class FinAccountChartException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7820058271305490305L;

	public FinAccountChartException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					FinAccountChartException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int TYPE_NO_ACCOBJECT = 2;
	
	public static final int TYPE_WRONG_TIME_SET = 3;
	
	public static final int TYPE_TOOLONG_TS = 4;

	public int getExceptionCategory(int errorCode) {
		return ServiceExceptionRecord.CATEGORY_NORMAL;
	}

}
