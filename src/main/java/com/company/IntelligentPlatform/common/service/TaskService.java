package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

/**
 * TODO-LEGACY: Stub replacing org.flowable.engine.TaskService.
 */
public interface TaskService {

    TaskQuery createTaskQuery();

    void complete(String taskId);

    void complete(String taskId, Map<String, Object> variables);

    void setAssignee(String taskId, String userId);

    void claim(String taskId, String userId);

    interface TaskQuery {
        TaskQuery processInstanceBusinessKey(String businessKey);
        TaskQuery processInstanceId(String processInstanceId);
        TaskQuery taskAssignee(String assignee);
        TaskQuery taskId(String taskId);
        List<Task> list();
        Task singleResult();
    }
}
