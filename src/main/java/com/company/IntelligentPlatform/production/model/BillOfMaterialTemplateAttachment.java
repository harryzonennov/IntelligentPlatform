package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class BillOfMaterialTemplateAttachment extends BillOfMaterialAttachment {

	public static final String NODENAME = IServiceModelConstants.BillOfMaterialTemplateAttachment;

	public static final String SENAME = IServiceModelConstants.BillOfMaterialTemplate;

	public BillOfMaterialTemplateAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
