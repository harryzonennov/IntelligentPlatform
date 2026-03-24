package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureParaGroupUIModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureParaUIModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureUIModel;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;

@Component
public class ServiceDocConfigureServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocConfigure.NODENAME, nodeInstId = ServiceDocConfigure.SENAME, convToUIMethod = ServiceDocConfigureManager.METHOD_ConvServiceDocConfigureToUI, convUIToMethod = ServiceDocConfigureManager.METHOD_ConvUIToServiceDocConfigure)
	protected ServiceDocConfigureUIModel serviceDocConfigureUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocConfigurePara.NODENAME, nodeInstId = ServiceDocConfigurePara.NODENAME, convToUIMethod = ServiceDocConfigureManager.METHOD_ConvServiceDocConfigureParaToUI, convUIToMethod = ServiceDocConfigureManager.METHOD_ConvUIToServiceDocConfigurePara)
	protected List<ServiceDocConfigureParaUIModel> serviceDocConfigureParaUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocConfigureParaGroup.NODENAME, nodeInstId = ServiceDocConfigureParaGroup.NODENAME, convToUIMethod = ServiceDocConfigureManager.METHOD_ConvServiceDocConfigureParaGroupToUI, convUIToMethod = ServiceDocConfigureManager.METHOD_ConvUIToServiceDocConfigureParaGroup)
	protected List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupUIModelList = new ArrayList<>();
	
	protected List<ServiceDocConfigureParaUIModel> inputParaUIModelList = new ArrayList<>();
	
	protected List<ServiceDocConfigureParaUIModel> outputParaUIModelList  = new ArrayList<>();

	public ServiceDocConfigureUIModel getServiceDocConfigureUIModel() {
		return this.serviceDocConfigureUIModel;
	}

	public void setServiceDocConfigureUIModel(
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		this.serviceDocConfigureUIModel = serviceDocConfigureUIModel;
	}

	public List<ServiceDocConfigureParaUIModel> getServiceDocConfigureParaUIModelList() {
		return this.serviceDocConfigureParaUIModelList;
	}

	public void setServiceDocConfigureParaUIModelList(
			List<ServiceDocConfigureParaUIModel> serviceDocConfigureParaUIModelList) {
		this.serviceDocConfigureParaUIModelList = serviceDocConfigureParaUIModelList;
	}

	public List<ServiceDocConfigureParaGroupUIModel> getServiceDocConfigureParaGroupUIModelList() {
		return this.serviceDocConfigureParaGroupUIModelList;
	}

	public void setServiceDocConfigureParaGroupUIModelList(
			List<ServiceDocConfigureParaGroupUIModel> serviceDocConfigureParaGroupUIModelList) {
		this.serviceDocConfigureParaGroupUIModelList = serviceDocConfigureParaGroupUIModelList;
	}

	public List<ServiceDocConfigureParaUIModel> getInputParaUIModelList() {
		return inputParaUIModelList;
	}

	public void setInputParaUIModelList(
			List<ServiceDocConfigureParaUIModel> inputParaUIModelList) {
		this.inputParaUIModelList = inputParaUIModelList;
	}

	public List<ServiceDocConfigureParaUIModel> getOutputParaUIModelList() {
		return outputParaUIModelList;
	}

	public void setOutputParaUIModelList(
			List<ServiceDocConfigureParaUIModel> outputParaUIModelList) {
		this.outputParaUIModelList = outputParaUIModelList;
	}

}
