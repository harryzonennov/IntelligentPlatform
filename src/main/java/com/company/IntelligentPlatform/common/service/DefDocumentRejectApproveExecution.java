package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: import org.flowable.engine.delegate.DelegateExecution;
// TODO-LEGACY: import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

@Service
// TODO-LEGACY: Flowable BPM not yet migrated - implements JavaDelegate
public class DefDocumentRejectApproveExecution {

    @Autowired
    protected DefDocApproveFlowProxy defDocApproveFlowProxy;

    // TODO-LEGACY: public void execute(DelegateExecution execution) { ... }

}
