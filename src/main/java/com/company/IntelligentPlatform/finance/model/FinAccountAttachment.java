package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinFinance - FinAccountAttachment.java
 * New table: FinAccountAttachment (schema: finance)
 */
@Entity
@Table(name = "FinAccountAttachment", schema = "finance")
public class FinAccountAttachment extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.FinAccountAttachment;

	@Lob
	@Column(name = "content")
	protected byte[] content;

	@Column(name = "fileType")
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
