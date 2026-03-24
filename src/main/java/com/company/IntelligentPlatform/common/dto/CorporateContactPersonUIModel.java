package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * IndividualCustomer UI Model
 ** 
 * @author
 * @date Mon Jul 01 16:04:38 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class CorporateContactPersonUIModel extends SEUIComModel {

	protected String baseUUID;

	@ISEDropDownResourceMapping(resouceMapping = "CorporateContactPerson_contactRole", valueFieldName = "contactRoleValue")
	protected int contactRole;

	protected String contactRoleNote;

	protected String contactRoleValue;

	protected String telephone;

	protected String mobile;

	@ISEDropDownResourceMapping(resouceMapping = "IndividualCustomer_customerType", valueFieldName = "customerTypeValue")
	protected int customerType;

	protected String customerTypeValue;

	protected int contactPosition;
	
	protected String contactPositionNote;
	
	protected String contactPositionValue;

	protected String cityName;
	
	protected String countryName;

	protected String stateName;

	protected String refCityUUID;

	protected String townZone;

	protected String subArea;

	protected String streetName;

	protected String houseNumber;

	protected String address;
	
	protected String weixinId;
	
	protected String email;

	/**
	 * Reference to target "individual customer" entity
	 */
	protected String refUUID;
	
	protected String refCorporateCustomerId;
	
	protected String refCorporateCustomerName;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getContactRole() {
		return contactRole;
	}

	public void setContactRole(int contactRole) {
		this.contactRole = contactRole;
	}

	public String getContactRoleValue() {
		return contactRoleValue;
	}

	public void setContactRoleValue(String contactRoleValue) {
		this.contactRoleValue = contactRoleValue;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTypeValue() {
		return customerTypeValue;
	}

	public void setCustomerTypeValue(String customerTypeValue) {
		this.customerTypeValue = customerTypeValue;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getContactRoleNote() {
		return contactRoleNote;
	}

	public void setContactRoleNote(String contactRoleNote) {
		this.contactRoleNote = contactRoleNote;
	}

	public int getContactPosition() {
		return contactPosition;
	}

	public void setContactPosition(int contactPosition) {
		this.contactPosition = contactPosition;
	}

	public String getContactPositionValue() {
		return contactPositionValue;
	}

	public void setContactPositionValue(String contactPositionValue) {
		this.contactPositionValue = contactPositionValue;
	}

	public String getContactPositionNote() {
		return contactPositionNote;
	}

	public void setContactPositionNote(String contactPositionNote) {
		this.contactPositionNote = contactPositionNote;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getRefCorporateCustomerId() {
		return refCorporateCustomerId;
	}

	public void setRefCorporateCustomerId(String refCorporateCustomerId) {
		this.refCorporateCustomerId = refCorporateCustomerId;
	}

	public String getRefCorporateCustomerName() {
		return refCorporateCustomerName;
	}

	public void setRefCorporateCustomerName(String refCorporateCustomerName) {
		this.refCorporateCustomerName = refCorporateCustomerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
