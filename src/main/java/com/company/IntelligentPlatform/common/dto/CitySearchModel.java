package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.City;

@Component
public class CitySearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "uuid", nodeName = City.NODENAME, seName = City.SENAME, nodeInstID = City.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id",  nodeName = City.NODENAME, seName = City.SENAME, nodeInstID = City.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name",  nodeName = City.NODENAME, seName = City.SENAME, nodeInstID = City.SENAME)
	protected String name;

	/**
	 * This attribute is just for get the page on UI, must be cleared before
	 * each search
	 */
	@BSearchFieldConfig(fieldName = "userType", nodeInstID = "LogonUser")
	protected int currentPage;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
