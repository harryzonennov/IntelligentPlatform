package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "QualityInsOrderAttachment", catalog = "logistics")
public class QualityInsOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.QualityInsOrderAttachment;

	public static final String SENAME = IServiceModelConstants.QualityInspectOrder;

	public QualityInsOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}
}
