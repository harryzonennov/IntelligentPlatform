package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPlanTarSubItem;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdPlanTargetMatItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdPlanTargetMatItem.NODENAME, nodeInstId = ProdPlanTargetMatItem.NODENAME)
	protected ProdPlanTargetMatItem prodPlanTargetMatItem;

	@IServiceModuleFieldConfig(nodeName = ProdPlanTarSubItem.NODENAME, nodeInstId = ProdPlanTarSubItem.NODENAME)
	protected List<ServiceEntityNode> prodPlanTarSubItemList = new ArrayList<ServiceEntityNode>();

	public ProdPlanTargetMatItem getProdPlanTargetMatItem() {
		return prodPlanTargetMatItem;
	}

	public void setProdPlanTargetMatItem(ProdPlanTargetMatItem prodPlanTargetMatItem) {
		this.prodPlanTargetMatItem = prodPlanTargetMatItem;
	}

	public List<ServiceEntityNode> getProdPlanTarSubItemList() {
		return prodPlanTarSubItemList;
	}

	public void setProdPlanTarSubItemList(
			List<ServiceEntityNode> prodPlanTarSubItemList) {
		this.prodPlanTarSubItemList = prodPlanTarSubItemList;
	}

}
