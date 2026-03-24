package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.common.model.SystemConfigureUIField;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class SystemConfigureElementServiceModel extends ServiceModule {
	
    public static final String NODEID_SUB_SYSTEMCONFIGUREELEMENT = "subSystemConfigureElement";

	@IServiceModuleFieldConfig(nodeName = SystemConfigureElement.NODENAME, nodeInstId = SystemConfigureElement.NODENAME)
	protected SystemConfigureElement systemConfigureElement;

	@IServiceModuleFieldConfig(nodeName = SystemConfigureUIField.NODENAME, nodeInstId = SystemConfigureUIField.NODENAME)
	protected List<ServiceEntityNode> systemConfigureUIFieldList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = SystemConfigureElement.NODENAME, nodeInstId = NODEID_SUB_SYSTEMCONFIGUREELEMENT)
	protected List<ServiceEntityNode> systemConfigureElementList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SystemConfigureExtensionUnion.NODENAME, nodeInstId = SystemConfigureExtensionUnion.NODENAME)
	protected List<ServiceEntityNode> systemConfigureExtensionUnionList = new ArrayList<>();

	
	public SystemConfigureElement getSystemConfigureElement() {
		return systemConfigureElement;
	}

	public void setSystemConfigureElement(
			SystemConfigureElement systemConfigureElement) {
		this.systemConfigureElement = systemConfigureElement;
	}

	public List<ServiceEntityNode> getSystemConfigureUIFieldList() {
		return systemConfigureUIFieldList;
	}

	public void setSystemConfigureUIFieldList(
			List<ServiceEntityNode> systemConfigureUIFieldList) {
		this.systemConfigureUIFieldList = systemConfigureUIFieldList;
	}

	public List<ServiceEntityNode> getSystemConfigureExtensionUnionList() {
		return systemConfigureExtensionUnionList;
	}

	public void setSystemConfigureExtensionUnionList(
			List<ServiceEntityNode> systemConfigureExtensionUnionList) {
		this.systemConfigureExtensionUnionList = systemConfigureExtensionUnionList;
	}

	public List<ServiceEntityNode> getSystemConfigureElementList() {
		return systemConfigureElementList;
	}

	public void setSystemConfigureElementList(
			List<ServiceEntityNode> systemConfigureElementList) {
		this.systemConfigureElementList = systemConfigureElementList;
	}

}
