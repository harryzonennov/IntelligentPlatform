package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdPickingRefMaterialItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstId = ProdPickingRefMaterialItem.NODENAME)
	protected ProdPickingRefMaterialItem prodPickingRefMaterialItem;

	public ProdPickingRefMaterialItem getProdPickingRefMaterialItem() {
		return prodPickingRefMaterialItem;
	}

	public void setProdPickingRefMaterialItem(ProdPickingRefMaterialItem prodPickingRefMaterialItem) {
		this.prodPickingRefMaterialItem = prodPickingRefMaterialItem;
	}
}
