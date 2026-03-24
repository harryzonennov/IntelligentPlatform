package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ServiceAttachmentException extends ServiceEntityRuntimeException {

    private static final long serialVersionUID = 1L;

    public static final int TYPE_SYSTEM_WRONG = 1;

    public static final int PARA_SYSTEM_WRONG = 2;

    public static final int PARA_OVER_SIZE = 3;

    public static final int PARA_WRONG_FORMAT = 4;

    public ServiceAttachmentException(int errorCode) {
        super(errorCode);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    AuthorizationException.class, errorCode);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public ServiceAttachmentException(int errorCode,Object var) {
        super(errorCode);
        try {
            if(var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)){
                String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
                        ServiceAttachmentException.class, errorCode);
                this.errorMessage = String.format(errorMesTamplate, var);
            } else {
                this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                        ServiceAttachmentException.class, errorCode);
            }
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

}
