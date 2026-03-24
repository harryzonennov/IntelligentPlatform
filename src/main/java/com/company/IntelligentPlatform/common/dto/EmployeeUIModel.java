package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.dto.AccountUIModel;

public class EmployeeUIModel extends AccountUIModel {

	protected String identification;

	protected String boardDate;
	
	protected String birthDate;

	protected String organizationId;
	
	protected String organizationName;
	
	protected String organizationUUID;
	
	protected int organizationType;

	protected int age;

	protected String mobile;

	@ISEDropDownResourceMapping(resouceMapping = "Employee_gender", valueFieldName = "genderValue")
	protected int gender;

	protected String genderValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "Employee_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;

	@ISEDropDownResourceMapping(resouceMapping = "Employee_workRole", valueFieldName = "workRoleValue")
	protected int workRole;

	protected String workRoleValue;

	@ISEDropDownResourceMapping(resouceMapping = "Employee_jobLevel", valueFieldName = "jobLevelValue")
	protected int jobLevel;

	protected String jobLevelValue;

	@ISEDropDownResourceMapping(resouceMapping = "Employee_operateType", valueFieldName = "operateTypeValue")
	protected int operateType;

	protected String operateTypeValue;

	protected String logonUserId;

	protected String logonUserUUID;

	protected String logonUserName;

	protected String logonRoleName;
	
	protected String driverLicenseNumber;

	protected int driverLicenseType;	

	protected int driverLicenseTypeValue;
	
	protected int employeeRole;
	
	protected String employeeRoleValue;
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationUUID() {
		return organizationUUID;
	}

	public void setOrganizationUUID(String organizationUUID) {
		this.organizationUUID = organizationUUID;
	}

	public int getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(int organizationType) {
		this.organizationType = organizationType;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
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

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getJobLevelValue() {
		return jobLevelValue;
	}

	public void setJobLevelValue(String jobLevelValue) {
		this.jobLevelValue = jobLevelValue;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getOperateTypeValue() {
		return operateTypeValue;
	}

	public void setOperateTypeValue(String operateTypeValue) {
		this.operateTypeValue = operateTypeValue;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getLogonUserId() {
		return logonUserId;
	}

	public void setLogonUserId(String logonUserId) {
		this.logonUserId = logonUserId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLogonUserUUID() {
		return logonUserUUID;
	}

	public void setLogonUserUUID(String logonUserUUID) {
		this.logonUserUUID = logonUserUUID;
	}

	public String getLogonUserName() {
		return logonUserName;
	}

	public void setLogonUserName(String logonUserName) {
		this.logonUserName = logonUserName;
	}

	public String getLogonRoleName() {
		return logonRoleName;
	}

	public void setLogonRoleName(String logonRoleName) {
		this.logonRoleName = logonRoleName;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public void setDriverLicenseNumber(String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public int getDriverLicenseType() {
		return driverLicenseType;
	}

	public void setDriverLicenseType(int driverLicenseType) {
		this.driverLicenseType = driverLicenseType;
	}

	public int getDriverLicenseTypeValue() {
		return driverLicenseTypeValue;
	}

	public void setDriverLicenseTypeValue(int driverLicenseTypeValue) {
		this.driverLicenseTypeValue = driverLicenseTypeValue;
	}

	public int getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(int employeeRole) {
		this.employeeRole = employeeRole;
	}

	public String getEmployeeRoleValue() {
		return employeeRoleValue;
	}

	public void setEmployeeRoleValue(String employeeRoleValue) {
		this.employeeRoleValue = employeeRoleValue;
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

}
