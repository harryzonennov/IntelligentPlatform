package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class FinAccountTitleTreeUIModel extends SEUIComModel implements
Comparable<FinAccountTitleTreeUIModel> {

	protected String parentAccountTitleUUID;

	protected String nodeUIName;

	protected String title;
	
	@ISEUIModelMapping(fieldName = "accountType", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, textAreaFlag = true)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_originalType", valueFieldName = "originalTypeType")
	protected int originalType;
	
	@ISEUIModelMapping(showOnEditor = false)
	protected String originalTypeType;
	
	protected int containsDataFlag;

	public String getParentAccountTitleUUID() {
		return parentAccountTitleUUID;
	}

	public void setParentAccountTitleUUID(String parentAccountTitleUUID) {
		this.parentAccountTitleUUID = parentAccountTitleUUID;
	}

	public String getNodeUIName() {
		return nodeUIName;
	}

	public void setNodeUIName(String nodeUIName) {
		this.nodeUIName = nodeUIName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOriginalType() {
		return originalType;
	}

	public void setOriginalType(int originalType) {
		this.originalType = originalType;
	}

	public String getOriginalTypeType() {
		return originalTypeType;
	}

	public void setOriginalTypeType(String originalTypeType) {
		this.originalTypeType = originalTypeType;
	}

	public int getContainsDataFlag() {
		return containsDataFlag;
	}

	public void setContainsDataFlag(int containsDataFlag) {
		this.containsDataFlag = containsDataFlag;
	}

	@Override
	public int compareTo(FinAccountTitleTreeUIModel otherModule) {
		if (this.getId() != null
				&& otherModule.getId() != null) {
			return this.getId().compareTo(
					otherModule.getId());
		}
		return 0;
	}

}
