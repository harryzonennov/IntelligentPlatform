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

public class PurchaseContractException extends ServiceEntityException {
	
	List<SimpleSEMessageResponse> errorMessageList = new ArrayList<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303199763058601180L;
	
	public static final int TYPE_SYSTEM_WRONG = 1;
	
	public static final int PARA_SYSTEM_ERROR = 2;
	
	public static final int PARA_NODONE_ITEM = 3;
	
	public static final int PARA_SPLIT_OVERAMOUNT = 4;
	
	public static final int PARA_REGPRD_NOMAT = 5;
	
	public static final int PARA2_REGPRD_DUPSERID = 6;
	
	public static final int PARA_REGPRD_INITCHECK = 7;
	
	public static final int TYPE_NOITEM = 8;
	
	public static final int PARA_MERGE_WRONG_STATUS = 9;
	
	public static final int PARA_MERGE_DIFF_SUPPLIER = 10;
	
	public static final int TYPE_MERGE_NO_SOURCE = 11;

	public PurchaseContractException(int errorCode) {
		super(errorCode);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					PurchaseContractException.class, errorCode);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public PurchaseContractException(int errorCode, List<SimpleSEMessageResponse> errorMessageList) {
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

	public PurchaseContractException(int errorCode, Object var) {
		super(errorCode);
		try {
			if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
						PurchaseContractException.class, errorCode);
				this.errorMessage = String.format(errorMesTamplate, var.toString());
			}else{
				this.errorMessage = ServiceExceptionHelper.getErrorMessage(
						PurchaseContractException.class, errorCode);
			}
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}
	
	public PurchaseContractException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					PurchaseContractException.class, type) ;
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
