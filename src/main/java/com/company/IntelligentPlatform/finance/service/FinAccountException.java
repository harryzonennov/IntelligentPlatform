package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class FinAccountException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7820058271305490305L;

	public FinAccountException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					FinAccountException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public FinAccountException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						FinAccountException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						FinAccountException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int TYPE_NO_FINACCOUNT_AUTHOR = 2;

	public static final int TYPE_CANNOT_VERIFY = 3;

	public static final int TYPE_CANNOT_RECORD = 4;

	public static final int TYPE_NO_VERIFY_NOT_ACCOUNTANT = 5;

	public static final int TYPE_NO_VERIFY_NOT_AUDIT = 6;

	public static final int TYPE_NO_VERIFY_LOCK = 7;

	public static final int TYPE_NO_AUDIT_LOCK = 8;

	public static final int TYPE_NO_RECORD_NOT_CASHIER = 9;

	public static final int TYPE_NO_RECORD_NOT_VERIFY = 10;

	public static final int TYPE_NO_RECORD_LOCK = 11;

	public static final int TYPE_CANNOT_AUDIT = 12;
	
	public static final int TYPE_NO_ACCOUNT_TITLE = 13;
	
	public static final int TYPE_NO_PARENT_ACCOUNT_TITLE = 14;

	public int getExceptionCategory(int errorCode) {
		return ServiceExceptionRecord.CATEGORY_NORMAL;
	}

}
