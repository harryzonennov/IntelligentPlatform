package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class EmployeeServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = Employee.NODENAME, nodeInstId = Employee.SENAME)
	protected Employee employee;
	
	@IServiceModuleFieldConfig(nodeName = EmployeeAttachment.NODENAME , nodeInstId = EmployeeAttachment.NODENAME)
	protected List<ServiceEntityNode> employeeAttachmentList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = EmployeeOrgReference.NODENAME, nodeInstId = EmployeeOrgReference.NODENAME)
	protected List<EmployeeOrgServiceModel> employeeOrgRefList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = EmpLogonUserReference.NODENAME, nodeInstId = EmpLogonUserReference.NODENAME)
	protected List<EmpLogonUserServiceModel> empLogonUserList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId = EmployeeActionNode.NODEINST_ACTION_ACTIVE)
	protected EmployeeActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId = EmployeeActionNode.NODEINST_ACTION_APPROVE)
	protected EmployeeActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId = EmployeeActionNode.NODEINST_ACTION_SUBMIT)
	protected EmployeeActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId =
			EmployeeActionNode.NODEINST_ACTION_REINIT)
	protected EmployeeActionNode reInitBy;

	@IServiceModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId =
			EmployeeActionNode.NODEINST_ACTION_ARCHIVE)
	protected EmployeeActionNode archivedBy;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<ServiceEntityNode> getEmployeeAttachmentList() {
		return employeeAttachmentList;
	}

	public void setEmployeeAttachmentList(
			List<ServiceEntityNode> employeeAttachmentList) {
		this.employeeAttachmentList = employeeAttachmentList;
	}

	public List<EmployeeOrgServiceModel> getEmployeeOrgRefList() {
		return employeeOrgRefList;
	}

	public void setEmployeeOrgRefList(List<EmployeeOrgServiceModel> employeeOrgRefList) {
		this.employeeOrgRefList = employeeOrgRefList;
	}

	public EmployeeActionNode getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(EmployeeActionNode activeBy) {
		this.activeBy = activeBy;
	}

	public EmployeeActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(EmployeeActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public EmployeeActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(EmployeeActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public EmployeeActionNode getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(EmployeeActionNode reInitBy) {
		this.reInitBy = reInitBy;
	}

	public EmployeeActionNode getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(EmployeeActionNode archivedBy) {
		this.archivedBy = archivedBy;
	}

	public List<EmpLogonUserServiceModel> getEmpLogonUserList() {
		return empLogonUserList;
	}

	public void setEmpLogonUserList(List<EmpLogonUserServiceModel> empLogonUserList) {
		this.empLogonUserList = empLogonUserList;
	}
}
