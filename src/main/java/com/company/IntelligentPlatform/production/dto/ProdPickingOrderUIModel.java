package com.company.IntelligentPlatform.production.dto;


import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class ProdPickingOrderUIModel extends DocumentUIModel {

	@ISEUIModelMapping(fieldName = "category", seName = ProdPickingOrder.SENAME, nodeName = ProdPickingOrder.NODENAME, nodeInstID = ProdPickingOrder.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProdPickingOrder_category", valueFieldName = "null")
	protected int category;
	
	protected String categoryValue;

	@ISEUIModelMapping(fieldName = "priorityCode", seName = ProdPickingOrder.SENAME, nodeName = ProdPickingOrder.NODENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected int priorityCode;
	
	protected String priorityCodeValue;

	@ISEUIModelMapping(fieldName = "status", seName = ProdPickingOrder.SENAME, nodeName = ProdPickingOrder.NODENAME, nodeInstID = ProdPickingOrder.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProdPickingOrder_status", valueFieldName = "null")
	protected int status;
	
	protected String statusValue;

	@ISEUIModelMapping(fieldName = "processType", seName = ProdPickingOrder.SENAME, nodeName = ProdPickingOrder.NODENAME, nodeInstID = ProdPickingOrder.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProdPickingOrder_processType", valueFieldName = "null")
	protected int processType;
	
	protected String processTypeValue;

	@ISEUIModelMapping(fieldName = "note", seName = ProdPickingOrder.SENAME, nodeName = ProdPickingOrder.NODENAME, nodeInstID = ProdPickingOrder.SENAME)
	protected String note;

	protected String defProdOrderId;

	protected String defProdOrderName;

	protected String defProdOrderUUID;

    protected String approveBy;

    protected String approveById;

	protected String approveByName;
	
	protected String approveDate;
	
	protected int approveType;
	
	protected String approveTypeValue;
	
	protected String processBy;

    protected String processById;

	protected String processByName;
	
	protected String processDate;
	
	protected double grossCost;

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPriorityCode() {
		return this.priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getPriorityCodeValue() {
		return priorityCodeValue;
	}

	public void setPriorityCodeValue(String priorityCodeValue) {
		this.priorityCodeValue = priorityCodeValue;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getProcessTypeValue() {
		return processTypeValue;
	}

	public void setProcessTypeValue(String processTypeValue) {
		this.processTypeValue = processTypeValue;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getApproveById() {
		return approveById;
	}

	public void setApproveById(String approveById) {
		this.approveById = approveById;
	}

	public String getApproveByName() {
		return approveByName;
	}

	public void setApproveByName(String approveByName) {
		this.approveByName = approveByName;
	}

	public int getApproveType() {
		return approveType;
	}

	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}

	public String getProcessBy() {
		return processBy;
	}

	public void setProcessBy(String processBy) {
		this.processBy = processBy;
	}

	public String getProcessById() {
		return processById;
	}

	public void setProcessById(String processById) {
		this.processById = processById;
	}

	public String getProcessByName() {
		return processByName;
	}

	public void setProcessByName(String processByName) {
		this.processByName = processByName;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getApproveTypeValue() {
		return approveTypeValue;
	}

	public void setApproveTypeValue(String approveTypeValue) {
		this.approveTypeValue = approveTypeValue;
	}

	public double getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(double grossCost) {
		this.grossCost = grossCost;
	}

	public String getDefProdOrderId() {
		return defProdOrderId;
	}

	public void setDefProdOrderId(String defProdOrderId) {
		this.defProdOrderId = defProdOrderId;
	}

	public String getDefProdOrderName() {
		return defProdOrderName;
	}

	public void setDefProdOrderName(String defProdOrderName) {
		this.defProdOrderName = defProdOrderName;
	}

	public String getDefProdOrderUUID() {
		return defProdOrderUUID;
	}

	public void setDefProdOrderUUID(String defProdOrderUUID) {
		this.defProdOrderUUID = defProdOrderUUID;
	}
}
