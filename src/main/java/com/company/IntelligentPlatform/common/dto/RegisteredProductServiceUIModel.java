package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class RegisteredProductServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProduct.NODENAME, nodeInstId = RegisteredProduct.SENAME, convToUIMethod = RegisteredProductManager.METHOD_ConvRegisteredProductToUI)
	protected RegisteredProductUIModel registeredProductUIModel;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductExtendProperty.NODENAME, nodeInstId = RegisteredProductExtendProperty.NODENAME)
	protected List<RegisteredProductExtendPropertyServiceUIModel> registeredProductExtendPropertyUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductAttachment.NODENAME, nodeInstId = RegisteredProductAttachment.NODENAME, 
			convToUIMethod = RegisteredProductManager.METHOD_ConvRegisteredProductAttachmentToUI)
	protected List<RegisteredProductAttachmentUIModel> registeredProductAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductActionLog.NODENAME, nodeInstId =
			RegisteredProductActionLog.NODEINST_ACTION_ACTIVE)
	protected RegisteredProductActionLogUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductActionLog.NODENAME, nodeInstId =
			RegisteredProductActionLog.NODEINST_ACTION_INPROCESS)
	protected RegisteredProductActionLogUIModel inProcessBy;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductActionLog.NODENAME, nodeInstId =
			RegisteredProductActionLog.NODEINST_ACTION_ARCHIVE)
	protected RegisteredProductActionLogUIModel archivedBy;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_SALESBY)
	protected RegisteredProductInvolvePartyUIModel salesBy;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_SALESTO)
	protected RegisteredProductInvolvePartyUIModel salesTo;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_PURCHASEBY)
	protected RegisteredProductInvolvePartyUIModel purchaseBy;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_PURCHASEFROM)
	protected RegisteredProductInvolvePartyUIModel purchaseFrom;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_PRODUCTBY)
	protected RegisteredProductInvolvePartyUIModel productBy;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductInvolveParty.NODENAME, nodeInstId =
			RegisteredProductInvolveParty.NODEINST_SUPPORTBY)
	protected RegisteredProductInvolvePartyUIModel supportBy;
    
	public RegisteredProductUIModel getRegisteredProductUIModel() {
		return this.registeredProductUIModel;
	}

	public void setRegisteredProductUIModel(
			RegisteredProductUIModel registeredProductUIModel) {
		this.registeredProductUIModel = registeredProductUIModel;
	}

	public List<RegisteredProductExtendPropertyServiceUIModel> getRegisteredProductExtendPropertyUIModelList() {
		return this.registeredProductExtendPropertyUIModelList;
	}

	public void setRegisteredProductExtendPropertyUIModelList(
			List<RegisteredProductExtendPropertyServiceUIModel> registeredProductExtendPropertyUIModelList) {
		this.registeredProductExtendPropertyUIModelList = registeredProductExtendPropertyUIModelList;
	}

	public List<RegisteredProductAttachmentUIModel> getRegisteredProductAttachmentUIModelList() {
		return registeredProductAttachmentUIModelList;
	}

	public void setRegisteredProductAttachmentUIModelList(
			List<RegisteredProductAttachmentUIModel> registeredProductAttachmentUIModelList) {
		this.registeredProductAttachmentUIModelList = registeredProductAttachmentUIModelList;
	}

	public RegisteredProductActionLogUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(RegisteredProductActionLogUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public RegisteredProductActionLogUIModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(RegisteredProductActionLogUIModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public RegisteredProductActionLogUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(RegisteredProductActionLogUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public RegisteredProductInvolvePartyUIModel getSalesBy() {
		return salesBy;
	}

	public void setSalesBy(RegisteredProductInvolvePartyUIModel salesBy) {
		this.salesBy = salesBy;
	}

	public RegisteredProductInvolvePartyUIModel getSalesTo() {
		return salesTo;
	}

	public void setSalesTo(RegisteredProductInvolvePartyUIModel salesTo) {
		this.salesTo = salesTo;
	}

	public RegisteredProductInvolvePartyUIModel getPurchaseBy() {
		return purchaseBy;
	}

	public void setPurchaseBy(RegisteredProductInvolvePartyUIModel purchaseBy) {
		this.purchaseBy = purchaseBy;
	}

	public RegisteredProductInvolvePartyUIModel getPurchaseFrom() {
		return purchaseFrom;
	}

	public void setPurchaseFrom(RegisteredProductInvolvePartyUIModel purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}

	public RegisteredProductInvolvePartyUIModel getProductBy() {
		return productBy;
	}

	public void setProductBy(RegisteredProductInvolvePartyUIModel productBy) {
		this.productBy = productBy;
	}

	public RegisteredProductInvolvePartyUIModel getSupportBy() {
		return supportBy;
	}

	public void setSupportBy(RegisteredProductInvolvePartyUIModel supportBy) {
		this.supportBy = supportBy;
	}
}
