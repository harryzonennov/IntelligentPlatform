package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * BillOfMaterialOrder UI Model
 ** 
 * @author
 * @date Sat Dec 26 16:37:55 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class BillOfMaterialOrderSearchModel extends SEUIComModel {
	
	public static final String NODEID_SUBITEM_MATERIAL = "subItemMaterial";

	@BSearchFieldConfig(fieldName = "id", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME
			, nodeInstID = BillOfMaterialOrder.SENAME)
	protected String name;
	
	@BSearchFieldConfig(fieldName = "itemCategory", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected int itemCategory;

	@BSearchFieldConfig(fieldName = "serviceEntityName", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected String serviceEntityName;
	
	@BSearchFieldConfig(fieldName = "status", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialOrder_status", valueFieldName = "statusValue")
	protected int status;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, 
			nodeInstID = BillOfMaterialOrder.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date createdTimeLow;

	@BSearchFieldConfig(fieldName = "createdTime", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, 
			nodeInstID = BillOfMaterialOrder.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date createdTimeHigh;	

	protected String createdTimeLowStr;

	protected String createdTimeHighStr;

	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME, showOnUI = false)
	protected String refMaterialSKUUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUId;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "refTemplateUUID", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected String refTemplateUUID;

	@BSearchFieldConfig(fieldName = "layer", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected int layer;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = NODEID_SUBITEM_MATERIAL)
	protected String subMaterialSKUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = NODEID_SUBITEM_MATERIAL)
	protected String subMaterialSKUName;
	
	protected int viewType;
	
	@BSearchFieldConfig(fieldName = "refWocUUID", nodeName = BillOfMaterialOrder.NODENAME, seName = BillOfMaterialOrder.SENAME, nodeInstID = BillOfMaterialOrder.SENAME)
	protected String refWocUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected String refWocId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected String refWocName;

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

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public Date getCreatedTimeLow() {
		return createdTimeLow;
	}

	public void setCreatedTimeLow(Date createdTimeLow) {
		this.createdTimeLow = createdTimeLow;
	}

	public Date getCreatedTimeHigh() {
		return createdTimeHigh;
	}

	public void setCreatedTimeHigh(Date createdTimeHigh) {
		this.createdTimeHigh = createdTimeHigh;
	}

	public String getCreatedTimeLowStr() {
		return createdTimeLowStr;
	}

	public void setCreatedTimeLowStr(String createdTimeLowStr) {
		this.createdTimeLowStr = createdTimeLowStr;
	}

	public String getCreatedTimeHighStr() {
		return createdTimeHighStr;
	}

	public void setCreatedTimeHighStr(String createdTimeHighStr) {
		this.createdTimeHighStr = createdTimeHighStr;
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

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getSubMaterialSKUID() {
		return subMaterialSKUID;
	}

	public void setSubMaterialSKUID(String subMaterialSKUID) {
		this.subMaterialSKUID = subMaterialSKUID;
	}

	public String getSubMaterialSKUName() {
		return subMaterialSKUName;
	}

	public void setSubMaterialSKUName(String subMaterialSKUName) {
		this.subMaterialSKUName = subMaterialSKUName;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getRefWocUUID() {
		return refWocUUID;
	}

	public void setRefWocUUID(String refWocUUID) {
		this.refWocUUID = refWocUUID;
	}

	public String getRefWocId() {
		return refWocId;
	}

	public void setRefWocId(String refWocId) {
		this.refWocId = refWocId;
	}

	public String getRefWocName() {
		return refWocName;
	}

	public void setRefWocName(String refWocName) {
		this.refWocName = refWocName;
	}

	public String getRefTemplateUUID() {
		return refTemplateUUID;
	}

	public void setRefTemplateUUID(String refTemplateUUID) {
		this.refTemplateUUID = refTemplateUUID;
	}
}
