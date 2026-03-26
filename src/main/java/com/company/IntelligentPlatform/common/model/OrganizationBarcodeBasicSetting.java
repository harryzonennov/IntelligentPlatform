package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Basic model to record barcode setting for organization for barcode generation
 * rule
 * 
 * @author Zhang,Hang
 *
 */
@Entity
@Table(name = "OrganizationBarcodeBasicSetting", catalog = "platform")
public class OrganizationBarcodeBasicSetting extends ServiceEntityNode {

	public static final int EAN13_COUNTRYHEAD_690 = 690;
	
	public static final int EAN13_COUNTRYHEAD_691 = 691;
	
	public static final int EAN13_COUNTRYHEAD_692 = 692;
	
	public static final int EAN13_COUNTRYHEAD_693 = 693;
	
	public static final int EAN13_COUNTRYHEAD_694 = 694;
	
	public static final int EAN13_COUNTRYHEAD_695 = 695;

	/**
	 * One digital long country head code
	 */
	protected int ean13CountryHead;

	/**
	 * Six digital long country head code
	 */
	protected int ean13CompanyCode;

	protected String refOrganizationUUID;

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.OrganizationBarcodeBasicSetting;

	public OrganizationBarcodeBasicSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
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

	public String getRefOrganizationUUID() {
		return refOrganizationUUID;
	}

	public void setRefOrganizationUUID(String refOrganizationUUID) {
		this.refOrganizationUUID = refOrganizationUUID;
	}

}
