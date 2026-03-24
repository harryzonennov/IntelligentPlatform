package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class FinAccountMatItemAttachment extends ServiceEntityNode{

	public static final String NODENAME = IServiceModelConstants.FinAccountMatItemAttachment;

	public static final String SENAME = IServiceModelConstants.FinAccount;

	public FinAccountMatItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	protected byte[] content;

	protected String fileType;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
