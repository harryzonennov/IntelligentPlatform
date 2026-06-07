package com.company.IntelligentPlatform.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.CorporateCustomerUIModel;
import com.company.IntelligentPlatform.platform.dto.EmployeeUIModel;
import com.company.IntelligentPlatform.platform.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.platform.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.platform.model.CorporateCustomer;
import com.company.IntelligentPlatform.platform.model.Employee;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorporateCustomerListExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected CorporateCustomerSpecifier corporateCustomerSpecifier;

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("id"));
        fieldNameList.add(new FieldNameUnit("name"));
        fieldNameList.add(new FieldNameUnit("address"));
        fieldNameList.add(new FieldNameUnit("telephone"));
        fieldNameList.add(new FieldNameUnit("mobile"));
        fieldNameList.add(new FieldNameUnit("cityName"));
        return fieldNameList;
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
        return CorporateCustomerUIModel.class;
    }

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return corporateCustomerSpecifier;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

    @Override
    public String getConfigureName() {
        return CorporateCustomer.SENAME;
    }

    @Override
    protected <CorporateCustomerUIModel extends SEUIComModel> List<MetaModelConfigure<CorporateCustomerUIModel>> getMetaModelConfigure(
            SerialLogonInfo serialLogonInfo) {
        return null;
    }

    @Override
    protected <CorporateCustomerUIModel extends SEUIComModel> List<FieldMeta<CorporateCustomerUIModel>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException {
       return null;
    }

}
