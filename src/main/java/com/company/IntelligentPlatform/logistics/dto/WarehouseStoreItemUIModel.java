package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.model.ServiceConvertMeta;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * Inbound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class WarehouseStoreItemUIModel extends DocMatItemUIModel {

	public static class ConvertMeta extends ServiceConvertMeta {

		private boolean listFlag;

		private int batchMode;

		private String reservedMatItemUUID;

		private int reservedDocType;

		public ConvertMeta(){

		}

		public ConvertMeta(boolean listFlag, int batchMode) {
			this.listFlag = listFlag;
			this.batchMode = batchMode;
		}

		public boolean isListFlag() {
			return listFlag;
		}

		public void setListFlag(boolean listFlag) {
			this.listFlag = listFlag;
		}

		public int getBatchMode() {
			return batchMode;
		}

		public void setBatchMode(int batchMode) {
			this.batchMode = batchMode;
		}

		public String getReservedMatItemUUID() {
			return reservedMatItemUUID;
		}

		public void setReservedMatItemUUID(String reservedMatItemUUID) {
			this.reservedMatItemUUID = reservedMatItemUUID;
		}

		public int getReservedDocType() {
			return reservedDocType;
		}

		public void setReservedDocType(int reservedDocType) {
			this.reservedDocType = reservedDocType;
		}
	}

	protected String storeBatchId;

	protected double volume;

	protected double weight;

	protected double availableAmount;

	protected String availableAmountLabel;
	
	protected double initAmount;

	protected double declaredValue;
	
	protected String refMaterialSKUBarcode;

	protected String productionDate;

	protected int availableStatus;

	protected String availableStatusValue;

	@ISEDropDownResourceMapping(resouceMapping = "WarehouseStoreItem_reservedStatus", valueFieldName = "statusValue")
	protected int reservedStatus;

	protected String reservedStatusValue;

	protected String refInitUnitUUID;

	protected String refInitUnitName;

	protected double inboundFee;

	protected double inboundItemPrice;

	protected double inboundUnitPrice;
	
	protected double inboundItemPriceNoTax;

	protected double inboundUnitPriceNoTax;

	protected double inboundFeeNoTax;

	protected String refUnitNodeInstId;

	protected String refWarehouseId;
	
	protected String refWarehouseName;
	
	protected String inboundDeliveryId;

	protected String outboundDeliveryId;

	protected String refWarehouseUUID;

	protected String refWarehouseAreaUUID;

	protected String refWarehouseAreaId;

	protected String refShelfNumberId;

	protected String productionPlace;

	protected double outboundFee;

	protected double outboundFeeNoTax;

	protected double outboundItemPrice;

	protected double outboundUnitPrice;

	protected double outboundItemPriceNoTax;

	protected double outboundUnitPriceNoTax;

	protected int storeDay;

	protected String recordStoreDate;

	protected String lastUpdateTime;

	protected int errorType;

	protected String errorTypeValue;

	protected String errorTypeTitle;

	protected double gapAmount;

	protected String gapRefUnitName;

	protected String outboundDate;

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getAvailableAmountLabel() {
		return availableAmountLabel;
	}

	public void setAvailableAmountLabel(String availableAmountLabel) {
		this.availableAmountLabel = availableAmountLabel;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getRefMaterialSKUBarcode() {
		return refMaterialSKUBarcode;
	}

	public void setRefMaterialSKUBarcode(String refMaterialSKUBarcode) {
		this.refMaterialSKUBarcode = refMaterialSKUBarcode;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getRefUnitNodeInstId() {
		return refUnitNodeInstId;
	}

	public void setRefUnitNodeInstId(String refUnitNodeInstId) {
		this.refUnitNodeInstId = refUnitNodeInstId;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getRefWarehouseAreaId() {
		return refWarehouseAreaId;
	}

	public void setRefWarehouseAreaId(String refWarehouseAreaId) {
		this.refWarehouseAreaId = refWarehouseAreaId;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefShelfNumberId() {
		return refShelfNumberId;
	}

	public void setRefShelfNumberId(String refShelfNumberId) {
		this.refShelfNumberId = refShelfNumberId;
	}

	public String getProductionPlace() {
		return productionPlace;
	}

	public void setProductionPlace(String productionPlace) {
		this.productionPlace = productionPlace;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getInboundDeliveryId() {
		return inboundDeliveryId;
	}

	public void setInboundDeliveryId(String inboundDeliveryId) {
		this.inboundDeliveryId = inboundDeliveryId;
	}

	public String getOutboundDeliveryId() {
		return outboundDeliveryId;
	}

	public void setOutboundDeliveryId(String outboundDeliveryId) {
		this.outboundDeliveryId = outboundDeliveryId;
	}

	public double getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(double initAmount) {
		this.initAmount = initAmount;
	}

	public String getRefInitUnitUUID() {
		return refInitUnitUUID;
	}

	public void setRefInitUnitUUID(String refInitUnitUUID) {
		this.refInitUnitUUID = refInitUnitUUID;
	}

	public String getRefInitUnitName() {
		return refInitUnitName;
	}

	public void setRefInitUnitName(String refInitUnitName) {
		this.refInitUnitName = refInitUnitName;
	}

	public int getStoreDay() {
		return storeDay;
	}

	public void setStoreDay(int storeDay) {
		this.storeDay = storeDay;
	}

	public String getRecordStoreDate() {
		return recordStoreDate;
	}

	public void setRecordStoreDate(String recordStoreDate) {
		this.recordStoreDate = recordStoreDate;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getErrorTypeValue() {
		return errorTypeValue;
	}

	public void setErrorTypeValue(String errorTypeValue) {
		this.errorTypeValue = errorTypeValue;
	}

	public double getGapAmount() {
		return gapAmount;
	}

	public void setGapAmount(double gapAmount) {
		this.gapAmount = gapAmount;
	}

	public String getGapRefUnitName() {
		return gapRefUnitName;
	}

	public void setGapRefUnitName(String gapRefUnitName) {
		this.gapRefUnitName = gapRefUnitName;
	}

	public String getErrorTypeTitle() {
		return errorTypeTitle;
	}

	public void setErrorTypeTitle(String errorTypeTitle) {
		this.errorTypeTitle = errorTypeTitle;
	}

	public double getInboundFee() {
		return inboundFee;
	}

	public void setInboundFee(double inboundFee) {
		this.inboundFee = inboundFee;
	}

	public double getOutboundFee() {
		return outboundFee;
	}

	public void setOutboundFee(double outboundFee) {
		this.outboundFee = outboundFee;
	}

	public double getInboundFeeNoTax() {
		return inboundFeeNoTax;
	}

	public void setInboundFeeNoTax(double inboundFeeNoTax) {
		this.inboundFeeNoTax = inboundFeeNoTax;
	}

	public double getOutboundFeeNoTax() {
		return outboundFeeNoTax;
	}

	public void setOutboundFeeNoTax(double outboundFeeNoTax) {
		this.outboundFeeNoTax = outboundFeeNoTax;
	}

	public double getInboundItemPrice() {
		return inboundItemPrice;
	}

	public void setInboundItemPrice(double inboundItemPrice) {
		this.inboundItemPrice = inboundItemPrice;
	}

	public double getInboundUnitPrice() {
		return inboundUnitPrice;
	}

	public void setInboundUnitPrice(double inboundUnitPrice) {
		this.inboundUnitPrice = inboundUnitPrice;
	}

	public double getInboundItemPriceNoTax() {
		return inboundItemPriceNoTax;
	}

	public void setInboundItemPriceNoTax(double inboundItemPriceNoTax) {
		this.inboundItemPriceNoTax = inboundItemPriceNoTax;
	}

	public double getInboundUnitPriceNoTax() {
		return inboundUnitPriceNoTax;
	}

	public void setInboundUnitPriceNoTax(double inboundUnitPriceNoTax) {
		this.inboundUnitPriceNoTax = inboundUnitPriceNoTax;
	}

	public double getOutboundItemPrice() {
		return outboundItemPrice;
	}

	public void setOutboundItemPrice(double outboundItemPrice) {
		this.outboundItemPrice = outboundItemPrice;
	}

	public double getOutboundUnitPrice() {
		return outboundUnitPrice;
	}

	public void setOutboundUnitPrice(double outboundUnitPrice) {
		this.outboundUnitPrice = outboundUnitPrice;
	}

	public double getOutboundItemPriceNoTax() {
		return outboundItemPriceNoTax;
	}

	public String getStoreBatchId() {
		return storeBatchId;
	}

	public void setStoreBatchId(String storeBatchId) {
		this.storeBatchId = storeBatchId;
	}

	public int getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(int availableStatus) {
		this.availableStatus = availableStatus;
	}

	public String getAvailableStatusValue() {
		return availableStatusValue;
	}

	public void setAvailableStatusValue(String availableStatusValue) {
		this.availableStatusValue = availableStatusValue;
	}

	public int getReservedStatus() {
		return reservedStatus;
	}

	public void setReservedStatus(int reservedStatus) {
		this.reservedStatus = reservedStatus;
	}

	public String getReservedStatusValue() {
		return reservedStatusValue;
	}

	public void setReservedStatusValue(String reservedStatusValue) {
		this.reservedStatusValue = reservedStatusValue;
	}

	public void setOutboundItemPriceNoTax(double outboundItemPriceNoTax) {
		this.outboundItemPriceNoTax = outboundItemPriceNoTax;
	}

	public double getOutboundUnitPriceNoTax() {
		return outboundUnitPriceNoTax;
	}

	public void setOutboundUnitPriceNoTax(double outboundUnitPriceNoTax) {
		this.outboundUnitPriceNoTax = outboundUnitPriceNoTax;
	}

	public String getOutboundDate() {
		return outboundDate;
	}

	public void setOutboundDate(String outboundDate) {
		this.outboundDate = outboundDate;
	}

}
