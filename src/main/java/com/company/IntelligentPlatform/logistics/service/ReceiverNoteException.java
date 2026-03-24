package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ReceiverNoteException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233870117192301393L;
	/**
	 * ERROR CODE:unknown others system error
	 */
	public static final int TYPE_SYSTEM_WRONG = 1;
	/**
	 * ERROR CODE:no booking note error
	 */
	public static final int PARA_SYSTEM_WRONG = 2;
	
	
	public static final int PARA_WRONG_STATUS = 3;
	
	public static final int TYPE_WRONG_STATUS_ARRIVED = 4;	
	
	public static final int TYPE_WRONG_STATUS_INITIAL = 5;	
	
	public static final int PARA_NO_RECEIVERNOTE_OFBOOK = 6;
	
	public static final int PARA_NO_RECEIVERNOTE = 7;
	
	public ReceiverNoteException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ReceiverNoteException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ReceiverNoteException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						ReceiverNoteException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						ReceiverNoteException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

}
