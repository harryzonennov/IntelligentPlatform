package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SerExtendPageI18n extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.SerExtendPageI18n;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;

	public SerExtendPageI18n() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

	@ISQLSepcifyAttribute(subType = ISQLSepcifyAttribute.SUBTYPE_TEXT)
	protected String propertyContent;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_SHORT)
	protected String lanCode;

	public String getPropertyContent() {
		return propertyContent;
	}

	public void setPropertyContent(String propertyContent) {
		this.propertyContent = propertyContent;
	}

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}
}
