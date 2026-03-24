package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProdOrderReportItem extends DocMatItemNode {

	public final static String NODENAME = IServiceModelConstants.ProdOrderReportItem;

	public final static String SENAME = ProductionOrder.SENAME;

	protected int processIndex;

	protected String refTemplateSKUUUID;

	protected String refSerialId;

	protected String refInboundUUID;

	public ProdOrderReportItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PRODORDERREPORT;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefTemplateSKUUUID() {
		return refTemplateSKUUUID;
	}

	public void setRefTemplateSKUUUID(String refTemplateSKUUUID) {
		this.refTemplateSKUUUID = refTemplateSKUUUID;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(String refSerialId) {
		this.refSerialId = refSerialId;
	}

	public String getRefInboundUUID() {
		return refInboundUUID;
	}

	public void setRefInboundUUID(String refInboundUUID) {
		this.refInboundUUID = refInboundUUID;
	}


}
