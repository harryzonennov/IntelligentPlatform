package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemConfigureUIField extends ReferenceNode {	
	
	public static final String NODENAME = IServiceModelConstants.SystemConfigureUIField;

	public static final String SENAME = SystemConfigureCategory.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public SystemConfigureUIField() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}
	
	protected boolean showFieldFlag = true;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_2000)
	protected String internationl18Content;
	
	protected boolean setI18NFlag = false;

	public boolean getShowFieldFlag() {
		return showFieldFlag;
	}

	public void setShowFieldFlag(boolean showFieldFlag) {
		this.showFieldFlag = showFieldFlag;
	}

	public String getInternationl18Content() {
		return internationl18Content;
	}

	public void setInternationl18Content(String internationl18Content) {
		this.internationl18Content = internationl18Content;
	}

	public boolean getSetI18NFlag() {
		return setI18NFlag;
	}

	public void setSetI18NFlag(boolean setI18NFlag) {
		this.setI18NFlag = setI18NFlag;
	} 
	
}
