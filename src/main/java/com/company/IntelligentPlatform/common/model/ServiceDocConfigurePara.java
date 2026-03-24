package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocConfigurePara extends ServiceEntityNode{	
	
	public static final int INPUT_VALUEMODE_PASSVALUE = 1;
	
	public static final int INPUT_VALUEMODE_SETVALUE = 2;
	
	public static final int INPUT_VALUEMODE_COMDATASOURCE = 3;
	
    public static final int OUTPUT_VALUEMODE_PASSVALUE = 1;
	
	public static final int OUTPUT_VALUEMODE_MANUALOUT = 2;

	protected String consumerFieldName;

	protected String resourceFieldName;
	
	// field label is traversed from document resource proxy class
	protected String resourceFieldLabel;
	
	protected String refGroupUUID;

	protected int consumerValueMode;

	protected int switchFlag;

	protected String fixValue;
	
	protected int fixValueOperator;
	
	protected double fixValueDouble;
	
	protected int fixValueInt;
	
	protected Date fixValueDate;
	
	protected String fixValueHigh;
	
    protected double fixValueDoubleHigh;
	
	protected int fixValueIntHigh;
	
	protected Date fixValueDateHigh;
	
	protected int paraDirection;
	
	protected int logicOperator;
	
	protected int dataOffsetDirection;
	
	protected double dataOffsetValue;
	
	protected String dataOffsetUnit;
	
	protected int dataOffsetUnitInt;
	
	protected String dataSourceProviderID;
	
    protected double dataOffsetValueHigh;
	
	protected String dataOffsetUnitHigh;
	
	protected int dataOffsetUnitIntHigh;
	
	protected int dataOffsetDirectionHigh;
	
	public static final String NODENAME = IServiceModelConstants.ServiceDocConfigurePara;

	public static final String SENAME = ServiceDocConfigure.SENAME;

	public ServiceDocConfigurePara() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.fixValueOperator = SEFieldSearchConfig.OPERATOR_EQUAL;
	}	

	public String getConsumerSourceFieldName() {
		return consumerFieldName;
	}

	public void setConsumerSourceFieldName(String consumerSourceFieldName) {
		this.consumerFieldName = consumerSourceFieldName;
	}

	public int getConsumerValueMode() {
		return consumerValueMode;
	}

	public void setConsumerValueMode(int consumerValueMode) {
		this.consumerValueMode = consumerValueMode;
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

	public int getParaDirection() {
		return paraDirection;
	}

	public void setParaDirection(int paraDirection) {
		this.paraDirection = paraDirection;
	}

	public int getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

	public String getResourceFieldLabel() {
		return resourceFieldLabel;
	}

	public void setResourceFieldLabel(String resourceFieldLabel) {
		this.resourceFieldLabel = resourceFieldLabel;
	}

	public String getRefGroupUUID() {
		return refGroupUUID;
	}

	public void setRefGroupUUID(String refGroupUUID) {
		this.refGroupUUID = refGroupUUID;
	}

	public int getFixValueOperator() {
		return fixValueOperator;
	}

	public void setFixValueOperator(int fixValueOperator) {
		this.fixValueOperator = fixValueOperator;
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

	public Date getFixValueDate() {
		return fixValueDate;
	}

	public void setFixValueDate(Date fixValueDate) {
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

	public Date getFixValueDateHigh() {
		return fixValueDateHigh;
	}

	public void setFixValueDateHigh(Date fixValueDateHigh) {
		this.fixValueDateHigh = fixValueDateHigh;
	}

	public int getDataOffsetDirection() {
		return dataOffsetDirection;
	}

	public void setDataOffsetDirection(int dataOffsetDirection) {
		this.dataOffsetDirection = dataOffsetDirection;
	}

	public int getDataOffsetDirectionHigh() {
		return dataOffsetDirectionHigh;
	}

	public void setDataOffsetDirectionHigh(int dataOffsetDirectionHigh) {
		this.dataOffsetDirectionHigh = dataOffsetDirectionHigh;
	}

	public String getDataSourceProviderID() {
		return dataSourceProviderID;
	}

	public void setDataSourceProviderID(String dataSourceProviderID) {
		this.dataSourceProviderID = dataSourceProviderID;
	}

	
	public int getDataOffsetUnitIntHigh() {
		return dataOffsetUnitIntHigh;
	}

	public void setDataOffsetUnitIntHigh(int dataOffsetUnitIntHigh) {
		this.dataOffsetUnitIntHigh = dataOffsetUnitIntHigh;
	}

	public double getDataOffsetValue() {
		return dataOffsetValue;
	}

	public void setDataOffsetValue(double dataOffsetValue) {
		this.dataOffsetValue = dataOffsetValue;
	}

	public String getDataOffsetUnit() {
		return dataOffsetUnit;
	}

	public void setDataOffsetUnit(String dataOffsetUnit) {
		this.dataOffsetUnit = dataOffsetUnit;
	}

	public int getDataOffsetUnitInt() {
		return dataOffsetUnitInt;
	}

	public void setDataOffsetUnitInt(int dataOffsetUnitInt) {
		this.dataOffsetUnitInt = dataOffsetUnitInt;
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

	public void setDataOffsetUnitHigh(String dataOffsetUnitHigh) {
		this.dataOffsetUnitHigh = dataOffsetUnitHigh;
	}

}
