package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

import java.util.Date;

@Component
public class ProductionDocRequestItemSearchModel extends SEUIComModel {

	protected String parentDocUUID;
	
	protected String parentDocId;
	
	protected String parentDocName;

	protected int parentDocStatus;

	protected int parentDocType;

	protected String uuid;

	protected int itemStatus;

	protected Date planExecutionDateLow;

	protected String planExecutionDateStrLow;

	protected Date planExecutionDateHigh;

	protected String planExecutionDateStrHigh;

	protected DocEmbedMaterialSKUSearchModel itemMaterialSKU;

	public String getParentDocUUID() {
		return parentDocUUID;
	}

	public void setParentDocUUID(String parentDocUUID) {
		this.parentDocUUID = parentDocUUID;
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

	public int getParentDocStatus() {
		return parentDocStatus;
	}

	public void setParentDocStatus(int parentDocStatus) {
		this.parentDocStatus = parentDocStatus;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public DocEmbedMaterialSKUSearchModel getItemMaterialSKU() {
		return itemMaterialSKU;
	}

	public void setItemMaterialSKU(DocEmbedMaterialSKUSearchModel itemMaterialSKU) {
		this.itemMaterialSKU = itemMaterialSKU;
	}

	public int getParentDocType() {
		return parentDocType;
	}

	public void setParentDocType(int parentDocType) {
		this.parentDocType = parentDocType;
	}

	public Date getPlanExecutionDateLow() {
		return planExecutionDateLow;
	}

	public void setPlanExecutionDateLow(Date planExecutionDateLow) {
		this.planExecutionDateLow = planExecutionDateLow;
	}

	public String getPlanExecutionDateStrLow() {
		return planExecutionDateStrLow;
	}

	public void setPlanExecutionDateStrLow(String planExecutionDateStrLow) {
		this.planExecutionDateStrLow = planExecutionDateStrLow;
	}

	public Date getPlanExecutionDateHigh() {
		return planExecutionDateHigh;
	}

	public void setPlanExecutionDateHigh(Date planExecutionDateHigh) {
		this.planExecutionDateHigh = planExecutionDateHigh;
	}

	public String getPlanExecutionDateStrHigh() {
		return planExecutionDateStrHigh;
	}

	public void setPlanExecutionDateStrHigh(String planExecutionDateStrHigh) {
		this.planExecutionDateStrHigh = planExecutionDateStrHigh;
	}
}
