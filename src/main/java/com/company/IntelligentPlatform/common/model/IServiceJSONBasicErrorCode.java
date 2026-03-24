package com.company.IntelligentPlatform.common.model;

// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus;

/**
 * Standard Service entity JSON Error Code Constants For message maintenance,
 * please refer to platform.foundation.Model.Common.ServiceCommonResource
 * properties files
 *
 * @author Zhang,Hang
 *
 */
public interface IServiceJSONBasicErrorCode {

	int DEF_OK = 200; // HttpStatus.SC_OK

	int UNKNOWN_SYS_ERROR = 500; // HttpStatus.SC_INTERNAL_SERVER_ERROR

	int DUPLICATE_ID = 409; // HttpStatus.SC_CONFLICT

	int NULL_ID = 204; // HttpStatus.SC_NO_CONTENT

}
