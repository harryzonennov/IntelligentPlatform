package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.MatDecisionValueSettingUIModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class MatDecisionValueSettingManager {

	public static final String METHOD_ConvMatDecisionValueSettingToUI = "convMatDecisionValueSettingToUI";

	public static final String METHOD_ConvUIToMatDecisionValueSetting = "convUIToMatDecisionValueSetting";

	public static final String METHOD_ConvConfigureTemplateToDecisionValueUI = "convConfigureTemplateToDecisionValueUI";

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected StandardLogicOperatorProxy standardLogicOperatorProxy;

	public static int VAUSAGE_PURCHASE_SUPPLIER = 1;

	public static int VAUSAGE_INBOUND_WAREHOUSE = 2;

	public static int VAUSAGE_SERIALNUM_FORMAT = 3;

	public static int VAUSAGE_RAWMAT_WAREHOUSE = 4;

	public static int VAUSAGE_SUBID_FORMAT = 5;

	protected List<ServiceEntityNode> allHeaderConditionList;

	private Map<String, Map<Integer, String>> valueUsageMapLan = new HashMap<>();


	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), MaterialConfigureTemplate.NODENAME,
						request.getUuid(), MatDecisionValueSetting.NODENAME, materialConfigureTemplateManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<MaterialConfigureTemplate>) materialConfigureTemplate -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							docPageHeaderModelProxy.getDocPageHeaderModelList(materialConfigureTemplate, null);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<MatDecisionValueSetting>) (matDecisionValueSetting,
																					 pageHeaderModel) -> {
					// How to render current page header
					pageHeaderModel.setHeaderName(matDecisionValueSetting.getRawValue());
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	/**
	 * Logic to get unique Decision value unit
	 * 
	 * @param materialStockKeepUnit
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public MatDecisionValueSetting getDecisionValue(
			MaterialStockKeepUnit materialStockKeepUnit, int valueUsage)
			throws MaterialException, ServiceEntityConfigureException {
		Map<String, Double> resultWeightMap = getDecisionValueMap(materialStockKeepUnit);
		List<Double> valueArray = new ArrayList<>(resultWeightMap.values());
		Collections.sort(valueArray, new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			}
		});
		/*
		 * [Step2] filter out the value
		 */
		if (!ServiceCollectionsHelper.checkNullList(valueArray)) {
			for (Double value : valueArray) {
				String keyStr = getKeyFromValue(resultWeightMap, value);
				if (!ServiceEntityStringHelper.checkNullString(keyStr)) {
					try {
						MaterialConfigureTemplate materialConfigureTemplate = (MaterialConfigureTemplate) materialConfigureTemplateManager
								.getEntityNodeByKey(keyStr,
										IServiceEntityNodeFieldConstant.UUID,
										MaterialConfigureTemplate.NODENAME,
										null);
						MaterialConfigureTemplateServiceModel materialConfigureTemplateServiceModel = (MaterialConfigureTemplateServiceModel) materialConfigureTemplateManager
								.loadServiceModule(
										MaterialConfigureTemplateServiceModel.class,
										materialConfigureTemplate);
						MatDecisionValueSetting matDecisionValueSetting = getDecisionValue(
								materialConfigureTemplateServiceModel
										.getMatDecisionValueSettingList(),
								valueUsage);
						if (matDecisionValueSetting != null) {
							return matDecisionValueSetting;
						}
					} catch (ServiceModuleProxyException e) {
						// continue;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Logic to get unique Decision value unit
	 * 
	 * @param material
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public MatDecisionValueSetting getDecisionValue(Material material,
			int valueUsage) throws MaterialException,
			ServiceEntityConfigureException {
		Map<String, Double> resultWeightMap = getDecisionValueMap(material);
		List<Double> valueArray = new ArrayList<>(resultWeightMap.values());
		Collections.sort(valueArray, new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			}
		});
		/*
		 * [Step2] filter out the value
		 */
		if (!ServiceCollectionsHelper.checkNullList(valueArray)) {
			for (Double value : valueArray) {
				String keyStr = getKeyFromValue(resultWeightMap, value);
				if (!ServiceEntityStringHelper.checkNullString(keyStr)) {
					try {
						MaterialConfigureTemplate materialConfigureTemplate = (MaterialConfigureTemplate) materialConfigureTemplateManager
								.getEntityNodeByKey(keyStr,
										IServiceEntityNodeFieldConstant.UUID,
										MaterialConfigureTemplate.NODENAME,
										null);
						MaterialConfigureTemplateServiceModel materialConfigureTemplateServiceModel = (MaterialConfigureTemplateServiceModel) materialConfigureTemplateManager
								.loadServiceModule(
										MaterialConfigureTemplateServiceModel.class,
										materialConfigureTemplate);
						MatDecisionValueSetting matDecisionValueSetting = getDecisionValue(
								materialConfigureTemplateServiceModel
										.getMatDecisionValueSettingList(),
								valueUsage);
						if (matDecisionValueSetting != null) {
							return matDecisionValueSetting;
						}
					} catch (ServiceModuleProxyException e) {
						// continue;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Logic to get unique Decision value unit
	 * 
	 * @param materialStockKeepUnit
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> getExtPropertySettingList(
			MaterialStockKeepUnit materialStockKeepUnit)
			throws MaterialException, ServiceEntityConfigureException {
		Map<String, Double> resultWeightMap = getDecisionValueMap(materialStockKeepUnit);
		List<Double> valueArray = new ArrayList<>(resultWeightMap.values());
		Collections.sort(valueArray, new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			}
		});
		/*
		 * [Step2] filter out the value
		 */
		if (!ServiceCollectionsHelper.checkNullList(valueArray)) {
			List<ServiceEntityNode> resultList = new ArrayList<>();
			for (Double value : valueArray) {
				String keyStr = getKeyFromValue(resultWeightMap, value);
				if (!ServiceEntityStringHelper.checkNullString(keyStr)) {
					try {
						MaterialConfigureTemplate materialConfigureTemplate = (MaterialConfigureTemplate) materialConfigureTemplateManager
								.getEntityNodeByKey(keyStr,
										IServiceEntityNodeFieldConstant.UUID,
										MaterialConfigureTemplate.NODENAME,
										null);
						MaterialConfigureTemplateServiceModel materialConfigureTemplateServiceModel = (MaterialConfigureTemplateServiceModel) materialConfigureTemplateManager
								.loadServiceModule(
										MaterialConfigureTemplateServiceModel.class,
										materialConfigureTemplate);
						List<MatConfigExtPropertySettingServiceModel> matConfigExtPropertySettingList = materialConfigureTemplateServiceModel
								.getMatConfigExtPropertySettingList();
						if (!ServiceCollectionsHelper
								.checkNullList(matConfigExtPropertySettingList)) {
							resultList.addAll(matConfigExtPropertySettingList.stream().map(
									MatConfigExtPropertySettingServiceModel::getMatConfigExtPropertySetting).collect(
									Collectors.toList()));
						}
					} catch (ServiceModuleProxyException e) {
						// continue;
					}
				}
			}
			return resultList;
		}
		return null;
	}

	/**
	 * Main entry to get Decision value list by material SKU and value usage
	 * 
	 * @param materialStockKeepUnit
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public List<ServiceEntityNode> getDecisionValueList(
			MaterialStockKeepUnit materialStockKeepUnit, int valueUsage)
			throws MaterialException, ServiceEntityConfigureException {
		Map<String, Double> resultWeightMap = getDecisionValueMap(materialStockKeepUnit);
		List<Double> valueArray = new ArrayList<>(resultWeightMap.values());
		Collections.sort(valueArray, new Comparator<Double>() {
			public int compare(Double o1, Double o2) {
				return o1.compareTo(o2);
			}
		});
		/*
		 * [Step2] filter out the value
		 */
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(valueArray)) {
			for (Double value : valueArray) {
				String keyStr = getKeyFromValue(resultWeightMap, value);
				if (!ServiceEntityStringHelper.checkNullString(keyStr)) {
					try {
						MaterialConfigureTemplate materialConfigureTemplate = (MaterialConfigureTemplate) materialConfigureTemplateManager
								.getEntityNodeByKey(keyStr,
										IServiceEntityNodeFieldConstant.UUID,
										MaterialConfigureTemplate.NODENAME,
										null);
						MaterialConfigureTemplateServiceModel materialConfigureTemplateServiceModel = (MaterialConfigureTemplateServiceModel) materialConfigureTemplateManager
								.loadServiceModule(
										MaterialConfigureTemplateServiceModel.class,
										materialConfigureTemplate);
						MatDecisionValueSetting matDecisionValueSetting = getDecisionValue(
								materialConfigureTemplateServiceModel
										.getMatDecisionValueSettingList(),
								valueUsage);
						if (matDecisionValueSetting != null) {
							resultList.add(matDecisionValueSetting);
						}
					} catch (ServiceModuleProxyException e) {
						// continue;
					}
				}
			}
		}
		return resultList;
	}

	public String getKeyFromValue(Map<String, Double> rawMap, double targetValue) {
		Set<String> keySet = rawMap.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Double keyValue = rawMap.get(key);
			if (targetValue == keyValue) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Entrance to generate Decision value map
	 * 
	 * @param materialStockKeepUnit
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public Map<String, Double> getDecisionValueMap(
			MaterialStockKeepUnit materialStockKeepUnit)
			throws ServiceEntityConfigureException, MaterialException {
		this.allHeaderConditionList = materialConfigureTemplateManager
				.getEntityNodeListByKey(null, null,
						MatConfigHeaderCondition.NODENAME,
						materialStockKeepUnit.getClient(), null);
		Map<String, List<ServiceEntityNode>> headerConditionMap = groupHeaderCondition(this.allHeaderConditionList);
		Set<String> keySet = headerConditionMap.keySet();
		Iterator<String> it = keySet.iterator();
		Map<String, Double> resultWeightMap = new HashMap<>();
		while (it.hasNext()) {
			String key = it.next();
			List<ServiceEntityNode> headerConditionList = headerConditionMap
					.get(key);
			double weightFactor = getGroupHeaderConditionWeightFactor(
					headerConditionList, materialStockKeepUnit);
			if (weightFactor > 0) {
				resultWeightMap.put(key, weightFactor);
			}
		}
		return resultWeightMap;
	}

	/**
	 * Entrance to generate Decision value map
	 * 
	 * @param material
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public Map<String, Double> getDecisionValueMap(Material material)
			throws ServiceEntityConfigureException, MaterialException {
		this.allHeaderConditionList = materialConfigureTemplateManager
				.getEntityNodeListByKey(null, null,
						MatConfigHeaderCondition.NODENAME,
						material.getClient(), null);
		Map<String, List<ServiceEntityNode>> headerConditionMap = groupHeaderCondition(this.allHeaderConditionList);
		Set<String> keySet = headerConditionMap.keySet();
		Iterator<String> it = keySet.iterator();
		Map<String, Double> resultWeightMap = new HashMap<>();
		while (it.hasNext()) {
			String key = it.next();
			List<ServiceEntityNode> headerConditionList = headerConditionMap
					.get(key);
			double weightFactor = getGroupHeaderConditionWeightFactor(
					headerConditionList, material);
			if (weightFactor > 0) {
				resultWeightMap.put(key, weightFactor);
			}
		}
		return resultWeightMap;
	}

	/**
	 * Core Logic to provide weight factor base on Material Type Layer in
	 * material type list
	 * 
	 * @param materialType
	 * @return
	 * @throws ServiceEntityConfigureException 
	 */
	public double getMaterialWeightFacWithType(
			List<ServiceEntityNode> rawMaterialTypeList,
			MaterialType materialType) throws ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(rawMaterialTypeList)) {
			return 0;
		}
		if (materialType == null) {
			return 0;
		}
		List<ServiceEntityNode> parentList = materialTypeManager
				.getRecursiveMaterialTypeWrapper(materialType.getUuid(),
						materialType.getClient(), rawMaterialTypeList);
		if(ServiceCollectionsHelper.checkNullList(parentList)){
			return 0;
		}
		return parentList.size() * 1.0 / rawMaterialTypeList.size();
	}

	/**
	 * Core Logic to provide weight factor base on node id
	 * 
	 * @param refNodeId
	 * @return
	 */
	public static int getRefNodeIdWeightFactor(String refNodeId) {
		if (ServiceEntityStringHelper.checkNullString(refNodeId)) {
			return 0;
		}
		if (refNodeId.equals(MaterialType.SENAME)) {
			return 1;
		}
		if (refNodeId.equals(Material.SENAME)) {
			return 2;
		}
		if (refNodeId.equals(MaterialStockKeepUnit.SENAME)) {
			return 3;
		}
		return 0;
	}

	/**
	 * Core Logic to provide weight factor base on fieldName
	 * 
	 * @param fieldName
	 * @return
	 */
	public static int getWeightFactorByField(String fieldName) {
		if (ServiceEntityStringHelper.checkNullString(fieldName)) {
			return 0;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.UUID)) {
			return 3;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.ID)) {
			return 2;
		}
		return 1;
	}

	public MatDecisionValueSetting getDecisionValue(
			List<MatDecisionValueSettingServiceModel> rawDecisionValueSettingList, int valueUsage) {
		if (!ServiceCollectionsHelper
				.checkNullList(rawDecisionValueSettingList)) {
			for (MatDecisionValueSettingServiceModel matDecisionValueSettingServiceModel :
					rawDecisionValueSettingList) {
				MatDecisionValueSetting matDecisionValueSetting = matDecisionValueSettingServiceModel.getMatDecisionValueSetting();
				if (matDecisionValueSetting.getValueUsage() == valueUsage) {
					return matDecisionValueSetting;
				}
			}
		}
		return null;
	}

	public double getGroupHeaderConditionWeightFactor(
			List<ServiceEntityNode> groupHeaderConditionList,
			MaterialStockKeepUnit targetMaterialSKU) throws MaterialException,
			ServiceEntityConfigureException {
		if (!ServiceCollectionsHelper.checkNullList(groupHeaderConditionList)) {
			double result = 0;
			for (ServiceEntityNode seNode : groupHeaderConditionList) {
				MatConfigHeaderCondition matConfigHeaderCondition = (MatConfigHeaderCondition) seNode;
				double tmpResult = getHeaderConditionWeightFactor(
						matConfigHeaderCondition, targetMaterialSKU);
				if (matConfigHeaderCondition.getLogicOperator() == StandardLogicOperatorProxy.OPERATOR_AND) {
					if (tmpResult == 0) {
						return 0;
					}
					result = result + tmpResult;
				} else {
					result = result + tmpResult;
				}
			}
			return result;
		}
		return 0;
	}

	public double getGroupHeaderConditionWeightFactor(
			List<ServiceEntityNode> groupHeaderConditionList,
			Material targetMaterial) throws MaterialException,
			ServiceEntityConfigureException {
		if (!ServiceCollectionsHelper.checkNullList(groupHeaderConditionList)) {
			double result = 0;
			for (ServiceEntityNode seNode : groupHeaderConditionList) {
				MatConfigHeaderCondition matConfigHeaderCondition = (MatConfigHeaderCondition) seNode;
				int tmpResult = getHeaderConditionWeightFactor(
						matConfigHeaderCondition, targetMaterial);
				if (matConfigHeaderCondition.getLogicOperator() == StandardLogicOperatorProxy.OPERATOR_AND) {
					if (tmpResult == 0) {
						return 0;
					}
					result = result + tmpResult;
				} else {
					result = result + tmpResult;
				}
			}
			return result;
		}
		return 0;
	}

	protected Map<String, List<ServiceEntityNode>> groupHeaderCondition(
			List<ServiceEntityNode> allHeaderConditionList) {
		Map<String, List<ServiceEntityNode>> resultMap = new HashMap<String, List<ServiceEntityNode>>();
		if (!ServiceCollectionsHelper.checkNullList(allHeaderConditionList)) {
			for (ServiceEntityNode seNode : allHeaderConditionList) {
				if (resultMap.containsKey(seNode.getRootNodeUUID())) {
					List<ServiceEntityNode> tmpArray = resultMap.get(seNode
							.getRootNodeUUID());
					ServiceCollectionsHelper.mergeToList(tmpArray, seNode);
				} else {
					List<ServiceEntityNode> tmpArray = new ArrayList<>();
					tmpArray.add(seNode);
					resultMap.put(seNode.getRootNodeUUID(),
							tmpArray);
				}
			}
			return resultMap;
		}
		return null;
	}

	public double getHeaderConditionWeightFactor(
			MatConfigHeaderCondition matConfigHeaderCondition,
			MaterialStockKeepUnit targetMaterialSKU) throws MaterialException,
			ServiceEntityConfigureException {
		if (matConfigHeaderCondition.getRefNodeInstId().equals(
				MaterialType.SENAME)) {
			List<ServiceEntityNode> materialTypeList = null;
			try {
				materialTypeList = materialStockKeepUnitManager
						.getRecursiveMaterialType(targetMaterialSKU.getUuid(),
								targetMaterialSKU.getClient());
			} catch (ServiceComExecuteException e) {
				throw new MaterialException(MaterialException.PARA_SYSTEM_ERROR, e.getErrorMessage());
			}
			if (!ServiceCollectionsHelper.checkNullList(materialTypeList)) {
				for (ServiceEntityNode rawSENode : materialTypeList) {
					MaterialType materialType = (MaterialType) rawSENode;
					String fieldValue = ServiceEntityFieldsHelper
							.getStrServiceFieldValueWrapper(materialType,
									matConfigHeaderCondition.getFieldName());
					if(ServiceEntityStringHelper.checkEqualOrContain(fieldValue,
							matConfigHeaderCondition.getFieldValue())) {
						int nodeIndFactor = getRefNodeIdWeightFactor(MaterialType.SENAME)
								* getWeightFactorByField(matConfigHeaderCondition
										.getFieldName());
					    if(nodeIndFactor > 0){
					    	double materialTypeFactor = getMaterialWeightFacWithType(materialTypeList, materialType);
					    	return nodeIndFactor * materialTypeFactor;
					    }
					}
				}
			}
			return 0;
		}

		if (matConfigHeaderCondition.getRefNodeInstId().equals(Material.SENAME)) {
			Material material = materialManager.getMaterialWrapper(
					targetMaterialSKU.getRefMaterialUUID(),
					targetMaterialSKU.getClient(), null);
			if (material != null) {
				String fieldValue = ServiceEntityFieldsHelper
						.getStrServiceFieldValueWrapper(material,
								matConfigHeaderCondition.getFieldName());
				if(ServiceEntityStringHelper.checkEqualOrContain(fieldValue,
						matConfigHeaderCondition.getFieldValue())) {
					return getRefNodeIdWeightFactor(Material.SENAME)
							* getWeightFactorByField(matConfigHeaderCondition
									.getFieldName());
				} else {
					return 0;
				}
			}
		}

		if (matConfigHeaderCondition.getRefNodeInstId().equals(
				MaterialStockKeepUnit.SENAME)) {
			String fieldValue = ServiceEntityFieldsHelper
					.getStrServiceFieldValueWrapper(targetMaterialSKU,
							matConfigHeaderCondition.getFieldName());
			if(ServiceEntityStringHelper.checkEqualOrContain(fieldValue,
					matConfigHeaderCondition.getFieldValue())) {
				return getRefNodeIdWeightFactor(MaterialStockKeepUnit.SENAME)
						* getWeightFactorByField(matConfigHeaderCondition
								.getFieldName());
			} else {
				return 0;
			}
		}
		return 0;
	}

	public int getHeaderConditionWeightFactor(
			MatConfigHeaderCondition matConfigHeaderCondition,
			Material targetMaterial) throws MaterialException,
			ServiceEntityConfigureException {
		if (matConfigHeaderCondition.getRefNodeInstId().equals(
				MaterialType.SENAME)) {
			MaterialType materialType = materialTypeManager
					.getMaterialTypeWrapper(
							targetMaterial.getRefMaterialType(),
							targetMaterial.getClient(), null);
			if (materialType != null) {
				String fieldValue = ServiceEntityFieldsHelper
						.getStrServiceFieldValueWrapper(materialType,
								matConfigHeaderCondition.getFieldName());
				if(ServiceEntityStringHelper.checkEqualOrContain(fieldValue,
						matConfigHeaderCondition.getFieldValue())) {
					return getRefNodeIdWeightFactor(MaterialType.SENAME)
							* getWeightFactorByField(matConfigHeaderCondition
									.getFieldName());
				} else {
					return 0;
				}
			}
		}

		if (matConfigHeaderCondition.getRefNodeInstId().equals(Material.SENAME)) {
			Material material = materialManager.getMaterialWrapper(
					targetMaterial.getUuid(), targetMaterial.getClient(), null);
			if (material != null) {
				String fieldValue = ServiceEntityFieldsHelper
						.getStrServiceFieldValueWrapper(material,
								matConfigHeaderCondition.getFieldName());
				if(ServiceEntityStringHelper.checkEqualOrContain(fieldValue,
						matConfigHeaderCondition.getFieldValue())) {
					return getRefNodeIdWeightFactor(Material.SENAME)
							* getWeightFactorByField(matConfigHeaderCondition
									.getFieldName());
				} else {
					return 0;
				}
			}
		}
		return 0;
	}

	public ServiceEntityManager getHeaderConditionSEManager(String refNodeInstId) {
		if (refNodeInstId == null) {
			return null;
		}
		if (refNodeInstId.equals(Material.SENAME)) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.materialManager);
		}
		if (refNodeInstId.equals(MaterialType.SENAME)) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.materialTypeManager);
		}
		if (refNodeInstId.equals(MaterialStockKeepUnit.SENAME)) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.materialStockKeepUnitManager);
		}
		return null;
	}

	public ServiceEntityNode getHeaderConditionSENode(String refNodeInstId,
			String fieldName, String fieldValue, String client)
			throws ServiceEntityConfigureException {
		ServiceEntityManager targetSEManager = getHeaderConditionSEManager(refNodeInstId);
		if (targetSEManager == null) {
			return null;
		}
		if (fieldName == null) {
			return null;
		}
		if (!fieldName.equals(IServiceEntityNodeFieldConstant.UUID)) {
			return null;
		}
		ServiceEntityNode targetNode = targetSEManager.getEntityNodeByKey(
				fieldValue, IServiceEntityNodeFieldConstant.UUID,
				ServiceEntityNode.NODENAME_ROOT, client, null, true);
		return targetNode;
	}

	public ServiceEntityManager getDecisionTargetSEManager(int valueUsage) {
		if (valueUsage == VAUSAGE_SERIALNUM_FORMAT) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.serialNumberSettingManager);
		}
		if (valueUsage == VAUSAGE_SUBID_FORMAT) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.serialNumberSettingManager);
		}
		if (valueUsage == VAUSAGE_PURCHASE_SUPPLIER) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.corporateCustomerManager);
		}
		if (valueUsage == VAUSAGE_INBOUND_WAREHOUSE) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.warehouseManager);
		}
		if (valueUsage == VAUSAGE_RAWMAT_WAREHOUSE) {
			return serviceEntityManagerFactoryInContext
					.getManagerByManagerName(ServiceEntityManagerFactoryInContext.warehouseManager);
		}
		return null;
	}

	public ServiceEntityNode getDefDecisionTargetSENode(int valueUsage,
			String rawValue, String client)
			throws ServiceEntityConfigureException {
		ServiceEntityManager targetSEManager = getDecisionTargetSEManager(valueUsage);
		if (targetSEManager == null) {
			return null;
		}
		ServiceEntityNode targetNode = targetSEManager.getEntityNodeByKey(
				rawValue, IServiceEntityNodeFieldConstant.UUID,
				ServiceEntityNode.NODENAME_ROOT, client, null, true);
		return targetNode;
	}

	public Map<Integer, String> initValueUsageMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.valueUsageMapLan, MatDecisionValueSettingUIModel.class, "valueUsage");
	}

	public void convMatDecisionValueSettingToUI(
			MatDecisionValueSetting matDecisionValueSetting,
			MatDecisionValueSettingUIModel matDecisionValueSettingUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		convMatDecisionValueSettingToUI(matDecisionValueSetting, matDecisionValueSettingUIModel, null);
	}
	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convMatDecisionValueSettingToUI(
			MatDecisionValueSetting matDecisionValueSetting,
			MatDecisionValueSettingUIModel matDecisionValueSettingUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (matDecisionValueSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(matDecisionValueSetting, matDecisionValueSettingUIModel);
			if(logonInfo != null){
				Map<Integer, String> valueUsageMap = this.initValueUsageMap(logonInfo.getLanguageCode());
				matDecisionValueSettingUIModel
						.setValueUsage(matDecisionValueSetting.getValueUsage());
				matDecisionValueSettingUIModel
						.setValueUsageValue(valueUsageMap
								.get(matDecisionValueSetting.getValueUsage()));
			}
			matDecisionValueSettingUIModel.setRawValue(matDecisionValueSetting
					.getRawValue());
			ServiceEntityNode targetDecisionNode = getDefDecisionTargetSENode(
							matDecisionValueSetting.getValueUsage(),
							matDecisionValueSetting.getRawValue(),
							matDecisionValueSetting.getClient());
			if (targetDecisionNode != null) {
				String valueLabel = targetDecisionNode.getId();
				if (!ServiceEntityStringHelper
						.checkNullString(targetDecisionNode.getName())) {
					valueLabel = targetDecisionNode.getId() + '-'
							+ targetDecisionNode.getName();
				}
				matDecisionValueSettingUIModel.setValueLabel(valueLabel);
				matDecisionValueSettingUIModel.setValueId(targetDecisionNode
						.getId());
				matDecisionValueSettingUIModel.setValueName(targetDecisionNode
						.getName());
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:matDecisionValueSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToMatDecisionValueSetting(
			MatDecisionValueSettingUIModel matDecisionValueSettingUIModel,
			MatDecisionValueSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(matDecisionValueSettingUIModel, rawEntity);
		rawEntity.setValueUsage(matDecisionValueSettingUIModel.getValueUsage());
		rawEntity.setRawValue(matDecisionValueSettingUIModel.getRawValue());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convConfigureTemplateToDecisionValueUI(
			MaterialConfigureTemplate materialConfigureTemplate,
			MatDecisionValueSettingUIModel matDecisionValueSettingUIModel) {
		matDecisionValueSettingUIModel.setTemplateId(materialConfigureTemplate
				.getId());
		matDecisionValueSettingUIModel
				.setTemplateName(materialConfigureTemplate.getName());
	}



}
