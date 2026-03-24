package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.StandardMaterialUnitSearchModel;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StandardMaterialUnitSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return StandardMaterialUnitSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return standardMaterialUnitManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		searchNodeConfigList.add(
				new BSearchNodeComConfigureBuilder().modelClass(StandardMaterialUnit.class).startNodeFlag(true).build());
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(StandardMaterialUnit.class).nodeInstId("refMaterialUnit").mapFieldUUID("referUnitUUID").
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).baseNodeInstId(StandardMaterialUnit.SENAME).build());
		return searchNodeConfigList;
	}

}
