package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * this class combile key information of service entity node for basic search
 * 
 * @author Zhang, Hang
 * 
 */
public class ServiceBasicKeyStructure {

	protected Object keyValue;

	protected String keyName;

	protected int operator = OPERATOR_ADD;

	public static final int OPERATOR_ADD = 1;

	public static final int OPERATOR_OR = 2;
	
	protected List<?> multipleValueList = new ArrayList<>();
	
	public ServiceBasicKeyStructure() {
		super();
	}

	public ServiceBasicKeyStructure(Object keyValue, String keyName) {
		super();
		this.keyValue = keyValue;
		this.keyName = keyName;
	}

	public ServiceBasicKeyStructure(Object keyValue, String keyName, int operator) {
		super();
		this.keyValue = keyValue;
		this.keyName = keyName;
		if(operator > 0){
			this.operator = operator;
		}
	}

	public ServiceBasicKeyStructure(List<?> multipleValueList, String keyName, int operator) {
		this.multipleValueList = multipleValueList;
		this.keyName = keyName;
		if(operator > 0){
			this.operator = operator;
		}
	}

	public Object getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(Object keyValue) {
		this.keyValue = keyValue;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public List<?> getMultipleValueList() {
		return multipleValueList;
	}

	public void setMultipleValueList(List<?> multipleValueList) {
		this.multipleValueList = multipleValueList;
	}
	
//	public void addToMultipleValue(Object value){
//		if(!multipleValueList.contains(value)){
//			multipleValueList.add(value);
//		}
//	}

}
