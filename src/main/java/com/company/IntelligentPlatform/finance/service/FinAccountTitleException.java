package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * CreateAccTitleException
 * 
 * @author DylanYang
 * @date Apr 25th 2013
 */
public class FinAccountTitleException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3077793907128741087L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int TYPE_NO_TITLE_EXIST = 2;
	
	public static final int TYPE_DUPLICATE_TITLE_ID = 3;
	
	public static final int TYPE_NO_SUB_TITLE = 4;
	
	public static final int TYPE_MISMATCH_TITLE_ID = 5;
	
	public static final int TYPE_CANNOT_DEL_SUB_TITLE = 6;
	
	public static final int TYPE_CANNOT_DEL_SYSSTANDARD = 7;
	
	public static final int TYPE_NO_PARENT_TITLE = 8;
	
	public static final int TYPE_NO_TITLE_EXIST_BYID = 9;

	public FinAccountTitleException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					FinAccountTitleException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public FinAccountTitleException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						FinAccountTitleException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						FinAccountTitleException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	
}
