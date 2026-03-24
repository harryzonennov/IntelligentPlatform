package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: import org.flowable.engine.delegate.DelegateExecution;
// TODO-LEGACY: import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

// TODO-LEGACY: implements JavaDelegate removed — Flowable BPM not available
@Service
public class DefDocumentApproveExecution {

    @Autowired
    protected DefDocApproveFlowProxy defDocApproveFlowProxy;

    // TODO-LEGACY: execute(DelegateExecution) not available — Flowable BPM not migrated
    public void execute(Object execution) {
        // no-op stub
    }
}
