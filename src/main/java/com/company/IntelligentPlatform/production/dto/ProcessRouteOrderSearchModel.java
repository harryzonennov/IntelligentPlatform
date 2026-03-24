package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ProcessRouteOrder UI Model
 **
 * @author
 * @date Thu Mar 31 15:20:05 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProcessRouteOrderSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "keyRouteFlag", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected int keyRouteFlag;

	@BSearchFieldConfig(fieldName = "routeType", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProcessRouteOrderSearch_routeType", valueFieldName = "")
	protected int routeType;

	@BSearchFieldConfig(fieldName = "status", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProcessRouteOrderSearch_status", valueFieldName = "")
	protected int status;

	@BSearchFieldConfig(fieldName = "refParentProcessRouteUUID", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected String refParentProcessRouteUUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = ProcessRouteOrder.NODENAME, seName = ProcessRouteOrder.SENAME, nodeInstID = ProcessRouteOrder.SENAME)
	protected String refMaterialSKUUUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUID", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUName", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyRouteFlag() {
		return keyRouteFlag;
	}

	public void setKeyRouteFlag(int keyRouteFlag) {
		this.keyRouteFlag = keyRouteFlag;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRefParentProcessRouteUUID() {
		return refParentProcessRouteUUID;
	}

	public void setRefParentProcessRouteUUID(String refParentProcessRouteUUID) {
		this.refParentProcessRouteUUID = refParentProcessRouteUUID;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
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

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
