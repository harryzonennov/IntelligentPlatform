package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;
import com.company.IntelligentPlatform.common.dto.AccountSearchSubModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;

@Component
public class EmployeeSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = CorporateCustomer.SENAME)
	AccountSearchSubModel headerModel;

	@BSearchGroupConfig(groupInstId = CorporateCustomer.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchFieldConfig(fieldName = "identification", nodeName = Employee.NODENAME, seName = Employee.SENAME, nodeInstID = Employee.SENAME)
	protected String identification;
	
	@BSearchFieldConfig(fieldName = "gender", nodeName = Employee.NODENAME, seName = Employee.SENAME, nodeInstID = Employee.SENAME)
	protected int gender;
	
	protected int ageLow;
	
	protected int ageHigh;

	@BSearchFieldConfig(fieldName = "birthDate", nodeName = Employee.NODENAME, seName = Employee.SENAME, 
			nodeInstID = Employee.SENAME, fieldType=BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date birthDateLow;
	
	@BSearchFieldConfig(fieldName = "birthDate", nodeName = Employee.NODENAME, seName = Employee.SENAME, 
			nodeInstID = Employee.SENAME, fieldType=BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date birthDateHigh;
	
	@BSearchFieldConfig(fieldName = "birthDate", nodeName = Employee.NODENAME, seName = Employee.SENAME, 
			nodeInstID = Employee.SENAME, fieldType=BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date boardDateLow;
	
	@BSearchFieldConfig(fieldName = "birthDate", nodeName = Employee.NODENAME, seName = Employee.SENAME, 
			nodeInstID = Employee.SENAME, fieldType=BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date boardDateHigh;

	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationName;

	@BSearchFieldConfig(fieldName = "workRole", nodeName = Employee.NODENAME, seName = Employee.SENAME, nodeInstID = Employee.SENAME)
	protected int workRole;
	
	@BSearchFieldConfig(fieldName = "jobLevel", nodeName = Employee.NODENAME, seName = Employee.SENAME, nodeInstID = Employee.SENAME)
	protected int jobLevel;

	@BSearchFieldConfig(fieldName = "operateType", nodeName = Employee.NODENAME, seName = Employee.SENAME, nodeInstID = Employee.SENAME)
	protected int operateType;

	@BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = "LogonUser")
	protected String logonUserId;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = Role.NODENAME, seName = Role.SENAME, nodeInstID = Role.SENAME)
	protected String roleId;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
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

	public int getWorkRole() {
		return workRole;
	}

	public void setWorkRole(int workRole) {
		this.workRole = workRole;
	}

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getLogonUserId() {
		return logonUserId;
	}

	public void setLogonUserId(String logonUserId) {
		this.logonUserId = logonUserId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getAgeLow() {
		return ageLow;
	}

	public void setAgeLow(int ageLow) {
		this.ageLow = ageLow;
	}

	public int getAgeHigh() {
		return ageHigh;
	}

	public void setAgeHigh(int ageHigh) {
		this.ageHigh = ageHigh;
	}

	public Date getBirthDateLow() {
		return birthDateLow;
	}

	public void setBirthDateLow(Date birthDateLow) {
		this.birthDateLow = birthDateLow;
	}

	public Date getBirthDateHigh() {
		return birthDateHigh;
	}

	public void setBirthDateHigh(Date birthDateHigh) {
		this.birthDateHigh = birthDateHigh;
	}

	public Date getBoardDateLow() {
		return boardDateLow;
	}

	public void setBoardDateLow(Date boardDateLow) {
		this.boardDateLow = boardDateLow;
	}

	public Date getBoardDateHigh() {
		return boardDateHigh;
	}

	public void setBoardDateHigh(Date boardDateHigh) {
		this.boardDateHigh = boardDateHigh;
	}

	public AccountSearchSubModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(AccountSearchSubModel headerModel) {
		this.headerModel = headerModel;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}
}
