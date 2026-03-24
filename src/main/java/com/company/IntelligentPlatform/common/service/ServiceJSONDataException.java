package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;

public class ServiceJSONDataException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1916876017599510843L;

	public static final String MSG_FILED_ILLEGAL = "Illegal field from SE:";

	public static final int TYPE_CANNOT_FIND_FIELD = 1;

	public static final int TYPE_CANNOT_ILLEGAL_FIELD = 2;

	public static final int TYPE_SYSTEM_WRONG = 3;

	public ServiceJSONDataException(int errorCode) {
		super(errorCode);
	}

	public ServiceJSONDataException(int errorCode, String var1, String var2) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceJSONDataException.class, errorCode)
					+ " ["
					+ var1
					+ "] [" + var2 + "]";
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = ServiceEntityException.SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	@Override
	public int getExceptionCategory(int errorCode) {
		if (errorCode == TYPE_CANNOT_FIND_FIELD) {
			return ServiceExceptionRecord.CATEGORY_TECH_ERROR;
		}
		if (errorCode == TYPE_CANNOT_ILLEGAL_FIELD) {
			return ServiceExceptionRecord.CATEGORY_TECH_ERROR;
		}
		if (errorCode == TYPE_SYSTEM_WRONG) {
			return ServiceExceptionRecord.CATEGORY_UNKNOWN;
		}
		return ServiceExceptionRecord.CATEGORY_UNKNOWN;
	}

}
