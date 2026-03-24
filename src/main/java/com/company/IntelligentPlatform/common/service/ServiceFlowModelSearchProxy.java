package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;

import java.util.List;
import java.util.Map;

@Service
public class ServiceFlowModelSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected ServiceFlowModelManager serviceFlowModelManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceFlowModelServiceModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return serviceFlowModelManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[ServiceFlowModel root node]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(ServiceFlowModel.class,
						ServiceFlowCondGroup.class);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(ServiceFlowCondField.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
				baseNodeInstId(ServiceFlowCondGroup.NODENAME).build());
		// search node [ServiceFlowModel->FlowRouter]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(FlowRouter.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refRouterUUID").
				baseNodeInstId(ServiceFlowModel.SENAME).build());
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(ServiceFlowModel.class, null));
		return searchNodeConfigList;
	}

}
