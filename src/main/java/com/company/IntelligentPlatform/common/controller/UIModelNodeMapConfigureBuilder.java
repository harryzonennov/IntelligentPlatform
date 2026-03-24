package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

public class UIModelNodeMapConfigureBuilder {

	protected Class<? extends ServiceEntityNode> modelClass;

	protected Class<? extends SEUIComModel> uiModelClass;

	protected String seName;

	protected String nodeName;

	protected String nodeInstId;

	protected String baseNodeInstId;

	protected String mapFieldName;

	protected String mapBaseFieldName;

	protected int toBaseNodeType;

	protected boolean hostNodeFlag;

	protected ServiceEntityManager serviceEntityManager;

	/**
	 * where the data conversion logic should take place
	 */
	protected Object logicManager;

	protected String convToUIMethod;

	protected Class<?>[] convToUIMethodParas;

	protected String convUIToMethod;

	protected Class<?>[] convUIToMethodParas;

	protected List<SearchConfigConnectCondition> connectionConditions = new ArrayList<>();

	protected List<UIModelNodeConfigPreCondition> preConditions = new ArrayList<>();

	public UIModelNodeMapConfigureBuilder modelClass(Class<? extends ServiceEntityNode> modelClass) {
		this.modelClass = modelClass;
		return this;
	}

	public UIModelNodeMapConfigureBuilder uiModelClass(Class<? extends SEUIComModel> uiModelClass) {
		this.uiModelClass = uiModelClass;
		return this;
	}

