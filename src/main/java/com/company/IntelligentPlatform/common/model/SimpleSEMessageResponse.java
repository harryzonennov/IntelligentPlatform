package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.IServiceEntityException;

/**
 * simple & default JSON response to front end
 * @author Zhang,Hang
 *
 */
public class SimpleSEMessageResponse extends ServiceEntityJSONData {

	public static final int MESSAGELEVEL_INFO = 1;

	public static final int MESSAGELEVEL_WARN = 2;

	public static final int MESSAGELEVEL_ERROR = 3;

	protected int messageLevel;

	protected String messageContent;

	protected IServiceEntityException refException;

	protected int errorCode;

	protected String[] errorParas;

	public IServiceEntityException getRefException() {
		return refException;
	}

	public void setRefException(IServiceEntityException refException) {
		this.refException = refException;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String[] getErrorParas() {
		return errorParas;
	}

	public void setErrorParas(String[] errorParas) {
		this.errorParas = errorParas;
	}

	public int getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(int messageLevel) {
		this.messageLevel = messageLevel;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

}
