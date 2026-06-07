package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "MatConfigHeaderCondition", catalog = "platform")
public class MatConfigHeaderCondition extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.MatConfigHeaderCondition;

	public static final String SENAME = MaterialConfigureTemplate.SENAME;

	public MatConfigHeaderCondition() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	protected String refNodeInstId;

	protected String fieldName;

	protected String fieldValue;

	protected int logicOperator;

	public String getRefNodeInstId() {
		return refNodeInstId;
	}

	public void setRefNodeInstId(String refNodeInstId) {
		this.refNodeInstId = refNodeInstId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

}
