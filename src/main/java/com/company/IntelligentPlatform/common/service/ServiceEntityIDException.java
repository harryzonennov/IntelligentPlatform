package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

public class ServiceEntityIDException extends ServiceEntityException {

    /**
     *
     */
    private static final long serialVersionUID = -7066274046156034916L;

    /**
     * Exception type: cannot find the version recode
     */
    public static final int TYPE_DUPLICATE_ID = 2;

    public static final int TYPE_SYSTEM_WRONG = 1;

    public ServiceEntityIDException(int errorCode) {
        super(errorCode);
        try {
            String errorMessageFormat = ServiceExceptionHelper.getErrorMessage(
                    LockObjectFailureException.class, errorCode);
            this.errorMessage = String.format(errorMessageFormat);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public ServiceEntityIDException(int errorCode, String var) {
        super(errorCode);
        try {
            String errorMessageFormat = ServiceExceptionHelper.getErrorMessage(
                    LockObjectFailureException.class, errorCode);
            this.errorMessage = String.format(errorMessageFormat, var);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

}
