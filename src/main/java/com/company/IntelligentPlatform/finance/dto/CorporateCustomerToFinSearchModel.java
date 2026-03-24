package com.company.IntelligentPlatform.finance.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

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
public class CorporateCustomerToFinSearchModel extends AccountToFinanceSearchModel {
	
	@BSearchFieldConfig(fieldName = "id", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "telephone", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "mobile", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String mobile;
	
	@BSearchFieldConfig(fieldName = "address", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected String address;
	
	@BSearchFieldConfig(fieldName = "customerType", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
	protected int customerType;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

}
