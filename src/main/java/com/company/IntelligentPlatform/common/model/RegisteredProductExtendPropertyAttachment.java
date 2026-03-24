package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class RegisteredProductExtendPropertyAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.RegisteredProductExtendPropertyAttachment;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public RegisteredProductExtendPropertyAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
