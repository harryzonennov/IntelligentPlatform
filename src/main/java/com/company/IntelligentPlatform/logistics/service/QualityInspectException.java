package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class QualityInspectException extends ServiceEntityException {
	
	List<SimpleSEMessageResponse> errorMessageList = new ArrayList<SimpleSEMessageResponse>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_CHECKITEM_INIT = 3;
	
	public static final int PARA_NO_DATA = 4;
	
	public static final int PARA_FAIL_OVERAMOUNT = 5;

	public static final int PARA_EMPTY_SERIALID = 6;

	public static final int TYPE_CANT_START = 7;

	public QualityInspectException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					QualityInspectException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public QualityInspectException(int errorCode, List<SimpleSEMessageResponse> errorMessageList) {
		super(errorCode);
		try {
			ServiceCollectionsHelper.traverseListInterrupt(errorMessageList, simpleSEMessageResponse -> {
				try {
					ServiceExceptionHelper.handleSimpleMessageResponse(simpleSEMessageResponse, PurchaseContractException.class, errorCode);
				} catch (ServiceEntityInstallationException e) {
					throw new RuntimeException(e);
				}
				return true;
			});
		} catch (DocActionException e) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = e.getErrorMessage();
		}
		this.errorMessageList = errorMessageList;
	}

	public QualityInspectException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						QualityInspectException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						QualityInspectException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public QualityInspectException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					QualityInspectException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public List<SimpleSEMessageResponse> getErrorMessageList() {
		return errorMessageList;
	}

	public void setErrorMessageList(List<SimpleSEMessageResponse> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

	
}
