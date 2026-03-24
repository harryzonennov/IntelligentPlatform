package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class NavigationElementResource extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.NavigationElementResource;
	
	public static final int INSTSWITCH_ON = 1;
	
	public static final int INSTSWITCH_OFF = 2;
	
    public static final int DISPLAY_SWITCH_ON = 1;
	
	public static final int DISPLAY_SWITCH_OFF = 2;
	
	public static final String FIELD_LINKURL = "linkURL";
	
	public static final String FIELD_GROUPUUID = "refNavigationGroupUUID";
	
	public static int nodeCategory = NODE_CATEGORY_SYS;
	
	protected int indexInGroup;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String linkURL;
	
	protected int instSwitch;
	
	protected int displaySwitch;
	
	protected String refSimAuthorObjectUUID;
	
	protected String refAuthorActionCodeUUID;
	
	protected String refNavigationGroupUUID;
	
	protected String elementTitle;
	
	protected String parentElementUUID;
	
	protected String elementIcon;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String tabs;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_800)
	protected String targetPageLink;
	
	public NavigationElementResource() {
		instSwitch = INSTSWITCH_ON;
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}

	public int getIndexInGroup() {
		return indexInGroup;
	}

	public void setIndexInGroup(int indexInGroup) {
		this.indexInGroup = indexInGroup;
	}

	public int getDisplaySwitch() {
		return displaySwitch;
	}

	public void setDisplaySwitch(int displaySwitch) {
		this.displaySwitch = displaySwitch;
	}

	public int getInstSwitch() {
		return instSwitch;
	}

	public void setInstSwitch(int instSwitch) {
		this.instSwitch = instSwitch;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
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

	public String getRefNavigationGroupUUID() {
		return refNavigationGroupUUID;
	}

	public void setRefNavigationGroupUUID(String refNavigationGroupUUID) {
		this.refNavigationGroupUUID = refNavigationGroupUUID;
	}

	public String getElementTitle() {
		return elementTitle;
	}

	public void setElementTitle(String elementTitle) {
		this.elementTitle = elementTitle;
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

	public String getTabs() {
		return tabs;
	}

	public void setTabs(String tabs) {
		this.tabs = tabs;
	}

	public String getTargetPageLink() {
		return targetPageLink;
	}

	public void setTargetPageLink(String targetPageLink) {
		this.targetPageLink = targetPageLink;
	}

}
