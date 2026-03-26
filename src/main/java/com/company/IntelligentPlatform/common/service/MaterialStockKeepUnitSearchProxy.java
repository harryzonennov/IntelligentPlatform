package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitSearchModel;
import com.company.IntelligentPlatform.common.dto.MaterialTypeSearchSubModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;
import java.util.Map;

@Service
public class MaterialStockKeepUnitSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialTypeSearchProxy materialTypeSearchProxy;

	@Autowired
	protected MaterialSearchProxy materialSearchProxy;

	@Override
	public Class<?> getDocSearchModelCls() {
		return MaterialStockKeepUnitSearchModel.class;
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
	public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException,
			ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
			LogonInfoException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
		searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
		searchContext.setFuzzyFlag(true);
		MaterialStockKeepUnitSearchModel materialStockKeepUnitSearchModel = (MaterialStockKeepUnitSearchModel) searchContext.getSearchModel();
		materialStockKeepUnitSearchModel.setServiceEntityName(MaterialStockKeepUnit.SENAME);
		MaterialTypeSearchSubModel materialTypeSearchSubModel = materialStockKeepUnitSearchModel != null ? materialStockKeepUnitSearchModel.getMaterialType(): null;
		return materialSearchProxy.searchDocListCore(searchContext, materialTypeSearchSubModel, searchNodeConfigList);
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[materialSKU->materialSKUUnitReference]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(MaterialStockKeepUnit.class,
						MaterialSKUUnitReference.class);
		// Search node:[materialSKU->material]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Material.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(MaterialStockKeepUnit.FIELD_REF_MATERIALUUID).
				baseNodeInstId(MaterialStockKeepUnit.SENAME).build());
		// search node [MaterialUnitReference->standardMaterialUnit]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(StandardMaterialUnit.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(MaterialSKUUnitReference.NODENAME).build());
		// Search node:[materialSKU->corporateCustomer]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialStockKeepUnit.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("supplierUUID").
				baseNodeInstId(MaterialStockKeepUnit.SENAME).build());
		// Search node:[material->materialType]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialType.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refMaterialType").
				baseNodeInstId(Material.SENAME).build());
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialSKUActionLog.class, MaterialSKUActionLog.NODEINST_ACTION_SUBMIT,
				MaterialSKUActionLog.DOC_ACTION_SUBMIT,
				null, MaterialStockKeepUnit.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialSKUActionLog.class, MaterialSKUActionLog.NODEINST_ACTION_APPROVE,
				MaterialSKUActionLog.DOC_ACTION_APPROVE,
				null, MaterialStockKeepUnit.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialSKUActionLog.class, MaterialSKUActionLog.NODEINST_ACTION_ACTIVE,
				MaterialSKUActionLog.DOC_ACTION_ACTIVE,
				null, MaterialStockKeepUnit.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialSKUActionLog.class, MaterialSKUActionLog.NODEINST_ACTION_REINIT,
				MaterialSKUActionLog.DOC_ACTION_REINIT,
				null, MaterialStockKeepUnit.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				MaterialSKUActionLog.class, MaterialSKUActionLog.NODEINST_ACTION_ARCHIVE,
				MaterialSKUActionLog.DOC_ACTION_ARCHIVE,
				null, MaterialStockKeepUnit.SENAME
		));
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(MaterialStockKeepUnit.class, null));
		return searchNodeConfigList;
	}

	@Override
	public BSearchResponse searchOnline(SearchContext searchContext)
			throws SearchConfigureException {
		try {
			List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
			searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
			searchContext.setFuzzyFlag(true);
			MaterialStockKeepUnitSearchModel materialStockKeepUnitSearchModel = (MaterialStockKeepUnitSearchModel) searchContext.getSearchModel();
			MaterialTypeSearchSubModel materialTypeSearchSubModel = materialStockKeepUnitSearchModel != null ? materialStockKeepUnitSearchModel.getMaterialType(): null;
			return searchOnlineCore(searchContext, materialTypeSearchSubModel, searchNodeConfigList);
		} catch (ServiceEntityConfigureException | LogonInfoException | ServiceEntityInstallationException e) {
			throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getErrorMessage());
		}
	}

	public BSearchResponse searchOnlineCore(SearchContext searchContext, MaterialTypeSearchSubModel materialTypeSearchSubModel,
											 List<BSearchNodeComConfigure> searchNodeConfigList)
			throws ServiceEntityInstallationException, SearchConfigureException, ServiceEntityConfigureException, LogonInfoException {
		SEUIComModel searchModel = searchContext.getSearchModel();
		List<ServiceEntityNode> allSEList = searchContext.getRawSEList();
		if (searchModel == null || !materialTypeSearchProxy.checkSearchMaterialTypeHit(materialTypeSearchSubModel)) {
			try {
				return bsearchService.doSearchOnline(
						searchContext, searchNodeConfigList, allSEList);
			} catch (ServiceEntityConfigureException | LogonInfoException | ServiceEntityInstallationException e) {
				throw new RuntimeException(e);
			}
		} else {
			String materialTypeUUIDMultiValue = materialTypeSearchProxy.getAllSubTypeUUIDMultiValue(
					materialTypeSearchSubModel,
					searchContext.getSearchAuthorizationContext(),
					searchContext.getClient());
			//TODO checking fieldNameArray works or not
			materialTypeSearchSubModel.setUuid(materialTypeUUIDMultiValue);
			searchContext.setFieldNameArray(new String[]{IServiceEntityNodeFieldConstant.UUID});
			return bsearchService.doSearchOnline(
					searchContext, searchNodeConfigList, allSEList);
		}
	}

}

