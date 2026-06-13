package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "RegisteredProductExtendProperty", catalog = "platform")
public class RegisteredProductExtendProperty extends MaterialSKUExtendProperty{

	public static final String NODENAME = IServiceModelConstants.RegisteredProductExtendProperty;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public RegisteredProductExtendProperty() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
