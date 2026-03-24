package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.IServiceExcelConfigureConstants; // migrated from legacy platform.foundation
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportErrorLogExcelModel;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportErrorLogUnion;

@Service
public class ServiceExcelUploadResponseView extends ServiceExcelReportView {

	@Override
	protected void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int rowIndex = 0;
		@SuppressWarnings("unchecked")
		List<ServiceExcelReportConfig> serviceExcelReportConfigList = (List<ServiceExcelReportConfig>) map
				.get(ServiceExcelReportProxy.LABEL_EXCELREP_CONFIGLIST);
		if (serviceExcelReportConfigList != null
				&& serviceExcelReportConfigList.size() > 0) {
			ServiceExcelReportConfig serviceExcelReportConfig0 = serviceExcelReportConfigList
					.get(0);
			for (ServiceExcelReportConfig serviceExcelReportConfig : serviceExcelReportConfigList) {
				List<ServiceExcelCellConfig> cellConfigList = serviceExcelReportConfig
						.getServiceExcelCellConfigs();
				List<Field> fieldList = serviceExcelReportConfig.getFieldList();
				HSSFSheet curSheet = workbook
						.getSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
				if (curSheet == null) {
					curSheet = workbook
							.createSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
				}
				ServiceExcelReportProxy.insertTitle(workbook, curSheet,
						cellConfigList, rowIndex);
				rowIndex++;
				List<?> content = serviceExcelReportConfig
						.getExcelContent();
				for (Object obj : content) {
					SEUIComModel seData = (SEUIComModel) obj;
					ServiceExcelReportErrorLogExcelModel serviceExcelReportErrorLogExcelModel = (ServiceExcelReportErrorLogExcelModel) seData;
					insertRow(workbook, curSheet,
							serviceExcelReportErrorLogExcelModel, fieldList,
							cellConfigList, rowIndex);
					rowIndex++;
				}
				ServiceExcelReportProxy.insertBlankRow(curSheet, rowIndex);
				rowIndex++;
			}
			// String locFilePath = serviceExcelReportConfig0.getFilePath();
			String locFileName = serviceExcelReportConfig0.getReportName();
			locFileName = locFileName + ".xls";
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(locFileName, StandardCharsets.UTF_8) + "\"");
		}
	}

	public static void insertRow(
			HSSFWorkbook workbook,
			HSSFSheet curSheet,
			ServiceExcelReportErrorLogExcelModel serviceExcelReportErrorLogExcelModel,
			List<Field> fieldList, List<ServiceExcelCellConfig> configList,
			int rowIndex) throws IllegalArgumentException,
			IllegalAccessException {
		HSSFRow row = curSheet.createRow(rowIndex);
		for (Field field : fieldList) {
			ServiceExcelCellConfig cellConfig = ServiceExcelXMLConfigureHelper
					.getFieldConfigByName(configList, field.getName());
			if (cellConfig == null) {
				continue;
			}
			// Set cell style
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			// Set color value
			short color = ServiceExcelReportProxy
					.convertHSSFontColor(cellConfig.getColor());
			font.setColor(color);
			int columIndex = cellConfig.getColIndex();
			// Set weight
			if (cellConfig.getFieldWeight() > 0) {
				curSheet.setColumnWidth(columIndex, cellConfig.getFieldWeight()
						* ServiceExcelCellConfig.SIZE_FACTOR);
			}
			cellStyle.setWrapText(false);
			cellStyle.setFont(font);
			cellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			field.setAccessible(true);
			HSSFCell cell = row.createCell(columIndex);
			cell.setCellStyle(cellStyle);
			Object fieldValue = field.get(serviceExcelReportErrorLogExcelModel);
			if (ServiceExcelReportErrorLogExcelModel.FIELD_ITEMSTATUS
					.equals(field.getName())) {
				if (serviceExcelReportErrorLogExcelModel.getErrorCategory() == ServiceExcelReportErrorLogUnion.CATEGORY_ERROR) {
					cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
					cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
				}
				if (serviceExcelReportErrorLogExcelModel.getErrorCategory() == ServiceExcelReportErrorLogUnion.CATEGORY_WARN) {
					cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
					cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
				}
			}
			if (fieldValue != null) {
				cell.setCellValue(fieldValue.toString());
			}
		}
	}

}
