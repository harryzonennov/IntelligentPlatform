package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class InventoryCheckAttachmentUIModel extends SEUIComModel {

	protected String attachmentTitle;

	protected String attachmentDescription;
	
	protected String fileType;

	public String getAttachmentTitle() {
		return attachmentTitle;
	}

	public void setAttachmentTitle(String attachmentTitle) {
		this.attachmentTitle = attachmentTitle;
	}

	public String getAttachmentDescription() {
		return attachmentDescription;
	}

	public void setAttachmentDescription(String attachmentDescription) {
		this.attachmentDescription = attachmentDescription;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
