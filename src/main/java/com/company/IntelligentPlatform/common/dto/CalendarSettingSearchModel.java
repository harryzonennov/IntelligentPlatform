package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.CalendarSetting;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * CalendarSetting UI Model
 **
 * @author
 * @date Wed Jun 01 15:49:21 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class CalendarSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = CalendarSetting.NODENAME, seName = CalendarSetting.SENAME, nodeInstID = CalendarSetting.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = CalendarSetting.NODENAME, seName = CalendarSetting.SENAME, nodeInstID = CalendarSetting.SENAME)
	protected String name;

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
	
}
