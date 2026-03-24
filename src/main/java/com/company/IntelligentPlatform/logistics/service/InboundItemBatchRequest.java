package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Internal Class: to be used by batch in-bound delivery call-back
 * @author Zhang,Hang
 *
 */
public class InboundItemBatchRequest {
	
	private DocMatItemNode docMatItemNode;
	
	private ServiceEntityNode documentContent;
		
	private MaterialStockKeepUnit materialStockKeepUnit;
	
	private InboundItem inboundItem;
	
	private InboundItem refInboundItem;
	
	public InboundItemBatchRequest(){
		
	}

	public InboundItemBatchRequest(DocMatItemNode docMatItemNode,
			ServiceEntityNode documentContent, 
			MaterialStockKeepUnit materialStockKeepUnit,
			InboundItem inboundItem,
			InboundItem refInboundItem) {
		super();
		this.docMatItemNode = docMatItemNode;
		this.documentContent = documentContent;
		this.materialStockKeepUnit = materialStockKeepUnit;
		this.inboundItem = inboundItem;
		this.refInboundItem = refInboundItem;
	}

	public DocMatItemNode getDocMatItemNode() {
		return docMatItemNode;
	}

	public void setDocMatItemNode(DocMatItemNode docMatItemNode) {
		this.docMatItemNode = docMatItemNode;
	}

	public ServiceEntityNode getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(ServiceEntityNode documentContent) {
		this.documentContent = documentContent;
	}

	public MaterialStockKeepUnit getMaterialStockKeepUnit() {
		return materialStockKeepUnit;
	}

	public void setMaterialStockKeepUnit(MaterialStockKeepUnit materialStockKeepUnit) {
		this.materialStockKeepUnit = materialStockKeepUnit;
	}

	public InboundItem getInboundItem() {
		return inboundItem;
	}

	public void setInboundItem(InboundItem inboundItem) {
		this.inboundItem = inboundItem;
	}

	public InboundItem getRefInboundItem() {
		return refInboundItem;
	}

	public void setRefInboundItem(
			InboundItem refInboundItem) {
		this.refInboundItem = refInboundItem;
	}

}
