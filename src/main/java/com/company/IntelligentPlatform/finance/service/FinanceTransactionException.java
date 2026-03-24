package com.company.IntelligentPlatform.finance.service;

public class FinanceTransactionException extends Exception {

	public static int CODE_ACC_TITLE_NOT_EXIST = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int errorCode;

	protected String errorMessage;

	public FinanceTransactionException(int errorCode) {
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

}
