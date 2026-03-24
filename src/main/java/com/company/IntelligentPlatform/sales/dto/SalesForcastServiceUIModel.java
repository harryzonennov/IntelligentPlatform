package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalesForcastServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SalesForcast.NODENAME, nodeInstId = SalesForcast.SENAME)
	protected SalesForcastUIModel salesForcastUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = SalesForcastParty.NODENAME, nodeInstId = SalesForcastParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected SalesForcastPartyUIModel soldToCustomerUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = SalesForcastParty.NODENAME, nodeInstId = SalesForcastParty.PARTY_NODEINST_SOLD_ORG)
	protected SalesForcastPartyUIModel soldFromOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesForcastActionNode.NODENAME, nodeInstId = SalesForcastActionNode.NODEINST_ACTION_APPROVE)
	protected SalesForcastActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesForcastActionNode.NODENAME, nodeInstId =
			SalesForcastActionNode.NODEINST_ACTION_SUBMIT)
	protected SalesForcastActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = SalesForcastMaterialItem.NODENAME, nodeInstId = SalesForcastMaterialItem.NODENAME)
	protected List<SalesForcastMaterialItemServiceUIModel> salesForcastMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = SalesForcastAttachment.NODENAME, nodeInstId = SalesForcastAttachment.NODENAME)
	protected List<SalesForcastAttachmentUIModel> salesForcastAttachmentUIModelList = new ArrayList<>();


	public List<SalesForcastMaterialItemServiceUIModel> getSalesForcastMaterialItemUIModelList() {
		return salesForcastMaterialItemUIModelList;
	}

	public void setSalesForcastMaterialItemUIModelList(
			List<SalesForcastMaterialItemServiceUIModel> salesForcastMaterialItemUIModelList) {
		this.salesForcastMaterialItemUIModelList = salesForcastMaterialItemUIModelList;
	}

	public List<SalesForcastAttachmentUIModel> getSalesForcastAttachmentUIModelList() {
		return salesForcastAttachmentUIModelList;
	}

	public void setSalesForcastAttachmentUIModelList(
			List<SalesForcastAttachmentUIModel> salesForcastAttachmentUIModelList) {
		this.salesForcastAttachmentUIModelList = salesForcastAttachmentUIModelList;
	}

	public SalesForcastUIModel getSalesForcastUIModel() {
		return salesForcastUIModel;
	}

	public void setSalesForcastUIModel(
			SalesForcastUIModel salesForcastUIModel) {
		this.salesForcastUIModel = salesForcastUIModel;
	}

	public SalesForcastPartyUIModel getSoldToCustomerUIModel() {
		return soldToCustomerUIModel;
	}

	public void setSoldToCustomerUIModel(SalesForcastPartyUIModel soldToCustomerUIModel) {
		this.soldToCustomerUIModel = soldToCustomerUIModel;
	}

	public SalesForcastPartyUIModel getSoldFromOrgUIModel() {
		return soldFromOrgUIModel;
	}

	public void setSoldFromOrgUIModel(SalesForcastPartyUIModel soldFromOrgUIModel) {
		this.soldFromOrgUIModel = soldFromOrgUIModel;
	}

	public SalesForcastActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(SalesForcastActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SalesForcastActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(SalesForcastActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

}