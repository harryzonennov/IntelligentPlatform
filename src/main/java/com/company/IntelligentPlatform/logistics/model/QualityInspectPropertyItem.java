package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class QualityInspectPropertyItem extends ServiceEntityNode{

	public static final String NODENAME = IServiceModelConstants.QualityInspectPropertyItem;

	public static final String SENAME = IServiceModelConstants.QualityInspectOrder;

	public QualityInspectPropertyItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.propCheckStatus = QualityInspectOrder.CHECKSTATUS_INITIAL;
	}

	protected int propCheckStatus;

	protected String fieldName;

	protected double actualValueDouble;

	protected double criCenterValueDouble;

	protected double criLowValueDouble;

	protected double criHighValueDouble;

	protected double criOffSetValueDouble;

	protected String actualValue;

	protected String criCenterValue;

	protected String criLowValue;

	protected String criHighValue;

	protected String criOffSetValue;

	protected String refPropertyUUID;

	protected String refUnitUUID;

	public int getPropCheckStatus() {
		return propCheckStatus;
	}

	public void setPropCheckStatus(int propCheckStatus) {
		this.propCheckStatus = propCheckStatus;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public double getActualValueDouble() {
		return actualValueDouble;
	}

	public void setActualValueDouble(double actualValueDouble) {
		this.actualValueDouble = actualValueDouble;
	}

	public double getCriCenterValueDouble() {
		return criCenterValueDouble;
	}

	public void setCriCenterValueDouble(double criCenterValueDouble) {
		this.criCenterValueDouble = criCenterValueDouble;
	}

	public double getCriLowValueDouble() {
		return criLowValueDouble;
	}

	public void setCriLowValueDouble(double criLowValueDouble) {
		this.criLowValueDouble = criLowValueDouble;
	}

	public double getCriHighValueDouble() {
		return criHighValueDouble;
	}

	public void setCriHighValueDouble(double criHighValueDouble) {
		this.criHighValueDouble = criHighValueDouble;
	}

	public double getCriOffSetValueDouble() {
		return criOffSetValueDouble;
	}

	public void setCriOffSetValueDouble(double criOffSetValueDouble) {
		this.criOffSetValueDouble = criOffSetValueDouble;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	public String getCriCenterValue() {
		return criCenterValue;
	}

	public void setCriCenterValue(String criCenterValue) {
		this.criCenterValue = criCenterValue;
	}

	public String getCriLowValue() {
		return criLowValue;
	}

	public void setCriLowValue(String criLowValue) {
		this.criLowValue = criLowValue;
	}

	public String getCriHighValue() {
		return criHighValue;
	}

	public void setCriHighValue(String criHighValue) {
		this.criHighValue = criHighValue;
	}

	public String getCriOffSetValue() {
		return criOffSetValue;
	}

	public void setCriOffSetValue(String criOffSetValue) {
		this.criOffSetValue = criOffSetValue;
	}

	public String getRefPropertyUUID() {
		return refPropertyUUID;
	}

	public void setRefPropertyUUID(String refPropertyUUID) {
		this.refPropertyUUID = refPropertyUUID;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

}
