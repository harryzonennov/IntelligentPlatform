package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SearchFieldConfig extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.SearchFieldConfig;

	public static final String SENAME = IServiceModelConstants.SearchProxyConfig;

	public final static int CATE_CONVERSION = 1;

	public final static int CATE_CALCULATION = 2;

	public final static int CATE_SELECTMAP = 3;

	public final static int REF_SELECTMAP = 1;

	public final static int REF_SELECTMODULE = 2;

	/**
	 * In case the value is <code>CATE_SELECTMAP</code>, then this field is
	 * selection type
	 */
	protected int category;

	protected String fieldName;

	/**
	 * In case value is <code>REF_SELECTMAP</code>, then will generate selection
	 * map, In case the value is <code>REF_SELECTMODULE</code>, it will generate
	 * the logic to select from module.
	 */
	protected int refSelectType;

	/**
	 * Value will be filled after select method resource generated
	 */
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_800)
	protected String refSelectURL;

	protected int switchFlag;

	public SearchFieldConfig() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.category = CATE_CONVERSION;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getRefSelectType() {
		return refSelectType;
	}

	public void setRefSelectType(int refSelectType) {
		this.refSelectType = refSelectType;
	}

	public String getRefSelectURL() {
		return refSelectURL;
	}

	public void setRefSelectURL(String refSelectURL) {
		this.refSelectURL = refSelectURL;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}
}
