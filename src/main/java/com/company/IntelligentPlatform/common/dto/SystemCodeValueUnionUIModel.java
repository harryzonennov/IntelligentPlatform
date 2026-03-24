package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SystemCodeValueUnionUIModel extends SEUIComModel {

	protected int keyType;
	
	protected String keyTypeValue;
	
	protected String iconClass;
	
	protected String colorStyle;
	
	protected int hideFlag;
	
	protected String hideFlagValue;

	protected int unionType;
	
	protected String unionTypeValue;

	protected String lanKey;


	public int getKeyType() {
		return this.keyType;
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

	public String getHideFlagValue() {
		return hideFlagValue;
	}

	public void setHideFlagValue(String hideFlagValue) {
		this.hideFlagValue = hideFlagValue;
	}

	public int getUnionType() {
		return unionType;
	}

	public void setUnionType(int unionType) {
		this.unionType = unionType;
	}

	public String getUnionTypeValue() {
		return unionTypeValue;
	}

	public void setUnionTypeValue(String unionTypeValue) {
		this.unionTypeValue = unionTypeValue;
	}

	public String getLanKey() {
		return lanKey;
	}

	public void setLanKey(String lanKey) {
		this.lanKey = lanKey;
	}
}
