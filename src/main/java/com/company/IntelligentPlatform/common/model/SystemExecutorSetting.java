package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemExecutorSetting extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SystemExecutorSetting;
	
	protected int executionType;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_2000)
	protected String proxyName;
	
	protected String refPreExecuteSettingUUID;
	
	protected int executeBatchNumber;
	
	protected String refExecutorId;
	
	protected String refAOUUID;
	
	protected String refActionCode;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_8000)
	protected String requestContent;

	public SystemExecutorSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public int getExecutionType() {
		return executionType;
	}

	public void setExecutionType(int executionType) {
		this.executionType = executionType;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getRefPreExecuteSettingUUID() {
		return refPreExecuteSettingUUID;
	}

	public void setRefPreExecuteSettingUUID(String refPreExecuteSettingUUID) {
		this.refPreExecuteSettingUUID = refPreExecuteSettingUUID;
	}

	public String getRefAOUUID() {
		return refAOUUID;
	}

	public void setRefAOUUID(String refAOUUID) {
		this.refAOUUID = refAOUUID;
	}

	public String getRefActionCode() {
		return refActionCode;
	}

	public void setRefActionCode(String refActionCode) {
		this.refActionCode = refActionCode;
	}

	public int getExecuteBatchNumber() {
		return executeBatchNumber;
	}

	public void setExecuteBatchNumber(int executeBatchNumber) {
		this.executeBatchNumber = executeBatchNumber;
	}

	public String getRefExecutorId() {
		return refExecutorId;
	}

	public void setRefExecutorId(String refExecutorId) {
		this.refExecutorId = refExecutorId;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
}
