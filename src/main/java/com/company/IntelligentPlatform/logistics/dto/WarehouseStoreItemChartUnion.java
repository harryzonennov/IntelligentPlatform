package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Internal and Dummy unit, only used for chart calculation
 * @author Zhang,Hang
 *
 */
public class WarehouseStoreItemChartUnion {
	
	protected String skuUUID;
	
	protected String skuName;
	
	protected double allAmount;
	
	protected double allOutboundAmount;
	
	protected double outboundRate;
	
	protected double averageAmount;
	
	protected int arriveIndex;
	
	protected int timeSlotIndex;
	
	protected Date startDate;
	
	protected Date endDate;
	
	protected List<WarehouseStoreItemLog> warehouseStoreItemLogList = new ArrayList<>();

	public String getSkuUUID() {
		return skuUUID;
	}

	public void setSkuUUID(String skuUUID) {
		this.skuUUID = skuUUID;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public double getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(double allAmount) {
		this.allAmount = allAmount;
	}

	public double getAllOutboundAmount() {
		return allOutboundAmount;
	}

	public void setAllOutboundAmount(double allOutboundAmount) {
		this.allOutboundAmount = allOutboundAmount;
	}

	public double getAverageAmount() {
		return averageAmount;
	}

	public void setAverageAmount(double averageAmount) {
		this.averageAmount = averageAmount;
	}

	public int getArriveIndex() {
		return arriveIndex;
	}

	public void setArriveIndex(int arriveIndex) {
		this.arriveIndex = arriveIndex;
	}

	public double getOutboundRate() {
		return outboundRate;
	}

	public void setOutboundRate(double outboundRate) {
		this.outboundRate = outboundRate;
	}

	public List<WarehouseStoreItemLog> getWarehouseStoreItemLogList() {
		return warehouseStoreItemLogList;
	}

	public void setWarehouseStoreItemLogList(
			List<WarehouseStoreItemLog> warehouseStoreItemLogList) {
		this.warehouseStoreItemLogList = warehouseStoreItemLogList;
	}

	public int getTimeSlotIndex() {
		return timeSlotIndex;
	}

	public void setTimeSlotIndex(int timeSlotIndex) {
		this.timeSlotIndex = timeSlotIndex;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
