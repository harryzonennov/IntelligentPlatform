package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.Role;

/**
 * role UI Model
 ** 
 * @author
 * @date Thu Nov 07 14:06:27 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class RoleSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = Role.NODENAME, seName = Role.SENAME, nodeInstID = Role.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = Role.NODENAME, seName = Role.SENAME, nodeInstID = Role.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "note", nodeName = Role.NODENAME, seName = Role.SENAME, nodeInstID = Role.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "name", nodeName = AuthorizationObject.NODENAME,
			seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String authorizationObjectName;

	@BSearchFieldConfig(fieldName = "id", nodeName = AuthorizationObject.NODENAME,
			seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String authorizationObjectId;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAuthorizationObjectName() {
		return authorizationObjectName;
	}

	public void setAuthorizationObjectName(String authorizationObjectName) {
		this.authorizationObjectName = authorizationObjectName;
	}

	public String getAuthorizationObjectId() {
		return authorizationObjectId;
	}

	public void setAuthorizationObjectId(String authorizationObjectId) {
		this.authorizationObjectId = authorizationObjectId;
	}
}
