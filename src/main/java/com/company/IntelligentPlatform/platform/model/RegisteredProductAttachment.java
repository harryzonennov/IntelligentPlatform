package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "RegisteredProductAttachment", catalog = "platform")
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
