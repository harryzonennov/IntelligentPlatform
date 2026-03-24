package com.company.IntelligentPlatform.common.model;

/**
 * Node not found or not configured in Entity exception
 * 
 * @author ZhangHang
 * @date Nov 7, 2012
 * 
 */
public class NodeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String message1 = "Node Name:[";

	public static final String message2 = "] not found in Entity!";

	public NodeNotFoundException(String nodeName) {
		super(message1 + nodeName + message2);
	}

}
