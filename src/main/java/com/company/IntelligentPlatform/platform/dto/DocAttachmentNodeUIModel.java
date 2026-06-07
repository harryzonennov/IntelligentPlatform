package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

public class DocAttachmentNodeUIModel extends SEUIComModel {

    protected String attachmentTitle;

    protected String attachmentDescription;

    protected byte[] content;

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

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
