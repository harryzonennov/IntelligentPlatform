package com.company.IntelligentPlatform.common.service;

import java.util.List;

/**
 * TODO-LEGACY: Stub replacing org.flowable.engine.runtime.ProcessInstanceQuery.
 */
public interface ProcessInstanceQuery {

    ProcessInstanceQuery processInstanceBusinessKey(String businessKey);

    ProcessInstanceQuery processInstanceId(String processInstanceId);

    List<ProcessInstance> list();

    ProcessInstance singleResult();
}
