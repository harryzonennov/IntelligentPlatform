package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProductiveBOMItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProductiveBOMItem.NODENAME, nodeInstId = ProductiveBOMItem.NODENAME)
	protected ProductiveBOMItemUIModel productiveBOMItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProductiveBOMItem.NODENAME, nodeInstId = ProductiveBOMItem.NODENAME)
	protected List<ProductiveBOMItemServiceUIModel> subProductiveBOMItemUIModelList = new ArrayList<>();

	public ProductiveBOMItemUIModel getProductiveBOMItemUIModel() {
		return productiveBOMItemUIModel;
	}

	public void setProductiveBOMItemUIModel(
			ProductiveBOMItemUIModel productiveBOMItemUIModel) {
		this.productiveBOMItemUIModel = productiveBOMItemUIModel;
	}

	public List<ProductiveBOMItemServiceUIModel> getSubProductiveBOMItemUIModelList() {
		return subProductiveBOMItemUIModelList;
	}

	public void setSubProductiveBOMItemUIModelList(
			List<ProductiveBOMItemServiceUIModel> subProductiveBOMItemUIModelList) {
		this.subProductiveBOMItemUIModelList = subProductiveBOMItemUIModelList;
	}

}
