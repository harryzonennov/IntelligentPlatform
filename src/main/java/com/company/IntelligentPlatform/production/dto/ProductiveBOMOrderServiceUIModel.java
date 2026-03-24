package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;
import com.company.IntelligentPlatform.production.model.ProductiveBOMOrder;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProductiveBOMOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProductiveBOMOrder.NODENAME, nodeInstId = ProductiveBOMOrder.SENAME)
	protected ProductiveBOMOrderUIModel productiveBOMOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProductiveBOMItem.NODENAME, nodeInstId = ProductiveBOMItem.NODENAME)
	protected List<ProductiveBOMItemServiceUIModel> productiveBOMItemUIModelList = new ArrayList<>();

	public ProductiveBOMOrderUIModel getProductiveBOMOrderUIModel() {
		return this.productiveBOMOrderUIModel;
	}

	public void setProductiveBOMOrderUIModel(
			ProductiveBOMOrderUIModel productiveBOMOrderUIModel) {
		this.productiveBOMOrderUIModel = productiveBOMOrderUIModel;
	}

	public List<ProductiveBOMItemServiceUIModel> getProductiveBOMItemUIModelList() {
		return this.productiveBOMItemUIModelList;
	}

	public void setProductiveBOMItemUIModelList(
			List<ProductiveBOMItemServiceUIModel> productiveBOMItemUIModelList) {
		this.productiveBOMItemUIModelList = productiveBOMItemUIModelList;
	}

}
