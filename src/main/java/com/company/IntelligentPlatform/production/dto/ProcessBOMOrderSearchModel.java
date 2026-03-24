package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProcessBOMOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ProcessBOMOrder UI Model
 **
 * @author
 * @date Sun Apr 03 22:57:39 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProcessBOMOrderSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = ProcessBOMOrder.NODENAME, seName = ProcessBOMOrder.SENAME, nodeInstID = ProcessBOMOrder.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "refMaterialSKUID", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUName", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "refBOMUUID", nodeName = ProcessBOMOrder.NODENAME, seName = ProcessBOMOrder.SENAME, nodeInstID = ProcessBOMOrder.SENAME, showOnUI = false)
	protected String refBOMUUID;

	@BSearchFieldConfig(fieldName = "refBOMOrderID", nodeName = ProcessBOMOrder.NODENAME, seName = ProcessBOMOrder.SENAME, nodeInstID = ProcessBOMOrder.SENAME)
	protected String refBOMOrderID;

	@BSearchFieldConfig(fieldName = "refProcessRouteID", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected String refProcessRouteID;

	@BSearchFieldConfig(fieldName = "routeType", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected int routeType;
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

	public String getRefMaterialSKUID() {
		return refMaterialSKUID;
	}

	public void setRefMaterialSKUID(String refMaterialSKUID) {
		this.refMaterialSKUID = refMaterialSKUID;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getRefBOMUUID() {
		return refBOMUUID;
	}

	public void setRefBOMUUID(String refBOMUUID) {
		this.refBOMUUID = refBOMUUID;
	}

	public String getRefBOMOrderID() {
		return refBOMOrderID;
	}

	public void setRefBOMOrderID(String refBOMOrderID) {
		this.refBOMOrderID = refBOMOrderID;
	}

	public String getRefProcessRouteID() {
		return refProcessRouteID;
	}

	public void setRefProcessRouteID(String refProcessRouteID) {
		this.refProcessRouteID = refProcessRouteID;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
