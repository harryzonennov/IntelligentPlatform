package com.company.IntelligentPlatform.salesDistribution.service;

import com.company.IntelligentPlatform.salesDistribution.dto.SalesReturnMaterialItemUIModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.platform.service.DocMatItemExcelHandler;
import com.company.IntelligentPlatform.platform.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.platform.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

import java.util.List;

@Service
public class SalesReturnMaterialItemExcelHelper extends DocMatItemExcelHandler {

    @Autowired
    protected SalesReturnOrderSpecifier salesForcastSpecifier;

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return salesForcastSpecifier;
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
        return SalesReturnMaterialItemUIModel.class;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo)
            throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

}