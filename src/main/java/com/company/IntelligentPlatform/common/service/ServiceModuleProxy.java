package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.ServiceEntityConfigureProxyService;
import com.company.IntelligentPlatform.common.service.ServiceEntityLogModelManager;
import com.company.IntelligentPlatform.common.service.ServiceModelExtensionHelper;
import com.company.IntelligentPlatform.common.service.ServiceModelExtensionHelper.ExtensionUnionResponse;
import com.company.IntelligentPlatform.common.service.ServiceExtensionManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class ServiceModuleProxy {

	@Autowired
	protected ServiceEntityLogModelManager serviceEntityLogModelManager;

	@Autowired
	protected ServiceExtensionManager serviceExtensionManager;

	protected static Logger logger = LoggerFactory.getLogger(ServiceModuleProxy.class);

	/**
	 * Core Logic to update service module
	 * 
	 * @param serviceModuleType
	 * @param serviceEntityManager
	 * @param serviceModule
	 * @param deleteSubListItemFlag
	 *            flag indicate whether to delete the sub list item which is
	 *            missing from UI
	 * @throws ServiceModuleProxyException
	 * @throws ServiceEntityConfigureException
	 */
	public void updateServiceModule(Class<?> serviceModuleType,
			ServiceEntityManager serviceEntityManager,
			ServiceModule serviceModule, String logonUserUUID,
			String organizationUUID, boolean deleteSubListItemFlag)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		Field coreModuleField = getCoreModuleField(serviceModuleType);
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModuleType.getSimpleName());
		}
		coreModuleField.setAccessible(true);
		IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
				.getAnnotation(IServiceModuleFieldConfig.class);

		if (serviceModuleFieldConfig == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOANNOTATION,
					coreModuleField.getName());
		}
		if (serviceModuleFieldConfig.blockUpdate()) {
			return;
		}
		try {
			ServiceEntityNode serviceEntityNode = (ServiceEntityNode) coreModuleField
					.get(serviceModule);
			if (serviceEntityNode == null) {
				// no need to update this service module when core module with
				// empty value
				return;
			}
			ServiceEntityNode backEntityNode = serviceEntityManager
					.getEntityNodeByKey(serviceEntityNode.getUuid(),
							IServiceEntityNodeFieldConstant.UUID,
							serviceModuleFieldConfig.nodeName(),
							serviceEntityNode.getClient(), null);
			if (backEntityNode != null) {
				// In case entity already exist in back-end, then update the
				// entity
				// Mandatory fields information update from back node entity.
				checkAssignManFieldInfo(serviceEntityNode, backEntityNode);
				serviceEntityManager.updateSENode(serviceEntityNode,
						backEntityNode, logonUserUUID, organizationUUID);
			} else {
				// In case entity NOT exist in back-end, then insert the entity
				serviceEntityManager.insertSENode(serviceEntityNode,
						logonUserUUID, organizationUUID);
			}

			/*
			 * [Step4] find the sub list attributes
			 */
			List<Field> listTypeFields = getListTypeFields(serviceModuleType);
			if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
				for (Field listField : listTypeFields) {
					IServiceModuleFieldConfig subListFieldConfigure = listField
							.getAnnotation(IServiceModuleFieldConfig.class);
					if (subListFieldConfigure == null) {
						// raise exception when no core module field
						// found
						throw new ServiceModuleProxyException(
								ServiceModuleProxyException.PARA_NOANNOTATION,
								listField.getName());
					}
					// In case update this sub node is blocked.
					if (subListFieldConfigure.blockUpdate()) {
						continue;
					}
					List<ServiceEntityNode> backSubNodeList = serviceEntityManager
							.getEntityNodeListByKey(
									serviceEntityNode.getUuid(),
									IServiceEntityNodeFieldConstant.PARENTNODEUUID,
									subListFieldConfigure.nodeName(),
									serviceEntityNode.getClient(), null);
					if (ServiceEntityFieldsHelper
							.checkSuperClassExtends(ServiceEntityFieldsHelper
									.getListSubType(listField),
									ServiceEntityNode.class.getSimpleName())) {
						listField.setAccessible(true);
						// In case the sub list type is service entity node,
						// then update into persistence directly
						@SuppressWarnings("unchecked")
						List<ServiceEntityNode> seNodeList = (List<ServiceEntityNode>) listField
								.get(serviceModule);
						/**
						 * [Step4.5] Process for deleteSubListItem function
						 */
						if (deleteSubListItemFlag) {
							this.deleteListItemByCompare(serviceEntityManager,
									seNodeList, backSubNodeList, logonUserUUID,
									organizationUUID);
						}
						if (!ServiceCollectionsHelper.checkNullList(seNodeList)) {
							for (ServiceEntityNode tmpSubNode : seNodeList) {
								ServiceEntityNode backSubNode = serviceEntityManager
										.getEntityNodeByKey(
												tmpSubNode.getUuid(),
												IServiceEntityNodeFieldConstant.UUID,
												subListFieldConfigure
														.nodeName(), tmpSubNode
														.getClient(), null);
								if (backSubNode != null) {
									// In case entity already exist in back-end,
									// then update the
									// entity
									checkAssignManFieldInfo(tmpSubNode,
											backSubNode);
									serviceEntityManager.updateSENode(
											tmpSubNode, backSubNode,
											logonUserUUID, organizationUUID);
								} else {
									// In case entity NOT exist in back-end,
									// then insert the entity
									serviceEntityManager.insertSENode(
											tmpSubNode, logonUserUUID,
											organizationUUID);
								}
							}
						}
					}

					if (ServiceEntityFieldsHelper
							.checkSuperClassExtends(ServiceEntityFieldsHelper
									.getListSubType(listField),
									ServiceModule.class.getSimpleName())) {
						// In case the sub list type is serviceModule, then
						// update into persistence directly
						listField.setAccessible(true);
						List<?> serviceModuleList = (List<?>) listField
								.get(serviceModule);

						/*
						 * [Step4.5] Process for deleteSubListItem function
						 */
						if (deleteSubListItemFlag) {
							List<ServiceEntityNode> seNodeList = convertServiceModelListToBasicSENodeList(
									serviceModuleList,
									ServiceEntityFieldsHelper
											.getListSubType(listField));
							this.deleteListItemByCompare(serviceEntityManager,
									seNodeList, backSubNodeList, logonUserUUID,
									organizationUUID);
						}

						if (!ServiceCollectionsHelper
								.checkNullList(serviceModuleList)) {
							for (int i = 0; i < serviceModuleList.size(); i++) {
								ServiceModule tmpSubServiceModule = (ServiceModule) serviceModuleList
										.get(i);
								this.updateServiceModule(
										tmpSubServiceModule.getClass(),
										serviceEntityManager,
										tmpSubServiceModule, logonUserUUID,
										organizationUUID, deleteSubListItemFlag);
							}
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * Utiltiy method to get Core Service Entity field value from ServiceModule
	 * @param serviceModule
	 * @return
	 */
	public static ServiceEntityNode getCoreServiceEntityNode(ServiceModule serviceModule, String nodeInstId) throws ServiceModuleProxyException, IllegalAccessException {
		Class<?> serviceModuleType = serviceModule.getClass();
		Field coreModuleField = getModuleFieldByNodeInstId(
				serviceModuleType, nodeInstId);
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModuleType.getSimpleName());
		}
		coreModuleField.setAccessible(true);
		return (ServiceEntityNode) coreModuleField
				.get(serviceModule);
	}

	/**
	 * New Service Entity Node instance in dynamic way
	 * @param nodeName
	 * @param serviceEntityManager
	 * @param parentNode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ServiceEntityNode newServiceEntityNode(String nodeName, ServiceEntityManager serviceEntityManager,
												  ServiceEntityNode parentNode, String client)
			throws ServiceEntityConfigureException, InstantiationException, IllegalAccessException {
		ServiceEntityNode seNodeInstance;
		if(nodeName.equals(ServiceEntityNode.NODENAME_ROOT)){
			seNodeInstance = serviceEntityManager.newRootEntityNode(client);
		} else {
			seNodeInstance = serviceEntityManager.newEntityNode(parentNode, nodeName);
		}
		return seNodeInstance;
	}


	public Class<?> getNodeTypeByConfigureProxy(
			ServiceEntityManager serviceEntityManager, String nodeName)
			throws ServiceEntityConfigureException {
		ServiceEntityConfigureProxy serviceEntityConfigureProxy = serviceEntityManager
				.getSeConfigureProxy();
		ServiceEntityConfigureMap serviceEntityConfigureMap = serviceEntityConfigureProxy
				.getConfigureByNodeName(nodeName);
		return serviceEntityConfigureMap.getNodeType();
	}

	public static <T extends ServiceModule> ServiceEntityNode filterServiceEntityNodeOnline(List<T> serviceModuleList,
																 String nodeInstId,  String uuid){
		if(ServiceCollectionsHelper.checkNullList(serviceModuleList)){
			return null;
		}
		for(ServiceModule serviceModule: serviceModuleList){
			try {
				ServiceEntityNode coreSENode = getCoreServiceEntityNode(serviceModule, nodeInstId);
				if(coreSENode != null){
					if(uuid.equals(coreSENode.getUuid())){
						return coreSENode;
					}
				}
			} catch (ServiceModuleProxyException | IllegalAccessException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			}
		}
		return null;
	}


	/**
	 * Core Logic to update service module
	 * 
	 * @param serviceModuleType
	 * @param serviceEntityManager
	 * @param serviceModule
	 * @param deleteSubListItemFlag
	 *            flag indicate whether to delete the sub list item which is
	 *            missing from UI
	 * @throws ServiceModuleProxyException
	 * @throws ServiceEntityConfigureException
	 */
	public void updateServiceModule(Class<?> serviceModuleType,
			ServiceEntityManager serviceEntityManager,
			ServiceModule serviceModule, String logonUserUUID,
			String organizationUUID, boolean deleteSubListItemFlag,
			String nodeInstId, ServiceUIModelExtension serviceUIModelExtension)
			throws ServiceModuleProxyException, ServiceEntityConfigureException {
		try {
			Field coreModuleField = getModuleFieldByNodeInstId(
					serviceModuleType, nodeInstId);
			if (coreModuleField == null) {
				// raise exception when no core module field found
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.PARA_NOCOREMODULE,
						serviceModuleType.getSimpleName());
			}
			coreModuleField.setAccessible(true);
			IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
					.getAnnotation(IServiceModuleFieldConfig.class);
			ExtensionUnionResponse extensionUnionResponse =  ServiceModelExtensionHelper
					.getUIModelExtensionByNodeInstId(
							serviceModuleFieldConfig.nodeInstId(),
							serviceUIModelExtension);
			if (extensionUnionResponse == null) {
				return;
			}
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = extensionUnionResponse.getServiceUIModelExtensionUnion();			
			if (serviceUIModelExtensionUnion == null) {
				// raise exception when no core module field found
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.PARA_NOCOREMODULE,
						serviceModuleType.getSimpleName());
			}
			ServiceEntityNode serviceEntityNode = (ServiceEntityNode) coreModuleField
					.get(serviceModule);
			if (serviceEntityNode == null) {
				// no need to update this service module when core module with
				// empty value
				return;
			}
			/*
			 * [Step2] Update the start SE node firstly
			 */
			updateStartSENodeCore(serviceModule, serviceEntityNode,
					serviceEntityManager, serviceUIModelExtensionUnion,
					logonUserUUID, organizationUUID);
			/*
			 * [Step3] find the sub flat attributes
			 */
			List<Field> subFlatFields = getNonListSubFields(serviceModuleType,
					nodeInstId);
			if (!ServiceCollectionsHelper.checkNullList(subFlatFields)) {
				for (Field subFlatField : subFlatFields) {
					updateToSubFieldWrapper(subFlatField, serviceUIModelExtension,
							serviceEntityManager, serviceModule,
							serviceEntityNode, deleteSubListItemFlag,
							logonUserUUID, organizationUUID, false);
				}
			}
			/*
			 * [Step4] find the sub list attributes
			 */
			List<Field> listTypeFields = getListTypeFields(serviceModuleType);
			if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
				for (Field listField : listTypeFields) {
					updateToSubFieldWrapper(listField, serviceUIModelExtension,
							serviceEntityManager, serviceModule,
							serviceEntityNode, deleteSubListItemFlag,
							logonUserUUID, organizationUUID, true);
				}
			}
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * [Internal method] Logic to update sub field value
	 * 
	 * @param subField
	 * @param serviceUIModelExtension
	 * @param serviceEntityManager
	 * @param serviceModule
	 * @param parentEntityNode
	 * @param deleteSubListItemFlag
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @param listFlag
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ServiceModuleProxyException
	 * @throws ServiceEntityConfigureException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateToSubFieldWrapper(Field subField,
			ServiceUIModelExtension serviceUIModelExtension,
			ServiceEntityManager serviceEntityManager,
			ServiceModule serviceModule, ServiceEntityNode parentEntityNode,
			boolean deleteSubListItemFlag, String logonUserUUID,
			String organizationUUID, boolean listFlag)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceModuleProxyException, NoSuchFieldException,
			SecurityException, ServiceEntityConfigureException {
		/*
		 * [Step1] Get field annotation
		 */
		IServiceModuleFieldConfig subFieldConfigure = subField
				.getAnnotation(IServiceModuleFieldConfig.class);
		if (subFieldConfigure == null) {
			// raise exception when no core module field
			// found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOANNOTATION,
					subField.getName());
		}
		// In case update is blocked for this field
		if (subFieldConfigure.blockUpdate()) {
			return;
		}
		ExtensionUnionResponse subExtensionUnionResponse =  ServiceModelExtensionHelper
				.getUIModelExtensionByNodeInstId(
						subFieldConfigure.nodeInstId(),
						serviceUIModelExtension);	
		if(subExtensionUnionResponse == null){
			return;
		}
		ServiceUIModelExtensionUnion subExtensionUnion = subExtensionUnionResponse.getServiceUIModelExtensionUnion();
		if (subExtensionUnion == null) {
			return;
		}
		/*
		 * [Step2] try to get back-end sub node List from logic in extension
		 * union
		 */
		List<ServiceEntityNode> backSubNodeList = this.loadSubNodeListWrapper(
				parentEntityNode, serviceEntityManager,
				subFieldConfigure.nodeName(), subExtensionUnion);

		if (listFlag) {
			/*
			 * [Step3.1] In case sub node type is ServiceEntity
			 */
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					ServiceEntityFieldsHelper.getListSubType(subField),
					ServiceEntityNode.class.getSimpleName())) {
				updateSubServiceEntityNodeListValueWrapper(subField,
						serviceModule, backSubNodeList, serviceEntityManager,
						subFieldConfigure, deleteSubListItemFlag,
						logonUserUUID, organizationUUID, subExtensionUnion);
			}
			/*
			 * [Step3.2] In case sub node type is Service Module
			 */
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					ServiceEntityFieldsHelper.getListSubType(subField),
					ServiceModule.class.getSimpleName())) {
				// In case the sub list type is serviceModule, then
				// update into persistence directly
				subField.setAccessible(true);
				List<?> serviceModuleList = (List<?>) subField
						.get(serviceModule);
				/*
				 * [Step3.3] Process for deleteSubListItem function
				 */
				if (deleteSubListItemFlag) {
					List<ServiceEntityNode> seNodeList = convertServiceModelListToBasicSENodeList(
							serviceModuleList,
							ServiceEntityFieldsHelper.getListSubType(subField));
					this.deleteListItemByCompare(serviceEntityManager,
							seNodeList, backSubNodeList, logonUserUUID,
							organizationUUID);
				}

				if (!ServiceCollectionsHelper.checkNullList(serviceModuleList)) {
					for (Object object : serviceModuleList) {
						ServiceModule tmpSubServiceModule = (ServiceModule) object;
						this.updateServiceModule(tmpSubServiceModule.getClass(), serviceEntityManager,
								tmpSubServiceModule, logonUserUUID, organizationUUID, deleteSubListItemFlag);
					}
				}
			}
		} else {
			/*
			 * [Step3.1] Flat type In case sub node type is ServiceEntity
			 */
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					subField.getType(),
					ServiceEntityNode.class.getSimpleName())) {
				updateSubServiceEntityNodeValueWrapper(subField, serviceModule,
						backSubNodeList, serviceEntityManager,
						subFieldConfigure, deleteSubListItemFlag,
						logonUserUUID, organizationUUID, subExtensionUnion);
			}
			/*
			 * [Step3.2] In case sub node type is Service Module
			 */
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					subField.getType(), ServiceModule.class.getSimpleName())) {
				// In case the sub list type is serviceModule, then
				// update into persistence directly
				subField.setAccessible(true);
				ServiceModule subServiceModule = (ServiceModule) subField
						.get(serviceModule);
				/*
				 * [Step3.3] Process for deleteSubListItem function
				 */
				if (deleteSubListItemFlag) {
					List serviceModuleList = new ArrayList();
					serviceModuleList.add(subServiceModule);
					List<ServiceEntityNode> seNodeList = convertServiceModelListToBasicSENodeList(
							serviceModuleList,
							ServiceEntityFieldsHelper.getListSubType(subField));
					this.deleteListItemByCompare(serviceEntityManager,
							seNodeList, backSubNodeList, logonUserUUID,
							organizationUUID);
				}
				this.updateServiceModule(subServiceModule.getClass(),
						serviceEntityManager, subServiceModule, logonUserUUID,
						organizationUUID, deleteSubListItemFlag);
			}
		}

	}

	private void updateStartSENodeCore(ServiceModule serviceModule,
			ServiceEntityNode serviceEntityNode,
			ServiceEntityManager serviceEntityManager,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion,
			String logonUserUUID, String organizationUUID)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceEntityConfigureException, NoSuchFieldException,
			SecurityException, ServiceModuleProxyException {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = serviceUIModelExtensionUnion
				.getUiModelNodeMapList();
		if (!ServiceCollectionsHelper.checkNullList(uiModelNodeMapList)) {
			UIModelNodeMapConfigure startMapConfigure = ServiceModelExtensionHelper
					.getFirstNodeMapConfigure(uiModelNodeMapList);
			if (startMapConfigure == null) {
				// TODO should raise exception
				logger.error("startMapConfigure is empty");
			}

			// Process the host map configure
			if (!ServiceEntityStringHelper.checkNullString(startMapConfigure
					.getConvUIToMethod())) {
				if (serviceEntityNode == null) {
					// no need to update when core module empty value
					return;
				}
				updateSENodeCore(serviceEntityNode,
						serviceEntityNode.getNodeName(), serviceEntityManager,
						logonUserUUID, organizationUUID);
				if(!ServiceEntityNode.NODENAME_ROOT.equals(serviceEntityNode.getNodeName())){
					// In case current node is not root node. should notify parent node
					updateAdminParentNodeRecursive(serviceEntityNode, serviceEntityManager, logonUserUUID, organizationUUID);
				}
			}
			List<UIModelNodeMapConfigure> childNodeMapList = ServiceModelExtensionHelper
					.filterChildNodeMapList(uiModelNodeMapList,
							startMapConfigure);
			if (!ServiceCollectionsHelper.checkNullList(childNodeMapList)) {
				for (UIModelNodeMapConfigure uiModelNodeMapConfigure : childNodeMapList) {
					updateSubSENodeRecursive(serviceModule,
							uiModelNodeMapConfigure, serviceEntityManager,
							uiModelNodeMapList, logonUserUUID,
							organizationUUID, serviceEntityNode.getClient());
				}
			}
		}
	}

	private void updateAdminParentNodeRecursive(ServiceEntityNode serviceEntityNode, ServiceEntityManager serviceEntityManager, String logonUserUUID,
												String organizationUUID) throws ServiceEntityConfigureException {
		ServiceEntityNode parentEntityNode = serviceEntityManager.getParentEntityNode(serviceEntityNode);
		if(parentEntityNode == null){
			return;
		}
		parentEntityNode.setLastUpdateBy(logonUserUUID);
		parentEntityNode.setLastUpdateTime(LocalDateTime.now());
		if(ServiceEntityStringHelper.checkNullString(parentEntityNode.getResOrgUUID())){
			parentEntityNode.setResOrgUUID(organizationUUID);
		}
		this.updateAdminParentNodeRecursive(parentEntityNode, serviceEntityManager, logonUserUUID, organizationUUID);
	}


	/**
	 * Core Logic to update/insert SE instance into DB, as well as register
	 * update log
	 * 
	 * @throws ServiceEntityConfigureException
	 */
	private void updateSENodeCore(ServiceEntityNode serviceEntityNode,
			String nodeName, ServiceEntityManager serviceEntityManager,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		boolean createFlag = false;
		ServiceEntityNode backEntityNode = null;
		if (serviceEntityNode == null) {
			createFlag = true;
		} else {
			backEntityNode = serviceEntityManager.getEntityNodeByKey(
					serviceEntityNode.getUuid(),
					IServiceEntityNodeFieldConstant.UUID, nodeName,
					serviceEntityNode.getClient(), null);
			if (backEntityNode == null) {
				createFlag = true;
			}
		}
		if (!createFlag) {
			// In case entity already exist in back-end, then update the
			// entity
			// Mandatory fields information update from back node entity.
			checkAssignManFieldInfo(serviceEntityNode, backEntityNode);
			serviceEntityNode.setLastUpdateBy(logonUserUUID);
			serviceEntityNode.setLastUpdateTime(LocalDateTime.now());
			serviceEntityNode.setResOrgUUID(organizationUUID);
			serviceEntityManager.updateSENode(serviceEntityNode,
					backEntityNode, logonUserUUID, organizationUUID);
		} else {
			// In case entity NOT exist in back-end, then insert the entity
			serviceEntityNode.setCreatedBy(logonUserUUID);
			serviceEntityNode.setCreatedTime(LocalDateTime.now());
			serviceEntityNode.setLastUpdateBy(logonUserUUID);
			serviceEntityNode.setLastUpdateTime(LocalDateTime.now());
			serviceEntityNode.setResOrgUUID(organizationUUID);
			serviceEntityManager.insertSENode(serviceEntityNode, logonUserUUID,
					organizationUUID);
		}
	}

	/**
	 * Read SE node value from Service Model and insert into db, as well as
	 * insert sub nodes belongs to "same level".
	 * 
	 * @Pre-requisite: SE node value should be recorded in
	 *                 <code>ServiceEntityNode</code> instance in Service Model
	 * 
	 * @param serviceModule
	 * @param curNodeConfigure
	 * @param serviceEntityManager
	 * @param uiModelNodeMapList
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @param client
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ServiceEntityConfigureException
	 */
	private void updateSubSENodeRecursive(ServiceModule serviceModule,
			UIModelNodeMapConfigure curNodeConfigure,
			ServiceEntityManager serviceEntityManager,
			List<UIModelNodeMapConfigure> uiModelNodeMapList,
			String logonUserUUID, String organizationUUID, String client)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			ServiceEntityConfigureException {
		/*
		 * [Step1] Update Or insert current
		 */
		if (curNodeConfigure == null
				|| ServiceEntityStringHelper.checkNullString(curNodeConfigure
						.getConvUIToMethod())) {
			return;
		}
		ServiceEntityNode seNodeValue = null;
		Field relativeField = getModuleFieldByNodeInstId(
				serviceModule.getClass(), curNodeConfigure.getNodeInstID());
		if (relativeField == null) {
			return;
		}
		relativeField.setAccessible(true);
		seNodeValue = (ServiceEntityNode) relativeField.get(serviceModule);
		if (seNodeValue == null) {
			return;
		}
		updateSENodeCore(seNodeValue, curNodeConfigure.getNodeName(),
				serviceEntityManager, logonUserUUID, organizationUUID);
		/*
		 * [Step2] Retrieve all the sub node from current node configure and
		 * call this method recursive
		 */
		List<UIModelNodeMapConfigure> childNodeMapList = ServiceModelExtensionHelper
				.filterChildNodeMapList(uiModelNodeMapList, curNodeConfigure);
		if (!ServiceCollectionsHelper.checkNullList(childNodeMapList)) {
			for (UIModelNodeMapConfigure uiModelNodeMapConfigure : childNodeMapList) {
				updateSubSENodeRecursive(serviceModule,
						uiModelNodeMapConfigure, serviceEntityManager,
						uiModelNodeMapList, logonUserUUID, organizationUUID,
						client);
			}
		}
	}

	/**
	 * Utility method to create new SE instance
	 * 
	 * @param curNodeMapConfigure
	 * @param rawSENode
	 * @param serviceEntityManager
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public static ServiceEntityNode reflectCreateNewNode(
			UIModelNodeMapConfigure curNodeMapConfigure,
			ServiceEntityNode rawSENode, String client,
			ServiceEntityManager serviceEntityManager)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		if (rawSENode != null
				&& curNodeMapConfigure.getToBaseNodeType() == UIModelNodeMapConfigure.TOBASENODE_TO_PARENT) {
			ServiceEntityNode seNode = serviceEntityManager.newEntityNode(
					rawSENode, curNodeMapConfigure.getNodeName());
			return seNode;
		}
		if (ServiceEntityNode.NODENAME_ROOT.equals(curNodeMapConfigure
				.getNodeName())) {
			ServiceEntityNode seNode = serviceEntityManager
					.newRootEntityNode(client);
			return seNode;
		}
		throw new ServiceModuleProxyException(
				ServiceModuleProxyException.PARA_NONSUPPORT_CREATE_NODE,
				curNodeMapConfigure.getNodeInstID());

	}

	/**
	 * Utility Method: Get (NOT root) SE Node value from back-end by current
	 * node configure, and parent SE Node value
	 * 
	 * @param curNodeConfigure
	 * @param serviceEntityManager
	 * @param rawSENodeValue
	 * @param client
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ServiceEntityConfigureException
	 */
	public static ServiceEntityNode getSENodeFromDBWrapper(
			UIModelNodeMapConfigure curNodeConfigure,
			ServiceEntityManager serviceEntityManager,
			ServiceEntityNode rawSENodeValue, String client)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			ServiceEntityConfigureException {
		/**
		 * [Step1] retrieve the SE node value to current node configure from raw
		 * SE node value
		 */
		ServiceEntityNode seNodeValue = null;
		if (curNodeConfigure.getGetSENodeCallback() != null) {
			// Retrieve seNodeValue for current node by call back hook
			seNodeValue = curNodeConfigure.getGetSENodeCallback().execute(rawSENodeValue);
		} else {
			List<ServiceBasicKeyStructure> keyList = ServiceModelExtensionHelper
					.generateKeyStructFromNodeMapConfigure(curNodeConfigure,
							rawSENodeValue, client);
			if (keyList == null) {
				return null;
			}
			ServiceEntityManager executeManager = serviceEntityManager;
			if (curNodeConfigure.getServiceEntityManager() != null) {
				executeManager = curNodeConfigure.getServiceEntityManager();
			}
			seNodeValue = executeManager.getEntityNodeByKeyList(keyList,
					curNodeConfigure.getNodeName(), null);
		}
		return seNodeValue;
	}

	private void updateSubServiceEntityNodeValueWrapper(Field subField,
			ServiceModule serviceModule,
			List<ServiceEntityNode> backSubNodeList,
			ServiceEntityManager serviceEntityManager,
			IServiceModuleFieldConfig subListFieldConfigure,
			boolean deleteSubListItemFlag, String logonUserUUID,
			String organizationUUID,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceEntityConfigureException {
		subField.setAccessible(true);
		// In case the sub list type is service entity node,
		// then update into persistence directly
		ServiceEntityNode seNode = (ServiceEntityNode) subField
				.get(serviceModule);
		if(seNode == null){
			return;
		}
		List<ServiceEntityNode> seNodeList = new ArrayList<>();
		seNodeList.add(seNode);
		if (deleteSubListItemFlag) {
			this.deleteListItemByCompare(serviceEntityManager, seNodeList,
					backSubNodeList, logonUserUUID, organizationUUID);
		}
		this.updateSENodeCore(seNode, subListFieldConfigure.nodeName(),
				serviceEntityManager, logonUserUUID, organizationUUID);
	}

	@SuppressWarnings("unchecked")
	private void updateSubServiceEntityNodeListValueWrapper(Field listField,
			ServiceModule serviceModule,
			List<ServiceEntityNode> backSubNodeList,
			ServiceEntityManager serviceEntityManager,
			IServiceModuleFieldConfig subListFieldConfigure,
			boolean deleteSubListItemFlag, String logonUserUUID,
			String organizationUUID,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws IllegalArgumentException, IllegalAccessException,
			ServiceEntityConfigureException {

		listField.setAccessible(true);
		// In case the sub list type is service entity node,
		// then update into persistence directly
		List<ServiceEntityNode> seNodeList = (List<ServiceEntityNode>) listField
				.get(serviceModule);
		if (deleteSubListItemFlag) {
			this.deleteListItemByCompare(serviceEntityManager, seNodeList,
					backSubNodeList, logonUserUUID, organizationUUID);
		}
		if (!ServiceCollectionsHelper.checkNullList(seNodeList)) {
			for (ServiceEntityNode tmpSubNode : seNodeList) {
				this.updateSENodeCore(tmpSubNode,
						subListFieldConfigure.nodeName(), serviceEntityManager,
						logonUserUUID, organizationUUID);

			}
		}
	}

	/**
	 * Copy some mandatory fields from back-end model if there is null value in
	 * new service entity node model.
	 * 
	 * @param serviceEntityNode
	 * @param backEntityNode
	 */
	public void checkAssignManFieldInfo(ServiceEntityNode serviceEntityNode,
			ServiceEntityNode backEntityNode) {
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getUuid())) {
			serviceEntityNode.setUuid(backEntityNode.getUuid());
		}
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getClient())) {
			serviceEntityNode.setClient(backEntityNode.getClient());
		}
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getParentNodeUUID())) {
			serviceEntityNode.setParentNodeUUID(backEntityNode
					.getParentNodeUUID());
		}
		if (ServiceEntityStringHelper.checkNullString(serviceEntityNode
				.getRootNodeUUID())) {
			serviceEntityNode.setRootNodeUUID(backEntityNode.getRootNodeUUID());
		}
	}

	/**
	 * [Internal method] Converting the service model list dynamically to basic
	 * se node list
	 * 
	 * @param serviceModuleList
	 * @param serviceModuleType
	 * @return
	 * @throws ServiceModuleProxyException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected List<ServiceEntityNode> convertServiceModelListToBasicSENodeList(
			List<?> serviceModuleList, Class<?> serviceModuleType)
			throws ServiceModuleProxyException, IllegalArgumentException,
			IllegalAccessException {
		if (ServiceCollectionsHelper.checkNullList(serviceModuleList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (int i = 0; i < serviceModuleList.size(); i++) {
			ServiceModule tmpSubServiceModule = (ServiceModule) serviceModuleList
					.get(i);
			ServiceEntityNode serviceEntityNode = getCoreEntityNodeValue(tmpSubServiceModule);
			resultList.add(serviceEntityNode);
		}
		return resultList;
	}

	/**
	 * [Internal method] delete the item model form persistence by comparing the
	 * new list and old list from persistence
	 * 
	 * @param serviceEntityList
	 * @param backSEList
	 * @throws ServiceEntityConfigureException
	 */
	private void deleteListItemByCompare(
			ServiceEntityManager serviceEntityManager,
			List<ServiceEntityNode> serviceEntityList,
			List<ServiceEntityNode> backSEList, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(backSEList)) {
			return;
		}
		List<ServiceEntityNode> deleteNodeList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(serviceEntityList)) {
			deleteNodeList = backSEList;
		} else {
			for (ServiceEntityNode backEntityNode : backSEList) {
				boolean hitFlag = false;
				for (ServiceEntityNode tmpSENode : serviceEntityList) {
					if (tmpSENode.getUuid().equals(backEntityNode.getUuid())) {
						hitFlag = true;
						break;
					}
				}
				if (!hitFlag) {
					deleteNodeList.add(backEntityNode);
				}
			}
		}
		List<ServiceEntityConfigureMap> serviceEntityConfigureMapList = serviceEntityManager
				.getSeConfigureProxy().getSeConfigMapList();
		// Execution the deleting function
		deleteSENodeWithSubNode(serviceEntityManager, deleteNodeList,
				serviceEntityConfigureMapList, logonUserUUID, organizationUUID);
	}

	private void deleteSENodeWithSubNode(
			ServiceEntityManager serviceEntityManager,
			List<ServiceEntityNode> deleteNodeList,
			List<ServiceEntityConfigureMap> serviceEntityConfigureMapList,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		// Execution the deleting function
		if (!ServiceCollectionsHelper.checkNullList(deleteNodeList)) {
			for (ServiceEntityNode deleteNode : deleteNodeList) {
				// Should delete the sub node list firstly
				List<ServiceEntityConfigureMap> subConfigureMapList = ServiceEntityConfigureProxyService
						.getDirectChildConfigureMapList(
								serviceEntityConfigureMapList,
								deleteNode.getNodeName());
				if (!ServiceCollectionsHelper
						.checkNullList(subConfigureMapList)) {
					for (ServiceEntityConfigureMap serviceEntityConfigureMap : subConfigureMapList) {
						List<ServiceEntityNode> subEntityNodeList = serviceEntityManager
								.getEntityNodeListByKey(
										deleteNode.getUuid(),
										IServiceEntityNodeFieldConstant.PARENTNODEUUID,
										serviceEntityConfigureMap.getNodeName(),
										null);
						if (!ServiceCollectionsHelper
								.checkNullList(subEntityNodeList)) {
							// Call this method in recursive way
							deleteSENodeWithSubNode(serviceEntityManager,
									subEntityNodeList,
									serviceEntityConfigureMapList,
									logonUserUUID, organizationUUID);
						}
					}
				}
			}
			serviceEntityManager.deleteSENode(deleteNodeList, logonUserUUID,
					organizationUUID);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void defaultMergeServiceModule(
			ServiceEntityManager serviceEntityManager,
			ServiceModule originServiceModule,
			ServiceModule serviceModuleToMerge)
			throws ServiceModuleProxyException {
		ServiceEntityNode parentNode = getCoreEntityNodeValue(originServiceModule);
		List<Field> listTypeFields = getListTypeFields(serviceModuleToMerge
				.getClass());
		if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
			for (Field listField : listTypeFields) {
				if (ServiceEntityFieldsHelper.checkSuperClassExtends(
						ServiceEntityFieldsHelper.getListSubType(listField),
						ServiceEntityNode.class.getSimpleName())) {
					try {
						// In case extend from service entity node directly
						listField.setAccessible(true);
						List<ServiceEntityNode> subListToMerge = (List<ServiceEntityNode>) listField
								.get(serviceModuleToMerge);
						List<ServiceEntityNode> subListToOrigin = (List<ServiceEntityNode>) listField
								.get(originServiceModule);
						if (ServiceCollectionsHelper
								.checkNullList(subListToMerge)) {
							continue;
						}
						for (ServiceEntityNode seNode : subListToMerge) {
							serviceEntityManager.changeParent(seNode,
									parentNode);
						}
						subListToOrigin.addAll(subListToMerge);
						listField.set(originServiceModule, subListToOrigin);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						continue;
					}
				}
				if (ServiceEntityFieldsHelper.checkSuperClassExtends(
						ServiceEntityFieldsHelper.getListSubType(listField),
						ServiceModule.class.getSimpleName())) {
					// In case extend from service module
					try {
						listField.setAccessible(true);
						List<?> subListToMerge = (List<?>) listField
								.get(serviceModuleToMerge);
						List subListToOrigin = (List) listField
								.get(originServiceModule);
						if (ServiceCollectionsHelper
								.checkNullList(subListToMerge)) {
							continue;
						}
						for (int i = 0; i < subListToMerge.size(); i++) {
							ServiceModule subServiceModule = (ServiceModule) subListToMerge
									.get(i);
							changeParentServiceModule(serviceEntityManager,
									parentNode, subServiceModule);
							subListToOrigin.add(subServiceModule);
						}
						listField.set(originServiceModule, subListToOrigin);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						continue;
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void changeParentServiceModule(
			ServiceEntityManager serviceEntityManager,
			ServiceEntityNode parentNode, ServiceModule serviceModule)
			throws ServiceModuleProxyException {
		ServiceEntityNode coreNode = getCoreEntityNodeValue(serviceModule);
		serviceEntityManager.changeParent(coreNode, parentNode);
		List<Field> listTypeFields = getListTypeFields(serviceModule.getClass());
		if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
			for (Field listField : listTypeFields) {
				if (ServiceEntityFieldsHelper.checkSuperClassExtends(
						ServiceEntityFieldsHelper.getListSubType(listField),
						ServiceEntityNode.class.getSimpleName())) {
					try {
						// In case extend from service entity node directly
						listField.setAccessible(true);
						List<ServiceEntityNode> subSeNodeList = (List<ServiceEntityNode>) listField
								.get(serviceModule);
						if (ServiceCollectionsHelper
								.checkNullList(subSeNodeList)) {
							continue;
						}
						for (ServiceEntityNode seNode : subSeNodeList) {
							serviceEntityManager.changeParent(seNode, coreNode);
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						continue;
					}
				}
				if (ServiceEntityFieldsHelper.checkSuperClassExtends(
						ServiceEntityFieldsHelper.getListSubType(listField),
						ServiceModule.class.getSimpleName())) {
					// In case extend from service module
					try {
						listField.setAccessible(true);
						List<?> subListToMerge = (List<?>) listField
								.get(serviceModule.getClass());
						if (ServiceCollectionsHelper
								.checkNullList(subListToMerge)) {
							continue;
						}
						for (int i = 0; i < subListToMerge.size(); i++) {
							ServiceModule subServiceModule = (ServiceModule) subListToMerge
									.get(i);
							changeParentServiceModule(serviceEntityManager,
									coreNode, subServiceModule);
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						continue;
					}
				}
			}
		}
	}

	/**
	 * Main Entrance to load and fill service module from persistence
	 * 
	 * @param serviceModuleType
	 * @param serviceEntityManager
	 * @param serviceEntityNode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	@SuppressWarnings("unchecked")
	public ServiceModule loadServiceModule(Class<?> serviceModuleType,
			ServiceEntityManager serviceEntityManager,
			ServiceEntityNode serviceEntityNode)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		Field coreModuleField = getCoreModuleField(serviceModuleType);
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModuleType.getSimpleName());
		}
		IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
				.getAnnotation(IServiceModuleFieldConfig.class);
		if (serviceModuleFieldConfig == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOANNOTATION,
					coreModuleField.getName());
		}

		/*
		 * [Step3] Generate new service module and set the core module property
		 */
		try {
			ServiceModule serviceModuleInstance = (ServiceModule) serviceModuleType
					.newInstance();
			coreModuleField.setAccessible(true);
			coreModuleField.set(serviceModuleInstance, serviceEntityNode);
			/*
			 * [Step4] find the sub list attributes
			 */
			List<Field> listTypeFields = getListTypeFields(serviceModuleType);
			if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
				for (Field listField : listTypeFields) {
					// In case list generic type is service entity node, then
					// set value directly
					IServiceModuleFieldConfig subListFieldConfigure = listField
							.getAnnotation(IServiceModuleFieldConfig.class);
					if (subListFieldConfigure == null) {
						// raise exception when no core module field found
						throw new ServiceModuleProxyException(
								ServiceModuleProxyException.PARA_NOANNOTATION,
								listField.getName());
					}
					List<ServiceEntityNode> subSENodeList = serviceEntityManager
							.getEntityNodeListByKey(
									serviceEntityNode.getUuid(),
									IServiceEntityNodeFieldConstant.PARENTNODEUUID,
									subListFieldConfigure.nodeName(), null);
					if (ServiceEntityFieldsHelper
							.checkSuperClassExtends(ServiceEntityFieldsHelper
									.getListSubType(listField),
									ServiceEntityNode.class.getSimpleName())) {
						if (!ServiceCollectionsHelper
								.checkNullList(subSENodeList)) {
							listField.setAccessible(true);
							listField.set(serviceModuleInstance, subSENodeList);
						}
					}
					if (ServiceEntityFieldsHelper
							.checkSuperClassExtends(ServiceEntityFieldsHelper
									.getListSubType(listField),
									ServiceModule.class.getSimpleName())) {
						// In case list generic type is service module
						if (!ServiceCollectionsHelper
								.checkNullList(subSENodeList)) {
							@SuppressWarnings("rawtypes")
							List subServiceModuleList = new ArrayList();
							for (ServiceEntityNode tmpSENode : subSENodeList) {
								ServiceModule tmpServiceModule = loadServiceModule(
										ServiceEntityFieldsHelper
												.getListSubType(listField),
										serviceEntityManager, tmpSENode);
								subServiceModuleList.add(tmpServiceModule);
							}
							if (!ServiceCollectionsHelper
									.checkNullList(subServiceModuleList)) {
								listField.setAccessible(true);
								listField.set(serviceModuleInstance,
										subServiceModuleList);
							}
						}
					}
				}
			}
			return serviceModuleInstance;
		} catch (InstantiationException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * Main Entrance to load and fill service module from persistence
	 *
	 * @param serviceModuleType
	 * @param serviceEntityManager
	 * @param serviceEntityNode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public ServiceModule loadServiceModule(Class<?> serviceModuleType,
										   ServiceEntityManager serviceEntityManager,
										   ServiceEntityNode serviceEntityNode,
										   ServiceUIModelExtension serviceUIModelExtension, String nodeInstId)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		/*
		 * [Step1] Load Service Module Core part from all the flat nodes
		 */
		try {
			ServiceModule serviceModuleInstance = loadServiceModuleCoreWithFlatNodes(serviceModuleType,
					serviceEntityManager, serviceEntityNode, serviceUIModelExtension, nodeInstId);
			/*
			 * [Step2] find the sub list attributes
			 */
			List<Field> listTypeFields = getListTypeFields(serviceModuleType);
			if (!ServiceCollectionsHelper.checkNullList(listTypeFields)) {
				for (Field listField : listTypeFields) {
					loadFillSubNode(serviceModuleInstance, listField,
							serviceEntityNode, serviceEntityManager,
							serviceUIModelExtension, true);
				}
			}
			return serviceModuleInstance;
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	public ServiceModule loadServiceModuleCore(Class<?> serviceModuleType,
															ServiceEntityNode serviceEntityNode,String nodeInstId)
			throws ServiceModuleProxyException {
		/*
		 * [Step1] Data prepare and check
		 */
		Field coreModuleField = getModuleFieldByNodeInstId(serviceModuleType,
				nodeInstId);
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModuleType.getSimpleName());
		}
		IServiceModuleFieldConfig serviceModuleFieldConfig = coreModuleField
				.getAnnotation(IServiceModuleFieldConfig.class);
		if (serviceModuleFieldConfig == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOANNOTATION,
					coreModuleField.getName());
		}
		/*
		 * [Step2] Generate new service module and set the core module property
		 */
		try {
			ServiceModule serviceModuleInstance = (ServiceModule) serviceModuleType
					.getDeclaredConstructor().newInstance();
			coreModuleField.setAccessible(true);
			coreModuleField.set(serviceModuleInstance, serviceEntityNode);
			return serviceModuleInstance;
		} catch (InstantiationException | IllegalAccessException | SecurityException |
                 IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * Load ServiceModule Core with only flat nodes
	 * 
	 * @param serviceModuleType
	 * @param serviceEntityManager
	 * @param serviceEntityNode
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 */
	public ServiceModule loadServiceModuleCoreWithFlatNodes(Class<?> serviceModuleType,
			ServiceEntityManager serviceEntityManager,
			ServiceEntityNode serviceEntityNode,
			ServiceUIModelExtension serviceUIModelExtension, String nodeInstId)
			throws ServiceEntityConfigureException, ServiceModuleProxyException {
		/*
		 * [Step2] Generate new service module and set the core module property
		 */
		try {
			ServiceModule serviceModuleInstance = loadServiceModuleCore(serviceModuleType, serviceEntityNode, nodeInstId);
			/*
			 * [Step3] find the sub non-list attributes and fill in flat node instances
			 */
			List<Field> subFlatFields = getNonListSubFields(serviceModuleType,
					nodeInstId);
			if (!ServiceCollectionsHelper.checkNullList(subFlatFields)) {
				for (Field subFlatField : subFlatFields) {
					loadFillSubNode(serviceModuleInstance, subFlatField,
							serviceEntityNode, serviceEntityManager,
							serviceUIModelExtension, false);
				}
			}
			return serviceModuleInstance;
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException |
                 IllegalArgumentException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
    }

	/**
	 * [Internal method] Utility Method Reflective load raw values and set value
	 * to sub field from specific Service Model
	 * 
	 * @param serviceModuleInstance
	 * @param subField
	 * @throws ServiceModuleProxyException
	 * @throws ServiceEntityConfigureException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings("unchecked")
	private void loadFillSubNode(ServiceModule serviceModuleInstance,
			Field subField, ServiceEntityNode parentNode,
			ServiceEntityManager serviceEntityManager,
			ServiceUIModelExtension serviceUIModelExtension, boolean listFlag)
			throws ServiceModuleProxyException, NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, ServiceEntityConfigureException {
		IServiceModuleFieldConfig subListFieldConfigure = subField
				.getAnnotation(IServiceModuleFieldConfig.class);
		if (subListFieldConfigure == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOANNOTATION,
					subField.getName());
		}
		ExtensionUnionResponse extensionUnionResponse =  ServiceModelExtensionHelper
				.getUIModelExtensionByNodeInstId(
						subListFieldConfigure.nodeInstId(),
						serviceUIModelExtension);
		if (extensionUnionResponse == null) {
			return;
		}
		ServiceUIModelExtensionUnion subExtensionUnion = extensionUnionResponse.getServiceUIModelExtensionUnion();
		ServiceUIModelExtension subExtension = extensionUnionResponse.getServiceUIModelExtension();
		String subNodeInstId = subListFieldConfigure.nodeInstId();
		if (ServiceEntityStringHelper.checkNullString(subNodeInstId)) {
			return;
		}
		List<ServiceEntityNode> subSENodeList = this.loadSubNodeListWrapper(
				parentNode, serviceEntityManager,
				subListFieldConfigure.nodeName(), subExtensionUnion);
		if (listFlag) {
			// In case current sub field is list type, and member extend basic
			// service entity node
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					ServiceEntityFieldsHelper.getListSubType(subField),
					ServiceEntityNode.class.getSimpleName())) {
				if (!ServiceCollectionsHelper.checkNullList(subSENodeList)) {
					subField.setAccessible(true);
					subField.set(serviceModuleInstance, subSENodeList);
				}
			}
		} else {
			// In case current sub field is flat type and extend basic service
			// entity node
			if (ServiceEntityFieldsHelper
					.checkSuperClassExtends(subField.getType(),
							ServiceEntityNode.class.getSimpleName())) {
				ServiceEntityNode subNodeValue = ServiceCollectionsHelper
						.checkNullList(subSENodeList) ? null : subSENodeList
						.get(0);
				subField.setAccessible(true);
				subField.set(serviceModuleInstance, subNodeValue);
			}
		}

		if (listFlag) {
			// In case current sub field is list type and member extend Service
			// Module
			
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					ServiceEntityFieldsHelper.getListSubType(subField),
					ServiceModule.class.getSimpleName())) {
				// In case list generic type is service module
				if (!ServiceCollectionsHelper.checkNullList(subSENodeList)) {					
					@SuppressWarnings("rawtypes")
					List subServiceModuleList = new ArrayList();
					for (ServiceEntityNode tmpSENode : subSENodeList) {
						ServiceModule tmpServiceModule = loadServiceModule(
								ServiceEntityFieldsHelper
										.getListSubType(subField),
								serviceEntityManager, tmpSENode, subExtension,
								subNodeInstId);
						subServiceModuleList.add(tmpServiceModule);
					}
					if (!ServiceCollectionsHelper
							.checkNullList(subServiceModuleList)) {
						subField.setAccessible(true);
						subField.set(serviceModuleInstance,
								subServiceModuleList);
					}
				}
			}
		} else {
			// In case current sub field is flat type and extend Service Module
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					subField.getType(), ServiceModule.class.getSimpleName())) {				
				ServiceEntityNode tmpSENode = ServiceCollectionsHelper
						.checkNullList(subSENodeList) ? null : subSENodeList
						.get(0);
				if (tmpSENode != null) {
					// Get sub service UI Model
					ServiceModule tmpServiceModule = loadServiceModule(
							ServiceEntityFieldsHelper.getListSubType(subField),
							serviceEntityManager, tmpSENode, subExtension,
							subNodeInstId);
					subField.setAccessible(true);
					subField.set(serviceModuleInstance, tmpServiceModule);
				}
			}
		}

	}

	/**
	 * Wrapper method to load Sub Node list from persistency
	 * 
	 * @param parentEntityNode
	 * @param serviceEntityManager
	 * @param subExtensionUnion
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private List<ServiceEntityNode> loadSubNodeListWrapper(
			ServiceEntityNode parentEntityNode,
			ServiceEntityManager serviceEntityManager, String nodeName,
			ServiceUIModelExtensionUnion subExtensionUnion)
			throws ServiceEntityConfigureException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		if (subExtensionUnion.getToParentModelNodeMapConfigure() != null) {
			// In case the relationship to parent is configured
			return this.getSubNodeListByNodeConfigure(parentEntityNode,
					serviceEntityManager,
					subExtensionUnion.getToParentModelNodeMapConfigure());
		} else {
			// Default mode: sub node list as parent-to-child relationship
			List<ServiceEntityNode> subSENodeList = serviceEntityManager
					.getEntityNodeListByKey(parentEntityNode.getUuid(),
							IServiceEntityNodeFieldConstant.PARENTNODEUUID,
							nodeName, null);
			return subSENodeList;
		}
	}

	/**
	 * Utility method: get sub entity node list by parent entity node and Node
	 * Map Configure
	 * 
	 * @param parentEntityNode
	 * @param serviceEntityManager
	 * @param curNodeConfigure
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public List<ServiceEntityNode> getSubNodeListByNodeConfigure(
			ServiceEntityNode parentEntityNode,
			ServiceEntityManager serviceEntityManager,
			UIModelNodeMapConfigure curNodeConfigure)
			throws ServiceEntityConfigureException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (curNodeConfigure.getGetSENodeListCallback() != null) {
			// Retrieve retrieve seNodeValue for current node by call back hook
			// method
			resultList = curNodeConfigure.getGetSENodeListCallback().apply(
					parentEntityNode);
		} else {
			List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
			keyList = ServiceModelExtensionHelper
					.generateKeyStructFromNodeMapConfigure(curNodeConfigure,
							parentEntityNode, parentEntityNode.getClient());
			if (keyList == null) {
				return null;
			}
			ServiceEntityManager executeManager = serviceEntityManager;
			if (curNodeConfigure.getServiceEntityManager() != null) {
				executeManager = curNodeConfigure.getServiceEntityManager();
			}
			resultList = executeManager.getEntityNodeListByKeyList(keyList,
					curNodeConfigure.getNodeName(), null);
		}
		return resultList;
	}

	/**
	 * Filter the field out of list fields by node name
	 * 
	 * @param listFieldList
	 * @param nodeName
	 * @return
	 */
	public static Field filterListFieldByNodeName(List<Field> listFieldList,
			String nodeName) {
		if (ServiceCollectionsHelper.checkNullList(listFieldList)) {
			return null;
		}
		for (Field field : listFieldList) {
			IServiceModuleFieldConfig subListFieldConfigure = field
					.getAnnotation(IServiceModuleFieldConfig.class);
			if (subListFieldConfigure == null) {
				continue;
			}
			if (subListFieldConfigure.nodeName().equals(nodeName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * V2 Filter the field out of list fields by node Inst Id
	 * 
	 * @param listFieldList
	 * @param nodeInstId
	 * @return
	 */
	public static Field filterListFieldByNodeInstId(List<Field> listFieldList,
			String nodeInstId) {
		if (ServiceCollectionsHelper.checkNullList(listFieldList)) {
			return null;
		}
		for (Field field : listFieldList) {
			IServiceModuleFieldConfig subListFieldConfigure = field
					.getAnnotation(IServiceModuleFieldConfig.class);
			if (subListFieldConfigure == null) {
				continue;
			}
			if (subListFieldConfigure.nodeInstId().equals(nodeInstId)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Core Logic to return the core module field by given service module type
	 * 
	 * @param serviceModuleType
	 * @return
	 */
	public static Field getCoreModuleField(Class<?> serviceModuleType) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(
					field.getType(), ServiceEntityNode.class.getSimpleName())) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Core Logic to return the core module field by nodeInstId by given service
	 * module type
	 *
	 * @param serviceModuleType
	 * @param nodeName
	 * @return
	 */
	public static Field getModuleFieldByNodeName(Class<?> serviceModuleType,
												   String nodeName) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			IServiceModuleFieldConfig serviceModuleFieldConfig = field
					.getAnnotation(IServiceModuleFieldConfig.class);
			if (serviceModuleFieldConfig != null) {
				if (nodeName.equals(serviceModuleFieldConfig.nodeName())) {
					return field;
				}
			}
		}
		return null;
	}

	public static void setServiceModelFieldValue(ServiceModule targetServiceModel, String nodeName, Object value)
			throws IllegalAccessException {
		Class<?> serviceModelClass = targetServiceModel.getClass();
		Field field = ServiceModuleProxy.getModuleFieldByNodeName(serviceModelClass,
				nodeName);
		field.setAccessible(true);
		field.set(targetServiceModel, value);
	}

	public static void setServiceModelFieldValueByNodeInstId(ServiceModule targetServiceModel, String nodeInstId,
														 Object value)
			throws IllegalAccessException {
		Class<?> serviceModelClass = targetServiceModel.getClass();
		Field field = ServiceModuleProxy.getModuleFieldByNodeInstId(serviceModelClass,
				nodeInstId);
		field.setAccessible(true);
		field.set(targetServiceModel, value);
	}

	/**
	 * Core Logic to return the core module field by nodeInstId by given service
	 * module type
	 * 
	 * @param serviceModuleType
	 * @param nodeInstId
	 * @return
	 */
	public static Field getModuleFieldByNodeInstId(Class<?> serviceModuleType,
			String nodeInstId) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			IServiceModuleFieldConfig serviceModuleFieldConfig = field
					.getAnnotation(IServiceModuleFieldConfig.class);
			if (serviceModuleFieldConfig != null) {
				if (nodeInstId.equals(serviceModuleFieldConfig.nodeInstId())) {
					return field;
				}
			}
		}
		return null;
	}

	/**
	 * Core Logic to return the core ui module field by nodeInstId by given
	 * service module type
	 * 
	 * @param serviceUIModuleType
	 * @param nodeInstId
	 * @return
	 */
	public static Field getUIModuleFieldByNodeInstId(
			Class<?> serviceUIModuleType, String nodeInstId) {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceUIModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			IServiceUIModuleFieldConfig serviceUIModuleFieldConfig = field
					.getAnnotation(IServiceUIModuleFieldConfig.class);
			if (serviceUIModuleFieldConfig != null) {
				if (nodeInstId.equals(serviceUIModuleFieldConfig.nodeInstId())) {
					return field;
				}
			}
		}
		return null;
	}

	/**
	 * Filter out each node implementation class by node inst id
	 * 
	 * @param nodeInstId
	 * @param serviceModuleType
	 * @return
	 */
	public static Field getModuleFieldByNodeInstIdRecursive(String nodeInstId,
			Class<?> serviceModuleType) {
		Field modelField = getModuleFieldByNodeInstId(serviceModuleType,
				nodeInstId);
		if (modelField != null) {
			return modelField;
		}
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			Class<?> fieldType = field.getType();
			if (ServiceEntityFieldsHelper.checkSuperClassExtends(fieldType,
					ServiceModule.class.getSimpleName())) {
				Field tmpField = getModuleFieldByNodeInstIdRecursive(
						nodeInstId, fieldType);
				if (tmpField != null) {
					return tmpField;
				}
			}
		}
		return null;
	}

	/**
	 * Core Logic to get the List type field array by given service module type
	 * 
	 * @param serviceModuleType
	 * @return
	 */
	public static List<Field> getListTypeFields(Class<?> serviceModuleType) {
		List<Field> resultList = new ArrayList<Field>();
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			if (field.getType().isAssignableFrom(List.class)) {
				resultList.add(field);
			}
		}
		return resultList;
	}

	/**
	 * Core Logic to get the List type field array by given service module type
	 * 
	 * @param serviceModuleType
	 * @return
	 */
	public static List<Field> getNonListSubFields(Class<?> serviceModuleType) {
		List<Field> resultList = new ArrayList<>();
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			if (field.getType().isAssignableFrom(List.class)) {
				continue;
			}
			resultList.add(field);
		}
		return resultList;
	}

	/**
	 * Core Logic to get the List type field array by given service module type
	 * 
	 * @param serviceModuleType
	 * @return
	 */
	public static List<Field> getNonListSubFields(Class<?> serviceModuleType,
			String nodeInstId) {
		List<Field> resultList = new ArrayList<>();
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceModuleType);
		if (ServiceCollectionsHelper.checkNullList(fieldList)) {
			return null;
		}
		for (Field field : fieldList) {
			if (field.getType().isAssignableFrom(List.class)) {
				continue;
			}
			IServiceModuleFieldConfig serviceModuleFieldConfig = field
					.getAnnotation(IServiceModuleFieldConfig.class);
			if (serviceModuleFieldConfig != null) {
				if (nodeInstId.equals(serviceModuleFieldConfig.nodeInstId())) {
					continue;
				}
				resultList.add(field);
			}
		}
		return resultList;
	}

	/**
	 * Get the core SE node value from
	 * 
	 * @param serviceModule
	 * @param serviceUIModelExtensionUnion
	 * @return
	 * @throws ServiceModuleProxyException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static ServiceEntityNode getCoreEntityNodeValue(
			ServiceModule serviceModule,
			ServiceUIModelExtensionUnion serviceUIModelExtensionUnion)
			throws ServiceModuleProxyException, IllegalArgumentException,
			IllegalAccessException {
		Field coreModuleField = ServiceModuleProxy.getModuleFieldByNodeInstId(
				serviceModule.getClass(),
				serviceUIModelExtensionUnion.getNodeInstId());
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModule.getClass().getSimpleName());
		}
		coreModuleField.setAccessible(true);
		ServiceEntityNode seNodeValue = (ServiceEntityNode) coreModuleField
				.get(serviceModule);
		return seNodeValue;
	}

	/**
	 * Core Logic to return core service entity node value from service module
	 * 
	 * @param serviceModule
	 * @return
	 * @throws ServiceModuleProxyException
	 */
	@Deprecated
	public static ServiceEntityNode getCoreEntityNodeValue(
			ServiceModule serviceModule) throws ServiceModuleProxyException {
		Class<?> serviceModuleType = serviceModule.getClass();
		Field coreModuleField = ServiceModuleProxy
				.getCoreModuleField(serviceModuleType);
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModuleType.getSimpleName());
		}
		coreModuleField.setAccessible(true);
		ServiceEntityNode serviceEntityNode = null;
		try {
			serviceEntityNode = (ServiceEntityNode) coreModuleField
					.get(serviceModule);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModuleType.getSimpleName());
		}
		return serviceEntityNode;
	}

	/**
	 * Get the core SE node value from
	 * 
	 * @param serviceModule
	 * @return
	 * @throws ServiceModuleProxyException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setCoreEntityNodeValue(ServiceModule serviceModule,
			ServiceEntityNode seNodeValue,
			String nodeInstId)
			throws ServiceModuleProxyException, IllegalArgumentException,
			IllegalAccessException {
		Field coreModuleField = ServiceModuleProxy.getModuleFieldByNodeInstId(
				serviceModule.getClass(),
				nodeInstId);
		if (coreModuleField == null) {
			// raise exception when no core module field found
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_NOCOREMODULE,
					serviceModule.getClass().getSimpleName());
		}
		coreModuleField.setAccessible(true);
		coreModuleField.set(serviceModule, seNodeValue);
	}


	public <T extends ServiceModule> T quickCreateServiceModel(Class<T> serviceModuleType,
															   ServiceEntityNode coreSENode,
															   List<ServiceEntityNode> docMatItemList,
															   ServiceUIModelExtensionUnion serviceUIModelExtensionUnion) throws ServiceModuleProxyException {
		try {
			/*
			 * [Step1] Retrieve core UI model field
			 */
			String nodeInstId = serviceUIModelExtensionUnion.getNodeInstId();
			Field coreModuleField = ServiceModuleProxy
					.getModuleFieldByNodeInstId(serviceModuleType, nodeInstId);
			if (coreModuleField == null) {
				// raise exception when no core module field found
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.PARA_NOCOREMODULE,
						serviceModuleType.getSimpleName());
			}
			coreModuleField.setAccessible(true);
			// New ServiceModule instance in reflective way.
			ServiceModule serviceModule = serviceModuleType
					.getDeclaredConstructor().newInstance();
			coreModuleField.set(serviceModule, coreSENode);
			if(ServiceCollectionsHelper.checkNullList(docMatItemList)){
				return (T) serviceModule;
			}
			String docMatItemNodeName = docMatItemList.get(0).getNodeName();
			List<Field> listTypeFields = getListTypeFields(serviceModuleType);
			Field subField = filterListFieldByNodeInstId(listTypeFields, docMatItemNodeName);
			if(subField == null){
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.PARA_NOFIELD_BYNAME,
						docMatItemNodeName);
			}
			subField.setAccessible(true);
			subField.set(serviceModule, docMatItemList);
			return (T) serviceModule;
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

	public <T extends ServiceModule> T quickCreateServiceModel(Class<T> serviceModuleType,
															   ServiceEntityNode coreSENode,
															   String nodeInstId) throws ServiceModuleProxyException {
		try {
			/*
			 * [Step1] Retrieve core UI model field
			 */
			Field coreModuleField = ServiceModuleProxy
					.getModuleFieldByNodeInstId(serviceModuleType, nodeInstId);
			if (coreModuleField == null) {
				// raise exception when no core module field found
				throw new ServiceModuleProxyException(
						ServiceModuleProxyException.PARA_NOCOREMODULE,
						serviceModuleType.getSimpleName());
			}
			coreModuleField.setAccessible(true);
			// New ServiceModule instance in reflective way.
			ServiceModule serviceModule = serviceModuleType
					.newInstance();
			coreModuleField.set(serviceModule, coreSENode);
			return (T) serviceModule;
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException e) {
			throw new ServiceModuleProxyException(
					ServiceModuleProxyException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	public List<ServiceEntityNode> getNodeValueByDocNodeCategory(ServiceModule serviceModule,  int docNodeCategory){
		Class<?> serviceModuleType = serviceModule.getClass();
		List<Field> resultFieldList = getFieldListByDocNodeCategory(serviceModuleType, docNodeCategory);
		if(ServiceCollectionsHelper.checkNullList(resultFieldList)){
			return null;
		}
		List<ServiceEntityNode> serviceEntityNodeList = new ArrayList<>();
		for(Field field: resultFieldList){
			if (field.getType().isAssignableFrom(List.class)) {
				try {
					List<ServiceEntityNode> fieldValueList = (List<ServiceEntityNode>) field.get(serviceModule);
					ServiceCollectionsHelper.mergeToList(serviceEntityNodeList, fieldValueList);
				} catch (IllegalAccessException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
							serviceModuleType.getSimpleName() + ":" + field.getName()));
				}
			} else {
				try {
					ServiceEntityNode fieldValue = (ServiceEntityNode) field.get(serviceModule);
					ServiceCollectionsHelper.mergeToList(serviceEntityNodeList, fieldValue);
				} catch (IllegalAccessException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,
							serviceModuleType.getSimpleName() + ":" + field.getName()));
				}
			}
		}
		return serviceEntityNodeList;
	}

	public static List<Field> getFieldListByDocNodeCategory(Class<?> serviceModuleType, int docNodeCategory){
		List<Field> resultList;
		List<Field> subFlatFields = getNonListSubFields(serviceModuleType);
		resultList = getFieldListByDocNodeCategory(subFlatFields, docNodeCategory);
		if(!ServiceCollectionsHelper.checkNullList(resultList)){
			return resultList;
		}
		List<Field> listTypeFields = getListTypeFields(serviceModuleType);
		return getFieldListByDocNodeCategory(listTypeFields, docNodeCategory);
	}

	public static String getNodeNameByFieldDocNodeCategory(Class<?> serviceModuleType, int docNodeCategory){
		List<Field> resultFieldList = getFieldListByDocNodeCategory(serviceModuleType,
				docNodeCategory);
		if(ServiceCollectionsHelper.checkNullList(resultFieldList)){
			return null;
		}
		Field targetField = resultFieldList.get(0);
		IServiceModuleFieldConfig fieldConfig = targetField.getAnnotation(IServiceModuleFieldConfig.class);
		return fieldConfig.nodeName();
	}

	private static List<Field> getFieldListByDocNodeCategory(List<Field> allFieldList, int docNodeCategory){
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return null;
		}
		List<Field> resultList = new ArrayList<>();
		for (Field field : allFieldList) {
			IServiceModuleFieldConfig serviceModuleFieldConfig = field
					.getAnnotation(IServiceModuleFieldConfig.class);
			if (serviceModuleFieldConfig != null) {
				if(docNodeCategory == serviceModuleFieldConfig.docNodeCategory()){
					resultList.add(field);
				}
			}
		}
		return resultList;
	}


}
