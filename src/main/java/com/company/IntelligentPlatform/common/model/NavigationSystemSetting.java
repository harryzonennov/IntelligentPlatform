package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class NavigationSystemSetting  extends ServiceEntityNode {
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.NavigationSystemSetting;
	
	public static final int APP_LEVEL_COMPANY = 1;
	
	public static final int APP_LEVEL_USER = 2;

	public static final int STATUS_INIT = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_ACTIVE = DocumentContent.STATUS_ACTIVE;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;
	
	public NavigationSystemSetting(){
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.status = NavigationSystemSetting.STATUS_INIT;
		this.applicationLevel = APP_LEVEL_COMPANY;
		this.systemCategory = StandardSystemCategoryProxy.CATE_SYSTEM_STANDARD;
	}
	
	protected int status;
	
	protected int applicationLevel;
	
	protected int systemCategory;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getApplicationLevel() {
		return applicationLevel;
	}

	public void setApplicationLevel(int applicationLevel) {
		this.applicationLevel = applicationLevel;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

}
