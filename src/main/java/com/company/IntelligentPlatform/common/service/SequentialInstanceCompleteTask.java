package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

import java.io.Serializable;

/**
 * Execution Listener to decide weather to break and stop whole process
 */
@Service
public class SequentialInstanceCompleteTask implements Serializable {

    // TODO-LEGACY: Flowable BPM not yet migrated
    // public boolean accessCondition(DelegateExecution execution){ ... }

}
