package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.SerialNumberSettingSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SerialNumberSettingSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected SerialNumberSettingManager serialNumberSettingManager;
	
	@Autowired
	protected BsearchService bsearchService;

	@Override
	public Class<?> getDocSearchModelCls() {
		return SerialNumberSettingSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.SystemResource;
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[SerialNumberSetting]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// start node:[MaterialType]
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(SerialNumberSetting.class).startNodeFlag(true).build());
		return searchNodeConfigList;
	}

}
