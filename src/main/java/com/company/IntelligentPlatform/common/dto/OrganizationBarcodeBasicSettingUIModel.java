package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.OrganizationBarcodeBasicSetting;

public class OrganizationBarcodeBasicSettingUIModel  extends SEUIComModel {
	
	@ISEUIModelMapping(fieldName = "uuid", seName = OrganizationBarcodeBasicSetting.SENAME, nodeName = OrganizationBarcodeBasicSetting.NODENAME, 
			nodeInstID = OrganizationBarcodeBasicSetting.NODENAME, hiddenFlag = true, searchFlag = false, secId = OrganizationBarcodeBasicSetting.NODENAME, tabId = TABID_BASIC)
	protected String uuid;
	
	@ISEUIModelMapping(fieldName = "refOrganizationUUID", seName = OrganizationBarcodeBasicSetting.SENAME, nodeName = OrganizationBarcodeBasicSetting.NODENAME, 
			nodeInstID = OrganizationBarcodeBasicSetting.NODENAME, hiddenFlag = true, searchFlag = false, secId = OrganizationBarcodeBasicSetting.NODENAME, tabId = TABID_BASIC)
	protected String refOrganizationUUID;
	
	@ISEUIModelMapping(fieldName = "ean13CountryHead", seName = OrganizationBarcodeBasicSetting.SENAME, nodeName = OrganizationBarcodeBasicSetting.NODENAME, 
			nodeInstID = OrganizationBarcodeBasicSetting.NODENAME, hiddenFlag = true, searchFlag = false, secId = OrganizationBarcodeBasicSetting.NODENAME, tabId = TABID_BASIC)
	protected int ean13CountryHead;
	
	@ISEUIModelMapping(fieldName = "ean13CompanyCode", seName = OrganizationBarcodeBasicSetting.SENAME, nodeName = OrganizationBarcodeBasicSetting.NODENAME, 
			nodeInstID = OrganizationBarcodeBasicSetting.NODENAME, hiddenFlag = true, searchFlag = false, secId = OrganizationBarcodeBasicSetting.NODENAME, tabId = TABID_BASIC)
	protected int ean13CompanyCode;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRefOrganizationUUID() {
		return refOrganizationUUID;
	}

	public void setRefOrganizationUUID(String refOrganizationUUID) {
		this.refOrganizationUUID = refOrganizationUUID;
	}

	public int getEan13CountryHead() {
		return ean13CountryHead;
	}

	public void setEan13CountryHead(int ean13CountryHead) {
		this.ean13CountryHead = ean13CountryHead;
	}

	public int getEan13CompanyCode() {
		return ean13CompanyCode;
	}

	public void setEan13CompanyCode(int ean13CompanyCode) {
		this.ean13CompanyCode = ean13CompanyCode;
	}
	
}
