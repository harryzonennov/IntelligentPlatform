package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.PricingSetting;

@Component
public class PricingSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "name", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "defaultTaxRate", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected double defaultTaxRate;

	@BSearchFieldConfig(fieldName = "note", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected String note;

	@BSearchFieldConfig(fieldName = "multiCurrencyFlag", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected boolean multiCurrencyFlag;

	@BSearchFieldConfig(fieldName = "id", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "defCurrencyCode", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected String defCurrencyCode;

	@BSearchFieldConfig(fieldName = "client", nodeName = PricingSetting.NODENAME, seName = PricingSetting.SENAME, nodeInstID = PricingSetting.SENAME)
	protected String client;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDefaultTaxRate() {
		return this.defaultTaxRate;
	}

	public void setDefaultTaxRate(double defaultTaxRate) {
		this.defaultTaxRate = defaultTaxRate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean getMultiCurrencyFlag() {
		return this.multiCurrencyFlag;
	}

	public void setMultiCurrencyFlag(boolean multiCurrencyFlag) {
		this.multiCurrencyFlag = multiCurrencyFlag;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDefCurrencyCode() {
		return this.defCurrencyCode;
	}

	public void setDefCurrencyCode(String defCurrencyCode) {
		this.defCurrencyCode = defCurrencyCode;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

}
