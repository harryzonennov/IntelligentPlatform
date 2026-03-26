package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Entity to store each service entity registration information
 * 
 * @author ZhangHang
 * @date Nov 25, 2012
 * 
 */
@Entity
@Table(name = "ServiceEntityRegisterEntity", schema = "platform")
public class ServiceEntityRegisterEntity extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceEntityRegisterEntity;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	protected String seManagerType;

	protected String seManagerName;

	protected String seDAOType;

	protected String seDAOName;

	protected String seProxyType;

	protected String seProxyName;

	protected String seModuleType;

	protected String seModuleName;

	protected String packageName;

	public ServiceEntityRegisterEntity() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public String getSeManagerType() {
		return seManagerType;
	}

	public void setSeManagerType(String seManagerType) {
		this.seManagerType = seManagerType;
	}

	public String getSeDAOType() {
		return seDAOType;
	}

	public void setSeDAOType(String seDAOType) {
		this.seDAOType = seDAOType;
	}

	public String getSeProxyType() {
		return seProxyType;
	}

	public void setSeProxyType(String seProxyType) {
		this.seProxyType = seProxyType;
	}

	public String getSeModuleType() {
		return seModuleType;
	}

	public void setSeModuleType(String seModuleType) {
		this.seModuleType = seModuleType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSeManagerName() {
		return seManagerName;
	}

	public void setSeManagerName(String seManagerName) {
		this.seManagerName = seManagerName;
	}

	public String getSeDAOName() {
		return seDAOName;
	}

	public void setSeDAOName(String seDAOName) {
		this.seDAOName = seDAOName;
	}

	public String getSeProxyName() {
		return seProxyName;
	}

	public void setSeProxyName(String seProxyName) {
		this.seProxyName = seProxyName;
	}

	public String getSeModuleName() {
		return seModuleName;
	}

	public void setSeModuleName(String seModuleName) {
		this.seModuleName = seModuleName;
	}

}
