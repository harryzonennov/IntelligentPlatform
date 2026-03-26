package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "SystemCodeValueUnion", schema = "platform")
public class SystemCodeValueUnion  extends ServiceEntityNode {
	
	public static final String SENAME = IServiceModelConstants.SystemCodeValueCollection;

	public static final String NODENAME = IServiceModelConstants.SystemCodeValueUnion;
	
	protected int keyType;
	
	protected String iconClass;
	
	protected String colorStyle;
	
	protected int hideFlag; // Whether to hide this value setting temporary 
	
	protected int unionType;

	protected String lanKey;
	
	public SystemCodeValueUnion() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getColorStyle() {
		return colorStyle;
	}

	public void setColorStyle(String colorStyle) {
		this.colorStyle = colorStyle;
	}

	public int getHideFlag() {
		return hideFlag;
	}

	public void setHideFlag(int hideFlag) {
		this.hideFlag = hideFlag;
	}

	public int getUnionType() {
		return unionType;
	}

	public void setUnionType(int unionType) {
		this.unionType = unionType;
	}

	public String getLanKey() {
		return lanKey;
	}

	public void setLanKey(String lanKey) {
		this.lanKey = lanKey;
	}
}
