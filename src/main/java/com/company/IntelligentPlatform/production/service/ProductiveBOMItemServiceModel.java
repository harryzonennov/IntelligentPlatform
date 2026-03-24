package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductiveBOMItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductiveBOMItem.NODENAME, nodeInstId = ProductiveBOMItem.NODENAME)
	protected ProductiveBOMItem productiveBOMItem;

	@IServiceModuleFieldConfig(nodeName = ProductiveBOMItem.NODENAME, nodeInstId = ProductiveBOMItem.NODENAME)
	protected List<ProductiveBOMItemServiceModel> subProductiveBOMItemList = new ArrayList<ProductiveBOMItemServiceModel>();

	public ProductiveBOMItem getProductiveBOMItem() {
		return productiveBOMItem;
	}

	public void setProductiveBOMItem(ProductiveBOMItem productiveBOMItem) {
		this.productiveBOMItem = productiveBOMItem;
	}

	public List<ProductiveBOMItemServiceModel> getSubProductiveBOMItemList() {
		return subProductiveBOMItemList;
	}

	public void setSubProductiveBOMItemList(
			List<ProductiveBOMItemServiceModel> subProductiveBOMItemList) {
		this.subProductiveBOMItemList = subProductiveBOMItemList;
	}

}
