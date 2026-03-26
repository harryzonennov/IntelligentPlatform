package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;

public class SerEventException extends ServiceEntityException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ERROR CODE: internal system information
     */
    public static final int TYPE_SYSTEM_WRONG = 1;
    /**
     * ERROR CODE:internal system information
     */
    public static final int PARA_SYSTEM_WRONG = 2;

    private ServiceEntityException sourceException;

    public SerEventException(int errorCode) {
        super(errorCode);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    SerEventException.class, errorCode);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public SerEventException(int errorCode, ServiceEntityException sourceException) {
        super(errorCode);
        this.sourceException = sourceException;
    }

    public ServiceEntityException getSourceException() {
        return sourceException;
    }

    public void setSourceException(ServiceEntityException sourceException) {
        this.sourceException = sourceException;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
