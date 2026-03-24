package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

/**
 * Service System Installation Exception class
 *
 * @author Zhang, Hang
 * @date 2013-4-11
 */
public class ServiceSystemInstallationException extends ServiceEntityException {
    /**
     *
     */
    private static final long serialVersionUID = 1046082850601095565L;

    /**
     * Exception type: other system wrong error
     */
    public static final int TYPE_SYSTEM_WRONG = 1;

    /**
     * Exception type: SE not registered
     */
    public static final int TYPE_SE_NOT_REGISTERD = 2;

    /**
     * Exception type:common configuration error
     */
    public static final int TYPE_WRG_CONFIGURE = 3;

    /**
     * Exception type: drop down configuration error
     */
    public static final int TYPE_CONFIG_DROPDOWN = 4;

    /**
     * Exception type: UI model configuration error
     */
    public static final int TYPE_CONFIG_UIMODEL = 5;

    /**
     * Exception type: DB type not supported
     */
    public static final int TYPE_DBTYPE_NOT_SUPPORT = 6;

    /**
     * Exception type: message helper configuration error
     */
    public static final int TYPE_MESSAGE_CONFIG = 7;

    /**
     * Exception type: can not initial class instance dynamically by framework
     */
    public static final int TYPE_INITIALIZE_CLASS = 8;

    /**
     * Exception type: can not meet the requirement in installation
     */
    public static final int TYPE_INSTREQ_NOT_MATCH = 9;

    /**
     * @param type :Log on Exception type
     */
    public ServiceSystemInstallationException(int type) {
        super(type);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    ServiceSystemInstallationException.class, type);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public ServiceSystemInstallationException(int type, String var1) {
        super(type);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    ServiceSystemInstallationException.class, type)
                    + " "
                    + var1;
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

}
