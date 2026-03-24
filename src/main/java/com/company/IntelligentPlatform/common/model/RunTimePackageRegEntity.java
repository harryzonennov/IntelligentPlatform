package com.company.IntelligentPlatform.common.model;

// TODO-DAO: import platform.foundation.DAO.ServiceEntityRegisterEntityDAO;

public class RunTimePackageRegEntity {

	protected Object seRegisterDAO; // TODO-DAO: was ServiceEntityRegisterEntityDAO

	protected String databaseSubName;

	protected String packageName;

	public Object getSeRegisterDAO() { // TODO-DAO: was ServiceEntityRegisterEntityDAO
		return seRegisterDAO;
	}

	public void setSeRegisterDAO(
			Object seRegisterDAOType) { // TODO-DAO: was ServiceEntityRegisterEntityDAO
		this.seRegisterDAO = seRegisterDAOType;
	}

	public String getDatabaseSubName() {
		return databaseSubName;
	}

	public void setDatabaseSubName(String databaseSubName) {
		this.databaseSubName = databaseSubName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
