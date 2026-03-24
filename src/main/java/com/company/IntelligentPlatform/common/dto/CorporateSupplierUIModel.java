package com.company.IntelligentPlatform.common.dto;

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
public class CorporateSupplierUIModel extends AccountUIModel {

	protected String baseCityUUID;

	@ISEDropDownResourceMapping(resouceMapping = "CorporateCustomer_customerType", valueFieldName = "customerTypeValue")
	protected int customerType;

	protected String customerTypeValue;
	
	protected int supplierLevel;
	
	protected String supplierLevelValue;

	protected String weiboID;

	protected String weiXinID;

	protected String faceBookID;

	@ISEDropDownResourceMapping(resouceMapping = "CorporateCustomer_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	protected String resUserUUID;

	protected String resUserName;

	protected String resUserID;

	protected String resOrgName;

	protected String resOrgID;
	
	protected String tags;
	
    protected String taxNumber;
	
	protected String bankAccount;
	
	protected String depositBank;
	public int getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(int supplierLevel) {
		this.supplierLevel = supplierLevel;
	}

	public String getSupplierLevelValue() {
		return supplierLevelValue;
	}

	public void setSupplierLevelValue(String supplierLevelValue) {
		this.supplierLevelValue = supplierLevelValue;
	}

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

	public String getResUserID() {
		return resUserID;
	}

	public void setResUserID(String resUserID) {
		this.resUserID = resUserID;
	}

	public String getResOrgID() {
		return resOrgID;
	}

	public void setResOrgID(String resOrgID) {
		this.resOrgID = resOrgID;
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
