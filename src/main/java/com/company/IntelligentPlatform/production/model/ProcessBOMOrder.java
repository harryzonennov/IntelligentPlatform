package com.company.IntelligentPlatform.production.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
@Entity
@Table(name = "ProcessBOMOrder", schema = "production")
public class ProcessBOMOrder extends ServiceEntityNode {

	public final static String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public final static String SENAME = IServiceModelConstants.ProcessBOMOrder;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_INUSE = 2;

	public static final int STATUS_RETIRED = 3;

	protected String refMaterialSKUUUID;

	protected String refBOMUUID;

	protected double amount;

	protected String refUnitUUID;

	protected int status;

	protected int itemCategory;

	protected String refProcessRouteUUID;

	public ProcessBOMOrder() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.parentNodeUUID = super.uuid;
		this.rootNodeUUID = super.uuid;
		this.status = STATUS_INITIAL;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getRefBOMUUID() {
		return refBOMUUID;
	}

	public void setRefBOMUUID(String refBOMUUID) {
		this.refBOMUUID = refBOMUUID;
	}

	public String getRefProcessRouteUUID() {
		return refProcessRouteUUID;
	}

	public void setRefProcessRouteUUID(String refProcessRouteUUID) {
		this.refProcessRouteUUID = refProcessRouteUUID;
	}

}
