package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

public class ServiceVersionException extends ServiceEntityException {

    /**
     *
     */
    private static final long serialVersionUID = -8486220197514621036L;

    /**
     * Exception type: cannot find the version recode
     */
    public static final int TYPE_CANNOT_FIND_RECORD = 2;

    public static final int TYPE_SYSTEM_WRONG = 1;

    public ServiceVersionException(int errorCode) {
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

}
