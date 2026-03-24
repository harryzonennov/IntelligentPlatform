package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SerExtendPageSection extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.SerExtendPageSection;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;

	public static final int SECTION_CATE_EDIT = 1;

	public static final int SECTION_CATE_TABLE = 2;

	public static final int SECTION_CATE_TREE = 3;

	public static final int SECTION_CATE_SEARCH = 4;

	public static final int SECTION_CATE_EMBEDLIST = 5;

	public static final int SECTION_CATE_ATTACHMENT = 6;

	public SerExtendPageSection() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.sectionCategory = SECTION_CATE_EDIT;
		this.visibleSwitch = StandardSwitchProxy.SWITCH_ON;
		this.processIndex = 1;
	}

	protected String tabId;

	protected int processIndex;

	protected int sectionCategory;

	protected int visibleSwitch;

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

	public int getVisibleSwitch() {
		return visibleSwitch;
	}

	public void setVisibleSwitch(int visibleSwitch) {
		this.visibleSwitch = visibleSwitch;
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
