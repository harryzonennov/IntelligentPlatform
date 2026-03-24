package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendPropertyAttachment;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

public class RegisteredProductExtendPropertyServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductExtendProperty.NODENAME, nodeInstId = RegisteredProductExtendProperty.NODENAME)
	protected RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel;

	@IServiceUIModuleFieldConfig(nodeName = RegisteredProductExtendPropertyAttachment.NODENAME, nodeInstId = RegisteredProductExtendPropertyAttachment.NODENAME)
	protected List<RegisteredProductExtendPropertyAttachmentUIModel> registeredProductExtendPropertyAttachmentUIModelList =
			new ArrayList<>();

	public RegisteredProductExtendPropertyUIModel getRegisteredProductExtendPropertyUIModel() {
		return registeredProductExtendPropertyUIModel;
	}

	public void setRegisteredProductExtendPropertyUIModel(
			RegisteredProductExtendPropertyUIModel registeredProductExtendPropertyUIModel) {
		this.registeredProductExtendPropertyUIModel = registeredProductExtendPropertyUIModel;
	}

	public List<RegisteredProductExtendPropertyAttachmentUIModel> getRegisteredProductExtendPropertyAttachmentUIModelList() {
		return registeredProductExtendPropertyAttachmentUIModelList;
	}

	public void setRegisteredProductExtendPropertyAttachmentUIModelList(
			List<RegisteredProductExtendPropertyAttachmentUIModel> registeredProductExtendPropertyAttachmentUIModelList) {
		this.registeredProductExtendPropertyAttachmentUIModelList =
				registeredProductExtendPropertyAttachmentUIModelList;
	}
}
