package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;


/**
 * corporateCustomer UI Model
 ** 
 * @author
 * @date Tue Aug 13 11:55:51 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class CorporateDealerSearchModel extends CorporateCustomerSearchModel {
	
	public static final String NODE_PARENT_DISTRIBUTOR = "parentDistributor";

	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_PARENT_DISTRIBUTOR)
	protected String parentDistributorID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_PARENT_DISTRIBUTOR)
	protected String parentDistributorName;
	
	@BSearchFieldConfig(fieldName = "uuid", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_PARENT_DISTRIBUTOR)
	protected String parentDistributorUUID;

	public String getParentDistributorID() {
		return parentDistributorID;
	}

	public void setParentDistributorID(String parentDistributorID) {
		this.parentDistributorID = parentDistributorID;
	}

	public String getParentDistributorName() {
		return parentDistributorName;
	}

	public void setParentDistributorName(String parentDistributorName) {
		this.parentDistributorName = parentDistributorName;
	}

	public String getParentDistributorUUID() {
		return parentDistributorUUID;
	}

	public void setParentDistributorUUID(String parentDistributorUUID) {
		this.parentDistributorUUID = parentDistributorUUID;
	}

}
