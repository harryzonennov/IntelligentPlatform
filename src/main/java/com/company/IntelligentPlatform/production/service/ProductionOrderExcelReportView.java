package com.company.IntelligentPlatform.production.service;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.company.IntelligentPlatform.production.dto.ProdOrderItemExcelProposal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
// TODO-LEGACY: import org.springframework.web.servlet.view.document.AbstractExcelView; (removed in Spring 5+)

import com.company.IntelligentPlatform.common.service.IServiceExcelConfigureConstants; // migrated from legacy platform.foundation
import com.company.IntelligentPlatform.common.service.ServiceExcelCellConfig;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportConfig;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;
import com.company.IntelligentPlatform.common.service.ServiceExcelXMLConfigureHelper;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;

public class ProductionOrderExcelReportView { // TODO-LEGACY: was extends AbstractExcelView
	
	public static final String LABEL_EXCELREP_CONFIGHEADER = "excelReportHeader";

	protected void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<ServiceExcelReportConfig> serviceExcelReportHeaderList = (List<ServiceExcelReportConfig>) map
				.get(LABEL_EXCELREP_CONFIGHEADER);
		int rowIndex = 0;
		HSSFSheet curSheet = workbook
				.getSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
		if (curSheet == null) {
			curSheet = workbook
					.createSheet(IServiceExcelConfigureConstants.DEF_EXCEL_SHEETNAME);
		}
		if (serviceExcelReportHeaderList != null
				&& serviceExcelReportHeaderList.size() > 0) {			
			for (ServiceExcelReportConfig serviceExcelReportConfig : serviceExcelReportHeaderList) {
				List<ServiceExcelCellConfig> cellConfigList = serviceExcelReportConfig
						.getServiceExcelCellConfigs();
				List<?> content = serviceExcelReportConfig
						.getExcelContent();
				List<Field> fieldList = serviceExcelReportConfig.getFieldList();
				ServiceExcelReportProxy.insertTitle(workbook, curSheet,
						cellConfigList, rowIndex);
				rowIndex++;
				for (Object obj : content) {
					SEUIComModel seData = (SEUIComModel) obj;
					ServiceExcelReportProxy.insertRow(workbook, curSheet, seData, fieldList, cellConfigList, rowIndex);
					rowIndex++;
				}
			}
		}
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
				ServiceExcelReportProxy.insertTitle(workbook, curSheet,
						cellConfigList, rowIndex);
				rowIndex++;
				List<?> content = serviceExcelReportConfig
						.getExcelContent();
				for (Object obj : content) {
					SEUIComModel seData = (SEUIComModel) obj;
					ProdOrderItemExcelProposal purchaseItemExcelProposal = (ProdOrderItemExcelProposal) seData;
					if (purchaseItemExcelProposal.getItemCategory() == ProdOrderItemExcelProposal.ITEM_CATE_PRODITEM){
						// In case production order item
						insertProductionOrderItem(workbook, curSheet, purchaseItemExcelProposal, fieldList,
								cellConfigList, rowIndex);
					}
					if (purchaseItemExcelProposal.getRefDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
						insertProdPurchaseItem(workbook, curSheet, purchaseItemExcelProposal, fieldList,
								cellConfigList, rowIndex);
					}
					if (purchaseItemExcelProposal.getRefDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
						insertOutboundDelivery(workbook, curSheet, purchaseItemExcelProposal, fieldList,
								cellConfigList, rowIndex);
					}
					if (purchaseItemExcelProposal.getRefDocumentType() == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
						insertProdPurchaseItem(workbook, curSheet, purchaseItemExcelProposal, fieldList,
								cellConfigList, rowIndex);
					}
					rowIndex++;
				}
				ServiceExcelReportProxy.insertBlankRow(curSheet, rowIndex);
				rowIndex++;
			}
			// String locFilePath = serviceExcelReportConfig0.getFilePath();
			String locFileName = serviceExcelReportConfig0.getReportName();
			locFileName = locFileName + ".xls";
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(locFileName, "UTF-8") + "\"");
		}
	}
	
	/**
	 * [Internal method] Logic to insert one outbound delivery proposal
	 * @param workbook
	 * @param curSheet
	 * @param fieldList
	 * @param configList
	 * @param rowIndex
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected void insertProductionOrderItem(HSSFWorkbook workbook, HSSFSheet curSheet,
			ProdOrderItemExcelProposal productionOrderItemExcelProposal, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int rowIndex)
			throws IllegalArgumentException, IllegalAccessException {
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
			short color = ServiceExcelReportProxy.convertHSSFontColor(cellConfig.getColor());
			font.setColor(color);
			if(field.getName().equals("itemTitleLabel")){				
				if(productionOrderItemExcelProposal.getItemStatus() == StandardSwitchProxy.SWITCH_ON){
					cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());
					cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
				}else{
					cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
					cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
				}
			}
            if(field.getName().equals("index")){
            	font.setBold(true);
            	if(productionOrderItemExcelProposal.getItemStatus() == StandardSwitchProxy.SWITCH_ON){
            		cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());
					cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
				}else{
					cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
					cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
				}
			}
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
			Object fieldValue = field.get(productionOrderItemExcelProposal);
			if (fieldValue != null) {
				cell.setCellValue(fieldValue.toString());
			}
		}
	}

	/**
	 * [Internal method] Logic to insert one outbound delivery proposal
	 * @param workbook
	 * @param curSheet
	 * @param purchaseItemExcelProposal
	 * @param fieldList
	 * @param configList
	 * @param rowIndex
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected void insertOutboundDelivery(HSSFWorkbook workbook, HSSFSheet curSheet,
			ProdOrderItemExcelProposal purchaseItemExcelProposal, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int rowIndex)
			throws IllegalArgumentException, IllegalAccessException {
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
			short color = ServiceExcelReportProxy.convertHSSFontColor(cellConfig.getColor());
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
			Object fieldValue = field.get(purchaseItemExcelProposal);
			if (fieldValue != null) {
				cell.setCellValue(fieldValue.toString());
			}
		}
	}
	
	/**
	 * [Internal method] Logic to insert one outbound delivery proposal
	 * @param workbook
	 * @param curSheet
	 * @param purchaseItemExcelProposal
	 * @param fieldList
	 * @param configList
	 * @param rowIndex
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected void insertProdPurchaseItem(HSSFWorkbook workbook, HSSFSheet curSheet,
			ProdOrderItemExcelProposal purchaseItemExcelProposal, List<Field> fieldList,
			List<ServiceExcelCellConfig> configList, int rowIndex)
			throws IllegalArgumentException, IllegalAccessException {
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
			short color = ServiceExcelReportProxy.convertHSSFontColor(cellConfig.getColor());
			font.setColor(color);
			if(field.getName().equals("itemTitleLabel")){				
				cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
				cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
			}
            if(field.getName().equals("index")){            	
            	cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
            	cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
			}
            if(field.getName().equals("amount")){            	
            	cellStyle.setFillForegroundColor(HSSFColorPredefined.CORAL.getIndex());
            	cellStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
			}
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
			Object fieldValue = field.get(purchaseItemExcelProposal);
			if (fieldValue != null) {
				cell.setCellValue(fieldValue.toString());
			}
		}
	}


}
