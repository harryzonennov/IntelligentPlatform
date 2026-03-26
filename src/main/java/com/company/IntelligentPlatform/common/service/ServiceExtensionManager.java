package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.ServiceExtendUIModel;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxy;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Provide Fields extension core logic
 * 
 * @author Zhang,Hang
 *
 */
@Service
public class ServiceExtensionManager {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	protected Logger logger = LoggerFactory.getLogger(ServiceExtensionManager.class);

	public List<ServiceExtensionSimModel> parseToDefExtensionSimModelList(List<DocumentContentSpecifier.UIModelClassMap> uiModelClassMapList,
																		  List<DocumentContentSpecifier.PropertyMap> propertyMapList, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException {
		List<ServiceExtensionSimModel> serviceExtensionSimModelList = new ArrayList<>();
		if(!ServiceCollectionsHelper.checkNullList(uiModelClassMapList)){
			for(DocumentContentSpecifier.UIModelClassMap uiModelClassMap: uiModelClassMapList){
				try {
					ServiceExtensionSetting serviceExtensionSetting =
							getServiceExtensionSettingWrapper(uiModelClassMap.getModelId(), serialLogonInfo.getClient());
					List<ServiceEntityNode> serviceExtendFieldList =
							batchParseFieldSettingCore(uiModelClassMap.getUiModelClass(), serviceExtensionSetting);
					serviceExtensionSimModelList.add(new ServiceExtensionSimModel(uiModelClassMap.getModelId(),
							serviceExtensionSetting, serviceExtendFieldList));
				} catch (ServiceEntityConfigureException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}
		}
		if(!ServiceCollectionsHelper.checkNullList(propertyMapList)){
			batchParseFieldSettings(propertyMapList, serviceExtensionSimModelList, serialLogonInfo);
		}
		return serviceExtensionSimModelList;
	}

	public List<ServiceExtensionSimModel> parseToDefExtensionSimModelList(List<DocumentContentSpecifier.UIModelClassMap> uiModelClassMapList,
																		  List<DocumentContentSpecifier.PropertyMap> propertyMapList, String modelId, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException {
		List<DocumentContentSpecifier.UIModelClassMap> selectedUiModelClassMapList = ServiceCollectionsHelper.filterListOnline(uiModelClassMapList,
				uiModelClassMap -> { return modelId.equalsIgnoreCase(uiModelClassMap.getModelId());}, false);
		List<DocumentContentSpecifier.PropertyMap> selectedPropertyMapList =
				ServiceCollectionsHelper.filterListOnline(propertyMapList,
				propertyMap -> { return modelId.equalsIgnoreCase(propertyMap.getModelId());}, false);
		return parseToDefExtensionSimModelList(selectedUiModelClassMapList, selectedPropertyMapList, serialLogonInfo);
	}

	public List<ServiceEntityNode> getComFieldSettingList(List<DocumentContentSpecifier.UIModelClassMap> uiModelClassMapList,
																		  List<DocumentContentSpecifier.PropertyMap> propertyMapList, String modelId, SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException {
		List<ServiceExtensionSimModel> serviceExtensionSimModelList =
				parseToDefExtensionSimModelList(uiModelClassMapList, propertyMapList, modelId, serialLogonInfo);
		List<ServiceEntityNode> fieldSettingList = new ArrayList<>();
		if(ServiceCollectionsHelper.checkNullList(serviceExtensionSimModelList)){
			return fieldSettingList;
		}
		for(ServiceExtensionSimModel serviceExtensionSimModel: serviceExtensionSimModelList){
			List<ServiceEntityNode> tmpFieldSettingList = serviceExtensionSimModel.getServiceExtendFieldList();
			ServiceCollectionsHelper.mergeToList(fieldSettingList, tmpFieldSettingList, seNode->{
				ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) seNode;
				return serviceExtendFieldSetting.getFieldName();
			});
		}
		return fieldSettingList;
	}

	private ServiceExtensionSetting getServiceExtensionSettingWrapper(String modelId, String client)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyStructureList = new ArrayList<>();
		keyStructureList.add(new ServiceBasicKeyStructure(modelId, ServiceExtensionSetting.FIELD_MODELID,
				ServiceBasicKeyStructure.OPERATOR_OR));
		keyStructureList.add(new ServiceBasicKeyStructure(ServiceEntityStringHelper.headerToUpperCase(modelId), ServiceExtensionSetting.FIELD_REFSENAME,
				ServiceBasicKeyStructure.OPERATOR_OR));
		keyStructureList.add(new ServiceBasicKeyStructure(ServiceEntityStringHelper.headerToUpperCase(modelId), ServiceExtensionSetting.FIELD_REFNODENAME,
				ServiceBasicKeyStructure.OPERATOR_OR));
		ServiceExtensionSetting serviceExtensionSetting = (ServiceExtensionSetting) serviceExtensionSettingManager.getEntityNodeByKeyList(keyStructureList,
				ServiceExtensionSetting.NODENAME, client, null, true);
		if(serviceExtensionSetting != null){
			return serviceExtensionSetting;
		}
		return (ServiceExtensionSetting) serviceExtensionSettingManager.newRootEntityNode(client);
	}

	public List<ServiceEntityNode> batchParseFieldSettingCore(Class<?> uiModelClass,
														   ServiceExtensionSetting serviceExtensionSetting)
			throws ServiceEntityConfigureException {
		List<Field> fieldList = ServiceEntityFieldsHelper.getFieldsList(uiModelClass);
		if(ServiceCollectionsHelper.checkNullList(fieldList)){
			return null;
		}
		List<ServiceEntityNode> fieldSettingList = new ArrayList<>();
		for(Field field: fieldList){
			ServiceExtendFieldSetting serviceExtendFieldSetting =
					(ServiceExtendFieldSetting) serviceExtensionSettingManager.newEntityNode(serviceExtensionSetting,
							ServiceExtendFieldSetting.NODENAME);
			serviceExtendFieldSetting.setActiveSwitch(StandardSwitchProxy.SWITCH_ON);
			serviceExtendFieldSetting.setExtendedFieldFlag(false);
			serviceExtendFieldSetting.setFieldName(field.getName());
			serviceExtendFieldSetting.setFieldType(field.getType().getSimpleName());
			fieldSettingList.add(serviceExtendFieldSetting);
		}
		return fieldSettingList;
	}

	public List<ServiceExtensionSimModel> batchParseFieldSettings(
			List<DocumentContentSpecifier.PropertyMap> propertyMapList,
			List<ServiceExtensionSimModel> serviceExtensionSimModelList,
			SerialLogonInfo serialLogonInfo)
			throws ServiceEntityConfigureException {
		if(ServiceCollectionsHelper.checkNullList(propertyMapList)){
			return null;
		}
		List<ServiceEntityNode> newFieldList = new ArrayList<>();
		// Traverse each property file path
		for(DocumentContentSpecifier.PropertyMap propertyMap: propertyMapList){
			String propertiesPath = propertyMap.getPropertyPath();
			// parsing property from path
			try {
				ServiceExtensionSimModel serviceExtensionSimModel =
						ServiceCollectionsHelper.filterOnline(serviceExtensionSimModelList, simModel->{
							return propertyMap.getModelId().equals(simModel.getModelId());
						});
				if(serviceExtensionSimModel == null){
					ServiceExtensionSetting serviceExtensionSetting =
							getServiceExtensionSettingWrapper(propertyMap.getModelId(), serialLogonInfo.getClient());
					serviceExtensionSimModel = new ServiceExtensionSimModel(propertyMap.getModelId(), serviceExtensionSetting,
							new ArrayList<>());
					serviceExtensionSimModelList.add(serviceExtensionSimModel);
				}
				Map<String, String> propertiesMap = ServiceDropdownListHelper.getStrStaticDropDownMap(propertiesPath,
						serialLogonInfo.getLanguageCode());
				List<ServiceEntityNode> updatedFieldSettingList = parseToFieldSettingListCore(propertiesMap,
						serviceExtensionSimModel.getServiceExtendFieldList(), serviceExtensionSimModel.getServiceExtensionSetting());
				serviceExtensionSimModel.setServiceExtendFieldList(updatedFieldSettingList);
			} catch (IOException e) {
				logger.error("no properties found" + e.getMessage());
			}
		}
		return serviceExtensionSimModelList;
	}

	/**
	 * [Internal] Parse to field setting list by lan property map
	 * @param propertyMap
	 * @param rawFieldSettingList
	 * @param serviceExtensionSetting
	 * @return
	 */
	private List<ServiceEntityNode> parseToFieldSettingListCore(
			Map<String, String> propertyMap, List<ServiceEntityNode> rawFieldSettingList, ServiceExtensionSetting serviceExtensionSetting)
			throws ServiceEntityConfigureException {
		if(propertyMap.size() == 0){
			return null;
		}
		Set<String> propertyKeySet = propertyMap.keySet();
		List<ServiceEntityNode> newFieldList = new ArrayList<>();
		for(String key: propertyKeySet){
			ServiceExtendFieldSetting existedFieldSetting =
					(ServiceExtendFieldSetting)ServiceCollectionsHelper.filterOnline(rawFieldSettingList,
							seNode->{
								ServiceExtendFieldSetting tmpFieldSetting = (ServiceExtendFieldSetting) seNode;
								return tmpFieldSetting.getFieldName().equals(key);
							});
			if(existedFieldSetting == null){
				// In case new field parse from lan property
				existedFieldSetting =
						(ServiceExtendFieldSetting) serviceExtensionSettingManager.newEntityNode(serviceExtensionSetting,
								ServiceExtendFieldSetting.NODENAME);
				existedFieldSetting.setActiveSwitch(StandardSwitchProxy.SWITCH_ON);
				existedFieldSetting.setExtendedFieldFlag(false);
				existedFieldSetting.setFieldName(key);
				existedFieldSetting.setFieldLabel(propertyMap.get(key));
				newFieldList.add(existedFieldSetting);
			} else {
				// serviceExtendFieldSetting
				existedFieldSetting.setFieldLabel(propertyMap.get(key));
			}
		}
		ServiceCollectionsHelper.mergeToList(newFieldList, rawFieldSettingList);
		return newFieldList;
	}

	/**
	 * Main entrance to update extension field list from Service UI Model
	 * 
	 * @param serviceUIModule
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceUIModuleProxyException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceExtensionException 
	 */
	public void updateToStoreModelWrapper(ServiceUIModule serviceUIModule,
			String client, String logonUserUUID, String organizationUUID)
			throws ServiceUIModuleProxyException, IllegalArgumentException,
			IllegalAccessException, ServiceEntityConfigureException, ServiceExtensionException {
		/*
		 * [Step1] Scan and get the extendUIModel list from service ui model
		 */
		Field extendField = ServiceUIModuleProxy
				.getExtendListTypeField(serviceUIModule.getClass());
		if (extendField == null) {
			return;
		}
		extendField.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<ServiceExtendUIModel> serviceExtendUIModelList = (List<ServiceExtendUIModel>) extendField
				.get(serviceUIModule);
		if (ServiceCollectionsHelper.checkNullList(serviceExtendUIModelList)) {
			return;
		}
		/*
		 * [Step2] Get the relative field setting list by model id
		 */
		Field coreUIModuleField = ServiceUIModuleProxy
				.getCoreUIModuleField(serviceUIModule.getClass());
		if (coreUIModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOCOREMODULE,
					serviceUIModule.getClass().getSimpleName());
		}
		coreUIModuleField.setAccessible(true);
		IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
				.getAnnotation(IServiceUIModuleFieldConfig.class);
		if (serviceUIModuleFieldConfig == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOANNOTATION,
					serviceUIModule.getClass().getSimpleName());
		}
		String nodeInstId = serviceUIModuleFieldConfig.nodeInstId();
		// Get core UI model value
		SEUIComModel uiModelValue = (SEUIComModel) coreUIModuleField
				.get(serviceUIModule);
		/*
		 * [Step3] Get the relative field setting list by model id
		 */
		List<ServiceEntityNode> serviceExtendFieldSettingList = serviceExtensionSettingManager
				.getFieldSettingList(nodeInstId, client);
		/*
		 * [Step4] Update execution
		 */
		for (ServiceExtendUIModel serviceExtendUIModel : serviceExtendUIModelList) {
			ServiceEntityNode rawSEField = ServiceCollectionsHelper
					.filterSENodeOnline(serviceExtendUIModel.getRefFieldUUID(),
							serviceExtendFieldSettingList);
			updateStoreModelUnion(serviceExtendUIModel,
					(ServiceExtendFieldSetting) rawSEField,
					uiModelValue.getUuid(), logonUserUUID, organizationUUID);
		}
	}

