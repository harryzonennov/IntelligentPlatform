package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class OutboundDeliveryException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;

	/**
	 * ERROR CODE:Internal System error.
	 */
	public static final int PARA_SYSTEM_WRONG = 1;
	/**
	 * ERROR CODE:No relative booking note can be found
	 */
	public static final int TYPE_NO_BOOKINGNOTE = 2;
	/**
	 * ERROR CODE:Can not process outbound for non approved delivery
	 */
	public static final int TYPE_CANNOT_OUTBOUND_NOAPPROVE = 3;
	/**
	 * ERROR CODE:wrong status for approve
	 */
	public static final int TYPE_WRG_STATUS_APPROVE = 4;
	/**
	 * ERROR CODE:wrong status for store outbound
	 */
	public static final int TYPE_WRG_STATUS_OUTBOUND = 5;
	/**
	 * ERROR CODE:wrong approve status 
	 */
	public static final int TYPE_WRG_APPROVESTATUS = 6;
	
	/**
	 * ERROR CODE:can not create outbound delivery for no relative inbound delivery founded
	 */
	public static final int CANNOT_CREATE_NO_INBOUNDDELIVERY = 7;
	
	/**
	 * ERROR CODE:can not create outbound delivery for no process done inbound delivery founded
	 */
	public static final int CANNOT_CREATE_NO_INBOUND_DONE = 8;
	
	public static final int PARA_NO_WARESTORE_ITEM = 9;
	
	public static final int PARA_NO_OUTBOUND_DELIVERY = 10;
	
	public static final int PARA_OVER_AMOUNT_OUTBOUND = 11;
	
	public static final int PARA_SYSERROR_NO_SKUUNIT = 12;
	
	/**
	 * Can not create outbound delivery for this warehouse, cause it is currently
	 * in process of inventory check
	 */
	public static final int PARA_INPROCESS_INVENTORYCHECK = 13;
	
	
	
	/**
	 * Can not create outbound delivery for this warehouse, cause it is currently
	 * in process of inventory check
	 */
	public static final int PARA2_INPROCESS_INVENTORYCHECK = 15;
	
	public static final int PARA_NO_OUTBOUNDFOUND_BYBARCODE = 16;
	
	public static final int PARA3_NO_OUTBOUNDFOUND_SKU_WAREHOUSE = 17;
	
	public static final int PARA3_DUPLICATE_OUTBOUNDFOUND_SKU_WAREHOUSE = 18;
	
	public static final int TYPE_FAILOUTBOUND_EMPTY_BARCODE = 19;

	public OutboundDeliveryException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					OutboundDeliveryException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	public OutboundDeliveryException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						OutboundDeliveryException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						OutboundDeliveryException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public OutboundDeliveryException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					OutboundDeliveryException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public OutboundDeliveryException(int type, String var1, String var2, String var3) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					OutboundDeliveryException.class, type) ;
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
		if (errorCode == SYSTEM_ERROR) {
			return ServiceExceptionRecord.CATEGORY_UNKNOWN;
		}
		if (errorCode == TYPE_NO_BOOKINGNOTE) {
			return ServiceExceptionRecord.CATEGORY_TECH_ERROR;
		}	
		if (errorCode == TYPE_CANNOT_OUTBOUND_NOAPPROVE) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == TYPE_WRG_STATUS_APPROVE) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == TYPE_WRG_STATUS_OUTBOUND) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		if (errorCode == TYPE_WRG_APPROVESTATUS) {
			return ServiceExceptionRecord.CATEGORY_NORMAL;
		}
		return ServiceExceptionRecord.CATEGORY_UNKNOWN;
	}

}
