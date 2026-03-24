package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.dto.WarehouseSubSearchModel;

@Component
public class WarehouseSubSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid")
	protected String refWarehouseUUID;

	@BSearchFieldConfig(fieldName = "id")
	protected String refWarehouseId;

	@BSearchFieldConfig(fieldName = "uuid", subNodeInstId = WarehouseArea.NODENAME)
	protected String refWarehouseAreaUUID;

	@BSearchFieldConfig(fieldName = "name")
	protected String refWarehouseName;

	@BSearchFieldConfig(fieldName = "id", subNodeInstId = WarehouseArea.NODENAME)
	protected String refWarehouseAreaId;

	@BSearchFieldConfig(fieldName = "name", subNodeInstId = WarehouseArea.NODENAME)
	protected String refWarehouseAreaName;

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefWarehouseAreaId() {
		return refWarehouseAreaId;
	}

	public void setRefWarehouseAreaId(String refWarehouseAreaId) {
		this.refWarehouseAreaId = refWarehouseAreaId;
	}

	public String getRefWarehouseAreaName() {
		return refWarehouseAreaName;
	}

	public void setRefWarehouseAreaName(String refWarehouseAreaName) {
		this.refWarehouseAreaName = refWarehouseAreaName;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}
}
