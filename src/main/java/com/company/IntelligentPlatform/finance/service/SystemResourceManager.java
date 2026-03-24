package com.company.IntelligentPlatform.finance.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.finance.dto.ResFinAccountFieldSettingUIModel;
import com.company.IntelligentPlatform.finance.dto.ResFinAccountProcessCodeUIModel;
import com.company.IntelligentPlatform.finance.dto.ResFinAccountSettingUIModel;
import com.company.IntelligentPlatform.finance.dto.ResFinSystemResourceUIModel;
import com.company.IntelligentPlatform.finance.dto.ResourceAuthorizationUIModel;
import com.company.IntelligentPlatform.finance.dto.ResFinSystemResourceSearchModel;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.IFinSettleConfigure;
import com.company.IntelligentPlatform.common.controller.IFinanceControllerResource;
import com.company.IntelligentPlatform.common.service.SystemResourceDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ResFinAccountSettingIdHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.service.SystemConfigureException;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceManager;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountProcessCode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.ResourceAuthorization;
import com.company.IntelligentPlatform.common.model.SystemResource;
import com.company.IntelligentPlatform.common.model.SystemResourceConfigureProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class SystemResourceManager extends ServiceEntityManager {

	public static final String METHOD_ConvResFinSystemResourceToUI = "convResFinSystemResourceToUI";

	public static final String METHOD_ConvUIToResFinSystemResource = "convUIToResFinSystemResource";

	public static final String METHOD_ConvResFinAccountSettingToUI = "convResFinAccountSettingToUI";

	public static final String METHOD_ConvUIToResFinAccountSetting = "convUIToResFinAccountSetting";

	public static final String METHOD_ConvResFinSystemResToSettingUI = "convResFinSystemResToSettingUI";

	public static final String METHOD_ConvResFinAccountProcessCodeToUI = "convResFinAccountProcessCodeToUI";

	public static final String METHOD_ConvUIToResFinAccountProcessCode = "convUIToResFinAccountProcessCode";

	public static final String METHOD_ConvResourceAuthorizationToUI = "convResourceAuthorizationToUI";

	public static final String METHOD_ConvUIToResourceAuthorization = "convUIToResourceAuthorization";

	public static final String METHOD_ConvResFinAccountFieldSettingToUI = "convResFinAccountFieldSettingToUI";

	public static final String METHOD_ConvUIToResFinAccountFieldSetting = "convUIToResFinAccountFieldSetting";

	public static final String METHOD_ConvResFinAccountSettingToFieldSettingUI = "convResFinAccountSettingToFieldSettingUI";

	public static final String METHOD_ConvFinAccountTitleToFieldSettingUI = "convFinAccountTitleToFieldSettingUI";

	public static final String METHOD_ConvFinAccountTitleToSettingUI = "convFinAccountTitleToSettingUI";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected SystemResourceDAO systemResourceDAO;

	@Autowired
	protected SystemResourceConfigureProxy systemResourceConfigureProxy;

	@Autowired
	protected ResFinAccountSettingIdHelper resFinAccountSettingIdHelper;

	@Autowired
	protected SystemResourceIdHelper systemResourceIdHelper;

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	/**
	 * Constant of finance Account Object Proxy class simple name, should be
	 * consistent with the class name in finance model.
	 */
	public static final String IFinanceAccountObjectProxy = "IFinanceAccountObjectProxy";

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(systemResourceDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(systemResourceConfigureProxy);
	}

	@Override
	public ServiceEntityNode newEntityNode(ServiceEntityNode parentNode,
                                           String nodeName) throws ServiceEntityConfigureException {
		if (nodeName.equals(ResFinAccountSetting.NODENAME)) {
			ResFinAccountSetting ResFinAccountSetting = (ResFinAccountSetting) super
					.newEntityNode(parentNode, nodeName);
			String ResFinAccountSettingID = resFinAccountSettingIdHelper
					.genDefaultId(parentNode.getClient());
			ResFinAccountSetting.setId(ResFinAccountSettingID);
			return ResFinAccountSetting;
		} else {
			return super.newEntityNode(parentNode, nodeName);
		}
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		SystemResource SystemResource = (SystemResource) super
				.newRootEntityNode(client);
		String SystemResourceID = systemResourceIdHelper.genDefaultId(client);
		SystemResource.setId(SystemResourceID);
		return SystemResource;
	}

	public Map<Integer, String> getFinAccObjectKeyMap(String controllerName)
			throws SystemResourceException {
		try {
			Class<?> controllerClass = Class.forName(controllerName);
			String controllerClsSimName = controllerClass.getSimpleName();
			IFinanceControllerResource finResController = (IFinanceControllerResource) springContextBeanService
					.getBean(ServiceEntityStringHelper
							.headerToLowerCase(controllerClsSimName));
			if (finResController == null) {
				String interfaceName = IFinanceControllerResource.class
						.getSimpleName();
				throw new SystemResourceException(
						SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
						controllerName, interfaceName);
			}
			return finResController.getFinAccObjectMap();
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					controllerName);
		}
	}

	public void checkUIModel(String uiModelClassName)
			throws SystemResourceException {
		try {
			Class.forName(uiModelClassName);
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_UI_MODEL, uiModelClassName);
		}
	}

	public Map<Integer, String> getProcessCodeMap(String controllerName)
			throws SystemResourceException {
		try {
			Class<?> controllerClass = Class.forName(controllerName);
			String controllerClsSimName = controllerClass.getSimpleName();
			IFinanceControllerResource finResController = (IFinanceControllerResource) springContextBeanService
					.getBean(ServiceEntityStringHelper
							.headerToLowerCase(controllerClsSimName));
			if (finResController == null) {
				String interfaceName = IFinanceControllerResource.class
						.getSimpleName();
				throw new SystemResourceException(
						SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
						controllerName, interfaceName);
			}
			return finResController.getProcessCodeMap();
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					controllerName);
		}
	}
	
	

	public List<Field> getDoubleFieldList(Class<?> uiModelCls) {
		List<Field> rawFieldList = ServiceEntityFieldsHelper
				.getSelfDefinedFieldsList(uiModelCls);
		List<Field> resultList = new ArrayList<Field>();
		String doubleClsName = Double.class.getSimpleName();
		String doubleSimName = double.class.getSimpleName();
		if (rawFieldList == null) {
			return null;
		}
		for (Field field : rawFieldList) {
			String fieldTypeName = field.getType().getSimpleName();
			if (fieldTypeName.equals(doubleClsName)
					|| fieldTypeName.equals(doubleSimName)) {
				resultList.add(field);
			}
		}
		return resultList;
	}

	public List<Field> getFinAmountFieldList(Class<?> uiModelCls, int fieldType) {
		List<Field> rawFieldList = ServiceEntityFieldsHelper
				.getSelfDefinedFieldsList(uiModelCls);
		List<Field> resultList = new ArrayList<Field>();
		if (rawFieldList == null) {
			return null;
		}
		for (Field field : rawFieldList) {
			IFinSettleConfigure finSettleConfigure = field
					.getAnnotation(IFinSettleConfigure.class);
			if (finSettleConfigure == null) {
				continue;
			}
			if (finSettleConfigure.fieldType() == fieldType) {
				resultList.add(field);
			}
		}
		return resultList;
	}

	public void checkControllerClassValid(String controllerClassName)
			throws SystemResourceException {
		try {
			Class<?> controllerClass = Class.forName(controllerClassName);
			boolean checkInterface = checkClassInterfaceImplemented(
					controllerClass,
					IFinanceControllerResource.class.getSimpleName());
			if (!checkInterface) {
				throw new SystemResourceException(
						SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
						controllerClassName,
						IFinanceControllerResource.class.getSimpleName());
			}
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					controllerClassName);
		}
	}

	public void checkAccObjectProxyClassValid(String finAccObjectClassName)
			throws SystemResourceException {
		try {
			Class<?> finAccObjectClass = Class.forName(finAccObjectClassName);
			boolean checkInterface = checkClassInterfaceImplemented(
					finAccObjectClass, IFinanceAccountObjectProxy);
			if (!checkInterface) {
				throw new SystemResourceException(
						SystemResourceException.PARA2_WRG_CONTROL_INTERFACE,
						finAccObjectClassName, IFinanceAccountObjectProxy);
			}
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_CONTROLLER_CLASS,
					finAccObjectClassName);
		}
	}

	public boolean checkClassInterfaceImplemented(Class<?> classType,
			String interfaceName) {
		Class<?>[] interfaceArray = classType.getInterfaces();
		if (interfaceArray == null || interfaceArray.length == 0) {
			return false;
		}
		for (Class<?> interfaceClass : interfaceArray) {
			if (interfaceName.equals(interfaceClass.getSimpleName())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkClassExtendSuperClass(Class<?> classType,
			String superClassName) {
		Class<?> superClass = classType.getSuperclass();
		if (superClass == null) {
			return false;
		}
		if (superClassName.equals(superClass.getSimpleName())) {
			return true;
		}
		return false;
	}

	public Map<String, String> genDoubleFieldMap(String uimodelClassName)
			throws SystemResourceException {
		try {
			Class<?> uiModelCls = Class.forName(uimodelClassName);
			List<Field> doubleFieldList = getDoubleFieldList(uiModelCls);
			Map<String, String> resultMap = new HashMap<String, String>();
			if (doubleFieldList == null) {
				return null;
			}
			for (Field field : doubleFieldList) {
				resultMap.put(field.getName(), field.getName());
			}
			return resultMap;
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_UI_MODEL, uimodelClassName);
		}
	}

	/**
	 * Get Fin Amount field by core settle ID & field Type
	 * 
	 * @param settleUIModelCls
	 * @param fieldType
	 * @param coreID
	 * @return
	 */
	public Field getFinAmountField(Class<?> settleUIModelCls, int fieldType,
			String coreSettleID) {
		List<Field> finFieldList = getFinAmountFieldList(settleUIModelCls,
				fieldType);
		if (finFieldList == null || finFieldList.size() == 0) {
			return null;
		}
		for (Field field : finFieldList) {
			IFinSettleConfigure finSettleConfigure = field
					.getAnnotation(IFinSettleConfigure.class);
			if (finSettleConfigure == null) {
				continue;
			}
			if (coreSettleID.equals(finSettleConfigure.coreSettleID())) {
				return field;
			}
		}
		return null;
	}

	public Map<String, String> genFinAmountFieldMap(String uimodelClassName,
			int fieldType) throws SystemResourceException {
		try {
			Class<?> uiModelCls = Class.forName(uimodelClassName);
			List<Field> finFieldList = getFinAmountFieldList(uiModelCls,
					fieldType);
			Map<String, String> resultMap = new HashMap<String, String>();
			if (finFieldList == null) {
				return null;
			}
			for (Field field : finFieldList) {
				resultMap.put(field.getName(), field.getName());
			}
			return resultMap;
		} catch (ClassNotFoundException e) {
			throw new SystemResourceException(
					SystemResourceException.PARA_WRG_UI_MODEL, uimodelClassName);
		}
	}

	public Map<Integer, String> getProcessCodeMap(SystemResource systemResource)
			throws ClassNotFoundException, SystemConfigureException {
		Class<?> classType = Class.forName(systemResource
				.getControllerClassName());
		boolean result = systemConfigureResourceManager
				.checkClassImplementInterface(classType,
						IFinanceControllerResource.class.getSimpleName());
		if (result == false) {
			throw new SystemConfigureException(
					SystemConfigureException.PARA2_WRG_SWITCH_INTERFACE,
					classType.getSimpleName(),
					IFinanceControllerResource.class.getSimpleName());
		}
		try {
			IFinanceControllerResource financeResource = (IFinanceControllerResource) classType.newInstance();
			return financeResource.getProcessCodeMap();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SystemConfigureException(
					SystemConfigureException.PARA_SYSTEM_WRONG, e.getMessage());
		}		
	}

	public void admDeleteResFinAccSettingBatchByResourceID(String resourceID,
			String client) throws ServiceEntityConfigureException {
		SystemResource systemResource = (SystemResource) getEntityNodeByKey(
				resourceID, IServiceEntityNodeFieldConstant.ID,
				SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return;
		}
		// In case overwritten, delete the previous entries firstly
		List<ServiceEntityNode> resFinAccSettingList = getEntityNodeListByKey(
				systemResource.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ResFinAccountSetting.NODENAME, client, null);
		if (resFinAccSettingList != null && resFinAccSettingList.size() > 0) {
			deleteEntityNodeListByKey(systemResource.getUuid(),
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					ResFinAccountSetting.NODENAME, systemResource.getClient(),
					true);
		}
		List<ServiceEntityNode> resFinAccFieldList = getEntityNodeListByKey(
				systemResource.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ResFinAccountFieldSetting.NODENAME, client, null);
		if (resFinAccFieldList != null && resFinAccFieldList.size() > 0) {
			deleteEntityNodeListByKey(systemResource.getUuid(),
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					ResFinAccountFieldSetting.NODENAME,
					systemResource.getClient(), true);
		}
		List<ServiceEntityNode> resFinAccProcessCodeList = getEntityNodeListByKey(
				systemResource.getUuid(),
				IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ResFinAccountProcessCode.NODENAME, client, null);
		if (resFinAccProcessCodeList != null
				&& resFinAccProcessCodeList.size() > 0) {
			deleteEntityNodeListByKey(systemResource.getUuid(),
					IServiceEntityNodeFieldConstant.ROOTNODEUUID,
					ResFinAccountProcessCode.NODENAME,
					systemResource.getClient(), true);
		}
	}

	public void admDeleteResFinAccSetting(String uuid)
			throws ServiceEntityConfigureException {
		ResFinAccountSetting resFinAccountSetting = (ResFinAccountSetting) getEntityNodeByKey(
				uuid, IServiceEntityNodeFieldConstant.UUID,
				ResFinAccountSetting.NODENAME, null);
		if (resFinAccountSetting == null) {
			return;
		}
		/*
		 * [Step1] Execute deletion of this ResFinAccountSetting
		 */
		admDeleteEntityByKey(resFinAccountSetting.getUuid(),
				IServiceEntityNodeFieldConstant.UUID,
				ResFinAccountSetting.NODENAME);
		/*
		 * [Step2] found the sub process code and ResFinAccountFieldSetting
		 */
		deleteEntityNodeListByKey(resFinAccountSetting.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ResFinAccountFieldSetting.NODENAME,
				resFinAccountSetting.getClient(), true);
		deleteEntityNodeListByKey(resFinAccountSetting.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ResFinAccountProcessCode.NODENAME,
				resFinAccountSetting.getClient(), true);

	}

	public void admDeleteSystemResource(String resourceID,
			String logonUserUUID, String organizationUUID, String client)
			throws ServiceEntityConfigureException {
		// Get System resource firstly
		SystemResource systemResource = (SystemResource) getEntityNodeByKey(
				resourceID, IServiceEntityNodeFieldConstant.ID,
				SystemResource.NODENAME, client, null, true);
		if (systemResource == null) {
			return;
		}
		String baseUUID = systemResource.getUuid();
		List<ServiceEntityNode> resFinAccSettingList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ResFinAccountSetting.NODENAME, client, null);
		if (resFinAccSettingList != null && resFinAccSettingList.size() > 0) {
			for (ServiceEntityNode seNode : resFinAccSettingList) {
				// Delete array of finAcc field list
				List<ServiceEntityNode> resFinAccFieldList = getEntityNodeListByKey(
						seNode.getUuid(),
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						ResFinAccountFieldSetting.NODENAME, client, null);
				if (resFinAccFieldList != null) {
					for (ServiceEntityNode accFieldNode : resFinAccFieldList) {
						admDeleteEntityByKey(
								accFieldNode.getUuid(),
								IServiceEntityNodeFieldConstant.UUID,
								ResFinAccountFieldSetting.NODENAME);
					}
				}
				// Delete array of finance process code list
				List<ServiceEntityNode> resFinProcessCodeList = getEntityNodeListByKey(
						seNode.getUuid(),
						IServiceEntityNodeFieldConstant.PARENTNODEUUID,
						ResFinAccountProcessCode.NODENAME, client, null);
				if (resFinProcessCodeList != null) {
					for (ServiceEntityNode processcodeNode : resFinProcessCodeList) {
						admDeleteEntityByKey(
								processcodeNode.getUuid(),
								IServiceEntityNodeFieldConstant.UUID,
								ResFinAccountProcessCode.NODENAME);
					}
				}
				// delete the finance account setting
				admDeleteEntityByKey(seNode.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						ResFinAccountSetting.NODENAME);
			}
		}
		// Delete authorization object referece node
		List<ServiceEntityNode> resFinAuthorObjectList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ResourceAuthorization.NODENAME, client, null);
		if (resFinAuthorObjectList != null && resFinAuthorObjectList.size() > 0) {
			for (ServiceEntityNode seNode : resFinAuthorObjectList) {
				admDeleteEntityByKey(seNode.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						ResourceAuthorization.NODENAME);
			}
		}
		// Delete the system resource root node
		admDeleteEntityByKey(baseUUID,
				IServiceEntityNodeFieldConstant.UUID, SystemResource.NODENAME);
	}

	public List<ServiceEntityNode> searchInternal(
			ResFinSystemResourceSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[systemResource]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(SystemResource.SENAME);
		searchNodeConfig0.setNodeName(SystemResource.NODENAME);
		searchNodeConfig0.setNodeInstID(SystemResource.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[resFinAccountSetting]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ResFinAccountSetting.SENAME);
		searchNodeConfig2.setNodeName(ResFinAccountSetting.NODENAME);
		searchNodeConfig2.setNodeInstID(ResFinAccountSetting.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(SystemResource.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[resFinAccountProcessCode]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(ResFinAccountProcessCode.SENAME);
		searchNodeConfig4.setNodeName(ResFinAccountProcessCode.NODENAME);
		searchNodeConfig4.setNodeInstID(ResFinAccountProcessCode.NODENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig4.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// Search node:[resourceAuthorization]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(ResourceAuthorization.SENAME);
		searchNodeConfig6.setNodeName(ResourceAuthorization.NODENAME);
		searchNodeConfig6.setNodeInstID(ResourceAuthorization.NODENAME);
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig6.setBaseNodeInstID(SystemResource.SENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// Search node:[resFinAccountFieldSetting]
		BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
		searchNodeConfig8.setSeName(ResFinAccountFieldSetting.SENAME);
		searchNodeConfig8.setNodeName(ResFinAccountFieldSetting.NODENAME);
		searchNodeConfig8.setNodeInstID(ResFinAccountFieldSetting.NODENAME);
		searchNodeConfig8.setStartNodeFlag(false);
		searchNodeConfig8
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig8.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		searchNodeConfigList.add(searchNodeConfig8);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convResFinSystemResourceToUI(SystemResource systemResource,
			ResFinSystemResourceUIModel resFinSystemResourceUIModel) {
		if (systemResource != null) {
			if (!ServiceEntityStringHelper.checkNullString(systemResource
					.getUuid())) {
				resFinSystemResourceUIModel.setUuid(systemResource.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemResource
					.getParentNodeUUID())) {
				resFinSystemResourceUIModel.setParentNodeUUID(systemResource
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemResource
					.getRootNodeUUID())) {
				resFinSystemResourceUIModel.setRootNodeUUID(systemResource
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(systemResource
					.getClient())) {
				resFinSystemResourceUIModel.setClient(systemResource
						.getClient());
			}
			resFinSystemResourceUIModel.setNote(systemResource.getNote());
			resFinSystemResourceUIModel.setId(systemResource.getId());
			resFinSystemResourceUIModel.setUiModelClassName(systemResource
					.getUiModelClassName());
			resFinSystemResourceUIModel
					.setRefSimAuthorObjectUUID(systemResource
							.getRefSimAuthorObjectUUID());
			resFinSystemResourceUIModel.setRegNodeName(systemResource
					.getRegNodeName());
			resFinSystemResourceUIModel.setViewType(systemResource
					.getViewType());
			resFinSystemResourceUIModel.setControllerClassName(systemResource
					.getControllerClassName());
			resFinSystemResourceUIModel.setName(systemResource.getName());
			resFinSystemResourceUIModel.setUrl(systemResource.getUrl());
			resFinSystemResourceUIModel.setRegSEName(systemResource
					.getRegSEName());
			resFinSystemResourceUIModel.setAbsURL(systemResource.getAbsURL());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:systemResource
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToResFinSystemResource(
			ResFinSystemResourceUIModel resFinSystemResourceUIModel,
			SystemResource rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(resFinSystemResourceUIModel.getUuid())) {
			rawEntity.setUuid(resFinSystemResourceUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinSystemResourceUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(resFinSystemResourceUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinSystemResourceUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(resFinSystemResourceUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinSystemResourceUIModel.getClient())) {
			rawEntity.setClient(resFinSystemResourceUIModel.getClient());
		}
		rawEntity.setNote(resFinSystemResourceUIModel.getNote());
		rawEntity.setId(resFinSystemResourceUIModel.getId());
		rawEntity.setUiModelClassName(resFinSystemResourceUIModel
				.getUiModelClassName());
		rawEntity.setUuid(resFinSystemResourceUIModel.getUuid());
		rawEntity.setRefSimAuthorObjectUUID(resFinSystemResourceUIModel
				.getRefSimAuthorObjectUUID());
		rawEntity.setClient(resFinSystemResourceUIModel.getClient());
		rawEntity.setRegNodeName(resFinSystemResourceUIModel.getRegNodeName());
		rawEntity.setViewType(resFinSystemResourceUIModel.getViewType());
		rawEntity.setControllerClassName(resFinSystemResourceUIModel
				.getControllerClassName());
		rawEntity.setName(resFinSystemResourceUIModel.getName());
		rawEntity.setUrl(resFinSystemResourceUIModel.getUrl());
		rawEntity.setRegSEName(resFinSystemResourceUIModel.getRegSEName());
		rawEntity.setAbsURL(resFinSystemResourceUIModel.getAbsURL());

	}
	
	public void convResFinAccountSettingToUI(
			ResFinAccountSetting resFinAccountSetting,
			ResFinAccountSettingUIModel resFinAccountSettingUIModel)
			throws ServiceEntityInstallationException {
		convResFinAccountSettingToUI(resFinAccountSetting, resFinAccountSettingUIModel, null);
	}

	public void convResFinAccountSettingToUI(
			ResFinAccountSetting resFinAccountSetting,
			ResFinAccountSettingUIModel resFinAccountSettingUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (resFinAccountSetting != null) {
			if (!ServiceEntityStringHelper.checkNullString(resFinAccountSetting
					.getUuid())) {
				resFinAccountSettingUIModel.setUuid(resFinAccountSetting
						.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(resFinAccountSetting
					.getParentNodeUUID())) {
				resFinAccountSettingUIModel
						.setParentNodeUUID(resFinAccountSetting
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(resFinAccountSetting
					.getRootNodeUUID())) {
				resFinAccountSettingUIModel
						.setRootNodeUUID(resFinAccountSetting.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(resFinAccountSetting
					.getClient())) {
				resFinAccountSettingUIModel.setClient(resFinAccountSetting
						.getClient());
			}
			resFinAccountSettingUIModel.setId(resFinAccountSetting.getId());
			resFinAccountSettingUIModel.setName(resFinAccountSetting.getName());
			resFinAccountSettingUIModel.setNote(resFinAccountSetting.getNote());
			resFinAccountSettingUIModel.setRefUUID(resFinAccountSetting
					.getRefUUID());
			resFinAccountSettingUIModel
					.setRefFinAccObjectKey(resFinAccountSetting
							.getRefFinAccObjectKey());
			resFinAccountSettingUIModel
					.setRefFinAccObjectProxyClass(resFinAccountSetting
							.getRefFinAccObjectProxyClass());
			resFinAccountSettingUIModel.setSwitchFlag(resFinAccountSetting
					.getSwitchFlag());
			if(logonInfo != null){
				Map<Integer, String> switchFlagMap = standardSwitchProxy.getSimpleSwitchMap(logonInfo.getLanguageCode());
				resFinAccountSettingUIModel.setSwitchFlagValue(switchFlagMap
						.get(resFinAccountSetting.getSwitchFlag()));
			}
			resFinAccountSettingUIModel.setCoreSettleId(resFinAccountSetting
					.getCoreSettleID());
			resFinAccountSettingUIModel
					.setSettleUIModelName(resFinAccountSetting
							.getSettleUIModelName());
			resFinAccountSettingUIModel
					.setAllAmountFieldName(resFinAccountSetting
							.getAllAmountFieldName());
			resFinAccountSettingUIModel
					.setToSettleFieldName(resFinAccountSetting
							.getToSettleFieldName());
			resFinAccountSettingUIModel
					.setSettledFieldName(resFinAccountSetting
							.getSettledFieldName());
		}
	}

	public void convResFinSystemResToSettingUI(SystemResource systemResource,
			ResFinAccountSettingUIModel resFinAccountSettingUIModel) {
		resFinAccountSettingUIModel.setParentNodeId(systemResource.getId());
		resFinAccountSettingUIModel.setParentNodeName(systemResource.getName());
	}

	public void convUIToResFinAccountSetting(
			ResFinAccountSettingUIModel resFinAccountSettingUIModel,
			ResFinAccountSetting rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountSettingUIModel.getUuid())) {
			rawEntity.setUuid(resFinAccountSettingUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountSettingUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(resFinAccountSettingUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountSettingUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(resFinAccountSettingUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountSettingUIModel.getClient())) {
			rawEntity.setClient(resFinAccountSettingUIModel.getClient());
		}
		rawEntity.setId(resFinAccountSettingUIModel.getId());
		rawEntity.setName(resFinAccountSettingUIModel.getName());
		rawEntity.setNote(resFinAccountSettingUIModel.getNote());
		rawEntity.setRefFinAccObjectKey(resFinAccountSettingUIModel
				.getRefFinAccObjectKey());
		rawEntity.setRefUUID(resFinAccountSettingUIModel.getRefUUID());
		rawEntity.setRefFinAccObjectProxyClass(resFinAccountSettingUIModel
				.getRefFinAccObjectProxyClass());
		rawEntity.setSwitchFlag(resFinAccountSettingUIModel.getSwitchFlag());
		rawEntity
				.setCoreSettleID(resFinAccountSettingUIModel.getCoreSettleId());
		rawEntity.setSettleUIModelName(resFinAccountSettingUIModel
				.getSettleUIModelName());
		rawEntity.setAllAmountFieldName(resFinAccountSettingUIModel
				.getAllAmountFieldName());
		rawEntity.setToSettleFieldName(resFinAccountSettingUIModel
				.getToSettleFieldName());
		rawEntity.setSettledFieldName(resFinAccountSettingUIModel
				.getSettledFieldName());
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:resFinAccountProcessCode
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToResFinAccountProcessCode(
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel,
			ResFinAccountProcessCode rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountProcessCodeUIModel.getUuid())) {
			rawEntity.setUuid(resFinAccountProcessCodeUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountProcessCodeUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(resFinAccountProcessCodeUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountProcessCodeUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(resFinAccountProcessCodeUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountProcessCodeUIModel.getClient())) {
			rawEntity.setClient(resFinAccountProcessCodeUIModel.getClient());
		}
		rawEntity.setClient(resFinAccountProcessCodeUIModel.getClient());
		rawEntity.setName(resFinAccountProcessCodeUIModel.getName());
		rawEntity.setId(resFinAccountProcessCodeUIModel.getId());
		rawEntity.setUuid(resFinAccountProcessCodeUIModel.getUuid());
		rawEntity.setProcessCode(resFinAccountProcessCodeUIModel
				.getProcessCode());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convResFinAccountProcessCodeToUI(
			ResFinAccountProcessCode resFinAccountProcessCode,
			ResFinAccountProcessCodeUIModel resFinAccountProcessCodeUIModel) {
		if (resFinAccountProcessCode != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountProcessCode.getUuid())) {
				resFinAccountProcessCodeUIModel
						.setUuid(resFinAccountProcessCode.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountProcessCode
							.getParentNodeUUID())) {
				resFinAccountProcessCodeUIModel
						.setParentNodeUUID(resFinAccountProcessCode
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountProcessCode.getRootNodeUUID())) {
				resFinAccountProcessCodeUIModel
						.setRootNodeUUID(resFinAccountProcessCode
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountProcessCode.getClient())) {
				resFinAccountProcessCodeUIModel
						.setClient(resFinAccountProcessCode.getClient());
			}
			resFinAccountProcessCodeUIModel.setName(resFinAccountProcessCode
					.getName());
			resFinAccountProcessCodeUIModel.setId(resFinAccountProcessCode
					.getId());
			resFinAccountProcessCodeUIModel
					.setProcessCode(resFinAccountProcessCode.getProcessCode());
			
			try {
				SystemResource systemResource = (SystemResource) this.getEntityNodeByKey(
								resFinAccountProcessCode.getRootNodeUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								SystemResource.NODENAME, resFinAccountProcessCode.getClient(),null);
				if(systemResource != null){
					Map<Integer, String> processCodeMap = getProcessCodeMap(systemResource.getControllerClassName());
					resFinAccountProcessCodeUIModel.setProcessCodeValue(processCodeMap
							.get(resFinAccountProcessCode.getProcessCode()));
				}
			} catch (ServiceEntityConfigureException e) {
				// just continue;
			} catch (SystemResourceException e) {
				// just continue;
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convResourceAuthorizationToUI(
			ResourceAuthorization resourceAuthorization,
			ResourceAuthorizationUIModel resourceAuthorizationUIModel) {
		if (resourceAuthorization != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(resourceAuthorization.getUuid())) {
				resourceAuthorizationUIModel.setUuid(resourceAuthorization
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resourceAuthorization.getParentNodeUUID())) {
				resourceAuthorizationUIModel
						.setParentNodeUUID(resourceAuthorization
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resourceAuthorization.getRootNodeUUID())) {
				resourceAuthorizationUIModel
						.setRootNodeUUID(resourceAuthorization
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resourceAuthorization.getClient())) {
				resourceAuthorizationUIModel.setClient(resourceAuthorization
						.getClient());
			}
			resourceAuthorizationUIModel.setRefNodeName(resourceAuthorization
					.getRefNodeName());
			resourceAuthorizationUIModel.setRefUUID(resourceAuthorization
					.getRefUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:resourceAuthorization
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToResourceAuthorization(
			ResourceAuthorizationUIModel resourceAuthorizationUIModel,
			ResourceAuthorization rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(resourceAuthorizationUIModel.getUuid())) {
			rawEntity.setUuid(resourceAuthorizationUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resourceAuthorizationUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(resourceAuthorizationUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resourceAuthorizationUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(resourceAuthorizationUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resourceAuthorizationUIModel.getClient())) {
			rawEntity.setClient(resourceAuthorizationUIModel.getClient());
		}
		rawEntity.setRefNodeName(resourceAuthorizationUIModel.getRefNodeName());
		rawEntity.setRefUUID(resourceAuthorizationUIModel.getRefUUID());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convResFinAccountFieldSettingToUI(
			ResFinAccountFieldSetting resFinAccountFieldSetting,
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel) {
		if (resFinAccountFieldSetting != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountFieldSetting.getUuid())) {
				resFinAccountFieldSettingUIModel
						.setUuid(resFinAccountFieldSetting.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountFieldSetting
							.getParentNodeUUID())) {
				resFinAccountFieldSettingUIModel
						.setParentNodeUUID(resFinAccountFieldSetting
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountFieldSetting
							.getRootNodeUUID())) {
				resFinAccountFieldSettingUIModel
						.setRootNodeUUID(resFinAccountFieldSetting
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(resFinAccountFieldSetting.getClient())) {
				resFinAccountFieldSettingUIModel
						.setClient(resFinAccountFieldSetting.getClient());
			}
			resFinAccountFieldSettingUIModel
					.setWeightFactor(resFinAccountFieldSetting
							.getWeightFactor());
			resFinAccountFieldSettingUIModel
					.setFieldName(resFinAccountFieldSetting.getFieldName());
			resFinAccountFieldSettingUIModel
					.setFinAccProxyMethodName(resFinAccountFieldSetting
							.getFinAccProxyMethodName());
			resFinAccountFieldSettingUIModel
					.setFinAccProxyClassName(resFinAccountFieldSetting
							.getFinAccProxyClassName());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:resFinAccountFieldSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToResFinAccountFieldSetting(
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel,
			ResFinAccountFieldSetting rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountFieldSettingUIModel.getUuid())) {
			rawEntity.setUuid(resFinAccountFieldSettingUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountFieldSettingUIModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(resFinAccountFieldSettingUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountFieldSettingUIModel
						.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(resFinAccountFieldSettingUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(resFinAccountFieldSettingUIModel.getClient())) {
			rawEntity.setClient(resFinAccountFieldSettingUIModel.getClient());
		}
		rawEntity.setWeightFactor(resFinAccountFieldSettingUIModel
				.getWeightFactor());
		rawEntity.setFieldName(resFinAccountFieldSettingUIModel.getFieldName());
		rawEntity.setFinAccProxyMethodName(resFinAccountFieldSettingUIModel
				.getFinAccProxyMethodName());
		rawEntity.setClient(resFinAccountFieldSettingUIModel.getClient());
		rawEntity.setFinAccProxyClassName(resFinAccountFieldSettingUIModel
				.getFinAccProxyClassName());
	}

	public void convResFinAccountSettingToFieldSettingUI(
			ResFinAccountSetting resFinAccountSetting,
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel) {
		if (resFinAccountSetting != null) {
			resFinAccountFieldSettingUIModel.setBaseId(resFinAccountSetting
					.getId());
			resFinAccountFieldSettingUIModel.setBaseName(resFinAccountSetting
					.getName());
		}
	}

	public void convFinAccountTitleToFieldSettingUI(
			FinAccountTitle finAccountTitle,
			ResFinAccountFieldSettingUIModel resFinAccountFieldSettingUIModel) {
		if (finAccountTitle != null) {
			resFinAccountFieldSettingUIModel
					.setFinAccountTitleId(finAccountTitle.getId());
			resFinAccountFieldSettingUIModel
					.setFinAccountTitleName(finAccountTitle.getName());
		}
	}

	public void convFinAccountTitleToSettingUI(FinAccountTitle finAccountTitle,
			ResFinAccountSettingUIModel resFinAccountFieldUIModel) {
		if (finAccountTitle != null) {
			resFinAccountFieldUIModel.setFinAccountTitleId(finAccountTitle
					.getId());
			resFinAccountFieldUIModel.setFinAccountTitleName(finAccountTitle
					.getName());
		}
	}

}
