package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// TODO-LEGACY: import org.springframework.web.servlet.view.document.AbstractExcelView; (removed in Spring 5+)

// TODO-LEGACY: import platform.foundation.Administration.InstallService.Configure.IServiceExcelConfigureConstants;
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceExcelReportView { // TODO-LEGACY: was extends AbstractExcelView

    protected void buildExcelDocument(Map<String, Object> map,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int rowIndex = 0;
        @SuppressWarnings("unchecked")
        List<ServiceExcelReportConfig> serviceExcelReportConfigList =
                (List<ServiceExcelReportConfig>) map.get(ServiceExcelReportProxy.LABEL_EXCELREP_CONFIGLIST);
        if (serviceExcelReportConfigList != null && serviceExcelReportConfigList.size() > 0) {
            ServiceExcelReportConfig serviceExcelReportConfig0 = serviceExcelReportConfigList.get(0);
            for (ServiceExcelReportConfig serviceExcelReportConfig : serviceExcelReportConfigList) {
                List<ServiceExcelCellConfig> cellConfigList = serviceExcelReportConfig.getServiceExcelCellConfigs();
                List<Field> fieldList = serviceExcelReportConfig.getFieldList();
                HSSFSheet curSheet = workbook.getSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
                if (curSheet == null) {
                    curSheet = workbook.createSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
                }
                ServiceExcelReportProxy.insertTitle(workbook, curSheet, cellConfigList, rowIndex);
                rowIndex++;
                List<?> content = serviceExcelReportConfig.getExcelContent();
                for (Object object : content) {
                    SEUIComModel seData = (SEUIComModel) object;
                    ServiceExcelReportProxy.insertRow(workbook, curSheet, seData, fieldList, cellConfigList, rowIndex);
                    rowIndex++;
                }
                ServiceExcelReportProxy.insertBlankRow(curSheet, rowIndex);
                rowIndex++;
            }
            //String locFilePath = serviceExcelReportConfig0.getFilePath();
            String locFileName = serviceExcelReportConfig0.getReportName();
            locFileName = locFileName + ".xls";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(locFileName, StandardCharsets.UTF_8) + "\"");
        }
    }

}
