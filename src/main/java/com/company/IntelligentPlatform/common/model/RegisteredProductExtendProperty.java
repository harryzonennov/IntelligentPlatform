package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class RegisteredProductExtendProperty extends MaterialSKUExtendProperty{

	public static final String NODENAME = IServiceModelConstants.RegisteredProductExtendProperty;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public RegisteredProductExtendProperty() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
