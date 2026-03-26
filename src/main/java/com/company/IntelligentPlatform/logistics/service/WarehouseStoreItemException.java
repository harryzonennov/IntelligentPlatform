package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class WarehouseStoreItemException extends ServiceEntityRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233870117192301393L;
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_WRONG = 2;
	/**
	 * ERROR CODE:can not update warehouse item for over amount request
	 */
	public static final int PARA_OVER_AMOUNT = 3;
	
	/**
	 * ERROR CODE:can not out-bound since no warehouse setting
	 */
	public static final int TYPE_NO_WAREHOUSE_OUT = 4;

	public WarehouseStoreItemException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					WarehouseStoreItemException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public WarehouseStoreItemException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						WarehouseStoreItemException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						WarehouseStoreItemException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	@Override
	public int getExceptionCategory(int errorCode) {		
		if (errorCode == TYPE_SYSTEM_WRONG) {
			return ServiceExceptionRecord.CATEGORY_UNKNOWN;
		}
		if (errorCode == PARA_OVER_AMOUNT) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}		
		return ServiceExceptionRecord.CATEGORY_UNKNOWN;
	}

}
