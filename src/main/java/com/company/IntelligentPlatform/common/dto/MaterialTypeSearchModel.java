package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.MaterialActionLog;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;

/**
 * MaterialType UI Model
 ** 
 * @author
 * @date Wed Aug 12 14:12:25 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class MaterialTypeSearchModel extends SEUIComModel {

	@BSearchGroupConfig(groupInstId = MaterialType.SENAME)
	protected ServiceDocSearchHeaderModel headerModel;

	@BSearchGroupConfig(groupInstId = MaterialType.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_SUBMIT)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ACTIVE)
	protected DocActionNodeSearchModel activeBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_REINIT)
	protected DocActionNodeSearchModel reInitBy;

	@BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ARCHIVE)
	protected DocActionNodeSearchModel archivedBy;
	
	@BSearchFieldConfig(fieldName = "status", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialType.SENAME)
	protected int status;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialTypeUIModel.SEC_PARENTTYPE)
	protected String parentTypeId;
	
	@BSearchFieldConfig(fieldName = "parentTypeUUID", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialType.SENAME)
	protected String parentTypeUUID;
	
	@BSearchFieldConfig(fieldName = "rootTypeUUID", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialType.SENAME)
	protected String rootTypeUUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialTypeUIModel.SEC_PARENTTYPE)
	protected String parentTypeName;

	@BSearchFieldConfig(fieldName = "id", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialTypeUIModel.SEC_ROOTTYPE)
	protected String rootTypeId;	
	
	@BSearchFieldConfig(fieldName = "name", nodeName = MaterialType.NODENAME, seName = MaterialType.SENAME, nodeInstID = MaterialTypeUIModel.SEC_ROOTTYPE)
	protected String rootTypeName;

	public ServiceDocSearchHeaderModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
		this.headerModel = headerModel;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}

	public DocActionNodeSearchModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public DocActionNodeSearchModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public DocActionNodeSearchModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(DocActionNodeSearchModel activeBy) {
		this.activeBy = activeBy;
	}

	public DocActionNodeSearchModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(DocActionNodeSearchModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public DocActionNodeSearchModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(DocActionNodeSearchModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getParentTypeUUID() {
		return parentTypeUUID;
	}

	public void setParentTypeUUID(String parentTypeUUID) {
		this.parentTypeUUID = parentTypeUUID;
	}

	public String getRootTypeUUID() {
		return rootTypeUUID;
	}

	public void setRootTypeUUID(String rootTypeUUID) {
		this.rootTypeUUID = rootTypeUUID;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public String getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getRootTypeId() {
		return rootTypeId;
	}

	public void setRootTypeId(String rootTypeId) {
		this.rootTypeId = rootTypeId;
	}

	public String getRootTypeName() {
		return rootTypeName;
	}

	public void setRootTypeName(String rootTypeName) {
		this.rootTypeName = rootTypeName;
	}

}
