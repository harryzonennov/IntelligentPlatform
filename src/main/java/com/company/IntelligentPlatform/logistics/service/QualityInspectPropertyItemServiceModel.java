package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.QualityInspectPropertyItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class QualityInspectPropertyItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = QualityInspectPropertyItem.NODENAME, nodeInstId = QualityInspectPropertyItem.NODENAME,docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected QualityInspectPropertyItem qualityInspectMatItem;

	public QualityInspectPropertyItem getQualityInspectMatItem() {
		return qualityInspectMatItem;
	}

	public void setQualityInspectMatItem(QualityInspectPropertyItem qualityInspectMatItem) {
		this.qualityInspectMatItem = qualityInspectMatItem;
	}
}
