package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class SerialNumberSettingUIModel extends SEUIComModel {
	
	protected String prefixCode1;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerialNumberSetting_seperator", valueFieldName = "")
	protected int seperator1;

	protected String seperator1Json;
	
	protected String prefixTimeCode;
	
	protected int timeCodeFormat;
	
	protected String postfixTimeCode;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerialNumberSetting_seperator", valueFieldName = "")
	protected int seperator2;

	protected String seperator2Json;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerialNumberSetting_coreNumberFormat", valueFieldName = "")
	protected int coreNumberLength;
	
	protected int coreStartNumber = 1;
	
	protected int coreStepSize = 1;
	
	protected int switchFlag;
	
	protected String switchFlagValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerialNumberSetting_category", valueFieldName = "categoryValue")
	protected int category;
	
	protected String categoryValue;
	
	protected int systemStandardCategory;
	
	protected String systemStandardCategoryValue;
	
	protected String serialNumberExample;
	
	protected String barcodeType;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerialNumberSetting_ean13CompanyCodeGenType", valueFieldName = "")
    protected int ean13CompanyCodeGenType;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerialNumberSetting_ean13PostCodeGenType", valueFieldName = "")
	protected int ean13PostCodeGenType;
	
	protected int barcodeAsendSize;
	
	protected int barcodeStartValue;
	
	protected String qrcodeType;

	public String getPrefixCode1() {
		return prefixCode1;
	}

	public void setPrefixCode1(String prefixCode1) {
		this.prefixCode1 = prefixCode1;
	}

	public int getSeperator1() {
		return seperator1;
	}

	public void setSeperator1(int seperator1) {
		this.seperator1 = seperator1;
	}

	public String getPrefixTimeCode() {
		return prefixTimeCode;
	}

	public void setPrefixTimeCode(String prefixTimeCode) {
		this.prefixTimeCode = prefixTimeCode;
	}

	public int getTimeCodeFormat() {
		return timeCodeFormat;
	}

	public void setTimeCodeFormat(int timeCodeFormat) {
		this.timeCodeFormat = timeCodeFormat;
	}

	public String getPostfixTimeCode() {
		return postfixTimeCode;
	}

	public void setPostfixTimeCode(String postfixTimeCode) {
		this.postfixTimeCode = postfixTimeCode;
	}

	public int getSeperator2() {
		return seperator2;
	}

	public void setSeperator2(int seperator2) {
		this.seperator2 = seperator2;
	}

	public int getCoreNumberLength() {
		return coreNumberLength;
	}

	public void setCoreNumberLength(int coreNumberLength) {
		this.coreNumberLength = coreNumberLength;
	}

	public int getCoreStartNumber() {
		return coreStartNumber;
	}

	public void setCoreStartNumber(int coreStartNumber) {
		this.coreStartNumber = coreStartNumber;
	}

	public int getCoreStepSize() {
		return coreStepSize;
	}

	public void setCoreStepSize(int coreStepSize) {
		this.coreStepSize = coreStepSize;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSystemStandardCategory() {
		return systemStandardCategory;
	}

	public void setSystemStandardCategory(int systemStandardCategory) {
		this.systemStandardCategory = systemStandardCategory;
	}

	public String getSwitchFlagValue() {
		return switchFlagValue;
	}

	public void setSwitchFlagValue(String switchFlagValue) {
		this.switchFlagValue = switchFlagValue;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getSystemStandardCategoryValue() {
		return systemStandardCategoryValue;
	}

	public void setSystemStandardCategoryValue(String systemStandardCategoryValue) {
		this.systemStandardCategoryValue = systemStandardCategoryValue;
	}

	public String getSerialNumberExample() {
		return serialNumberExample;
	}

	public void setSerialNumberExample(String serialNumberExample) {
		this.serialNumberExample = serialNumberExample;
	}

	public String getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(String barcodeType) {
		this.barcodeType = barcodeType;
	}

	public int getEan13CompanyCodeGenType() {
		return ean13CompanyCodeGenType;
	}

	public void setEan13CompanyCodeGenType(int ean13CompanyCodeGenType) {
		this.ean13CompanyCodeGenType = ean13CompanyCodeGenType;
	}

	public int getEan13PostCodeGenType() {
		return ean13PostCodeGenType;
	}

	public void setEan13PostCodeGenType(int ean13PostCodeGenType) {
		this.ean13PostCodeGenType = ean13PostCodeGenType;
	}

	public int getBarcodeAsendSize() {
		return barcodeAsendSize;
	}

	public void setBarcodeAsendSize(int barcodeAsendSize) {
		this.barcodeAsendSize = barcodeAsendSize;
	}

	public int getBarcodeStartValue() {
		return barcodeStartValue;
	}

	public void setBarcodeStartValue(int barcodeStartValue) {
		this.barcodeStartValue = barcodeStartValue;
	}

	public String getQrcodeType() {
		return qrcodeType;
	}

	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
	}

	public String getSeperator1Json() {
		return seperator1Json;
	}

	public void setSeperator1Json(final String seperator1Json) {
		this.seperator1Json = seperator1Json;
	}

	public String getSeperator2Json() {
		return seperator2Json;
	}

	public void setSeperator2Json(final String seperator2Json) {
		this.seperator2Json = seperator2Json;
	}
}
