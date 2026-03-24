package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.service.ServiceChartHelper;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;

public class WarehouseAverageChartRequest {
	
	protected String warehouseUUID1;
	
	protected String warehouseName1;
	
	protected String warehouseUUID2;
	
	protected String warehouseName2;
	
	protected String warehouseUUID3;
	
	protected String warehouseName3;
	
	protected String warehouseUUID4;
	
	protected String warehouseName4;
	
	protected String startTime;
	
	protected String endTime;
	
	protected String chartType;
	
	public WarehouseAverageChartRequest(){
		endTime = DefaultDateFormatConstant.DATE_MIN_FORMAT.format(new Date());
		chartType = ServiceChartHelper.CHARTTYPE_COLUMN;		
	}

	public String getWarehouseUUID1() {
		return warehouseUUID1;
	}

	public void setWarehouseUUID1(String warehouseUUID1) {
		this.warehouseUUID1 = warehouseUUID1;
	}

	public String getWarehouseName1() {
		return warehouseName1;
	}

	public void setWarehouseName1(String warehouseName1) {
		this.warehouseName1 = warehouseName1;
	}

	public String getWarehouseUUID2() {
		return warehouseUUID2;
	}

	public void setWarehouseUUID2(String warehouseUUID2) {
		this.warehouseUUID2 = warehouseUUID2;
	}

	public String getWarehouseName2() {
		return warehouseName2;
	}

	public void setWarehouseName2(String warehouseName2) {
		this.warehouseName2 = warehouseName2;
	}

	public String getWarehouseUUID3() {
		return warehouseUUID3;
	}

	public void setWarehouseUUID3(String warehouseUUID3) {
		this.warehouseUUID3 = warehouseUUID3;
	}

	public String getWarehouseName3() {
		return warehouseName3;
	}

	public void setWarehouseName3(String warehouseName3) {
		this.warehouseName3 = warehouseName3;
	}

	public String getWarehouseUUID4() {
		return warehouseUUID4;
	}

	public void setWarehouseUUID4(String warehouseUUID4) {
		this.warehouseUUID4 = warehouseUUID4;
	}

	public String getWarehouseName4() {
		return warehouseName4;
	}

	public void setWarehouseName4(String warehouseName4) {
		this.warehouseName4 = warehouseName4;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	
}
