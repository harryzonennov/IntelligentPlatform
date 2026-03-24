package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

public class ServiceEntityInstallationException extends ServiceEntityException {

    private static final long serialVersionUID = 8976005749310056008L;

    public static final int TYPE_SYSTEM_WRONG = 1;

    public static final int PARA_SYSTEM_WRONG = 2;

    public static final int PARA_FAIL_INIT_CLASS = 3;

    public static final int PARA_NON_SE_REGISTERED = 4;

    public static final int PARA_CONFIG_DROPDOWN = 5;

    public static final int PARA_NON_PAK_REGISTERED = 6;

    public static final int PARA_CONFIG_UIMODEL = 7;

    public static final int PARA_FIELD_CONFIG_DROPDOWN = 8;

    public static final int PARA_NON_SUPPORT_DBTYPE = 9;

    public static final int PARA_CONFIG_MSG = 10;

    public static final String MSG_INITIALIZE_CLASS = "[Installation eror] Cannot initialize class instance ";

    public static final String MSG_NOTREGISTERD = "[Installation eror] not registered for SE ";

    public static final String MSG_CONFIG = "[Installation eror] Configuration error ";

    public static final String MSG_CONFIG_DROPDOWN = "[Installation eror] Drop down list Helper Configuration error ";

    public static final String MSG_NO_PACKAGE = "[Installation eror] can not find package registerd";

    public static final String MSG_CONFIG_UIMODEL = "[Installation eror] UI model Configuration error ";

    public static final String MSG_CONFIG_DROPDOWN_MIS = "[Installation eror] No drop down list configure has been " +
            "found on field ";

    public static final String MSG_DBTYPE_NOT_SUPPORT = "[Installation eror] Don't support DB type ";

    public static final String MSG_MESSAGE_CONFIG = "message configure is wrongly configured ";


    public ServiceEntityInstallationException(int type) {
        super(type);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    ServiceEntityInstallationException.class, type);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public ServiceEntityInstallationException(int type, String var1) {
        super(type);
        try {
            String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
                    ServiceEntityInstallationException.class, type);
            this.errorMessage = String.format(errorMesTemplate,var1);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public ServiceEntityInstallationException(int type, String var1, String var2) {
        super(type);
        try {
            String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
                    ServiceEntityInstallationException.class, type) ;
            this.errorMessage = String.format(errorMesTemplate, var1, var2);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

}
