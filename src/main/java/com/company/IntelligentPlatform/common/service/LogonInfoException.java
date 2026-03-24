package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus; // replaced by local HttpStatus stub

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

/**
 * Log on Exception class
 *
 * @author Zhang, Hang
 * @date 2013-4-11
 */
public class LogonInfoException extends ServiceEntityException {

    public static final String LOGON_PAGE = "../common/index.html";

    /**
     *
     */
    private static final long serialVersionUID = 1046082850601095565L;

    /**
     * Exception type: no user can be found
     */
    public static final int PARA_NO_USER_FOUND = 1;

    /**
     * Exception type: not correct password
     */
    public static final int TYPE_WRONG_PASS = 2;

    /**
     * Exception type: no organization belongs to this login user
     */
    public static final int TYPE_NO_ORGNIZATION = 3;

    /**
     * Exception type: no login user
     */
    public static final int TYPE_NO_LOGON_USER = 4;

    /**
     * Exception type: different login user on same web session
     */
    public static final int TYPE_DIF_USER = 5;

    /**
     * Exception type: no organization belongs to this login user
     */
    public static final int TYPE_WRG_ORGNIZATION = 6;

    /**
     * Exception type: no organization belongs to this login user
     */
    public static final int TYPE_SYSTEM_WRONG = 7;

    /**
     * Exception type: no logon user for the equipment
     */
    public static final int TYPE_NO_USER_FOR_EQUIP = 8;

    /**
     * Exception type: input empty password
     */
    public static final int TYPE_EMP_PASSWORD = 9;

    /**
     * Exception type: input empty user name
     */
    public static final int TYPE_EMP_USERNAME = 10;

    /**
     * Exception type:confirmed password doesn't match
     */
    public static final int TYPE_NOMATCH_PASSWORD = 11;

    /**
     * Exception type:confirmed password doesn't match
     */
    public static final int TYPE_NO_HOSTCOMPANY = 12;

    /**
     * Exception type:the client not exist on the system.
     */
    public static final int PARA_NO_CLIENT = 13;

    protected final String ERRORPAGE_NO_USER = "LogonAction";

    public static final String SC_SUBERROR_LOGONFAILED = "401.1";

    protected String errorPage;

    /**
     * @param type :Log on Exception type
     */
    public LogonInfoException(int type) {
        super(type);
        try {
            this.errorCode = HttpStatus.SC_UNAUTHORIZED;
            this.subErrorCode = SC_SUBERROR_LOGONFAILED;
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    LogonInfoException.class, type);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = SYSTEM_ERROR;
            this.errorMessage = ex.getMessage();
        }
    }

    public LogonInfoException(int type, String var1) {
        super(type);
        try {
            this.errorCode = HttpStatus.SC_UNAUTHORIZED;
            this.subErrorCode = SC_SUBERROR_LOGONFAILED;
            String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
                    LogonInfoException.class, type);
            this.errorMessage = String.format(errorMesTemplate, var1);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = SYSTEM_ERROR;
            this.errorMessage = ex.getMessage();
        }
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

}
