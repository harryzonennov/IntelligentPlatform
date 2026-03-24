package com.company.IntelligentPlatform.common.service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServiceConcurrentLockUnit {

    protected String uuid;

    protected ReentrantReadWriteLock reentrantReadWriteLock;

    protected String writeLockBy;

    public ServiceConcurrentLockUnit() {
    }

    public ServiceConcurrentLockUnit(String uuid, ReentrantReadWriteLock reentrantReadWriteLock, String writeLockBy) {
        this.uuid = uuid;
        this.reentrantReadWriteLock = reentrantReadWriteLock;
        this.writeLockBy = writeLockBy;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ReentrantReadWriteLock getReentrantReadWriteLock() {
        return reentrantReadWriteLock;
    }

    public void setReentrantReadWriteLock(ReentrantReadWriteLock reentrantReadWriteLock) {
        this.reentrantReadWriteLock = reentrantReadWriteLock;
    }

    public String getWriteLockBy() {
        return writeLockBy;
    }

    public void setWriteLockBy(String writeLockBy) {
        this.writeLockBy = writeLockBy;
    }
}
