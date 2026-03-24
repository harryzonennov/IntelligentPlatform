package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO-LEGACY: import org.flowable.engine.RuntimeService;
// TODO-LEGACY: import org.flowable.engine.TaskService;
// TODO-LEGACY: import org.flowable.engine.runtime.ProcessInstance;
// TODO-LEGACY: import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.FlowableProcessInsUIModel;
import com.company.IntelligentPlatform.common.controller.FlowableTaskUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FlowableTaskManager {

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected TaskService taskService;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected RuntimeService runtimeService;

    protected Logger logger = LoggerFactory.getLogger(FlowableTaskManager.class);

    // TODO-LEGACY: Flowable BPM methods not yet migrated
    // public List<FlowableTaskUIModel> getModuleListCore(List<Task> rawList, LogonInfo logonInfo) { ... }
    // public FlowableTaskUIModel convertToFlowableTaskUIModel(Task task, ...) { ... }
    // public FlowableProcessInsUIModel convertToFlowableProcessUIModel(ProcessInstance processInstance, ...) { ... }

}
