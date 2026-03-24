package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

public class Location extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "Location";

	public static final int LOC_LEVEL_COUNTRY = 1;

	public static final int LOC_LEVEL_PROVINCE = 2;

	public static final int LOC_LEVEL_CITY = 3;

	protected int locationLevel;

	protected String parentLocationUUID;

	public Location() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public int getLocationLevel() {
		return locationLevel;
	}

	public void setLocationLevel(int locationLevel) {
		this.locationLevel = locationLevel;
	}

	public String getParentLocationUUID() {
		return parentLocationUUID;
	}

	public void setParentLocationUUID(String parentLocationUUID) {
		this.parentLocationUUID = parentLocationUUID;
	}

}
