package com.company.IntelligentPlatform.salesDistribution.service;

import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItem;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItemAttachment;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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