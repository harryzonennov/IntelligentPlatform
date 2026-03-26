package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

/**
 * corporateCustomerArea UI Model
 ** 
 * @author
 * @date Mon Dec 02 14:00:30 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class CorporateContactPersonSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateContactPerson.NODENAME, 
			seName = CorporateContactPerson.SENAME, nodeInstID = CorporateContactPerson.NODENAME)
	protected String id;
	
	@BSearchFieldConfig(fieldName = "parentNodeUUID", nodeName = CorporateContactPerson.NODENAME, 
			seName = CorporateContactPerson.SENAME, nodeInstID = CorporateContactPerson.NODENAME)
	protected String parentNodeUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", nodeName = CorporateContactPerson.NODENAME, 
			seName = CorporateContactPerson.SENAME, nodeInstID = CorporateContactPerson.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateCustomer.NODENAME,
			seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String corporateCustomerId;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateCustomer.NODENAME, 
			seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String corporateCustomerName;

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

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getCorporateCustomerId() {
		return corporateCustomerId;
	}

	public void setCorporateCustomerId(String corporateCustomerId) {
		this.corporateCustomerId = corporateCustomerId;
	}

	public String getCorporateCustomerName() {
		return corporateCustomerName;
	}

	public void setCorporateCustomerName(String corporateCustomerName) {
		this.corporateCustomerName = corporateCustomerName;
	}
	
}
