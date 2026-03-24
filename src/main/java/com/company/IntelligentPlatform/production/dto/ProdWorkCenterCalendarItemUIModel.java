package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdWorkCenterCalendarItem;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdWorkCenterCalendarItemUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "endDate", seName = ProdWorkCenterCalendarItem.SENAME, nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstID = ProdWorkCenterCalendarItem.NODENAME, tabId = TABID_BASIC)
	protected String endDate;

	@ISEUIModelMapping(fieldName = "refUUID", seName = ProdWorkCenterCalendarItem.SENAME, nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstID = ProdWorkCenterCalendarItem.NODENAME, showOnList = false, tabId = TABID_BASIC)
	protected String refUUID;

	@ISEUIModelMapping(fieldName = "startDate", seName = ProdWorkCenterCalendarItem.SENAME, nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstID = ProdWorkCenterCalendarItem.NODENAME, tabId = TABID_BASIC)
	protected String startDate;

	@ISEUIModelMapping(fieldName = "refNodeName", seName = ProdWorkCenterCalendarItem.SENAME, nodeName = ProdWorkCenterCalendarItem.NODENAME, nodeInstID = ProdWorkCenterCalendarItem.NODENAME, showOnList = false, searchFlag = false, tabId = TABID_BASIC)
	protected String refNodeName;

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRefUUID() {
		return this.refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getRefNodeName() {
		return this.refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

}
