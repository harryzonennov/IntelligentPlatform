package com.company.IntelligentPlatform.logistics.dto;

import java.util.Date;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * WarehouseStoreItemLog UI Model
 ** 
 * @author
 * @date Fri Sep 25 00:59:45 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class WarehouseStoreItemLogSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = WarehouseStoreItemLog.NODENAME, seName = Warehouse.SENAME, nodeInstID = WarehouseStoreItemLog.NODENAME,
			fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date createdTimeLow;
	
	protected String createdTimeLowStr;
	
	@BSearchFieldConfig(fieldName = "createdTime", nodeName = WarehouseStoreItemLog.NODENAME, seName = Warehouse.SENAME, nodeInstID = WarehouseStoreItemLog.NODENAME, 
			fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date createdTimeHigh;
	
	protected String createdTimeHighStr;

	@BSearchFieldConfig(fieldName = "warehouseName", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseName;

	@BSearchFieldConfig(fieldName = "warehouseID", nodeName = Warehouse.NODENAME, seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = WarehouseStoreItemLog.NODENAME, seName = Warehouse.SENAME, nodeInstID = WarehouseStoreItemLog.NODENAME)
	protected String warehouseUUID;

	@BSearchFieldConfig(fieldName = "documentType", nodeName = WarehouseStoreItemLog.NODENAME, seName = Warehouse.SENAME, nodeInstID = WarehouseStoreItemLog.NODENAME)
	protected int documentType;

	@BSearchFieldConfig(fieldName = "documentID", nodeName = ServiceEntityNode.NODENAME_ROOT, seName = IServiceModelConstants.InboundDelivery, nodeInstID = IServiceModelConstants.InboundDelivery)
	protected String documentID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUName", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "refMaterialSKUID", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUID;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;
	
	public Date getCreatedTimeLow() {
		return createdTimeLow;
	}
	
	public void setCreatedTimeLow(Date createdTimeLow) {
		this.createdTimeLow = createdTimeLow;
	}
	
	public String getCreatedTimeLowStr() {
		return createdTimeLowStr;
	}

	public void setCreatedTimeLowStr(String createdTimeLowStr) {
		this.createdTimeLowStr = createdTimeLowStr;
	}

	public String getCreatedTimeHighStr() {
		return createdTimeHighStr;
	}

	public void setCreatedTimeHighStr(String createdTimeHighStr) {
		this.createdTimeHighStr = createdTimeHighStr;
	}

	public Date getCreatedTimeHigh() {
		return createdTimeHigh;
	}
	
	public void setCreatedTimeHigh(Date createdTimeHigh) {
		this.createdTimeHigh = createdTimeHigh;
	}
	
	public String getWarehouseName() {
		return warehouseName;
	}
	
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	public String getWarehouseID() {
		return warehouseID;
	}
	
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	
	public String getWarehouseUUID() {
		return warehouseUUID;
	}

	public void setWarehouseUUID(String warehouseUUID) {
		this.warehouseUUID = warehouseUUID;
	}

	public int getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}
	
	public String getDocumentID() {
		return documentID;
	}
	
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	
	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}
	
	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}
	
	public String getRefMaterialSKUID() {
		return refMaterialSKUID;
	}
	
	public void setRefMaterialSKUID(String refMaterialSKUID) {
		this.refMaterialSKUID = refMaterialSKUID;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
