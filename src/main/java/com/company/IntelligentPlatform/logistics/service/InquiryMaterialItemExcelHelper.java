package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InquiryMaterialItemUIModel;
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
public class InquiryMaterialItemExcelHelper extends DocMatItemExcelHandler {

    @Autowired
    protected InquirySpecifier inquirySpecifier;

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return inquirySpecifier;
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
        return InquiryMaterialItemUIModel.class;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo)
            throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

}
