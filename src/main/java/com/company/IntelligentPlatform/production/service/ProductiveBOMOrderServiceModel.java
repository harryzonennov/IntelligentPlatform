package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProductiveBOMItem;
import com.company.IntelligentPlatform.production.model.ProductiveBOMOrder;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductiveBOMOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductiveBOMOrder.NODENAME, nodeInstId = ProductiveBOMOrder.SENAME)
	protected ProductiveBOMOrder productiveBOMOrder;

	@IServiceModuleFieldConfig(nodeName = ProductiveBOMItem.NODENAME, nodeInstId = ProductiveBOMItem.NODENAME)
	protected List<ProductiveBOMItemServiceModel> productiveBOMItemList = new ArrayList<ProductiveBOMItemServiceModel>();

	public List<ProductiveBOMItemServiceModel> getProductiveBOMItemList() {
		return this.productiveBOMItemList;
	}

	public void setProductiveBOMItemList(
			List<ProductiveBOMItemServiceModel> productiveBOMItemList) {
		this.productiveBOMItemList = productiveBOMItemList;
	}

	public ProductiveBOMOrder getProductiveBOMOrder() {
		return this.productiveBOMOrder;
	}

	public void setProductiveBOMOrder(ProductiveBOMOrder productiveBOMOrder) {
		this.productiveBOMOrder = productiveBOMOrder;
	}

}
