package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProcessRouteOrderUIModel extends SEUIComModel {

	protected int keyRouteFlag;
	
	protected String keyRouteValue;

	@ISEDropDownResourceMapping(resouceMapping = "ProcessRouteOrder_routeType", valueFieldName = "routeTypeValue")
	protected int routeType;

	protected String routeTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "ProcessRouteOrder_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	protected String refParentProcessRouteUUID;

	protected String refTemplateProcessRouteUUID;

	protected String refMaterialSKUUUID;
	
	protected String refMaterialSKUId;

	protected String refMaterialSKUName;
	
	protected String refUnitUUID;
	
	protected String refUnitName;

	public int getKeyRouteFlag() {
		return keyRouteFlag;
	}

	public void setKeyRouteFlag(int keyRouteFlag) {
		this.keyRouteFlag = keyRouteFlag;
	}

	public String getKeyRouteValue() {
		return keyRouteValue;
	}

	public void setKeyRouteValue(String keyRouteValue) {
		this.keyRouteValue = keyRouteValue;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public String getRouteTypeValue() {
		return routeTypeValue;
	}

	public void setRouteTypeValue(String routeTypeValue) {
		this.routeTypeValue = routeTypeValue;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getRefParentProcessRouteUUID() {
		return refParentProcessRouteUUID;
	}

	public void setRefParentProcessRouteUUID(String refParentProcessRouteUUID) {
		this.refParentProcessRouteUUID = refParentProcessRouteUUID;
	}

	public String getRefTemplateProcessRouteUUID() {
		return refTemplateProcessRouteUUID;
	}

	public void setRefTemplateProcessRouteUUID(String refTemplateProcessRouteUUID) {
		this.refTemplateProcessRouteUUID = refTemplateProcessRouteUUID;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
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

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}
	
	

}
