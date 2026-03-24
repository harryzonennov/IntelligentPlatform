package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SerExtendPageSection;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * SerExtendPageSection UI Model
 ** 
 * @author
 * @date Fri Nov 20 15:25:24 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class SerExtendPageSectionSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = SerExtendPageSection.NODENAME, seName = SerExtendPageSection.SENAME, nodeInstID = SerExtendPageSection.NODENAME)
	protected String uuid;	
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = SerExtendPageSection.NODENAME, seName = SerExtendPageSection.SENAME, nodeInstID = SerExtendPageSection.NODENAME)
	protected String parentNodeUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = SerExtendPageSection.NODENAME, seName = SerExtendPageSection.SENAME, nodeInstID = SerExtendPageSection.NODENAME)
	protected String rootNodeUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = SerExtendPageSection.NODENAME, seName = SerExtendPageSection.SENAME, nodeInstID = SerExtendPageSection.NODENAME)
	protected String id;	
	
	@BSearchFieldConfig(fieldName = "id", nodeName = SerExtendPageSetting.NODENAME, seName = SerExtendPageSetting.SENAME, nodeInstID = SerExtendPageSetting.NODENAME)
	protected String pageId;
	
	@BSearchFieldConfig(fieldName = "pageCategory", nodeName = SerExtendPageSection.NODENAME, seName = SerExtendPageSection.SENAME, nodeInstID = SerExtendPageSection.NODENAME)
	protected int sectionCategory;	

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

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public int getSectionCategory() {
		return sectionCategory;
	}

	public void setSectionCategory(int sectionCategory) {
		this.sectionCategory = sectionCategory;
	}
	
}
