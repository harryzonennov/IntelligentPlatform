package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DocAttachmentNode extends ServiceEntityNode {

    protected byte[] content;

    protected String fileType;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
