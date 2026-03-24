package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ScanBarcodeResponseModel extends SEUIComModel{
	
	protected String outputResult;
	
	protected String errorResult;
	
	protected String inputBarcode;
	
	protected String processByID;
	
	protected String processByName;

	public String getOutputResult() {
		return outputResult;
	}

	public void setOutputResult(String outputResult) {
		this.outputResult = outputResult;
	}

	public String getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(String errorResult) {
		this.errorResult = errorResult;
	}

	public String getInputBarcode() {
		return inputBarcode;
	}

	public void setInputBarcode(String inputBarcode) {
		this.inputBarcode = inputBarcode;
	}

	public String getProcessByID() {
		return processByID;
	}

	public void setProcessByID(String processByID) {
		this.processByID = processByID;
	}

	public String getProcessByName() {
		return processByName;
	}

	public void setProcessByName(String processByName) {
		this.processByName = processByName;
	}

}
