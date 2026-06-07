package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.platform.model.LogonInfo;

import java.util.Map;

/**
 * Common Executor template
 */
public interface IComMapExecutor {

    <R> R execute(Map<String, Object> inputMap, LogonInfo logonInfo) throws ServiceEntityExceptionContainer;
}
