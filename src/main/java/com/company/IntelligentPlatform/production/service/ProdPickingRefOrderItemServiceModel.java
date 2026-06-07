package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class ProdPickingRefOrderItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdPickingRefOrderItem.NODENAME, nodeInstId = ProdPickingRefOrderItem.NODENAME)
	protected ProdPickingRefOrderItem prodPickingRefOrderItem;

	@IServiceModuleFieldConfig(nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstId = ProdPickingRefMaterialItem.NODENAME)
	protected List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList = new ArrayList<>();

	public ProdPickingRefOrderItem getProdPickingRefOrderItem() {
		return prodPickingRefOrderItem;
	}

	public void setProdPickingRefOrderItem(
			ProdPickingRefOrderItem prodPickingRefOrderItem) {
		this.prodPickingRefOrderItem = prodPickingRefOrderItem;
	}

	public List<ProdPickingRefMaterialItemServiceModel> getProdPickingRefMaterialItemList() {
		return prodPickingRefMaterialItemList;
	}

	public void setProdPickingRefMaterialItemList(
			List<ProdPickingRefMaterialItemServiceModel> prodPickingRefMaterialItemList) {
		this.prodPickingRefMaterialItemList = prodPickingRefMaterialItemList;
	}
}
