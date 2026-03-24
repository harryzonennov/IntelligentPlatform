package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;

@Component
public class RoleAuthorizationObjectSearchModel extends SEUIComModel {
	

	@BSearchFieldConfig(fieldName = "uuid", nodeName = RoleAuthorization.NODENAME, seName = RoleAuthorization.SENAME, nodeInstID = RoleAuthorization.NODENAME)
	protected String uuid;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = RoleAuthorization.NODENAME, seName = RoleAuthorization.SENAME, nodeInstID = RoleAuthorization.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "id",nodeName = Role.NODENAME, seName = Role.SENAME, nodeInstID = Role.SENAME)
	protected String roleID;

	@BSearchFieldConfig(fieldName = "name",nodeName = Role.NODENAME, seName = Role.SENAME, nodeInstID = Role.SENAME)
	protected String roleName;
	
	@BSearchFieldConfig(fieldName = "uuid",nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String aoUUID;
	
	@BSearchFieldConfig(fieldName = "id",nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String aoID;
	
	@BSearchFieldConfig(fieldName = "name",nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String aoName;
	
	@BSearchFieldConfig(fieldName = "authorizationObjectType",nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected int authorizationObjectType;
	
	protected int currentPage;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAoUUID() {
		return aoUUID;
	}

	public void setAoUUID(String aoUUID) {
		this.aoUUID = aoUUID;
	}

	public String getAoID() {
		return aoID;
	}

	public void setAoID(String aoID) {
		this.aoID = aoID;
	}

	public String getAoName() {
		return aoName;
	}

	public void setAoName(String aoName) {
		this.aoName = aoName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getAuthorizationObjectType() {
		return authorizationObjectType;
	}

	public void setAuthorizationObjectType(int authorizationObjectType) {
		this.authorizationObjectType = authorizationObjectType;
	}

}
