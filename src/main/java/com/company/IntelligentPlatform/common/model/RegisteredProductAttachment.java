package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class RegisteredProductAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.RegisteredProductAttachment;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public RegisteredProductAttachment() {
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
