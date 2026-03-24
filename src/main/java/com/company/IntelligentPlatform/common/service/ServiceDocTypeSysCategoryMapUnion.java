package com.company.IntelligentPlatform.common.service;

/**
 * Union model for mapping from service document type to system resource category
 * @author zhang,hang
 *
 */
public class ServiceDocTypeSysCategoryMapUnion {
	
	protected String categoryID;
	
	protected int documentType;

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	

}
