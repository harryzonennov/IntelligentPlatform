package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdOrderTarSubItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderTarSubItem.NODENAME, nodeInstId = ProdOrderTarSubItem.NODENAME)
	protected ProdOrderTarSubItemUIModel prodOrderTarSubItemUIModel;

	public ProdOrderTarSubItemUIModel getProdOrderTarSubItemUIModel() {
		return prodOrderTarSubItemUIModel;
	}

	public void setProdOrderTarSubItemUIModel(ProdOrderTarSubItemUIModel prodOrderTarSubItemUIModel) {
		this.prodOrderTarSubItemUIModel = prodOrderTarSubItemUIModel;
	}

}
