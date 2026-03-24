package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;


@Component
public class ProdPickingRefMaterialItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME,
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected String uuid;
	
	@BSearchFieldConfig(fieldName = "parentNodeUUID", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected String parentNodeUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "nextDocType", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected int nextDocType;
	
	@BSearchFieldConfig(fieldName = "nextDocMatItemUUID", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected String nextDocMatItemUUID;

	@BSearchFieldConfig(fieldName = "prevDocType", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected int prevDocType;

	@BSearchFieldConfig(fieldName = "itemStatus", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected int itemStatus;
	
	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected String refMaterialSKUUUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME, showOnUI = false)
	protected String itemMaterialSKUName;	
	
	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME, showOnUI = false)
	protected String itemMaterialSKUId;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, 
			nodeInstID = ProdPickingOrder.SENAME, showOnUI = false)
	protected String refPickingOrderId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, 
			nodeInstID = ProdPickingOrder.SENAME, showOnUI = false)
	protected String refPickingOrderName;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, 
			nodeInstID = ProductionOrder.SENAME, showOnUI = false)
	protected String refOrderName;

	@BSearchFieldConfig(fieldName = "id", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, 
			nodeInstID = ProductionOrder.SENAME, showOnUI = false)
	protected String refOrderId;
	
	@BSearchFieldConfig(fieldName = "refProdOrderUUID", nodeName = ProdPickingRefOrderItem.NODENAME, seName = ProdPickingRefOrderItem.SENAME, 
			nodeInstID = ProdPickingRefOrderItem.NODENAME, showOnUI = false)
	protected String refProdOrderUUID;
	
	@BSearchFieldConfig(fieldName = "itemProcessType", nodeName = ProdPickingRefMaterialItem.NODENAME, seName = ProdPickingRefMaterialItem.SENAME, 
			nodeInstID = ProdPickingRefMaterialItem.NODENAME, showOnUI = false)
	protected int itemProcessType;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public int getNextDocType() {
		return nextDocType;
	}

	public void setNextDocType(int nextDocType) {
		this.nextDocType = nextDocType;
	}

	public String getNextDocMatItemUUID() {
		return nextDocMatItemUUID;
	}

	public void setNextDocMatItemUUID(String nextDocMatItemUUID) {
		this.nextDocMatItemUUID = nextDocMatItemUUID;
	}

	public int getPrevDocType() {
		return prevDocType;
	}

	public void setPrevDocType(int prevDocType) {
		this.prevDocType = prevDocType;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getItemMaterialSKUName() {
		return itemMaterialSKUName;
	}

	public void setItemMaterialSKUName(String itemMaterialSKUName) {
		this.itemMaterialSKUName = itemMaterialSKUName;
	}

	public String getItemMaterialSKUId() {
		return itemMaterialSKUId;
	}

	public void setItemMaterialSKUId(String itemMaterialSKUId) {
		this.itemMaterialSKUId = itemMaterialSKUId;
	}

	public String getRefPickingOrderId() {
		return refPickingOrderId;
	}

	public void setRefPickingOrderId(String refPickingOrderId) {
		this.refPickingOrderId = refPickingOrderId;
	}

	public String getRefPickingOrderName() {
		return refPickingOrderName;
	}

	public void setRefPickingOrderName(String refPickingOrderName) {
		this.refPickingOrderName = refPickingOrderName;
	}

	public String getRefOrderName() {
		return refOrderName;
	}

	public void setRefOrderName(String refOrderName) {
		this.refOrderName = refOrderName;
	}

	public String getRefOrderId() {
		return refOrderId;
	}

	public void setRefOrderId(String refOrderId) {
		this.refOrderId = refOrderId;
	}

	public String getRefProdOrderUUID() {
		return refProdOrderUUID;
	}

	public void setRefProdOrderUUID(String refProdOrderUUID) {
		this.refProdOrderUUID = refProdOrderUUID;
	}

	public int getItemProcessType() {
		return itemProcessType;
	}

	public void setItemProcessType(int itemProcessType) {
		this.itemProcessType = itemProcessType;
	}

}
