package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.MaterialTypeUIModel;
// TODO-DAO: import ...MaterialTypeDAO;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialTypeConfigureProxy;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [MaterialType]
 * 
 * @author
 * @date Tue Aug 11 16:49:53 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class MaterialTypeManager extends ServiceEntityManager {

	public static final String METHOD_ConvMaterialTypeToUI = "convMaterialTypeToUI";

	public static final String METHOD_ConvUIToMaterialType = "convUIToMaterialType";

	public static final String METHOD_ConvParentMaterialTypeToUI = "convParentTypeToUI";

	public static final String METHOD_ConvRootMaterialTypeToUI = "convRootTypeToUI";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected MaterialTypeDAO materialTypeDAO;

	@Autowired
	protected MaterialTypeConfigureProxy materialTypeConfigureProxy;

	@Autowired
	protected MaterialTypeIdHelper materialTypeIdHelper;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected MaterialTypeSearchProxy materialTypeSearchProxy;

	protected Logger logger = LoggerFactory.getLogger(MaterialTypeManager.class);

	private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

	protected Map<String, MaterialType> materialTypeMap;
	

	/**
	 * Logic to get all sub material type list from current material type
	 * 
	 * @param keyList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllSubTypeByKeyList(
			List<ServiceBasicKeyStructure> keyList, String client)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawTypeList = getEntityNodeListByKeyList(
				keyList, MaterialType.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(rawTypeList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<ServiceEntityNode> allRawList = getEntityNodeListByKey(null, null,
				MaterialType.NODENAME, client, null, true);
		for (ServiceEntityNode seNode : rawTypeList) {
			MaterialType materialType = (MaterialType) seNode;
			resultList.add(materialType);
			List<ServiceEntityNode> tmpSubTypeList = getAllSubType(
					materialType, allRawList);
			if (tmpSubTypeList != null && tmpSubTypeList.size() > 0) {
				ServiceCollectionsHelper
						.mergeToList(resultList, tmpSubTypeList);
			}
		}
		return resultList;
	}

	/**
	 * Get Recursive MaterialType List until root node
	 * 
	 * @param uuid
	 * @param client
	 * @param rawMaterialTypeList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getRecursiveMaterialTypeWrapper(String uuid,
			String client, List<ServiceEntityNode> rawMaterialTypeList)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> locAllMaterialTypeList = rawMaterialTypeList;
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (ServiceEntityStringHelper.checkNullString(uuid)) {
			return resultList;
		}
		if (ServiceCollectionsHelper.checkNullList(locAllMaterialTypeList)) {
			locAllMaterialTypeList = this.getEntityNodeListByKey(null, null,
					MaterialType.NODENAME, client, null);
		}
		MaterialType directMaterialType = getMaterialTypeWrapper(uuid, client,
				locAllMaterialTypeList);
		if (directMaterialType == null) {
			return resultList;
		} else {
			resultList.add(directMaterialType);
			String parentTypeUUID = directMaterialType.getParentTypeUUID();
			if (ServiceEntityStringHelper.checkNullString(parentTypeUUID)) {
				return resultList;
			}
			if (parentTypeUUID.equals(directMaterialType.getUuid())) {
				return resultList;
			}
			List<ServiceEntityNode> parentTypeList = getRecursiveMaterialTypeWrapper(
					parentTypeUUID, client, rawMaterialTypeList);
			if (!ServiceCollectionsHelper.checkNullList(parentTypeList)) {
				resultList.addAll(parentTypeList);
			}
			return resultList;
		}
	}

	/**
	 * Get Material Type instance online, from cache, or finally from DB
	 * persistence
	 * 
	 * @param uuid
	 * @param client
	 * @param rawMaterialTypeList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public MaterialType getMaterialTypeWrapper(String uuid, String client,
			List<ServiceEntityNode> rawMaterialTypeList)
			throws ServiceEntityConfigureException {
		if (!ServiceCollectionsHelper.checkNullList(rawMaterialTypeList)) {
			for (ServiceEntityNode seNode : rawMaterialTypeList) {
				if (uuid.equals(seNode.getUuid())) {
					return (MaterialType) seNode;
				}
			}
		}
		// In case not find, trying to find from DB
		if (this.materialTypeMap == null) {
			this.materialTypeMap = new HashMap<>();
		}
		if (this.materialTypeMap.containsKey(uuid)) {
			return this.materialTypeMap.get(uuid);
		}
		// In case not find, then find from persistence
		MaterialType materialType = (MaterialType) getEntityNodeByKey(uuid,
				IServiceEntityNodeFieldConstant.UUID, MaterialType.NODENAME,
				client, null);
		this.materialTypeMap.put(uuid, materialType);
		return materialType;
	}

	public void convMaterialTypeToUI(MaterialType materialType,
									 MaterialTypeUIModel materialTypeUIModel)
			throws ServiceEntityInstallationException {

	}

	public Map<Integer, String> initStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusMapLan, MaterialTypeUIModel.class,
				"status");
	}

	public void convMaterialTypeToUI(MaterialType materialType,
									 MaterialTypeUIModel materialTypeUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (materialType != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(materialType, materialTypeUIModel);
			materialTypeUIModel.setParentTypeUUID(materialType
					.getParentTypeUUID());
			materialTypeUIModel.setStatus(materialType.getStatus());
			materialTypeUIModel.setSystemStandardCategory(materialType
					.getSystemStandardCategory());
			if(logonInfo != null){
				Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
				String statusValue = statusMap.get(materialType
						.getStatus());
				materialTypeUIModel.setStatusValue(statusValue);
				Map<Integer, String> systemStandardCategoryMap = standardSystemCategoryProxy
						.getSystemCategoryMap(logonInfo.getLanguageCode());
				String systemStandardCategoryValue = systemStandardCategoryMap
						.get(materialType.getSystemStandardCategory());
				materialTypeUIModel
						.setSystemStandardCategoryValue(systemStandardCategoryValue);
			}

		}
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(materialTypeDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(materialTypeConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		MaterialType MaterialType = (MaterialType) super
				.newRootEntityNode(client);
		String materialTypeId = materialTypeIdHelper.genDefaultId(client);
		MaterialType.setId(materialTypeId);
		return MaterialType;
	}

	public MaterialType newMaterialTypeFromParent(String parentTypeUUID, MaterialType rawMaterialType) throws MaterialTypeException,
			ServiceEntityConfigureException {
		MaterialType parentType = (MaterialType) getEntityNodeByUUID(
				rawMaterialType.getParentTypeUUID(),
				MaterialType.NODENAME, rawMaterialType.getClient());
		if (parentType != null) {
			setParentTypeLogic(rawMaterialType, parentType);
		}
		return rawMaterialType;
	}

	public List<ServiceModuleConvertPara> genMaterialTypeConvertParaList(SerialLogonInfo serialLogonInfo,
																		 String targetNodeInstId)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> materialTypeList = getRawMaterialTypeList(serialLogonInfo);
		ServiceModuleConvertPara serviceModuleConvertPara = new ServiceModuleConvertPara();
		serviceModuleConvertPara.setTargetNodeInstId(targetNodeInstId);
		serviceModuleConvertPara.setNodeInstId(targetNodeInstId);
		serviceModuleConvertPara.setServiceEntityList(materialTypeList);
		return ServiceCollectionsHelper.asList(serviceModuleConvertPara);
	}

	public List<ServiceEntityNode> getRawMaterialTypeList(SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException {
		return this
				.getEntityNodeListByKey(null, null,
						MaterialType.NODENAME, serialLogonInfo.getClient(), null);
	}

	/**
	 * Retrieves the root level material type from the current material type instance by recursively
	 * fetching its parent material type until the root level is reached.
	 *
	 * @param materialType The current material type instance.
	 * @param allMaterialTypeList [optional] A list of all material types.
	 * @return The root level MaterialType.
	 * @throws ServiceEntityConfigureException If an error occurs during the retrieval process.
	 */
	public MaterialType getRootMaterialType(MaterialType materialType, List<ServiceEntityNode> allMaterialTypeList) throws ServiceEntityConfigureException {
		if (checkRootType(materialType)) {
			return materialType;
		}
		MaterialType parentMaterialType;
		if (!ServiceCollectionsHelper.checkNullList(allMaterialTypeList)) {
			parentMaterialType = (MaterialType) ServiceCollectionsHelper.filterOnline(allMaterialTypeList, se-> materialType.getParentTypeUUID().equals(se.getUuid()));
		} else {
			parentMaterialType = (MaterialType) getEntityNodeByUUID(
					materialType.getParentTypeUUID(),
					MaterialType.NODENAME, materialType.getClient());
		}
		if (parentMaterialType == null) {
			return null;
		}
		return getRootMaterialType(parentMaterialType, allMaterialTypeList);
	}

	/**
	 * Checks if current material type is root level material type or not
	 * @param materialType
	 * @return
	 */
	public boolean checkRootType(MaterialType materialType) {
        return ServiceEntityStringHelper.checkNullString(materialType.getParentTypeUUID()) || materialType.getUuid().equals(materialType.getParentTypeUUID());
    }

	/**
	 * Core logic to set parent type && root type information from parent
	 * material type
	 * 
	 * @param materialType
	 * @param parentType
	 */
	public void setParentTypeLogic(MaterialType materialType,
			MaterialType parentType) throws ServiceEntityConfigureException {
		if (materialType != null && parentType != null) {
			materialType.setParentTypeUUID(parentType.getUuid());
			MaterialType rootMaterialType = getRootMaterialType(parentType, null);
			if (rootMaterialType != null) {
				materialType.setRootTypeUUID(rootMaterialType.getUuid());
			}
		}
	}


	/**
	 * Logic to get all sub material type list from current material type
	 * 
	 * @param materialType
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getAllSubType(MaterialType materialType,
			List<ServiceEntityNode> rawTypeList)
			throws ServiceEntityConfigureException {
		if (rawTypeList == null || rawTypeList.size() == 0) {
			return null;
		}
		List<ServiceEntityNode> subTypeList = filterAllSubMaterialTypeOnline(
				materialType.getUuid(), rawTypeList);
		return subTypeList;
	}

	public List<ServiceEntityNode> filterAllSubMaterialTypeOnline(
			String parentTypeUUID, List<ServiceEntityNode> rawTypeList) {
		if (ServiceCollectionsHelper.checkNullList(rawTypeList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawTypeList) {
			MaterialType materialType = (MaterialType) seNode;
			if (parentTypeUUID.equals(materialType.getParentTypeUUID())) {
				resultList.add(materialType);
				List<ServiceEntityNode> deeperSubTypeList = filterAllSubMaterialTypeOnline(
						materialType.getUuid(), rawTypeList);
				if (!ServiceCollectionsHelper.checkNullList(deeperSubTypeList)) {
					ServiceCollectionsHelper.mergeToList(resultList,
							deeperSubTypeList);
				}
			}
		}
		return resultList;
	}

	public void convParentTypeToUI(MaterialType parentType,
			MaterialTypeUIModel materialTypeUIModel) {
		if (parentType != null) {
			materialTypeUIModel.setParentTypeId(parentType.getId());
			materialTypeUIModel.setParentTypeName(parentType.getName());
		}
	}

	public void convRootTypeToUI(MaterialType rootType,
			MaterialTypeUIModel materialTypeUIModel) {
		if (rootType != null) {
			materialTypeUIModel.setRootTypeId(rootType.getId());
			materialTypeUIModel.setRootTypeName(rootType.getName());
			materialTypeUIModel.setRootTypeUUID(rootType.getUuid());
		}
	}

	public BSearchResponse searchTreeList(SearchContext searchContext) throws
			SearchConfigureException, AuthorizationException, ServiceEntityInstallationException, ServiceEntityConfigureException, LogonInfoException {
		List<ServiceEntityNode> rawList = this.getSearchProxy()
				.searchDocList(searchContext).getResultList();
		List<ServiceEntityNode> allList = this.getEntityNodeListByKey(null, null, MaterialType.NODENAME, searchContext.getClient(), null);
		@SuppressWarnings("unchecked")
		List<ServiceEntityNode> resultList = (List<ServiceEntityNode>) ServiceCollectionsHelper
				.mergeToTreeListCore(rawList, allList, rawNode->{
					if(rawNode == null)return null;
					MaterialType materialType = (MaterialType)rawNode;
					return materialType.getParentTypeUUID();
				},rawNode->{
					if(rawNode == null)return null;
					MaterialType materialType = (MaterialType)rawNode;
					return materialType.getUuid();
				});
        return BsearchService.genSearchResponse(resultList, 0);
	}


	/**
	 * [Internal method] Convert from UI model to se model:materialType
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToMaterialType(MaterialTypeUIModel materialTypeUIModel,
			MaterialType rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(materialTypeUIModel, rawEntity);
		rawEntity.setParentTypeUUID(materialTypeUIModel.getParentTypeUUID());
		rawEntity.setId(materialTypeUIModel.getId());
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return materialTypeSearchProxy;
	}
}
