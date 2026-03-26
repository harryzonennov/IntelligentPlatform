package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * warehouseArea UI Model
 ** 
 * @author
 * @date Mon Dec 02 14:00:30 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class WarehouseAreaSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = WarehouseArea.NODENAME, 
			seName = WarehouseArea.SENAME, nodeInstID = WarehouseArea.NODENAME)
	protected String id;
	
	@BSearchFieldConfig(fieldName = "parentNodeUUID", nodeName = WarehouseArea.NODENAME, 
			seName = WarehouseArea.SENAME, nodeInstID = WarehouseArea.NODENAME)
	protected String parentNodeUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = WarehouseArea.NODENAME, 
			seName = WarehouseArea.SENAME, nodeInstID = WarehouseArea.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = Warehouse.NODENAME, 
			seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = Warehouse.NODENAME,
			seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected String warehouseName;

	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = Warehouse.NODENAME,
			seName = Warehouse.SENAME, nodeInstID = Warehouse.SENAME)
	protected int switchFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}	
	
}
