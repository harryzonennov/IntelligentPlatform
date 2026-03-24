package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;


@Component
public class CalendarTemplateSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "year", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME)
	protected int year;

	@BSearchFieldConfig(fieldName = "note", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "client", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME)
	protected String client;

	@BSearchFieldConfig(fieldName = "name", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = CalendarTemplate.NODENAME, seName = CalendarTemplate.SENAME, nodeInstID = CalendarTemplate.SENAME)
	protected String uuid;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
