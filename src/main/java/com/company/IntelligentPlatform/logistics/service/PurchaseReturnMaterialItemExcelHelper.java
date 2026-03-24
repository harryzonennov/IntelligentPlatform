package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseReturnMaterialItemUIModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocMatItemExcelHandler;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;

@Service
public class PurchaseReturnMaterialItemExcelHelper extends DocMatItemExcelHandler {

    @Autowired
    protected PurchaseReturnOrderSpecifier purchaseReturnOrderSpecifier;

    public static final String RESOURCE_FILE = "PurchaseReturnMaterialItem";

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return purchaseReturnOrderSpecifier;
    }

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> basicFieldNameList = DocMatItemExcelHandler.getDefDocMatItemFieldNameList();
        return basicFieldNameList;
    }

    @Override
    public boolean checkCustomUploadExcel(String configureName, String client) throws ServiceEntityConfigureException {
        return false;
    }

    @Override
    public boolean checkCustomDownloadExcel(String configureName, String client)
            throws ServiceEntityConfigureException {
        return false;
    }


    @Override
    public Class<?> getExcelModelClass() {
        return PurchaseReturnMaterialItemUIModel.class;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }
}
