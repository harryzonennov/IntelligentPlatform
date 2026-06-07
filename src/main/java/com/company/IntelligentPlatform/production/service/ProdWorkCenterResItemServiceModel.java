package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class ProdWorkCenterResItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdWorkCenterResItem.NODENAME, nodeInstId = ProdWorkCenterResItem.NODENAME)
	protected ProdWorkCenterResItem prodWorkCenterResItem;

	public ProdWorkCenterResItem getProdWorkCenterResItem() {
		return prodWorkCenterResItem;
	}

	public void setProdWorkCenterResItem(ProdWorkCenterResItem prodWorkCenterResItem) {
		this.prodWorkCenterResItem = prodWorkCenterResItem;
	}
}
