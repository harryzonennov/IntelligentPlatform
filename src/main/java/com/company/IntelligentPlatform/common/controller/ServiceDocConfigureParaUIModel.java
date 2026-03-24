package com.company.IntelligentPlatform.common.controller;

import java.util.Map;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

public class ServiceDocConfigureParaUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "uuid", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String uuid;

	@ISEUIModelMapping(fieldName = "parentNodeUUID", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String parentNodeUUID;

	@ISEUIModelMapping(fieldName = "consumerFieldName", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String consumerFieldName;

	@ISEUIModelMapping(fieldName = "resourceFieldName", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String resourceFieldName;
	
	protected int dataOffsetDirectionHigh;

	@ISEUIModelMapping(fieldName = "consumerValueMode", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceDocConfigurePara_inputValueMode", valueFieldName = "inputValueModelLabel")
	protected int consumerValueMode;

	@ISEUIModelMapping(seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String consumerValueModelLabel;

	@ISEUIModelMapping(fieldName = "consumerValueMode", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceDocConfigurePara_outputValueMode", valueFieldName = "outputValueModeLabel")
	protected int outputValueMode;

	@ISEUIModelMapping(seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String outputValueModeLabel;

	@ISEUIModelMapping(fieldName = "paraDirection", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceDocConfigurePara_paraDirection", valueFieldName = "inputValueModelLabel")
	protected int paraDirection;

	protected String paraDirectionValue;

	protected String dataOffsetUnit;
	
	protected String resourceFieldLabel;

	@ISEUIModelMapping(fieldName = "switchFlag", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected int switchFlag;

	@ISEUIModelMapping(seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String switchFlagValue;

	@ISEUIModelMapping(fieldName = "fixValue", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String fixValue;

	@ISEUIModelMapping(fieldName = "fixValueOperator", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected int fixValueOperator;
	
	protected int dataOffsetUnitInt;

	protected String fixValueOperatorValue;

	@ISEUIModelMapping(fieldName = "fixValueDouble", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected double fixValueDouble;

	@ISEUIModelMapping(fieldName = "fixValueInt", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected int fixValueInt;

	@ISEUIModelMapping(fieldName = "fixValueHigh", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String fixValueHigh;

	@ISEUIModelMapping(fieldName = "fixValueDoubleHigh", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected double fixValueDoubleHigh;

	@ISEUIModelMapping(fieldName = "fixValueIntHigh", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected int fixValueIntHigh;

	@ISEUIModelMapping(fieldName = "fixValueDateHigh", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String fixValueDateHigh;

	@ISEUIModelMapping(seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected Class<?> resourceFieldType;

	@ISEUIModelMapping(seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String resourceFieldTypeLabel;

	@ISEUIModelMapping(fieldName = "logicOperator", seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected int logicOperator;

	@ISEUIModelMapping(seName = ServiceDocConfigurePara.SENAME, nodeName = ServiceDocConfigurePara.NODENAME, nodeInstID = ServiceDocConfigurePara.NODENAME)
	protected String logicOperatorValue;

	protected int dataOffsetDirection;
	
	protected double dataOffsetValueHigh;

	protected String dataOffsetDirectionValue;

	protected String refGroupUUID;

	protected String refGroupId;

	protected String refGroupName;

	protected Map<?, ?> dropdownMap;

	protected String dataProviderId;

	protected double dataOffsetValue;

	protected String fixValueDate;
		
	protected String dataOffsetUnitHigh;
	
	protected int dataOffsetUnitIntHigh;
	
	protected String refConfigureId;
	
	protected String refConfigureName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getConsumerSourceFieldName() {
		return consumerFieldName;
	}

	public void setConsumerSourceFieldName(String consumerSourceFieldName) {
		this.consumerFieldName = consumerSourceFieldName;
	}

	public String getDataOffsetUnit() {
		return dataOffsetUnit;
	}

	public void setDataOffsetUnit(String dataOffsetUnit) {
		this.dataOffsetUnit = dataOffsetUnit;
	}

	public int getDataOffsetDirection() {
		return dataOffsetDirection;
	}

	public int getDataOffsetDirectionHigh() {
		return dataOffsetDirectionHigh;
	}

	public void setDataOffsetDirectionHigh(int dataOffsetDirectionHigh) {
		this.dataOffsetDirectionHigh = dataOffsetDirectionHigh;
	}

	public void setDataOffsetDirection(int dataOffsetDirection) {
		this.dataOffsetDirection = dataOffsetDirection;
	}

	public String getDataOffsetDirectionValue() {
		return dataOffsetDirectionValue;
	}

	public void setDataOffsetDirectionValue(String dataOffsetDirectionValue) {
		this.dataOffsetDirectionValue = dataOffsetDirectionValue;
	}

	public int getParaDirection() {
		return paraDirection;
	}

	public void setParaDirection(int paraDirection) {
		this.paraDirection = paraDirection;
	}

	public String getParaDirectionValue() {
		return paraDirectionValue;
	}

	public void setParaDirectionValue(String paraDirectionValue) {
		this.paraDirectionValue = paraDirectionValue;
	}

	public String getFixValueOperatorValue() {
		return fixValueOperatorValue;
	}

	public void setFixValueOperatorValue(String fixValueOperatorValue) {
		this.fixValueOperatorValue = fixValueOperatorValue;
	}

	public int getDataOffsetUnitInt() {
		return dataOffsetUnitInt;
	}

	public void setDataOffsetUnitInt(int dataOffsetUnitInt) {
		this.dataOffsetUnitInt = dataOffsetUnitInt;
	}

	public int getConsumerValueMode() {
		return consumerValueMode;
	}

	public void setConsumerValueMode(int consumerValueMode) {
		this.consumerValueMode = consumerValueMode;
	}

	public String getConsumerValueModelLabel() {
		return consumerValueModelLabel;
	}

	public void setConsumerValueModelLabel(String consumerValueModelLabel) {
		this.consumerValueModelLabel = consumerValueModelLabel;
	}

	public int getOutputValueMode() {
		return outputValueMode;
	}

	public void setOutputValueMode(int outputValueMode) {
		this.outputValueMode = outputValueMode;
	}

	public String getOutputValueModeLabel() {
		return outputValueModeLabel;
	}

	public void setOutputValueModeLabel(String outputValueModeLabel) {
		this.outputValueModeLabel = outputValueModeLabel;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getConsumerFieldName() {
		return consumerFieldName;
	}

	public void setConsumerFieldName(String consumerFieldName) {
		this.consumerFieldName = consumerFieldName;
	}

	public String getSwitchFlagValue() {
		return switchFlagValue;
	}

	public void setSwitchFlagValue(String switchFlagValue) {
		this.switchFlagValue = switchFlagValue;
	}

	public String getResourceFieldName() {
		return resourceFieldName;
	}

	public void setResourceFieldName(String resourceFieldName) {
		this.resourceFieldName = resourceFieldName;
	}

	public String getFixValue() {
		return fixValue;
	}

	public void setFixValue(String fixValue) {
		this.fixValue = fixValue;
	}

	public int getFixValueOperator() {
		return fixValueOperator;
	}

	public void setFixValueOperator(int fixValueOperator) {
		this.fixValueOperator = fixValueOperator;
	}

	public Class<?> getResourceFieldType() {
		return resourceFieldType;
	}

	public void setResourceFieldType(Class<?> resourceFieldType) {
		this.resourceFieldType = resourceFieldType;
	}

	public String getResourceFieldTypeLabel() {
		return resourceFieldTypeLabel;
	}

	public void setResourceFieldTypeLabel(String resourceFieldTypeLabel) {
		this.resourceFieldTypeLabel = resourceFieldTypeLabel;
	}

	public int getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

	public String getLogicOperatorValue() {
		return logicOperatorValue;
	}

	public void setLogicOperatorValue(String logicOperatorValue) {
		this.logicOperatorValue = logicOperatorValue;
	}

	public String getRefGroupUUID() {
		return refGroupUUID;
	}

	public void setRefGroupUUID(String refGroupUUID) {
		this.refGroupUUID = refGroupUUID;
	}

	public String getRefGroupId() {
		return refGroupId;
	}

	public void setRefGroupId(String refGroupId) {
		this.refGroupId = refGroupId;
	}

	public String getRefGroupName() {
		return refGroupName;
	}

	public void setRefGroupName(String refGroupName) {
		this.refGroupName = refGroupName;
	}

	public Map<?, ?> getDropdownMap() {
		return dropdownMap;
	}

	public void setDropdownMap(Map<?, ?> dropdownMap) {
		this.dropdownMap = dropdownMap;
	}

	public String getDataProviderId() {
		return dataProviderId;
	}

	public void setDataProviderId(String dataProviderId) {
		this.dataProviderId = dataProviderId;
	}

	public double getFixValueDouble() {
		return fixValueDouble;
	}

	public void setFixValueDouble(double fixValueDouble) {
		this.fixValueDouble = fixValueDouble;
	}

	public int getFixValueInt() {
		return fixValueInt;
	}

	public void setFixValueInt(int fixValueInt) {
		this.fixValueInt = fixValueInt;
	}

	public String getFixValueDate() {
		return fixValueDate;
	}

	public void setFixValueDate(String fixValueDate) {
		this.fixValueDate = fixValueDate;
	}

	public String getFixValueHigh() {
		return fixValueHigh;
	}

	public void setFixValueHigh(String fixValueHigh) {
		this.fixValueHigh = fixValueHigh;
	}

	public double getFixValueDoubleHigh() {
		return fixValueDoubleHigh;
	}

	public void setFixValueDoubleHigh(double fixValueDoubleHigh) {
		this.fixValueDoubleHigh = fixValueDoubleHigh;
	}

	public int getFixValueIntHigh() {
		return fixValueIntHigh;
	}

	public void setFixValueIntHigh(int fixValueIntHigh) {
		this.fixValueIntHigh = fixValueIntHigh;
	}

	public String getFixValueDateHigh() {
		return fixValueDateHigh;
	}

	public void setFixValueDateHigh(String fixValueDateHigh) {
		this.fixValueDateHigh = fixValueDateHigh;
	}

	public double getDataOffsetValue() {
		return dataOffsetValue;
	}

	public void setDataOffsetValue(double dataOffsetValue) {
		this.dataOffsetValue = dataOffsetValue;
	}

	public double getDataOffsetValueHigh() {
		return dataOffsetValueHigh;
	}

	public void setDataOffsetValueHigh(double dataOffsetValueHigh) {
		this.dataOffsetValueHigh = dataOffsetValueHigh;
	}

	public String getDataOffsetUnitHigh() {
		return dataOffsetUnitHigh;
	}

	public String getResourceFieldLabel() {
		return resourceFieldLabel;
	}

	public void setResourceFieldLabel(String resourceFieldLabel) {
		this.resourceFieldLabel = resourceFieldLabel;
	}

	public void setDataOffsetUnitHigh(String dataOffsetUnitHigh) {
		this.dataOffsetUnitHigh = dataOffsetUnitHigh;
	}

	public int getDataOffsetUnitIntHigh() {
		return dataOffsetUnitIntHigh;
	}

	public void setDataOffsetUnitIntHigh(int dataOffsetUnitIntHigh) {
		this.dataOffsetUnitIntHigh = dataOffsetUnitIntHigh;
	}

	public String getRefConfigureId() {
		return refConfigureId;
	}

	public void setRefConfigureId(String refConfigureId) {
		this.refConfigureId = refConfigureId;
	}

	public String getRefConfigureName() {
		return refConfigureName;
	}

	public void setRefConfigureName(String refConfigureName) {
		this.refConfigureName = refConfigureName;
	}

}
