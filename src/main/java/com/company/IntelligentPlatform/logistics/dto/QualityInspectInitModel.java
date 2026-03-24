package com.company.IntelligentPlatform.logistics.dto;

public class QualityInspectInitModel {
	
	protected String category;
	
	public QualityInspectInitModel(){
		
	}

	public QualityInspectInitModel(int categoryProduction) {
		this.category = categoryProduction + "";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
