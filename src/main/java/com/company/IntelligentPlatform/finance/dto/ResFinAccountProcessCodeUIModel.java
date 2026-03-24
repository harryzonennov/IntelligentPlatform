package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;

public class ResFinAccountProcessCodeUIModel extends SEUIComModel {


	@ISEUIModelMapping(fieldName = "processCode", seName = ResFinAccountProcessCode.SENAME, nodeName = ResFinAccountProcessCode.NODENAME, 
			nodeInstID = ResFinAccountProcessCode.NODENAME, showOnList = false, secId = ResFinAccountProcessCode.NODENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ResFinAccountProcessCode_processCode", valueFieldName = "processCodeValue")
	protected int processCode;

	@ISEUIModelMapping(seName = ResFinAccountProcessCode.SENAME, nodeName = ResFinAccountProcessCode.NODENAME, 
			nodeInstID = ResFinAccountProcessCode.NODENAME, showOnEditor = false, secId = ResFinAccountProcessCode.NODENAME, tabId = TABID_BASIC)
	protected String processCodeValue;


	public int getProcessCode() {
		return processCode;
	}

	public void setProcessCode(int processCode) {
		this.processCode = processCode;
	}

	public String getProcessCodeValue() {
		return processCodeValue;
	}

	public void setProcessCodeValue(String processCodeValue) {
		this.processCodeValue = processCodeValue;
	}

}
