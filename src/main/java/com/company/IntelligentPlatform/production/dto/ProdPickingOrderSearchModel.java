package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;


@Component
public class ProdPickingOrderSearchModel extends SEUIComModel {

	public static final String NODEINST_ORDERMATERIALSKU = "orderMaterialSKU";

	public static final String NODEINST_ITEMMATERIALSKU = "itemMaterialSKU";

	@BSearchFieldConfig(fieldName = "category", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected int category;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "id", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "priorityCode", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected int priorityCode;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "status", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected int status;

	@BSearchFieldConfig(fieldName = "processType", nodeName = ProdPickingOrder.NODENAME, seName = ProdPickingOrder.SENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected int processType;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = NODEINST_ORDERMATERIALSKU)
	protected String orderMaterialSKUId;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = NODEINST_ORDERMATERIALSKU)
	protected String orderMaterialSKUName;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = NODEINST_ITEMMATERIALSKU)
	protected String itemMaterialSKUName;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = NODEINST_ITEMMATERIALSKU)
	protected String itemMaterialSKUId;

	@BSearchFieldConfig(fieldName = "id", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, nodeInstID = ProductionOrder.SENAME)
	protected String orderId;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, nodeInstID = ProductionOrder.SENAME)
	protected String orderName;

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPriorityCode() {
		return this.priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProcessType() {
		return this.processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getOrderMaterialSKUId() {
		return orderMaterialSKUId;
	}

	public void setOrderMaterialSKUId(String orderMaterialSKUId) {
		this.orderMaterialSKUId = orderMaterialSKUId;
	}

	public String getOrderMaterialSKUName() {
		return orderMaterialSKUName;
	}

	public void setOrderMaterialSKUName(String orderMaterialSKUName) {
		this.orderMaterialSKUName = orderMaterialSKUName;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

}
