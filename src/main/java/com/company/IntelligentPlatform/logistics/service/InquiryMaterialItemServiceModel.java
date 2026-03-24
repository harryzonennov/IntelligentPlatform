package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.InquiryMaterialItem;
import com.company.IntelligentPlatform.logistics.model.InquiryMaterialItemAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class InquiryMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = InquiryMaterialItem.NODENAME, nodeInstId = InquiryMaterialItem.NODENAME,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected InquiryMaterialItem inquiryMaterialItem;

	@IServiceModuleFieldConfig(nodeName = InquiryMaterialItemAttachment.NODENAME, nodeInstId = InquiryMaterialItemAttachment.NODENAME, 
			blockUpdate = true,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inquiryMaterialItemAttachmentList = new ArrayList<>();

	public List<ServiceEntityNode> getInquiryMaterialItemAttachmentList() {
		return this.inquiryMaterialItemAttachmentList;
	}

	public void setInquiryMaterialItemAttachmentList(
			List<ServiceEntityNode> inquiryMaterialItemAttachmentList) {
		this.inquiryMaterialItemAttachmentList = inquiryMaterialItemAttachmentList;
	}

	public InquiryMaterialItem getInquiryMaterialItem() {
		return this.inquiryMaterialItem;
	}

	public void setInquiryMaterialItem(
			InquiryMaterialItem inquiryMaterialItem) {
		this.inquiryMaterialItem = inquiryMaterialItem;
	}

}
