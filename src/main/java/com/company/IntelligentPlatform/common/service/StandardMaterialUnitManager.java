package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.dto.StandardMaterialUnitSearchModel;
import com.company.IntelligentPlatform.common.dto.StandardMaterialUnitUIModel;
// TODO-DAO: import ...StandardMaterialUnitDAO;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnitConfigureProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [StandardMaterialUnit]
 * 
 * @author
 * @date Tue Aug 11 16:57:07 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class StandardMaterialUnitManager extends ServiceEntityManager {

	public static final String METHOD_ConvStandardMaterialUnitToUI = "convStandardMaterialUnitToUI";

	public static final String METHOD_ConvUIToStandardMaterialUnit = "convUIToStandardMaterialUnit";

	public static final String METHOD_ConvRefMaterialUnitToUI = "convRefMaterialUnitToUI";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected StandardMaterialUnitDAO standardMaterialUnitDAO;

	@Autowired
	protected StandardMaterialUnitConfigureProxy standardMaterialUnitConfigureProxy;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected StandardMaterialUnitSearchProxy standardMaterialUnitSearchProxy;

	protected Map<String, Map<Integer, String>> unitTypeMapLan;

	protected Map<String, Map<Integer, String>> unitCategoryMapLan;
	
	protected Map<String, Map<Integer, String>> systemCategoryMapLan;

	protected Map<String, Map<String, StandardMaterialUnit>> standardMaterialUnitMap = new HashMap<>();

	public StandardMaterialUnitManager() {
		super.seConfigureProxy = new StandardMaterialUnitConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new StandardMaterialUnitDAO();
	}

	public Map<Integer, String> initUnitTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.unitTypeMapLan, StandardMaterialUnitUIModel.class,
				"unitType");
	}

	public Map<Integer, String> initSystemCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.systemCategoryMapLan, StandardMaterialUnitUIModel.class,
				"systemCategory");
	}

	public Map<Integer, String> initUnitCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.unitCategoryMapLan, StandardMaterialUnitUIModel.class,
				"unitCategory");
	}


	public Map<String, String> getStandardUnitMap(String client)
			throws ServiceEntityConfigureException {
		Map<String, String> standrdUnitMap = new HashMap<>();
		List<ServiceEntityNode> standardUnitList = getEntityNodeListByKey(null,
				null, StandardMaterialUnit.NODENAME, client, null);
		if (standardUnitList != null && standardUnitList.size() > 0) {
			for (ServiceEntityNode seNode : standardUnitList) {
				standrdUnitMap.put(seNode.getUuid(), seNode.getName());
			}
			return standrdUnitMap;
		}
		return null;
	}

	public void convStandardMaterialUnitToUI(
			StandardMaterialUnit standardMaterialUnit,
			StandardMaterialUnitUIModel standardMaterialUnitUIModel)
			throws ServiceEntityInstallationException {
		convStandardMaterialUnitToUI(standardMaterialUnit, standardMaterialUnitUIModel, null);
	}

	public void convStandardMaterialUnitToUI(
			StandardMaterialUnit standardMaterialUnit,
			StandardMaterialUnitUIModel standardMaterialUnitUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (standardMaterialUnit != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(standardMaterialUnit, standardMaterialUnitUIModel);
			standardMaterialUnitUIModel.setLanguageCode(standardMaterialUnit
					.getLanguageCode());
			standardMaterialUnitUIModel.setUnitType(standardMaterialUnit
					.getUnitType());
			if (logonInfo != null) {
				Map<Integer, String> unitTypeMap = this.initUnitTypeMap(logonInfo.getLanguageCode());
				standardMaterialUnitUIModel.setUnitTypeValue(unitTypeMap
						.get(standardMaterialUnit.getUnitCategory()));
				Map<Integer, String> unitCategoryMap =  this.initUnitCategoryMap(logonInfo.getLanguageCode());
				standardMaterialUnitUIModel
						.setUnitCategoryValue(unitCategoryMap
								.get(standardMaterialUnit.getUnitCategory()));
				Map<Integer, String>  systemCategoryMap = this.initSystemCategoryMap(logonInfo.getLanguageCode());
				standardMaterialUnitUIModel.setSystemCategoryValue(systemCategoryMap.get(standardMaterialUnit
						.getSystemCategory()));
			}
			standardMaterialUnitUIModel.setUnitCategory(standardMaterialUnit
					.getUnitCategory());
			standardMaterialUnitUIModel
					.setToReferUnitFactor(standardMaterialUnit
							.getToReferUnitFactor());
			standardMaterialUnitUIModel
					.setToReferUnitOffset(standardMaterialUnit
							.getToReferUnitOffset());
			standardMaterialUnitUIModel.setReferUnitUUID(standardMaterialUnit
					.getReferUnitUUID());
			standardMaterialUnitUIModel.setSystemCategory(standardMaterialUnit
					.getSystemCategory());
		}
	}

	public void convUIToStandardMaterialUnit(
			StandardMaterialUnitUIModel standardMaterialUnitUIModel, StandardMaterialUnit rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(standardMaterialUnitUIModel, rawEntity);
		rawEntity.setLanguageCode(standardMaterialUnitUIModel.getLanguageCode());
		rawEntity.setUnitType(standardMaterialUnitUIModel.getUnitType());
		rawEntity
				.setUnitCategory(standardMaterialUnitUIModel.getUnitCategory());
		rawEntity.setToReferUnitFactor(standardMaterialUnitUIModel
				.getToReferUnitFactor());
		rawEntity.setToReferUnitOffset(standardMaterialUnitUIModel
				.getToReferUnitOffset());
		rawEntity.setReferUnitUUID(standardMaterialUnitUIModel
				.getReferUnitUUID());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convRefMaterialUnitToUI(StandardMaterialUnit refMaterialUnit,
			StandardMaterialUnitUIModel standardMaterialUnitUIModel) {
		if (refMaterialUnit != null) {
			standardMaterialUnitUIModel.setRefMaterialUnitId(refMaterialUnit
					.getId());
			standardMaterialUnitUIModel.setRefMaterialUnitName(refMaterialUnit
					.getName());
		}
	}
	
	public List<ServiceEntityNode> searchInternal(
			StandardMaterialUnitSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// Search node:[refMaterialUnit]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(StandardMaterialUnit.SENAME);
		searchNodeConfig0.setNodeName(StandardMaterialUnit.NODENAME);
		searchNodeConfig0.setNodeInstID("refMaterialUnit");
		searchNodeConfig0.setStartNodeFlag(false);
		searchNodeConfig0
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig0.setMapBaseFieldName("referUnitUUID");
		searchNodeConfig0.setMapSourceFieldName("uuid");
		searchNodeConfig0.setBaseNodeInstID(StandardMaterialUnit.SENAME);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[standardMaterialUnit]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(StandardMaterialUnit.SENAME);
		searchNodeConfig2.setNodeName(StandardMaterialUnit.NODENAME);
		searchNodeConfig2.setNodeInstID(StandardMaterialUnit.SENAME);
		searchNodeConfig2.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig2);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(standardMaterialUnitDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(standardMaterialUnitConfigureProxy);
	}


	@Override
	public ServiceSearchProxy getSearchProxy() {
		return standardMaterialUnitSearchProxy;
	}
}
