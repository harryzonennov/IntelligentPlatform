package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.LockObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for {@link LockObject} — replaces the legacy Hibernate
 * {@code LockObjectDAO} stub.
 *
 * <p>Provides the lock-check queries used by {@link
 * com.company.IntelligentPlatform.common.service.LockObjectManager}.</p>
 */
@Repository
public interface LockObjectRepository extends JpaRepository<LockObject, String> {

    /**
     * Finds all lock records for a given referenced entity UUID.
     * Used to check whether a specific object is already locked before editing.
     *
     * @param refUUID the UUID of the entity being locked (LockObject.refUUID)
     * @return all lock records pointing at that entity
     */
    List<LockObject> findByRefUUID(String refUUID);

    /**
     * Finds all lock records held by a specific user.
     * Used when unlocking all objects for a user (e.g. on session logout).
     *
     * @param resEmployeeUUID the UUID of the user who holds the locks
     * @return all lock records owned by that user
     */
    List<LockObject> findByResEmployeeUUID(String resEmployeeUUID);

}
