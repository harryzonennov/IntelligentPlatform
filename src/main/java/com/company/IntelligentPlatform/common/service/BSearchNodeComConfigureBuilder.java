package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

public class BSearchNodeComConfigureBuilder {

	protected Class<? extends ServiceEntityNode> modelClass;

	protected String seName;

	protected String nodeName;

	protected String nodeInstId;

	protected boolean startNodeFlag;

	protected String baseNodeInstId;

	protected String mapSourceFieldName;

	protected String mapBaseFieldName;

	protected int toBaseNodeType;

	/**
	 * Indicate local node inst id inside search group
	 */
	protected String subNodeInstId;

	protected List<SearchConfigConnectCondition> connectionConditions = new ArrayList<>();

	protected List<SearchConfigPreCondition> preConditions = new ArrayList<>();

	public BSearchNodeComConfigureBuilder modelClass(Class<? extends ServiceEntityNode> modelClass) {
		this.modelClass = modelClass;
		return this;
	}

	public BSearchNodeComConfigureBuilder nodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
		return this;
	}

	public BSearchNodeComConfigureBuilder startNodeFlag(boolean startNodeFlag) {
		this.startNodeFlag = startNodeFlag;
		return this;
	}

	public BSearchNodeComConfigureBuilder baseNodeInstId(String baseNodeInstId) {
		this.baseNodeInstId = baseNodeInstId;
		return this;
	}

	public BSearchNodeComConfigureBuilder subNodeInstId(String subNodeInstId) {
		this.subNodeInstId = subNodeInstId;
		return this;
	}

	public BSearchNodeComConfigureBuilder mapField(String mapBaseFieldName, String mapSourceFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
		this.mapSourceFieldName = mapSourceFieldName;
		return this;
	}

	public BSearchNodeComConfigureBuilder mapFieldUUID(String mapBaseFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
		this.mapSourceFieldName = IServiceEntityNodeFieldConstant.UUID;
		return this;
	}

	public BSearchNodeComConfigureBuilder toBaseNodeType(int toBaseNodeType) {
		this.toBaseNodeType = toBaseNodeType;
		return this;
	}

	public BSearchNodeComConfigureBuilder addConnectionCondition(String sourceFieldName,
																						 String targetFieldName) {
		SearchConfigConnectCondition searchConfigConnectCondition =
				new SearchConfigConnectCondition(sourceFieldName, targetFieldName);
		this.connectionConditions.add(searchConfigConnectCondition);
		return this;
	}

	public BSearchNodeComConfigureBuilder addPreCondition(Object fieldValue,
																 String fieldName, String nodeInstId) {
		SearchConfigPreCondition sarchConfigPreCondition =
				new SearchConfigPreCondition(fieldValue, fieldName, nodeInstId);
		this.preConditions.add(sarchConfigPreCondition);
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

	public static String getDefaultNodeInstId(Class<? extends ServiceEntityNode> modelClass) throws NoSuchFieldException, IllegalAccessException {
		String refSEName = calculateRefSEName(modelClass);
		String refNodeName = calculateRefNodeName(modelClass);
		return ServiceEntityStringHelper.getDefModelId(refSEName, refNodeName);
	}

	public BSearchNodeComConfigure build() throws SearchConfigureException {
		BSearchNodeComConfigure bSearchNodeComConfigure = new BSearchNodeComConfigure();
		if (ServiceEntityStringHelper.checkNullString(getSeName())) {
			try {
				setSeName(calculateRefSEName(this.modelClass));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
			}
		}
		if (ServiceEntityStringHelper.checkNullString(getNodeName())) {
			try {
				setNodeName(calculateRefNodeName(this.modelClass));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
			}
		}
		if (ServiceEntityStringHelper.checkNullString(getNodeInstId())) {
			String nodeInstId = ServiceEntityStringHelper.getDefModelId(getSeName(), getNodeName());
			setNodeInstId(nodeInstId);
		}
		bSearchNodeComConfigure.setSeName(getSeName());
		bSearchNodeComConfigure.setNodeName(getNodeName());
		bSearchNodeComConfigure.setNodeInstID(getNodeInstId());
		bSearchNodeComConfigure.setBaseNodeInstID(getBaseNodeInstId());
		bSearchNodeComConfigure.setSubNodeInstId(getSubNodeInstId());
		bSearchNodeComConfigure.setStartNodeFlag(getStartNodeFlag());
		bSearchNodeComConfigure.setToBaseNodeType(getToBaseNodeType());
		bSearchNodeComConfigure.setMapBaseFieldName(getMapBaseFieldName());
		bSearchNodeComConfigure.setMapSourceFieldName(getMapSourceFieldName());
		bSearchNodeComConfigure.setConnectionConditions(getConnectionConditions());
		bSearchNodeComConfigure.setPreConditions(getPreConditions());
		return bSearchNodeComConfigure;
	}

	public Class<? extends ServiceEntityNode> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<? extends ServiceEntityNode> modelClass) {
		this.modelClass = modelClass;
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

	public boolean getStartNodeFlag() {
		return startNodeFlag;
	}

	public void setStartNodeFlag(boolean startNodeFlag) {
		this.startNodeFlag = startNodeFlag;
	}

	public String getBaseNodeInstId() {
		return baseNodeInstId;
	}

	public void setBaseNodeInstId(String baseNodeInstId) {
		this.baseNodeInstId = baseNodeInstId;
	}

	public String getMapSourceFieldName() {
		return mapSourceFieldName;
	}

	public void setMapSourceFieldName(String mapSourceFieldName) {
		this.mapSourceFieldName = mapSourceFieldName;
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

	public List<SearchConfigPreCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<SearchConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
	}

	public int getToBaseNodeType() {
		return toBaseNodeType;
	}

	public void setToBaseNodeType(int toBaseNodeType) {
		this.toBaseNodeType = toBaseNodeType;
	}

	public String getSubNodeInstId() {
		return subNodeInstId;
	}

	public void setSubNodeInstId(String subNodeInstId) {
		this.subNodeInstId = subNodeInstId;
	}
}
