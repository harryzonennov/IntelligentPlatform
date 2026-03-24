package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class ProdPickingRefMaterialItemUIModel extends DocMatItemUIModel {

	@ISEUIModelMapping(fieldName = "refInboundItemUUID", seName = ProdPickingRefMaterialItem.SENAME, nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstID = ProdPickingRefMaterialItem.NODENAME)
	protected String refInboundItemUUID;
	
    protected String refInboundDeliveryId;
	
	protected int refInboundDeliveryStatus;
	
	protected String refInboundDeliveryStatusValue;
	
	protected String refOutboundDeliveryId;
	
	protected int refOutboundDeliveryStatus;
	
	protected String refOutboundDeliveryStatusValue;
    
    protected String refOutboundItemUUID;

	protected String refUUID;
	
	@ISEUIModelMapping(fieldName = "refNextOrderUUID", seName = ProdPickingRefMaterialItem.SENAME, nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstID = ProdPickingRefMaterialItem.NODENAME)
	protected String refNextOrderUUID;

	@ISEDropDownResourceMapping(resouceMapping = "ProdPickingOrder_status", valueFieldName = "null")
	protected int itemStatus;
	
	@ISEUIModelMapping(fieldName = "refPrevOrderUUID", seName = ProdPickingRefMaterialItem.SENAME, nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstID = ProdPickingRefMaterialItem.NODENAME)
	protected String refPrevOrderUUID;	
	
	protected String refPickingOrderId;
	
	protected String refPickingOrderName;
	
	protected String refOrderName;

	protected String refOrderId;
	
	protected String refProdOrderUUID;
	
	protected String refProdOrderItemUUID;
	
	protected int refNextOrderStatus;
	
	protected String refNextOrderStatusValue;
	
	protected int refPrevOrderStatus;
	
	protected String refPrevOrderStatusValue;
	
	@ISEUIModelMapping(fieldName = "itemProcessType", seName = ProdPickingRefMaterialItem.SENAME, nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstID = ProdPickingRefMaterialItem.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProdPickingOrder_processType", valueFieldName = "null")
	protected int itemProcessType;
	
	protected String itemProcessTypeValue;
	
	protected double itemPriceNoTax;
	
	protected double unitPriceNoTax;
	
	protected double taxRate;
	
	protected String refWarehouseUUID;
	
	protected String refBillOfMaterialUUID;

	protected String createdTime;
	
	protected int documentType;

	protected double inStockAmount;

	protected double toPickAmount;

	protected double inProcessAmount;

	protected double availableAmount;

	protected double pickedAmount;

	protected double suppliedAmount;

	protected String inStockAmountLabel;

	protected String toPickAmountLabel;

	protected String inProcessAmountLabel;

	protected String availableAmountLabel;

	protected String pickedAmountLabel;

	protected String suppliedAmountLabel;

	public String getRefInboundItemUUID() {
		return this.refInboundItemUUID;
	}

	public void setRefInboundItemUUID(String refInboundItemUUID) {
		this.refInboundItemUUID = refInboundItemUUID;
	}

	public int getRefNextOrderType() {
		return this.nextDocType;
	}

	public void setRefNextOrderType(int nextDocType) {
		this.nextDocType = nextDocType;
	}

	public String getRefNextOrderTypeValue() {
		return nextDocTypeValue;
	}

	public void setRefNextOrderTypeValue(String nextDocTypeValue) {
		this.nextDocTypeValue = nextDocTypeValue;
	}

	public String getNextDocMatItemUUID() {
		return nextDocMatItemUUID;
	}

	public void setNextDocMatItemUUID(String nextDocMatItemUUID) {
		this.nextDocMatItemUUID = nextDocMatItemUUID;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getRefMaterialSKUUUID() {
		return this.refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}
	
	public String getRefNextOrderUUID() {
		return refNextOrderUUID;
	}

	public void setRefNextOrderUUID(String refNextOrderUUID) {
		this.refNextOrderUUID = refNextOrderUUID;
	}

	public String getRefPrevOrderUUID() {
		return refPrevOrderUUID;
	}

	public void setRefPrevOrderUUID(String refPrevOrderUUID) {
		this.refPrevOrderUUID = refPrevOrderUUID;
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

	public String getRefProdOrderItemUUID() {
		return refProdOrderItemUUID;
	}

	public void setRefProdOrderItemUUID(String refProdOrderItemUUID) {
		this.refProdOrderItemUUID = refProdOrderItemUUID;
	}

	public int getRefNextOrderStatus() {
		return refNextOrderStatus;
	}

	public void setRefNextOrderStatus(int refNextOrderStatus) {
		this.refNextOrderStatus = refNextOrderStatus;
	}

	public String getRefNextOrderStatusValue() {
		return refNextOrderStatusValue;
	}

	public void setRefNextOrderStatusValue(String refNextOrderStatusValue) {
		this.refNextOrderStatusValue = refNextOrderStatusValue;
	}

	public int getRefPrevOrderStatus() {
		return refPrevOrderStatus;
	}

	public void setRefPrevOrderStatus(int refPrevOrderStatus) {
		this.refPrevOrderStatus = refPrevOrderStatus;
	}

	public String getRefPrevOrderStatusValue() {
		return refPrevOrderStatusValue;
	}

	public void setRefPrevOrderStatusValue(String refPrevOrderStatusValue) {
		this.refPrevOrderStatusValue = refPrevOrderStatusValue;
	}

	public int getItemProcessType() {
		return itemProcessType;
	}

	public void setItemProcessType(int itemProcessType) {
		this.itemProcessType = itemProcessType;
	}

	public String getItemProcessTypeValue() {
		return itemProcessTypeValue;
	}

	public void setItemProcessTypeValue(String itemProcessTypeValue) {
		this.itemProcessTypeValue = itemProcessTypeValue;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getItemPriceNoTax() {
		return itemPriceNoTax;
	}

	public void setItemPriceNoTax(double itemPriceNoTax) {
		this.itemPriceNoTax = itemPriceNoTax;
	}

	public double getUnitPriceNoTax() {
		return unitPriceNoTax;
	}

	public void setUnitPriceNoTax(double unitPriceNoTax) {
		this.unitPriceNoTax = unitPriceNoTax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefBillOfMaterialUUID() {
		return refBillOfMaterialUUID;
	}

	public void setRefBillOfMaterialUUID(String refBillOfMaterialUUID) {
		this.refBillOfMaterialUUID = refBillOfMaterialUUID;
	}

	public String getRefOutboundDeliveryId() {
		return refOutboundDeliveryId;
	}

	public void setRefOutboundDeliveryId(String refOutboundDeliveryId) {
		this.refOutboundDeliveryId = refOutboundDeliveryId;
	}

	public String getRefOutboundItemUUID() {
		return refOutboundItemUUID;
	}

	public void setRefOutboundItemUUID(String refOutboundItemUUID) {
		this.refOutboundItemUUID = refOutboundItemUUID;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public int getRefOutboundDeliveryStatus() {
		return refOutboundDeliveryStatus;
	}

	public void setRefOutboundDeliveryStatus(int refOutboundDeliveryStatus) {
		this.refOutboundDeliveryStatus = refOutboundDeliveryStatus;
	}

	public String getRefOutboundDeliveryStatusValue() {
		return refOutboundDeliveryStatusValue;
	}

	public void setRefOutboundDeliveryStatusValue(
			String refOutboundDeliveryStatusValue) {
		this.refOutboundDeliveryStatusValue = refOutboundDeliveryStatusValue;
	}

	public String getRefInboundDeliveryId() {
		return refInboundDeliveryId;
	}

	public void setRefInboundDeliveryId(String refInboundDeliveryId) {
		this.refInboundDeliveryId = refInboundDeliveryId;
	}

	public int getRefInboundDeliveryStatus() {
		return refInboundDeliveryStatus;
	}

	public void setRefInboundDeliveryStatus(int refInboundDeliveryStatus) {
		this.refInboundDeliveryStatus = refInboundDeliveryStatus;
	}

	public String getRefInboundDeliveryStatusValue() {
		return refInboundDeliveryStatusValue;
	}

	public void setRefInboundDeliveryStatusValue(
			String refInboundDeliveryStatusValue) {
		this.refInboundDeliveryStatusValue = refInboundDeliveryStatusValue;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public double getToPickAmount() {
		return toPickAmount;
	}

	public void setToPickAmount(double toPickAmount) {
		this.toPickAmount = toPickAmount;
	}

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getPickedAmount() {
		return pickedAmount;
	}

	public void setPickedAmount(double pickedAmount) {
		this.pickedAmount = pickedAmount;
	}

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
	}

	public String getInStockAmountLabel() {
		return inStockAmountLabel;
	}

	public void setInStockAmountLabel(String inStockAmountLabel) {
		this.inStockAmountLabel = inStockAmountLabel;
	}

	public String getToPickAmountLabel() {
		return toPickAmountLabel;
	}

	public void setToPickAmountLabel(String toPickAmountLabel) {
		this.toPickAmountLabel = toPickAmountLabel;
	}

	public String getInProcessAmountLabel() {
		return inProcessAmountLabel;
	}

	public void setInProcessAmountLabel(String inProcessAmountLabel) {
		this.inProcessAmountLabel = inProcessAmountLabel;
	}

	public String getAvailableAmountLabel() {
		return availableAmountLabel;
	}

	public void setAvailableAmountLabel(String availableAmountLabel) {
		this.availableAmountLabel = availableAmountLabel;
	}

	public String getPickedAmountLabel() {
		return pickedAmountLabel;
	}

	public void setPickedAmountLabel(String pickedAmountLabel) {
		this.pickedAmountLabel = pickedAmountLabel;
	}

	public String getSuppliedAmountLabel() {
		return suppliedAmountLabel;
	}

	public void setSuppliedAmountLabel(String suppliedAmountLabel) {
		this.suppliedAmountLabel = suppliedAmountLabel;
	}
}
