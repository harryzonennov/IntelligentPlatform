package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

/**
 * TODO-LEGACY: Stub replacing legacy platform.foundation.Administration.InstallService.InstallServiceCommonTool.
 */
@Component
public class InstallServiceCommonTool {

    public static final String ID_SUPERUSER = "SUPERUSER";

    /**
     * Batch update all item entities under each parent node by applying the given function.
     * TODO-LEGACY: stub — no-op implementation
     */
    public void batchUpdateAllItemListFromParent(
            ServiceEntityManager seManager,
            String parentNodeName,
            String itemNodeName,
            BiFunction<ServiceEntityNode, ServiceEntityNode, ServiceEntityNode> itemUpdater,
            SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException {
        // TODO-LEGACY: stub — implement when full migration is complete
    }
}
