package com.company.IntelligentPlatform.common.service;

/**
 * TODO-LEGACY: Stub replacing org.flowable.engine.repository.DeploymentBuilder.
 */
public interface DeploymentBuilder {

    DeploymentBuilder addClasspathResource(String resource);

    DeploymentBuilder name(String name);

    Deployment deploy();
}
