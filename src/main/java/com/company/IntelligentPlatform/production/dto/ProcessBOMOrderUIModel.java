package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProcessBOMOrderUIModel extends SEUIComModel {

	protected String refMaterialSKUUUID;

	protected String refMaterialSKUID;

	protected String refMaterialSKUName;

	protected double amount;

	protected String refUnitUUID;

	protected String refBOMUUID;
	
	protected String refBOMOrderID;
	
	protected String refProcessRouteUUID;

	protected String refProcessRouteID;
	
	protected int routeType;
	
	
	protected int routeStatus;

	public ProcessBOMOrderUIModel() {

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

	public String getRefBOMUUID() {
		return refBOMUUID;
	}

	public void setRefBOMUUID(String refBOMUUID) {
		this.refBOMUUID = refBOMUUID;
	}

	public String getRefBOMOrderID() {
		return refBOMOrderID;
	}

	public void setRefBOMOrderID(String refBOMOrderID) {
		this.refBOMOrderID = refBOMOrderID;
	}

	public String getRefProcessRouteUUID() {
		return refProcessRouteUUID;
	}

	public void setRefProcessRouteUUID(String refProcessRouteUUID) {
		this.refProcessRouteUUID = refProcessRouteUUID;
	}

	public String getRefProcessRouteID() {
		return refProcessRouteID;
	}

	public void setRefProcessRouteID(String refProcessRouteID) {
		this.refProcessRouteID = refProcessRouteID;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRouteStatus() {
		return routeStatus;
	}

	public void setRouteStatus(int routeStatus) {
		this.routeStatus = routeStatus;
	}

}
