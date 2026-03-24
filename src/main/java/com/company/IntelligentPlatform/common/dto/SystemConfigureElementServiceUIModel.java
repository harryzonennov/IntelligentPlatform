package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureElementServiceModel;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.common.model.SystemConfigureUIField;

@Component
public class SystemConfigureElementServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureElement.NODENAME, nodeInstId = SystemConfigureElement.NODENAME)
	protected SystemConfigureElementUIModel systemConfigureElementUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureElement.NODENAME, nodeInstId = SystemConfigureElementServiceModel.NODEID_SUB_SYSTEMCONFIGUREELEMENT)
	protected List<SystemConfigureElementUIModel> systemConfigureElementUIModelList = new ArrayList<SystemConfigureElementUIModel>();
	
	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureUIField.NODENAME, nodeInstId = SystemConfigureUIField.NODENAME)
	protected List<SystemConfigureUIFieldUIModel> systemConfigureUIFieldUIModelList = new ArrayList<SystemConfigureUIFieldUIModel>();
	

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureExtensionUnion.NODENAME, nodeInstId =
			SystemConfigureExtensionUnion.NODENAME)
	protected List<SystemConfigureExtensionUnionUIModel> systemConfigureExtensionUnionUIModelList = new ArrayList<SystemConfigureExtensionUnionUIModel>();

	public SystemConfigureElementUIModel getSystemConfigureElementUIModel() {
		return systemConfigureElementUIModel;
	}

	public void setSystemConfigureElementUIModel(
			SystemConfigureElementUIModel systemConfigureElementUIModel) {
		this.systemConfigureElementUIModel = systemConfigureElementUIModel;
	}

	public List<SystemConfigureElementUIModel> getSystemConfigureElementUIModelList() {
		return systemConfigureElementUIModelList;
	}

	public void setSystemConfigureElementUIModelList(
			List<SystemConfigureElementUIModel> systemConfigureElementUIModelList) {
		this.systemConfigureElementUIModelList = systemConfigureElementUIModelList;
	}

	public List<SystemConfigureUIFieldUIModel> getSystemConfigureUIFieldUIModelList() {
		return systemConfigureUIFieldUIModelList;
	}

	public void setSystemConfigureUIFieldUIModelList(
			List<SystemConfigureUIFieldUIModel> systemConfigureUIFieldUIModelList) {
		this.systemConfigureUIFieldUIModelList = systemConfigureUIFieldUIModelList;
	}

	public List<SystemConfigureExtensionUnionUIModel> getSystemConfigureExtensionUnionUIModelList() {
		return systemConfigureExtensionUnionUIModelList;
	}

	public void setSystemConfigureExtensionUnionUIModelList(
			List<SystemConfigureExtensionUnionUIModel> systemConfigureExtensionUnionUIModelList) {
		this.systemConfigureExtensionUnionUIModelList = systemConfigureExtensionUnionUIModelList;
	}

}
