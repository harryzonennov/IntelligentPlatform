package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * SerialNumberSetting UI Model
 ** 
 * @author
 * @date Fri Nov 20 15:25:24 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class SerialNumberSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = SerialNumberSetting.NODENAME, seName = SerialNumberSetting.SENAME, nodeInstID = SerialNumberSetting.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = SerialNumberSetting.NODENAME, seName = SerialNumberSetting.SENAME, nodeInstID = SerialNumberSetting.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "switchFlag", nodeName = SerialNumberSetting.NODENAME, seName = SerialNumberSetting.SENAME, nodeInstID = SerialNumberSetting.SENAME)
	protected int switchFlag;

	@BSearchFieldConfig(fieldName = "category", nodeName = SerialNumberSetting.NODENAME, seName = SerialNumberSetting.SENAME, nodeInstID = SerialNumberSetting.SENAME)
	protected int category;

	@BSearchFieldConfig(fieldName = "category", nodeName = SerialNumberSetting.NODENAME, seName = SerialNumberSetting.SENAME, nodeInstID = SerialNumberSetting.SENAME)
	protected int systemStandardCategory;
	/**
	 * Dummy search field, only be used for page split function on UI
	 * [Important], should be reset as 0 before real search
	 */
	protected int currentPage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSystemStandardCategory() {
		return systemStandardCategory;
	}

	public void setSystemStandardCategory(int systemStandardCategory) {
		this.systemStandardCategory = systemStandardCategory;
	}

}
