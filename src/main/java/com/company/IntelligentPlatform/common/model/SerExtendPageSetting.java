package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SerExtendPageSetting extends ServiceEntityNode{
	
	public static final String NODENAME = IServiceModelConstants.SerExtendPageSetting;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;
	
	public static final int PAGE_CATE_EDIT = 1;
	
	public static final int PAGE_CATE_LIST = 2;
	
	public static final int PAGE_CATE_INIT = 3;

	public static final int STATUS_INIT = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ACTIVE = DocumentContent.STATUS_ACTIVE;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;
	
	public SerExtendPageSetting() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.pageCategory = PAGE_CATE_EDIT;
		this.status = STATUS_INIT;
		this.systemCategory = StandardSystemCategoryProxy.CATE_SYSTEM_STANDARD;
	}

	protected int status;
	
	protected String navigationId;
	
	protected String accessResourceId;
	
	protected String accessPageUrl;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String tabArray;
	
	protected int pageCategory;
	
	protected int activeSwitch;
	
	protected int systemCategory;	

	public String getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(String navigationId) {
		this.navigationId = navigationId;
	}

	public String getAccessResourceId() {
		return accessResourceId;
	}

	public int getActiveSwitch() {
		return activeSwitch;
	}

	public void setActiveSwitch(int activeSwitch) {
		this.activeSwitch = activeSwitch;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public void setAccessResourceId(String accessResourceId) {
		this.accessResourceId = accessResourceId;
	}

	public String getAccessPageUrl() {
		return accessPageUrl;
	}

	public void setAccessPageUrl(String accessPageUrl) {
		this.accessPageUrl = accessPageUrl;
	}

	public int getPageCategory() {
		return pageCategory;
	}

	public void setPageCategory(int pageCategory) {
		this.pageCategory = pageCategory;
	}

	public String getTabArray() {
		return tabArray;
	}

	public void setTabArray(String tabArray) {
		this.tabArray = tabArray;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
