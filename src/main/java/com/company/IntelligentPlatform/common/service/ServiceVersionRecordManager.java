package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// TODO-DAO: import ...ServiceVersionRecordDAO;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceVersionRecord;
import com.company.IntelligentPlatform.common.model.ServiceVersionRecordConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [ServiceVersionRecord]
 *
 * @author
 * @date Mon Sep 09 16:54:07 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class ServiceVersionRecordManager extends ServiceEntityManager {

    // TODO-DAO: @Autowired

    // TODO-DAO:     ServiceVersionRecordDAO serviceVersionRecordDAO;

    @Autowired
    ServiceVersionRecordConfigureProxy serviceVersionRecordConfigureProxy;

    public ServiceVersionRecordManager() {
        super.seConfigureProxy = new ServiceVersionRecordConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new ServiceVersionRecordDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(serviceVersionRecordDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(serviceVersionRecordConfigureProxy);
    }

    /**
     * Default logic to update version value of this version record entity
     *
     * @param id
     * @throws ServiceEntityConfigureException
     */
    public void updateVersion(String id, String logonUserUUID,
                              String organizationUUID) throws ServiceEntityConfigureException {
        ServiceVersionRecord serviceVersionRecord = (ServiceVersionRecord) this
                .getEntityNodeByKey(id, IServiceEntityNodeFieldConstant.ID,
                        ServiceVersionRecord.NODENAME, null, true);
        ServiceVersionRecord serviceVersionRecordBack = (ServiceVersionRecord) serviceVersionRecord.clone();
        serviceVersionRecord.setVersion(serviceVersionRecord.getVersion()
                + serviceVersionRecord.getVersionStep());
        this.updateSENode(serviceVersionRecord, serviceVersionRecordBack, logonUserUUID, organizationUUID);
    }
}
