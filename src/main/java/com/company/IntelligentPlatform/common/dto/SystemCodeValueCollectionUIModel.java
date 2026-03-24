package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;

public class SystemCodeValueCollectionUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "collectionCategory", seName = SystemCodeValueCollection.SENAME, nodeName = SystemCodeValueCollection.NODENAME, nodeInstID = SystemCodeValueCollection.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "SystemCodeValueCollection_collectionCategory", valueFieldName = "collectionCategoryValue")
	protected int collectionCategory;
	
    protected String collectionCategoryValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "SystemCodeValueCollection_keyType", valueFieldName = "collectionCategoryValue")
	protected int keyType;
	
    protected String keyTypeValue;
	
    @ISEDropDownResourceMapping(resouceMapping = "SystemCodeValueCollection_collectionType", valueFieldName = "collectionCategoryValue")
	protected int collectionType;
	
    protected String collectionTypeValue;

	public int getCollectionCategory() {
		return this.collectionCategory;
	}

	public void setCollectionCategory(int collectionCategory) {
		this.collectionCategory = collectionCategory;
	}

	public String getCollectionCategoryValue() {
		return collectionCategoryValue;
	}

	public void setCollectionCategoryValue(String collectionCategoryValue) {
		this.collectionCategoryValue = collectionCategoryValue;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	public String getKeyTypeValue() {
		return keyTypeValue;
	}

	public void setKeyTypeValue(String keyTypeValue) {
		this.keyTypeValue = keyTypeValue;
	}

	public int getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

	public String getCollectionTypeValue() {
		return collectionTypeValue;
	}

	public void setCollectionTypeValue(String collectionTypeValue) {
		this.collectionTypeValue = collectionTypeValue;
	}

}
