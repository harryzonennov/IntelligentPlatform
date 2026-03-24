package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.SystemConfigureUIField;

public class SystemConfigureUIFieldUIModel extends SEUIComModel {
	
    protected boolean showFieldFlag;
	
	protected String internationl18Content;
	
	protected boolean setI18NFlag;

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
