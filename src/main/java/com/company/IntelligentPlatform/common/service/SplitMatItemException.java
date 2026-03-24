package com.company.IntelligentPlatform.common.service;


import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;


public class SplitMatItemException extends ServiceEntityException {

    public static final int TYPE_SYSTEM_WRONG = 1;

    public static final int PARA_SYSTEM_ERROR = 2;

    public static final int PARA_SPLIT_OVERAMOUNT = 3;

    public static final int PARA_SPLIT_NOMINUS = 4;

    public SplitMatItemException(int errorCode) {
        super(errorCode);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    SplitMatItemException.class, errorCode);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public SplitMatItemException(int errorCode, Object var) {
        super(errorCode);
        try {
            if (var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
                        SplitMatItemException.class, errorCode);
                this.errorMessage = String.format(errorMesTamplate, var);
            } else {
                this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                        SplitMatItemException.class, errorCode);
            }
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public SplitMatItemException(int type, String var1, String var2) {
        super(type);
        try {
            String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
                    SplitMatItemException.class, type);
            this.errorMessage = String.format(errorMesTemplate, var1, var2);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }


}

