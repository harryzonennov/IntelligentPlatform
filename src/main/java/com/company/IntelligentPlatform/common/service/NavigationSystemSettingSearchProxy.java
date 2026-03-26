package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.NavigationItemSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NavigationSystemSettingSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;
	
	@Autowired
	protected BsearchService bsearchService;

	@Override
	public Class<?> getDocSearchModelCls() {
		return NavigationSystemSettingSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return NavigationItemSettingSearchModel.class;
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
		// start node:[NavigationSystemSetting]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// start node:[NavigationSystemSetting]
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(NavigationSystemSetting.class).startNodeFlag(true).build());
		return searchNodeConfigList;
	}
	@Override
	public String[] getItemFieldNameArray() {
		return new String[]{ "keywords", "id", "name" };
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[NavigationSystemSetting]
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// start node:[NavigationItemSetting]
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(NavigationItemSetting.class).startNodeFlag(true).build());
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(NavigationGroupSetting.class).baseNodeInstId(NavigationItemSetting.NODENAME).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).build());
		searchNodeConfigList.add(new BSearchNodeComConfigureBuilder().modelClass(NavigationSystemSetting.class).baseNodeInstId(NavigationGroupSetting.NODENAME).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD).build());
		return searchNodeConfigList;
	}

	public BSearchResponse searchKeywords(
			SearchContext searchContext)
			throws SearchConfigureException, ServiceEntityConfigureException,ServiceEntityInstallationException {
		NavigationItemSettingSearchModel navigationItemSettingSearchModel = (NavigationItemSettingSearchModel) searchContext.getSearchModel();
		List<BSearchNodeComConfigure> searchNodeConfigList = getBasicItemSearchNodeConfigureList(searchContext);
		String[] fieldNameArray = { "keywords", "id", "name" };
		if (ServiceEntityStringHelper
				.checkNullString(navigationItemSettingSearchModel.getKeywords())) {
			return BsearchService.genSearchResponse(null, 0);
		}
		navigationItemSettingSearchModel.setStatus(NavigationSystemSetting.STATUS_ACTIVE);
		List<ServiceEntityNode> resultList = new ArrayList<>();
		Map<String, List<?>> multiValueMap = bsearchService
				.generateMulitpleSearchMap(navigationItemSettingSearchModel, fieldNameArray);
		// Search node:[defaultChildItemSetting]
		List<ServiceEntityNode> resultList1 = bsearchService.doSearch(
				navigationItemSettingSearchModel, searchNodeConfigList, multiValueMap, searchContext.getClient(), true);
		if (!ServiceCollectionsHelper.checkNullList(resultList1)) {
			resultList.addAll(resultList1);
		}
		navigationItemSettingSearchModel.setName(navigationItemSettingSearchModel.getKeywords());
		navigationItemSettingSearchModel.setKeywords("");
		searchNodeConfigList = getBasicItemSearchNodeConfigureList(searchContext);
		List<ServiceEntityNode> resultList2 = bsearchService.doSearch(
				navigationItemSettingSearchModel, searchNodeConfigList, multiValueMap, searchContext.getClient(), true);
		if (!ServiceCollectionsHelper.checkNullList(resultList2)) {
			resultList = bsearchService.innerjoinResultList(resultList, resultList2);
		}
		return BsearchService.genSearchResponse(resultList, 0);
	}

}
