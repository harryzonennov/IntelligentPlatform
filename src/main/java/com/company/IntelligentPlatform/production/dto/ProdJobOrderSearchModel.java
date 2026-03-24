package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdJobOrder;
import com.company.IntelligentPlatform.production.model.ProdProcess;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ProdJobOrder UI Model
 **
 * @author
 * @date Mon Apr 11 15:48:35 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProdJobOrderSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = ProdJobOrder.NODENAME, seName = ProdJobOrder.SENAME, nodeInstID = ProdJobOrder.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "refProductionOrderUUID", nodeName = ProdJobOrder.NODENAME, seName = ProdJobOrder.SENAME, nodeInstID = ProdJobOrder.SENAME)
	protected String refProductionOrderUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = ProductionOrder.NODENAME, seName = ProductionOrder.SENAME, nodeInstID = ProductionOrder.SENAME)
	protected String refProductionOrderID;

	@BSearchFieldConfig(fieldName = "refMainMaterialID", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMainMaterialID;

	@BSearchFieldConfig(fieldName = "refMainMaterialName", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMainMaterialName;

	@BSearchFieldConfig(fieldName = "processID", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	protected String processID;

	@BSearchFieldConfig(fieldName = "processName", nodeName = ProdProcess.NODENAME, seName = ProdProcess.SENAME, nodeInstID = ProdProcess.SENAME)
	protected String processName;

	@BSearchFieldConfig(fieldName = "refWorkCenterID", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected String refWorkCenterID;

	@BSearchFieldConfig(fieldName = "refWorkCenterName", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected String refWorkCenterName;

	@BSearchFieldConfig(fieldName = "status", nodeName = ProdJobOrder.NODENAME, seName = ProdJobOrder.SENAME, nodeInstID = ProdJobOrder.SENAME)
	protected int status;

	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefProductionOrderUUID() {
		return refProductionOrderUUID;
	}

	public void setRefProductionOrderUUID(String refProductionOrderUUID) {
		this.refProductionOrderUUID = refProductionOrderUUID;
	}

	public String getRefProductionOrderID() {
		return refProductionOrderID;
	}

	public void setRefProductionOrderID(String refProductionOrderID) {
		this.refProductionOrderID = refProductionOrderID;
	}

	public String getRefMainMaterialID() {
		return refMainMaterialID;
	}

	public void setRefMainMaterialID(String refMainMaterialID) {
		this.refMainMaterialID = refMainMaterialID;
	}

	public String getRefMainMaterialName() {
		return refMainMaterialName;
	}

	public void setRefMainMaterialName(String refMainMaterialName) {
		this.refMainMaterialName = refMainMaterialName;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getRefWorkCenterID() {
		return refWorkCenterID;
	}

	public void setRefWorkCenterID(String refWorkCenterID) {
		this.refWorkCenterID = refWorkCenterID;
	}

	public String getRefWorkCenterName() {
		return refWorkCenterName;
	}

	public void setRefWorkCenterName(String refWorkCenterName) {
		this.refWorkCenterName = refWorkCenterName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
