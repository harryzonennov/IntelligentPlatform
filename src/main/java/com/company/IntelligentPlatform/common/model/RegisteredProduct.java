package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class RegisteredProduct extends MaterialStockKeepUnit{

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public static final String FEILD_SERIALID = "serialId";

	public static final String FEILD_refMaterialSKUUUID = "refMaterialSKUUUID";

	protected String refMaterialSKUUUID;

	protected String serialId;

	protected Date referenceDate;

	protected Date validFromDate;

	protected Date validToDate;

	public RegisteredProduct() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.traceLevel = MaterialStockKeepUnit.TRACELEVEL_INSTANCE;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	public Date getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public Date getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

}
