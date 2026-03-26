package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.service.DocumentBarCodeService;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "SerialNumberSetting", catalog = "platform")
public class SerialNumberSetting extends ServiceEntityNode {
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SerialNumberSetting;
	
	public static final String ID_OVERALL = "overall";
	
	public static final int PLACEHOLDER = -1;
	
	public static final int SEPERATOR_NONE = 0;
	
	public static final int SEPERATOR_HYPHEN = 1;
	
	public static final int SEPERATOR_UNDERSTORE = 2;
	
	public static final int CATEGORY_DOCUMENT = 1;
	
	public static final int CATEGORY_SERVICEENTITY = 2;
	
	public static final int CATEGORY_FRAMEWORK = 3;
	
	public static final String DEF_BARCODE_TYPE = DocumentBarCodeService.EAN13;
	
	public static final int EAN13_COMP_GENTYPE_FIXCOMVALUE = 1;
	
	public static final int EAN13_COMP_GENTYPE_FIXSETVALUE = 2;
	
	public static final int EAN13_COMP_GENTYPE_RANDOM = 3;
	
	public static final int EAN13_COMP_GENTYPE_ASCEND = 4;
	
    public static final int EAN13_POST_GENTYPE_RANDOM = 1;
	
	public static final int EAN13_POST_GENTYPE_ASCEND = 2;
	
	public SerialNumberSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.seperator1 = SEPERATOR_NONE;
		this.seperator2 = SEPERATOR_NONE;
		this.coreNumberLength = 3;
		this.category = CATEGORY_DOCUMENT;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
		this.systemStandardCategory = StandardSystemCategoryProxy.CATE_SELF_DEFINED;
		this.barcodeType = DEF_BARCODE_TYPE;
	}
	
	protected String prefixCode1;
	
	protected int seperator1;

	@ISQLSepcifyAttribute(subType = ISQLSepcifyAttribute.SUBTYPE_JSON)
	protected String seperator1Json;
	
	protected String prefixTimeCode;
	
	protected int timeCodeFormat;
	
	protected String postfixTimeCode;
	
	protected int seperator2;

	@ISQLSepcifyAttribute(subType = ISQLSepcifyAttribute.SUBTYPE_JSON)
	protected String seperator2Json;
	
	protected int coreNumberLength;
	
	protected int coreStartNumber = 1;
	
	protected int coreStepSize = 1;
	
	protected int switchFlag;
	
    protected int category;
	
	protected int systemStandardCategory;
	
	protected String barcodeType;
	
	protected int ean13CompanyCodeGenType;
	
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

	public int getSeperator2() {
		return seperator2;
	}

	public void setSeperator2(int seperator2) {
		this.seperator2 = seperator2;
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

	public String getQrcodeType() {
		return qrcodeType;
	}

	public void setQrcodeType(String qrcodeType) {
		this.qrcodeType = qrcodeType;
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
