package com.company.IntelligentPlatform.common.model;

import java.util.Date;

/**
 * Basic model class for document involved party node
 * @author Zhang,Hang
 *
 */
public class DocInvolveParty extends ReferenceNode {

	public static final int PARTY_ROLE_CUSTOMER = 1;

	public static final int PARTY_ROLE_SUPPLIER = 2;

	public static final int PARTY_ROLE_SALESORG = 3;

	public static final int PARTY_ROLE_PRODORG = 4;

	public static final int PARTY_ROLE_SUPPORTORG = 5;

	public static final int PARTY_ROLE_PURORG = 6;

	public static final String PARTY_NODEINST_PUR_SUPPLIER = "purchaseFromSupplier";
	
	public static final String PARTY_NODEINST_SOLD_CUSTOMER = "soldToCustomer";

	public static final String PARTY_NODEINST_SOLD_ORG = "soldFromOrg";

	public static final String PARTY_NODEINST_PROD_ORG = "productionOrg";

	public static final String PARTY_NODEINST_SUPPORT_ORG = "supportOrganization";

	public static final String PARTY_NODEINST_PUR_ORG = "purchaseToOrg";
	
	public static final String FIELD_PARTYROLE = "partyRole";
	
    protected int partyRole;
	
	protected String email;

	protected String telephone;

	protected String fax;
	
	protected String taxNumber;
	
	protected String bankAccount;

	protected String depositBank;
	
	protected String postcode;
		
	protected String cityName;
		
	protected String townZone;
		
	protected String subArea;
		
	protected String streetName;
		
	protected String houseNumber;
	
	protected String refContactUUID;
	
	protected String contactName;
	
	protected String contactId;
	
	protected String contactMobile;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String address;

	protected String refDocMatItemUUID;

	protected int refDocumentType;

	protected Date refDocumentDate;

	public int getPartyRole() {
		return partyRole;
	}

	public void setPartyRole(int partyRole) {
		this.partyRole = partyRole;
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

	public String getRefDocMatItemUUID() {
		return refDocMatItemUUID;
	}

	public void setRefDocMatItemUUID(String refDocMatItemUUID) {
		this.refDocMatItemUUID = refDocMatItemUUID;
	}

	public int getRefDocumentType() {
		return refDocumentType;
	}

	public void setRefDocumentType(int refDocumentType) {
		this.refDocumentType = refDocumentType;
	}

	public Date getRefDocumentDate() {
		return refDocumentDate;
	}

	public void setRefDocumentDate(Date refDocumentDate) {
		this.refDocumentDate = refDocumentDate;
	}
}
