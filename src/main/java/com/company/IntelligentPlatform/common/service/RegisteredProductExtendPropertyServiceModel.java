package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendPropertyAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RegisteredProductExtendPropertyServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RegisteredProductExtendProperty.NODENAME, nodeInstId = RegisteredProductExtendProperty.NODENAME)
	protected RegisteredProductExtendProperty registeredProductExtendProperty;

	@IServiceModuleFieldConfig(nodeName = RegisteredProductExtendPropertyAttachment.NODENAME, nodeInstId =
			RegisteredProductExtendPropertyAttachment.NODENAME)
	protected List<ServiceEntityNode> registeredProductExtendPropertyAttachmentList = new ArrayList<>();

	public RegisteredProductExtendProperty getRegisteredProductExtendProperty() {
		return registeredProductExtendProperty;
	}

	public void setRegisteredProductExtendProperty(RegisteredProductExtendProperty registeredProductExtendProperty) {
		this.registeredProductExtendProperty = registeredProductExtendProperty;
	}

	public List<ServiceEntityNode> getRegisteredProductExtendPropertyAttachmentList() {
		return registeredProductExtendPropertyAttachmentList;
	}

	public void setRegisteredProductExtendPropertyAttachmentList(
			List<ServiceEntityNode> registeredProductExtendPropertyAttachmentList) {
		this.registeredProductExtendPropertyAttachmentList = registeredProductExtendPropertyAttachmentList;
	}

}
