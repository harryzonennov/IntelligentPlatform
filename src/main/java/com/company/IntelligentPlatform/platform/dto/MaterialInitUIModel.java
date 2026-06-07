package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

public class MaterialInitUIModel extends SEUIComModel {
	
	protected String materialTypeUUID;
	
	protected String materialTypeName;
	
	protected int materialCategory;

	public String getMaterialTypeUUID() {
		return materialTypeUUID;
	}

	public void setMaterialTypeUUID(String materialTypeUUID) {
		this.materialTypeUUID = materialTypeUUID;
	}

	public String getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public int getMaterialCategory() {
		return materialCategory;
	}

	public void setMaterialCategory(int materialCategory) {
		this.materialCategory = materialCategory;
	}

}
