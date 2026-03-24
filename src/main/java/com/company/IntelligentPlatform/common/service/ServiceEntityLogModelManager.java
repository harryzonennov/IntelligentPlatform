package com.company.IntelligentPlatform.common.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogItemUIModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogModelSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogModelUIModel;
// TODO-DAO: import platform.foundation.DAO.ServiceEntityLogModelDAO;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceModelExtensionHelper.ExtensionUnionResponse;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityBindModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogItem;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModelConfigureProxy;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Logic Manager CLASS FOR Service Entity [Province]
 * 
 * @author
 * @date Sun Feb 10 13:54:24 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
public class ServiceEntityLogModelManager extends ServiceEntityManager {

	public static final String METHOD_ConvServiceEntityLogItemToUI = "convServiceEntityLogItemToUI";

	public static final String METHOD_ConvServiceEntityLogModelToUI = "convServiceEntityLogModelToUI";

	public static final String METHOD_ConvUIToServiceEntityLogModel = "convUIToServiceEntityLogModel";

	public static final String METHOD_ConvLogonUserToUI = "convLogonUserToUI";

	private Map<Integer, String> messageTypeMap;

	private Map<Integer, String> processModeMap;

	// TODO-DAO: @Autowired

	// TODO-DAO: 	ServiceEntityLogModelDAO serviceEntityLogModelDAO;

	@Autowired
	ServiceEntityLogModelConfigureProxy serviceEntityLogModelConfigureProxy;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ServiceModuleProxy serviceModuleProxy;

	public ServiceEntityLogModelManager() {
		super.seConfigureProxy = new ServiceEntityLogModelConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ServiceEntityLogModelDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(serviceEntityLogModelDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(serviceEntityLogModelConfigureProxy);
	}

	public void admDeleteLog(Object keyValue, String keyName,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> rawLogList = this.getEntityNodeListByKey(
				keyValue, keyName, ServiceEntityLogModel.NODENAME, null);
		if (!ServiceCollectionsHelper.checkNullList(rawLogList)) {
			for (ServiceEntityNode rawSENode : rawLogList) {
				List<ServiceEntityNode> rawLogItemList = this
						.getEntityNodeListByKey(rawSENode.getUuid(),
								IServiceEntityNodeFieldConstant.PARENTNODEUUID,
								ServiceEntityLogItem.NODENAME, null);
				if (!ServiceCollectionsHelper.checkNullList(rawLogItemList)) {
					this.deleteSENode(rawLogItemList, null, null);
				}
				this.deleteSENode(rawSENode, logonUserUUID, organizationUUID);
			}
		}
	}

	/**
	 * Wrapper method to generate logModel and sub items according to compare
	 * the seNode new value and old value
	 * 
	 * @param seNode
	 * @param seNodeBack
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> logServiceEntityNodeWrap(
			ServiceEntityNode seNode,
			ServiceEntityNode seNodeBack,
			int messageType,
			Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
			String logonUserUUID,			
			String organizationUUID) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = logServiceEntityNode(seNode,
				seNodeBack, messageType, setLogIdNameCallBack, logonUserUUID, organizationUUID);
		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			insertSENodeList(resultList, logonUserUUID, organizationUUID);
		}
		return resultList;
	}

	public List<ServiceEntityNode> logServiceEntityNode(
			ServiceEntityNode seNode, ServiceEntityNode seNodeBack, int messageType,
			Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
			 String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		/*
		 * [Step1] Decide the process mode for this log model
		 */
		if (seNode == null && seNodeBack == null) {
			return null;
		}
		String client = seNode == null ? seNodeBack.getClient() : seNode
				.getClient();
		ServiceEntityLogModel serviceEntityLogModel = (ServiceEntityLogModel) newRootEntityNode(client);
		
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (seNodeBack == null) {
			setLogModelReferInfo(serviceEntityLogModel, seNode,
					ServiceEntityBindModel.PROCESSMODE_CREATE, messageType,
					logonUserUUID, organizationUUID);
		}
		if (seNode == null) {
			setLogModelReferInfo(serviceEntityLogModel, seNodeBack,
					ServiceEntityBindModel.PROCESSMODE_DELETE, messageType,
					logonUserUUID, organizationUUID);
		}
		if (seNode != null && seNodeBack != null) {
			setLogModelReferInfo(serviceEntityLogModel, seNode,
					ServiceEntityBindModel.PROCESSMODE_UPDATE, messageType,
					logonUserUUID, organizationUUID);
		}
		
		
		/*
		 * [Step2] Generate log items according to different field values, Only
		 * for Update currently
		 */
		boolean logFlag = false;
		if (seNode != null && seNodeBack != null) {
			List<ServiceEntityNode> logItemList = logDiffValues(seNode,
					seNodeBack, serviceEntityLogModel);
			if (ServiceCollectionsHelper.checkNullList(logItemList)) {
				// In case there is no different values for update
				return null;
			} else {
				logFlag = true;
				resultList.addAll(logItemList);
			}
		}
		// In case there is value differences and need to record log
		if(logFlag){			
			if(setLogIdNameCallBack != null){
				String[] logIdName = setLogIdNameCallBack.apply(seNode == null ? seNodeBack:seNode);
				if(logIdName != null){
					if(logIdName.length == 1){
						if(!ServiceEntityStringHelper.checkNullString(logIdName[0])){
							serviceEntityLogModel.setId(logIdName[0]);
						}
					}
					if(logIdName.length == 2){
						if(!ServiceEntityStringHelper.checkNullString(logIdName[0])){
							serviceEntityLogModel.setId(logIdName[0]);
						}
						if(!ServiceEntityStringHelper.checkNullString(logIdName[1])){
							serviceEntityLogModel.setName(logIdName[1]);
						}
					}
				}				
			}
			resultList.add(serviceEntityLogModel);
			return resultList;
		}
		return null;
	}

