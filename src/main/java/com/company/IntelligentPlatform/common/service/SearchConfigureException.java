package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.io.Serializable;

public class SearchConfigureException extends ServiceEntityException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8763701922091962712L;

	public static final int PARA_SYSTEM_ERROR = 1;

	public static final int TYPE_NO_START_NODE = 2;

	public static final int TYPE_NO_BASE_NODE = 3;

	public static final int TYPE_MISSING_NODE = 4;

	public static final int TYPE_WRG_FIELD_NAME = 5;

	public static final int TYPE_SEARCH_ERROR = 6;
	
	public static final int TYPE_DUP_TABLENAME = 7;

	public SearchConfigureException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					SearchConfigureException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	public SearchConfigureException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					SearchConfigureException.class, type) + " " + var1;
			this.errorMessage = String.format(errorMesTemplate, var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	public SearchConfigureException(int type, String seName, String nodeName) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					SearchConfigureException.class, type)
					+ " "
					+ " SE:[ "
					+ seName + "] NODE:[" + nodeName + "]";
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

	public SearchConfigureException(int type, String seName, String nodeName,
			String fieldName) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					SearchConfigureException.class, type)
					+ " SE:[ "
					+ seName
					+ "] NODE:[" + nodeName + "] Field name[" + fieldName + "]";
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = SYSTEM_ERROR;
			this.errorMessage = ex.getMessage();
		}
	}

}
