package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProcessRouteOrder;
import com.company.IntelligentPlatform.production.model.ProcessRouteProcessItem;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProcessRouteOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProcessRouteOrder.NODENAME, nodeInstId = ProcessRouteOrder.SENAME)
	protected ProcessRouteOrder processRouteOrder;

	@IServiceModuleFieldConfig(nodeName = ProcessRouteProcessItem.NODENAME, nodeInstId = ProcessRouteProcessItem.NODENAME)
	protected List<ServiceEntityNode> processRouteProcessItemList = new ArrayList<ServiceEntityNode>();

	public List<ServiceEntityNode> getProcessRouteProcessItemList() {
		return this.processRouteProcessItemList;
	}

	public void setProcessRouteProcessItemList(
			List<ServiceEntityNode> processRouteProcessItemList) {
		this.processRouteProcessItemList = processRouteProcessItemList;
	}

	public ProcessRouteOrder getProcessRouteOrder() {
		return this.processRouteOrder;
	}

	public void setProcessRouteOrder(ProcessRouteOrder processRouteOrder) {
		this.processRouteOrder = processRouteOrder;
	}

}
