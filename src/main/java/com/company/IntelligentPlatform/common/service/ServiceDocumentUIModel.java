package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;


/**
 * Dummy UI model class to manage the document type field
 *
 * @author Zhang, hang
 */
public class ServiceDocumentUIModel extends SEUIComModel {

    @ISEDropDownResourceMapping(resouceMapping = "ServiceDocument_documentType", valueFieldName =
            "refDocumentTypeValue")
    protected int documentType;

    protected String documentTypeValue;

    @ISEDropDownResourceMapping(resouceMapping = "ServiceDocument_dummyDocumentType", valueFieldName =
            "refDocumentTypeValue")
    protected String dummyDocumentType;

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeValue() {
        return documentTypeValue;
    }

    public void setDocumentTypeValue(String documentTypeValue) {
        this.documentTypeValue = documentTypeValue;
    }

    public String getDummyDocumentType() {
        return dummyDocumentType;
    }

    public void setDummyDocumentType(String dummyDocumentType) {
        this.dummyDocumentType = dummyDocumentType;
    }

}
