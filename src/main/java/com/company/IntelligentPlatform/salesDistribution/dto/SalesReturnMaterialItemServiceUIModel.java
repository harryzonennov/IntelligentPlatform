package com.company.IntelligentPlatform.salesDistribution.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderManager;
import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnMatItemAttachment;
import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnMaterialItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class SalesReturnMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnMaterialItem.NODENAME, nodeInstId = SalesReturnMaterialItem.NODENAME)
	protected SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesReturnMatItemAttachment.NODENAME, nodeInstId = SalesReturnMatItemAttachment.NODENAME)
	protected List<SalesReturnMatItemAttachmentUIModel> salesReturnMatItemAttachmentUIModelList = new ArrayList<SalesReturnMatItemAttachmentUIModel>();

	public SalesReturnMaterialItemUIModel getSalesReturnMaterialItemUIModel() {
		return this.salesReturnMaterialItemUIModel;
	}

	public void setSalesReturnMaterialItemUIModel(
			SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel) {
		this.salesReturnMaterialItemUIModel = salesReturnMaterialItemUIModel;
	}

	public List<SalesReturnMatItemAttachmentUIModel> getSalesReturnMatItemAttachmentUIModelList() {
		return this.salesReturnMatItemAttachmentUIModelList;
	}

	public void setSalesReturnMatItemAttachmentUIModelList(
			List<SalesReturnMatItemAttachmentUIModel> salesReturnMatItemAttachmentUIModelList) {
		this.salesReturnMatItemAttachmentUIModelList = salesReturnMatItemAttachmentUIModelList;
	}

}