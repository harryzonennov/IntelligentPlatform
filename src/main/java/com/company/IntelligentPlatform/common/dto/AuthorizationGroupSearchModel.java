package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;

@Component
public class AuthorizationGroupSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "client", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String client;

	@BSearchFieldConfig(fieldName = "note", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "crossGroupProcessType", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected int crossGroupProcessType;

	@BSearchFieldConfig(fieldName = "innerProcessType", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected int innerProcessType;

	@BSearchFieldConfig(fieldName = "id", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "name", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String name;

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getCrossGroupProcessType() {
		return this.crossGroupProcessType;
	}

	public void setCrossGroupProcessType(int crossGroupProcessType) {
		this.crossGroupProcessType = crossGroupProcessType;
	}

	public int getInnerProcessType() {
		return this.innerProcessType;
	}

	public void setInnerProcessType(int innerProcessType) {
		this.innerProcessType = innerProcessType;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
