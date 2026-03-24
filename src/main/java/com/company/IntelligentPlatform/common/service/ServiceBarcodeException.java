package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

public class ServiceBarcodeException extends ServiceEntityException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    /*
     * ERROR CODE:unknown others system error
     */
    public static final int TYPE_SYSTEM_WRONG = 1;

    public static final int PARA_SYSTEM_WRONG = 2;

    public static final int PARA_WRONG_ENCODETYPE = 3;

    public static final int PARA_NO_ENCODETYPE = 4;

    public static final int PARA_NOSUPPORT_ENCODETYPE = 5;

    public static final int PARA_WRONG_BARCODE = 6;

    public static final int PARA_WRONGLENGTH_BARCODE = 7;

    public static final int TYPE_INVALID_CODEGENTYPE_COMBINE = 8;

    public static final int TYPE_NO_HOMEORG_BARCODESETTING = 9;

    public ServiceBarcodeException(int errorCode) {
        super(errorCode);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    ServiceBarcodeException.class, errorCode);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public ServiceBarcodeException(int errorCode, Object var) {
        super(errorCode);
        try {
            if (var != null & !var.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                String errorMesTamplate = ServiceExceptionHelper.getErrorMessage(
                        ServiceBarcodeException.class, errorCode);
                this.errorMessage = String.format(errorMesTamplate, var);
            } else {
                this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                        ServiceBarcodeException.class, errorCode);
            }
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

}
