package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.platform.model.LogonInfo;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

import java.util.List;

/**
 * Common Executor template
 */
public interface IComSimExecutor<T, R> {

    R execute(T t, LogonInfo logonInfo) throws ServiceEntityExceptionContainer;
}
