package com.company.IntelligentPlatform.finance.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;

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
public class AccountToFinanceSearchModel extends AccountSearchModel {

	
	@BSearchFieldConfig(fieldName = "uuid", nodeName = FinAccountObjectRef.NODENAME, seName = FinAccountObjectRef.SENAME, nodeInstID = FinAccountObjectRef.NODENAME)
	protected String finAccountUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = FinAccount.NODENAME, seName = FinAccount.SENAME, nodeInstID = FinAccount.SENAME)
	protected String finAccountId;
	
	@BSearchFieldConfig(fieldName = "recordStatus", nodeName = FinAccount.NODENAME, seName = FinAccount.SENAME, nodeInstID = FinAccount.SENAME)
	protected int recordStatus;
	
	@BSearchFieldConfig(fieldName = "auditStatus", nodeName = FinAccount.NODENAME, seName = FinAccount.SENAME, nodeInstID = FinAccount.SENAME)
	protected int auditStatus;

	@BSearchFieldConfig(fieldName = "verifyStatus", nodeName = FinAccount.NODENAME, seName = FinAccount.SENAME, nodeInstID = FinAccount.SENAME)
	protected int verifyStatus;	

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

	public String getFinAccountUUID() {
		return finAccountUUID;
	}

	public void setFinAccountUUID(String finAccountUUID) {
		this.finAccountUUID = finAccountUUID;
	}

	public String getFinAccountId() {
		return finAccountId;
	}

	public void setFinAccountId(String finAccountId) {
		this.finAccountId = finAccountId;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
}
