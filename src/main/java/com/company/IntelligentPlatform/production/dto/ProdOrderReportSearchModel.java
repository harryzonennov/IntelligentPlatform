package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdOrderReport;
import com.company.IntelligentPlatform.production.model.ProdOrderReportItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonUser;


@Component
public class ProdOrderReportSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReport.NODENAME, showOnUI = false)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReport.NODENAME, showOnUI = false)
	protected String name;

	@BSearchFieldConfig(fieldName = "reportStatus", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReport.NODENAME, showOnUI = false)
	protected int reportStatus;
	
	@BSearchFieldConfig(fieldName = "reportTime", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, 
			nodeInstID = ProdOrderReport.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date reportTimeHigh;
	
	protected String reportTimeStrHigh;
	
	@BSearchFieldConfig(fieldName = "reportTime", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, 
			nodeInstID = ProdOrderReport.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date reportTimeLow;
	
	protected String reportTimeStrLow;	


	@BSearchFieldConfig(fieldName = "reportedBy", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReport.NODENAME, showOnUI = false)
	protected String reportedBy;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ProdOrderReport.NODENAME, seName = ProdOrderReport.SENAME, nodeInstID = ProdOrderReport.NODENAME, showOnUI = false)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME, showOnUI = false)
	protected String reportByName;

	@BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME, showOnUI = false)
	protected String reportById;
	
	@BSearchFieldConfig(fieldName = "productionBatchNumber", nodeName = ProdOrderReportItem.NODENAME, seName = ProdOrderReportItem.SENAME, 
			nodeInstID = ProdOrderReportItem.NODENAME)
	protected String productionBatchNumber;	
	
	@BSearchFieldConfig(fieldName = "refSerialId", nodeName = ProdOrderReportItem.NODENAME, seName = ProdOrderReportItem.SENAME, 
			nodeInstID = ProdOrderReportItem.NODENAME)
	protected String refSerialId;	

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUId;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;
	
	@BSearchFieldConfig(fieldName = "packageStandard", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, 
			nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String packageStandard;

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

	public int getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Date getReportTimeHigh() {
		return reportTimeHigh;
	}

	public void setReportTimeHigh(Date reportTimeHigh) {
		this.reportTimeHigh = reportTimeHigh;
	}

	public String getReportTimeStrHigh() {
		return reportTimeStrHigh;
	}

	public void setReportTimeStrHigh(String reportTimeStrHigh) {
		this.reportTimeStrHigh = reportTimeStrHigh;
	}

	public Date getReportTimeLow() {
		return reportTimeLow;
	}

	public void setReportTimeLow(Date reportTimeLow) {
		this.reportTimeLow = reportTimeLow;
	}

	public String getReportTimeStrLow() {
		return reportTimeStrLow;
	}

	public void setReportTimeStrLow(String reportTimeStrLow) {
		this.reportTimeStrLow = reportTimeStrLow;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getReportByName() {
		return reportByName;
	}

	public void setReportByName(String reportByName) {
		this.reportByName = reportByName;
	}

	public String getReportById() {
		return reportById;
	}

	public void setReportById(String reportById) {
		this.reportById = reportById;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
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

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	
}
