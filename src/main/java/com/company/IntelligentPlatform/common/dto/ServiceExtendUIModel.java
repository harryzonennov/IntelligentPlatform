package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * UI Model for trans
 * 
 * @author Zhang,Hang
 *
 */
public class ServiceExtendUIModel extends SEUIComModel {

	protected String refUUID;

	protected String storeModelName;

	protected String refFieldName;

	protected String refFieldUUID;

	protected Object fieldValue;
	
	public ServiceExtendUIModel(){
		super();
	}

	public ServiceExtendUIModel(String refUUID, String storeModelName,
			String refFieldName, String refFieldUUID, Object fieldValue) {
		super();
		this.refUUID = refUUID;
		this.storeModelName = storeModelName;
		this.refFieldName = refFieldName;
		this.refFieldUUID = refFieldUUID;
		this.fieldValue = fieldValue;
	}

	public ServiceExtendUIModel(String uuid, String parentNodeUUID,
			String rootNodeUUID, String refUUID, String storeModelName,
			String refFieldName, String refFieldUUID, Object fieldValue) {
		super();
		this.uuid = uuid;
		this.parentNodeUUID = parentNodeUUID;
		this.rootNodeUUID = rootNodeUUID;
		this.refUUID = refUUID;
		this.storeModelName = storeModelName;
		this.refFieldName = refFieldName;
		this.refFieldUUID = refFieldUUID;
		this.fieldValue = fieldValue;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getStoreModelName() {
		return storeModelName;
	}

	public void setStoreModelName(String storeModelName) {
		this.storeModelName = storeModelName;
	}

	public String getRefFieldName() {
		return refFieldName;
	}

	public void setRefFieldName(String refFieldName) {
		this.refFieldName = refFieldName;
	}

	public String getRefFieldUUID() {
		return refFieldUUID;
	}

	public void setRefFieldUUID(String refFieldUUID) {
		this.refFieldUUID = refFieldUUID;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

}
