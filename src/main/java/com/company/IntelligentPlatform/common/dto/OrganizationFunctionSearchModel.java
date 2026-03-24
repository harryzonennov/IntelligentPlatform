package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.OrganizationFunction;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * OrganizationFunction UI Model
 **
 * @author
 * @date Thu Sep 01 17:09:18 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class OrganizationFunctionSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = OrganizationFunction.NODENAME, seName = OrganizationFunction.SENAME, nodeInstID = OrganizationFunction.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = OrganizationFunction.NODENAME, seName = OrganizationFunction.SENAME, nodeInstID = OrganizationFunction.SENAME)
	protected String name;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
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
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
