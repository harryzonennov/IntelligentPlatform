package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;

/**
 * BillOfMaterialTemplateItem UI Model
 ** 
 * @author
 * @date Sat Dec 26 16:37:55 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class BillOfMaterialTemplateItemSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = BillOfMaterialTemplateItem.NODENAME, seName = BillOfMaterialTemplateItem.SENAME, nodeInstID = BillOfMaterialTemplateItem.NODENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "serviceEntityName", nodeName = BillOfMaterialTemplateItem.NODENAME, seName = BillOfMaterialTemplateItem.SENAME, nodeInstID = BillOfMaterialTemplateItem.NODENAME)
	protected String serviceEntityName;

	@BSearchFieldConfig(fieldName = "refMaterialSKUUUID", nodeName = BillOfMaterialTemplateItem.NODENAME, seName = BillOfMaterialTemplateItem.SENAME, nodeInstID = BillOfMaterialTemplateItem.NODENAME, showOnUI = false)
	protected String refMaterialSKUUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.NODENAME)
	protected String refMaterialSKUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.NODENAME)
	protected String refMaterialSKUName;

	@BSearchFieldConfig(fieldName = "layer", nodeName = BillOfMaterialTemplateItem.NODENAME, seName = BillOfMaterialTemplateItem.SENAME, nodeInstID = BillOfMaterialTemplateItem.NODENAME)
	protected int layer;

	@BSearchFieldConfig(fieldName = "refParentItemUUID", nodeName = BillOfMaterialTemplateItem.NODENAME, seName = BillOfMaterialTemplateItem.SENAME, nodeInstID = BillOfMaterialTemplateItem.NODENAME, showOnUI = false)
	protected String refParentItemUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = BillOfMaterialTemplateItem.NODENAME, seName = BillOfMaterialTemplateItem.SENAME, nodeInstID = BillOfMaterialTemplateItem.NODENAME, showOnUI = false)
	protected String rootNodeUUID;
	
	public BillOfMaterialTemplateItemSearchModel(){
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
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

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getRefParentItemUUID() {
		return refParentItemUUID;
	}

	public void setRefParentItemUUID(String refParentItemUUID) {
		this.refParentItemUUID = refParentItemUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

}
