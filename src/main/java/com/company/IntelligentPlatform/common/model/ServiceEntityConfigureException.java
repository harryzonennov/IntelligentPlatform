package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

/**
 * Exception class to handle Service Entity Configuration Exception
 * 
 * @author ZhangHang
 * @date Nov 10, 2012
 * 
 */
public class ServiceEntityConfigureException extends ServiceEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1996679240269035420L;

	public static final int TYPE_SYSTEM_WRONG = 1;

	public static final int PARA_SYSTEM_WRONG = 2;

	public static final int PARA_NON_NODE = 3;

	public static final int PARA_NON_FIELD = 4;

	public static final int PARA_CANNOT_DELETE = 5;

	public static final int PARA_INIT_SE = 6;

	public static final int PARA_NO_PARENT_NODE = 7;

	public static final String MSG_CANNOT_INIT_SE = "[SE Configure error] Can not init SE instance";

	public static final String MSG_NON_NODE = "[SE Configure error] Can not find the node:";

	public static final String MSG_CANNOT_DEL = "Can not delete SE instance, reason:";

	public static final String MSG_NON_FIELD = "[SE Configure error] Can not find the field:";

	public ServiceEntityConfigureException(int type) {
		super(type);
		try {
			this.errorMessage = ServiceExceptionHelper.getErrorMessage(
					ServiceEntityConfigureException.class, type);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceEntityConfigureException(int type, String var1) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceEntityConfigureException.class, type);
			this.errorMessage = String.format(errorMesTemplate,var1);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

	public ServiceEntityConfigureException(int type, String var1, String var2) {
		super(type);
		try {
			String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
					ServiceEntityConfigureException.class, type) ;
			this.errorMessage = String.format(errorMesTemplate, var1, var2);
		} catch (ServiceEntityInstallationException ex) {
			super.errorCode = TYPE_SYSTEM_WRONG;
			this.errorMessage = ex.getMessage();
		}
	}

//	public ServiceEntityConfigureException(String msg) {
//		super(msg);
//	}
//
//	/**
//	 * Handle nodeName cannot be find in Configuration
//	 *
//	 * @param msg
//	 * @param nodeName
//	 *            :Service Entity node name
//	 */
//	public ServiceEntityConfigureException(String msg, String nodeName) {
//		super(msg + nodeName);
//	}
//
//	/**
//	 * Handle nodeName cannot be find in Configuration
//	 *
//	 * @param seName
//	 *            : Service Entity name
//	 * @param msg
//	 * @param nodeName
//	 *            :Service Entity node name
//	 */
//	public ServiceEntityConfigureException(String msg, String seName,
//			String nodeName) {
//		super(msg + nodeName + " in SE:" + seName);
//	}

}
