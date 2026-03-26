package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;

import static com.company.IntelligentPlatform.common.dto.DocInvolvePartySearchModel.NODE_INST_PARTYCONTACT;
import static com.company.IntelligentPlatform.common.dto.DocInvolvePartySearchModel.NODE_INST_TARGETPARTY;

/**
 * Account UI Model
 ** 
 * @author
 * @date Tue Dec 29 10:42:22 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

//TODO think about merge with DocInvolvePartySearchModel: there are lots's of partyId, partyName.... there.
@Component
public class AccountSearchSubModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid")
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String name;

	@BSearchFieldConfig(fieldName = "telephone", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "mobile", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String mobile;

	@BSearchFieldConfig(fieldName = "address", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String address;

	@BSearchFieldConfig(fieldName = "accountType", subNodeInstId = NODE_INST_TARGETPARTY)
	protected int accountType;

	@BSearchFieldConfig(fieldName = "countryName", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String countryName;

	@BSearchFieldConfig(fieldName = "stateName", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String stateName;

	@BSearchFieldConfig(fieldName = "cityName", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String cityName;

	@BSearchFieldConfig(fieldName = "status", subNodeInstId = NODE_INST_TARGETPARTY)
	protected int status;

	@BSearchFieldConfig(fieldName = "townZone", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String townZone;

	@BSearchFieldConfig(fieldName = "subArea", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String subArea;

	@BSearchFieldConfig(fieldName = "streetName", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String streetName;

	@BSearchFieldConfig(fieldName = "houseNumber", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String houseNumber;

	@BSearchFieldConfig(fieldName = "weiXinID", subNodeInstId = NODE_INST_TARGETPARTY)
	protected String weiXinID;

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

	@BSearchFieldConfig(fieldName = "name", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String contactName;

	@BSearchFieldConfig(fieldName = "id", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String contactId;

	@BSearchFieldConfig(fieldName = "mobile", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String contactMobile;

	@BSearchFieldConfig(fieldName = "qqNumber", subNodeInstId = NODE_INST_PARTYCONTACT)
	protected String qqNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getWeiXinID() {
		return weiXinID;
	}

	public void setWeiXinID(String weiXinID) {
		this.weiXinID = weiXinID;
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

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
