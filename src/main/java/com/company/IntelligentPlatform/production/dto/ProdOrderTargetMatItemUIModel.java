package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class ProdOrderTargetMatItemUIModel extends DocMatItemUIModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "ProdOrderTargetMatItem_status", valueFieldName = "")
	protected int itemStatus;
	
	protected String parentDocId;
	
	protected int parentDocStatus;
	
	protected String parentDocStatusValue;
	
	protected int processIndex;
	
	protected String refSerialId;

	protected int splitEnableFlag;

	protected int traceMode;

	protected String traceModeValue;

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(String refSerialId) {
		this.refSerialId = refSerialId;
	}

	public int getSplitEnableFlag() {
		return splitEnableFlag;
	}

	public void setSplitEnableFlag(final int splitEnableFlag) {
		this.splitEnableFlag = splitEnableFlag;
	}

	public int getTraceMode() {
		return traceMode;
	}

	public void setTraceMode(final int traceMode) {
		this.traceMode = traceMode;
	}

	public String getTraceModeValue() {
		return traceModeValue;
	}

	public void setTraceModeValue(final String traceModeValue) {
		this.traceModeValue = traceModeValue;
	}
}
