package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Class to record logging for service entity node instance
 * 
 * @author ZhangHang
 * @date Nov 25, 2012
 * 
 */
public class ServiceEntityLogModel extends ReferenceNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceEntityLogModel;

	public static final int MESSAGE_TYPE_INFO = 1;

	public static final int MESSAGE_TYPE_WARNING = 2;

	public static final int MESSAGE_TYPE_ERROR = 3;

	/**
	 * the value is equal to ServiceEntityBindModel.processMode
	 */
	protected int processMode;

	protected int messageType;

	public ServiceEntityLogModel() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public int getProcessMode() {
		return processMode;
	}

	public void setProcessMode(int processMode) {
		this.processMode = processMode;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

}
