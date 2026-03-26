package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "SystemCodeValueCollection", catalog = "platform")
public class SystemCodeValueCollection  extends ServiceEntityNode {
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SystemCodeValueCollection;
	
	public static int CATEGORY_PUBLIC = 1;
	
	public static int CATEGORY_PRIVATE = 2;	

	public static int KEYTYPE_INT = 1;
	
	public static int KEYTYPE_STRING = 2;

	public static int COLTYPE_SYSTEM = 1;
	
	public static int COLTYPE_CUSTOM = 2;
	
	protected int collectionCategory;
	
	protected int keyType;
	
	protected int collectionType;
	
	public SystemCodeValueCollection() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.collectionCategory = CATEGORY_PUBLIC;
		this.keyType = KEYTYPE_INT;
		this.collectionType = COLTYPE_SYSTEM;
	}

	public int getCollectionCategory() {
		return collectionCategory;
	}

	public void setCollectionCategory(int collectionCategory) {
		this.collectionCategory = collectionCategory;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	public int getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(int collectionType) {
		this.collectionType = collectionType;
	}

}
