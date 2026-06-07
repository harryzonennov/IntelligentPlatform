package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

public class MatConfigHeaderConditionServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MatConfigHeaderCondition.NODENAME, nodeInstId =
			MatConfigHeaderCondition.NODENAME)
	protected MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel;

	public MatConfigHeaderConditionUIModel getMatConfigHeaderConditionUIModel() {
		return matConfigHeaderConditionUIModel;
	}

	public void setMatConfigHeaderConditionUIModel(MatConfigHeaderConditionUIModel matConfigHeaderConditionUIModel) {
		this.matConfigHeaderConditionUIModel = matConfigHeaderConditionUIModel;
	}
}