	private void setLogModelReferInfo(
			ServiceEntityLogModel serviceEntityLogModel,
			ServiceEntityNode seNode, int processMode, int messageType,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		this.buildReferenceNode(seNode, serviceEntityLogModel,
				ServiceEntityFieldsHelper.getCommonPackage(seNode.getClass()));
		serviceEntityLogModel.setId(seNode.getId());
		serviceEntityLogModel.setName(seNode.getName());
		serviceEntityLogModel.setMessageType(messageType);
		serviceEntityLogModel.setProcessMode(processMode);
		this.setAdminData(serviceEntityLogModel,
				ServiceEntityBindModel.PROCESSMODE_CREATE, logonUserUUID,
				organizationUUID);

	}

	/**
	 * Generate log item list based on different field values
	 * 
	 * @param seNode
	 * @param seNodeBack
	 * @param serviceEntityLogModel
	 * @return
	 */
	public List<ServiceEntityNode> logDiffValues(ServiceEntityNode seNode,
			ServiceEntityNode seNodeBack,
			ServiceEntityLogModel serviceEntityLogModel) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getServiceSelfDefinedFieldsList(seNode.getClass(),
						ServiceEntityNode.class);
		List<ServiceEntityNode> logItemList = new ArrayList<>();
		for (Field field : fieldList) {
			boolean excludeFlag = checkExcludeFieldName(field.getName());
			if (excludeFlag) {
				continue;
			}
			excludeFlag = checkExcludeByFieldType(field);
			if (excludeFlag) {
				continue;
			}
			java.beans.PropertyDescriptor pd;
//			try {
//				pd = new java.beans.PropertyDescriptor(field.getName(), seNode.getClass());
//				Method getMethod = pd.getReadMethod();
//				Object objectNew = getMethod.invoke(seNode);
//				excludeFlag = checkExcludeByFieldValue(objectNew);
//				String sNew = ServiceEntityStringHelper.EMPTYSTRING;
//				sNew = objectNew == null ? "" : objectNew.toString();
//				if (excludeFlag) {
//					continue;
//				}
//				String sBack = ServiceEntityStringHelper.EMPTYSTRING;
//				Object objectBack = null;
//				if (seNodeBack != null) {
//					objectBack = getMethod.invoke(seNodeBack);
//				}
//				sBack = objectBack == null ? "" : objectBack.toString();
//				if (!sNew.equals(sBack)) {
//					// In case values are different
//					ServiceEntityLogItem serviceEntityLogItem = (ServiceEntityLogItem) newEntityNode(
//							serviceEntityLogModel,
//							ServiceEntityLogItem.NODENAME);
//					serviceEntityLogItem
//							.setFieldType(field.getType().getName());
//					serviceEntityLogItem.setId(field.getName());
//					serviceEntityLogItem.setName(field.getName());
//					serviceEntityLogItem.setOldValue(sBack);
//					serviceEntityLogItem.setNewValue(sNew);
//					this.buildReferenceNode(seNode, serviceEntityLogItem,
//							ServiceEntityFieldsHelper.getCommonPackage(seNode
//									.getClass()));
//					logItemList.add(serviceEntityLogItem);
//				}
//			} catch (IntrospectionException e) {
//				// Just skip this run
//			} catch (IllegalAccessException e) {
//				// Just skip this run
//			} catch (IllegalArgumentException e) {
//				// Just skip this run
//			} catch (InvocationTargetException e) {
//				// Just skip this run
//			} catch (ServiceEntityConfigureException e) {
//				// Just skip this run
//			}
		}
		return logItemList;
	}

	public boolean checkExcludeByFieldType(Field field) {
        return field.getType().getName().equals(byte[].class.getName());
    }

	public boolean checkExcludeByFieldValue(Object rawValue) {
		if (rawValue == null) {
			return false;
		}
        return rawValue.toString().length() > 2500;
    }

	public boolean checkExcludeFieldName(String fieldName) {
		if (ServiceEntityStringHelper.checkNullString(fieldName)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.UUID)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.CLIENT)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.PARENTNODEUUID)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.ROOTNODEUUID)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.CREATEDBY)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.CREATEDTIME)) {
			return true;
		}
		if (fieldName.equals(IServiceEntityNodeFieldConstant.LASTUPDATETIME)) {
			return true;
		}
		if (fieldName.equals("lastUpdateBy")) {
			return true;
		}
		if (fieldName.equals(IReferenceNodeFieldConstant.REFNODENAME)) {
			return true;
		}
		if (fieldName.equals(IReferenceNodeFieldConstant.REFPACKAGENAME)) {
			return true;
		}
        return fieldName.equals(IReferenceNodeFieldConstant.REFSENAME);
    }

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convServiceEntityLogModelToUI(
			ServiceEntityLogModel serviceEntityLogModel,
			ServiceEntityLogModelUIModel serviceEntityLogModelUIModel)
			throws ServiceEntityInstallationException {
		if (serviceEntityLogModel != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serviceEntityLogModel.getUuid())) {
				serviceEntityLogModelUIModel.setUuid(serviceEntityLogModel
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceEntityLogModel.getParentNodeUUID())) {
				serviceEntityLogModelUIModel
						.setParentNodeUUID(serviceEntityLogModel
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceEntityLogModel.getRootNodeUUID())) {
				serviceEntityLogModelUIModel
						.setRootNodeUUID(serviceEntityLogModel
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceEntityLogModel.getClient())) {
				serviceEntityLogModelUIModel.setClient(serviceEntityLogModel
						.getClient());
			}
			serviceEntityLogModelUIModel.setId(serviceEntityLogModel.getId());
			serviceEntityLogModelUIModel.setName(serviceEntityLogModel
					.getName());
			serviceEntityLogModelUIModel.setCreatedBy(serviceEntityLogModel
					.getCreatedBy());
			serviceEntityLogModelUIModel.setMessageType(serviceEntityLogModel
					.getMessageType());
			initMessageTypeMap();
			serviceEntityLogModelUIModel
					.setMessageTypeValue(this.messageTypeMap
							.get(serviceEntityLogModel.getMessageType()));
			initProcessModeMap();
			serviceEntityLogModelUIModel
					.setProcessModeValue(this.processModeMap
							.get(serviceEntityLogModel.getProcessMode()));
			serviceEntityLogModelUIModel.setRefNodeName(serviceEntityLogModel
					.getRefNodeName());
			serviceEntityLogModelUIModel.setRefSEName(serviceEntityLogModel
					.getRefSEName());
			if (serviceEntityLogModel.getLastUpdateTime() != null) {
				serviceEntityLogModelUIModel
						.setLastUpdateTime(DefaultDateFormatConstant.DATE_MIN_FORMAT
								.format(serviceEntityLogModel
										.getLastUpdateTime()));
			}

			serviceEntityLogModelUIModel.setProcessMode(serviceEntityLogModel
					.getProcessMode());
			serviceEntityLogModelUIModel.setRefUUID(serviceEntityLogModel
					.getRefUUID());

		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:serviceEntityLogModel
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToServiceEntityLogModel(
			ServiceEntityLogModelUIModel serviceEntityLogModelUIModel,
			ServiceEntityLogModel rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(serviceEntityLogModelUIModel.getUuid())) {
			rawEntity.setUuid(serviceEntityLogModelUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceEntityLogModelUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(serviceEntityLogModelUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceEntityLogModelUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(serviceEntityLogModelUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceEntityLogModelUIModel.getClient())) {
			rawEntity.setClient(serviceEntityLogModelUIModel.getClient());
		}
		rawEntity.setCreatedBy(serviceEntityLogModelUIModel.getCreatedBy());
		rawEntity.setMessageType(serviceEntityLogModelUIModel.getMessageType());
		rawEntity.setRefNodeName(serviceEntityLogModelUIModel.getRefNodeName());
		rawEntity.setRefSEName(serviceEntityLogModelUIModel.getRefSEName());
		rawEntity.setId(serviceEntityLogModelUIModel.getId());
		rawEntity.setName(serviceEntityLogModelUIModel.getName());
		if (!ServiceEntityStringHelper
				.checkNullString(serviceEntityLogModelUIModel
						.getLastUpdateTime())) {
			try {
				rawEntity
						.setCreatedTime(DefaultDateFormatConstant.DATE_MIN_FORMAT.parse(serviceEntityLogModelUIModel
										.getLastUpdateTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setProcessMode(serviceEntityLogModelUIModel.getProcessMode());
		rawEntity.setRefUUID(serviceEntityLogModelUIModel.getRefUUID());
	}

	public Map<Integer, String> initMessageTypeMap()
			throws ServiceEntityInstallationException {
		if (this.messageTypeMap == null) {
			this.messageTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					ServiceEntityLogModelUIModel.class, "messageType");
		}
		return this.messageTypeMap;
	}

	public Map<Integer, String> initProcessModeMap()
			throws ServiceEntityInstallationException {
		if (this.processModeMap == null) {
			this.processModeMap = serviceDropdownListHelper.getUIDropDownMap(
					ServiceEntityLogModelUIModel.class, "processMode");
		}
		return this.processModeMap;
	}

	public List<ServiceEntityNode> searchInternal(
			ServiceEntityLogModelSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[serviceEntityLogModel]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ServiceEntityLogModel.SENAME);
		searchNodeConfig0.setNodeName(ServiceEntityLogModel.NODENAME);
		searchNodeConfig0.setNodeInstID(ServiceEntityLogModel.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[logonUser]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(LogonUser.SENAME);
		searchNodeConfig2.setNodeName(LogonUser.NODENAME);
		searchNodeConfig2.setNodeInstID(LogonUser.SENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig2.setMapBaseFieldName("createdBy");
		searchNodeConfig2.setMapSourceFieldName("uuid");
		searchNodeConfig2.setBaseNodeInstID(ServiceEntityLogModel.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		convertSearchModelFormat(searchModel);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	private void convertSearchModelFormat(
			ServiceEntityLogModelSearchModel searchModel) {
		try {
			if (!ServiceEntityStringHelper.checkNullString(searchModel
					.getLastUpdateTimeStrHigh())) {
				searchModel.setLastUpdateTimeHigh(DefaultDateFormatConstant.DATE_FORMAT.parse(searchModel.getLastUpdateTimeStrHigh()));
			}
			if (!ServiceEntityStringHelper.checkNullString(searchModel
					.getLastUpdateTimeStrLow())) {
				searchModel.setLastUpdateTimeLow(DefaultDateFormatConstant.DATE_FORMAT.parse(searchModel.getLastUpdateTimeStrLow()));
			}
		} catch (ParseException e) {
			// do nothing
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convLogonUserToUI(LogonUser logonUser,
			ServiceEntityLogModelUIModel serviceEntityLogModelUIModel) {
		if (logonUser != null) {
			serviceEntityLogModelUIModel.setRefLogonUserId(logonUser.getId());
			serviceEntityLogModelUIModel.setRefLogonUserName(logonUser
					.getName());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceEntityLogItemToUI(
			ServiceEntityLogItem serviceEntityLogItem,
			ServiceEntityLogItemUIModel serviceEntityLogItemUIModel) {
		if (serviceEntityLogItem != null) {
			if (!ServiceEntityStringHelper.checkNullString(serviceEntityLogItem
					.getUuid())) {
				serviceEntityLogItemUIModel.setUuid(serviceEntityLogItem
						.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceEntityLogItem
					.getParentNodeUUID())) {
				serviceEntityLogItemUIModel
						.setParentNodeUUID(serviceEntityLogItem
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceEntityLogItem
					.getRootNodeUUID())) {
				serviceEntityLogItemUIModel
						.setRootNodeUUID(serviceEntityLogItem.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceEntityLogItem
					.getClient())) {
				serviceEntityLogItemUIModel.setClient(serviceEntityLogItem
						.getClient());
			}
			serviceEntityLogItemUIModel.setId(serviceEntityLogItem.getId());
			serviceEntityLogItemUIModel.setName(serviceEntityLogItem.getName());
			serviceEntityLogItemUIModel.setRefSEName(serviceEntityLogItem
					.getRefNodeName());
			serviceEntityLogItemUIModel.setRefNodeName(serviceEntityLogItem
					.getRefNodeName());
			serviceEntityLogItemUIModel.setRefUUID(serviceEntityLogItem
					.getRefUUID());
			serviceEntityLogItemUIModel.setNewValue(serviceEntityLogItem
					.getNewValue());
			serviceEntityLogItemUIModel.setFieldType(serviceEntityLogItem
					.getFieldType());
			serviceEntityLogItemUIModel.setOldValue(serviceEntityLogItem
					.getOldValue());
		}
	}

	/**
	 * Main Entrance to generate log model and items for Service Module
	 * 
	 * @param serviceModule
	 * @param serviceModuleBack
	 * @param messageType
	 * @param serviceUIModelExtension
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @param serviceUIModelExtensionUnion
	 * @return
	 * @throws ServiceModuleProxyException
	 */
	public List<ServiceEntityNode> logActionForServiceModuleWrapper(
			ServiceModule serviceModule, ServiceModule serviceModuleBack,
			int messageType, ServiceUIModelExtension serviceUIModelExtension,  Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
			String logonUserUUID, String organizationUUID,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws ServiceModuleProxyException {
		List<ServiceEntityNode> logList = logActionForServiceModule(
				serviceModule, serviceModuleBack, messageType,
				serviceUIModelExtension, setLogIdNameCallBack, logonUserUUID, organizationUUID,
				serviceUIModelExtensionUnion);
		if (!ServiceCollectionsHelper.checkNullList(logList)) {
			insertSENodeList(logList, logonUserUUID, organizationUUID);
		}
		return logList;
	}

	/**
	 * Main Entrance to generate log model and items for Service Module
	 * 
	 * @param serviceModule
	 * @param serviceModuleBack
	 * @param messageType
	 * @param serviceUIModelExtension
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @param serviceUIModelExtensionUnion
	 * @return
	 * @throws ServiceModuleProxyException
	 */
	public List<ServiceEntityNode> logActionForServiceModule(
			ServiceModule serviceModule, ServiceModule serviceModuleBack,
			int messageType, ServiceUIModelExtension serviceUIModelExtension, Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
			String logonUserUUID, String organizationUUID,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws ServiceModuleProxyException {
		try {
			/**
			 * [Step1] Get the core seNode value from new model and back model
			 */
			if (serviceModule == null) {
				return null;
			}
			Field coreModuleField = ServiceModuleProxy
					.getModuleFieldByNodeInstId(serviceModule.getClass(),
							serviceUIModelExtensionUnion.getNodeInstId());
			if (coreModuleField == null) {
				// raise exception when no core module field found
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.PARA_NOCOREMODULE,
						serviceModule.getClass().getSimpleName());
			}
			coreModuleField.setAccessible(true);
			ServiceEntityNode seNodeValue = null;
			if (serviceModule != null) {
				seNodeValue = (ServiceEntityNode) coreModuleField
						.get(serviceModule);
			}
			ServiceEntityNode seNodeBackValue = null;
			if (serviceModuleBack != null) {
				seNodeBackValue = (ServiceEntityNode) coreModuleField
						.get(serviceModuleBack);
			}
			List<ServiceEntityNode> logModelList = logServiceEntityNode(
					seNodeValue, seNodeBackValue,
					ServiceEntityLogModel.MESSAGE_TYPE_INFO, setLogIdNameCallBack, logonUserUUID,
					organizationUUID);
			/**
			 * [Step2] Log for sub list fields
			 */
			List<Field> listTypeFields = ServiceModuleProxy
					.getListTypeFields(serviceModule.getClass());
			if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
				for (Field listField : listTypeFields) {
					IServiceModuleFieldConfig subListFieldConfig = listField
							.getAnnotation(IServiceModuleFieldConfig.class);
					if (subListFieldConfig == null) {
						throw new ServiceModuleProxyException(
								ServiceUIModuleProxyException.PARA_NOANNOTATION,
								listField.getName());
					}
					ExtensionUnionResponse extensionUnionResponse = ServiceModelExtensionHelper
							.getUIModelExtensionByNodeInstId(
									subListFieldConfig.nodeInstId(),
									serviceUIModelExtension);
					if (extensionUnionResponse == null) {
						continue;
					}
					ServiceUIModelExtensionUnion subExtensionUnion = extensionUnionResponse.getServiceUIModelExtensionUnion();
					if (subExtensionUnion == null) {
						continue;
					}
					ServiceUIModelExtension subExtension = extensionUnionResponse.getServiceUIModelExtension();
					if (ServiceEntityFieldsHelper
							.checkSuperClassExtends(ServiceEntityFieldsHelper
									.getListSubType(listField),
									ServiceEntityNode.class.getSimpleName())) {						
						/*
						 * [Step 2.3] Generate the log for sub SE node list and
						 * merge to result
						 */
						List<ServiceEntityNode> tempLogModelList;
						try {
							tempLogModelList = this
									.logSubServiceEntityNodeListWrapper(
											listField, serviceModule,
											serviceModuleBack, messageType,
											serviceUIModelExtension,
											serviceUIModelExtensionUnion, setLogIdNameCallBack,
											logonUserUUID, organizationUUID);
						} catch (IllegalArgumentException
								| IllegalAccessException
								| ServiceEntityConfigureException e) {
							continue;
						}
						if (!ServiceCollectionsHelper
								.checkNullList(tempLogModelList)) {
							mergeToLogModelList(logModelList, tempLogModelList);
						}
					}
					if (ServiceEntityFieldsHelper
							.checkSuperClassExtends(ServiceEntityFieldsHelper
									.getListSubType(listField),
									ServiceModule.class.getSimpleName())) {
						/*
						 * [Step 2.7] Generate the log for sub service module
						 * list and merge to result
						 */
						List<ServiceEntityNode> tempLogModelList;
						try {
							tempLogModelList = logSubServiceModelListWrapper(
									listField, serviceModule,
									serviceModuleBack, messageType,
									subExtension, subExtensionUnion, setLogIdNameCallBack,
									logonUserUUID, organizationUUID);
						} catch (IllegalArgumentException
								| IllegalAccessException
								| ServiceEntityConfigureException e) {
							continue;
						}
						if (!ServiceCollectionsHelper
								.checkNullList(tempLogModelList)) {
							mergeToLogModelList(logModelList, tempLogModelList);
						}
					}
				}
			}
			// Log into DB
			// this.insertSENodeList(logModelList, logonUserUUID,
			// organizationUUID);
			return logModelList;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return null;
		} catch (ServiceEntityConfigureException e1) {
			return null;
		}

	}

	private ServiceModule filterSENodeFromServiceModuleList(String uuid,
			List<ServiceModule> subListValue,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws ServiceModuleProxyException, IllegalArgumentException,
			IllegalAccessException {
		if (ServiceCollectionsHelper.checkNullList(subListValue)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(uuid)) {
			return null;
		}
		for (int i = 0; i < subListValue.size(); i++) {
			ServiceModule serviceModule = subListValue.get(i);
			ServiceEntityNode seNode = ServiceModuleProxy
					.getCoreEntityNodeValue(serviceModule,
							serviceUIModelExtensionUnion);
			if (seNode != null) {
				if (uuid.equals(seNode.getUuid())) {
					return serviceModule;
				}
			}
		}
		return null;
	}

	/**
	 * Merge and add ServiceLogModel & ServiceLogItem to current list, with
	 * duplicate check
	 * 
	 * @param existedList
	 * @param newList
	 * @return
	 */
	public List<ServiceEntityNode> mergeToLogModelList(
			List<ServiceEntityNode> existedList, List<ServiceEntityNode> newList) {
		if (ServiceCollectionsHelper.checkNullList(existedList)) {
			return newList;
		}
		if (ServiceCollectionsHelper.checkNullList(newList)) {
			return existedList;
		}
		for (ServiceEntityNode seNode : newList) {
			if (IServiceModelConstants.ServiceEntityLogModel.equals(seNode
					.getServiceEntityName())) {
				// In case this seNode is 'ServiceEntityLogModel'
				ServiceEntityLogModel serviceEntityLogModel = (ServiceEntityLogModel) seNode;
				// Check duplicate for refSEName, refNodeName in existed list
				List<ServiceEntityNode> tmpModeList = filterServiceLogModelList(
						newList, serviceEntityLogModel.getRefSEName(),
						serviceEntityLogModel.getRefNodeName());
				if (ServiceCollectionsHelper.checkNullList(tmpModeList)) {
					existedList.add(serviceEntityLogModel);
				}
			}
			if (IServiceModelConstants.ServiceEntityLogItem.equals(seNode
					.getServiceEntityName())) {
				// In case this seNode is 'ServiceEntityLogItem'
				ServiceEntityLogItem serviceEntityLogItem = (ServiceEntityLogItem) seNode;
				// Check duplicate for refSEName, refNodeName in existed list
				List<ServiceEntityNode> tmpModeList = filterServiceLogItemList(
						newList, serviceEntityLogItem.getRefSEName(),
						serviceEntityLogItem.getRefNodeName(),
						serviceEntityLogItem.getId());
				if (ServiceCollectionsHelper.checkNullList(tmpModeList)) {
					existedList.add(serviceEntityLogItem);
				}
			}
		}
		return existedList;
	}

	public List<ServiceEntityNode> filterServiceLogModelList(
			List<ServiceEntityNode> rawServiceLogModelList, String refSEName,
			String refNodeName) {
		if (ServiceCollectionsHelper.checkNullList(rawServiceLogModelList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(refSEName)
				|| ServiceEntityStringHelper.checkNullString(refNodeName)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawServiceLogModelList) {
			if (IServiceModelConstants.ServiceEntityLogModel.equals(seNode)) {
				ServiceEntityLogModel serviceEntityLogModel = (ServiceEntityLogModel) seNode;
				if (refSEName.equals(serviceEntityLogModel.getRefSEName())
						&& refSEName.equals(serviceEntityLogModel
								.getRefNodeName())) {
					resultList.add(serviceEntityLogModel);
				}
			}
		}
		return resultList;
	}

	public List<ServiceEntityNode> filterServiceLogItemList(
			List<ServiceEntityNode> rawServiceLogItemList, String refSEName,
			String refNodeName, String fieldName) {
		if (ServiceCollectionsHelper.checkNullList(rawServiceLogItemList)) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(refSEName)
				|| ServiceEntityStringHelper.checkNullString(refNodeName)
				|| ServiceEntityStringHelper.checkNullString(fieldName)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawServiceLogItemList) {
			if (IServiceModelConstants.ServiceEntityLogItem.equals(seNode)) {
				ServiceEntityLogItem serviceEntityLogItem = (ServiceEntityLogItem) seNode;
				if (refSEName.equals(serviceEntityLogItem.getRefSEName())
						&& refSEName.equals(serviceEntityLogItem
								.getRefNodeName())
						&& fieldName.equals(serviceEntityLogItem.getId())) {
					resultList.add(serviceEntityLogItem);
				}
			}
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<ServiceEntityNode> logSubServiceEntityNodeListWrapper(
			Field listField, ServiceModule serviceModule,
			ServiceModule serviceModuleBack, int messageType,
			ServiceUIModelExtension serviceUIModelExtension,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion, Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, IllegalArgumentException,
			IllegalAccessException, ServiceEntityConfigureException {
		/**
		 * [Step1] Get the list value from list field of the service module
		 */
		listField.setAccessible(true);
		List<ServiceEntityNode> subListValue = (List<ServiceEntityNode>) listField
				.get(serviceModule);
		List<ServiceEntityNode> subListValueBack = null;
		if (serviceModuleBack != null) {
			subListValueBack = (List<ServiceEntityNode>) listField
					.get(serviceModuleBack);
		}
		List<ServiceEntityNode> result = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(subListValue)
				&& ServiceCollectionsHelper.checkNullList(subListValueBack)) {
			return null;
		}
		if (!ServiceCollectionsHelper.checkNullList(subListValue)) {
			// In case of creation or update
			for (int i = 0; i < subListValue.size(); i++) {
				ServiceEntityNode subSENode = subListValue
						.get(i);
				ServiceEntityNode subSENodeBack = ServiceCollectionsHelper
						.filterSENodeOnline(subSENode.getUuid(),
								subListValueBack);
				List<ServiceEntityNode> tmpResult = logServiceEntityNode(
						subSENode, subSENodeBack, messageType, setLogIdNameCallBack, logonUserUUID,
						organizationUUID);
				if (!ServiceCollectionsHelper.checkNullList(tmpResult)) {
					mergeToLogModelList(result, tmpResult);
				}
			}
		}

		if (!ServiceCollectionsHelper.checkNullList(subListValueBack)) {
			// record the deleted log
			for (int i = 0; i < subListValueBack.size(); i++) {
				ServiceEntityNode subSENode = subListValueBack
						.get(i);
				ServiceEntityNode subSENodeBack = ServiceCollectionsHelper
						.filterSENodeOnline(subSENode.getUuid(),
								subListValueBack);
				List<ServiceEntityNode> tmpResult = logServiceEntityNode(
						subSENode, subSENodeBack, messageType, setLogIdNameCallBack, logonUserUUID,
						organizationUUID);
				if (!ServiceCollectionsHelper.checkNullList(tmpResult)) {
					mergeToLogModelList(result, tmpResult);
				}
			}
		}
		return result;
	}

	/**
	 * [Internal method] generate log result for sub service module list
	 * 
	 * @param listField
	 * @param serviceModule
	 * @param serviceModuleBack
	 * @param messageType
	 * @param serviceUIModelExtension
	 * @param serviceUIModelExtensionUnion
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @return
	 * @throws ServiceModuleProxyException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ServiceEntityConfigureException
	 */
	@SuppressWarnings("unchecked")
	private List<ServiceEntityNode> logSubServiceModelListWrapper(
			Field listField, ServiceModule serviceModule,
			ServiceModule serviceModuleBack, int messageType,
			ServiceUIModelExtension serviceUIModelExtension,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
			Function<ServiceEntityNode, String[]> setLogIdNameCallBack,
			String logonUserUUID, String organizationUUID)
			throws ServiceModuleProxyException, IllegalArgumentException,
			IllegalAccessException, ServiceEntityConfigureException {
		/**
		 * [Step1] Get the list value from list field of the service module
		 */
		// Check the parent node should not be empty
		if (serviceModule == null || serviceModuleBack == null) {
			return null;
		}
		listField.setAccessible(true);
		List<ServiceModule> subListValue = (List<ServiceModule>) listField
				.get(serviceModule);
		List<ServiceModule> subListValueBack = null;
		if (serviceModuleBack != null) {
			subListValueBack = (List<ServiceModule>) listField
					.get(serviceModuleBack);
		}
		List<ServiceEntityNode> result = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(subListValue)
				&& ServiceCollectionsHelper.checkNullList(subListValueBack)) {
			return null;
		}
		if (!ServiceCollectionsHelper.checkNullList(subListValue)) {
			// In case of creation or update
			for (int i = 0; i < subListValue.size(); i++) {
				ServiceModule subServiceModule = subListValue
						.get(i);
				ServiceEntityNode seNode = ServiceModuleProxy
						.getCoreEntityNodeValue(subServiceModule,
								serviceUIModelExtensionUnion);
				if (seNode == null) {
					continue;
				}
				ServiceModule subServiceModuleBack = filterSENodeFromServiceModuleList(
						seNode.getUuid(), subListValueBack,
						serviceUIModelExtensionUnion);
				List<ServiceEntityNode> tmpResult = logActionForServiceModule(
						subServiceModule, subServiceModuleBack, messageType,
						serviceUIModelExtension, setLogIdNameCallBack,  logonUserUUID,
						organizationUUID, serviceUIModelExtensionUnion);
				if (!ServiceCollectionsHelper.checkNullList(tmpResult)) {
					mergeToLogModelList(result, tmpResult);
				}
			}
		}

		if (!ServiceCollectionsHelper.checkNullList(subListValueBack)) {
			// record the deleted log
			for (int i = 0; i < subListValueBack.size(); i++) {
				ServiceModule subServiceModuleBack = subListValueBack
						.get(i);
				ServiceEntityNode seNodeBack = ServiceModuleProxy
						.getCoreEntityNodeValue(subServiceModuleBack,
								serviceUIModelExtensionUnion);
				if (seNodeBack == null) {
					continue;
				}
				ServiceModule subServiceModule = filterSENodeFromServiceModuleList(
						seNodeBack.getUuid(), subListValue,
						serviceUIModelExtensionUnion);
				if (subServiceModule == null) {
					// Confirmation if it is deleted
					List<ServiceEntityNode> tmpResult = logActionForServiceModule(
							null, subServiceModuleBack, messageType,
							serviceUIModelExtension, setLogIdNameCallBack, logonUserUUID,
							organizationUUID, serviceUIModelExtensionUnion);
					if (!ServiceCollectionsHelper.checkNullList(tmpResult)) {
						mergeToLogModelList(result, tmpResult);
					}
				}
			}
		}
		return result;

	}

}
