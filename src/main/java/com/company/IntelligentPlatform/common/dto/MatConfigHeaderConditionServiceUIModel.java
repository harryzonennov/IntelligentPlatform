package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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
