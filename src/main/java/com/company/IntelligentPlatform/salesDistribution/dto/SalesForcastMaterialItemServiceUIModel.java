package com.company.IntelligentPlatform.salesDistribution.dto;

import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItemAttachment;
import com.company.IntelligentPlatform.salesDistribution.model.SalesForcastMaterialItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalesForcastMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SalesForcastMaterialItem.NODENAME, nodeInstId = SalesForcastMaterialItem.NODENAME)
	protected SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SalesForcastMaterialItemAttachment.NODENAME, nodeInstId = SalesForcastMaterialItemAttachment.NODENAME)
	protected List<SalesForcastMaterialItemAttachmentUIModel> salesForcastMatItemAttachmentUIModelList = new ArrayList<SalesForcastMaterialItemAttachmentUIModel>();

	public SalesForcastMaterialItemUIModel getSalesForcastMaterialItemUIModel() {
		return this.salesForcastMaterialItemUIModel;
	}

	public void setSalesForcastMaterialItemUIModel(
			SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel) {
		this.salesForcastMaterialItemUIModel = salesForcastMaterialItemUIModel;
	}

	public List<SalesForcastMaterialItemAttachmentUIModel> getSalesForcastMatItemAttachmentUIModelList() {
		return salesForcastMatItemAttachmentUIModelList;
	}

	public void setSalesForcastMatItemAttachmentUIModelList(List<SalesForcastMaterialItemAttachmentUIModel> salesForcastMatItemAttachmentUIModelList) {
		this.salesForcastMatItemAttachmentUIModelList = salesForcastMatItemAttachmentUIModelList;
	}
}