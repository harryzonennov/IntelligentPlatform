package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountUIModel;

/**
 * TransSite UI Model
 ** 
 * @author
 * @date Mon Feb 11 22:36:50 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class OrganizationUIModel extends AccountUIModel {

	protected String contactPerson;
	
	@ISEDropDownResourceMapping(resouceMapping = "Organization_organizationFunction", valueFieldName = "organizationFunctionValue")
	protected int organizationFunction;
	
	@ISEDropDownResourceMapping(resouceMapping = "Organization_organizationFunction", valueFieldName = "organizationFunctionValue")
	protected String refOrganizationFunction;
	
	protected String organizationFunctionValue;

	protected String refFinOrgUUID;
	
	protected String refFinOrgId;
	
	protected String refFinOrgName;

	protected String contactMobileNumber;

	protected String mainContactUUID;

	protected String contactEmployeeTelephone;

	protected String refCashierUUID;

	protected String refCashierName;

	protected String refCashierId;

	protected String fullName;

	protected String refAccountantUUID;

	protected String refAccountantName;

	protected String refAccountantId;

	protected String baseCityUUID;
	
	protected String parentOrganizationId;
	
	protected String parentOrganizationUUID;
	
	protected String parentOrganizationName;
	
	@ISEDropDownResourceMapping(resouceMapping = "Organization_organizationFunction", valueFieldName = "parentOrganizationFunctionValue")
	protected String parentOrganizationFunction;
	
	protected String parentOrganizationFunctionValue;
	
	protected String tags;

    protected String taxNumber;
	
	protected String bankAccount;
	
	protected String depositBank;

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(int organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

	public String getOrganizationFunctionValue() {
		return organizationFunctionValue;
	}

	public void setOrganizationFunctionValue(String organizationFunctionValue) {
		this.organizationFunctionValue = organizationFunctionValue;
	}

	public String getContactMobileNumber() {
		return contactMobileNumber;
	}

	public void setContactMobileNumber(String contactMobileNumber) {
		this.contactMobileNumber = contactMobileNumber;
	}

	public String getContactEmployeeTelephone() {
		return contactEmployeeTelephone;
	}

	public void setContactEmployeeTelephone(String contactEmployeeTelephone) {
		this.contactEmployeeTelephone = contactEmployeeTelephone;
	}
	
	public String getRefCashierUUID() {
		return refCashierUUID;
	}

	public void setRefCashierUUID(String refCashierUUID) {
		this.refCashierUUID = refCashierUUID;
	}

	public String getRefCashierName() {
		return refCashierName;
	}

	public void setRefCashierName(String refCashierName) {
		this.refCashierName = refCashierName;
	}

	public String getRefAccountantUUID() {
		return refAccountantUUID;
	}

	public void setRefAccountantUUID(String refAccountantUUID) {
		this.refAccountantUUID = refAccountantUUID;
	}

	public String getRefAccountantName() {
		return refAccountantName;
	}

	public void setRefAccountantName(String refAccountantName) {
		this.refAccountantName = refAccountantName;
	}

	public String getMainContactUUID() {
		return mainContactUUID;
	}

	public void setMainContactUUID(String mainContactUUID) {
		this.mainContactUUID = mainContactUUID;
	}

	public String getBaseCityUUID() {
		return baseCityUUID;
	}

	public void setBaseCityUUID(String baseCityUUID) {
		this.baseCityUUID = baseCityUUID;
	}

	public String getRefCashierId() {
		return refCashierId;
	}

	public void setRefCashierId(String refCashierId) {
		this.refCashierId = refCashierId;
	}

	public String getRefAccountantId() {
		return refAccountantId;
	}

	public void setRefAccountantId(String refAccountantId) {
		this.refAccountantId = refAccountantId;
	}

	public String getRefFinOrgUUID() {
		return refFinOrgUUID;
	}

	public void setRefFinOrgUUID(String refFinOrgUUID) {
		this.refFinOrgUUID = refFinOrgUUID;
	}

	public String getRefFinOrgId() {
		return refFinOrgId;
	}

	public void setRefFinOrgId(String refFinOrgId) {
		this.refFinOrgId = refFinOrgId;
	}

	public String getRefFinOrgName() {
		return refFinOrgName;
	}

	public void setRefFinOrgName(String refFinOrgName) {
		this.refFinOrgName = refFinOrgName;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getParentOrganizationUUID() {
		return parentOrganizationUUID;
	}

	public void setParentOrganizationUUID(String parentOrganizationUUID) {
		this.parentOrganizationUUID = parentOrganizationUUID;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

	public String getParentOrganizationFunction() {
		return parentOrganizationFunction;
	}

	public void setParentOrganizationFunction(String parentOrganizationFunction) {
		this.parentOrganizationFunction = parentOrganizationFunction;
	}

	public String getParentOrganizationFunctionValue() {
		return parentOrganizationFunctionValue;
	}

	public void setParentOrganizationFunctionValue(
			String parentOrganizationFunctionValue) {
		this.parentOrganizationFunctionValue = parentOrganizationFunctionValue;
	}

	public String getRefOrganizationFunction() {
		return refOrganizationFunction;
	}

	public void setRefOrganizationFunction(String refOrganizationFunction) {
		this.refOrganizationFunction = refOrganizationFunction;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

}
