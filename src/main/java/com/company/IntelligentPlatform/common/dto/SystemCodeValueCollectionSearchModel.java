package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

@Component
public class SystemCodeValueCollectionSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "collectionCategory", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected int collectionCategory;
	
	@BSearchFieldConfig(fieldName = "collectionType", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected int collectionType;

	@BSearchFieldConfig(fieldName = "id", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "note", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "client", nodeName = SystemCodeValueCollection.NODENAME, seName = SystemCodeValueCollection.SENAME, nodeInstID = SystemCodeValueCollection.SENAME)
	protected String client;

	public int getCollectionCategory() {
		return this.collectionCategory;
	}

	public void setCollectionCategory(int collectionCategory) {
		this.collectionCategory = collectionCategory;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

}