	public UIModelNodeMapConfigureBuilder nodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
		return this;
	}

	public UIModelNodeMapConfigureBuilder baseNodeInstId(String baseNodeInstId) {
		this.baseNodeInstId = baseNodeInstId;
		return this;
	}

	public UIModelNodeMapConfigureBuilder mapField(String mapBaseFieldName, String mapFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
		this.mapFieldName = mapFieldName;
		return this;
	}

	public UIModelNodeMapConfigureBuilder mapFieldUUID(String mapBaseFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
		this.mapFieldName = IServiceEntityNodeFieldConstant.UUID;
		return this;
	}

	public UIModelNodeMapConfigureBuilder toBaseNodeType(int toBaseNodeType) {
		this.toBaseNodeType = toBaseNodeType;
		return this;
	}

	public UIModelNodeMapConfigureBuilder hostNodeFlag(boolean hostNodeFlag) {
		this.hostNodeFlag = hostNodeFlag;
		return this;
	}

	public UIModelNodeMapConfigureBuilder serviceEntityManager(ServiceEntityManager serviceEntityManager) {
		this.serviceEntityManager = serviceEntityManager;
		return this;
	}

	public UIModelNodeMapConfigureBuilder logicManager(Object logicManager) {
		this.logicManager = logicManager;
		return this;
	}

	public UIModelNodeMapConfigureBuilder convToUIMethod(String convToUIMethod) {
		this.convToUIMethod = convToUIMethod;
		return this;
	}

	public UIModelNodeMapConfigureBuilder convToUIMethodParas(Class<?>... classes) {
		List<Class<?>> convToUIMethodParas = ServiceCollectionsHelper.asList(classes);
		this.convToUIMethodParas = ServiceCollectionsHelper.convListToArray(convToUIMethodParas);
		return this;
	}

	public UIModelNodeMapConfigureBuilder convUIToMethod(String convUIToMethod) {
		this.convUIToMethod = convUIToMethod;
		return this;
	}

	public UIModelNodeMapConfigureBuilder convUIToMethodParas(Class<?>... classes) {
		List<Class<?>> convUIToMethodParas = ServiceCollectionsHelper.asList(classes);
		this.convUIToMethodParas = ServiceCollectionsHelper.convListToArray(convUIToMethodParas);
		return this;
	}

	public UIModelNodeMapConfigureBuilder connectionConditions(List<SearchConfigConnectCondition> connectionConditions) {
		this.connectionConditions = connectionConditions;
		return this;
	}

	public UIModelNodeMapConfigureBuilder preConditions(List<UIModelNodeConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
		return this;
	}

	public UIModelNodeMapConfigureBuilder addConnectionCondition(String sourceFieldName) {
		SearchConfigConnectCondition searchConfigConnectCondition =
				new SearchConfigConnectCondition(sourceFieldName, IServiceEntityNodeFieldConstant.UUID);
		this.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		this.connectionConditions.add(searchConfigConnectCondition);
		return this;
	}

	public UIModelNodeMapConfigureBuilder addConnectionCondition(String sourceFieldName,
																 String targetFieldName) {
		SearchConfigConnectCondition searchConfigConnectCondition =
				new SearchConfigConnectCondition(sourceFieldName, targetFieldName);
		this.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		this.connectionConditions.add(searchConfigConnectCondition);
		return this;
	}

	public UIModelNodeMapConfigureBuilder processUIModelNodeMapConfigure(UIModelNodeMapConfigureBuilder uiModelNodeMapConfigureBuilder) {
		setMapBaseFieldName(uiModelNodeMapConfigureBuilder.getMapBaseFieldName());
		setConnectionConditions(uiModelNodeMapConfigureBuilder.getConnectionConditions());
		setHostNodeFlag(uiModelNodeMapConfigureBuilder.getHostNodeFlag());
		setServiceEntityManager(uiModelNodeMapConfigureBuilder.getServiceEntityManager());
		setLogicManager(uiModelNodeMapConfigureBuilder.getLogicManager());
		setConvUIToMethod(uiModelNodeMapConfigureBuilder.getConvToUIMethod());
		setConvUIToMethodParas(uiModelNodeMapConfigureBuilder.getConvToUIMethodParas());
		setConvToUIMethod(uiModelNodeMapConfigureBuilder.getConvUIToMethod());
		setConvToUIMethodParas(uiModelNodeMapConfigureBuilder.getConvUIToMethodParas());
		setPreConditions(uiModelNodeMapConfigureBuilder.getPreConditions());
		return this;
	}

	public static String calculateRefSEName(Class<? extends ServiceEntityNode> modelClass) throws NoSuchFieldException, IllegalAccessException {
		return ServiceEntityFieldsHelper.getStaticFieldValue(
				modelClass, IServiceEntityNodeFieldConstant.STA_SENAME).toString();
	}

	public static String calculateRefNodeName(Class<? extends ServiceEntityNode> modelClass) throws NoSuchFieldException, IllegalAccessException {
		return ServiceEntityFieldsHelper.getStaticFieldValue(
				modelClass, IServiceEntityNodeFieldConstant.STA_NODENAME).toString();
	}

	private static void checkClassValid(Class<?> clazz, String className) throws ServiceEntityConfigureException {
		if (clazz == null) {
			throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, className + " is NULL");
		}
	}

	public static Class<?>[] calculateConvToUIMethodParas(Class<? extends ServiceEntityNode> modelClass, Class<? extends SEUIComModel> uiModelClass) throws ServiceEntityConfigureException {
		checkClassValid(modelClass, "modelClass");
		checkClassValid(uiModelClass, "uiModelClass");
		List<Class<?>> convToUIMethodParas = ServiceCollectionsHelper.asList(modelClass, uiModelClass);
		return ServiceCollectionsHelper.convListToArray(convToUIMethodParas);
	}

	public static Class<?>[] calculateConvUIToMethodParas(Class<? extends ServiceEntityNode> modelClass, Class<? extends SEUIComModel> uiModelClass) throws ServiceEntityConfigureException {
		checkClassValid(modelClass, "modelClass");
		checkClassValid(uiModelClass, "uiModelClass");
		List<Class<?>> convUIToMethodParas = ServiceCollectionsHelper.asList(uiModelClass, modelClass);
		return ServiceCollectionsHelper.convListToArray(convUIToMethodParas);
	}

	public static String getDefaultNodeInstId(Class<? extends ServiceEntityNode> modelClass) throws NoSuchFieldException, IllegalAccessException {
		String refSEName = calculateRefSEName(modelClass);
		String refNodeName = calculateRefNodeName(modelClass);
		return ServiceEntityStringHelper.getDefModelId(refSEName, refNodeName);
	}

	public void setNotification() throws ServiceEntityConfigureException {
		if (ServiceEntityStringHelper.checkNullString(getSeName())) {
			try {
				setSeName(calculateRefSEName(this.modelClass));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getMessage());
			}
		}
		if (ServiceEntityStringHelper.checkNullString(getNodeName())) {
			try {
				setNodeName(calculateRefNodeName(this.modelClass));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getMessage());
			}
		}
		if (ServiceEntityStringHelper.checkNullString(getNodeInstId())) {
			String nodeInstId = ServiceEntityStringHelper.getDefModelId(getSeName(), getNodeName());
			setNodeInstId(nodeInstId);
		}
		if (getConvToUIMethodParas() == null) {
			setConvToUIMethodParas(calculateConvToUIMethodParas(getModelClass(), getUiModelClass()));
		}
	}

	public UIModelNodeMapConfigure build() throws ServiceEntityConfigureException {
		UIModelNodeMapConfigure uiModelNodeMapConfigure = new UIModelNodeMapConfigure();
		setNotification();
		if (getConvToUIMethodParas() == null) {
			setConvToUIMethodParas(calculateConvToUIMethodParas(getModelClass(), getUiModelClass()));
		}
		if (getConvUIToMethodParas() == null) {
			setConvUIToMethodParas(calculateConvUIToMethodParas(getModelClass(), getUiModelClass()));
		}
		uiModelNodeMapConfigure.setSeName(getSeName());
		uiModelNodeMapConfigure.setNodeName(getNodeName());
		uiModelNodeMapConfigure.setNodeInstID(getNodeInstId());
		uiModelNodeMapConfigure.setBaseNodeInstID(getBaseNodeInstId());
		uiModelNodeMapConfigure.setMapBaseFieldName(getMapBaseFieldName());
		uiModelNodeMapConfigure.setConnectionConditions(getConnectionConditions());
		uiModelNodeMapConfigure.setHostNodeFlag(getHostNodeFlag());
		uiModelNodeMapConfigure.setServiceEntityManager(getServiceEntityManager());
		uiModelNodeMapConfigure.setLogicManager(getLogicManager());
		uiModelNodeMapConfigure.setToBaseNodeType(getToBaseNodeType());
		uiModelNodeMapConfigure.setConvToUIMethod(getConvToUIMethod());
		uiModelNodeMapConfigure.setConvToUIMethodParas(getConvToUIMethodParas());
		uiModelNodeMapConfigure.setConvUIToMethod(getConvUIToMethod());
		uiModelNodeMapConfigure.setConvUIToMethodParas(getConvUIToMethodParas());
		uiModelNodeMapConfigure.setPreConditions(getPreConditions());
		return uiModelNodeMapConfigure;
	}

	public Class<? extends ServiceEntityNode> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<? extends ServiceEntityNode> modelClass) {
		this.modelClass = modelClass;
	}

	public Class<? extends SEUIComModel> getUiModelClass() {
		return uiModelClass;
	}

	public void setUiModelClass(Class<? extends SEUIComModel> uiModelClass) {
		this.uiModelClass = uiModelClass;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public String getBaseNodeInstId() {
		return baseNodeInstId;
	}

	public void setBaseNodeInstId(String baseNodeInstId) {
		this.baseNodeInstId = baseNodeInstId;
	}

	public String getMapBaseFieldName() {
		return mapBaseFieldName;
	}

	public void setMapBaseFieldName(String mapBaseFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
	}

	public List<SearchConfigConnectCondition> getConnectionConditions() {
		return connectionConditions;
	}

	public void setConnectionConditions(List<SearchConfigConnectCondition> connectionConditions) {
		this.connectionConditions = connectionConditions;
	}

	public String getMapFieldName() {
		return mapFieldName;
	}

	public void setMapFieldName(String mapFieldName) {
		this.mapFieldName = mapFieldName;
	}

	public int getToBaseNodeType() {
		return toBaseNodeType;
	}

	public void setToBaseNodeType(int toBaseNodeType) {
		this.toBaseNodeType = toBaseNodeType;
	}

	public boolean getHostNodeFlag() {
		return hostNodeFlag;
	}

	public void setHostNodeFlag(boolean hostNodeFlag) {
		this.hostNodeFlag = hostNodeFlag;
	}

	public ServiceEntityManager getServiceEntityManager() {
		return serviceEntityManager;
	}

	public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
		this.serviceEntityManager = serviceEntityManager;
	}

	public Object getLogicManager() {
		return logicManager;
	}

	public void setLogicManager(Object logicManager) {
		this.logicManager = logicManager;
	}

	public String getConvToUIMethod() {
		return convToUIMethod;
	}

	public void setConvToUIMethod(String convToUIMethod) {
		this.convToUIMethod = convToUIMethod;
	}

	public Class<?>[] getConvToUIMethodParas() {
		return convToUIMethodParas;
	}

	public void setConvToUIMethodParas(Class<?>[] convToUIMethodParas) {
		this.convToUIMethodParas = convToUIMethodParas;
	}

	public String getConvUIToMethod() {
		return convUIToMethod;
	}

	public void setConvUIToMethod(String convUIToMethod) {
		this.convUIToMethod = convUIToMethod;
	}

	public Class<?>[] getConvUIToMethodParas() {
		return convUIToMethodParas;
	}

	public void setConvUIToMethodParas(Class<?>[] convUIToMethodParas) {
		this.convUIToMethodParas = convUIToMethodParas;
	}

	public List<UIModelNodeConfigPreCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<UIModelNodeConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
	}

	public static interface IProcessUIModelNode {

		UIModelNodeMapConfigureBuilder execute(UIModelNodeMapConfigureBuilder uiModelNodeMapConfigureBuilder) throws ServiceEntityConfigureException;

	}
}
