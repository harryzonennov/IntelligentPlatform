package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.ServiceEntityExceptionContainer;
import com.company.IntelligentPlatform.common.model.LogonInfo;

import java.util.Map;

/**
 * Common Executor template
 */
public interface IComMapExecutor {

    <R> R execute(Map<String, Object> inputMap, LogonInfo logonInfo) throws ServiceEntityExceptionContainer;
}
