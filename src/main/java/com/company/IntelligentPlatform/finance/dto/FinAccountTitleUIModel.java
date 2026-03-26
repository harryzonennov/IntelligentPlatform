package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class FinAccountTitleUIModel extends SEUIComModel {
	
	public static final int CONTAINS_DATA_FLAG_YES = 2;
	
	public static final int CONTAINS_DATA_FLAG_NO = 1;

	@ISEUIModelMapping(fieldName = "finAccountType", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, textAreaFlag = true)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_finAccountType", valueFieldName = "finAccountTypeValue")
	protected int finAccountType;
	
	@ISEUIModelMapping(showOnEditor = false)
	protected String finAccountTypeValue;
	
	@ISEUIModelMapping(fieldName = "accountType", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, textAreaFlag = true)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_category", valueFieldName = "categoryValue")
	protected int category;
	
	@ISEUIModelMapping(showOnEditor = false)
	protected String categoryValue;

	@ISEUIModelMapping(fieldName = "parentAccountTitleUUID", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, hiddenFlag = true)
	protected String parentAccountTitleUUID;

	@ISEUIModelMapping(fieldName = "id", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, hiddenFlag = true)
	protected String parentAccountTitleId;
	
	@ISEUIModelMapping(fieldName = "name", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, hiddenFlag = true)
	protected String parentAccountTitleName;
	
	@ISEUIModelMapping(fieldName = "parentAccountTitleUUID", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, hiddenFlag = true)
	protected String rootAccountTitleUUID;
	
	@ISEUIModelMapping(fieldName = "id", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, hiddenFlag = true)
	protected String rootAccountTitleId;
	
	@ISEUIModelMapping(fieldName = "name", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, hiddenFlag = true)
	protected String rootAccountTitleName;
	

	@ISEUIModelMapping(fieldName = "originalType", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_originalType", valueFieldName = "originalTypeValue")
	protected int originalType;	
	
	@ISEUIModelMapping(showOnEditor = false)
	protected String originalTypeValue;
	

	@ISEUIModelMapping(fieldName = "settleType", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_settleType", valueFieldName = "settleTypeValue")
	protected int settleType;
	
	@ISEUIModelMapping(showOnEditor = false)
	protected String settleTypeValue;
	
	protected int containsDataFlag;

	public int getFinAccountType() {
		return finAccountType;
	}

	public void setFinAccountType(int finAccountType) {
		this.finAccountType = finAccountType;
	}

	public String getFinAccountTypeValue() {
		return finAccountTypeValue;
	}

	public void setFinAccountTypeValue(String finAccountTypeValue) {
		this.finAccountTypeValue = finAccountTypeValue;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getParentAccountTitleUUID() {
		return parentAccountTitleUUID;
	}

	public void setParentAccountTitleUUID(String parentAccountTitleUUID) {
		this.parentAccountTitleUUID = parentAccountTitleUUID;
	}

	public String getParentAccountTitleId() {
		return parentAccountTitleId;
	}

	public void setParentAccountTitleId(String parentAccountTitleId) {
		this.parentAccountTitleId = parentAccountTitleId;
	}

	public String getParentAccountTitleName() {
		return parentAccountTitleName;
	}

	public void setParentAccountTitleName(String parentAccountTitleName) {
		this.parentAccountTitleName = parentAccountTitleName;
	}

	public String getRootAccountTitleUUID() {
		return rootAccountTitleUUID;
	}

	public void setRootAccountTitleUUID(String rootAccountTitleUUID) {
		this.rootAccountTitleUUID = rootAccountTitleUUID;
	}

	public String getRootAccountTitleId() {
		return rootAccountTitleId;
	}

	public void setRootAccountTitleId(String rootAccountTitleId) {
		this.rootAccountTitleId = rootAccountTitleId;
	}

	public String getRootAccountTitleName() {
		return rootAccountTitleName;
	}

	public void setRootAccountTitleName(String rootAccountTitleName) {
		this.rootAccountTitleName = rootAccountTitleName;
	}

	public int getContainsDataFlag() {
		return containsDataFlag;
	}

	public void setContainsDataFlag(int containsDataFlag) {
		this.containsDataFlag = containsDataFlag;
	}

	public int getOriginalType() {
		return originalType;
	}

	public void setOriginalType(int originalType) {
		this.originalType = originalType;
	}

	public String getOriginalTypeValue() {
		return originalTypeValue;
	}

	public void setOriginalTypeValue(String originalTypeValue) {
		this.originalTypeValue = originalTypeValue;
	}

	public int getSettleType() {
		return settleType;
	}

	public void setSettleType(int settleType) {
		this.settleType = settleType;
	}

	public String getSettleTypeValue() {
		return settleTypeValue;
	}

	public void setSettleTypeValue(String settleTypeValue) {
		this.settleTypeValue = settleTypeValue;
	}

}
