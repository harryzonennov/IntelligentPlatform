package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ProductionResourceUnion UI Model
 **
 * @author
 * @date Sun Mar 27 18:44:11 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProductionResourceUnionSearchModel extends SEUIComModel {
	
	public static final String NODEID_COSTCENTER = "costCenter";

	@BSearchFieldConfig(fieldName = "id", nodeName = ProductionResourceUnion.NODENAME, seName = ProductionResourceUnion.SENAME, nodeInstID = ProductionResourceUnion.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProductionResourceUnion.NODENAME, seName = ProductionResourceUnion.SENAME, nodeInstID = ProductionResourceUnion.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "keyResourceFlag", nodeName = ProductionResourceUnion.NODENAME, seName = ProductionResourceUnion.SENAME, nodeInstID = ProductionResourceUnion.SENAME)
	protected int keyResourceFlag;

	@BSearchFieldConfig(fieldName = "resourceType", nodeName = ProductionResourceUnion.NODENAME, seName = ProductionResourceUnion.SENAME, nodeInstID = ProductionResourceUnion.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProductionResourceUnioSearch_resourceType", valueFieldName = "")
	protected int resourceType;

	@BSearchFieldConfig(fieldName = "resourceTypeValue", nodeName = ProductionResourceUnion.NODENAME, seName = ProductionResourceUnion.SENAME, nodeInstID = ProductionResourceUnion.SENAME)
	protected String resourceTypeValue;
	
	@BSearchFieldConfig(fieldName = "uuid", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_COSTCENTER, showOnUI = false)
	protected String refCostCenterUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_COSTCENTER)
	protected String refCostCenterID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_COSTCENTER)
	protected String refCostCenterName;
	
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

	public int getKeyResourceFlag() {
		return keyResourceFlag;
	}

	public void setKeyResourceFlag(int keyResourceFlag) {
		this.keyResourceFlag = keyResourceFlag;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceTypeValue() {
		return resourceTypeValue;
	}

	public void setResourceTypeValue(String resourceTypeValue) {
		this.resourceTypeValue = resourceTypeValue;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getRefCostCenterUUID() {
		return refCostCenterUUID;
	}

	public void setRefCostCenterUUID(String refCostCenterUUID) {
		this.refCostCenterUUID = refCostCenterUUID;
	}

	public String getRefCostCenterID() {
		return refCostCenterID;
	}

	public void setRefCostCenterID(String refCostCenterID) {
		this.refCostCenterID = refCostCenterID;
	}

	public String getRefCostCenterName() {
		return refCostCenterName;
	}

	public void setRefCostCenterName(String refCostCenterName) {
		this.refCostCenterName = refCostCenterName;
	}
	
}
