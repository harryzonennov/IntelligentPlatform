package com.company.IntelligentPlatform.common.controller;

import org.springframework.beans.propertyeditors.PropertiesEditor;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class DoubleEditor extends PropertiesEditor {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.equals(ServiceEntityStringHelper.EMPTYSTRING)) {  
            text = ServiceEntityStringHelper.ZEROSTRING;  
        }  
        setValue(Double.parseDouble(text));
	}

	@Override
	public String getAsText() {
		return getValue().toString();
	}
	
}
