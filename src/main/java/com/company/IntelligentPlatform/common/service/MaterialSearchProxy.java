package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialSearchModel;
import com.company.IntelligentPlatform.common.dto.MaterialTypeSearchSubModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;
import java.util.Map;

@Service
public class MaterialSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialTypeSearchProxy materialTypeSearchProxy;

	@Override
	public Class<?> getDocSearchModelCls() {
		return MaterialSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return materialManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[material->materialUnitReference]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(Material.class,
						MaterialUnitReference.class);
		// search node [MaterialUnitReference->standardMaterialUnit]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(StandardMaterialUnit.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(MaterialUnitReference.NODENAME).build());
		// Search node:[material->corporateCustomer]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Material.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refMainSupplierUUID").baseNodeInstId(Material.SENAME).build());
		// Search node:[material->materialType]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialType.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refMaterialType").baseNodeInstId(Material.SENAME).build());
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialActionLog.class, MaterialActionLog.NODEINST_ACTION_SUBMIT,
				MaterialActionLog.DOC_ACTION_SUBMIT,
				null, Material.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialActionLog.class, MaterialActionLog.NODEINST_ACTION_APPROVE,
				MaterialActionLog.DOC_ACTION_APPROVE,
				null, Material.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialActionLog.class, MaterialActionLog.NODEINST_ACTION_ACTIVE,
				MaterialActionLog.DOC_ACTION_ACTIVE,
				null, Material.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialActionLog.class, MaterialActionLog.NODEINST_ACTION_REINIT,
				MaterialActionLog.DOC_ACTION_REINIT,
				null, Material.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialActionLog.class, MaterialActionLog.NODEINST_ACTION_ARCHIVE,
				MaterialActionLog.DOC_ACTION_ARCHIVE,
				null, Material.SENAME
		));
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(Material.class, null));
		return searchNodeConfigList;
	}

	@Override
	public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException,
			ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
			LogonInfoException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
		searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
		searchContext.setFuzzyFlag(true);
		MaterialSearchModel materialSearchModel = (MaterialSearchModel) searchContext.getSearchModel();
		MaterialTypeSearchSubModel materialTypeSearchSubModel = materialSearchModel != null ? materialSearchModel.getMaterialType(): null;
		return searchDocListCore(searchContext, materialTypeSearchSubModel, searchNodeConfigList);
	}

	public BSearchResponse searchDocListCore(SearchContext searchContext, MaterialTypeSearchSubModel materialTypeSearchSubModel,
											 List<BSearchNodeComConfigure> searchNodeConfigList)
			throws ServiceEntityInstallationException, SearchConfigureException, ServiceEntityConfigureException, LogonInfoException {
		SEUIComModel searchModel = searchContext.getSearchModel();
		if (searchModel == null || !materialTypeSearchProxy.checkSearchMaterialTypeHit(materialTypeSearchSubModel)) {
			return bsearchService.doSearchWithContext(
					searchContext, searchNodeConfigList);
		} else {
			String materialTypeUUIDMultiValue = materialTypeSearchProxy.getAllSubTypeUUIDMultiValue(
					materialTypeSearchSubModel,
					searchContext.getSearchAuthorizationContext(),
					searchContext.getClient());
			materialTypeSearchSubModel.setUuid(materialTypeUUIDMultiValue);
			//TODO checking fieldNameArray works or not
			searchContext.setFieldNameArray(new String[]{IServiceEntityNodeFieldConstant.UUID});
			return bsearchService.doSearchWithContext(
					searchContext, searchNodeConfigList);
		}
	}

}

