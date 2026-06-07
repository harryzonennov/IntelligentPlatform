package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "QualityInsMatItemAttachment", catalog = "logistics")
public class QualityInsMatItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.QualityInsMatItemAttachment;

	public static final String SENAME = IServiceModelConstants.QualityInspectOrder;

	public QualityInsMatItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}
