package com.company.IntelligentPlatform.sales.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceWordTemplateHelper {

	private static final Logger logger = LoggerFactory.getLogger(ServiceWordTemplateHelper.class);

	public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replaceInPara(para, params);
		}
	}

	public void generateWordWrapper(XWPFDocument doc, SEUIComModel headerModel,
			List<?> uiModelList) {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replaceWithField(
					para,
					fieldName -> {
						Field field;
						try {
							field = ServiceEntityFieldsHelper.getServiceField(
									headerModel.getClass(), fieldName);
							if (field != null) {
								field.setAccessible(true);
								Object fieldValue = field.get(headerModel);
								if (fieldValue != null) {
									return fieldValue.toString();
								}
							}
						} catch (IllegalArgumentException
								| IllegalAccessException | NoSuchFieldException e) {
							// do nothing
							return null;
						}
						return null;
					});
		}
		List<XWPFTable> xwpfTableList = doc.getTables();
		if (!ServiceCollectionsHelper.checkNullList(uiModelList)
				&& !ServiceCollectionsHelper.checkNullList(xwpfTableList)) {
			for (XWPFTable xwpfTable : xwpfTableList) {
				renderInTableCore(xwpfTable, uiModelList);
			}
		}
	}

	public void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
		List<XWPFRun> runs;
		Matcher matcher;
		if (this.matcher(para.getParagraphText()).find()) {
			runs = para.getRuns();

			int start = -1;
			int end = -1;
			String str = "";
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				logger.debug("------>>>>>>>>>{}", runText);
				if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
					start = i;
				}
				if ((start != -1)) {
					str += runText;
				}
				if ('}' == runText.charAt(runText.length() - 1)) {
					if (start != -1) {
						end = i;
						break;
					}
				}
			}
			logger.debug("start--->{}", start);
			logger.debug("end--->{}", end);
			logger.debug("str---->>>{}", str);
			for (int i = start; i <= end; i++) {
				para.removeRun(i);
				i--;
				end--;
				logger.debug("remove i={}", i);
			}
			for (String key : params.keySet()) {
				if (str.equals(key)) {
					para.createRun().setText((String) params.get(key));
					break;
				}
			}
		}
	}

	private boolean checkValidRunEnd(char targetChar) {
		if ('}' == targetChar || ' ' == targetChar) {
			return true;
		}
		return false;
	}

	public void replaceWithField(XWPFParagraph para,
			Function<String, String> getFieldValueCallback) {
		List<XWPFRun> runs;
		if (this.matcher(para.getParagraphText()).find()) {
			runs = para.getRuns();
			int start = -1;
			int end = -1;
			String str = ServiceEntityStringHelper.EMPTYSTRING;
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				if (runText.length() <= 1) {
					continue;
				}
				if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
					start = i;
				}
				if ((start != -1)) {
					str += runText;
				}
				if (checkValidRunEnd(runText.charAt(runText.length() - 1))) {
					if (start != -1) {
						end = i;
						replaceTextInParaCore(str, para, start, end, getFieldValueCallback);
						// break;
					}
				}
			}
//			if (!ServiceEntityStringHelper.checkNullString(str)
//					& getFieldValueCallback != null) {
//				// Remove all the space.
//				str = str.replaceAll("\\s*", "");
//				String fieldName = str.substring(2, str.length());
//				if (str.charAt(str.length() - 1) == '}') {
//					fieldName = str.substring(2, str.length() - 1);
//				}
//				String fieldValue = getFieldValueCallback.apply(fieldName);
//				for (int i = start; i <= end; i++) {
//					para.removeRun(i);
//					i--;
//					end--;
//				}
//				if (!ServiceEntityStringHelper.checkNullString(fieldValue)) {
//					para.createRun().setText(fieldValue);
//				} else {
//					para.createRun().setText("");
//				}
//			}
		}
	}

	private void replaceTextInParaCore(String rawStr, XWPFParagraph para, int start, int end,
			Function<String, String> getFieldValueCallback) {
		if (!ServiceEntityStringHelper.checkNullString(rawStr)
				& getFieldValueCallback != null) {
			// Remove all the space.
			rawStr = rawStr.replaceAll("\\s*", "");
			String fieldName = rawStr.substring(2, rawStr.length());
			if (rawStr.charAt(rawStr.length() - 1) == '}') {
				fieldName = rawStr.substring(2, rawStr.length() - 1);
			}
			try{
				String fieldValue = getFieldValueCallback.apply(fieldName);
				for (int i = start; i <= end; i++) {
					para.removeRun(i);
					i--;
					end--;
				}
				if (!ServiceEntityStringHelper.checkNullString(fieldValue)) {
					para.createRun().setText(fieldValue);
				} else {
					para.createRun().setText("");
				}
			}catch(Exception e){
				// just continue;
				return;
			}
		}
	}

	public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
		Iterator<XWPFTable> iterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						this.replaceInPara(para, params);
					}
				}
			}
		}
	}

	public void renderInTableCore(XWPFTable xwpfTable, List<?> uiModelList) {
		List<XWPFTableRow> templateTableRows = xwpfTable.getRows();
		if (!ServiceCollectionsHelper.checkNullList(templateTableRows)
				&& !ServiceCollectionsHelper.checkNullList(uiModelList)) {
			XWPFTableRow templateTableRow = templateTableRows.get(1);
			for (Object object : uiModelList) {
				SEUIComModel uiModel = (SEUIComModel) object;
				insertTableRowCore(uiModel, xwpfTable, templateTableRow);
			}
			// Remove template
			// int len = templateTableRows.size();
			xwpfTable.removeRow(1);
			// for (int i = 1; i <= len; i++) {
			// xwpfTable.removeRow(i);
			// }
		}
	}

	private void insertTableRowCore(SEUIComModel uiModel, XWPFTable xwpfTable,
			XWPFTableRow templateTableRow) {
		List<XWPFParagraph> paras;
		xwpfTable.addRow(templateTableRow);
		XWPFTableRow dataRow = xwpfTable.getRow(xwpfTable.getRows().size() - 1);
		List<XWPFTableCell> cells = dataRow.getTableCells();
		if (!ServiceCollectionsHelper.checkNullList(cells)) {
			for (XWPFTableCell cell : cells) {
				paras = cell.getParagraphs();
				for (XWPFParagraph para : paras) {
					this.replaceWithField(
							para,
							fieldName -> {
								Field field;
								try {
									field = ServiceEntityFieldsHelper
											.getServiceField(
													uiModel.getClass(),
													fieldName);
									if (field != null) {
										field.setAccessible(true);
										Object fieldValue = field.get(uiModel);
										if (fieldValue != null) {
											return fieldValue.toString();
										}
									}
								} catch (IllegalArgumentException
										| IllegalAccessException e) {
									// do nothing
									logger.debug("Field not accessible", e);
								} catch (NoSuchFieldException e) {
									return null;
								}
								return null;
							});
				}
			}
		}
	}

	private Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	public void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.warn("Failed to close InputStream", e);
			}
		}
	}

	public void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.warn("Failed to close OutputStream", e);
			}
		}
	}

}