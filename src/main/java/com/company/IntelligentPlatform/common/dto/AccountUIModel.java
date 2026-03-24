package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.Account;

public class AccountUIModel extends SEUIComModel {
	
	protected String address;
	
	protected String telephone;
	
	protected int accountType;
	
	protected String accountTypeValue;
	
	protected String fax;
	
	protected String email;
	
	protected String webPage;
	
	protected String postcode;
	
    protected String countryName;
	
	protected String stateName;
	
	protected String cityName;
	
	protected String refCityUUID;
	
	protected String townZone;
	
	protected String subArea;
	
	protected String streetName;

	protected String houseNumber;
	
	protected int regularType;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getAccountTypeValue() {
		return accountTypeValue;
	}

	public void setAccountTypeValue(String accountTypeValue) {
		this.accountTypeValue = accountTypeValue;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRefCityUUID() {
		return refCityUUID;
	}

	public void setRefCityUUID(String refCityUUID) {
		this.refCityUUID = refCityUUID;
	}

	public String getTownZone() {
		return townZone;
	}

	public void setTownZone(String townZone) {
		this.townZone = townZone;
	}

	public String getSubArea() {
		return subArea;
	}

	public void setSubArea(String subArea) {
		this.subArea = subArea;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public int getRegularType() {
		return regularType;
	}

	public void setRegularType(int regularType) {
		this.regularType = regularType;
	}


}
