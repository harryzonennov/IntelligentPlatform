package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
@Entity
@Table(name = "MaterialType", catalog = "platform")
public class MaterialType extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.MaterialType;

	public static final int STATUS_INIT = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ACTIVE = DocumentContent.STATUS_ACTIVE;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;

	public MaterialType() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.status = STATUS_INIT;
	}

	protected String parentTypeUUID;

	protected String rootTypeUUID;

	protected int status;

	protected int systemStandardCategory;

	public String getParentTypeUUID() {
		return parentTypeUUID;
	}

	public void setParentTypeUUID(String parentTypeUUID) {
		this.parentTypeUUID = parentTypeUUID;
	}

	public String getRootTypeUUID() {
		return rootTypeUUID;
	}

	public void setRootTypeUUID(String rootTypeUUID) {
		this.rootTypeUUID = rootTypeUUID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSystemStandardCategory() {
		return systemStandardCategory;
	}

	public void setSystemStandardCategory(int systemStandardCategory) {
		this.systemStandardCategory = systemStandardCategory;
	}

}
