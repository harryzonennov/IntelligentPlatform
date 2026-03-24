package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

/**
 * TODO-LEGACY: Stub replacing org.flowable.engine.RuntimeService.
 */
public interface RuntimeService {

    void suspendProcessInstanceById(String processInstanceId);

    void activateProcessInstanceById(String processInstanceId);

    void deleteProcessInstance(String processInstanceId, String reason);

    ProcessInstanceQuery createProcessInstanceQuery();

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

    void setVariables(String executionId, Map<String, ? extends Object> variables);

    void setVariable(String executionId, String variableName, Object value);

    Object getVariable(String executionId, String variableName);
}
