package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.model.SalesForcastMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesForcastMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class SalesForcastMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SalesForcastMaterialItem.NODENAME, nodeInstId = SalesForcastMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected SalesForcastMaterialItem salesForcastMaterialItem;

	@IServiceModuleFieldConfig(nodeName = SalesForcastMaterialItemAttachment.NODENAME, nodeInstId =
			SalesForcastMaterialItemAttachment.NODENAME, docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> salesForcastMaterialItemAttachmentList = new ArrayList<>();

	public SalesForcastMaterialItem getSalesForcastMaterialItem() {
		return salesForcastMaterialItem;
	}

	public void setSalesForcastMaterialItem(final SalesForcastMaterialItem salesForcastMaterialItem) {
		this.salesForcastMaterialItem = salesForcastMaterialItem;
	}

	public List<ServiceEntityNode> getSalesForcastMaterialItemAttachmentList() {
		return salesForcastMaterialItemAttachmentList;
	}

	public void setSalesForcastMaterialItemAttachmentList(List<ServiceEntityNode> salesForcastMaterialItemAttachmentList) {
		this.salesForcastMaterialItemAttachmentList = salesForcastMaterialItemAttachmentList;
	}
}