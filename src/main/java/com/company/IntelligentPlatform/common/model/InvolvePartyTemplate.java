package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

import java.util.Date;

/**
 * Involve Party Super class for Material, registered product
 */
public class InvolvePartyTemplate extends DocInvolveParty {

	public static final int PARTY_ROLE_CUSTOMER = DocInvolveParty.PARTY_ROLE_CUSTOMER;

	public static final int PARTY_ROLE_SUPPLIER = DocInvolveParty.PARTY_ROLE_SUPPLIER;

	public static final int PARTY_ROLE_SALESORG = DocInvolveParty.PARTY_ROLE_SALESORG;

	public static final int PARTY_ROLE_PRODORG = DocInvolveParty.PARTY_ROLE_PRODORG;

	public static final int PARTY_ROLE_SUPPORTORG = DocInvolveParty.PARTY_ROLE_SUPPORTORG;

	public static final int PARTY_ROLE_PURORG = DocInvolveParty.PARTY_ROLE_PURORG;

	protected String refPartyId;

	protected String refPartyName;

	protected String refPartyTelephone;

	protected String refPartyAddress;

	protected String refPartyTaxNumber;

	protected String refPartyBankAccount;

	protected String refPartyEmail;

	protected String refPartyFax;

	protected String refPartyContactName;

	protected String refPartyContactId;

	protected String refPartyContactMobile;

	protected String refPartyContactWeixin;

	protected String refPartyContactEmail;

	protected String refPartyContactUUID;

	public String getRefPartyId() {
		return refPartyId;
	}

	public void setRefPartyId(String refPartyId) {
		this.refPartyId = refPartyId;
	}

	public String getRefPartyName() {
		return refPartyName;
	}

	public void setRefPartyName(String refPartyName) {
		this.refPartyName = refPartyName;
	}

	public String getRefPartyTelephone() {
		return refPartyTelephone;
	}

	public void setRefPartyTelephone(String refPartyTelephone) {
		this.refPartyTelephone = refPartyTelephone;
	}

	public String getRefPartyAddress() {
		return refPartyAddress;
	}

	public void setRefPartyAddress(String refPartyAddress) {
		this.refPartyAddress = refPartyAddress;
	}

	public String getRefPartyTaxNumber() {
		return refPartyTaxNumber;
	}

	public void setRefPartyTaxNumber(String refPartyTaxNumber) {
		this.refPartyTaxNumber = refPartyTaxNumber;
	}

	public String getRefPartyBankAccount() {
		return refPartyBankAccount;
	}

	public void setRefPartyBankAccount(String refPartyBankAccount) {
		this.refPartyBankAccount = refPartyBankAccount;
	}

	public String getRefPartyEmail() {
		return refPartyEmail;
	}

	public void setRefPartyEmail(String refPartyEmail) {
		this.refPartyEmail = refPartyEmail;
	}

	public String getRefPartyFax() {
		return refPartyFax;
	}

	public void setRefPartyFax(String refPartyFax) {
		this.refPartyFax = refPartyFax;
	}

	public String getRefPartyContactName() {
		return refPartyContactName;
	}

	public void setRefPartyContactName(String refPartyContactName) {
		this.refPartyContactName = refPartyContactName;
	}

	public String getRefPartyContactId() {
		return refPartyContactId;
	}

	public void setRefPartyContactId(String refPartyContactId) {
		this.refPartyContactId = refPartyContactId;
	}

	public String getRefPartyContactMobile() {
		return refPartyContactMobile;
	}

	public void setRefPartyContactMobile(String refPartyContactMobile) {
		this.refPartyContactMobile = refPartyContactMobile;
	}

	public String getRefPartyContactWeixin() {
		return refPartyContactWeixin;
	}

	public void setRefPartyContactWeixin(String refPartyContactWeixin) {
		this.refPartyContactWeixin = refPartyContactWeixin;
	}

	public String getRefPartyContactEmail() {
		return refPartyContactEmail;
	}

	public void setRefPartyContactEmail(String refPartyContactEmail) {
		this.refPartyContactEmail = refPartyContactEmail;
	}

	public String getRefPartyContactUUID() {
		return refPartyContactUUID;
	}

	public void setRefPartyContactUUID(String refPartyContactUUID) {
		this.refPartyContactUUID = refPartyContactUUID;
	}

}
