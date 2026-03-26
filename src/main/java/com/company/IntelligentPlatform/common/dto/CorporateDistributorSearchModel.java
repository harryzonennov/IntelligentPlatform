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
public class CorporateDistributorSearchModel extends CorporateCustomerSearchModel {
	
	public static final String NODE_SUB_DEALER = "subDealer";

	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_SUB_DEALER)
	protected String subDealerID;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_SUB_DEALER)
	protected String subDealerName;
	
	@BSearchFieldConfig(fieldName = "uuid", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_SUB_DEALER)
	protected String subDealerUUID;
	
	@BSearchFieldConfig(fieldName = "subDistributorType", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = NODE_SUB_DEALER)
	protected int subDistributorType;
	
	@BSearchFieldConfig(fieldName = "subDistributorType", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, 
			nodeInstID = CorporateCustomer.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)	
	protected int startDistributorLevel;

	public String getSubDealerID() {
		return subDealerID;
	}

	public void setSubDealerID(String subDealerID) {
		this.subDealerID = subDealerID;
	}

	public String getSubDealerName() {
		return subDealerName;
	}

	public void setSubDealerName(String subDealerName) {
		this.subDealerName = subDealerName;
	}

	public String getSubDealerUUID() {
		return subDealerUUID;
	}

	public void setSubDealerUUID(String subDealerUUID) {
		this.subDealerUUID = subDealerUUID;
	}

	public int getSubDistributorType() {
		return subDistributorType;
	}

	public void setSubDistributorType(int subDistributorType) {
		this.subDistributorType = subDistributorType;
	}

	public int getStartDistributorLevel() {
		return startDistributorLevel;
	}

	public void setStartDistributorLevel(int startDistributorLevel) {
		this.startDistributorLevel = startDistributorLevel;
	}

}
