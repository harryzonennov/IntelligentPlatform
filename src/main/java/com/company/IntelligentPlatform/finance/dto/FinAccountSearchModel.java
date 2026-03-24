package com.company.IntelligentPlatform.finance.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.finance.model.FinAccDocRef;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;


import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.LogonUser;


@Component
public class FinAccountSearchModel extends SEUIComModel {

	public final static String ID_ACCOBJECT = "accountObject";

	public final static String ID_ACCOBJECT_REF = "accountObjectRef";

	public final static String ID_ACCDOC = "accountDocument";

	public final static String ID_ACCDOC_REF = "accountDocumentRef";

	public final static String ID_CASHIER = "cashier";

	public final static String ID_ACCOUNTANT = "accountant";

	@BSearchFieldConfig(fieldName = "uuid", nodeInstID = FinAccount.SENAME, showOnUI = false)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id", nodeInstID = FinAccount.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "paymentType", nodeInstID = FinAccount.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountSearch_paymentType", valueFieldName = "")
	protected int paymentType;

	@BSearchFieldConfig(fieldName = "documentType", nodeInstID = FinAccount.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountSearch_documentType", valueFieldName = "")
	protected int documentType;
	
	@BSearchFieldConfig(fieldName = "createdBy", nodeName = FinAccount.NODENAME, seName = FinAccount.SENAME, nodeInstID = FinAccount.SENAME)
	protected String createdBy;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME)
	protected String createdByName;

	@BSearchFieldConfig(fieldName = "accountantUUID", nodeInstID = FinAccount.SENAME)
	protected String accountantUUID;

	@BSearchFieldConfig(fieldName = "createdTime", nodeInstID = FinAccount.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date createdTimeLow;

	@BSearchFieldConfig(fieldName = "createdTime", nodeInstID = FinAccount.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date createdTimeHigh;
	
	@BSearchFieldConfig(fieldName = "financeTime", nodeInstID = FinAccount.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
	protected Date financeTimeLow;

	@BSearchFieldConfig(fieldName = "financeTime", nodeInstID = FinAccount.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
	protected Date financeTimeHigh;
	
	protected String financeTimeStrLow;

	protected String financeTimeStrHigh;

	@BSearchFieldConfig(fieldName = "cashierUUID", nodeInstID = FinAccount.SENAME)
	protected String cashierUUID;

	@BSearchFieldConfig(fieldName = "resEmployeeUUID", nodeInstID = FinAccount.SENAME)
	protected String resEmployeeUUID;

	@BSearchFieldConfig(fieldName = "resOrgUUID", nodeInstID = FinAccount.SENAME)
	protected String resOrgUUID;

	@BSearchFieldConfig(fieldName = "auditStatus", nodeInstID = FinAccount.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountSearch_auditStatus", valueFieldName = "")
	protected int auditStatus;

	@BSearchFieldConfig(fieldName = "refUUID", nodeInstID = ID_ACCOBJECT_REF)
	protected String refObjUUID;

	@BSearchFieldConfig(fieldName = "verifyStatus", nodeInstID = FinAccount.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountSearch_verifyStatus", valueFieldName = "")
	protected int verifyStatus;

	@BSearchFieldConfig(fieldName = "recordStatus", nodeInstID = FinAccount.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountSearch_recordStatus", valueFieldName = "")
	protected int recordStatus;

	@BSearchFieldConfig(fieldName = "name", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = ID_ACCOBJECT)
	protected String accountObjectName;
	
	@BSearchFieldConfig(fieldName = IReferenceNodeFieldConstant.REFUUID, nodeName = FinAccountObjectRef.NODENAME, seName = FinAccountObjectRef.SENAME, nodeInstID = ID_ACCOBJECT_REF)
	protected String accountObjectUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = ID_ACCOBJECT)
	protected String accountObjectId;
	
	@BSearchFieldConfig(fieldName = "telephone", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = ID_ACCOBJECT)
	protected String accountObjectTelephone;
	
	@BSearchFieldConfig(fieldName = "accountType", nodeName = Account.NODENAME, seName = Account.SENAME, nodeInstID = ID_ACCOBJECT)
	protected int accountObjectType;

	@BSearchFieldConfig(fieldName = "id", nodeInstID = FinAccountTitle.SENAME)
	protected String finAccountTitleId;

	@BSearchFieldConfig(fieldName = "name", nodeInstID = FinAccountTitle.SENAME)
	protected String finAccountTitleName;

	@BSearchFieldConfig(fieldName = "finAccountType", nodeInstID = FinAccountTitle.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitleSearch_finAccountType", valueFieldName = "")
	protected int finAccountType;

	@BSearchFieldConfig(fieldName = "id", nodeInstID = ID_ACCDOC, nodeName = FinAccDocRef.NODENAME, seName = FinAccDocRef.SENAME)
	protected String documentId;
	
	@BSearchFieldConfig(fieldName = "uuid", nodeInstID =  ID_ACCDOC, nodeName = FinAccDocRef.NODENAME, seName = FinAccDocRef.SENAME)
	protected String refDocumentUUID;

	@BSearchFieldConfig(fieldName = "name", nodeInstID = ID_CASHIER)
	protected String cashierName;

	@BSearchFieldConfig(fieldName = "name", nodeInstID = ID_ACCOUNTANT)
	protected String accountantName;

	/**
	 * This attribute is just for get the page on UI, must be cleared before
	 * each search
	 */
	@BSearchFieldConfig(fieldName = "auditStatus", nodeInstID = FinAccount.SENAME)
	protected int currentPage;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getAccountObjectName() {
		return accountObjectName;
	}

	public void setAccountObjectName(String accountObjectName) {
		this.accountObjectName = accountObjectName;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public int getFinAccountType() {
		return finAccountType;
	}

	public void setFinAccountType(int finAccountType) {
		this.finAccountType = finAccountType;
	}
	
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}	

	public String getFinAccountTitleId() {
		return finAccountTitleId;
	}

	public void setFinAccountTitleId(String finAccountTitleId) {
		this.finAccountTitleId = finAccountTitleId;
	}

	public String getFinAccountTitleName() {
		return finAccountTitleName;
	}

	public void setFinAccountTitleName(String finAccountTitleName) {
		this.finAccountTitleName = finAccountTitleName;
	}

	public String getAccountantName() {
		return accountantName;
	}

	public void setAccountantName(String accountantName) {
		this.accountantName = accountantName;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
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

	public String getAccountantUUID() {
		return accountantUUID;
	}

	public void setAccountantUUID(String accountantUUID) {
		this.accountantUUID = accountantUUID;
	}

	public String getCashierUUID() {
		return cashierUUID;
	}

	public void setCashierUUID(String cashierUUID) {
		this.cashierUUID = cashierUUID;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getResEmployeeUUID() {
		return resEmployeeUUID;
	}

	public void setResEmployeeUUID(String resEmployeeUUID) {
		this.resEmployeeUUID = resEmployeeUUID;
	}

	public String getResOrgUUID() {
		return resOrgUUID;
	}

	public void setResOrgUUID(String resOrgUUID) {
		this.resOrgUUID = resOrgUUID;
	}

	public Date getCreatedTimeLow() {
		return createdTimeLow;
	}

	public void setCreatedTimeLow(Date createdTimeLow) {
		this.createdTimeLow = createdTimeLow;
	}

	public Date getCreatedTimeHigh() {
		return createdTimeHigh;
	}

	public void setCreatedTimeHigh(Date createdTimeHigh) {
		this.createdTimeHigh = createdTimeHigh;
	}

	public String getRefObjUUID() {
		return refObjUUID;
	}

	public void setRefObjUUID(String refObjUUID) {
		this.refObjUUID = refObjUUID;
	}


	public String getRefDocumentUUID() {
		return refDocumentUUID;
	}

	public void setRefDocumentUUID(String refDocumentUUID) {
		this.refDocumentUUID = refDocumentUUID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getAccountObjectId() {
		return accountObjectId;
	}

	public void setAccountObjectId(String accountObjectId) {
		this.accountObjectId = accountObjectId;
	}

	public String getAccountObjectTelephone() {
		return accountObjectTelephone;
	}

	public void setAccountObjectTelephone(String accountObjectTelephone) {
		this.accountObjectTelephone = accountObjectTelephone;
	}

	public int getAccountObjectType() {
		return accountObjectType;
	}

	public void setAccountObjectType(int accountObjectType) {
		this.accountObjectType = accountObjectType;
	}

	public String getAccountObjectUUID() {
		return accountObjectUUID;
	}

	public void setAccountObjectUUID(String accountObjectUUID) {
		this.accountObjectUUID = accountObjectUUID;
	}

	public Date getFinanceTimeLow() {
		return financeTimeLow;
	}

	public void setFinanceTimeLow(Date financeTimeLow) {
		this.financeTimeLow = financeTimeLow;
	}

	public Date getFinanceTimeHigh() {
		return financeTimeHigh;
	}

	public void setFinanceTimeHigh(Date financeTimeHigh) {
		this.financeTimeHigh = financeTimeHigh;
	}

	public String getFinanceTimeStrLow() {
		return financeTimeStrLow;
	}

	public void setFinanceTimeStrLow(String financeTimeStrLow) {
		this.financeTimeStrLow = financeTimeStrLow;
	}

	public String getFinanceTimeStrHigh() {
		return financeTimeStrHigh;
	}

	public void setFinanceTimeStrHigh(String financeTimeStrHigh) {
		this.financeTimeStrHigh = financeTimeStrHigh;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}


}
