package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class NavigationGroupSetting  extends ServiceEntityNode {
	
	public static final String NODENAME = IServiceModelConstants.NavigationGroupSetting;

	public static final String SENAME = IServiceModelConstants.NavigationSystemSetting;	

	public NavigationGroupSetting() {		
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;	
		this.displayIndex = 1;
	}
	
	protected String refDefItemUUID;
	
	protected String refSourceUUID;
	
	protected String refSimAuthorObjectUUID;
	
	protected String refAuthorActionCodeUUID;
	
	protected String parentElementUUID;
	
	protected String elementIcon;
	
	protected int displayIndex;

	public String getRefDefItemUUID() {
		return refDefItemUUID;
	}

	public void setRefDefItemUUID(String refDefItemUUID) {
		this.refDefItemUUID = refDefItemUUID;
	}

	public String getRefSourceUUID() {
		return refSourceUUID;
	}

	public void setRefSourceUUID(String refSourceUUID) {
		this.refSourceUUID = refSourceUUID;
	}

	public String getRefSimAuthorObjectUUID() {
		return refSimAuthorObjectUUID;
	}

	public void setRefSimAuthorObjectUUID(String refSimAuthorObjectUUID) {
		this.refSimAuthorObjectUUID = refSimAuthorObjectUUID;
	}

	public String getRefAuthorActionCodeUUID() {
		return refAuthorActionCodeUUID;
	}

	public void setRefAuthorActionCodeUUID(String refAuthorActionCodeUUID) {
		this.refAuthorActionCodeUUID = refAuthorActionCodeUUID;
	}

	public String getParentElementUUID() {
		return parentElementUUID;
	}

	public void setParentElementUUID(String parentElementUUID) {
		this.parentElementUUID = parentElementUUID;
	}

	public String getElementIcon() {
		return elementIcon;
	}

	public void setElementIcon(String elementIcon) {
		this.elementIcon = elementIcon;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	
	
}
