package com.company.IntelligentPlatform.common.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Base entity — renamed from old 'BaseEntity' to match the real class name in old projects.
 *
 * Represents the common node structure shared by ALL entities across all 5 projects.
 * Every HBM in the old projects contained these identical 16 fields.
 *
 * Old class name in ThorsteinPlatform: ServiceEntityNode
 */
@MappedSuperclass
public abstract class ServiceEntityNode implements Cloneable {

	public static final String NODENAME_ROOT = "ROOT";

	public static final int NODE_CATEGORY_SYS = 1;
	public static final int NODE_CATEGORY_CONFIG = 2;

	public static final int NODELEVEL_ROOT = 0;
	public static final int NODELEVEL_NODE = 1;
	public static final int NODELEVEL_LEAVE = 2;

	public static final int NODESPECIFYTYPE_STANDARD   = 0;
	public static final int NODESPECIFYTYPE_REFERENCE  = 1;
	public static final int NODESPECIFYTYPE_PROJECTION = 2;
	public static final int NODESPECIFYTYPE_DOCUMENT   = 3;

	@Id
	@Column(name = "uuid", nullable = false, updatable = false)
	protected String uuid;

	@Column(name = "client")
	protected String client;

	@Column(name = "id")
	protected String id;

	@Column(name = "name")
	protected String name;

	@Column(name = "parentNodeUUID")
	protected String parentNodeUUID;

	@Column(name = "rootNodeUUID")
	protected String rootNodeUUID;

	@Column(name = "nodeLevel")
	protected int nodeLevel;

	@Column(name = "serviceEntityName")
	protected String serviceEntityName;

	@Column(name = "nodeName")
	protected String nodeName;

	@Column(name = "createdBy")
	protected String createdBy;

	@Column(name = "createdTime")
	protected LocalDateTime createdTime;

	@Column(name = "lastUpdateBy")
	protected String lastUpdateBy;

	@Column(name = "lastUpdateTime")
	protected LocalDateTime lastUpdateTime;

	@Column(name = "nodeSpecifyType")
	protected int nodeSpecifyType;

	@Column(name = "note")
	protected String note;

	@Column(name = "resEmployeeUUID")
	protected String resEmployeeUUID;

	@Column(name = "resOrgUUID")
	protected String resOrgUUID;

	@PrePersist
	protected void onCreate() {
		this.createdTime = LocalDateTime.now();
		this.lastUpdateTime = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdateTime = LocalDateTime.now();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public int getNodeLevel() {
		return nodeLevel;
	}

	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getNodeSpecifyType() {
		return nodeSpecifyType;
	}

	public void setNodeSpecifyType(int nodeSpecifyType) {
		this.nodeSpecifyType = nodeSpecifyType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getResEmployeeUUID() {
		return resEmployeeUUID;
	}

	public void setResEmployeeUUID(String resEmployeeUUID) {
		this.resEmployeeUUID = resEmployeeUUID;
	}

	public String getResOrgUUID() {
		return resOrgUUID;
	}

	public void setResOrgUUID(String resOrgUUID) {
		this.resOrgUUID = resOrgUUID;
	}

	@Override
	public ServiceEntityNode clone() {
		try {
			return (ServiceEntityNode) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
