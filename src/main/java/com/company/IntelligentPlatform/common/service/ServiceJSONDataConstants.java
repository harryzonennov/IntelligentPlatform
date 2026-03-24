package com.company.IntelligentPlatform.common.service;

public class ServiceJSONDataConstants {

	public static final String ELE_ERROR_CODE = "errorCode";
	
	public static final String ELE_SUBERROR_CODE = "subErrorCode";

	public static final String ELE_ERROR_MSG = "errorMessage";

	public static final String ELE_API_KEY = "apiKey";

	public static final String ELE_CONTENT = "content";

	public static final String PROP_VALUE = "value";

	public static final String MSG_UNKNOWN_ERROR = "Unkown error";

	public static final int ERROR_CODE_OK = 1;

	public static final int ERROR_CODE_UNKNOWNERROR = 200;

	public final static String DEF_UNKONWON_ERROR_JSON = "{\"" + ELE_ERROR_CODE
			+ "\":\"" + ERROR_CODE_UNKNOWNERROR + "\","
			+ "\"LABEL_ERROR_MSG\":\"" + MSG_UNKNOWN_ERROR + "\"}";

	public final static String DEF_SIMPLE_SUCCESS_JSON = "{\"" + ELE_ERROR_CODE
			+ "\":\"" + ERROR_CODE_OK + "\"}";

}
