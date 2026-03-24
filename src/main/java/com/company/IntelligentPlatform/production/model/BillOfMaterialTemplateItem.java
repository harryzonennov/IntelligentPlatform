package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class BillOfMaterialTemplateItem extends BillOfMaterialItem {


	public final static String NODENAME = IServiceModelConstants.BillOfMaterialTemplateItem;

	public final static String SENAME = BillOfMaterialTemplate.SENAME;

	public BillOfMaterialTemplateItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = BillOfMaterialTemplate.STATUS_INITIAL;
	}

}
