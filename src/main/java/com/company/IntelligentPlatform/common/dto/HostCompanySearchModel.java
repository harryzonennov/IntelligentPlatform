package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.HostCompany;

@Component
public class HostCompanySearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = HostCompany.NODENAME, seName = HostCompany.SENAME, nodeInstID = HostCompany.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = HostCompany.NODENAME, seName = HostCompany.SENAME, nodeInstID = HostCompany.SENAME)
	protected String name;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
