package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * ProductionPlan UI Model
 ** 
 * @author
 * @date Sun Jan 03 23:41:56 CST 2016
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProductionPlanSearchModel extends SEUIComModel {
	
	public static final String NODE_OutMaterialStockKeepUnit = "outMaterialStockKeepUnit";
	
	public static final String NODE_ItemMaterialStockKeepUnit = "itemMaterialStockKeepUnit";

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME,
			nodeInstID = ProductionPlan.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	protected String id;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "status", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProductionPlanSearch_status", valueFieldName = "")
	protected String status;
	
	@BSearchFieldConfig(fieldName = "priorityCode", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	protected int priorityCode;
	
	@BSearchFieldConfig(fieldName = "productionBatchNumber", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	protected int productionBatchNumber;
	
	@BSearchFieldConfig(fieldName = "category", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProductionPlanSearch_category", valueFieldName = "")
	protected int category;

	@BSearchFieldConfig(fieldName = "planStartTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date planStartTimeLow;

	@BSearchFieldConfig(fieldName = "planStartTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date planStartTimeHigh;

	@BSearchFieldConfig(fieldName = "actualStartTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date actualStartTimeLow;

	@BSearchFieldConfig(fieldName = "actualStartTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date actualStartTimeHigh;	

	@BSearchFieldConfig(fieldName = "planEndTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date planEndTimeLow;

	@BSearchFieldConfig(fieldName = "planEndTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date planEndTimeHigh;

	@BSearchFieldConfig(fieldName = "actualEndTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date actualEndTimeLow;

	@BSearchFieldConfig(fieldName = "actualEndTime", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date actualEndTimeHigh;	
	
	@BSearchFieldConfig(fieldName = "refMainProdOrderUUID", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, 
			nodeInstID = ProductionPlan.SENAME)
	protected String refMainProdOrderUUID;

	protected String planStartTimeLowStr;

	protected String planStartTimeHighStr;

	protected String actualStartTimeLowStr;

	protected String actualStartTimeHighStr;
	
	protected String planEndTimeLowStr;

	protected String planEndTimeHighStr;

	protected String actualEndTimeLowStr;

	protected String actualEndTimeHighStr;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = NODE_OutMaterialStockKeepUnit)
	protected String refMaterialSKUId;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = NODE_OutMaterialStockKeepUnit)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "refBillOfMaterialUUID", nodeName = ProductionPlan.NODENAME, seName = ProductionPlan.SENAME, nodeInstID = ProductionPlan.SENAME)
	protected String refBillOfMaterialUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected String refBillOfMaterialId;

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Date getPlanStartTimeLow() {
		return planStartTimeLow;
	}

	public void setPlanStartTimeLow(Date planStartTimeLow) {
		this.planStartTimeLow = planStartTimeLow;
	}

	public Date getPlanStartTimeHigh() {
		return planStartTimeHigh;
	}

	public void setPlanStartTimeHigh(Date planStartTimeHigh) {
		this.planStartTimeHigh = planStartTimeHigh;
	}

	public Date getActualStartTimeLow() {
		return actualStartTimeLow;
	}

	public void setActualStartTimeLow(Date actualStartTimeLow) {
		this.actualStartTimeLow = actualStartTimeLow;
	}

	public Date getActualStartTimeHigh() {
		return actualStartTimeHigh;
	}

	public void setActualStartTimeHigh(Date actualStartTimeHigh) {
		this.actualStartTimeHigh = actualStartTimeHigh;
	}

	public String getRefMainProdOrderUUID() {
		return refMainProdOrderUUID;
	}

	public void setRefMainProdOrderUUID(String refMainProdOrderUUID) {
		this.refMainProdOrderUUID = refMainProdOrderUUID;
	}

	public Date getPlanEndTimeLow() {
		return planEndTimeLow;
	}

	public void setPlanEndTimeLow(Date planEndTimeLow) {
		this.planEndTimeLow = planEndTimeLow;
	}

	public Date getPlanEndTimeHigh() {
		return planEndTimeHigh;
	}

	public void setPlanEndTimeHigh(Date planEndTimeHigh) {
		this.planEndTimeHigh = planEndTimeHigh;
	}

	public Date getActualEndTimeLow() {
		return actualEndTimeLow;
	}

	public void setActualEndTimeLow(Date actualEndTimeLow) {
		this.actualEndTimeLow = actualEndTimeLow;
	}

	public Date getActualEndTimeHigh() {
		return actualEndTimeHigh;
	}

	public void setActualEndTimeHigh(Date actualEndTimeHigh) {
		this.actualEndTimeHigh = actualEndTimeHigh;
	}

	public String getPlanStartTimeLowStr() {
		return planStartTimeLowStr;
	}

	public void setPlanStartTimeLowStr(String planStartTimeLowStr) {
		this.planStartTimeLowStr = planStartTimeLowStr;
	}

	public String getPlanStartTimeHighStr() {
		return planStartTimeHighStr;
	}

	public void setPlanStartTimeHighStr(String planStartTimeHighStr) {
		this.planStartTimeHighStr = planStartTimeHighStr;
	}

	public String getActualStartTimeLowStr() {
		return actualStartTimeLowStr;
	}

	public void setActualStartTimeLowStr(String actualStartTimeLowStr) {
		this.actualStartTimeLowStr = actualStartTimeLowStr;
	}

	public String getActualStartTimeHighStr() {
		return actualStartTimeHighStr;
	}

	public void setActualStartTimeHighStr(String actualStartTimeHighStr) {
		this.actualStartTimeHighStr = actualStartTimeHighStr;
	}

	public String getPlanEndTimeLowStr() {
		return planEndTimeLowStr;
	}

	public void setPlanEndTimeLowStr(String planEndTimeLowStr) {
		this.planEndTimeLowStr = planEndTimeLowStr;
	}

	public String getPlanEndTimeHighStr() {
		return planEndTimeHighStr;
	}

	public void setPlanEndTimeHighStr(String planEndTimeHighStr) {
		this.planEndTimeHighStr = planEndTimeHighStr;
	}

	public String getActualEndTimeLowStr() {
		return actualEndTimeLowStr;
	}

	public void setActualEndTimeLowStr(String actualEndTimeLowStr) {
		this.actualEndTimeLowStr = actualEndTimeLowStr;
	}

	public String getActualEndTimeHighStr() {
		return actualEndTimeHighStr;
	}

	public void setActualEndTimeHighStr(String actualEndTimeHighStr) {
		this.actualEndTimeHighStr = actualEndTimeHighStr;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getRefBillOfMaterialUUID() {
		return refBillOfMaterialUUID;
	}

	public void setRefBillOfMaterialUUID(String refBillOfMaterialUUID) {
		this.refBillOfMaterialUUID = refBillOfMaterialUUID;
	}

	public String getRefBillOfMaterialId() {
		return refBillOfMaterialId;
	}

	public void setRefBillOfMaterialId(String refBillOfMaterialId) {
		this.refBillOfMaterialId = refBillOfMaterialId;
	}

	public int getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public int getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(int productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}
