package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "ServiceDocumentSetting", schema = "platform")
public class ServiceDocumentSetting extends ServiceEntityNode {

	public static final String FIELD_DOCUMENT_TYPE = "documentType";

	public static final String FIELD_REF_SERVICE_ENTITY_NAME = "refServiceEntityName";

	public static final String FIELD_REF_NODE_NAME = "refNodeName";
	
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceDocumentSetting;

	protected String refServiceEntityName;

	protected String refNodeName;
	
	public ServiceDocumentSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}
	
	protected String documentType;
	
	protected Date validToDate;

	public Date getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

	public String getRefServiceEntityName() {
		return refServiceEntityName;
	}

	public void setRefServiceEntityName(String refServiceEntityName) {
		this.refServiceEntityName = refServiceEntityName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}
}
