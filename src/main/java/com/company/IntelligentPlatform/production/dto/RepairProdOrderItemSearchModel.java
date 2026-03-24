package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import com.company.IntelligentPlatform.production.model.RepairProdOrderItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;

/**
 * RepairProdOrderItem UI Model
 ** 
 * @author
 * @date Sun Jan 03 17:15:12 CST 2016
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class RepairProdOrderItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "index", nodeName = RepairProdOrderItem.NODENAME, seName = RepairProdOrderItem.SENAME, nodeInstID = RepairProdOrderItem.NODENAME, showOnUI = false)
	protected int index;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = RepairProdOrderItem.NODENAME, seName = RepairProdOrderItem.SENAME
			, nodeInstID = RepairProdOrderItem.NODENAME, showOnUI = false)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "nodeName", nodeName = RepairProdOrderItem.NODENAME, seName = RepairProdOrderItem.SENAME
			, nodeInstID = RepairProdOrderItem.NODENAME, showOnUI = false)
	protected String nodeName;

	@BSearchFieldConfig(fieldName = "parentNodeUUID", nodeName = RepairProdOrderItem.NODENAME, seName = RepairProdOrderItem.SENAME, nodeInstID = RepairProdOrderItem.NODENAME, showOnUI = false)
	protected String parentNodeUUID;

	@BSearchFieldConfig(fieldName = "itemStatus", nodeName = RepairProdOrderItem.NODENAME, seName = RepairProdOrderItem.SENAME, nodeInstID = RepairProdOrderItem.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "RepairProdOrderItemSearch_itemStatus", valueFieldName = "")
	protected int itemStatus;

	@BSearchFieldConfig(fieldName = "id", nodeName = RepairProdOrder.NODENAME, seName = RepairProdOrder.SENAME
			, nodeInstID = RepairProdOrder.SENAME)
	protected String orderId;

	@BSearchFieldConfig(fieldName = "name", nodeName = RepairProdOrder.NODENAME, seName = RepairProdOrder.SENAME
			, nodeInstID = RepairProdOrder.SENAME)
	protected String orderName;

	@BSearchFieldConfig(fieldName = "status", nodeName = RepairProdOrder.NODENAME, seName = RepairProdOrder.SENAME
			, nodeInstID = RepairProdOrder.SENAME)
	protected int orderStatus;

	// compound search field
	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;


	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	

}
