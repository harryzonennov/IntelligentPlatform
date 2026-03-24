package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * SerExtendPageSetting UI Model
 ** 
 * @author
 * @date Fri Nov 20 15:25:24 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class SerExtendPageSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected String uuid;	
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected String rootNodeUUID;		
	
	@BSearchFieldConfig(fieldName = "id", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected String name;
	
	@BSearchFieldConfig(fieldName = "navigationId", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected String navigationId;
	
	@BSearchFieldConfig(fieldName = "pageCategory", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected int pageCategory;

	@BSearchFieldConfig(fieldName = "id", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String nodeInstId;
	
	@BSearchFieldConfig(fieldName = "refSEName", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String refSEName;
	
	@BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, nodeInstID = ServiceExtensionSetting.SENAME)
	protected String refNodeName;

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

	public String getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(String navigationId) {
		this.navigationId = navigationId;
	}

	public int getPageCategory() {
		return pageCategory;
	}

	public void setPageCategory(int pageCategory) {
		this.pageCategory = pageCategory;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}
}
