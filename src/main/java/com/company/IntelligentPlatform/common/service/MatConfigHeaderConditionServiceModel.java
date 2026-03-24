package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class MatConfigHeaderConditionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MatConfigHeaderCondition.NODENAME, nodeInstId = MatConfigHeaderCondition.NODENAME)
	protected MatConfigHeaderCondition matConfigHeaderCondition;

	public MatConfigHeaderCondition getMatConfigHeaderCondition() {
		return matConfigHeaderCondition;
	}

	public void setMatConfigHeaderCondition(MatConfigHeaderCondition matConfigHeaderCondition) {
		this.matConfigHeaderCondition = matConfigHeaderCondition;
	}
	
}
