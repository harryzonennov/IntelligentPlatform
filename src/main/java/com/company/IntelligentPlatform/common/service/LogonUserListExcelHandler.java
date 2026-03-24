package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.dto.LogonUserUIModel;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogonUserListExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected LogonUserSpecifier logonUserSpecifier;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("id"));
        fieldNameList.add(new FieldNameUnit("name"));
        fieldNameList.add(new FieldNameUnit("roleName"));
        fieldNameList.add(new FieldNameUnit("roleId"));
        fieldNameList.add(new FieldNameUnit("organizationId"));
        fieldNameList.add(new FieldNameUnit("organizationName"));
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
        return LogonUserUIModel.class;
    }

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return logonUserSpecifier;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

    @Override
    public String getConfigureName() {
        return LogonUser.SENAME;
    }

    @Override
    protected List<MetaModelConfigure<LogonUserUIModel>> getMetaModelConfigure(
            SerialLogonInfo serialLogonInfo) {
        return null;
    }

    @Override
    protected List<FieldMeta<LogonUserUIModel>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException {
        List<FieldMeta<LogonUserUIModel>> fieldMetaList = new ArrayList<>();
        fieldMetaList.add(new FieldMeta<>("statusValue", "status",
                logonUserManager.initStatusMap(serialLogonInfo.getLanguageCode()), LogonUser.STATUS_INIT));
        return fieldMetaList;
    }

}
