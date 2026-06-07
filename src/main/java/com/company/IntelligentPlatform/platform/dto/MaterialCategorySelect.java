package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.Material;

public class MaterialCategorySelect {
	
	protected int materialCategory;
	
	protected String materialCategoryComment;
	
	public MaterialCategorySelect(){
		// set default one
		materialCategory = Material.MATECATE_RAW_MATERIAL;
	}

	public int getMaterialCategory() {
		return materialCategory;
	}

	public void setMaterialCategory(int materialCategory) {
		this.materialCategory = materialCategory;
	}

	public String getMaterialCategoryComment() {
		return materialCategoryComment;
	}

	public void setMaterialCategoryComment(String materialCategoryComment) {
		this.materialCategoryComment = materialCategoryComment;
	}
	
}
