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
@Table(name = "SerExtendPageMetadata", catalog = "platform")
public class SerExtendPageMetadata extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.SerExtendPageMetadata;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;

	public SerExtendPageMetadata() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	@ISQLSepcifyAttribute(subType = ISQLSepcifyAttribute.SUBTYPE_JSON)
	protected String pageMeta;

	protected int itemStatus;

	protected int systemCategory;

	public String getPageMeta() {
		return pageMeta;
	}

	public void setPageMeta(String pageMeta) {
		this.pageMeta = pageMeta;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}
}
