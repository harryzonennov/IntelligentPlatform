package com.company.IntelligentPlatform.common.service;

/**
 * TODO-LEGACY: Stub replacing org.flowable.engine.runtime.ProcessInstance.
 */
public interface ProcessInstance {

    String getId();

    String getProcessInstanceId();

    String getBusinessKey();

    String getProcessDefinitionId();

    String getProcessDefinitionKey();

    boolean isSuspended();

    boolean isEnded();
}
