package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;


import com.company.IntelligentPlatform.logistics.model.InquiryMaterialItem;
import com.company.IntelligentPlatform.logistics.model.InquiryMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class InquiryMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InquiryMaterialItem.NODENAME, nodeInstId = InquiryMaterialItem.NODENAME)
	protected InquiryMaterialItemUIModel inquiryMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InquiryMaterialItemAttachment.NODENAME, nodeInstId = InquiryMaterialItemAttachment.NODENAME)
	protected List<InquiryMaterialItemAttachmentUIModel> inquiryMaterialItemAttachmentUIModelList = new ArrayList<>();

	public InquiryMaterialItemUIModel getInquiryMaterialItemUIModel() {
		return this.inquiryMaterialItemUIModel;
	}

	public void setInquiryMaterialItemUIModel(
			InquiryMaterialItemUIModel purchaseContractMaterialItemUIModel) {
		this.inquiryMaterialItemUIModel = purchaseContractMaterialItemUIModel;
	}

	public List<InquiryMaterialItemAttachmentUIModel> getInquiryMaterialItemAttachmentUIModelList() {
		return inquiryMaterialItemAttachmentUIModelList;
	}

	public void setInquiryMaterialItemAttachmentUIModelList(List<InquiryMaterialItemAttachmentUIModel> inquiryMaterialItemAttachmentUIModelList) {
		this.inquiryMaterialItemAttachmentUIModelList = inquiryMaterialItemAttachmentUIModelList;
	}
}
