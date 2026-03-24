package com.company.IntelligentPlatform.logistics.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrder;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * InventoryCheckOrder UI Model
 ** 
 * @author
 * @date Fri Sep 18 10:55:27 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class InventoryCheckItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = InventoryCheckItem.NODENAME, seName = InventoryCheckItem.SENAME,
			nodeInstID = InventoryCheckItem.NODENAME)
	protected String rootNodeUUID;
	
	@BSearchFieldConfig(fieldName = "parentNodeUUID", nodeName = InventoryCheckItem.NODENAME, seName = InventoryCheckItem.SENAME,
			nodeInstID = InventoryCheckItem.NODENAME)
	protected String parentNodeUUID;

	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = InventoryCheckItem.NODENAME, seName = InventoryCheckItem.SENAME, 
			nodeInstID = InventoryCheckItem.NODENAME)
	protected Date refMaterialSKUUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected Date refMaterialSKUId;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "packageStandard", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String packageStandard;

	@BSearchFieldConfig(fieldName = "packageStandard", nodeName = InventoryCheckOrder.NODENAME, seName = InventoryCheckOrder.SENAME, nodeInstID = InventoryCheckOrder.SENAME)
	protected String checkOrderId;

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

	public Date getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(Date refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public Date getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(Date refMaterialSKUId) {
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

	public String getCheckOrderId() {
		return checkOrderId;
	}

	public void setCheckOrderId(String checkOrderId) {
		this.checkOrderId = checkOrderId;
	}
	
	
	
}
