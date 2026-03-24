package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageI18n;


@Component
public class SerExtendPageI18nServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageI18n.NODENAME, nodeInstId = SerExtendPageI18n.NODENAME)
	protected SerExtendPageI18nUIModel serExtendPageI18nUIModel;

	public SerExtendPageI18nUIModel getSerExtendPageI18nUIModel() {
		return serExtendPageI18nUIModel;
	}

	public void setSerExtendPageI18nUIModel(SerExtendPageI18nUIModel serExtendPageI18nUIModel) {
		this.serExtendPageI18nUIModel = serExtendPageI18nUIModel;
	}
}
