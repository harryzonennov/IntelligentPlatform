package com.company.IntelligentPlatform.common.dto;

public class SerExtendUIControlQueryRequest {
	
	protected String refSEName;
	
	protected String refNodeName;
	
	protected String refUUID;
	
	protected String screenId;
	
	protected String lanCode;

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

}
