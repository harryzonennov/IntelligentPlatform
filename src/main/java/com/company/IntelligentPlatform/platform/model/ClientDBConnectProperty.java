package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "ClientDBConnectProperty", catalog = "platform")
public class ClientDBConnectProperty extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "ClientDBConnectProperty";

	protected String clientID;

	protected String dbUserName;

	protected String dbPassword;

	protected String schemaID;

	public ClientDBConnectProperty() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getSchemaID() {
		return schemaID;
	}

	public void setSchemaID(String schemaID) {
		this.schemaID = schemaID;
	}

}
