package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;

/**
 * Common Executor template
 */
public interface IComSimExecutor<T, R> {

    R execute(T t, LogonInfo logonInfo) throws ServiceEntityExceptionContainer;
}
