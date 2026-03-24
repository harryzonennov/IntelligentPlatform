package com.company.IntelligentPlatform.common.controller;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


/**
 * ServiceDocConfigure UI Model
 **
 * @author
 * @date Fri May 06 15:10:10 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class ServiceDocConfigureSearchModel extends SEUIComModel {
	
	public static final String NODEINS_ID_INPUTUNION = "inputUnion";
	
	public static final String NODEINS_ID_CONSUMEUNION = "consumerUnion";

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME, showOnUI = false)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "name", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "id", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected String id;
	
	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected int switchFlag;

	@BSearchFieldConfig(fieldName = "name", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, 
			nodeInstID = NODEINS_ID_CONSUMEUNION)
	protected String consumerUnionName;
	
	@BSearchFieldConfig(fieldName = "consumerUnionUUID", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected String consumerUnionUUID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = ServiceDocConfigure.NODENAME, 
			seName = ServiceDocConfigure.SENAME, nodeInstID = NODEINS_ID_INPUTUNION)
	protected String inputUnionName;
	
	@BSearchFieldConfig(fieldName = "inputUnionUUID", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected String inputUnionUUID;

	@BSearchFieldConfig(fieldName = "resourceDocType", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected String resourceDocType;
	
	@BSearchFieldConfig(fieldName = "refSearchProxyUUID", nodeName = ServiceDocConfigure.NODENAME, seName = ServiceDocConfigure.SENAME, nodeInstID = ServiceDocConfigure.SENAME)
	protected String refSearchProxyUUID;	
	
	@BSearchFieldConfig(fieldName = "documentType", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected int searchDocType;
	
	@BSearchFieldConfig(fieldName = "searchModelName", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String refSearchModelName;
	
	@BSearchFieldConfig(fieldName = "searchModelName", nodeName = SearchProxyConfig.NODENAME, seName = SearchProxyConfig.SENAME, nodeInstID = SearchProxyConfig.SENAME)
	protected String refSearchProxyName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConsumerUnionName() {
		return consumerUnionName;
	}

	public void setConsumerUnionName(String consumerUnionName) {
		this.consumerUnionName = consumerUnionName;
	}

	public String getResourceDocType() {
		return resourceDocType;
	}

	public void setResourceDocType(String resourceDocType) {
		this.resourceDocType = resourceDocType;
	}


	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getRefSearchProxyUUID() {
		return refSearchProxyUUID;
	}

	public void setRefSearchProxyUUID(String refSearchProxyUUID) {
		this.refSearchProxyUUID = refSearchProxyUUID;
	}

	public int getSearchDocType() {
		return searchDocType;
	}

	public void setSearchDocType(int searchDocType) {
		this.searchDocType = searchDocType;
	}

	public String getRefSearchModelName() {
		return refSearchModelName;
	}

	public void setRefSearchModelName(String refSearchModelName) {
		this.refSearchModelName = refSearchModelName;
	}

	public String getRefSearchProxyName() {
		return refSearchProxyName;
	}

	public void setRefSearchProxyName(String refSearchProxyName) {
		this.refSearchProxyName = refSearchProxyName;
	}

}
