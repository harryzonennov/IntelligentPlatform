package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

public class LockObjectFailureException extends ServiceEntityRuntimeException {

    public LockObjectFailureException(int errorCode) {
        super(errorCode);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    LockObjectFailureException.class, errorCode);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public LockObjectFailureException(int errorCode, String var) {
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

    /**
     *
     */
    private static final long serialVersionUID = 4777137700775452482L;

    public static final int TYPE_SYSTEM_WRONG = 1;

    /**
     * ERROR CODE:already lock by other user
     */
    public static final int TYPE_ALREADY_LOCK_BYOTHER = 2;

    /**
     * ERROR CODE:lock SE faiure
     */
    public static final int TYPE_LOCK_FAILURE = 3;

}
