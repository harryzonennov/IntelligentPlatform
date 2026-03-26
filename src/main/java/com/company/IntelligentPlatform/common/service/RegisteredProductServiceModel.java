package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.RegisteredProductActionLog;
import com.company.IntelligentPlatform.common.model.RegisteredProductAttachment;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.model.RegisteredProductInvolveParty;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class RegisteredProductServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RegisteredProduct.NODENAME, nodeInstId = RegisteredProduct.SENAME)
	protected RegisteredProduct registeredProduct;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductExtendProperty.NODENAME, nodeInstId = RegisteredProductExtendProperty.NODENAME)
	protected List<RegisteredProductExtendPropertyServiceModel> registeredProductExtendPropertyList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = RegisteredProductAttachment.NODENAME, nodeInstId = RegisteredProductAttachment.NODENAME)
	protected List<ServiceEntityNode> registeredProductAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = RegisteredProductActionLog.NODENAME, nodeInstId =
			RegisteredProductActionLog.NODEINST_ACTION_ACTIVE)
	protected RegisteredProductActionLog activeBy;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductActionLog.NODENAME, nodeInstId =
			RegisteredProductActionLog.NODEINST_ACTION_INPROCESS)
	protected RegisteredProductActionLog inProcessBy;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductActionLog.NODENAME, nodeInstId =
			RegisteredProductActionLog.NODEINST_ACTION_ARCHIVE)
	protected RegisteredProductActionLog archivedBy;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_SALESBY)
	protected RegisteredProductInvolveParty salesBy;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_SALESTO)
	protected RegisteredProductInvolveParty salesTo;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_PURCHASEBY)
	protected RegisteredProductInvolveParty purchaseBy;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_PURCHASEFROM)
	protected RegisteredProductInvolveParty purchaseFrom;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_PRODUCTBY)
	protected RegisteredProductInvolveParty productBy;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_SUPPORTBY)
	protected RegisteredProductInvolveParty supportBy;

	public RegisteredProduct getRegisteredProduct() {
		return this.registeredProduct;
	}

	public void setRegisteredProduct(RegisteredProduct registeredProduct) {
		this.registeredProduct = registeredProduct;
	}

	public List<RegisteredProductExtendPropertyServiceModel> getRegisteredProductExtendPropertyList() {
		return this.registeredProductExtendPropertyList;
	}

	public void setRegisteredProductExtendPropertyList(
			List<RegisteredProductExtendPropertyServiceModel> registeredProductExtendPropertyList) {
		this.registeredProductExtendPropertyList = registeredProductExtendPropertyList;
	}

	public List<ServiceEntityNode> getRegisteredProductAttachmentList() {
		return registeredProductAttachmentList;
	}

	public void setRegisteredProductAttachmentList(
			List<ServiceEntityNode> registeredProductAttachmentList) {
		this.registeredProductAttachmentList = registeredProductAttachmentList;
	}

	public RegisteredProductActionLog getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(RegisteredProductActionLog activeBy) {
		this.activeBy = activeBy;
	}

	public RegisteredProductActionLog getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(RegisteredProductActionLog inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public RegisteredProductActionLog getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(RegisteredProductActionLog archivedBy) {
		this.archivedBy = archivedBy;
	}

	public RegisteredProductInvolveParty getSalesBy() {
		return salesBy;
	}

	public void setSalesBy(RegisteredProductInvolveParty salesBy) {
		this.salesBy = salesBy;
	}

	public RegisteredProductInvolveParty getSalesTo() {
		return salesTo;
	}

	public void setSalesTo(RegisteredProductInvolveParty salesTo) {
		this.salesTo = salesTo;
	}

	public RegisteredProductInvolveParty getPurchaseBy() {
		return purchaseBy;
	}

	public void setPurchaseBy(RegisteredProductInvolveParty purchaseBy) {
		this.purchaseBy = purchaseBy;
	}

	public RegisteredProductInvolveParty getPurchaseFrom() {
		return purchaseFrom;
	}

	public void setPurchaseFrom(RegisteredProductInvolveParty purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}

	public RegisteredProductInvolveParty getProductBy() {
		return productBy;
	}

	public void setProductBy(RegisteredProductInvolveParty productBy) {
		this.productBy = productBy;
	}

	public RegisteredProductInvolveParty getSupportBy() {
		return supportBy;
	}

	public void setSupportBy(RegisteredProductInvolveParty supportBy) {
		this.supportBy = supportBy;
	}
}
