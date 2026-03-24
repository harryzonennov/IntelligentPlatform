package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.dto.AccountSearchSubModel;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.CorporateCustomerActionNode;
import com.company.IntelligentPlatform.common.dto.AccountSearchModel;

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
public class CorporateSupplierSearchModel extends AccountSearchModel {

	@BSearchGroupConfig(groupInstId = CorporateCustomer.SENAME)
	AccountSearchSubModel headerModel;

	@BSearchGroupConfig(groupInstId = CorporateCustomer.SENAME)
	protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

	@BSearchFieldConfig(fieldName = "customerType", nodeName = "ROOT", seName = "CorporateCustomer", nodeInstID = "CorporateCustomer")
	protected int customerType;

	@BSearchFieldConfig(fieldName = "customerLevel", nodeName = "ROOT", seName = "CorporateCustomer", nodeInstID = "CorporateCustomer")
	protected int customerLevel;

	@BSearchGroupConfig(groupInstId = CorporateCustomerActionNode.NODEINST_ACTION_SUBMIT)
	protected DocActionNodeSearchModel submittedBy;

	@BSearchGroupConfig(groupInstId = CorporateCustomerActionNode.NODEINST_ACTION_APPROVE)
	protected DocActionNodeSearchModel approvedBy;

	@BSearchGroupConfig(groupInstId = CorporateCustomerActionNode.NODEINST_ACTION_ACTIVE)
	protected DocActionNodeSearchModel activeBy;

	@BSearchGroupConfig(groupInstId = CorporateCustomerActionNode.NODEINST_ACTION_REINIT)
	protected DocActionNodeSearchModel reInitBy;

	@BSearchGroupConfig(groupInstId = CorporateCustomerActionNode.NODEINST_ACTION_ARCHIVE)
	protected DocActionNodeSearchModel archivedBy;

	public AccountSearchSubModel getHeaderModel() {
		return headerModel;
	}

	public void setHeaderModel(AccountSearchSubModel headerModel) {
		this.headerModel = headerModel;
	}

	public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
		return createdUpdateModel;
	}

	public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
		this.createdUpdateModel = createdUpdateModel;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

	public int getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(int customerLevel) {
		this.customerLevel = customerLevel;
	}

	public DocActionNodeSearchModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public DocActionNodeSearchModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public DocActionNodeSearchModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(DocActionNodeSearchModel activeBy) {
		this.activeBy = activeBy;
	}

	public DocActionNodeSearchModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(DocActionNodeSearchModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public DocActionNodeSearchModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(DocActionNodeSearchModel archivedBy) {
		this.archivedBy = archivedBy;
	}
}
