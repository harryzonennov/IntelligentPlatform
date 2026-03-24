package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;


@Component
public class ProdPickingRefMaterialItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstId =
			ProdPickingRefMaterialItem.NODENAME)
	protected ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel;

	public ProdPickingRefMaterialItemUIModel getProdPickingRefMaterialItemUIModel() {
		return prodPickingRefMaterialItemUIModel;
	}

	public void setProdPickingRefMaterialItemUIModel(
			ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel) {
		this.prodPickingRefMaterialItemUIModel = prodPickingRefMaterialItemUIModel;
	}
}
