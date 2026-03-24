package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.ISQLSepcifyAttribute;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;

public class PurchaseOrderParty extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseOrderParty;

	public static final String SENAME = PurchaseOrder.SENAME;

	public static final int ROLE_PURCHASE_TO_PARTY = 1;

	public static final int ROLE_PURCHASE_FROM_PARTY = 2;

	protected int partyRole;

	protected String postcode;

	protected String cityName;

	protected String refCityUUID;

	protected String townZone;

	protected String subArea;

	protected String streetName;

	protected String houseNumber;

	protected String contactPerson;

	protected String contactMobile;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String address;

	protected int customerType;

	public PurchaseOrderParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public int getPartyRole() {
		return partyRole;
	}

	public void setPartyRole(int partyRole) {
		this.partyRole = partyRole;
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

}
