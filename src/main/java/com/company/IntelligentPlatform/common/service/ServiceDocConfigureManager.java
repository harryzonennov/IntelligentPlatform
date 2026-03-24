package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.SEUIModelFieldsHelper;
import com.company.IntelligentPlatform.common.controller.ISEUIModelHeader;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureUIModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureParaGroupUIModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureParaUIModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocInitConfigureModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocParaConsumerValueModeProxy;
// TODO-DAO: import ...ServiceDocConfigureDAO;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.LogonUserMessage;
import com.company.IntelligentPlatform.common.service.SearchProxyConfigManager;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigureParaGroup;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerUnion;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [ServiceDocConfigure]
 *
 * @author
 * @date Thu May 05 15:43:02 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class ServiceDocConfigureManager extends ServiceEntityManager {

	public static final String METHOD_ConvServiceDocConfigureToUI = "convServiceDocConfigureToUI";

	public static final String METHOD_ConvUIToServiceDocConfigure = "convUIToServiceDocConfigure";

	public static final String METHOD_ConvSearchProxyConfigToUI = "convSearchProxyConfigToUI";

	public static final String METHOD_ConvInputModuleToUI = "convInputModuleToUI";

	public static final String METHOD_ConvOutputModuleToUI = "convOutputModuleToUI";

	public static final String METHOD_ConvServiceDocConfigureParaToUI = "convServiceDocConfigureParaToUI";

	public static final String METHOD_ConvConfigureToParaUI = "convConfigureToParaUI";
	
	public static final String METHOD_ConvParntGroupToUI = "convParntGroupToUI";
	
	public static final String METHOD_ConvConfigureToParaGroupUI = "convConfigureToParaGroupUI";

	public static final String METHOD_ConvUIToServiceDocConfigurePara = "convUIToServiceDocConfigurePara";

	public static final String METHOD_ConvServiceDocConfigureParaGroupToUI = "convServiceDocConfigureParaGroupToUI";

	public static final String METHOD_ConvUIToServiceDocConfigureParaGroup = "convUIToServiceDocConfigureParaGroup";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected ServiceDocConfigureDAO serviceDocConfigureDAO;

	@Autowired
	protected ServiceDocConfigureResourceManager serviceDocConfigureResourceManager;

	@Autowired
	protected ServiceDocConfigureConfigureProxy serviceDocConfigureConfigureProxy;

	@Autowired
	protected ServiceDocConfigParaFactory serviceDocConfigParaFactory;
	
	@Autowired
	protected ServiceDocConfigureSearchProxy serviceDocConfigureSearchProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected ServiceDocConfigureIdHelper serviceDocConfigureIdHelper;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ServiceDocConsumerUnionManager serviceDocConsumerUnionManager;
	
	@Autowired
	protected SearchProxyConfigManager searchProxyConfigManager;

	@Autowired
	protected StandardLogicOperatorProxy standardLogicOperatorProxy;

	@Autowired
	protected ServiceDocParaConsumerValueModeProxy serviceDocParaConsumerValueModeProxy;

	private Map<Integer, String> paraDirectionMap;

	private Map<Integer, String> fixValueOperatorMap;

	private Map<Integer, String> dataOffsetDirectionMap;

	private Map<Integer, String> logicOperatorMap;

	private Map<Integer, String> switchFlagMap;

	private Map<Integer, String> dataOffsetUnitHighMap;
	
	private Map<Integer, String> inputConsValueModeMap;
	
	private Map<Integer, String> outputConsValueModeMap;

	public ServiceDocConfigureManager() {
		super.seConfigureProxy = new ServiceDocConfigureConfigureProxy();
		// TODO-DAO: super.serviceEntityDAO = new ServiceDocConfigureDAO();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(serviceDocConfigureDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(serviceDocConfigureConfigureProxy);
	}

	public Map<Integer, String> initParaDirectionMap()
			throws ServiceEntityInstallationException {
		if (this.paraDirectionMap == null) {
			this.paraDirectionMap = serviceDropdownListHelper.getUIDropDownMap(
					ServiceDocConfigureParaUIModel.class, "paraDirection");
		}
		return this.paraDirectionMap;
	}

	public Map<Integer, String> initFixValueOperatorMap()
			throws ServiceEntityInstallationException {
		if (this.fixValueOperatorMap == null) {
			this.fixValueOperatorMap = serviceDropdownListHelper
					.getUIDropDownMap(ServiceDocConfigureParaUIModel.class,
							"fixValueOperator");
		}
		return this.fixValueOperatorMap;
	}

	public Map<Integer, String> initDataOffsetDirectionMap()
			throws ServiceEntityInstallationException {
		if (this.dataOffsetDirectionMap == null) {
			this.dataOffsetDirectionMap = serviceDropdownListHelper
					.getUIDropDownMap(ServiceDocConfigureParaUIModel.class,
							"dataOffsetDirection");
		}
		return this.dataOffsetDirectionMap;
	}

	public Map<Integer, String> initLogicOperatorMap(String lanKey)
			throws ServiceEntityInstallationException {
		if (this.logicOperatorMap == null) {
			this.logicOperatorMap = standardLogicOperatorProxy
					.getLogicOperatorMap(lanKey);
		}
		return this.logicOperatorMap;
	}

	public Map<Integer, String> initSwitchFlagMap()
			throws ServiceEntityInstallationException {
		if (this.switchFlagMap == null) {
			this.switchFlagMap = standardSwitchProxy.getSwitchMap();
		}
		return this.switchFlagMap;
	}

	public Map<Integer, String> initDataOffsetUnitHighMap()
			throws ServiceEntityInstallationException {
		if (this.dataOffsetUnitHighMap == null) {
			this.dataOffsetUnitHighMap = serviceDropdownListHelper
					.getUIDropDownMap(ServiceDocConfigureParaUIModel.class,
							"dataOffsetUnitHigh");
		}
		return this.dataOffsetUnitHighMap;
	}
	
	public Map<Integer, String> initInputConsValueModeMap() throws ServiceEntityInstallationException{
		if(this.inputConsValueModeMap == null){
			this.inputConsValueModeMap = new HashMap<>();
			this.inputConsValueModeMap
					.put(ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE,
							serviceDocParaConsumerValueModeProxy
							.getValueModeLabel(ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE));
			this.inputConsValueModeMap
			.put(ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE,
					serviceDocParaConsumerValueModeProxy
					.getValueModeLabel(ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE));
			this.inputConsValueModeMap
			.put(ServiceDocConfigurePara.INPUT_VALUEMODE_COMDATASOURCE,
					serviceDocParaConsumerValueModeProxy
					.getValueModeLabel(ServiceDocConfigurePara.INPUT_VALUEMODE_COMDATASOURCE));
		}
		return this.inputConsValueModeMap;
		
	}
	

	public Map<Integer, String> initOutputConsValueModeMap() throws ServiceEntityInstallationException{
		if(this.outputConsValueModeMap == null){
			this.outputConsValueModeMap = new HashMap<>();
			this.outputConsValueModeMap
					.put(ServiceDocConfigurePara.OUTPUT_VALUEMODE_PASSVALUE,
							serviceDocParaConsumerValueModeProxy
							.getValueModeLabel(ServiceDocConfigurePara.OUTPUT_VALUEMODE_PASSVALUE));
			this.outputConsValueModeMap
			.put(ServiceDocConfigurePara.OUTPUT_VALUEMODE_MANUALOUT,
					serviceDocParaConsumerValueModeProxy
					.getValueModeLabel(ServiceDocConfigurePara.OUTPUT_VALUEMODE_MANUALOUT));			
		}
		return this.outputConsValueModeMap;
		
	}
	

	/**
	 * Core Logic to check condition to set Switch on
	 * 
	 * @param serviceDocConfigure
	 * @param serviceDocConfigureParaList
	 * @throws ServiceDocConfigureException
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceDocActiveMessageItem> checkSetSwitchOn(
			ServiceDocConfigure serviceDocConfigure,
			List<ServiceEntityNode> serviceDocConfigureParaList)
			throws ServiceDocConfigureException,
			ServiceEntityConfigureException {
		List<ServiceDocActiveMessageItem> messageItemList = new ArrayList<ServiceDocActiveMessageItem>();
		/**
		 * [Step1] Data preparation
		 */
		if (ServiceCollectionsHelper.checkNullList(serviceDocConfigureParaList)) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOTFOUND_INPUTPARA,
					serviceDocConfigure.getName());
		}
		ServiceDocConfigParaProxy serviceDocConfigParaProxy = serviceDocConfigParaFactory
				.getDocConfigProxy(serviceDocConfigure.getResourceID());
		if (serviceDocConfigParaProxy == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOT_IMPLEMENTED,
					serviceDocConfigure.getResourceDocType());
		}
		List<ServiceDocConfigParaUnion> rawInputParaList = serviceDocConfigParaProxy
				.getDocInputPara();
		List<ServiceDocConfigParaUnion> rawOutputParaList = serviceDocConfigParaProxy
				.getDocOutputPara();
		ServiceDocConsumerUnion serviceDocConsumerUnion = (ServiceDocConsumerUnion) serviceDocConsumerUnionManager
				.getEntityNodeByKey(serviceDocConfigure.getConsumerUnionUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						ServiceDocConsumerUnion.NODENAME,
						serviceDocConfigure.getClient(), null);
		String uiModelTypeFullName = serviceDocConsumerUnion
				.getUiModelTypeFullName();
		Class<?> consumerModelType = null;
		try {
			consumerModelType = Class.forName(uiModelTypeFullName);
		} catch (ClassNotFoundException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
		if (consumerModelType == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					"NULL consumer type");
		}
		for (ServiceEntityNode seNode : serviceDocConfigureParaList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getSwitchFlag() == StandardSwitchProxy.SWITCH_ON) {
				if (serviceDocConfigurePara.getParaDirection() == ServiceDocConfigParaUnion.DIRECT_INPUT) {
					List<ServiceDocActiveMessageItem> localMessageItems = checkSetSwitchOnInputParaUnion(
							rawInputParaList, consumerModelType,
							serviceDocConfigurePara);
					if (!ServiceCollectionsHelper
							.checkNullList(localMessageItems)) {
						messageItemList.addAll(localMessageItems);
					}
				} else {
					List<ServiceDocActiveMessageItem> localMessageItems = checkSetSwitchOnOutputParaUnion(
							rawOutputParaList, consumerModelType,
							serviceDocConfigurePara);
					if (!ServiceCollectionsHelper
							.checkNullList(localMessageItems)) {
						messageItemList.addAll(localMessageItems);
					}
				}
			}
		}		
		return messageItemList;
	}

	/**
	 * [Internal method] check input parameter for set switch on
	 * 
	 * @param rawInputParaList
	 * @param consumerModelType
	 * @param serviceDocConfigurePara
	 * @return
	 */
	protected List<ServiceDocActiveMessageItem> checkSetSwitchOnInputParaUnion(
			List<ServiceDocConfigParaUnion> rawInputParaList,
			Class<?> consumerModelType,
			ServiceDocConfigurePara serviceDocConfigurePara) {
		List<ServiceDocActiveMessageItem> messageItemList = new ArrayList<ServiceDocActiveMessageItem>();
		ServiceDocConfigParaUnion paraUnion = this.filterOutParaUnionOnline(
				rawInputParaList,
				serviceDocConfigurePara.getResourceFieldName());
		// Check if this para is input para
		if (serviceDocConfigurePara.getParaDirection() != ServiceDocConfigParaUnion.DIRECT_INPUT) {
			return null;
		}
		if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE) {
			/**
			 * [Step1] Check null consumer field name
			 */
			if (ServiceEntityStringHelper
					.checkNullString(serviceDocConfigurePara
							.getConsumerFieldName())) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem.setMessageContent(serviceDocConfigurePara
						.getResourceFieldName() + " 对应的消费字段为空");
				messageItemList.add(messageItem);
				return messageItemList;
			}
			/**
			 * [Step2] Check if the type matches
			 */
			try {
				Field consumerField = ServiceEntityFieldsHelper
						.getServiceField(consumerModelType,
								serviceDocConfigurePara.getConsumerFieldName());
				if (consumerField == null) {
					ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
					messageItem
							.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
					messageItem.setMessageContent(serviceDocConfigurePara
							.getResourceFieldName() + " 无法找到消费字段");
					messageItemList.add(messageItem);
				}
				if (consumerField
						.getType()
						.getSimpleName()
						.equalsIgnoreCase(
								paraUnion.getFieldTypeClass().getSimpleName())) {

				} else {
					ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
					messageItem
							.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
					messageItem.setMessageContent("字段类型无法匹配："
							+ serviceDocConfigurePara.getResourceFieldName()
							+ " 和 "
							+ serviceDocConfigurePara.getConsumerFieldName());
					messageItemList.add(messageItem);
				}
			} catch (IllegalArgumentException e) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem
						.setMessageContent(serviceDocConfigurePara
								.getResourceFieldName()
								+ " 无法找到消费字段："
								+ e.getMessage());
				messageItemList.add(messageItem);
			} catch (IllegalAccessException e) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem
						.setMessageContent(serviceDocConfigurePara
								.getResourceFieldName()
								+ " 无法找到消费字段："
								+ e.getMessage());
				messageItemList.add(messageItem);
			} catch (NoSuchFieldException e) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem
						.setMessageContent(serviceDocConfigurePara
								.getResourceFieldName()
								+ " 无法找到消费字段："
								+ e.getMessage());
				messageItemList.add(messageItem);
			}
			return messageItemList;
		}
		if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE) {
			/**
			 * [Step3] Check for manual set value, if input value is null
			 */
			Object fixValue = serviceDocConfigureResourceManager
					.getFixValueUnion(paraUnion, serviceDocConfigurePara, false);
			if (fixValue == null
					|| fixValue.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_WARNING);
				messageItem.setMessageContent(serviceDocConfigurePara
						.getResourceFieldName() + " 设置的固定值为空值");
				messageItemList.add(messageItem);
			}
		}
		return messageItemList;
	}

	/**
	 * [Internal method] check output parameter for set switch on
	 * 
	 * @param rawInputParaList
	 * @param consumerModelType
	 * @param serviceDocConfigurePara
	 * @return
	 */
	protected List<ServiceDocActiveMessageItem> checkSetSwitchOnOutputParaUnion(
			List<ServiceDocConfigParaUnion> rawInputParaList,
			Class<?> consumerModelType,
			ServiceDocConfigurePara serviceDocConfigurePara) {
		List<ServiceDocActiveMessageItem> messageItemList = new ArrayList<ServiceDocActiveMessageItem>();
		ServiceDocConfigParaUnion paraUnion = this.filterOutParaUnionOnline(
				rawInputParaList,
				serviceDocConfigurePara.getResourceFieldName());
		// Check if this para is input para
		if (serviceDocConfigurePara.getParaDirection() != ServiceDocConfigParaUnion.DIRECT_OUTPUT) {
			return null;
		}
		if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.OUTPUT_VALUEMODE_PASSVALUE) {

			/**
			 * [Step1] Check null consumer field name
			 */
			if (ServiceEntityStringHelper
					.checkNullString(serviceDocConfigurePara
							.getConsumerFieldName())) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem.setMessageContent(serviceDocConfigurePara
						.getResourceFieldName() + " 对应的消费字段为空");
				messageItemList.add(messageItem);
				return messageItemList;
			}
			/**
			 * [Step2] Check if the type matches
			 */
			try {
				Field consumerField = ServiceEntityFieldsHelper
						.getServiceField(consumerModelType,
								serviceDocConfigurePara.getConsumerFieldName());
				if (consumerField == null) {
					ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
					messageItem
							.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
					messageItem.setMessageContent(serviceDocConfigurePara
							.getResourceFieldName() + " 无法找到消费字段");
					messageItemList.add(messageItem);
				}
				if (consumerField
						.getType()
						.getSimpleName()
						.equalsIgnoreCase(
								paraUnion.getFieldTypeClass().getSimpleName())) {

				} else {
					ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
					messageItem
							.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
					messageItem.setMessageContent("字段类型无法匹配："
							+ serviceDocConfigurePara.getResourceFieldName()
							+ " 和 "
							+ serviceDocConfigurePara.getConsumerFieldName());
					messageItemList.add(messageItem);
				}
			} catch (IllegalArgumentException e) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem
						.setMessageContent(serviceDocConfigurePara
								.getResourceFieldName()
								+ " 无法找到消费字段："
								+ e.getMessage());
				messageItemList.add(messageItem);
			} catch (IllegalAccessException e) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem
						.setMessageContent(serviceDocConfigurePara
								.getResourceFieldName()
								+ " 无法找到消费字段："
								+ e.getMessage());
				messageItemList.add(messageItem);
			} catch (NoSuchFieldException e) {
				ServiceDocActiveMessageItem messageItem = new ServiceDocActiveMessageItem();
				messageItem.setErrorType(LogonUserMessage.MESSAGE_TYPE_ERROR);
				messageItem
						.setMessageContent(serviceDocConfigurePara
								.getResourceFieldName()
								+ " 无法找到消费字段："
								+ e.getMessage());
				messageItemList.add(messageItem);
			}
			return messageItemList;
		}
		return messageItemList;
	}

	/**
	 * Init new ServiceDocConfigure instance
	 * 
	 * @param serviceDocInitConfigureModel
	 * @param client
	 * @return
	 * @throws ServiceDocConfigureException
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceDocConfigure newServiceDocConfigure(
			ServiceDocInitConfigureModel serviceDocInitConfigureModel,
			String client) throws ServiceDocConfigureException,
			ServiceEntityConfigureException {
		int documentType = serviceDocInitConfigureModel.getDocumentType();
		ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) newRootEntityNode(client);
		serviceDocConfigure.setResourceDocType(documentType);
		serviceDocConfigure.setResourceID(serviceDocInitConfigureModel
				.getResourceID());
		serviceDocConfigure.setInputUnionUUID(serviceDocInitConfigureModel
				.getRefInputUnionUUID());
		serviceDocConfigure.setConsumerUnionUUID(serviceDocInitConfigureModel
				.getRefConsumerUnionUUID());
		return serviceDocConfigure;
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) super
				.newRootEntityNode(client);
		String defaultID = serviceDocConfigureIdHelper.genDefaultId(client);
		serviceDocConfigure.setId(defaultID);
		return serviceDocConfigure;
	}

	/**
	 * Init and create default service doc configure
	 * 
	 * @param serviceDocConfigure
	 * @return
	 * @throws ServiceDocConfigureException
	 * @throws ServiceEntityConfigureException
	 * @throws IOException
	 */
	public ServiceDocConfigureParaGroup initServiceDocConfigureParaGroup(
			ServiceDocConfigure serviceDocConfigure)
			throws ServiceDocConfigureException,
			ServiceEntityConfigureException, IOException {
		ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) newEntityNode(
				serviceDocConfigure, ServiceDocConfigureParaGroup.NODENAME);
		// Set default ID, name and operator
		serviceDocConfigureParaGroup
				.setId(ServiceDocConfigureParaGroup.GROUPID_DEFAULT);
		Map<String, String> preWarnMap = getPreWarnMap();
		String defaultGroupName = getPreWarnMsg("defaultGroupName", preWarnMap);
		serviceDocConfigureParaGroup.setName(defaultGroupName);
		serviceDocConfigureParaGroup
				.setLogicOperator(StandardLogicOperatorProxy.OPERATOR_AND);
		serviceDocConfigureParaGroup.setLayer(1);
		serviceDocConfigureParaGroup
				.setRefParentGroupUUID(ServiceEntityStringHelper.EMPTYSTRING);
		return serviceDocConfigureParaGroup;
	}

	protected String getPreWarnMsg(String key, Map<String, String> preWarnMap) {
		return preWarnMap.get(key);
	}

	public Map<String, String> getPreWarnMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = ServiceDocConfigureUIModel.class.getResource("")
				.getPath();
		String resFileName = ServiceDocConfigure.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	public List<ServiceEntityNode> newInitInputParaList(String resourceID,
			ServiceDocConfigure serviceDocConfigure, String defaultGroupUUID)
			throws ServiceDocConfigureException,
			ServiceEntityInstallationException, ServiceEntityConfigureException {
		ServiceDocConfigParaProxy serviceDocConfigParaProxy = serviceDocConfigParaFactory
				.getDocConfigProxy(resourceID);
		if (serviceDocConfigParaProxy == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOT_IMPLEMENTED,
					resourceID);
		}
		List<ServiceDocConfigParaUnion> rawInputParaList = serviceDocConfigParaProxy
				.getDocInputPara();
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (rawInputParaList != null && rawInputParaList.size() > 0) {
			for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawInputParaList) {
				ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) newEntityNode(
						serviceDocConfigure, ServiceDocConfigurePara.NODENAME);
				initConvDocConfigurePara(serviceDocConfigParaUnion,
						serviceDocConfigurePara, defaultGroupUUID);
				resultList.add(serviceDocConfigurePara);
			}
		}
		return resultList;
	}

	/**
	 * Generate consumer field map
	 * 
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public Map<String, String> generateConsumerFieldMap(
			ServiceDocConsumerUnion serviceDocConsumerUnion, int paraDirection)
			throws ServiceDocConfigureException {
		String uiModelTypeFullName = serviceDocConsumerUnion
				.getUiModelTypeFullName();
		if (ServiceEntityStringHelper.checkNullString(uiModelTypeFullName)) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOTFOUND_CONSUMERTYPE,
					serviceDocConsumerUnion.getId());
		}
		try {
			Class<?> consumerModelClass = Class.forName(uiModelTypeFullName);
			// Traverse and check all field could be used for export and import
			List<Field> uiFields = SEUIModelFieldsHelper
					.getFieldsList(consumerModelClass);
			Map<String, String> resultMap = new HashMap<String, String>();
			Map<String, String> i18nMap = null;
			ISEUIModelHeader uiModelHeader = consumerModelClass
					.getAnnotation(ISEUIModelHeader.class);
			if (uiModelHeader != null) {
				String path = consumerModelClass.getResource("").getPath();
				try {
					i18nMap = serviceDropdownListHelper.getDropDownMap(path,
							uiModelHeader.i18nFileName(), ServiceLanHelper.getDefault());
				} catch (IOException e) {
					// Skip
				}
			}
			if (uiFields != null && uiFields.size() > 0) {
				for (Field field : uiFields) {
					ISEUIModelMapping uiModelMapping = field
							.getAnnotation(ISEUIModelMapping.class);
					if (uiModelMapping != null) {
						String fieldName = field.getName();
						if (paraDirection == ServiceDocConfigParaUnion.DIRECT_INPUT
								&& uiModelMapping.exportParaFlag()) {
							String fieldLabel = getFieldLabel(fieldName,
									i18nMap);
							if (ServiceEntityStringHelper
									.checkNullString(fieldLabel)) {
								resultMap.put(fieldName, fieldName + "-"
										+ fieldLabel);
							} else {
								resultMap.put(fieldName, fieldName);
							}
						}
						if (paraDirection == ServiceDocConfigParaUnion.DIRECT_OUTPUT
								&& uiModelMapping.importParaFlag()) {
							String fieldLabel = getFieldLabel(field.getName(),
									i18nMap);
							if (ServiceEntityStringHelper
									.checkNullString(fieldLabel)) {
								resultMap.put(fieldName, fieldName + "-"
										+ fieldLabel);
							} else {
								resultMap.put(fieldName, fieldName);
							}
						}
					}
				}
				return resultMap;
			}
			return null;
		} catch (ClassNotFoundException ex) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOTFOUND_CONSUMERTYPE,
					uiModelTypeFullName);
		}
	}

	public String getFieldLabel(String fieldName, Map<String, String> i18nMap) {
		if (i18nMap != null && i18nMap.containsKey(fieldName)) {
			return i18nMap.get(fieldName);
		} else {
			return fieldName;
		}
	}

	/**
	 * 
	 * @param rawServiceDocParaList
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public ServiceDocConfigParaUnion getDocParaUnion(
			List<ServiceEntityNode> rawServiceDocParaList, String fieldName,
			String resoruceID) throws ServiceDocConfigureException {
		if (ServiceEntityStringHelper.checkNullString(fieldName)) {
			return null;
		}
		ServiceDocConfigParaProxy serviceDocConfigParaProxy = serviceDocConfigParaFactory
				.getDocConfigProxy(resoruceID);
		if (serviceDocConfigParaProxy == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOT_IMPLEMENTED,
					resoruceID);
		}
		List<ServiceDocConfigParaUnion> rawInputUnionParaList = serviceDocConfigParaProxy
				.getDocInputPara();
		if (rawInputUnionParaList != null && rawInputUnionParaList.size() > 0) {
			for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawInputUnionParaList) {
				if (fieldName.equals(serviceDocConfigParaUnion.getFieldName())) {
					return serviceDocConfigParaUnion;
				}
			}
		}
		List<ServiceDocConfigParaUnion> rawOutputParaList = serviceDocConfigParaProxy
				.getDocOutputPara();
		if (rawOutputParaList != null && rawOutputParaList.size() > 0) {
			for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawOutputParaList) {
				if (fieldName.equals(serviceDocConfigParaUnion.getFieldName())) {
					return serviceDocConfigParaUnion;
				}
			}
		}
		return null;
	}

	public ServiceDocConfigParaUnion filterOutParaUnionOnline(
			List<ServiceDocConfigParaUnion> rawUnionParaList, String fieldName) {
		if (ServiceEntityStringHelper.checkNullString(fieldName)) {
			return null;
		}
		if (rawUnionParaList != null && rawUnionParaList.size() > 0) {
			for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawUnionParaList) {
				if (fieldName.equals(serviceDocConfigParaUnion.getFieldName())) {
					return serviceDocConfigParaUnion;
				}
			}
		}
		return null;
	}

	public List<ServiceEntityNode> newInitOutputParaList(String resourceID,
			ServiceDocConfigure serviceDocConfigure, String defaultGroupUUID)
			throws ServiceDocConfigureException,
			ServiceEntityInstallationException, ServiceEntityConfigureException {
		ServiceDocConfigParaProxy serviceDocConfigParaProxy = serviceDocConfigParaFactory
				.getDocConfigProxy(resourceID);
		List<ServiceDocConfigParaUnion> rawOutputParaList = serviceDocConfigParaProxy
				.getDocOutputPara();
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (rawOutputParaList != null && rawOutputParaList.size() > 0) {
			for (ServiceDocConfigParaUnion serviceDocConfigParaUnion : rawOutputParaList) {
				ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) newEntityNode(
						serviceDocConfigure, ServiceDocConfigurePara.NODENAME);
				initConvDocConfigurePara(serviceDocConfigParaUnion,
						serviceDocConfigurePara, defaultGroupUUID);
				resultList.add(serviceDocConfigurePara);
			}
		}
		return resultList;
	}

	/**
	 * Converting from proxy para info to UI
	 * 
	 * @param serviceDocConfigParaUnion
	 * @param serviceDocConfigurePara
	 * @throws ServiceEntityInstallationException
	 */
	public void initConvDocConfigurePara(
			ServiceDocConfigParaUnion serviceDocConfigParaUnion,
			ServiceDocConfigurePara serviceDocConfigurePara,
			String defaultGroupUUID) throws ServiceEntityInstallationException {
		if (serviceDocConfigurePara != null && serviceDocConfigurePara != null) {
			serviceDocConfigurePara
					.setResourceFieldName(serviceDocConfigParaUnion
							.getFieldName());
			serviceDocConfigurePara
					.setResourceFieldLabel(serviceDocConfigParaUnion
							.getFieldLabel());
			// Default switch is ON
			serviceDocConfigurePara
					.setSwitchFlag(StandardSwitchProxy.SWITCH_ON);
			// Default logic operator for each new para is "AND"
			serviceDocConfigurePara
					.setLogicOperator(StandardLogicOperatorProxy.OPERATOR_AND);
			serviceDocConfigurePara
					.setConsumerValueMode(serviceDocConfigParaUnion
							.getValueMode());
			serviceDocConfigurePara.setParaDirection(serviceDocConfigParaUnion
					.getParaDirection());
			serviceDocConfigurePara
					.setResourceFieldName(serviceDocConfigParaUnion
							.getFieldName());
			serviceDocConfigurePara.setRefGroupUUID(defaultGroupUUID);
		}
	}

	/**
	 * Converting Data Area
	 */

	/**
	 * [Internal method] Convert from UI model to se model:serviceDocConfigure
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToServiceDocConfigure(
			ServiceDocConfigureUIModel serviceDocConfigureUIModel,
			ServiceDocConfigure rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(serviceDocConfigureUIModel.getUuid())) {
			rawEntity.setUuid(serviceDocConfigureUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceDocConfigureUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(serviceDocConfigureUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceDocConfigureUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(serviceDocConfigureUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(serviceDocConfigureUIModel.getClient())) {
			rawEntity.setClient(serviceDocConfigureUIModel.getClient());
		}
		rawEntity.setId(serviceDocConfigureUIModel.getId());
		rawEntity.setConsumerUnionUUID(serviceDocConfigureUIModel
				.getConsumerUnionUUID());
		rawEntity.setInputUnionUUID(serviceDocConfigureUIModel
				.getInputUnionUUID());
		rawEntity.setSwitchFlag(serviceDocConfigureUIModel.getSwitchFlag());
		rawEntity.setName(serviceDocConfigureUIModel.getName());
		rawEntity.setUuid(serviceDocConfigureUIModel.getUuid());
		rawEntity.setResourceDocType(serviceDocConfigureUIModel
				.getResourceDocType());
		rawEntity.setClient(serviceDocConfigureUIModel.getClient());
		rawEntity.setRefSearchProxyUUID(serviceDocConfigureUIModel.getRefSearchProxyUUID());
		rawEntity.setResourceID(serviceDocConfigureUIModel.getResourceID());
		rawEntity.setNote(serviceDocConfigureUIModel.getNote());
	}

	/**
	 * Generate the para UI List to show on UI by raw doc para list and
	 * paraDirection
	 * 
	 * @param rawServiceDocParaList
	 * @param paraDirection
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public List<ServiceDocConfigureParaUIModel> convServiceDocConfigureParaUIList(
			List<ServiceEntityNode> rawServiceDocParaList, int paraDirection,
			String resourceID) throws ServiceDocConfigureException {
		if (ServiceCollectionsHelper.checkNullList(rawServiceDocParaList)) {
			return null;
		}
		List<ServiceDocConfigureParaUIModel> resultList = new ArrayList<ServiceDocConfigureParaUIModel>();
		for (ServiceEntityNode seNode : rawServiceDocParaList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getParaDirection() == paraDirection) {
				ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel = new ServiceDocConfigureParaUIModel();
				ServiceDocConfigParaUnion serviceDocConfigParaUnion = getDocParaUnion(
						rawServiceDocParaList,
						serviceDocConfigurePara.getResourceFieldName(),
						resourceID);
				convServiceDocConfigureParaToUI(serviceDocConfigurePara,
						serviceDocConfigureParaUIModel);
				convServiceDocConfigParaUnionToUI(serviceDocConfigParaUnion,
						serviceDocConfigureParaUIModel);
				resultList.add(serviceDocConfigureParaUIModel);
			}
		}
		return resultList;
	}

	/**
	 * Generate the para group UI List
	 * 
	 * @param rawServiceDocParaGroupList
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public List<ServiceDocConfigureParaGroupUIModel> convServiceDocConfigureParaGroupUIList(
			List<ServiceEntityNode> rawServiceDocParaGroupList)
			throws ServiceEntityInstallationException {
		if (ServiceCollectionsHelper.checkNullList(rawServiceDocParaGroupList)) {
			return null;
		}
		List<ServiceDocConfigureParaGroupUIModel> resultList = new ArrayList<ServiceDocConfigureParaGroupUIModel>();
		for (ServiceEntityNode seNode : rawServiceDocParaGroupList) {
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup = (ServiceDocConfigureParaGroup) seNode;
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel = new ServiceDocConfigureParaGroupUIModel();
			convServiceDocConfigureParaGroupToUI(serviceDocConfigureParaGroup,
					serviceDocConfigureParaGroupUIModel);
			resultList.add(serviceDocConfigureParaGroupUIModel);
		}
		return resultList;
	}

	public void convUIToServiceDocConfigurePara(
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel,
			ServiceDocConfigurePara serviceDocConfigurePara) {
		if (serviceDocConfigureParaUIModel != null
				&& serviceDocConfigurePara != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaUIModel.getUuid())) {
				serviceDocConfigurePara.setUuid(serviceDocConfigureParaUIModel
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaUIModel
							.getParentNodeUUID())) {
				serviceDocConfigurePara
						.setParentNodeUUID(serviceDocConfigureParaUIModel
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaUIModel
							.getRootNodeUUID())) {
				serviceDocConfigurePara
						.setRootNodeUUID(serviceDocConfigureParaUIModel
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaUIModel.getClient())) {
				serviceDocConfigurePara
						.setClient(serviceDocConfigureParaUIModel.getClient());
			}
			if (serviceDocConfigureParaUIModel.getParaDirection() != 0) {
				serviceDocConfigurePara
						.setParaDirection(serviceDocConfigureParaUIModel
								.getParaDirection());
			}
			if (serviceDocConfigureParaUIModel.getSwitchFlag() != 0) {
				serviceDocConfigurePara
						.setSwitchFlag(serviceDocConfigureParaUIModel
								.getSwitchFlag());
			}
			serviceDocConfigurePara
					.setConsumerFieldName(serviceDocConfigureParaUIModel
							.getConsumerFieldName());
			if (serviceDocConfigureParaUIModel.getFixValue() != null) {
				serviceDocConfigurePara
						.setFixValue(serviceDocConfigureParaUIModel
								.getFixValue().toString());
			}
			serviceDocConfigurePara
					.setFixValueOperator(serviceDocConfigureParaUIModel
							.getFixValueOperator());
			serviceDocConfigurePara
					.setFixValueInt(serviceDocConfigureParaUIModel
							.getFixValueInt());
			serviceDocConfigurePara
					.setFixValueDouble(serviceDocConfigureParaUIModel
							.getFixValueDouble());			
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaUIModel
							.getFixValueDate())) {
				try {
					serviceDocConfigurePara
							.setFixValueDate(DefaultDateFormatConstant.DATE_FORMAT
									.parse(serviceDocConfigureParaUIModel
											.getFixValueDate()));
				} catch (ParseException e) {
					// just continue;
				}
			}
			
			serviceDocConfigurePara
					.setFixValueHigh(serviceDocConfigureParaUIModel
							.getFixValueHigh());
			serviceDocConfigurePara
					.setFixValueIntHigh(serviceDocConfigureParaUIModel
							.getFixValueIntHigh());
			serviceDocConfigurePara
					.setFixValueDoubleHigh(serviceDocConfigureParaUIModel
							.getFixValueDoubleHigh());
			if (serviceDocConfigureParaUIModel.getConsumerValueMode() != 0) {
				serviceDocConfigurePara
						.setConsumerValueMode(serviceDocConfigureParaUIModel
								.getConsumerValueMode());
			}
			serviceDocConfigurePara
					.setRefGroupUUID(serviceDocConfigureParaUIModel
							.getRefGroupUUID());
			serviceDocConfigurePara
					.setLogicOperator(serviceDocConfigureParaUIModel
							.getLogicOperator());
		}
	}

	public void convServiceDocConfigureParaToUI(
			ServiceDocConfigurePara serviceDocConfigurePara,
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel){
		convServiceDocConfigureParaToUI(serviceDocConfigurePara, serviceDocConfigureParaUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceDocConfigureParaToUI(
			ServiceDocConfigurePara serviceDocConfigurePara,
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel, LogonInfo logonInfo) {
		if (serviceDocConfigurePara != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigurePara.getUuid())) {
				serviceDocConfigureParaUIModel.setUuid(serviceDocConfigurePara
						.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigurePara
							.getParentNodeUUID())) {
				serviceDocConfigureParaUIModel
						.setParentNodeUUID(serviceDocConfigurePara
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigurePara.getRootNodeUUID())) {
				serviceDocConfigureParaUIModel
						.setRootNodeUUID(serviceDocConfigurePara
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigurePara.getClient())) {
				serviceDocConfigureParaUIModel
						.setClient(serviceDocConfigurePara.getClient());
			}
			serviceDocConfigureParaUIModel
					.setResourceFieldLabel(serviceDocConfigurePara
							.getResourceFieldLabel());
			serviceDocConfigureParaUIModel
					.setDataOffsetUnit(serviceDocConfigurePara
							.getDataOffsetUnit());
			serviceDocConfigureParaUIModel
					.setSwitchFlag(serviceDocConfigurePara.getSwitchFlag());
			serviceDocConfigureParaUIModel
					.setFixValueIntHigh(serviceDocConfigurePara
							.getFixValueIntHigh());
			serviceDocConfigureParaUIModel
					.setFixValueDouble(serviceDocConfigurePara
							.getFixValueDouble());
			serviceDocConfigureParaUIModel
					.setDataOffsetUnitIntHigh(serviceDocConfigurePara
							.getDataOffsetUnitIntHigh());
			serviceDocConfigureParaUIModel
					.setConsumerValueMode(serviceDocConfigurePara
							.getConsumerValueMode());
			serviceDocConfigureParaUIModel
					.setDataOffsetValue(serviceDocConfigurePara
							.getDataOffsetValue());
			serviceDocConfigureParaUIModel
			.setParaDirection(serviceDocConfigurePara
					.getParaDirection());
			try {
				this.initParaDirectionMap();
				serviceDocConfigureParaUIModel
				.setParaDirectionValue(this.paraDirectionMap
						.get(serviceDocConfigurePara.getParaDirection()));
			} catch (ServiceEntityInstallationException e1) {
				// just continue;
			}
			
			serviceDocConfigureParaUIModel
					.setRefGroupUUID(serviceDocConfigurePara.getRefGroupUUID());
			serviceDocConfigureParaUIModel
					.setFixValueDoubleHigh(serviceDocConfigurePara
							.getFixValueDoubleHigh());
			serviceDocConfigureParaUIModel
			.setFixValueOperator(serviceDocConfigurePara
					.getFixValueOperator());
			try {
				this.initFixValueOperatorMap();
				
				serviceDocConfigureParaUIModel
				.setFixValueOperatorValue(this.fixValueOperatorMap
						.get(serviceDocConfigurePara.getFixValueOperator()));
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
			serviceDocConfigureParaUIModel
					.setFixValueHigh(serviceDocConfigurePara.getFixValueHigh());
//			serviceDocConfigureParaUIModel.setName(serviceDocConfigurePara
//					.getName());
			if (serviceDocConfigurePara.getFixValueDateHigh() != null) {
				serviceDocConfigureParaUIModel
						.setFixValueDateHigh(DefaultDateFormatConstant.DATE_FORMAT
								.format(serviceDocConfigurePara
										.getFixValueDateHigh()));
			}
			serviceDocConfigureParaUIModel
					.setDataOffsetDirectionHigh(serviceDocConfigurePara
							.getDataOffsetDirectionHigh());
			serviceDocConfigureParaUIModel
					.setResourceFieldName(serviceDocConfigurePara
							.getResourceFieldName());
			serviceDocConfigureParaUIModel
					.setDataOffsetUnitInt(serviceDocConfigurePara
							.getDataOffsetUnitInt());
			if (serviceDocConfigurePara.getFixValueDate() != null) {
				serviceDocConfigureParaUIModel
						.setFixValueDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(serviceDocConfigurePara
										.getFixValueDate()));
			}
			try {
				this.initDataOffsetDirectionMap();
				serviceDocConfigureParaUIModel
				.setDataOffsetDirectionValue(this.dataOffsetDirectionMap
						.get(serviceDocConfigurePara
								.getDataOffsetDirection()));
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
			serviceDocConfigureParaUIModel
					.setDataOffsetDirection(serviceDocConfigurePara
							.getDataOffsetDirection());
			serviceDocConfigureParaUIModel.setFixValue(serviceDocConfigurePara
					.getFixValue());
			serviceDocConfigureParaUIModel
					.setDataOffsetValueHigh(serviceDocConfigurePara
							.getDataOffsetValueHigh());
			try {
				if(logonInfo != null){
					Map<Integer, String> logicOperatorMap = this.initLogicOperatorMap(logonInfo.getLanguageCode());
					serviceDocConfigureParaUIModel
							.setLogicOperatorValue(logicOperatorMap
									.get(serviceDocConfigurePara.getLogicOperator()));
				}
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
			serviceDocConfigureParaUIModel
					.setLogicOperator(serviceDocConfigurePara
							.getLogicOperator());
			serviceDocConfigureParaUIModel
					.setConsumerFieldName(serviceDocConfigurePara
							.getConsumerFieldName());
			serviceDocConfigureParaUIModel
					.setFixValueInt(serviceDocConfigurePara.getFixValueInt());
			try {
				this.initDataOffsetUnitHighMap();
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
			serviceDocConfigureParaUIModel
					.setDataOffsetUnitHigh(serviceDocConfigurePara
							.getDataOffsetUnitHigh());
			try {
				if (ServiceDocConfigParaUnion.DIRECT_INPUT == serviceDocConfigurePara.getParaDirection()) {
					this.initInputConsValueModeMap();
					serviceDocConfigureParaUIModel.setConsumerValueModelLabel(this.inputConsValueModeMap
							.get(serviceDocConfigurePara.getConsumerValueMode()));
				} else {
					this.initOutputConsValueModeMap();
					serviceDocConfigureParaUIModel.setConsumerValueModelLabel(this.outputConsValueModeMap
							.get(serviceDocConfigurePara.getConsumerValueMode()));
				}				
			} catch (ServiceEntityInstallationException e1) {
				// just continue;
			}
		}
	}
	
	
	public void convConfigureToParaUI(
			ServiceDocConfigure serviceDocConfigure,
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel) {
		if (serviceDocConfigure != null) {
			serviceDocConfigureParaUIModel.setRefConfigureId(serviceDocConfigure.getId());
			serviceDocConfigureParaUIModel.setRefConfigureName(serviceDocConfigure.getName());
		}
	}
	
	public void convConfigureToParaGroupUI(
			ServiceDocConfigure serviceDocConfigure,
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel) {
		if (serviceDocConfigure != null) {
			serviceDocConfigureParaGroupUIModel.setRefConfigureId(serviceDocConfigure.getId());
			serviceDocConfigureParaGroupUIModel.setRefConfigureName(serviceDocConfigure.getName());
		}
	}

	public void convParntGroupToUI(ServiceDocConfigureParaGroup parentConfigureGroup,
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel){
		if (parentConfigureGroup != null) {
			serviceDocConfigureParaGroupUIModel.setRefParentGroupId(parentConfigureGroup.getId());
			serviceDocConfigureParaGroupUIModel.setRefParentGroupName(parentConfigureGroup.getName());
		}
		
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInputModuleToUI(ServiceDocConsumerUnion inputModule,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		if (inputModule != null) {
			if (!ServiceEntityStringHelper.checkNullString(inputModule
					.getClient())) {
				serviceDocConfigureUIModel.setClient(inputModule.getClient());
			}
			serviceDocConfigureUIModel
					.setInputUnionName(inputModule.getName());
//			serviceDocConfigureUIModel.setUiModelType(inputModule
//					.getUiModelType());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convOutputModuleToUI(ServiceDocConsumerUnion outputModule,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		if (outputModule != null) {
			serviceDocConfigureUIModel.setConsumerUnionName(outputModule.getName());
//			serviceDocConfigureUIModel.setOutputModuleUiModelType(outputModule
//					.getUiModelType());
//			serviceDocConfigureUIModel.setOutputModuleName(outputModule
//					.getName());
//			serviceDocConfigureUIModel.setOutputModuleId(outputModule.getId());
		}
	}

	public void convServiceDocConfigureParaToUI(
			ServiceDocConfigurePara serviceDocConfigurePara,
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel,
			Map<Integer, String> logicOperatorMap,
			Map<Integer, String> switchMap) {
		if (serviceDocConfigureParaUIModel != null
				&& serviceDocConfigurePara != null) {
			serviceDocConfigureParaUIModel
					.setParaDirection(serviceDocConfigurePara
							.getParaDirection());
			serviceDocConfigureParaUIModel
					.setSwitchFlag(serviceDocConfigurePara.getSwitchFlag());
			serviceDocConfigureParaUIModel
					.setConsumerValueMode(serviceDocConfigurePara
							.getConsumerValueMode());
			serviceDocConfigureParaUIModel
					.setOutputValueMode(serviceDocConfigurePara
							.getConsumerValueMode());
			serviceDocConfigureParaUIModel
					.setConsumerFieldName(serviceDocConfigurePara
							.getConsumerFieldName());
			serviceDocConfigureParaUIModel
					.setResourceFieldName(serviceDocConfigurePara
							.getResourceFieldName());
			serviceDocConfigureParaUIModel
					.setParentNodeUUID(serviceDocConfigurePara
							.getParentNodeUUID());
			serviceDocConfigureParaUIModel.setUuid(serviceDocConfigurePara
					.getUuid());
			serviceDocConfigureParaUIModel.setFixValue(serviceDocConfigurePara
					.getFixValue());
			serviceDocConfigureParaUIModel
					.setFixValueInt(serviceDocConfigurePara.getFixValueInt());
			serviceDocConfigureParaUIModel
					.setFixValueDouble(serviceDocConfigurePara
							.getFixValueDouble());
			if (serviceDocConfigurePara.getFixValueDateHigh() != null) {
				serviceDocConfigureParaUIModel
						.setFixValueDateHigh(DefaultDateFormatConstant.DATE_FORMAT
								.format(serviceDocConfigurePara
										.getFixValueDateHigh()));
			}
			if (serviceDocConfigurePara.getFixValueDate() != null) {
				serviceDocConfigureParaUIModel
						.setFixValueDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(serviceDocConfigurePara
										.getFixValueDate()));
			}			
			serviceDocConfigureParaUIModel
					.setFixValueHigh(serviceDocConfigurePara.getFixValueHigh());
			serviceDocConfigureParaUIModel
					.setFixValueIntHigh(serviceDocConfigurePara
							.getFixValueIntHigh());
			serviceDocConfigureParaUIModel
					.setFixValueDoubleHigh(serviceDocConfigurePara
							.getFixValueDoubleHigh());
			serviceDocConfigureParaUIModel
					.setRefGroupUUID(serviceDocConfigurePara.getRefGroupUUID());
			serviceDocConfigureParaUIModel
					.setFixValueOperator(serviceDocConfigurePara
							.getFixValueOperator());
			serviceDocConfigureParaUIModel
					.setLogicOperator(serviceDocConfigurePara
							.getLogicOperator());
			serviceDocConfigureParaUIModel
					.setLogicOperatorValue(logicOperatorMap
							.get(serviceDocConfigurePara.getLogicOperator()));
		}
	}

	public void convServiceDocConfigureGroupToParaUI(
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup,
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel) {
		if (serviceDocConfigureParaUIModel != null
				&& serviceDocConfigureParaGroup != null) {
			serviceDocConfigureParaUIModel
					.setRefGroupName(serviceDocConfigureParaGroup.getName());
			serviceDocConfigureParaUIModel
					.setRefGroupId(serviceDocConfigureParaGroup.getId());
			serviceDocConfigureParaUIModel
					.setRefGroupUUID(serviceDocConfigureParaGroup.getUuid());
		}
	}


	/**
	 * Additional convertion from para union to UI model
	 * 
	 * @param serviceDocConfigParaUnion
	 * @param serviceDocConfigureParaUIModel
	 */
	public void convServiceDocConfigParaUnionToUI(
			ServiceDocConfigParaUnion serviceDocConfigParaUnion,
			ServiceDocConfigureParaUIModel serviceDocConfigureParaUIModel) {
		if (serviceDocConfigParaUnion != null
				&& serviceDocConfigureParaUIModel != null) {
			Class<?> fieldTypeClass = serviceDocConfigParaUnion
					.getFieldTypeClass();
			if (fieldTypeClass != null) {
				serviceDocConfigureParaUIModel
						.setResourceFieldType(fieldTypeClass);
				serviceDocConfigureParaUIModel
						.setResourceFieldTypeLabel(fieldTypeClass
								.getSimpleName());
			}
			if (serviceDocConfigParaUnion.getDropdownMap() != null
					&& serviceDocConfigParaUnion.getDropdownMap().size() > 0) {
				serviceDocConfigureParaUIModel
						.setDropdownMap(serviceDocConfigParaUnion
								.getDropdownMap());
			}
		}
	}

	public void convServiceDocConfigureParaGroupToUI(
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup,
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel) {
		convServiceDocConfigureParaGroupToUI(serviceDocConfigureParaGroup, serviceDocConfigureParaGroupUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceDocConfigureParaGroupToUI(
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup,
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel, LogonInfo logonInfo) {
		if (serviceDocConfigureParaGroup != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroup.getUuid())) {
				serviceDocConfigureParaGroupUIModel
						.setUuid(serviceDocConfigureParaGroup.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroup
							.getParentNodeUUID())) {
				serviceDocConfigureParaGroupUIModel
						.setParentNodeUUID(serviceDocConfigureParaGroup
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroup
							.getRootNodeUUID())) {
				serviceDocConfigureParaGroupUIModel
						.setRootNodeUUID(serviceDocConfigureParaGroup
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroup.getClient())) {
				serviceDocConfigureParaGroupUIModel
						.setClient(serviceDocConfigureParaGroup.getClient());
			}
			serviceDocConfigureParaGroupUIModel
					.setLayer(serviceDocConfigureParaGroup.getLayer());
			try {
				if(logonInfo != null){
					Map<Integer, String> logicOperatorMap = this.initLogicOperatorMap(logonInfo.getLanguageCode());
					serviceDocConfigureParaGroupUIModel
							.setLogicOperatorLabel(logicOperatorMap
									.get(serviceDocConfigureParaGroup
											.getLogicOperator()));
				}
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
			serviceDocConfigureParaGroupUIModel
					.setLogicOperator(serviceDocConfigureParaGroup
							.getLogicOperator());

			serviceDocConfigureParaGroupUIModel
					.setRefParentGroupUUID(serviceDocConfigureParaGroup
							.getRefParentGroupUUID());
			serviceDocConfigureParaGroupUIModel
					.setGroupId(serviceDocConfigureParaGroup.getId());
			serviceDocConfigureParaGroupUIModel
					.setNote(serviceDocConfigureParaGroup.getNote());
			serviceDocConfigureParaGroupUIModel
					.setGroupName(serviceDocConfigureParaGroup.getName());
		}
	}

	public void convParentServiceDocConfigureParaGroupToUI(
			ServiceDocConfigureParaGroup parentGroup,
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel) {
		if (serviceDocConfigureParaGroupUIModel != null && parentGroup != null) {
			serviceDocConfigureParaGroupUIModel
					.setRefParentGroupName(parentGroup.getName());
			serviceDocConfigureParaGroupUIModel.setRefParentGroupId(parentGroup
					.getId());
			serviceDocConfigureParaGroupUIModel
					.setRefParentGroupUUID(parentGroup.getUuid());
		}
	}

	public void convUIToServiceDocConfigureParaGroup(
			ServiceDocConfigureParaGroupUIModel serviceDocConfigureParaGroupUIModel,
			ServiceDocConfigureParaGroup serviceDocConfigureParaGroup) {
		if (serviceDocConfigureParaGroupUIModel != null
				&& serviceDocConfigureParaGroup != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroupUIModel
							.getUuid())) {
				serviceDocConfigureParaGroup
						.setUuid(serviceDocConfigureParaGroupUIModel.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroupUIModel
							.getParentNodeUUID())) {
				serviceDocConfigureParaGroup
						.setParentNodeUUID(serviceDocConfigureParaGroupUIModel
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroupUIModel
							.getRootNodeUUID())) {
				serviceDocConfigureParaGroup
						.setRootNodeUUID(serviceDocConfigureParaGroupUIModel
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroupUIModel
							.getClient())) {
				serviceDocConfigureParaGroup
						.setClient(serviceDocConfigureParaGroupUIModel
								.getClient());
			}
			serviceDocConfigureParaGroup
					.setId(serviceDocConfigureParaGroupUIModel.getGroupId());
			serviceDocConfigureParaGroup
					.setName(serviceDocConfigureParaGroupUIModel.getGroupName());
			serviceDocConfigureParaGroup
					.setLogicOperator(serviceDocConfigureParaGroupUIModel
							.getLogicOperator());
			if (!ServiceEntityStringHelper
					.checkNullString(serviceDocConfigureParaGroupUIModel
							.getRefParentGroupUUID())) {
				serviceDocConfigureParaGroup
						.setRefParentGroupUUID(serviceDocConfigureParaGroupUIModel
								.getRefParentGroupUUID());
			}
			if (serviceDocConfigureParaGroupUIModel.getLayer() != 0) {
				serviceDocConfigureParaGroup
						.setLayer(serviceDocConfigureParaGroupUIModel
								.getLayer());
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convServiceDocConfigureToUI(
			ServiceDocConfigure serviceDocConfigure,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		if (serviceDocConfigure != null) {
			if (!ServiceEntityStringHelper.checkNullString(serviceDocConfigure
					.getUuid())) {
				serviceDocConfigureUIModel.setUuid(serviceDocConfigure
						.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceDocConfigure
					.getParentNodeUUID())) {
				serviceDocConfigureUIModel
						.setParentNodeUUID(serviceDocConfigure
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceDocConfigure
					.getRootNodeUUID())) {
				serviceDocConfigureUIModel.setRootNodeUUID(serviceDocConfigure
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(serviceDocConfigure
					.getClient())) {
				serviceDocConfigureUIModel.setClient(serviceDocConfigure
						.getClient());
			}
			serviceDocConfigureUIModel.setId(serviceDocConfigure.getId());
			serviceDocConfigureUIModel.setConsumerUnionUUID(serviceDocConfigure
					.getConsumerUnionUUID());
			serviceDocConfigureUIModel.setInputUnionUUID(serviceDocConfigure
					.getInputUnionUUID());
			serviceDocConfigureUIModel.setRefSearchProxyUUID(serviceDocConfigure.getRefSearchProxyUUID());
			serviceDocConfigureUIModel.setSwitchFlag(serviceDocConfigure
					.getSwitchFlag());
			try {
				this.initSwitchFlagMap();
				serviceDocConfigureUIModel.setSwitchFlagValue(this.switchFlagMap.get(serviceDocConfigure
					.getSwitchFlag()));
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
			serviceDocConfigureUIModel.setName(serviceDocConfigure.getName());
			serviceDocConfigureUIModel.setResourceDocType(serviceDocConfigure
					.getResourceDocType());
			serviceDocConfigureUIModel.setResourceID(serviceDocConfigure
					.getResourceID());
			serviceDocConfigureUIModel.setNote(serviceDocConfigure.getNote());
		}
	}

	public void convSearchProxyConfigToUI(SearchProxyConfig searchProxyConfig,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		if (searchProxyConfig != null) {
			serviceDocConfigureUIModel.setRefSearchProxyConfigId(searchProxyConfig.getId());
			serviceDocConfigureUIModel.setRefSearchProxyConfigName(searchProxyConfig.getName());
			serviceDocConfigureUIModel.setRefSearchModelName(searchProxyConfig.getSearchModelName());
			serviceDocConfigureUIModel.setRefSearchProxyName(searchProxyConfig.getSearchProxyName());
			serviceDocConfigureUIModel.setSearchDocType(searchProxyConfig.getDocumentType());
			Map<Integer, String> documentTypeMap;
			try {
				documentTypeMap = searchProxyConfigManager.initDocumentTypeMap();
				serviceDocConfigureUIModel.setSearchDocTypeValue(documentTypeMap.get(searchProxyConfig
						.getDocumentType()));
			} catch (ServiceEntityInstallationException e) {
				// just continue;
			}
		}
		
	}

	public void convServiceDocConfigureToUI(
			ServiceDocConfigure serviceDocConfigure,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel,
			Map<String, String> serviceResourceMap,
			Map<Integer, String> switchFlagMap) {
		if (serviceDocConfigureUIModel != null && serviceDocConfigure != null) {
			serviceDocConfigureUIModel.setUuid(serviceDocConfigure.getUuid());
			serviceDocConfigureUIModel.setRootNodeUUID(serviceDocConfigure
					.getRootNodeUUID());
			serviceDocConfigureUIModel.setParentNodeUUID(serviceDocConfigure
					.getParentNodeUUID());
			serviceDocConfigureUIModel.setClient(serviceDocConfigure
					.getClient());
			serviceDocConfigureUIModel.setId(serviceDocConfigure.getId());
			serviceDocConfigureUIModel.setName(serviceDocConfigure.getName());
			serviceDocConfigureUIModel.setNote(serviceDocConfigure.getNote());
			serviceDocConfigureUIModel.setResourceDocType(serviceDocConfigure
					.getResourceDocType());
			serviceDocConfigureUIModel.setSwitchFlag(serviceDocConfigure
					.getSwitchFlag());
			String switchFlagValue = switchFlagMap.get(serviceDocConfigure
					.getSwitchFlag());
			serviceDocConfigureUIModel.setSwitchFlagValue(switchFlagValue);
			String resourceName = serviceResourceMap.get(serviceDocConfigure
					.getResourceID());
			serviceDocConfigureUIModel.setResourceID(serviceDocConfigure
					.getResourceID());
			serviceDocConfigureUIModel.setResourceName(resourceName);
		}
	}

	public void convServiceDocConsumerUnionToUI(
			ServiceDocConsumerUnion serviceDocConsumerUnion,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		if (serviceDocConfigureUIModel != null
				&& serviceDocConsumerUnion != null) {
			serviceDocConfigureUIModel
					.setConsumerUnionName(serviceDocConsumerUnion.getName());
			serviceDocConfigureUIModel
					.setConsumerUnionUUID(serviceDocConsumerUnion.getUuid());
		}
	}

	public void convInputConsumerUnionToUI(
			ServiceDocConsumerUnion serviceDocConsumerUnion,
			ServiceDocConfigureUIModel serviceDocConfigureUIModel) {
		if (serviceDocConfigureUIModel != null
				&& serviceDocConsumerUnion != null) {
			serviceDocConfigureUIModel
					.setInputUnionName(serviceDocConsumerUnion.getName());
			serviceDocConfigureUIModel
					.setInputUnionUUID(serviceDocConsumerUnion.getUuid());
		}
	}

	public void adminDeleteServiceDocConfigureUnion(String baseUUID,
			String client) throws ServiceEntityConfigureException {
		ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) getEntityNodeByKey(
				baseUUID, IServiceEntityNodeFieldConstant.UUID,
				ServiceDocConfigure.NODENAME, client, null);
		List<ServiceEntityNode> serviceDocConfigureParaList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ServiceDocConfigurePara.NODENAME, client, null);
		List<ServiceEntityNode> serviceDocConfigureParaGroupList = getEntityNodeListByKey(
				baseUUID, IServiceEntityNodeFieldConstant.ROOTNODEUUID,
				ServiceDocConfigureParaGroup.NODENAME, client, null);
		if (serviceDocConfigure != null) {
			deleteSENode(serviceDocConfigure, null, null);
		}
		if (serviceDocConfigureParaList != null) {
			deleteSENode(serviceDocConfigureParaList, null, null);
		}
		if (serviceDocConfigureParaGroupList != null) {
			deleteSENode(serviceDocConfigureParaGroupList, null, null);
		}
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.ServiceDocConfigure;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return this.serviceDocConfigureSearchProxy;
	}

	
}
