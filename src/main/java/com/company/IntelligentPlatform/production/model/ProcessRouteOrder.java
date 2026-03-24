package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinProduction - ProcessRouteOrder (extends ServiceEntityNode)
 * Table: ProcessRouteOrder (schema: production)
 */
@Entity
@Table(name = "ProcessRouteOrder", schema = "production")
public class ProcessRouteOrder extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProcessRouteOrder;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_INUSE   = 2;

	public static final int STATUS_RETIRED = 3;

	public static final int ROUTE_TYPE_STANDARD = 1;

	public static final int ROUTE_TYPE_REPAIR   = 2;

	@Column(name = "keyRouteFlag")
	protected int keyRouteFlag;

	@Column(name = "status")
	protected int status;

	@Column(name = "refParentProcessRouteUUID")
	protected String refParentProcessRouteUUID;

	@Column(name = "refTemplateProcessRouteUUID")
	protected String refTemplateProcessRouteUUID;

	@Column(name = "refMaterialSKUUUID")
	protected String refMaterialSKUUUID;

	@Column(name = "routeType")
	protected int routeType;

	@Column(name = "refUnitUUID")
	protected String refUnitUUID;

	public int getKeyRouteFlag() {
		return keyRouteFlag;
	}

	public void setKeyRouteFlag(int keyRouteFlag) {
		this.keyRouteFlag = keyRouteFlag;
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

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

}
