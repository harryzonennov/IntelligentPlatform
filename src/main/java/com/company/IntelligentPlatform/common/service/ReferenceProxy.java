package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Proxy class to provide core service for reference node
 * TODO-LEGACY: Legacy DAO-based reference resolution not yet migrated (IServiceEntityDAO, PackageRegisterationEntityDAO removed)
 */
public class ReferenceProxy {

    private static final ReferenceProxy instance = new ReferenceProxy();

    public final static ReferenceProxy getInstance() {
        return instance;
    }

    private ReferenceProxy() {
    }

    /**
     * TODO-LEGACY: Legacy DAO-based reference resolution not yet migrated
     */
    @SuppressWarnings("rawtypes")
    public ServiceEntityNode getRefTarget(ReferenceNode source)
            throws ServiceEntityConfigureException {
        // TODO-LEGACY: IServiceEntityDAO, PackageRegisterationEntityDAO not available in Spring Boot 3 migration
        throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG);
    }

    /**
     * Build the reference information from target node to reference node
     */
    public void buildReferenceNode(ServiceEntityNode target,
                                   ReferenceNode refNode, String refPackageName)
            throws ServiceEntityConfigureException {
        refNode.setRefNodeName(target.getNodeName());
        refNode.setRefSEName(target.getServiceEntityName());
        refNode.setRefUUID(target.getUuid());
        if (refPackageName != null
                && !refPackageName.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
            refNode.setRefPackageName(refPackageName);
        }
        // TODO-LEGACY: packageRegDAO/seRegDAO not available — package name retrieval from persistency not migrated
    }

}
