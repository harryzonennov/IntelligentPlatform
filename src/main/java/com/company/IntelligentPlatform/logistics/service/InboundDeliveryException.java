package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class InboundDeliveryException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;

	/**
	 * ERROR CODE:No cargo information exist
	 */
	public static final int PARA_SYSTEM_WRONG = 1;
	/**
	 * ERROR CODE:No relative booking note can be found
	 */
	public static final int TYPE_NO_BOOKINGNOTE = 2;
	/**
	 * ERROR CODE:No relative booking note can be found
	 */
	public static final int TYPE_CANNOT_RECORD_NOAPPROVE = 3;
	/**
	 * ERROR CODE:wrong status for approve
	 */
	public static final int TYPE_WRG_STATUS_APPROVE = 4;
	/**
	 * ERROR CODE:wrong status for store record
	 */
	public static final int TYPE_WRG_STATUS_RECORD = 5;
	/**
	 * ERROR CODE:wrong approve status
	 */
	public static final int TYPE_WRG_APPROVESTATUS = 6;

	public static final int PARA_NO_SKU_FOUND = 7;

	public static final int PARA_NO_INBOUND_DELIVERY = 8;

	/**
	 * Can not create inbound delivery for this warehouse, cause it is currently
	 * in process of inventory check
	 */
	public static final int PARA2_INPROCESS_INVENTORYCHECK = 9;
	
	public static final int PARA_NO_INBOUNDFOUND_BYBARCODE = 10;
	
	public static final int PARA3_NO_INBOUNDFOUND_SKU_WAREHOUSE = 11;
	
	public static final int PARA3_DUPLICATE_INBOUNDFOUND_SKU_WAREHOUSE = 12;
	
	public static final int TYPE_FAILINBOUND_EMPTY_BARCODE = 13;

	public static final int PARA_NO_WAREHOUSE_SET = 14;

	public static final int PARA_EMPTY_SERIALID = 15;

	public static final int TYPE_CANT_APPROVE = 16;

	public InboundDeliveryException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					InboundDeliveryException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	public InboundDeliveryException(int errorCode, Object var) {
		super(errorCode);
		try {
			if (var != null
					& !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				String errorMesTamplate = ServiceExceptionHelper
						.getErrorMessage(InboundDeliveryException.class,
								errorCode);
				this.errorMessage = String.format(errorMesTamplate,
						var.toString());
			} else {
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						InboundDeliveryException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public InboundDeliveryException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					InboundDeliveryException.class, type) ;
			if(ServiceEntityStringHelper.checkNullString(var2)){
				this.errorMessage = String.format(errorMesTemplate, var1);
			}else{
				this.errorMessage = String.format(errorMesTemplate, var1, var2);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public InboundDeliveryException(int type, String var1, String var2, String var3) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					InboundDeliveryException.class, type) ;
			if(ServiceEntityStringHelper.checkNullString(var3)){
				this.errorMessage = String.format(errorMesTemplate, var1, var2);
			}else{
				this.errorMessage = String.format(errorMesTemplate, var1, var2, var3);
			}			
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	@Override
	public int getExceptionCategory(int errorCode) {
		if (errorCode == PARA_SYSTEM_WRONG) {
			return ServiceExceptionRecord.CATEGORY_UNKNOWN;
		}
		if (errorCode == TYPE_NO_BOOKINGNOTE) {
			return ServiceExceptionRecord.CATEGORY_TECH_ERROR;
		}
		if (errorCode == TYPE_CANNOT_RECORD_NOAPPROVE) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == TYPE_WRG_STATUS_APPROVE) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == TYPE_WRG_STATUS_RECORD) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == TYPE_WRG_APPROVESTATUS) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		return ServiceExceptionRecord.CATEGORY_UNKNOWN;
	}

}
