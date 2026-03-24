package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ProductionOrderException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_NO_PRODORDER= 3;
	
	public static final int PARA_NO_MATERIALSKU = 4;
	
	public static final int PARA_NO_BOM = 5;	
	
	public static final int PARA_RETURN_EXCEED_LIMIT = 6;
	
	public static final int PARA_ZERO_REQUEST = 7;
	
	public static final int PARA_NO_DECISION_MAT = 8;
	
	public static final int PARA_NO_SERIAL_SET = 9;	
	
	public static final int PARA2_NO_PRODITEM_BYBOM = 10;
	
	public static final int PARA_NO_PROPOSAL = 11;

	public static final int PARA_NO_PICKITEM = 12;

	public static final int PARA_NO_TARGETITEM = 13;

	public static final int PARA_NODONE_TAEGET = 14;

	public static final int PARA_DUPITEM_WITHBOMITEM = 15;

	public static final int PARA_NO_ORDERITEM = 16;

	public ProductionOrderException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ProductionOrderException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ProductionOrderException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						ProductionOrderException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ProductionOrderException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public ProductionOrderException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ProductionOrderException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	
}
