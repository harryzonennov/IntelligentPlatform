package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

public class AccountDuplicateCheckException extends ServiceEntityException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_WRONG = 2;	
	
	public static final int PARA2_WRG_CONTROL_INTERFACE = 3;
	
	public static final int PARA_WRG_DUPCHECK_CLASS = 4;

	public AccountDuplicateCheckException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					AccountDuplicateCheckException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public AccountDuplicateCheckException(int type,Object var) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					AccountDuplicateCheckException.class, errorCode);
			this.errorMessage = String.format(errorMesTemplate,var);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public AccountDuplicateCheckException(int errorCode, Object var1, Object var2){
		super(errorCode);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					AccountDuplicateCheckException.class, errorCode) ;
			if(var2 == null){
				this.errorMessage = String.format(errorMesTemplate,var1);
			}else{
				this.errorMessage = String.format(errorMesTemplate,var1,var2);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
}
