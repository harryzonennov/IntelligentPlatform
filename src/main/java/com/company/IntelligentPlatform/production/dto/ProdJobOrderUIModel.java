package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdJobOrderUIModel extends SEUIComModel implements Comparable<ProdJobOrderUIModel>{

	protected String refProductionOrderUUID;

	protected String refProductionOrderID;

	protected String refMainMaterialSKUUUID;

	protected String refMainMaterialID;

	protected String refMainMaterialName;

	protected double mainAmount;
	
	protected String mainAmountLabel;

	protected String mainRefUnitUUID;

	protected String refProdRouteProcessItemUUID;

	protected String refProcessRouteOrderID;

	protected String refProcessRouteOrderName;

	protected String processID;
	
	protected String processName;	
	
	protected int processIndex;

	protected String refWorkCenterUUID;

	protected String refWorkCenterID;

	protected String refWorkCenterName;

	protected String startDate;
	
	protected String planStartDate;

	protected String endDate;

	protected String planEndDate;

	protected double planNeedTime;

	protected int status;

	protected String statusValue;

	public String getRefProductionOrderUUID() {
		return refProductionOrderUUID;
	}

	public void setRefProductionOrderUUID(String refProductionOrderUUID) {
		this.refProductionOrderUUID = refProductionOrderUUID;
	}

	public String getRefProductionOrderID() {
		return refProductionOrderID;
	}

	public void setRefProductionOrderID(String refProductionOrderID) {
		this.refProductionOrderID = refProductionOrderID;
	}

	public String getRefMainMaterialSKUUUID() {
		return refMainMaterialSKUUUID;
	}

	public void setRefMainMaterialSKUUUID(String refMainMaterialSKUUUID) {
		this.refMainMaterialSKUUUID = refMainMaterialSKUUUID;
	}

	public String getRefMainMaterialID() {
		return refMainMaterialID;
	}

	public void setRefMainMaterialID(String refMainMaterialID) {
		this.refMainMaterialID = refMainMaterialID;
	}

	public String getRefMainMaterialName() {
		return refMainMaterialName;
	}

	public void setRefMainMaterialName(String refMainMaterialName) {
		this.refMainMaterialName = refMainMaterialName;
	}

	public double getMainAmount() {
		return mainAmount;
	}

	public void setMainAmount(double mainAmount) {
		this.mainAmount = mainAmount;
	}

	public String getMainAmountLabel() {
		return mainAmountLabel;
	}

	public void setMainAmountLabel(String mainAmountLabel) {
		this.mainAmountLabel = mainAmountLabel;
	}

	public String getMainRefUnitUUID() {
		return mainRefUnitUUID;
	}

	public void setMainRefUnitUUID(String mainRefUnitUUID) {
		this.mainRefUnitUUID = mainRefUnitUUID;
	}

	public String getRefProdRouteProcessItemUUID() {
		return refProdRouteProcessItemUUID;
	}

	public void setRefProdRouteProcessItemUUID(String refProdRouteProcessItemUUID) {
		this.refProdRouteProcessItemUUID = refProdRouteProcessItemUUID;
	}

	public String getRefProcessRouteOrderID() {
		return refProcessRouteOrderID;
	}

	public void setRefProcessRouteOrderID(String refProcessRouteOrderID) {
		this.refProcessRouteOrderID = refProcessRouteOrderID;
	}

	public String getRefProcessRouteOrderName() {
		return refProcessRouteOrderName;
	}

	public void setRefProcessRouteOrderName(String refProcessRouteOrderName) {
		this.refProcessRouteOrderName = refProcessRouteOrderName;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefWorkCenterUUID() {
		return refWorkCenterUUID;
	}

	public void setRefWorkCenterUUID(String refWorkCenterUUID) {
		this.refWorkCenterUUID = refWorkCenterUUID;
	}

	public String getRefWorkCenterID() {
		return refWorkCenterID;
	}

	public void setRefWorkCenterID(String refWorkCenterID) {
		this.refWorkCenterID = refWorkCenterID;
	}

	public String getRefWorkCenterName() {
		return refWorkCenterName;
	}

	public void setRefWorkCenterName(String refWorkCenterName) {
		this.refWorkCenterName = refWorkCenterName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public double getPlanNeedTime() {
		return planNeedTime;
	}

	public void setPlanNeedTime(double planNeedTime) {
		this.planNeedTime = planNeedTime;
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

	@Override
	public int compareTo(ProdJobOrderUIModel other) {
		Integer thisProcessIndex = this.getProcessIndex();
		Integer otherProcessIndex = other.getProcessIndex();
		return thisProcessIndex.compareTo(otherProcessIndex);
	}
	

}
