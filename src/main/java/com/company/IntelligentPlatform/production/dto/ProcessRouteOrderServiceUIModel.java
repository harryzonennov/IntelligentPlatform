package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProcessRouteOrderUIModel;
import com.company.IntelligentPlatform.production.dto.ProcessRouteProcessItemUIModel;
import com.company.IntelligentPlatform.production.service.ProcessRouteOrderManager;
import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProcessRouteOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProcessRouteOrder.NODENAME, nodeInstId = ProcessRouteOrder.SENAME, convToUIMethod = ProcessRouteOrderManager.METHOD_ConvProcessRouteOrderToUI, convUIToMethod = ProcessRouteOrderManager.METHOD_ConvUIToProcessRouteOrder)
	protected ProcessRouteOrderUIModel processRouteOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProcessRouteProcessItem.NODENAME, nodeInstId = ProcessRouteProcessItem.NODENAME, convToUIMethod = ProcessRouteOrderManager.METHOD_ConvProcessRouteProcessItemToUI, convUIToMethod = ProcessRouteOrderManager.METHOD_ConvUIToProcessRouteProcessItem)
	protected List<ProcessRouteProcessItemUIModel> processRouteProcessItemUIModelList = new ArrayList<ProcessRouteProcessItemUIModel>();

	public ProcessRouteOrderUIModel getProcessRouteOrderUIModel() {
		return this.processRouteOrderUIModel;
	}

	public void setProcessRouteOrderUIModel(
			ProcessRouteOrderUIModel processRouteOrderUIModel) {
		this.processRouteOrderUIModel = processRouteOrderUIModel;
	}

	public List<ProcessRouteProcessItemUIModel> getProcessRouteProcessItemUIModelList() {
		return this.processRouteProcessItemUIModelList;
	}

	public void setProcessRouteProcessItemUIModelList(
			List<ProcessRouteProcessItemUIModel> processRouteProcessItemUIModelList) {
		this.processRouteProcessItemUIModelList = processRouteProcessItemUIModelList;
	}

}
