package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

/**
 * Inbound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class WarehouseStoreItemLogUIModel extends SEUIComModel {

	protected String warehouseName;

	protected String warehouseId;

	protected int documentType;
	
	protected String documentTypeValue;

	protected String documentUUID;
	
	protected String documentId;

	protected String documentName;
	
	protected String refUnitUUID;
	
	protected String refUnitName;

	protected double updatedVolume;

	protected double updatedWeight;

	protected double updatedAmount;
	
	protected String updatedRefUnitUUID;
	
	protected String updatedRefUnitName;
	
	protected double changeAmount;

	protected String changeRefUnitUUID;
	
	protected String changeRefUnitName;
	
	protected String changeAmountLabel;

	protected double declaredValue;

	protected double updatedDeclaredValue;

	protected String refMaterialSKUUUID;

	protected String refMaterialSKUName;

	protected String refMaterialSKUId;
	
	protected String amountLabel;
	
	protected String updatedAmountLabel;
	
	protected String refStoreItemUUID;
	
	protected String refUUID;
	
	protected double volume;

	protected String refNodeName;

	protected double amount;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public String getDocumentId() {
		return documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}

	public double getUpdatedVolume() {
		return updatedVolume;
	}

	public void setUpdatedVolume(double updatedVolume) {
		this.updatedVolume = updatedVolume;
	}

	public double getUpdatedWeight() {
		return updatedWeight;
	}

	public void setUpdatedWeight(double updatedWeight) {
		this.updatedWeight = updatedWeight;
	}

	public double getUpdatedAmount() {
		return updatedAmount;
	}

	public void setUpdatedAmount(double updatedAmount) {
		this.updatedAmount = updatedAmount;
	}

	public String getUpdatedRefUnitUUID() {
		return updatedRefUnitUUID;
	}

	public void setUpdatedRefUnitUUID(String updatedRefUnitUUID) {
		this.updatedRefUnitUUID = updatedRefUnitUUID;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public double getUpdatedDeclaredValue() {
		return updatedDeclaredValue;
	}

	public void setUpdatedDeclaredValue(double updatedDeclaredValue) {
		this.updatedDeclaredValue = updatedDeclaredValue;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public String getUpdatedAmountLabel() {
		return updatedAmountLabel;
	}

	public void setUpdatedAmountLabel(String updatedAmountLabel) {
		this.updatedAmountLabel = updatedAmountLabel;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public String getUpdatedRefUnitName() {
		return updatedRefUnitName;
	}

	public void setUpdatedRefUnitName(String updatedRefUnitName) {
		this.updatedRefUnitName = updatedRefUnitName;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getChangeRefUnitUUID() {
		return changeRefUnitUUID;
	}

	public void setChangeRefUnitUUID(String changeRefUnitUUID) {
		this.changeRefUnitUUID = changeRefUnitUUID;
	}

	public String getChangeRefUnitName() {
		return changeRefUnitName;
	}

	public void setChangeRefUnitName(String changeRefUnitName) {
		this.changeRefUnitName = changeRefUnitName;
	}

	public String getChangeAmountLabel() {
		return changeAmountLabel;
	}

	public void setChangeAmountLabel(String changeAmountLabel) {
		this.changeAmountLabel = changeAmountLabel;
	}
	
}
