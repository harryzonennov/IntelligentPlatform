package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;

public class DocInvolvePartySearchModel extends SEUIComModel {

	public static final String NODE_INST_TARGETPARTY = "targetParty";

	public static final String NODE_INST_PARTYCONTACT = "partyContact";

	@BSearchFieldConfig(fieldName = "partyRole")
	protected int partyRole;

	@BSearchFieldConfig(fieldName = "refUUID")
	protected String refUUID;

	@BSearchFieldConfig(fieldName = "refContactUUID")
	protected String refContactUUID;

	@BSearchFieldConfig(fieldName = "id", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String partyId;

	@BSearchFieldConfig(fieldName = "name", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String partyName;

	@BSearchFieldConfig(fieldName = "telephone", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "email", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String email;

	@BSearchFieldConfig(fieldName = "fax", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String fax;

	@BSearchFieldConfig(fieldName = "taxNumber", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String taxNumber;

	@BSearchFieldConfig(fieldName = "bankAccount", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String bankAccount;

	@BSearchFieldConfig(fieldName = "depositBank", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String depositBank;

	@BSearchFieldConfig(fieldName = "postcode", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String postcode;

	@BSearchFieldConfig(fieldName = "cityName", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String cityName;

	@BSearchFieldConfig(fieldName = "townZone", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String townZone;

	@BSearchFieldConfig(fieldName = "subArea", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String subArea;

	@BSearchFieldConfig(fieldName = "streetName", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String streetName;

	@BSearchFieldConfig(fieldName = "houseNumber", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String houseNumber;

	@BSearchFieldConfig(fieldName = "address", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String address;

	@BSearchFieldConfig(fieldName = "name", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String contactName;

	@BSearchFieldConfig(fieldName = "id", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String contactId;

	@BSearchFieldConfig(fieldName = "mobile", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String contactMobile;

	@BSearchFieldConfig(fieldName = "countryName", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String countryName;

	@BSearchFieldConfig(fieldName = "stateName", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String stateName;

	@BSearchFieldConfig(fieldName = "status", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected int status;

	public int getPartyRole() {
		return partyRole;
	}

	public void setPartyRole(int partyRole) {
		this.partyRole = partyRole;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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

	public String getRefContactUUID() {
		return refContactUUID;
	}

	public void setRefContactUUID(String refContactUUID) {
		this.refContactUUID = refContactUUID;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
