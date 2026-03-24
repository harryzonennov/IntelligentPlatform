package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.model.ISQLSepcifyAttribute;

public class SerExtendPageSectionUIModel extends SEUIComModel{	

    protected String tabId;
	
	protected int processIndex;
	
	@ISEDropDownResourceMapping(resouceMapping = "SerExtendPageSection_sectionCategory", valueFieldName = "pageCategoryValue")
	protected int sectionCategory;
	
	protected String sectionCategoryValue;
	
	protected int visibleSwitch;
	
	protected String visibleSwitchValue;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String visibleExpression;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String visibleActionCode;

	protected String refDomId;

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public int getSectionCategory() {
		return sectionCategory;
	}

	public void setSectionCategory(int sectionCategory) {
		this.sectionCategory = sectionCategory;
	}

	public String getSectionCategoryValue() {
		return sectionCategoryValue;
	}

	public void setSectionCategoryValue(String sectionCategoryValue) {
		this.sectionCategoryValue = sectionCategoryValue;
	}

	public int getVisibleSwitch() {
		return visibleSwitch;
	}

	public void setVisibleSwitch(int visibleSwitch) {
		this.visibleSwitch = visibleSwitch;
	}

	public String getVisibleSwitchValue() {
		return visibleSwitchValue;
	}

	public void setVisibleSwitchValue(String visibleSwitchValue) {
		this.visibleSwitchValue = visibleSwitchValue;
	}

	public String getVisibleExpression() {
		return visibleExpression;
	}

	public void setVisibleExpression(String visibleExpression) {
		this.visibleExpression = visibleExpression;
	}

	public String getVisibleActionCode() {
		return visibleActionCode;
	}

	public void setVisibleActionCode(String visibleActionCode) {
		this.visibleActionCode = visibleActionCode;
	}

	public String getRefDomId() {
		return refDomId;
	}

	public void setRefDomId(String refDomId) {
		this.refDomId = refDomId;
	}
    
}
