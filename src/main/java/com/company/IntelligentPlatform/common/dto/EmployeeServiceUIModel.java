package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.*;

@Component
public class EmployeeServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = Employee.NODENAME, nodeInstId = Employee.SENAME)
	protected EmployeeUIModel employeeUIModel;
	
    @IServiceUIModuleFieldConfig(nodeName = EmployeeAttachment.NODENAME, nodeInstId = EmployeeAttachment.NODENAME)
	protected List<EmployeeAttachmentUIModel> employeeAttachmentUIModelList = new ArrayList<>();

    @IServiceUIModuleFieldConfig(nodeName = EmployeeOrgReference.NODENAME, nodeInstId = EmployeeOrgReference.NODENAME)
	protected List<EmployeeOrgServiceUIModel> employeeOrgUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = EmpLogonUserReference.NODENAME, nodeInstId = EmpLogonUserReference.NODENAME)
	protected List<EmpLogonUserServiceUIModel> empLogonUserUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId = EmployeeActionNode.NODEINST_ACTION_ACTIVE)
	protected EmployeeActionNodeUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId =
			EmployeeActionNode.NODEINST_ACTION_APPROVE)
	protected EmployeeActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId =
			EmployeeActionNode.NODEINST_ACTION_SUBMIT)
	protected EmployeeActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId =
			EmployeeActionNode.NODEINST_ACTION_REINIT)
	protected EmployeeActionNodeUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = EmployeeActionNode.NODENAME, nodeInstId =
			EmployeeActionNode.NODEINST_ACTION_ARCHIVE)
	protected EmployeeActionNodeUIModel archivedBy;

    public EmployeeUIModel getEmployeeUIModel() {
		return employeeUIModel;
	}

	public void setEmployeeUIModel(EmployeeUIModel employeeUIModel) {
		this.employeeUIModel = employeeUIModel;
	}

	public List<EmployeeOrgServiceUIModel> getEmployeeOrgServiceUIModelList() {
		return employeeOrgUIModelList;
	}

	public void setEmployeeOrgServiceUIModelList(
			List<EmployeeOrgServiceUIModel> employeeOrgUIModelList) {
		this.employeeOrgUIModelList = employeeOrgUIModelList;
	}

	public List<EmployeeAttachmentUIModel> getEmployeeAttachmentUIModelList() {
		return employeeAttachmentUIModelList;
	}

	public void setEmployeeAttachmentUIModelList(
			List<EmployeeAttachmentUIModel> employeeAttachmentUIModelList) {
		this.employeeAttachmentUIModelList = employeeAttachmentUIModelList;
	}

	public List<EmployeeOrgServiceUIModel> getEmployeeOrgUIModelList() {
		return employeeOrgUIModelList;
	}

	public void setEmployeeOrgUIModelList(List<EmployeeOrgServiceUIModel> employeeOrgUIModelList) {
		this.employeeOrgUIModelList = employeeOrgUIModelList;
	}

	public EmployeeActionNodeUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(EmployeeActionNodeUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public EmployeeActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(EmployeeActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public EmployeeActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(EmployeeActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public EmployeeActionNodeUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(EmployeeActionNodeUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public EmployeeActionNodeUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(EmployeeActionNodeUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public List<EmpLogonUserServiceUIModel> getEmpLogonUserUIModelList() {
		return empLogonUserUIModelList;
	}

	public void setEmpLogonUserUIModelList(List<EmpLogonUserServiceUIModel> empLogonUserUIModelList) {
		this.empLogonUserUIModelList = empLogonUserUIModelList;
	}
}
