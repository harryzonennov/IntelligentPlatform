package com.company.IntelligentPlatform.common.controller;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * ServiceDocConsumerUnion UI Model
 **
 * @author
 * @date Fri May 06 15:10:10 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class ServiceDocConsumerUnionSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceDocConsumerUnion.NODENAME, seName = ServiceDocConsumerUnion.SENAME, nodeInstID = ServiceDocConsumerUnion.SENAME, showOnUI = false)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "name", nodeName = ServiceDocConsumerUnion.NODENAME, seName = ServiceDocConsumerUnion.SENAME, nodeInstID = ServiceDocConsumerUnion.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "id", nodeName = ServiceDocConsumerUnion.NODENAME, seName = ServiceDocConsumerUnion.SENAME, nodeInstID = ServiceDocConsumerUnion.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "uiModelType", nodeName = ServiceDocConsumerUnion.NODENAME, seName = ServiceDocConsumerUnion.SENAME, nodeInstID = ServiceDocConsumerUnion.SENAME)
	protected String uiModelType;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

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
	
	public String getUiModelType() {
		return uiModelType;
	}

	public void setUiModelType(String uiModelType) {
		this.uiModelType = uiModelType;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
