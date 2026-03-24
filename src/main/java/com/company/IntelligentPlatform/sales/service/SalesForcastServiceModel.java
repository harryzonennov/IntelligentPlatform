package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class SalesForcastServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SalesForcast.NODENAME, nodeInstId = SalesForcast.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected SalesForcast salesForcast;
	
	@IServiceModuleFieldConfig(nodeName = SalesForcastParty.NODENAME, nodeInstId = SalesForcastParty.PARTY_NODEINST_SOLD_CUSTOMER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected SalesForcastParty soldToCustomer;
	
	@IServiceModuleFieldConfig(nodeName = SalesForcastParty.NODENAME, nodeInstId = SalesForcastParty.PARTY_NODEINST_SOLD_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected SalesForcastParty soldFromOrg;

	@IServiceModuleFieldConfig(nodeName = SalesForcastActionNode.NODENAME,
			nodeInstId = SalesForcastActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesForcastActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = SalesForcastActionNode.NODENAME, nodeInstId =
			SalesForcastActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected SalesForcastActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = SalesForcastMaterialItem.NODENAME, nodeInstId = SalesForcastMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<SalesForcastMaterialItemServiceModel> salesForcastMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SalesForcastAttachment.NODENAME, nodeInstId = SalesForcastAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> salesForcastAttachmentList = new ArrayList<>();

	public List<SalesForcastMaterialItemServiceModel> getSalesForcastMaterialItemList() {
		return salesForcastMaterialItemList;
	}

	public void setSalesForcastMaterialItemList(final List<SalesForcastMaterialItemServiceModel> salesForcastMaterialItemList) {
		this.salesForcastMaterialItemList = salesForcastMaterialItemList;
	}

	public SalesForcast getSalesForcast() {
		return salesForcast;
	}

	public void setSalesForcast(SalesForcast salesForcast) {
		this.salesForcast = salesForcast;
	}

	public SalesForcastParty getSoldToCustomer() {
		return soldToCustomer;
	}

	public void setSoldToCustomer(SalesForcastParty soldToCustomer) {
		this.soldToCustomer = soldToCustomer;
	}

	public SalesForcastParty getSoldFromOrg() {
		return soldFromOrg;
	}

	public void setSoldFromOrg(SalesForcastParty soldFromOrg) {
		this.soldFromOrg = soldFromOrg;
	}

	public List<ServiceEntityNode> getSalesForcastAttachmentList() {
		return salesForcastAttachmentList;
	}

	public void setSalesForcastAttachmentList(List<ServiceEntityNode> salesForcastAttachmentList) {
		this.salesForcastAttachmentList = salesForcastAttachmentList;
	}

	public SalesForcastActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(SalesForcastActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public SalesForcastActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(SalesForcastActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

}