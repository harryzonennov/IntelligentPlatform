package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.Organization;

public class LogonUserOrganizationUIModel extends SEUIComModel {

	protected String password;

	@ISEDropDownResourceMapping(resouceMapping = "LogonUser_userType", valueFieldName = "userTypeStr")
	protected int userType;

	protected String userTypeStr;

	@ISEDropDownResourceMapping(resouceMapping = "LogonUserOrganization_orgRole", valueFieldName = "userTypeStr")
	protected int workRole;

	protected String workRoleValue;

	protected String refUUID;
	
	@ISEUIModelMapping(fieldName = "refSEName", seName = LogonUserOrgReference.SENAME, nodeName = LogonUserOrgReference.NODENAME, 
			nodeInstID = LogonUserOrgReference.NODENAME, showOnEditor = false)
	protected String refSEName;
	
	protected String refNodeName;

	protected String organizationId;

	protected String organizationName;
	
	protected int organizationFunction;
	
	protected String organizationFunctionValue;

	protected String organType;

	@ISEUIModelMapping(fieldName = "address", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String address;

	@ISEUIModelMapping(fieldName = "postcode", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String postcode;

	@ISEUIModelMapping(fieldName = "cityName", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String cityName;

	@ISEUIModelMapping(fieldName = "refCityUUID", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String refCityUUID;

	@ISEUIModelMapping(fieldName = "townZone", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String townZone;

	@ISEUIModelMapping(fieldName = "subArea", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String subArea;

	@ISEUIModelMapping(fieldName = "streetName", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String streetName;

	@ISEUIModelMapping(fieldName = "houseNumber", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String houseNumber;

	@ISEUIModelMapping(fieldName = "contactMobileNumber", seName = Organization.SENAME, nodeName = Organization.NODENAME, nodeInstID = Organization.SENAME)
	protected String contactMobileNumber;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserTypeStr() {
		return userTypeStr;
	}

	public void setUserTypeStr(String userTypeStr) {
		this.userTypeStr = userTypeStr;
	}

	public int getWorkRole() {
		return workRole;
	}

	public void setWorkRole(int workRole) {
		this.workRole = workRole;
	}

	public String getWorkRoleValue() {
		return workRoleValue;
	}

	public void setWorkRoleValue(String workRoleValue) {
		this.workRoleValue = workRoleValue;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getContactMobileNumber() {
		return contactMobileNumber;
	}

	public void setContactMobileNumber(String contactMobileNumber) {
		this.contactMobileNumber = contactMobileNumber;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

}
