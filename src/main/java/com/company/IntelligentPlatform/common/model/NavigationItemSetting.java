package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class NavigationItemSetting  extends ServiceEntityNode {
	
	public static final String NODENAME = IServiceModelConstants.NavigationItemSetting;

	public static final String SENAME = IServiceModelConstants.NavigationSystemSetting;

	public static final String FIELD_PARENT_ELEMENT_UUID = "parentElementUUID";

	public NavigationItemSetting() {		
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;	
		this.displayIndex = 1;
		this.layer = 1;
		this.displayFlag = StandardSwitchProxy.SWITCH_ON;
	}
	
	protected String refDefItemUUID;
	
	protected String refSourceUUID;
	
	protected String refSimAuthorObjectUUID;
	
	protected String refAuthorActionCodeUUID;
	
	protected String parentElementUUID;
	
	protected String elementIcon;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String keywords;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_800)
	protected String targetUrl;
	
	protected int displayIndex;
	
	protected int layer;
	
	protected int displayFlag;

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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public int getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(int displayFlag) {
		this.displayFlag = displayFlag;
	}
	
}
