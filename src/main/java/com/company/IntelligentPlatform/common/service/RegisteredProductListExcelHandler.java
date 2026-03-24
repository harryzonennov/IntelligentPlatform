package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.RegisteredProductUIModel;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisteredProductListExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected RegisteredProductSpecifier registeredProductSpecifier;

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("serialId"));
        fieldNameList.add(new FieldNameUnit("traceStatusValue", "traceStatus", "traceStatus"));
        fieldNameList.add(new FieldNameUnit("refMaterialSKUId"));
        fieldNameList.add(new FieldNameUnit("refMaterialSKUName"));
        fieldNameList.addAll(MaterialListExcelHandler.genCommonMaterialFieldNameList());
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
        return RegisteredProductUIModel.class;
    }

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return registeredProductSpecifier;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

    @Override
    public String getConfigureName() {
        return RegisteredProduct.SENAME;
    }

    @Override
    protected <U extends SEUIComModel> List<MetaModelConfigure<U>> getMetaModelConfigure(
            SerialLogonInfo serialLogonInfo) {
        return null;
    }

    @Override
    protected <U extends SEUIComModel> List<FieldMeta<U>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException {
        return null;
    }

}
