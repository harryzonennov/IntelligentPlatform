package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountUIModel;

/**
 * CorporateCustomer UI Model
 ** 
 * @author
 * @date Tue Aug 13 02:13:50 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class CorporateCustomerUIModel extends AccountUIModel {

	protected String baseCityUUID;

	@ISEDropDownResourceMapping(resouceMapping = "CorporateCustomer_customerType", valueFieldName = "customerTypeValue")
	protected int customerType;

	protected String customerTypeValue;
	
	protected int customerLevel;

	protected String fullName;
	
	protected String customerLevelValue;

	protected String weiboID;

	protected String weiXinID;

	protected String faceBookID;

	@ISEDropDownResourceMapping(resouceMapping = "CorporateCustomer_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	protected String retireReason;

	protected Date retireDate;

	protected String launchReason;

	protected Date launchDate;

	protected String resUserUUID;

	protected String resUserName;

	protected String resOrgName;

	protected String parentDistributorID;

	protected String parentDistributorName;

	protected String parentDistributorUUID;

	protected String refSalesAreaUUID;

	protected String refSalesAreaName;

	protected String refSalesAreaNameLabel;

	@ISEDropDownResourceMapping(resouceMapping = "CorporateCustomer_subDistributorType", valueFieldName = "customerTypeValue")
	protected int subDistributorType;

	protected String subDistributorTypeValue;

	protected boolean systemDefault;

	protected String tags;

	protected String taxNumber;

	protected String bankAccount;

	protected String depositBank;

	public String getBaseCityUUID() {
		return baseCityUUID;
	}

	public void setBaseCityUUID(String baseCityUUID) {
		this.baseCityUUID = baseCityUUID;
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

	public String getWeiboID() {
		return weiboID;
	}

	public void setWeiboID(String weiboID) {
		this.weiboID = weiboID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getWeiXinID() {
		return weiXinID;
	}

	public void setWeiXinID(String weiXinID) {
		this.weiXinID = weiXinID;
	}

	public String getFaceBookID() {
		return faceBookID;
	}

	public void setFaceBookID(String faceBookID) {
		this.faceBookID = faceBookID;
	}

	public int getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCustomerLevelValue() {
		return customerLevelValue;
	}

	public void setCustomerLevelValue(String customerLevelValue) {
		this.customerLevelValue = customerLevelValue;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getRetireReason() {
		return retireReason;
	}

	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	public String getLaunchReason() {
		return launchReason;
	}

	public void setLaunchReason(String launchReason) {
		this.launchReason = launchReason;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public String getResUserUUID() {
		return resUserUUID;
	}

	public void setResUserUUID(String resUserUUID) {
		this.resUserUUID = resUserUUID;
	}

	public String getResUserName() {
		return resUserName;
	}

	public void setResUserName(String resUserName) {
		this.resUserName = resUserName;
	}

	public String getResOrgName() {
		return resOrgName;
	}

	public void setResOrgName(String resOrgName) {
		this.resOrgName = resOrgName;
	}

	public String getParentDistributorID() {
		return parentDistributorID;
	}

	public void setParentDistributorID(String parentDistributorID) {
		this.parentDistributorID = parentDistributorID;
	}

	public String getParentDistributorName() {
		return parentDistributorName;
	}

	public void setParentDistributorName(String parentDistributorName) {
		this.parentDistributorName = parentDistributorName;
	}

	public String getParentDistributorUUID() {
		return parentDistributorUUID;
	}

	public void setParentDistributorUUID(String parentDistributorUUID) {
		this.parentDistributorUUID = parentDistributorUUID;
	}

	public String getRefSalesAreaUUID() {
		return refSalesAreaUUID;
	}

	public void setRefSalesAreaUUID(String refSalesAreaUUID) {
		this.refSalesAreaUUID = refSalesAreaUUID;
	}

	public String getRefSalesAreaName() {
		return refSalesAreaName;
	}

	public void setRefSalesAreaName(String refSalesAreaName) {
		this.refSalesAreaName = refSalesAreaName;
	}

	public String getRefSalesAreaNameLabel() {
		return refSalesAreaNameLabel;
	}

	public void setRefSalesAreaNameLabel(String refSalesAreaNameLabel) {
		this.refSalesAreaNameLabel = refSalesAreaNameLabel;
	}

	public int getSubDistributorType() {
		return subDistributorType;
	}

	public void setSubDistributorType(int subDistributorType) {
		this.subDistributorType = subDistributorType;
	}

	public String getSubDistributorTypeValue() {
		return subDistributorTypeValue;
	}

	public void setSubDistributorTypeValue(String subDistributorTypeValue) {
		this.subDistributorTypeValue = subDistributorTypeValue;
	}

	public boolean getSystemDefault() {
		return systemDefault;
	}

	public void setSystemDefault(boolean systemDefault) {
		this.systemDefault = systemDefault;
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
