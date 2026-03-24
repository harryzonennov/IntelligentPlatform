package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SerExtendPageMetadataUIModel extends SEUIComModel {

	protected String pageMeta;

	@ISEDropDownResourceMapping(resouceMapping = "SerExtendPageSetting_status", valueFieldName = "itemStatusValue")
	protected int itemStatus;

	protected int systemCategory;

	protected String itemStatusValue;

	protected String systemCategoryValue;

	public String getPageMeta() {
		return pageMeta;
	}

	public void setPageMeta(String pageMeta) {
		this.pageMeta = pageMeta;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public String getItemStatusValue() {
		return itemStatusValue;
	}

	public void setItemStatusValue(String itemStatusValue) {
		this.itemStatusValue = itemStatusValue;
	}

	public String getSystemCategoryValue() {
		return systemCategoryValue;
	}

	public void setSystemCategoryValue(String systemCategoryValue) {
		this.systemCategoryValue = systemCategoryValue;
	}

}
