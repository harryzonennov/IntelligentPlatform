package com.company.IntelligentPlatform.finance.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;

/**
 * Account Search Model extended to finance account
 ** 
 * @author
 * @date Tue Dec 29 10:42:22 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */

@Component
public class IndividualCustomerToFinSearchModel extends AccountToFinanceSearchModel {
	
	@BSearchFieldConfig(fieldName = "id", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "telephone", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "mobile", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String mobile;
	
	@BSearchFieldConfig(fieldName = "address", nodeName = IndividualCustomer.NODENAME, seName = IndividualCustomer.SENAME, nodeInstID = IndividualCustomer.SENAME)
	protected String address;	

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
