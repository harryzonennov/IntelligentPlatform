package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InboundItemUIModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocMatItemExcelHandler;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;

@Service
public class InboundItemExcelHelper extends DocMatItemExcelHandler {

    @Autowired
    protected InboundDeliverySpecifier inboundDeliverySpecifier;

    @Override
    public DocumentContentSpecifier getDocumentSpecifier() {
        return inboundDeliverySpecifier;
    }

    @Override
    public List<FieldNameUnit> getDefFieldNameList() {
        List<FieldNameUnit> basicFieldNameList = DocMatItemExcelHandler.getDefDocMatItemFieldNameList();
        List<FieldNameUnit> fieldNameList = new ArrayList<>();
        fieldNameList.addAll(basicFieldNameList);
        fieldNameList.add(6, new FieldNameUnit("refWarehouseId"));
        fieldNameList.add(7, new FieldNameUnit("refWarehouseName"));
        fieldNameList.add(8, new FieldNameUnit("refWarehouseAreaId"));
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
        return InboundItemUIModel.class;
    }

    @Override
    public ServiceExcelReportConfig getDefExcelConfigure(SerialLogonInfo serialLogonInfo)
            throws ServiceExcelConfigException {
        return this.getDefExcelConfigureTemplate(serialLogonInfo);
    }
}
