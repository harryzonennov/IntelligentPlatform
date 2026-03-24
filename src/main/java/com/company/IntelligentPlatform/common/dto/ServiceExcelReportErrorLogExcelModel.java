package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ServiceExcelReportErrorLog Excel Model
 **
 * @author
 * @date Tue Feb 23 23:21:18 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class ServiceExcelReportErrorLogExcelModel extends SEUIComModel {

	protected String id;

	protected String itemStatus;

	protected String errorMessage;

	protected String rowIndex;

	protected String sheetName;

	protected int locationType;

	protected String locationValue;

	protected int errorCategory;
	
	public static final String FIELD_ITEMSTATUS = "itemStatus";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getLocationType() {
		return locationType;
	}

	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}

	public String getLocationValue() {
		return locationValue;
	}

	public void setLocationValue(String locationValue) {
		this.locationValue = locationValue;
	}

	public int getErrorCategory() {
		return errorCategory;
	}

	public void setErrorCategory(int errorCategory) {
		this.errorCategory = errorCategory;
	}

}
