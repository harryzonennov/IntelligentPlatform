package com.company.IntelligentPlatform.common.service;

/**
 * TODO-LEGACY: Stub replacing org.flowable.engine.RepositoryService.
 */
public interface RepositoryService {

    DeploymentBuilder createDeployment();

    void deleteDeployment(String deploymentId, boolean cascade);
}
