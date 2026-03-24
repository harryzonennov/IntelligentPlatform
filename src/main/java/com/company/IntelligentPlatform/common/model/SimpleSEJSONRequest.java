package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

public class SimpleSEJSONRequest extends ServiceEntityJSONData {

	public static final String TABINDEX = "tabIndex";

	public static final String UUID = IServiceEntityNodeFieldConstant.UUID;

	public static final String BASEUUID = "baseUUID";

	public static final String ID = IServiceEntityNodeFieldConstant.ID;

	public static final String NODENAME = "nodeName";

	public static final String CLIENT = "client";

	public static final String PROCESSDATE = "processDate";

	protected String uuid;

	protected String baseUUID;

	protected String barcode;

	protected String id;

	protected String nodeName;

	protected String client;

	protected String processDate;

	protected int tabIndex;

	protected String content;

	public SimpleSEJSONRequest() {
	}

	public SimpleSEJSONRequest(String uuid, String baseUUID) {
		this.uuid = uuid;
		this.baseUUID = baseUUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
