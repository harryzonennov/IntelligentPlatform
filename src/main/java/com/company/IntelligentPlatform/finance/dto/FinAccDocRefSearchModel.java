package com.company.IntelligentPlatform.finance.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.finance.model.FinAccDocRef;


import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;


@Component
public class FinAccDocRefSearchModel extends SEUIComModel {
	
	@BSearchFieldConfig(fieldName = "createdTime", nodeInstID = FinAccDocRef.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date createdTimeLow;

	@BSearchFieldConfig(fieldName = "createdTime", nodeInstID = FinAccDocRef.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date createdTimeHigh;

	public Date getCreatedTimeLow() {
		return createdTimeLow;
	}

	public void setCreatedTimeLow(Date createdTimeLow) {
		this.createdTimeLow = createdTimeLow;
	}

	public Date getCreatedTimeHigh() {
		return createdTimeHigh;
	}

	public void setCreatedTimeHigh(Date createdTimeHigh) {
		this.createdTimeHigh = createdTimeHigh;
	}

}
