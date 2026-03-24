package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityRegisterEntity;

@Component
public class ServiceEntityRegisterEntitySearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "seModuleType", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seModuleType;

	@BSearchFieldConfig(fieldName = "seModuleName", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seModuleName;

	@BSearchFieldConfig(fieldName = "seProxyType", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seProxyType;

	@BSearchFieldConfig(fieldName = "seManagerName", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seManagerName;

	@BSearchFieldConfig(fieldName = "client", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String client;

	@BSearchFieldConfig(fieldName = "seManagerType", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seManagerType;

	@BSearchFieldConfig(fieldName = "seDAOName", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seDAOName;

	@BSearchFieldConfig(fieldName = "note", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "packageName", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String packageName;

	@BSearchFieldConfig(fieldName = "seDAOType", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seDAOType;

	@BSearchFieldConfig(fieldName = "seProxyName", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String seProxyName;

	@BSearchFieldConfig(fieldName = "name", nodeName = ServiceEntityRegisterEntity.NODENAME, seName = ServiceEntityRegisterEntity.SENAME, nodeInstID = ServiceEntityRegisterEntity.SENAME)
	protected String name;

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeModuleType() {
		return this.seModuleType;
	}

	public void setSeModuleType(String seModuleType) {
		this.seModuleType = seModuleType;
	}

	public String getSeModuleName() {
		return this.seModuleName;
	}

	public void setSeModuleName(String seModuleName) {
		this.seModuleName = seModuleName;
	}

	public String getSeProxyType() {
		return this.seProxyType;
	}

	public void setSeProxyType(String seProxyType) {
		this.seProxyType = seProxyType;
	}

	public String getSeManagerName() {
		return this.seManagerName;
	}

	public void setSeManagerName(String seManagerName) {
		this.seManagerName = seManagerName;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSeManagerType() {
		return this.seManagerType;
	}

	public void setSeManagerType(String seManagerType) {
		this.seManagerType = seManagerType;
	}

	public String getSeDAOName() {
		return this.seDAOName;
	}

	public void setSeDAOName(String seDAOName) {
		this.seDAOName = seDAOName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSeDAOType() {
		return this.seDAOType;
	}

	public void setSeDAOType(String seDAOType) {
		this.seDAOType = seDAOType;
	}

	public String getSeProxyName() {
		return this.seProxyName;
	}

	public void setSeProxyName(String seProxyName) {
		this.seProxyName = seProxyName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
