package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdPlanTargetMatItemUIModel;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProdPlanTarSubItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdPlanTargetMatItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdPlanTargetMatItem.NODENAME, nodeInstId = ProdPlanTargetMatItem.NODENAME)
	protected ProdPlanTargetMatItemUIModel prodPlanTargetMatItemUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = ProdPlanTarSubItem.NODENAME, nodeInstId = ProdPlanTarSubItem.NODENAME)
	protected List<ProdPlanTarSubItemUIModel> prodPlanTarSubItemUIModelList = new ArrayList<ProdPlanTarSubItemUIModel>();

	public ProdPlanTargetMatItemUIModel getProdPlanTargetMatItemUIModel() {
		return this.prodPlanTargetMatItemUIModel;
	}

	public void setProdPlanTargetMatItemUIModel(
			ProdPlanTargetMatItemUIModel prodPlanTargetMatItemUIModel) {
		this.prodPlanTargetMatItemUIModel = prodPlanTargetMatItemUIModel;
	}

	public List<ProdPlanTarSubItemUIModel> getProdPlanTarSubItemUIModelList() {
		return prodPlanTarSubItemUIModelList;
	}

	public void setProdPlanTarSubItemUIModelList(
			List<ProdPlanTarSubItemUIModel> prodPlanTarSubItemUIModelList) {
		this.prodPlanTarSubItemUIModelList = prodPlanTarSubItemUIModelList;
	}

}
