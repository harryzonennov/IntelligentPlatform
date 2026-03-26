package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
@Entity
@Table(name = "SystemResource", schema = "platform")
public class SystemResource extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SystemResource;

	public static final int VIEWTYPE_LIST = 1;

	public static final int VIEWTYPE_DISPLAY_DETAIL = 2;

	public static final int VIEWTYPE_EDIT = 3;
	
	public static final int VIEWTYPE_CHOOSER = 4;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	@ISQLSepcifyAttribute(fieldLength = 200)
	protected String url;
	
	@ISQLSepcifyAttribute(fieldLength = 200)
	protected String absURL;

	protected String regSEName;

	protected String regNodeName;

	protected int viewType;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String uiModelClassName;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String controllerClassName;
	
	protected String refSimAuthorObjectUUID;

	public SystemResource() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.viewType = VIEWTYPE_EDIT;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRegSEName() {
		return regSEName;
	}

	public void setRegSEName(String regSEName) {
		this.regSEName = regSEName;
	}

	public String getRegNodeName() {
		return regNodeName;
	}

	public void setRegNodeName(String regNodeName) {
		this.regNodeName = regNodeName;
	}
	
	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getAbsURL() {
		return absURL;
	}

	public void setAbsURL(String absURL) {
		this.absURL = absURL;
	}

	public String getUiModelClassName() {
		return uiModelClassName;
	}

	public void setUiModelClassName(String uiModelClassName) {
		this.uiModelClassName = uiModelClassName;
	}

	public String getRefSimAuthorObjectUUID() {
		return refSimAuthorObjectUUID;
	}

	public void setRefSimAuthorObjectUUID(String refSimAuthorObjectUUID) {
		this.refSimAuthorObjectUUID = refSimAuthorObjectUUID;
	}

	public String getControllerClassName() {
		return controllerClassName;
	}

	public void setControllerClassName(String controllerClassName) {
		this.controllerClassName = controllerClassName;
	}

}
