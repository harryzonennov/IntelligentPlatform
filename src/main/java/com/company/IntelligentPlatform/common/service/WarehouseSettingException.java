package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;


public class WarehouseSettingException extends ServiceEntityRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233870117192301393L;
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;
	/**
	 * ERROR CODE:can not delete the warehouse, still store item existed
	 */
	public static final int CANNOT_DEL_HOUSE_STOREITEM = 2;
	
	/**
	 * ERROR CODE:can not delete the warehouseArea, still store item existed
	 */
	public static final int CANNOT_DEL_AREA_STOREITEM = 3;
	/**
	 * ERROR CODE:can not create house from site cause no site could be found
	 */
	public static final int CANNOT_CREATE_HOUSE_NO_SITE = 4;
	/**
	 * ERROR CODE:can not create house from site cause no site could be found
	 */
	public static final int CANNOT_CREATE_AREA_NO_HOUSE = 5;
	/**
	 * 
	 * @param errorCode
	 */
	public static final int PARA_NO_WAREHOUSE = 6;
	
	public static final int PARA_NO_PARENT_CUSTOMER = 7;
	

	public WarehouseSettingException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					WarehouseSettingException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public WarehouseSettingException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						WarehouseSettingException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						WarehouseSettingException.class, errorCode);
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
		if (errorCode == CANNOT_DEL_HOUSE_STOREITEM) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == CANNOT_DEL_AREA_STOREITEM) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == CANNOT_CREATE_HOUSE_NO_SITE) {
			return ServiceExceptionRecord.CATEGORY_TECH_ERROR;
		}
		if (errorCode == CANNOT_CREATE_AREA_NO_HOUSE) {
			return ServiceExceptionRecord.CATEGORY_TECH_ERROR;
		}
		return ServiceExceptionRecord.CATEGORY_UNKNOWN;
	}

}
