package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LockObject;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.repository.LockObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA-backed implementation of the legacy Hibernate {@code LockObjectDAO}.
 *
 * <p>Replaces the no-op stub that was returning empty lists.  All methods
 * delegate to {@link LockObjectRepository}.  The public API is intentionally
 * kept identical to the old Hibernate DAO so that {@link LockObjectManager}
 * requires no changes.</p>
 *
 * <p>Supported {@code fieldName} constants (from
 * {@link IServiceEntityNodeFieldConstant} and
 * {@link IReferenceNodeFieldConstant}):</p>
 * <ul>
 *   <li>{@code IReferenceNodeFieldConstant.REFUUID} — find by referenced entity UUID</li>
 *   <li>{@code IServiceEntityNodeFieldConstant.RESPONSIBLE_EMPLOYEEUUID} — find by lock owner</li>
 *   <li>{@code IServiceEntityNodeFieldConstant.UUID} — find/delete by lock's own UUID</li>
 *   <li>{@code null} / any other — returns all records (used by admUnlockAllObject)</li>
 * </ul>
 */
@Component
public class LockObjectDAO {

    @Autowired
    private LockObjectRepository lockObjectRepository;

    /**
     * Finds lock records matching the given key field.
     *
     * @param keyValue  the value to match
     * @param fieldName the field to match against (see class Javadoc for supported values)
     * @param nodeName  ignored — kept for API compatibility with legacy DAO
     * @return matching {@link LockObject} records cast as {@link ServiceEntityNode}
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(
            String keyValue, String fieldName, String nodeName) {

        if (keyValue == null || fieldName == null) {
            // Called by admUnlockAllObject — return all lock records
            return new ArrayList<>(lockObjectRepository.findAll());
        }

        List<LockObject> result;
        switch (fieldName) {
            case IReferenceNodeFieldConstant.REFUUID:
                result = lockObjectRepository.findByRefUUID(keyValue);
                break;
            case IServiceEntityNodeFieldConstant.RESPONSIBLE_EMPLOYEEUUID:
                result = lockObjectRepository.findByResEmployeeUUID(keyValue);
                break;
            case IServiceEntityNodeFieldConstant.UUID:
                result = lockObjectRepository.findById(keyValue)
                        .map(List::of)
                        .orElse(List.of());
                break;
            default:
                result = List.of();
                break;
        }
        return result.stream()
                .map(lo -> (ServiceEntityNode) lo)
                .collect(Collectors.toList());
    }

    /**
     * Persists a list of new lock records.
     *
     * @param nodeList lock objects to insert
     */
    public void insertEntity(List<ServiceEntityNode> nodeList) {
        List<LockObject> lockObjects = nodeList.stream()
                .filter(n -> n instanceof LockObject)
                .map(n -> (LockObject) n)
                .collect(Collectors.toList());
        lockObjectRepository.saveAll(lockObjects);
    }

    /**
     * Persists a single new lock record.
     *
     * @param node the lock object to insert
     */
    public void insertEntity(ServiceEntityNode node) {
        if (node instanceof LockObject) {
            lockObjectRepository.save((LockObject) node);
        }
    }

    /**
     * Deletes lock records matching the given key field.
     *
     * @param keyValue  the value to match
     * @param fieldName the field to match against (see class Javadoc for supported values)
     * @param nodeName  ignored — kept for API compatibility with legacy DAO
     */
    public void deleteEntityNodeByKey(
            String keyValue, String fieldName, String nodeName) {

        if (keyValue == null) {
            lockObjectRepository.deleteAll();
            return;
        }

        switch (fieldName) {
            case IServiceEntityNodeFieldConstant.UUID:
                lockObjectRepository.deleteById(keyValue);
                break;
            case IReferenceNodeFieldConstant.REFUUID:
                lockObjectRepository.findByRefUUID(keyValue)
                        .forEach(lo -> lockObjectRepository.deleteById(lo.getUuid()));
                break;
            case IServiceEntityNodeFieldConstant.RESPONSIBLE_EMPLOYEEUUID:
                lockObjectRepository.findByResEmployeeUUID(keyValue)
                        .forEach(lo -> lockObjectRepository.deleteById(lo.getUuid()));
                break;
            default:
                break;
        }
    }
}
