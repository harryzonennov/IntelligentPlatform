package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountUIModel;

/**
 * IndividualCustomer UI Model
 ** 
 * @author
 * @date Mon Jul 01 16:04:38 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class IndividualCustomerUIModel extends AccountUIModel {
	
	protected String baseUUID;

	protected String mobile;
	
	@ISEDropDownResourceMapping(resouceMapping = "IndividualCustomer_customerType", valueFieldName = "customerTypeValue")
	protected int customerType;
	
	protected String customerTypeValue;
	
	protected String qqNumber;
	
	protected String weiboID;
	
	protected String weiXinID;
	
	protected String faceBookID;
	
	protected String regularTypeValue;

	protected String baseCityUUID;
	
	@ISEDropDownResourceMapping(resouceMapping = "CorporateContactPerson_contactRole", valueFieldName = "contactRoleValue")
	protected int contactRole;
	
	protected String contactRoleValue;
	
	protected String contactRoleNote;
	
	@ISEDropDownResourceMapping(resouceMapping = "CorporateContactPerson_contactPosition", valueFieldName = "contactPositionValue")
	protected int contactPosition;
	
	protected String contactPositionValue;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
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

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
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

	public String getRegularTypeValue() {
		return regularTypeValue;
	}

	public void setRegularTypeValue(String regularTypeValue) {
		this.regularTypeValue = regularTypeValue;
	}

	public String getBaseCityUUID() {
		return baseCityUUID;
	}

	public void setBaseCityUUID(String baseCityUUID) {
		this.baseCityUUID = baseCityUUID;
	}

	public int getContactRole() {
		return contactRole;
	}

	public void setContactRole(int contactRole) {
		this.contactRole = contactRole;
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

	public String getContactRoleValue() {
		return contactRoleValue;
	}

	public void setContactRoleValue(String contactRoleValue) {
		this.contactRoleValue = contactRoleValue;
	}

	public String getContactPositionValue() {
		return contactPositionValue;
	}

	public void setContactPositionValue(String contactPositionValue) {
		this.contactPositionValue = contactPositionValue;
	}

}
