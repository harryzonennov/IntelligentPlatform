package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.SystemCodeValueCollectionUIModel;
import com.company.IntelligentPlatform.common.dto.SystemCodeValueUnionUIModel;
// TODO-DAO: import ...SystemCodeValueCollectionDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollectionConfigureProxy;
import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class SystemCodeValueCollectionManager extends ServiceEntityManager {

	public static final String METHOD_ConvSystemCodeValueCollectionToUI = "convSystemCodeValueCollectionToUI";

	public static final String METHOD_ConvUIToSystemCodeValueCollection = "convUIToSystemCodeValueCollection";

	public static final String METHOD_ConvSystemCodeValueUnionToUI = "convSystemCodeValueUnionToUI";

	public static final String METHOD_ConvUIToSystemCodeValueUnion = "convUIToSystemCodeValueUnion";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected SystemCodeValueCollectionDAO systemCodeValueCollectionDAO;

	@Autowired
	protected SystemCodeValueCollectionConfigureProxy systemCodeValueCollectionConfigureProxy;

	@Autowired
	protected SystemCodeValueCollectionSearchProxy systemCodeValueCollectionSearchProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	private Map<String, Map<Integer, String>> keyTypeMapLan = new HashMap<>();

	private Map<String,Map<Integer, String>> collectionCategoryMapLan = new HashMap<>();

	private Map<String,Map<Integer, String>> collectionTypeMapLan = new HashMap<>();

	public List<ServiceEntityNode> loadRawCodeValueUnionList(String id,
			String client) throws ServiceEntityConfigureException,
			ServiceModuleProxyException {
		SystemCodeValueCollection systemCodeValueCollection = (SystemCodeValueCollection) getEntityNodeByKey(
				id, IServiceEntityNodeFieldConstant.ID,
				SystemCodeValueCollection.NODENAME, client, null,true);
		return loadRawCodeValueUnionList(systemCodeValueCollection);
	}

	public List<ServiceEntityNode> loadRawCodeValueUnionList(SystemCodeValueCollection systemCodeValueCollection) throws ServiceEntityConfigureException,
			ServiceModuleProxyException {
		if (systemCodeValueCollection == null) {
			return null;
		}
		SystemCodeValueCollectionServiceModel systemCodeValueCollectionServiceModel = (SystemCodeValueCollectionServiceModel) loadServiceModule(
				SystemCodeValueCollectionServiceModel.class,
				systemCodeValueCollection);
		return filterActiveValueUnionList(systemCodeValueCollectionServiceModel
				.getSystemCodeValueUnionList().stream().map(SystemCodeValueUnionServiceModel::getSystemCodeValueUnion).collect(Collectors.toList()));
	}

	public List<ServiceEntityNode> filterActiveValueUnionList(
			List<ServiceEntityNode> rawSystemCodeValueUnionList) {
		if (ServiceCollectionsHelper.checkNullList(rawSystemCodeValueUnionList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawSystemCodeValueUnionList) {
			SystemCodeValueUnion systemCodeValueUnion = (SystemCodeValueUnion) seNode;
			if (systemCodeValueUnion.getHideFlag() == StandardSwitchProxy.SWITCH_ON) {
				continue;
			}
			resultList.add(systemCodeValueUnion);
		}
		return resultList;
	}

	public Map<Integer, String> convertIntCodeValueUnionMap(
			List<ServiceEntityNode> systemCodeValueUnionList) {
		if (ServiceCollectionsHelper.checkNullList(systemCodeValueUnionList)) {
			return null;
		}
		Map<Integer, String> resultMap = new HashMap<>();
		for (ServiceEntityNode seNode : systemCodeValueUnionList) {
			SystemCodeValueUnion systemCodeValueUnion = (SystemCodeValueUnion) seNode;
			Integer key = Integer.valueOf(systemCodeValueUnion.getId());
			resultMap.put(key, systemCodeValueUnion.getName());
		}
		return resultMap;
	}

	public Map<String, String> convertStrCodeValueUnionMap(
			List<ServiceEntityNode> systemCodeValueUnionList) {
		if (ServiceCollectionsHelper.checkNullList(systemCodeValueUnionList)) {
			return null;
		}
		Map<String, String> resultMap = new HashMap<>();
		for (ServiceEntityNode seNode : systemCodeValueUnionList) {
			SystemCodeValueUnion systemCodeValueUnion = (SystemCodeValueUnion) seNode;
			resultMap.put(systemCodeValueUnion.getId(),
					systemCodeValueUnion.getName());
		}
		return resultMap;
	}

	/**
	 * Get SystemCodeValue instance by parent collection id and value id
	 * @param codeCollectionId
	 * @param codeValueId
	 * @return
	 */
	public SystemCodeValueUnion getCodeValueByValueId(String codeCollectionId, String codeValueId, String client) throws ServiceEntityConfigureException, ServiceModuleProxyException {
		SystemCodeValueCollection systemCodeValueCollection = (SystemCodeValueCollection) getEntityNodeByKey(
				codeCollectionId, IServiceEntityNodeFieldConstant.ID,
				SystemCodeValueCollection.NODENAME, client, null, true);
		if (systemCodeValueCollection == null) {
			return null;
		}
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(systemCodeValueCollection.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(codeValueId,
				IServiceEntityNodeFieldConstant.ID);
		return (SystemCodeValueUnion) getEntityNodeByKeyList(ServiceCollectionsHelper.asList(key1, key2),
				SystemCodeValueUnion.NODENAME, client, null);
	}

	public Map<Integer, String> initCollectionCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.collectionCategoryMapLan, SystemCodeValueCollectionUIModel.class,
				"collectionCategory");
	}

	public Map<Integer, String> initCollectionTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.collectionTypeMapLan, SystemCodeValueCollectionUIModel.class,
				"collectionType");
	}

	public Map<Integer, String> initKeyTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.keyTypeMapLan, SystemCodeValueCollectionUIModel.class,
				"keyType");
	}

	public Map<Integer, String> initHideFlagMap(String languageCode)
			throws ServiceEntityInstallationException {		
		return standardSwitchProxy.getSimpleSwitchMap(languageCode);
	}

	public void convSystemCodeValueCollectionToUI(
			SystemCodeValueCollection systemCodeValueCollection,
			SystemCodeValueCollectionUIModel systemCodeValueCollectionUIModel)
			throws ServiceEntityInstallationException {
		convSystemCodeValueCollectionToUI(systemCodeValueCollection, systemCodeValueCollectionUIModel);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSystemCodeValueCollectionToUI(
			SystemCodeValueCollection systemCodeValueCollection,
			SystemCodeValueCollectionUIModel systemCodeValueCollectionUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (systemCodeValueCollection != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(systemCodeValueCollection, systemCodeValueCollectionUIModel);
			systemCodeValueCollectionUIModel
					.setCollectionCategory(systemCodeValueCollection
							.getCollectionCategory());
			if (logonInfo != null) {
				Map<Integer, String> collectionCategoryMap = this.initCollectionCategoryMap(logonInfo.getLanguageCode());
				systemCodeValueCollectionUIModel
						.setCollectionCategoryValue(collectionCategoryMap
								.get(systemCodeValueCollection
										.getCollectionCategory()));
				Map<Integer, String> keyTypeMap = this.initKeyTypeMap(logonInfo.getLanguageCode());
				systemCodeValueCollectionUIModel.setKeyTypeValue(keyTypeMap
						.get(systemCodeValueCollection.getKeyType()));
				Map<Integer, String> collectionTypeMap = this.initCollectionTypeMap(logonInfo.getLanguageCode());
				systemCodeValueCollectionUIModel
						.setCollectionTypeValue(collectionTypeMap
								.get(systemCodeValueCollection.getCollectionType()));
			}
			systemCodeValueCollectionUIModel
					.setKeyType(systemCodeValueCollection.getKeyType());
			systemCodeValueCollectionUIModel
					.setCollectionType(systemCodeValueCollection
							.getCollectionType());
		}
	}

	/**
	 * [Internal method] Convert from UI model to SE
	 * model:systemCodeValueCollection
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSystemCodeValueCollection(
			SystemCodeValueCollectionUIModel systemCodeValueCollectionUIModel,
			SystemCodeValueCollection rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(systemCodeValueCollectionUIModel, rawEntity);
		rawEntity.setKeyType(systemCodeValueCollectionUIModel.getKeyType());
		if(systemCodeValueCollectionUIModel.getCollectionType() == SystemCodeValueCollection.COLTYPE_CUSTOM){
			rawEntity.setCollectionType(systemCodeValueCollectionUIModel
					.getCollectionType());
		}
		rawEntity.setCollectionCategory(systemCodeValueCollectionUIModel
				.getCollectionCategory());
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(systemCodeValueCollectionDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(systemCodeValueCollectionConfigureProxy);
	}

	public void convSystemCodeValueUnionToUI(
			SystemCodeValueUnion systemCodeValueUnion,
			SystemCodeValueUnionUIModel systemCodeValueUnionUIModel)
			throws ServiceEntityInstallationException {
		convSystemCodeValueUnionToUI(systemCodeValueUnion, systemCodeValueUnionUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convSystemCodeValueUnionToUI(
			SystemCodeValueUnion systemCodeValueUnion,
			SystemCodeValueUnionUIModel systemCodeValueUnionUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (systemCodeValueUnion != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(systemCodeValueUnion, systemCodeValueUnionUIModel);
			systemCodeValueUnionUIModel.setHideFlag(systemCodeValueUnion
					.getHideFlag());
			if (logonInfo != null) {
				Map<Integer, String> hideFlagMap = this.initHideFlagMap(logonInfo.getLanguageCode());
				systemCodeValueUnionUIModel.setHideFlagValue(hideFlagMap
						.get(systemCodeValueUnion.getHideFlag()));
				Map<Integer, String> collectionTypeMap = this.initCollectionTypeMap(logonInfo.getLanguageCode());
				systemCodeValueUnionUIModel
						.setUnionTypeValue(collectionTypeMap
								.get(systemCodeValueUnion.getUnionType()));
			}
			systemCodeValueUnionUIModel.setUnionType(systemCodeValueUnion
					.getUnionType());
			systemCodeValueUnionUIModel.setLanKey(systemCodeValueUnion.getLanKey());
			systemCodeValueUnionUIModel.setColorStyle(systemCodeValueUnion
					.getColorStyle());
			systemCodeValueUnionUIModel.setIconClass(systemCodeValueUnion
					.getIconClass());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:systemCodeValueUnion
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToSystemCodeValueUnion(
			SystemCodeValueUnionUIModel systemCodeValueUnionUIModel,
			SystemCodeValueUnion rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(systemCodeValueUnionUIModel, rawEntity);
		if (systemCodeValueUnionUIModel.getHideFlag() != 0) {
			rawEntity.setHideFlag(systemCodeValueUnionUIModel.getHideFlag());
		}
		rawEntity.setKeyType(systemCodeValueUnionUIModel.getKeyType());
		if(systemCodeValueUnionUIModel.getUnionType() == SystemCodeValueCollection.COLTYPE_CUSTOM){
			rawEntity.setUnionType(systemCodeValueUnionUIModel
					.getUnionType());
		}
		if(!ServiceEntityStringHelper.checkNullString(systemCodeValueUnionUIModel.getLanKey())){
			rawEntity.setLanKey(systemCodeValueUnionUIModel
					.getLanKey());
		}
		rawEntity.setColorStyle(systemCodeValueUnionUIModel.getColorStyle());
		rawEntity.setIconClass(systemCodeValueUnionUIModel.getIconClass());
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return systemCodeValueCollectionSearchProxy;
	}
}
