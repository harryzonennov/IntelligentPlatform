package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class ServiceConcurrentProxy {

    private static final ConcurrentMap<String, ServiceConcurrentLockUnit> lockMap = new ConcurrentHashMap<>();

    protected static Logger logger = LoggerFactory.getLogger(ServiceConcurrentProxy.class);

    public static void writeLock(String uuid, String writeLockBy){
        ServiceConcurrentLockUnit serviceConcurrentLockUnit = getOrCreateLockUnit(uuid, writeLockBy);
        ReentrantReadWriteLock.WriteLock writeLock = serviceConcurrentLockUnit.getReentrantReadWriteLock().writeLock();
        writeLock.lock();
    }

    public static void unLockWrite(String uuid){
        ServiceConcurrentLockUnit existedLockUnit = getLockUnit(uuid);
        ReentrantReadWriteLock.WriteLock writeLock = existedLockUnit.getReentrantReadWriteLock().writeLock();
        writeLock.unlock();
        tryRemoveLockMapEntity(existedLockUnit, uuid);
    }

    public static void readLock(String uuid, String readLockBy){
        ServiceConcurrentLockUnit serviceConcurrentLockUnit = getOrCreateLockUnit(uuid, readLockBy);
        ReentrantReadWriteLock.ReadLock readLock = serviceConcurrentLockUnit.getReentrantReadWriteLock().readLock();
        readLock.lock();
    }

    public static void unLockRead(String uuid){
        ServiceConcurrentLockUnit existedLockUnit = getLockUnit(uuid);
        if(existedLockUnit == null){
            logger.error("No read lock found when unlock:" + uuid);
            return;
        }
        ReentrantReadWriteLock.ReadLock readLock = existedLockUnit.getReentrantReadWriteLock().readLock();
        readLock.unlock();
        tryRemoveLockMapEntity(existedLockUnit, uuid);
    }

    private static void tryRemoveLockMapEntity(ServiceConcurrentLockUnit existedLockUnit, String uuid){
        if(existedLockUnit.getReentrantReadWriteLock().getReadLockCount() == 0
                && existedLockUnit.getReentrantReadWriteLock().isWriteLocked()){
            lockMap.remove(uuid);
        }
    }

    private static ServiceConcurrentLockUnit getOrCreateLockUnit(String uuid, String writeLockBy){
        ServiceConcurrentLockUnit existedLockUnit = getLockUnit(uuid);
        if(existedLockUnit != null){
            return existedLockUnit;
        }
        ServiceConcurrentLockUnit serviceConcurrentLockUnit = new ServiceConcurrentLockUnit(uuid,
                new ReentrantReadWriteLock(), writeLockBy);
        lockMap.put(uuid, serviceConcurrentLockUnit);
        return lockMap.get(uuid);
    }

    private static ServiceConcurrentLockUnit getLockUnit(String uuid){
        if(lockMap.containsKey(uuid)){
            return lockMap.get(uuid);
        }
        return null;
    }
}
