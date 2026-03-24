package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class InventoryTransferOrderException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;

	/**
	 * ERROR CODE:System Error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;
	/**
	 * ERROR CODE:System Error
	 */
	public static final int PARA_SYSTEM_WRONG = 2;
	
	/**
	 * Can not create inventory transfer order for this warehouse, cause it is currently
	 * in process of inventory check
	 */
	public static final int PARA_INPROCESS_INVENTORYCHECK = 3;
	
	public static final int PARA_NOTFOUND_OUTWAREHOUSE = 4;
		
	public static final int PARA_NOTFOUND_INWAREHOUSE = 5;
	
	public static final int PARA_NOTFOUND_OUTBOUNDDELIVERY = 6;
	
	public static final int PARA_NOTFOUND_INBOUNDDELIVERY = 7;
	
	public static final int PARA_DUPLICATE_WAREHOUSE = 8;
	
	public static final int TYPE_WRG_STATUS_APPROVE = 9;
	
	public static final int TYPE_WRG_STATUS_COUNTAPPROVE = 10;
	
	public static final int TYPE_WRG_STATUS_RECORD = 11;
	
	public static final int PARA_NODATA = 12;
	
	public static final int PARA_NOITEM_TOTRANSFER = 13;

	public InventoryTransferOrderException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					InventoryTransferOrderException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public InventoryTransferOrderException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						InventoryTransferOrderException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						InventoryTransferOrderException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public InventoryTransferOrderException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					InventoryTransferOrderException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
