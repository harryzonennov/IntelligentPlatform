package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;

/**
 * transSite UI Model
 ** 
 * @author
 * @date Mon Aug 19 23:52:19 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class OrganizationSearchModel extends AccountSearchModel {

	public static final String ID_MAIN_CONTACT = "mainContact";

	public static final String NODEINST_ID_PARENTORG = "parentOrg";

	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "name", nodeName = Employee.NODENAME, seName = Employee.SENAME, nodeInstID = ID_MAIN_CONTACT)
	protected String contactPerson;

	@BSearchFieldConfig(fieldName = "telephone", nodeName = Organization.NODENAME, seName = Organization.SENAME,
			nodeInstID =	Organization.SENAME)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "cityName", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String cityName;
	
	@BSearchFieldConfig(fieldName = "refOrganizationFunction", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "OrganizationSearch_organizationFunction", valueFieldName = "")
	protected String refOrganizationFunction;
	
	@BSearchFieldConfig(fieldName = "parentOrganizationUUID", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String parentOrganizationUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected String parentOrganizationId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected String parentOrganizationName;

	@BSearchFieldConfig(fieldName = "fax", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected String fax;

	@BSearchFieldConfig(fieldName = "postcode", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected String postcode;

	@BSearchFieldConfig(fieldName = "email", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected String email;

	@BSearchFieldConfig(fieldName = "address", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected String address;

	@BSearchFieldConfig(fieldName = "organizationFunction", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = NODEINST_ID_PARENTORG)
	protected int organizationFunction;
	

	/**
	 * This attribute is just for get the page on UI, must be cleared before
	 * each search
	 */
	protected int currentPage;

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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String getTelephone() {
		return telephone;
	}

	@Override
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}	

	public String getRefOrganizationFunction() {
		return refOrganizationFunction;
	}

	public void setRefOrganizationFunction(String refOrganizationFunction) {
		this.refOrganizationFunction = refOrganizationFunction;
	}

	public String getParentOrganizationUUID() {
		return parentOrganizationUUID;
	}

	public void setParentOrganizationUUID(String parentOrganizationUUID) {
		this.parentOrganizationUUID = parentOrganizationUUID;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getParentOrganizationName() {
		return parentOrganizationName;
	}

	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	public int getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(int organizationFunction) {
		this.organizationFunction = organizationFunction;
	}
}
