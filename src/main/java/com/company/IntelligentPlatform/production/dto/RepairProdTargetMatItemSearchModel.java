package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import com.company.IntelligentPlatform.production.model.RepairProdTargetMatItem;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;

import java.util.Date;

@Component
public class RepairProdTargetMatItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "prevDocType", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected int prevDocType;

	@BSearchFieldConfig(fieldName = "status", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected int status;

	@BSearchFieldConfig(fieldName = "refSerialId", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected String refSerialId;

	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = RepairProdOrder.NODENAME, seName = RepairProdOrder.SENAME, nodeInstID = RepairProdOrder.SENAME)
	protected String orderId;

	@BSearchFieldConfig(fieldName = "name", nodeName = RepairProdOrder.NODENAME, seName = RepairProdOrder.SENAME, nodeInstID = RepairProdOrder.SENAME)
	protected String orderName;

	@BSearchFieldConfig(fieldName = "prevDocMatItemUUID", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected String prevDocMatItemUUID;

	@BSearchFieldConfig(fieldName = "nextDocType", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected int nextDocType;

	@BSearchFieldConfig(fieldName = "prevDocMatItemUUID", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected String nextDocMatItemUUID;

	@BSearchFieldConfig(fieldName = "reservedDocType", nodeName = RepairProdTargetMatItem.NODENAME, seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.NODENAME)
	protected int reservedDocType;

	// compound search field
	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = RepairProdTargetMatItem.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH,
			seName = PurchaseContract.SENAME, nodeInstID = RepairProdTargetMatItem.SENAME)
	protected Date createdTimeHigh;

	protected String createdTimeStrHigh;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = RepairProdTargetMatItem.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = RepairProdTargetMatItem.SENAME, nodeInstID = RepairProdTargetMatItem.SENAME)
	protected Date createdTimeLow;

	protected String createdTimeStrLow;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(final String refSerialId) {
		this.refSerialId = refSerialId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(final String orderName) {
		this.orderName = orderName;
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

	protected int getNextDocType() {
		return nextDocType;
	}

	public void setNextDocType(final int nextDocType) {
		this.nextDocType = nextDocType;
	}

	protected String getNextDocMatItemUUID() {
		return nextDocMatItemUUID;
	}

	public void setNextDocMatItemUUID(final String nextDocMatItemUUID) {
		this.nextDocMatItemUUID = nextDocMatItemUUID;
	}

	public void setReservedDocType(int reservedDocType) {
		this.reservedDocType = reservedDocType;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	@Override
	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	@Override
	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getPrevDocMatItemUUID() {
		return prevDocMatItemUUID;
	}

	public void setPrevDocMatItemUUID(String prevDocMatItemUUID) {
		this.prevDocMatItemUUID = prevDocMatItemUUID;
	}

	protected Date getCreatedTimeHigh() {
		return createdTimeHigh;
	}

	public void setCreatedTimeHigh(final Date createdTimeHigh) {
		this.createdTimeHigh = createdTimeHigh;
	}

	public String getCreatedTimeStrHigh() {
		return createdTimeStrHigh;
	}

	public void setCreatedTimeStrHigh(final String createdTimeStrHigh) {
		this.createdTimeStrHigh = createdTimeStrHigh;
	}

	public Date getCreatedTimeLow() {
		return createdTimeLow;
	}

	public void setCreatedTimeLow(final Date createdTimeLow) {
		this.createdTimeLow = createdTimeLow;
	}
   public String getCreatedTimeStrLow() {
		return createdTimeStrLow;
	}

   public void setCreatedTimeStrLow(final String createdTimeStrLow) {
		this.createdTimeStrLow = createdTimeStrLow;
	}
}
