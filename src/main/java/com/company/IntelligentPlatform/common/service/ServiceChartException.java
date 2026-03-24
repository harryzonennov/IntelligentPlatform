package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;

/**
 * General Chart Exception class
 *
 * @author Zhang, Hang
 */
public class ServiceChartException extends ServiceEntityException {

    /**
     *
     */
    private static final long serialVersionUID = -7820058271305490305L;

    public ServiceChartException(int type) {
        super(type);
        try {
            this.errorMessage = ServiceExceptionHelper.getErrorMessage(
                    ServiceChartException.class, type);
        } catch (ServiceEntityInstallationException ex) {
            super.errorCode = TYPE_SYSTEM_WRONG;
            this.errorMessage = ex.getMessage();
        }
    }

    public static final int TYPE_SYSTEM_WRONG = 1;

    public static final int TYPE_WRONG_TIME_SET = 2;

    public static final int TYPE_TOOLONG_TS = 3;

    public int getExceptionCategory(int errorCode) {
        return ServiceExceptionRecord.CATEGORY_NORMAL;
    }

}
