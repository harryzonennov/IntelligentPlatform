package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SearchProxyConfigUIModel extends SEUIComModel {

	protected String searchModelName;
	
	protected String searchProxyName;

	protected String searchItemMethodName;

	protected String searchDocMethodName;
	
	protected String serviceManagerName;
	
	protected int documentType;

	protected String documentTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "SearchProxyConfig_proxyType", valueFieldName = "proxyTypeValue")
	protected int proxyType;
	
	protected String proxyTypeValue;
	
	protected int configureSearchLogicFlag;
	
	protected String configureSearchLogicFlagValue;

	public String getSearchModelName() {
		return this.searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public String getSearchProxyName() {
		return this.searchProxyName;
	}

	public void setSearchProxyName(String searchProxyName) {
		this.searchProxyName = searchProxyName;
	}

	public String getSearchItemMethodName() {
		return this.searchItemMethodName;
	}

	public void setSearchItemMethodName(String searchItemMethodName) {
		this.searchItemMethodName = searchItemMethodName;
	}

	public String getSearchDocMethodName() {
		return this.searchDocMethodName;
	}

	public void setSearchDocMethodName(String searchDocMethodName) {
		this.searchDocMethodName = searchDocMethodName;
	}

	public String getServiceManagerName() {
		return this.serviceManagerName;
	}

	public void setServiceManagerName(String serviceManagerName) {
		this.serviceManagerName = serviceManagerName;
	}

	public int getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public int getProxyType() {
		return proxyType;
	}

	public void setProxyType(int proxyType) {
		this.proxyType = proxyType;
	}

	public String getProxyTypeValue() {
		return proxyTypeValue;
	}

	public void setProxyTypeValue(String proxyTypeValue) {
		this.proxyTypeValue = proxyTypeValue;
	}

	public int getConfigureSearchLogicFlag() {
		return configureSearchLogicFlag;
	}

	public void setConfigureSearchLogicFlag(int configureSearchLogicFlag) {
		this.configureSearchLogicFlag = configureSearchLogicFlag;
	}

	public String getConfigureSearchLogicFlagValue() {
		return configureSearchLogicFlagValue;
	}

	public void setConfigureSearchLogicFlagValue(
			String configureSearchLogicFlagValue) {
		this.configureSearchLogicFlagValue = configureSearchLogicFlagValue;
	}

}
