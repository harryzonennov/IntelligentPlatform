package com.company.IntelligentPlatform.sales.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class SalesContractMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SalesContractMaterialItem.NODENAME, nodeInstId = SalesContractMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected SalesContractMaterialItem salesContractMaterialItem;

	@IServiceModuleFieldConfig(nodeName = SalesContractMaterialItemAttachment.NODENAME, nodeInstId = SalesContractMaterialItemAttachment.NODENAME, 
			blockUpdate = true, docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> salesContractMaterialItemAttachmentList = new ArrayList<>();

	public List<ServiceEntityNode> getSalesContractMaterialItemAttachmentList() {
		return this.salesContractMaterialItemAttachmentList;
	}

	public void setSalesContractMaterialItemAttachmentList(
			List<ServiceEntityNode> salesContractMaterialItemAttachmentList) {
		this.salesContractMaterialItemAttachmentList = salesContractMaterialItemAttachmentList;
	}

	public SalesContractMaterialItem getSalesContractMaterialItem() {
		return this.salesContractMaterialItem;
	}

	public void setSalesContractMaterialItem(
			SalesContractMaterialItem salesContractMaterialItem) {
		this.salesContractMaterialItem = salesContractMaterialItem;
	}

}