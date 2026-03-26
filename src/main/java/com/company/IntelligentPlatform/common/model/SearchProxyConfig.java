package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "SearchProxyConfig", catalog = "platform")
public class SearchProxyConfig extends ServiceEntityNode {	
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SearchProxyConfig;
	
	public static final int PROXYTYPE_SYS = 1;
	
	public static final int PROXYTYPE_CUS = 2;
	
	public SearchProxyConfig() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.configureSearchLogicFlag = StandardSwitchProxy.SWITCH_OFF;
	}
	
	protected String searchModelName;
	
	protected String searchProxyName;
	
	protected int documentType;
	
	protected int proxyType;
	
	protected int configureSearchLogicFlag;
	
	protected String serviceManagerName;
	
	protected String searchDocMethodName;
	
	protected String searchItemMethodName;

	protected String modelNameJson;

	public String getSearchModelName() {
		return searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public int getDocumentType() {
		return documentType;
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

	public String getSearchProxyName() {
		return searchProxyName;
	}

	public void setSearchProxyName(String searchProxyName) {
		this.searchProxyName = searchProxyName;
	}

	public String getServiceManagerName() {
		return serviceManagerName;
	}

	public void setServiceManagerName(String serviceManagerName) {
		this.serviceManagerName = serviceManagerName;
	}

	public String getSearchDocMethodName() {
		return searchDocMethodName;
	}

	public void setSearchDocMethodName(String searchDocMethodName) {
		this.searchDocMethodName = searchDocMethodName;
	}

	public String getSearchItemMethodName() {
		return searchItemMethodName;
	}

	public void setSearchItemMethodName(String searchItemMethodName) {
		this.searchItemMethodName = searchItemMethodName;
	}

	public int getConfigureSearchLogicFlag() {
		return configureSearchLogicFlag;
	}

	public void setConfigureSearchLogicFlag(int configureSearchLogicFlag) {
		this.configureSearchLogicFlag = configureSearchLogicFlag;
	}

	public String getModelNameJson() {
		return modelNameJson;
	}

	public void setModelNameJson(String modelNameJson) {
		this.modelNameJson = modelNameJson;
	}
}
