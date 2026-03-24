package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Account UI Model
 ** 
 * @author
 * @date Tue Dec 29 10:42:22 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class AccountSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = Account.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = Account.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "telephone", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = Account.SENAME)
	protected String telephone;

	@BSearchFieldConfig(fieldName = "mobile", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = Account.SENAME)
	protected String mobile;
	
	@BSearchFieldConfig(fieldName = "address", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = Account.SENAME)
	protected String address;

	@BSearchFieldConfig(fieldName = "accountType", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = Account.SENAME)
	protected int accountType;

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

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
