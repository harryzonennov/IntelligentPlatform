package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.QualityInspectPropertyItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class QualityInspectPropertyItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = QualityInspectPropertyItem.NODENAME, nodeInstId = QualityInspectPropertyItem.NODENAME)
	protected QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel;

	public QualityInspectPropertyItemUIModel getQualityInspectPropertyItemUIModel() {
		return qualityInspectPropertyItemUIModel;
	}

	public void setQualityInspectPropertyItemUIModel(QualityInspectPropertyItemUIModel qualityInspectPropertyItemUIModel) {
		this.qualityInspectPropertyItemUIModel = qualityInspectPropertyItemUIModel;
	}
}
