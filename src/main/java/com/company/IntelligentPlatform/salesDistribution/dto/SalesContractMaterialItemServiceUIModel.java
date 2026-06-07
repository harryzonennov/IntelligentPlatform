package com.company.IntelligentPlatform.salesDistribution.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.salesDistribution.controller.SalesContractMaterialItemAttachmentUIModel;
import com.company.IntelligentPlatform.salesDistribution.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.salesDistribution.model.SalesContractMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

@Component
public class SalesContractMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SalesContractMaterialItem.NODENAME,  nodeInstId = SalesContractMaterialItem.NODENAME)
	protected SalesContractMaterialItemUIModel salesContractMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesContractMaterialItemAttachment.NODENAME, nodeInstId = SalesContractMaterialItemAttachment.NODENAME)
	protected List<SalesContractMaterialItemAttachmentUIModel> salesContractMaterialItemAttachmentUIModelList = new ArrayList<SalesContractMaterialItemAttachmentUIModel>();

	public SalesContractMaterialItemUIModel getSalesContractMaterialItemUIModel() {
		return this.salesContractMaterialItemUIModel;
	}

	public void setSalesContractMaterialItemUIModel(
			SalesContractMaterialItemUIModel salesContractMaterialItemUIModel) {
		this.salesContractMaterialItemUIModel = salesContractMaterialItemUIModel;
	}

	public List<SalesContractMaterialItemAttachmentUIModel> getSalesContractMaterialItemAttachmentUIModelList() {
		return this.salesContractMaterialItemAttachmentUIModelList;
	}

	public void setSalesContractMaterialItemAttachmentUIModelList(
			List<SalesContractMaterialItemAttachmentUIModel> salesContractMaterialItemAttachmentUIModelList) {
		this.salesContractMaterialItemAttachmentUIModelList = salesContractMaterialItemAttachmentUIModelList;
	}
}