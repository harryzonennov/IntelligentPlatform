package com.company.IntelligentPlatform.production.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ProdWorkCenter UI Model
 **
 * @author
 * @date Sun Mar 27 19:00:30 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class ProdWorkCenterSearchModel extends SEUIComModel {
	
	public static final String NODEID_COSTCENTER = "costCenter";
	
	public static final String NODEID_PARENTORG = "parentOrganization";

	@BSearchFieldConfig(fieldName = "id", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "keyWorkCenterFlag", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected int keyWorkCenterFlag;

	@BSearchFieldConfig(fieldName = "capacityCalculateType", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ProdWorkCenterSearch_capacityCalculateType", valueFieldName = "")
	protected int capacityCalculateType;
	
	@BSearchFieldConfig(fieldName = "parentOrganizationUUID", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME,
nodeInstID =ProdWorkCenter.SENAME, showOnUI = false)
	protected String parentOrganizationUUID;
	    
	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_PARENTORG, showOnUI = false)
	protected String parentOrganizationId;	
		
	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_PARENTORG, showOnUI = false)
	protected String parentOrganizationName;
	
	@BSearchFieldConfig(fieldName = "uuid", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_COSTCENTER, showOnUI = false)
	protected String refCostCenterUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_COSTCENTER)
	protected String refCostCenterID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEID_COSTCENTER)
	protected String refCostCenterName;
	
	@BSearchFieldConfig(fieldName = "keyWorkCenter", nodeName = ProdWorkCenter.NODENAME, seName = ProdWorkCenter.SENAME, nodeInstID = ProdWorkCenter.SENAME)
	protected boolean keyWorkCenter;

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

	public int getKeyWorkCenterFlag() {
		return keyWorkCenterFlag;
	}

	public void setKeyWorkCenterFlag(int keyWorkCenterFlag) {
		this.keyWorkCenterFlag = keyWorkCenterFlag;
	}

	public int getCapacityCalculateType() {
		return capacityCalculateType;
	}

	public void setCapacityCalculateType(int capacityCalculateType) {
		this.capacityCalculateType = capacityCalculateType;
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

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean getKeyWorkCenter() {
		return keyWorkCenter;
	}

	public void setKeyWorkCenter(boolean keyWorkCenter) {
		this.keyWorkCenter = keyWorkCenter;
	}

	public String getParentOrganizationUUID() {
		return parentOrganizationUUID;
	}

	public void setParentOrganizationUUID(String parentOrganizationUUID) {
		this.parentOrganizationUUID = parentOrganizationUUID;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}
	
}
