package com.company.IntelligentPlatform.common.service;

import org.flowable.task.service.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @author puhaiyang
 * @date 2018/12/19
 */
public class ManagerTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("i00103");
    }
}
