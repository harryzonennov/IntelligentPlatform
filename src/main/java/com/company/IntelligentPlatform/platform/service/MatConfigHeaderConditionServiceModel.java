package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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