	/**
	 * Entrance to process ServiceUIModel in case of reading
	 * 
	 * @param serviceUIModule
	 * @throws ServiceUIModuleProxyException
	 * @throws ServiceEntityConfigureException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ServiceExtensionException 
	 */
	public void getExtendUIModelWrapper(ServiceUIModule serviceUIModule,
			String client) throws ServiceUIModuleProxyException,
			ServiceEntityConfigureException, IllegalArgumentException,
			IllegalAccessException, ServiceExtensionException {
		/*
		 * [Step1] Scan and get the extendUIModel list from service UI model
		 */
		Field extendField = ServiceUIModuleProxy
				.getExtendListTypeField(serviceUIModule.getClass());
		if (extendField == null) {
			return;
		}
		/*
		 * [Step2] Get the relative field setting list by model id
		 */
		Field coreUIModuleField = ServiceUIModuleProxy
				.getCoreUIModuleField(serviceUIModule.getClass());
		if (coreUIModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOCOREMODULE,
					serviceUIModule.getClass().getSimpleName());
		}
		coreUIModuleField.setAccessible(true);
		IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
				.getAnnotation(IServiceUIModuleFieldConfig.class);
		if (serviceUIModuleFieldConfig == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOANNOTATION,
					serviceUIModule.getClass().getSimpleName());
		}
		String nodeInstId = serviceUIModuleFieldConfig.nodeInstId();
		// Get core UI model value
		SEUIComModel uiModelValue = (SEUIComModel) coreUIModuleField
				.get(serviceUIModule);
		/*
		 * [Step3] Get the relative field setting list by model id
		 */
		List<ServiceEntityNode> serviceExtendFieldSettingList = serviceExtensionSettingManager
				.getFieldSettingList(nodeInstId, client);
		List<ServiceExtendUIModel> serviceExtendUIModelList = getExtendUIModelList(
				serviceExtendFieldSettingList, uiModelValue.getUuid(), client);
		extendField.setAccessible(true);
		extendField.set(serviceUIModule, serviceExtendUIModelList);
	}

	/**
	 * Entrance to process ServiceUIModel when creating new instance
	 * 
	 * @param serviceUIModule
	 * @throws ServiceUIModuleProxyException
	 * @throws ServiceEntityConfigureException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ServiceExtensionException 
	 */
	public void newExtendUIModelWrapper(ServiceUIModule serviceUIModule,
			String client) throws ServiceUIModuleProxyException,
			ServiceEntityConfigureException, IllegalArgumentException,
			IllegalAccessException, ServiceExtensionException {
		/*
		 * [Step1] Scan and get the extendUIModel list from service UI model
		 */
		Field extendField = ServiceUIModuleProxy
				.getExtendListTypeField(serviceUIModule.getClass());
		if (extendField == null) {
			return;
		}
		/*
		 * [Step2] Get the relative field setting list by model id
		 */
		Field coreUIModuleField = ServiceUIModuleProxy
				.getCoreUIModuleField(serviceUIModule.getClass());
		if (coreUIModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOCOREMODULE,
					serviceUIModule.getClass().getSimpleName());
		}
		coreUIModuleField.setAccessible(true);
		IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = coreUIModuleField
				.getAnnotation(IServiceUIModuleFieldConfig.class);
		if (serviceUIModuleFieldConfig == null) {
			// raise exception when no core module field found
			throw new ServiceUIModuleProxyException(
					ServiceUIModuleProxyException.PARA_NOANNOTATION,
					serviceUIModule.getClass().getSimpleName());
		}
		String nodeInstId = serviceUIModuleFieldConfig.nodeInstId();
		// Get core UI model value
		SEUIComModel uiModelValue = (SEUIComModel) coreUIModuleField
				.get(serviceUIModule);
		/*
		 * [Step3] Get the relative field setting list by model id
		 */
		List<ServiceEntityNode> serviceExtendFieldSettingList = serviceExtensionSettingManager
				.getFieldSettingList(nodeInstId, client);
		List<ServiceExtendUIModel> serviceExtendUIModelList = genExtendUIModelList(
				serviceExtendFieldSettingList, uiModelValue.getUuid(), client);
		extendField.setAccessible(true);
		extendField.set(serviceUIModule, serviceExtendUIModelList);
	}

	/**
	 * In case creation, Generate extend model list by field setting list
	 * 
	 * @param refFieldSettingList
	 * @param refUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException 
	 * @throws ServiceExtensionException 
	 */
	public List<ServiceExtendUIModel> genExtendUIModelList(
			List<ServiceEntityNode> refFieldSettingList, String refUUID,
			String client) throws ServiceEntityConfigureException, ServiceExtensionException {
		if (ServiceCollectionsHelper.checkNullList(refFieldSettingList)) {
			return null;
		}
		List<ServiceExtendUIModel> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : refFieldSettingList) {
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) seNode;
			ReferenceNode storeModule = newStoreModelUnion(serviceExtendFieldSetting, serviceExtendFieldSetting.getStoreModelName());			
			ServiceExtendUIModel serviceExtendUIModel = new ServiceExtendUIModel();
			convertToExtendUIModel(storeModule, serviceExtendUIModel);
			convertToExtendUIModel(serviceExtendFieldSetting, serviceExtendUIModel);
			serviceExtendUIModel.setClient(client);
			resultList.add(serviceExtendUIModel);
		}
		return resultList;
	}

	/**
	 * Core Logic to return the core module field by given service module type
	 * 
	 * @param serviceUIModuleType
	 * @return
	 */
	public static Field getExtendUIModuleField(Class<?> serviceUIModuleType) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceUIModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					field.getType(), SEUIComModel.class.getSimpleName())) {
				return field;
			}
		}
		return null;
	}

	public List<ServiceExtendUIModel> getExtendUIModelList(
			List<ServiceEntityNode> refFieldSettingList, String refUUID,
			String client) throws ServiceEntityConfigureException, ServiceExtensionException {
		if (ServiceCollectionsHelper.checkNullList(refFieldSettingList)) {
			return null;
		}
		List<ServiceEntityNode> rawStoreModelList = getExtendModelList(
				refFieldSettingList, refUUID, client);
		if (ServiceCollectionsHelper.checkNullList(rawStoreModelList)) {
			return null;
		}
		List<ServiceExtendUIModel> serviceExtendUIModelList = new ArrayList<>();
		for (ServiceEntityNode rawSENode : rawStoreModelList) {
			ServiceExtendUIModel serviceExtendUIModel = new ServiceExtendUIModel();
			convertToExtendUIModel((ReferenceNode) rawSENode,
					serviceExtendUIModel);
			serviceExtendUIModelList.add(serviceExtendUIModel);
		}
		return serviceExtendUIModelList;
	}

	/**
	 * Get all the relative extend store model list from persistence by refUUID,
	 * and field Setting list.
	 * 
	 * @param refFieldSettingList
	 * @param refUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceExtensionException 
	 */
	public List<ServiceEntityNode> getExtendModelList(
			List<ServiceEntityNode> refFieldSettingList, String refUUID,
			String client) throws ServiceEntityConfigureException, ServiceExtensionException {
		if (ServiceCollectionsHelper.checkNullList(refFieldSettingList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode rawField : refFieldSettingList) {
			ServiceExtendFieldSetting serviceExtendFieldSetting = (ServiceExtendFieldSetting) rawField;
			String storeModelName = serviceExtendFieldSetting.getStoreModelName();
			if(ServiceEntityStringHelper.checkNullString(storeModelName)){
				throw new ServiceExtensionException(ServiceExtensionException.PARA_WRONG_STOREMODEL, storeModelName);
			}
			List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
			keyList.add(new ServiceBasicKeyStructure(refUUID, IReferenceNodeFieldConstant.REFUUID));
			keyList.add(new ServiceBasicKeyStructure(serviceExtendFieldSetting.getUuid(), IServiceEntityNodeFieldConstant.PARENTNODEUUID));
			ServiceEntityNode storeModule = serviceExtensionSettingManager.getEntityNodeByKeyList(keyList, serviceExtendFieldSetting.getStoreModelName(), client, null);
			if(storeModule != null){
				resultList.add(storeModule);
			} else {
				storeModule = newStoreModelUnion(serviceExtendFieldSetting, serviceExtendFieldSetting.getStoreModelName());
				resultList.add(storeModule);
			}
		}
		return resultList;
	}

	public void updateStoreModelUnion(
			ServiceExtendUIModel serviceExtendUIModel,
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			String refUUID, String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException, ServiceExtensionException {
		if (ServiceEntityStringHelper.checkNullString(serviceExtendUIModel
				.getStoreModelName())) {
			throw new ServiceExtensionException(ServiceExtensionException.PARA_WRONG_STOREMODEL, serviceExtendFieldSetting.getFieldName());
		}
		/*
		 * [Step1] Trying to get the extend store union
		 */
		ReferenceNode serviceExtendStoreUnion = (ReferenceNode) getStoreModelUnion(
				serviceExtendUIModel.getUuid(),
				IServiceEntityNodeFieldConstant.UUID,
				serviceExtendUIModel.getStoreModelName());
		if (serviceExtendStoreUnion == null) {
			// In case need to create new instance and insert
			serviceExtendStoreUnion = newStoreModelUnion(
					serviceExtendFieldSetting,
					serviceExtendUIModel.getStoreModelName());
		}
		/*
		 * [Step2] Conversion UI Model to store model
		 */
		convertUIModelToExtendModel(serviceExtendUIModel,
				(ReferenceNode) serviceExtendStoreUnion);
		serviceExtendStoreUnion.setRefUUID(refUUID);
		/*
		 * [Step3] Update to persistence
		 */
		serviceExtensionSettingManager.updateSENode(serviceExtendStoreUnion,
				logonUserUUID, organizationUUID);

	}

	public ReferenceNode getStoreModelUnion(String keyValue, String keyName,
			String storeModelName) throws ServiceEntityConfigureException, ServiceExtensionException {
		if (ServiceEntityStringHelper.checkNullString(storeModelName)) {
			throw new ServiceExtensionException(ServiceExtensionException.PARA_WRONG_STOREMODEL, keyValue);
		}
		return (ReferenceNode) serviceExtensionSettingManager
				.getEntityNodeByKey(keyValue, keyName, storeModelName, null);
	}

	public ReferenceNode newStoreModelUnion(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			String storeModelName) throws ServiceEntityConfigureException, ServiceExtensionException {
		if (ServiceEntityStringHelper.checkNullString(storeModelName)) {
			throw new ServiceExtensionException(ServiceExtensionException.PARA_WRONG_STOREMODEL, serviceExtendFieldSetting.getFieldName());
		}
		return (ReferenceNode) serviceExtensionSettingManager.newEntityNode(
				serviceExtendFieldSetting, storeModelName);
	}

	public void convertToExtendUIModel(ReferenceNode storeModel,
			ServiceExtendUIModel serviceExtendUIModel) {
		if (storeModel != null && serviceExtendUIModel != null) {
			serviceExtendUIModel.setParentNodeUUID(storeModel
					.getParentNodeUUID());
			serviceExtendUIModel.setRootNodeUUID(storeModel.getRootNodeUUID());
			serviceExtendUIModel.setUuid(storeModel.getUuid());
			// [RefFieldUUID] equals parent field UUID
			serviceExtendUIModel.setRefFieldUUID(storeModel
					.getParentNodeUUID());
			if (IServiceModelConstants.SerExtendStr3000Field.equals(storeModel
					.getNodeName())) {
				SerExtendStr3000Field serExtendStr3000Field = (SerExtendStr3000Field) storeModel;
				serviceExtendUIModel.setFieldValue(serExtendStr3000Field
						.getFieldValue());
				serviceExtendUIModel
						.setStoreModelName(IServiceModelConstants.SerExtendStr3000Field);
			}
			if (IServiceModelConstants.SerExtendStr800Field.equals(storeModel
					.getNodeName())) {
				SerExtendStr800Field serExtendStr800Field = (SerExtendStr800Field) storeModel;
				serviceExtendUIModel.setFieldValue(serExtendStr800Field
						.getFieldValue());
				serviceExtendUIModel
						.setStoreModelName(IServiceModelConstants.SerExtendStr800Field);
			}
			if (IServiceModelConstants.SerExtendStr100Field.equals(storeModel
					.getNodeName())) {
				SerExtendStr100Field serExtendStr100Field = (SerExtendStr100Field) storeModel;
				serviceExtendUIModel.setFieldValue(serExtendStr100Field
						.getFieldValue());
				serviceExtendUIModel
						.setStoreModelName(IServiceModelConstants.SerExtendStr100Field);
			}
			if (IServiceModelConstants.SerExtendIntField.equals(storeModel
					.getNodeName())) {
				SerExtendIntField serExtendIntField = (SerExtendIntField) storeModel;
				serviceExtendUIModel.setFieldValue(serExtendIntField
						.getFieldValue());
				serviceExtendUIModel
						.setStoreModelName(IServiceModelConstants.SerExtendIntField);
			}
			if (IServiceModelConstants.SerExtendDoubleField.equals(storeModel
					.getNodeName())) {
				SerExtendDoubleField serExtendDoubleField = (SerExtendDoubleField) storeModel;
				serviceExtendUIModel.setFieldValue(serExtendDoubleField
						.getFieldValue());
				serviceExtendUIModel
						.setStoreModelName(IServiceModelConstants.SerExtendDoubleField);
			}
		}
	}

	public void convertUIModelToExtendModel(
			ServiceExtendUIModel serviceExtendUIModel, ReferenceNode rawEntity) {
		if (rawEntity != null && serviceExtendUIModel != null) {
			if (!ServiceEntityStringHelper.checkNullString(serviceExtendUIModel
					.getUuid())) {
				rawEntity.setUuid(serviceExtendUIModel.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceExtendUIModel
					.getRootNodeUUID())) {
				rawEntity.setRootNodeUUID(serviceExtendUIModel
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceExtendUIModel
					.getParentNodeUUID())) {
				rawEntity.setParentNodeUUID(serviceExtendUIModel
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceExtendUIModel
					.getClient())) {
				rawEntity.setClient(serviceExtendUIModel.getClient());
			}
			if (IServiceModelConstants.SerExtendStr3000Field.equals(rawEntity
					.getNodeName())) {
				SerExtendStr3000Field serExtendStr3000Field = (SerExtendStr3000Field) rawEntity;
				serExtendStr3000Field.setFieldValue(serviceExtendUIModel
						.getFieldValue() == null ? null : serviceExtendUIModel
						.getFieldValue().toString());
			}
			if (IServiceModelConstants.SerExtendStr800Field.equals(rawEntity
					.getNodeName())) {
				SerExtendStr800Field serExtendStr800Field = (SerExtendStr800Field) rawEntity;
				serExtendStr800Field.setFieldValue(serviceExtendUIModel
						.getFieldValue() == null ? null : serviceExtendUIModel
						.getFieldValue().toString());
			}
			if (IServiceModelConstants.SerExtendStr100Field.equals(rawEntity
					.getNodeName())) {
				SerExtendStr100Field serExtendStr100Field = (SerExtendStr100Field) rawEntity;
				serExtendStr100Field.setFieldValue(serviceExtendUIModel
						.getFieldValue() == null ? null : serviceExtendUIModel
						.getFieldValue().toString());
			}
			if (IServiceModelConstants.SerExtendIntField.equals(rawEntity
					.getNodeName())) {
				SerExtendIntField serExtendIntField = (SerExtendIntField) rawEntity;
				serExtendIntField.setFieldValue(serviceExtendUIModel
						.getFieldValue() == null ? 0 : Integer
						.parseInt(serviceExtendUIModel.getFieldValue()
								.toString()));
			}
			if (IServiceModelConstants.SerExtendDoubleField.equals(rawEntity
					.getNodeName())) {
				SerExtendDoubleField serExtendDoubleField = (SerExtendDoubleField) rawEntity;
				serExtendDoubleField.setFieldValue(serviceExtendUIModel
						.getFieldValue() == null ? 0 : Double
						.parseDouble(serviceExtendUIModel.getFieldValue()
								.toString()));
			}

		}
	}

	public void convertToExtendUIModel(
			ServiceExtendFieldSetting serviceExtendFieldSetting,
			ServiceExtendUIModel serviceExtendUIModel) {
		if (serviceExtendFieldSetting != null && serviceExtendUIModel != null) {
			serviceExtendUIModel.setRefFieldName(serviceExtendFieldSetting
					.getFieldName());
			serviceExtendUIModel.setRefFieldUUID(serviceExtendFieldSetting
					.getUuid());
		}
	}

}
