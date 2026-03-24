package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdPickingRefOrderItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingRefOrderItem.NODENAME, nodeInstId = ProdPickingRefOrderItem.NODENAME, 
			convToUIMethod = ProdPickingOrderManager.METHOD_ConvProdPickingOrderToUI, convUIToMethod = ProdPickingOrderManager.METHOD_ConvUIToProdPickingOrder)
	protected ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingRefMaterialItem.NODENAME, nodeInstId = ProdPickingRefMaterialItem.NODENAME)
	protected List<ProdPickingRefMaterialItemServiceUIModel> prodPickingRefMaterialItemUIModelList =
			new ArrayList<>();

	public List<ProdPickingRefMaterialItemServiceUIModel> getProdPickingRefMaterialItemUIModelList() {
		return prodPickingRefMaterialItemUIModelList;
	}

	public void setProdPickingRefMaterialItemUIModelList(
			List<ProdPickingRefMaterialItemServiceUIModel> prodPickingRefMaterialItemUIModelList) {
		this.prodPickingRefMaterialItemUIModelList = prodPickingRefMaterialItemUIModelList;
	}

	public ProdPickingRefOrderItemUIModel getProdPickingRefOrderItemUIModel() {
		return prodPickingRefOrderItemUIModel;
	}

	public void setProdPickingRefOrderItemUIModel(
			ProdPickingRefOrderItemUIModel prodPickingRefOrderItemUIModel) {
		this.prodPickingRefOrderItemUIModel = prodPickingRefOrderItemUIModel;
	}


}
