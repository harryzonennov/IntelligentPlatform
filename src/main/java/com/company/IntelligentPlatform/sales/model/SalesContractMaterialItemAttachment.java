package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class SalesContractMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesContractMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.SalesContract;

	public SalesContractMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}


}
