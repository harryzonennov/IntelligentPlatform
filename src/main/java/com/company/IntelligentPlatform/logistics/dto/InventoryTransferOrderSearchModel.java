package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.service.SearchDocConfigHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.WarehouseSubSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;

/**
 * InventoryTransferOrder UI Model
 ** 
 * @author
 * @date Sat Dec 28 23:34:17 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class InventoryTransferOrderSearchModel extends SEUIComModel {

	public static final String ID_OUTBOUND_WAREHOUSE = "outboundWarehouse";

	public static final String ID_INBOUND_WAREHOUSE = "inboundWarehouse";

	public static final String ID_OUTBOUND_WAREHOUSEAREA = "outboundWarehouseArea";

	public static final String ID_INBOUND_WAREHOUSEAREA = "inboundWarehouseArea";

	@BSearchGroupConfig(groupInstId = InventoryCheckOrder.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = InventoryCheckOrder.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_DOC)
	protected DocFlowNodeSearchModel prevDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_PREV_PROF_DOC)
	protected DocFlowNodeSearchModel prevProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_PROF_DOC)
	protected DocFlowNodeSearchModel nextProfDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_NEXT_DOC)
	protected DocFlowNodeSearchModel nextDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_BY_DOC)
	protected DocFlowNodeSearchModel reservedByDoc;

	@BSearchGroupConfig(groupInstId = SearchDocConfigHelper.NODE_ID_RESERVED_TARGET_DOC)
	protected DocFlowNodeSearchModel reservedTargetDoc;

	@BSearchGroupConfig(groupInstId = InventoryCheckOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = InventoryCheckOrderActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = InventoryCheckOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected DocActionNodeSearchModel deliveryDoneBy;

	@BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	@BSearchGroupConfig(groupInstId = "refInboundWarehouse")
	protected WarehouseSubSearchModel refInboundWarehouse;

	@BSearchGroupConfig(groupInstId = "refOutboundWarehouse")
	protected WarehouseSubSearchModel refOutboundWarehouse;

	@BSearchFieldConfig(fieldName = "refWarehouseUUID", nodeName = InventoryTransferOrder.NODENAME,
			seName = InventoryTransferOrder.SENAME, nodeInstID = InventoryTransferOrder.SENAME)
	protected String refOutboundWarehouseUUID;

	@BSearchFieldConfig(fieldName = "refInboundDeliveryUUID", nodeName = InventoryTransferOrder.NODENAME, 
			seName = InventoryTransferOrder.SENAME, nodeInstID = InventoryTransferOrder.SENAME)
	protected String refInboundDeliveryUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = InboundDelivery.NODENAME, 
			seName = InboundDelivery.SENAME, nodeInstID = InboundDelivery.SENAME)
	protected String refInboundDeliveryId;
	
	@BSearchFieldConfig(fieldName = "refOutboundDeliveryUUID", nodeName = InventoryTransferOrder.NODENAME, seName = InventoryTransferOrder.SENAME, nodeInstID = InventoryTransferOrder.SENAME)
	protected String refOutboundDeliveryUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = OutboundDelivery.NODENAME, seName = OutboundDelivery.SENAME, nodeInstID = OutboundDelivery.SENAME)
	protected String refOutboundDeliveryId;

	public ServiceDocSearchHeaderModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
		this.headerModel = headerModel;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}

	public DocFlowNodeSearchModel getPrevDoc() {
		return prevDoc;
	}

	public void setPrevDoc(DocFlowNodeSearchModel prevDoc) {
		this.prevDoc = prevDoc;
	}

	public DocFlowNodeSearchModel getPrevProfDoc() {
		return prevProfDoc;
	}

	public void setPrevProfDoc(DocFlowNodeSearchModel prevProfDoc) {
		this.prevProfDoc = prevProfDoc;
	}

	public DocFlowNodeSearchModel getNextProfDoc() {
		return nextProfDoc;
	}

	public void setNextProfDoc(DocFlowNodeSearchModel nextProfDoc) {
		this.nextProfDoc = nextProfDoc;
	}

	public DocFlowNodeSearchModel getNextDoc() {
		return nextDoc;
	}

	public void setNextDoc(DocFlowNodeSearchModel nextDoc) {
		this.nextDoc = nextDoc;
	}

	public DocFlowNodeSearchModel getReservedByDoc() {
		return reservedByDoc;
	}

	public void setReservedByDoc(DocFlowNodeSearchModel reservedByDoc) {
		this.reservedByDoc = reservedByDoc;
	}

	public DocActionNodeSearchModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public DocActionNodeSearchModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(DocActionNodeSearchModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public String getRefOutboundWarehouseUUID() {
		return refOutboundWarehouseUUID;
	}

	public void setRefOutboundWarehouseUUID(String refOutboundWarehouseUUID) {
		this.refOutboundWarehouseUUID = refOutboundWarehouseUUID;
	}

	public String getRefInboundDeliveryUUID() {
		return refInboundDeliveryUUID;
	}

	public void setRefInboundDeliveryUUID(String refInboundDeliveryUUID) {
		this.refInboundDeliveryUUID = refInboundDeliveryUUID;
	}

	public String getRefInboundDeliveryId() {
		return refInboundDeliveryId;
	}

	public void setRefInboundDeliveryId(String refInboundDeliveryId) {
		this.refInboundDeliveryId = refInboundDeliveryId;
	}

	public String getRefOutboundDeliveryUUID() {
		return refOutboundDeliveryUUID;
	}

	public void setRefOutboundDeliveryUUID(String refOutboundDeliveryUUID) {
		this.refOutboundDeliveryUUID = refOutboundDeliveryUUID;
	}

	public String getRefOutboundDeliveryId() {
		return refOutboundDeliveryId;
	}

	public void setRefOutboundDeliveryId(String refOutboundDeliveryId) {
		this.refOutboundDeliveryId = refOutboundDeliveryId;
	}

	public WarehouseSubSearchModel getRefInboundWarehouse() {
		return refInboundWarehouse;
	}

	public void setRefInboundWarehouse(WarehouseSubSearchModel refInboundWarehouse) {
		this.refInboundWarehouse = refInboundWarehouse;
	}

	public WarehouseSubSearchModel getRefOutboundWarehouse() {
		return refOutboundWarehouse;
	}

	public void setRefOutboundWarehouse(WarehouseSubSearchModel refOutboundWarehouse) {
		this.refOutboundWarehouse = refOutboundWarehouse;
	}

	public DocActionNodeSearchModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public DocFlowNodeSearchModel getReservedTargetDoc() {
		return reservedTargetDoc;
	}

	public void setReservedTargetDoc(DocFlowNodeSearchModel reservedTargetDoc) {
		this.reservedTargetDoc = reservedTargetDoc;
	}
}
