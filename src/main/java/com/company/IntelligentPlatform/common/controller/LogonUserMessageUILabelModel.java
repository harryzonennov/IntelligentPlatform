package com.company.IntelligentPlatform.common.controller;

public class LogonUserMessageUILabelModel {
	
	protected int category;
	
	protected int currentPage;
	
	protected boolean categoryACCAUDFlag;
	
	protected boolean categoryACCVERFlag;
	
	protected boolean categoryACCREDFlag;
	
	protected boolean categoryWASAFEFlag;
	
	protected boolean categoryTRANDELFlag;
	
	protected boolean categoryTRNTRAFlag;
	
	protected boolean categoryBKNTRAFlag;
	
	protected boolean categoryVEHCONFlag;
	
    public LogonUserMessageUILabelModel(){
    	// Set all label flag as false firstly
    	this.categoryACCAUDFlag = false;
    	this.categoryACCREDFlag = false;
    	this.categoryACCVERFlag = false;    	
    	this.categoryTRANDELFlag = false;
    	this.categoryWASAFEFlag = false;
    	this.categoryTRNTRAFlag = false;
    	this.categoryBKNTRAFlag = false;
    	this.categoryVEHCONFlag = false;
    }
    
	public boolean isCategoryACCAUDFlag() {
		return categoryACCAUDFlag;
	}

	public void setCategoryACCAUDFlag(boolean categoryACCAUDFlag) {
		this.categoryACCAUDFlag = categoryACCAUDFlag;
	}

	public boolean isCategoryACCVERFlag() {
		return categoryACCVERFlag;
	}

	public void setCategoryACCVERFlag(boolean categoryACCVERFlag) {
		this.categoryACCVERFlag = categoryACCVERFlag;
	}

	public boolean isCategoryACCREDFlag() {
		return categoryACCREDFlag;
	}

	public void setCategoryACCREDFlag(boolean categoryACCREDFlag) {
		this.categoryACCREDFlag = categoryACCREDFlag;
	}	

	public boolean isCategoryWASAFEFlag() {
		return categoryWASAFEFlag;
	}

	public void setCategoryWASAFEFlag(boolean categoryWASAFEFlag) {
		this.categoryWASAFEFlag = categoryWASAFEFlag;
	}

	public boolean isCategoryTRANDELFlag() {
		return categoryTRANDELFlag;
	}

	public void setCategoryTRANDELFlag(boolean categoryTRANDELFlag) {
		this.categoryTRANDELFlag = categoryTRANDELFlag;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isCategoryTRNTRAFlag() {
		return categoryTRNTRAFlag;
	}

	public void setCategoryTRNTRAFlag(boolean categoryTRNTRAFlag) {
		this.categoryTRNTRAFlag = categoryTRNTRAFlag;
	}

	public boolean isCategoryBKNTRAFlag() {
		return categoryBKNTRAFlag;
	}

	public void setCategoryBKNTRAFlag(boolean categoryBKNTRAFlag) {
		this.categoryBKNTRAFlag = categoryBKNTRAFlag;
	}

	public boolean isCategoryVEHCONFlag() {
		return categoryVEHCONFlag;
	}

	public void setCategoryVEHCONFlag(boolean categoryVEHCONFlag) {
		this.categoryVEHCONFlag = categoryVEHCONFlag;
	}
	
}
