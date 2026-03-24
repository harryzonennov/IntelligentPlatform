package com.company.IntelligentPlatform.common.controller;


/**
 * The Basic Service Entity Compound UI model, could be used for display in
 * Editor view or list view
 * 
 * @author Zhang Hang
 * 
 */
public class SEUIComModel implements Cloneable{
	
	public static final String TABID_BASIC = "basic";
	
	protected String uuid;
	
	protected String parentNodeUUID;
	
	protected String rootNodeUUID;
	
	protected String client;

	protected String createdDate;

	protected String createdTime;

	protected String createdByUUID;

	protected String createdById;

	protected String createdByName;

	protected String updatedDate;

	protected String updatedTime;

	protected String updatedByUUID;

	protected String updatedById;

	protected String updatedByName;

	protected String id;

	protected String name;

	protected String note;

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

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedByUUID() {
		return createdByUUID;
	}

	public void setCreatedByUUID(String createdByUUID) {
		this.createdByUUID = createdByUUID;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getUpdatedByUUID() {
		return updatedByUUID;
	}

	public void setUpdatedByUUID(String updatedByUUID) {
		this.updatedByUUID = updatedByUUID;
	}

	public String getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			// Should raise exception
			return null;
		}
		return o;
	}

}
