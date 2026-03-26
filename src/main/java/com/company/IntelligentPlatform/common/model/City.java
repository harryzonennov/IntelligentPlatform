package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "City", schema = "platform")
public class City extends Location {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.City;

	public static final int CITY_LEVEL_1 = 1;

	public static final int CITY_LEVEL_2 = 2;

	public static final int CITY_LEVEL_3 = 3;

	protected String teleAreaCode;

	protected String postcode;

	protected int cityLevel;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	public City() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		super.locationLevel = LOC_LEVEL_CITY;
	}

	public String getTeleAreaCode() {
		return teleAreaCode;
	}

	public void setTeleAreaCode(String teleAreaCode) {
		this.teleAreaCode = teleAreaCode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}

}
