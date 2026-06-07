package com.company.IntelligentPlatform.salesDistribution.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnMatItemAttachment;
import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnMaterialItem;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class SalesReturnMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SalesReturnMaterialItem.NODENAME, nodeInstId =
			SalesReturnMaterialItem.NODENAME,docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected SalesReturnMaterialItem salesReturnMaterialItem;

	@IServiceModuleFieldConfig(nodeName = SalesReturnMatItemAttachment.NODENAME, nodeInstId = SalesReturnMatItemAttachment.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> salesReturnMatItemAttachmentList = new ArrayList<>();

	public SalesReturnMaterialItem getSalesReturnMaterialItem() {
		return salesReturnMaterialItem;
	}

	public void setSalesReturnMaterialItem(final SalesReturnMaterialItem salesReturnMaterialItem) {
		this.salesReturnMaterialItem = salesReturnMaterialItem;
	}

	public List<ServiceEntityNode> getSalesReturnMatItemAttachmentList() {
		return this.salesReturnMatItemAttachmentList;
	}

	public void setSalesReturnMatItemAttachmentList(
			List<ServiceEntityNode> salesReturnMatItemAttachmentList) {
		this.salesReturnMatItemAttachmentList = salesReturnMatItemAttachmentList;
	}

}