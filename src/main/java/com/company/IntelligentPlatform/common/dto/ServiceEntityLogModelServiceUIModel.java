package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogItemUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogModelUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceEntityLogModelManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogItem;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;

@Component
public class ServiceEntityLogModelServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceEntityLogModel.NODENAME, nodeInstId = ServiceEntityLogModel.SENAME, convToUIMethod = ServiceEntityLogModelManager.METHOD_ConvServiceEntityLogModelToUI)
	protected ServiceEntityLogModelUIModel serviceEntityLogModelUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ServiceEntityLogItem.NODENAME, nodeInstId = ServiceEntityLogItem.NODENAME, convToUIMethod = ServiceEntityLogModelManager.METHOD_ConvServiceEntityLogItemToUI)
	protected List<ServiceEntityLogItemUIModel> serviceEntityLogItemUIModelList = new ArrayList<ServiceEntityLogItemUIModel>();

	public ServiceEntityLogModelUIModel getServiceEntityLogModelUIModel() {
		return this.serviceEntityLogModelUIModel;
	}

	public void setServiceEntityLogModelUIModel(
			ServiceEntityLogModelUIModel serviceEntityLogModelUIModel) {
		this.serviceEntityLogModelUIModel = serviceEntityLogModelUIModel;
	}

	public List<ServiceEntityLogItemUIModel> getServiceEntityLogItemUIModelList() {
		return this.serviceEntityLogItemUIModelList;
	}

	public void setServiceEntityLogItemUIModelList(
			List<ServiceEntityLogItemUIModel> serviceEntityLogItemUIModelList) {
		this.serviceEntityLogItemUIModelList = serviceEntityLogItemUIModelList;
	}

}
