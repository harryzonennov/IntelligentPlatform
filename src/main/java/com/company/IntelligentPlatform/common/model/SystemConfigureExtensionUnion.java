package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemConfigureExtensionUnion extends ReferenceNode {	
	
	
	public static final String NODENAME = IServiceModelConstants.SystemConfigureExtensionUnion;

	public static final String SENAME = SystemConfigureCategory.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public SystemConfigureExtensionUnion() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}
	
	protected String configureValueName;
	
	protected String configureValueId;
	
	protected String configureValue;
	
	protected String refCodeValueUUID;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String configureSwitchProxy;

	public static int getNodeCategory() {
		return nodeCategory;
	}

	public static void setNodeCategory(int nodeCategory) {
		SystemConfigureExtensionUnion.nodeCategory = nodeCategory;
	}

	public String getConfigureValueName() {
		return configureValueName;
	}

	public void setConfigureValueName(String configureValueName) {
		this.configureValueName = configureValueName;
	}

	public String getConfigureValueId() {
		return configureValueId;
	}

	public void setConfigureValueId(String configureValueId) {
		this.configureValueId = configureValueId;
	}

	public String getConfigureValue() {
		return configureValue;
	}

	public void setConfigureValue(String configureValue) {
		this.configureValue = configureValue;
	}

	public String getRefCodeValueUUID() {
		return refCodeValueUUID;
	}

	public void setRefCodeValueUUID(String refCodeValueUUID) {
		this.refCodeValueUUID = refCodeValueUUID;
	}

	public String getConfigureSwitchProxy() {
		return configureSwitchProxy;
	}

	public void setConfigureSwitchProxy(String configureSwitchProxy) {
		this.configureSwitchProxy = configureSwitchProxy;
	}

}
