package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.ProcessBOMMaterialItemUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.ProcessBOMMaterialItem;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;

@Service
public class ProcessBOMMaterialItemManager {

	public void convProcessBOMMaterialItemToUI(
			ProcessBOMMaterialItem processBOMMaterialItem,
			ProcessBOMMaterialItemUIModel processBOMMaterialItemUIModel) {
		if (processBOMMaterialItem != null) {
			processBOMMaterialItemUIModel.setUuid(processBOMMaterialItem
					.getUuid());
			processBOMMaterialItemUIModel
					.setParentNodeUUID(processBOMMaterialItem
							.getParentNodeUUID());
			processBOMMaterialItemUIModel.setId(processBOMMaterialItem.getId());
			processBOMMaterialItemUIModel.setNote(processBOMMaterialItem
					.getNote());
			processBOMMaterialItemUIModel.setRefUUID(processBOMMaterialItem
					.getRefUUID());
			processBOMMaterialItemUIModel
					.setRefMaterialSKUUUID(processBOMMaterialItem
							.getRefMaterialSKUUUID());
			processBOMMaterialItemUIModel.setAmount(ServiceEntityDoubleHelper
					.trancateDoubleScale4(processBOMMaterialItem.getAmount()));
			processBOMMaterialItemUIModel.setRefUnitUUID(processBOMMaterialItem
					.getRefUnitUUID());
		}
	}

	public void convUIToProcessBOMMaterialItem(
			ProcessBOMMaterialItem rawEntity,
			ProcessBOMMaterialItemUIModel processBOMMaterialItemUIModel) {
		rawEntity.setUuid(processBOMMaterialItemUIModel.getUuid());
		rawEntity.setParentNodeUUID(processBOMMaterialItemUIModel
				.getParentNodeUUID());
		rawEntity.setId(processBOMMaterialItemUIModel.getId());
		rawEntity.setNote(processBOMMaterialItemUIModel.getNote());
		rawEntity.setRefUUID(processBOMMaterialItemUIModel.getRefUUID());
		rawEntity.setRefMaterialSKUUUID(processBOMMaterialItemUIModel
				.getRefMaterialSKUUUID());
		rawEntity.setAmount(processBOMMaterialItemUIModel.getAmount());
		rawEntity
				.setRefUnitUUID(processBOMMaterialItemUIModel.getRefUnitUUID());
	}

	public void convBillOfMaterialItemToUI(
			BillOfMaterialItem billOfMaterialItem,
			ProcessBOMMaterialItemUIModel processBOMMaterialItemUIModel) {
		if (billOfMaterialItem != null) {
			processBOMMaterialItemUIModel.setRefBOMItemID(billOfMaterialItem
					.getId());
			processBOMMaterialItemUIModel.setUuid(billOfMaterialItem.getUuid());
			processBOMMaterialItemUIModel.setParentNodeUUID(billOfMaterialItem
					.getParentNodeUUID());
			processBOMMaterialItemUIModel.setId(billOfMaterialItem.getId());
			processBOMMaterialItemUIModel.setNote(billOfMaterialItem.getNote());
			processBOMMaterialItemUIModel
					.setRefMaterialSKUUUID(billOfMaterialItem
							.getRefMaterialSKUUUID());
			processBOMMaterialItemUIModel.setAmount(billOfMaterialItem
					.getAmount());
			processBOMMaterialItemUIModel.setRefUnitUUID(billOfMaterialItem
					.getRefUnitUUID());
		}
	}

	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			ProcessBOMMaterialItemUIModel processBOMMaterialItemUIModel) {
		if (materialStockKeepUnit != null) {
			processBOMMaterialItemUIModel
					.setRefMaterialSKUID(materialStockKeepUnit.getId());
			processBOMMaterialItemUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			processBOMMaterialItemUIModel
					.setRefMaterialSKUID(materialStockKeepUnit.getId());
			processBOMMaterialItemUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convUIToMaterialStockKeepUnit(MaterialStockKeepUnit rawEntity,
			ProcessBOMMaterialItemUIModel processBOMMaterialItemUIModel) {
		rawEntity.setId(processBOMMaterialItemUIModel.getRefMaterialSKUID());
		rawEntity
				.setName(processBOMMaterialItemUIModel.getRefMaterialSKUName());
		rawEntity.setId(processBOMMaterialItemUIModel.getRefMaterialSKUID());
		rawEntity
				.setName(processBOMMaterialItemUIModel.getRefMaterialSKUName());
	}

}
