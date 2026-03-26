package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class DocActionException extends ServiceEntityException {

    public static final int TYPE_SYSTEM_WRONG = 1;

    public static final int PARA_SYSTEM_ERROR = 2;

    public static final int PARA_EMB_ACTION_EXCEPTION = 3;

    public static final int PARA_WRONG_PRE_STATUS = 4;

    public static final int PARA_WRG_CONFIG = 5;

    public static final int PARA_MISS_CONFIG = 6;

    public static final int TYPE_NO_SELECT_ITEM = 7;

    public static final int TYPE_NO_VALID_DATA = 8;

    public static final int PARA_MISS_CONFIG_SPECIFIER = 9;

    public static final int PARA2_OVER_AMOUNT_PREVPROF = 10;

    public static final int PARA_DUPLICATE_ITEM = 11;

    public DocActionException(int errorCode) {
        super(errorCode);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    DocActionException.class, errorCode);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public DocActionException(int errorCode, Object var) {
        super(errorCode);
        try {
            if (var != null && !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
                        DocActionException.class, errorCode);
                this.errorMessage = String.format(errorMesTamplate, var);
            } else {
                this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                        DocActionException.class, errorCode);
            }
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public DocActionException(int type, String var1, String var2) {
        super(type);
        try {
            String errorMesTemplate = ServiceExceptionHelper.getErrorMessage(
                    DocActionException.class, type);
            this.errorMessage = String.format(errorMesTemplate, var1, var2);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

}

