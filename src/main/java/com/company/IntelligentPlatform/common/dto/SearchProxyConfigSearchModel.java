package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;

public class SearchProxyConfigSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "searchModelName", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String searchModelName;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String uuid;
	
	@BSearchFieldConfig(fieldName = "proxyType", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected int proxyType;

	@BSearchFieldConfig(fieldName = "searchProxyName", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String searchProxyName;

	@BSearchFieldConfig(fieldName = "id", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "serviceManagerName", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String serviceManagerName;

	@BSearchFieldConfig(fieldName = "name", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "documentType", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected int documentType;

	public String getSearchModelName() {
		return this.searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSearchProxyName() {
		return this.searchProxyName;
	}

	public void setSearchProxyName(String searchProxyName) {
		this.searchProxyName = searchProxyName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getServiceManagerName() {
		return this.serviceManagerName;
	}

	public void setServiceManagerName(String serviceManagerName) {
		this.serviceManagerName = serviceManagerName;
	}

	public String getName() {
		return this.name;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public int getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public int getProxyType() {
		return proxyType;
	}

	public void setProxyType(int proxyType) {
		this.proxyType = proxyType;
	}

}
