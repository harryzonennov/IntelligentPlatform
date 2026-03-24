package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MapSettingModel extends SEUIComModel {
	
	public static final int BASE_OBJTYPE_TRANSSITE = 1;
	
	public static final int BASE_OBJTYPE_WAREHOUSE = 2;
	
	public static final int BASE_OBJTYPE_HOSTCOMPANY = 3;
	
	public static final int BASE_OBJTYPE_ORGANIZATION = 4;
	
	public static final int BASE_OBJTYPE_CUSTOMER = 5;
	
	public static final int BASE_OBJTYPE_TRANSITPARTNER = 6;
	
	public static final String VIEW_NAME = "MapSetting";
	
	public static final String DEFAULT_LATITUDE = "103.56";
	
	public static final String DEFAULT_LONGITUDE = "30.35";
	
	protected int baseObjectType;
	
	protected String baseUUID;
	
	protected String addressOnMap;
	
	protected String  longitude; // jing du
	
	protected String latitude; // wei du

	public int getBaseObjectType() {
		return baseObjectType;
	}

	public void setBaseObjectType(int baseObjectType) {
		this.baseObjectType = baseObjectType;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getAddressOnMap() {
		return addressOnMap;
	}

	public void setAddressOnMap(String addressOnMap) {
		this.addressOnMap = addressOnMap;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
