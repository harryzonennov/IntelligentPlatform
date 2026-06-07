package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseContractMaterialItemUIModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.DocMatItemExcelHandler;
import com.company.IntelligentPlatform.platform.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.platform.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.platform.service.ServiceExcelReportConfig;

@Service
public class PurchaseContractMaterialItemExcelHelper extends DocMatItemExcelHandler {

    @Autowired
    protected PurchaseContractSpecifier purchaseContractSpecifier;

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return purchaseContractSpecifier;
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
        return PurchaseContractMaterialItemUIModel.class;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo)
            throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

}
