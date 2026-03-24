package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.Organization;

/**
 * organizationArea UI Model
 ** 
 * @author
 * @date Mon Dec 02 14:00:30 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */


@Component
public class LogonUserOrgSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = LogonUserOrgReference.NODENAME, 
			seName = LogonUserOrgReference.SENAME, nodeInstID = LogonUserOrgReference.NODENAME)
	protected String id;
	
	@BSearchFieldConfig(fieldName = "parentNodeUUID", nodeName = LogonUserOrgReference.NODENAME, 
			seName = LogonUserOrgReference.SENAME, nodeInstID = LogonUserOrgReference.NODENAME)
	protected String parentNodeUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = LogonUserOrgReference.NODENAME, 
			seName = LogonUserOrgReference.SENAME, nodeInstID = LogonUserOrgReference.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = Organization.NODENAME, 
			seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = Organization.NODENAME, 
			seName = Organization.SENAME, nodeInstID = Organization.SENAME)
	protected String organizationName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
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

	
}
