package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "ProdOrderReportAttachment", catalog = "production")
public class ProdOrderReportAttachment extends ServiceEntityNode{

	public static final String NODENAME = IServiceModelConstants.ProdOrderReportAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionOrder;

	public ProdOrderReportAttachment() {
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
