package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class NavigationGroupResource extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.NavigationGroupResource;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	protected String defStartElementID;

	protected int instSwitch;

	protected int displaySwitch;

	protected int displayIndex;
	
	protected String groupTitle;

	public NavigationGroupResource() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		instSwitch = NavigationElementResource.INSTSWITCH_ON;
		displaySwitch = NavigationElementResource.DISPLAY_SWITCH_ON;
	}

	public String getDefStartElementID() {
		return defStartElementID;
	}

	public void setDefStartElementID(String defStartElementID) {
		this.defStartElementID = defStartElementID;
	}

	public int getInstSwitch() {
		return instSwitch;
	}

	public void setInstSwitch(int instSwitch) {
		this.instSwitch = instSwitch;
	}

	public int getDisplaySwitch() {
		return displaySwitch;
	}

	public void setDisplaySwitch(int displaySwitch) {
		this.displaySwitch = displaySwitch;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

}
