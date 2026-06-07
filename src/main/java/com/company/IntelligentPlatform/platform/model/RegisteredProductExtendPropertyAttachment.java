package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "RegisteredProductExtendPropertyAttachment", catalog = "platform")
public class RegisteredProductExtendPropertyAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.RegisteredProductExtendPropertyAttachment;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public RegisteredProductExtendPropertyAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
