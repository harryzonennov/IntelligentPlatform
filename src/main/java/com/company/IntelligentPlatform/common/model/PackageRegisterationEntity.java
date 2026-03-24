package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

/**
 * Package registeration Entity, using ID to store package Name
 * 
 * @author ZhangHang
 * @date Nov 26, 2012
 * 
 */

public class PackageRegisterationEntity extends ServiceEntityNode {

	public static String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static String SENAME = "PackageRegisterationEntity";

	protected String seRegisterDAOType;

	protected String databaseSubName;

	public PackageRegisterationEntity() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public String getSeRegisterDAOType() {
		return seRegisterDAOType;
	}

	public void setSeRegisterDAOType(String seRegisterDAOType) {
		this.seRegisterDAOType = seRegisterDAOType;
	}

	public String getDatabaseSubName() {
		return databaseSubName;
	}

	public void setDatabaseSubName(String databaseSubName) {
		this.databaseSubName = databaseSubName;
	}

}
