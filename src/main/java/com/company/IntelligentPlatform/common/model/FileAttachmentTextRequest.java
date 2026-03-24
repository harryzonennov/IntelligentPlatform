package com.company.IntelligentPlatform.common.model;

public class FileAttachmentTextRequest extends ServiceEntityJSONData {
	
	public static final String ATTACHMENTTITLE = "attachmentTitle";
	
	public static final String ATTACHMENTDESCRIPTION = "attachmentDescription";
	
	public static final String BASEUUID = SimpleSEJSONRequest.BASEUUID;
	
	public static final String UUID = SimpleSEJSONRequest.UUID;
	
    protected String uuid;
    
    protected String baseUUID;
	
	protected String attachmentTitle;

	protected String attachmentDescription;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

}
