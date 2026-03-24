package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.EmployeeUIModel;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelHandlerProxy;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeListExcelHandler extends ServiceExcelHandlerProxy {

    @Autowired
    protected EmployeeSpecifier employeeSpecifier;

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected OrganizationManager organizationManager;

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.add(new FieldNameUnit("id"));
        fieldNameList.add(new FieldNameUnit("name"));
        fieldNameList.add(new FieldNameUnit("genderValue"));
        fieldNameList.add(new FieldNameUnit("operateTypeValue"));
        fieldNameList.add(new FieldNameUnit("workRoleValue"));
        fieldNameList.add(new FieldNameUnit("jobLevelValue"));
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
        return EmployeeUIModel.class;
    }

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return employeeSpecifier;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo) throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }

    @Override
    public String getConfigureName() {
        return Employee.SENAME;
    }

    @Override
    protected List<MetaModelConfigure<EmployeeUIModel>> getMetaModelConfigure(SerialLogonInfo serialLogonInfo) {
        return null;
    }

    @Override
    protected List<FieldMeta<EmployeeUIModel>> getFieldMetaList(SerialLogonInfo serialLogonInfo)
            throws ServiceEntityInstallationException {
        List<FieldMeta<EmployeeUIModel>> fieldMetaList = new ArrayList<>();
        fieldMetaList.add(new FieldMeta<>("genderValue", "gender",
                employeeManager.initGenderMap(serialLogonInfo.getLanguageCode()), Employee.GENDER_MALE));
        fieldMetaList.add(new FieldMeta<>("statusValue", "status",
                employeeManager.initStatusMap(serialLogonInfo.getLanguageCode()), Employee.STATUS_INIT));
        fieldMetaList.add(new FieldMeta<>("workRoleValue", "workRole",
                employeeManager.initWorkRoleMap(serialLogonInfo.getLanguageCode(), serialLogonInfo.getClient(),
                        false), null));
        fieldMetaList.add(new FieldMeta<>("jobLevelValue", "jobLevel",
                employeeManager.initJobLevelMap(serialLogonInfo.getLanguageCode(), serialLogonInfo.getClient(),
                        false), null));
        return fieldMetaList;
    }

}
