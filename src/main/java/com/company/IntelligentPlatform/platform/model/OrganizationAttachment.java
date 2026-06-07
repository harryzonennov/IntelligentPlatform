package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "OrganizationAttachment", catalog = "platform")
public class OrganizationAttachment extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.OrganizationAttachment;

	public static final String SENAME = IServiceModelConstants.Organization;
	
	public OrganizationAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}
