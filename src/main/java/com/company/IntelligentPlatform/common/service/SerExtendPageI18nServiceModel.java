package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageI18n;

public class SerExtendPageI18nServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SerExtendPageI18n.NODENAME, nodeInstId = SerExtendPageI18n.NODENAME)
	protected SerExtendPageI18n srExtendPageI18n;

	public SerExtendPageI18n getSrExtendPageI18n() {
		return srExtendPageI18n;
	}

	public void setSrExtendPageI18n(SerExtendPageI18n srExtendPageI18n) {
		this.srExtendPageI18n = srExtendPageI18n;
	}
}
