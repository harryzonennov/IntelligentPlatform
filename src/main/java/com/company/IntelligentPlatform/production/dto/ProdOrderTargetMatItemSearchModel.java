package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;


@Component
public class ProdOrderTargetMatItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "serviceEntityName", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected String serviceEntityName;

	@BSearchFieldConfig(fieldName = "prevDocType", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected int prevDocType;

	@BSearchFieldConfig(fieldName = "status", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected int status;

	@BSearchFieldConfig(fieldName = "refSerialId", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected String refSerialId;

	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, nodeInstID = ProductionOrder.SENAME)
	protected String parentDocId;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, nodeInstID = ProductionOrder.SENAME)
	protected String parentDocName;

	@BSearchFieldConfig(fieldName = "prevDocMatItemUUID", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected String prevDocMatItemUUID;

	@BSearchFieldConfig(fieldName = "nextDocType", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected int nextDocType;

	@BSearchFieldConfig(fieldName = "prevDocMatItemUUID", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected String nextDocMatItemUUID;

	@BSearchFieldConfig(fieldName = "reservedDocType", nodeName = ProdOrderTargetMatItem.NODENAME, seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.NODENAME)
	protected int reservedDocType;

	// compound search field
	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = ProdOrderTargetMatItem.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH,
			seName = PurchaseContract.SENAME, nodeInstID = ProdOrderTargetMatItem.SENAME)
	protected Date createdTimeHigh;

	protected String createdTimeStrHigh;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = ProdOrderTargetMatItem.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = ProdOrderTargetMatItem.SENAME, nodeInstID = ProdOrderTargetMatItem.SENAME)
	protected Date createdTimeLow;

	protected String createdTimeStrLow;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
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

	public String getParentDocId() {
		return parentDocId;
	}

	public void setParentDocId(String parentDocId) {
		this.parentDocId = parentDocId;
	}

	public String getParentDocName() {
		return parentDocName;
	}

	public void setParentDocName(String parentDocName) {
		this.parentDocName = parentDocName;
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
