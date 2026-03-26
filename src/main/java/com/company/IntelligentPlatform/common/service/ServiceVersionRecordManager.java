package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.ServiceVersionRecordRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

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
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected ServiceVersionRecordRepository serviceVersionRecordDAO;
    @Autowired
    ServiceVersionRecordConfigureProxy serviceVersionRecordConfigureProxy;

    public ServiceVersionRecordManager() {
        super.seConfigureProxy = new ServiceVersionRecordConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, serviceVersionRecordDAO));
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
