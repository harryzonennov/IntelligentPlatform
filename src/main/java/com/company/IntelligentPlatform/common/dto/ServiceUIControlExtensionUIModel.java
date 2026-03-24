package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;

/**
 * Model to manage the standard extend control type could provided in tool-box
 * @author I043125
 *
 */
public class ServiceUIControlExtensionUIModel extends SEUIComModel{	
	
	public static final String EXTENDCONTR_SELECT2 = SerExtendUIControlSet.CONTRTYPE_SELECT2;
	
	public static final String EXTENDCONTR_TEXTAREA = SerExtendUIControlSet.CONTRTYPE_TEXTAREA;
	
	public static final String EXTENDCONTR_INPUT_TEXT = "input-string";
	
	public static final String EXTENDCONTR_INPUT_NUM = "input-number";	
	
	public static final String EXTENDCONTR_DATE = "input-date";
	
	protected String baseUUID;
	
	@ISEDropDownResourceMapping(resouceMapping = "ServiceUIControlExtension_controlType", valueFieldName = "controlTypeValue")
	protected String controlType;
	
	protected String controlTypeValue;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlTypeValue() {
		return controlTypeValue;
	}

	public void setControlTypeValue(String controlTypeValue) {
		this.controlTypeValue = controlTypeValue;
	}
	
}
