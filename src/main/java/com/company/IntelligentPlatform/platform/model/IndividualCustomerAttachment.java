package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "IndividualCustomerAttachment", catalog = "platform")
public class IndividualCustomerAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.IndividualCustomerAttachment;

	public static final String SENAME = IServiceModelConstants.IndividualCustomer;

	public IndividualCustomerAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}
}
