package com.company.IntelligentPlatform.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.sales.controller.SalesContractMaterialItemAttachmentUIModel;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItemAttachment;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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