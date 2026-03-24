package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;

@Service
public class ServiceDocConfigureSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return ServiceDocConfigureSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return serviceDocConfigureManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException, ServiceEntityConfigureException,
			ServiceEntityInstallationException, LogonInfoException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getSearchComConfigureList();
		return bsearchService.doSearchWithContext(
				searchContext, searchNodeConfigList);
	}

	List<BSearchNodeComConfigure> getSearchComConfigureList() {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// Search node:[serviceDocConfigure]
		BSearchNodeComConfigure searchNodeConfig0= new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ServiceDocConfigure.SENAME);
		searchNodeConfig0.setNodeName(ServiceDocConfigure.NODENAME);
		searchNodeConfig0.setNodeInstID(ServiceDocConfigure.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[serviceDocConfigurePara]
		BSearchNodeComConfigure searchNodeConfig2= new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ServiceDocConfigurePara.SENAME);
		searchNodeConfig2.setNodeName(ServiceDocConfigurePara.NODENAME);
		searchNodeConfig2.setNodeInstID(ServiceDocConfigurePara.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[serviceDocConfigureParaGroup]
		BSearchNodeComConfigure searchNodeConfig4= new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(ServiceDocConfigureParaGroup.SENAME);
		searchNodeConfig4.setNodeName(ServiceDocConfigureParaGroup.NODENAME);
		searchNodeConfig4.setNodeInstID(ServiceDocConfigureParaGroup.NODENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig4.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// Search node:[searchProxyConfig]
		BSearchNodeComConfigure searchNodeConfig6= new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(SearchProxyConfig.SENAME);
		searchNodeConfig6.setNodeName(SearchProxyConfig.NODENAME);
		searchNodeConfig6.setNodeInstID(SearchProxyConfig.SENAME);
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig6.setMapBaseFieldName("refSearchProxyUUID");
		searchNodeConfig6.setMapSourceFieldName("uuid");
		searchNodeConfig6.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// Search node:[inputModule]
		BSearchNodeComConfigure searchNodeConfig8= new BSearchNodeComConfigure();
		searchNodeConfig8.setSeName(ServiceDocConsumerUnion.SENAME);
		searchNodeConfig8.setNodeName(ServiceDocConsumerUnion.NODENAME);
		searchNodeConfig8.setNodeInstID(ServiceDocConfigureSearchModel.NODEINS_ID_INPUTUNION);
		searchNodeConfig8.setStartNodeFlag(false);
		searchNodeConfig8.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig8.setMapBaseFieldName("inputUnionUUID");
		searchNodeConfig8.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig8.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		searchNodeConfigList.add(searchNodeConfig8);
		// Search node:[outputModule]
		BSearchNodeComConfigure searchNodeConfig10= new BSearchNodeComConfigure();
		searchNodeConfig10.setSeName(ServiceDocConsumerUnion.SENAME);
		searchNodeConfig10.setNodeName(ServiceDocConsumerUnion.NODENAME);
		searchNodeConfig10.setNodeInstID(ServiceDocConfigureSearchModel.NODEINS_ID_CONSUMEUNION);
		searchNodeConfig10.setStartNodeFlag(false);
		searchNodeConfig10.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig10.setMapBaseFieldName("consumerUnionUUID");
		searchNodeConfig10.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig10.setBaseNodeInstID(ServiceDocConfigure.SENAME);
		searchNodeConfigList.add(searchNodeConfig10);
		return searchNodeConfigList;
	}


}
