package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdOrderReport;
import com.company.IntelligentPlatform.production.model.ProdOrderReportItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;

@Component
public class ProdOrderReportItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ProdOrderReportItem.NODENAME, 
			seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReportItem.NODENAME)
	protected String uuid;
	
	@BSearchFieldConfig(fieldName = "prevDocType", nodeName = ProdOrderReportItem.NODENAME, 
			seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReportItem.NODENAME)
	protected int prevDocType;
	
	@BSearchFieldConfig(fieldName = "prevDocMatItemUUID", nodeName = ProdOrderReportItem.NODENAME, 
			seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReportItem.NODENAME)
	protected String prevDocMatItemUUID;
	
	@BSearchFieldConfig(fieldName = "reservedDocType", nodeName = ProdOrderReportItem.NODENAME, 
			seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReportItem.NODENAME)
	protected int reservedDocType;
	
	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = ProdOrderReportItem.NODENAME, 
			seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReportItem.NODENAME)
	protected String refMaterialSKUUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, 
			seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.NODENAME)
	protected String refMaterialSKUId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, 
			seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.NODENAME)
	protected String refMaterialSKUName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getPrevDocType() {
		return prevDocType;
	}

	public void setPrevDocType(int prevDocType) {
		this.prevDocType = prevDocType;
	}

	public int getReservedDocType() {
		return reservedDocType;
	}

	public void setReservedDocType(int reservedDocType) {
		this.reservedDocType = reservedDocType;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getPrevDocMatItemUUID() {
		return prevDocMatItemUUID;
	}

	public void setPrevDocMatItemUUID(String prevDocMatItemUUID) {
		this.prevDocMatItemUUID = prevDocMatItemUUID;
	}

	
}
