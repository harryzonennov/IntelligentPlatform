package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocConsumerUnion extends ServiceEntityNode{	
	
	protected String uiModelType;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String uiModelTypeFullName;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String i18nFullPath;
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceDocConsumerUnion;

	public ServiceDocConsumerUnion() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}	


	public String getUiModelType() {
		return uiModelType;
	}

	public void setUiModelType(String uiModelType) {
		this.uiModelType = uiModelType;
	}

	public String getUiModelTypeFullName() {
		return uiModelTypeFullName;
	}

	public void setUiModelTypeFullName(String uiModelTypeFullName) {
		this.uiModelTypeFullName = uiModelTypeFullName;
	}

	public String getI18nFullPath() {
		return i18nFullPath;
	}

	public void setI18nFullPath(String i18nFullPath) {
		this.i18nFullPath = i18nFullPath;
	}

}
