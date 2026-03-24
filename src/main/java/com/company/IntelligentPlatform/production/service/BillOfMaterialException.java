package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class BillOfMaterialException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_NO_UPITEM = 3;
	
	public static final int PARA_NO_MATERIAL = 4;	
	
	public static final int PARA_NO_BOMOrder = 5;
	
	public static final int PARA_NESTEDITEM = 6;
	
	public static final int PARA_NESTEDITEM_UPLAYER = 7;
	
	public static final int PARA_EXCEL_NOPARENT = 8;
	
	public static final int PARA2_EXCEL_NOMANPARA = 9;
	
	public static final int PARA_EXCEL_NOMAN_ID = 10;
	
	public static final int PARA_EXCEL_WRG_LAYER = 11;
	
	public static final int PARA_EXCEL_WRG_MATID = 12;
	
	public static final int PARA_EXCEL_WRG_UNITNAME = 13;

	public static final int PARA_NO_ACTIVE_BOMOrder = 14;

	public BillOfMaterialException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					BillOfMaterialException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public BillOfMaterialException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						BillOfMaterialException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						BillOfMaterialException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public BillOfMaterialException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					BillOfMaterialException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	
}
