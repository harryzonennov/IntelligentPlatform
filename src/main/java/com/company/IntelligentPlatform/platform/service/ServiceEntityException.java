package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus; // replaced by local HttpStatus stub

import com.company.IntelligentPlatform.platform.service.ServiceJSONDataConstants;
import com.company.IntelligentPlatform.platform.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

/**
 * The General Service Entity Exception class
 * 
 * @author Zhang,Hang
 * 
 */
public class ServiceEntityException extends Exception implements IServiceEntityException{

	/**
	 * generated code
	 */
	private static final long serialVersionUID = -6378109014242238773L;

	protected int errorCode;

	protected String subErrorCode = "0";

	protected String errorMessage;
	
	List<SimpleSEMessageResponse> errorMessageList = new ArrayList<SimpleSEMessageResponse>();

	public static final int SYSTEM_ERROR = HttpStatus.SC_INTERNAL_SERVER_ERROR;

	public ServiceEntityException(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getExceptionCategory(int errorCode) {
		return 0;
	}
	

	public List<SimpleSEMessageResponse> getErrorMessageList() {
		return errorMessageList;
	}

	public void setErrorMessageList(List<SimpleSEMessageResponse> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

	/**
	 * generate the simple error code JSON data, only with error code
	 * information
	 *
	 * @return
	 */
	public String generateSimpleErrorJSON() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"").append(ServiceJSONDataConstants.ELE_ERROR_MSG).append("\":\"").append(this.errorMessage).append("\"");
		if (this.errorCode > 0) {
			sb.append(", \"").append(ServiceJSONDataConstants.ELE_ERROR_CODE).append("\":").append(this.errorCode);
		}
		if (!Objects.equals(this.subErrorCode, "0")) {
			sb.append(", \"").append(ServiceJSONDataConstants.ELE_SUBERROR_CODE).append("\":\"").append(this.subErrorCode).append("\"");
		}
		sb.append("}");
		return sb.toString();
	}

}
