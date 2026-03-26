package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "Country", schema = "platform")
public class Country extends Location {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.Country;

	protected String countryCode;

	protected String languageCode;

	protected String countryTeleCode;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	public Country() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		super.locationLevel = LOC_LEVEL_COUNTRY;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getCountryTeleCode() {
		return countryTeleCode;
	}

	public void setCountryTeleCode(String countryTeleCode) {
		this.countryTeleCode = countryTeleCode;
	}

}
