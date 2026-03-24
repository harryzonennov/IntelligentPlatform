package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdWorkCenterResItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdWorkCenterResItem.NODENAME, nodeInstId = ProdWorkCenterResItem.NODENAME)
	protected ProdWorkCenterResItemUIModel prodWorkCenterResItemUIModel;

	public ProdWorkCenterResItemUIModel getProdWorkCenterResItemUIModel() {
		return prodWorkCenterResItemUIModel;
	}

	public void setProdWorkCenterResItemUIModel(ProdWorkCenterResItemUIModel prodWorkCenterResItemUIModel) {
		this.prodWorkCenterResItemUIModel = prodWorkCenterResItemUIModel;
	}
}
