package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.repository.LogonUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA-backed implementation of the legacy Hibernate {@code LogonUserDAO}.
 *
 * <p>Replaces the no-op stub that was returning empty lists.  The only caller
 * is {@link LockObjectManager#genJSONLockCheckResult}, which needs to look up
 * the display name of the user who currently holds a lock so the UI can show
 * a "locked by X" message.</p>
 *
 * <p>The public API is kept identical to the old DAO so {@link LockObjectManager}
 * requires no changes.</p>
 *
 * <p>Supported {@code fieldName} values:</p>
 * <ul>
 *   <li>{@code IServiceEntityNodeFieldConstant.UUID} — find by primary key (UUID)</li>
 * </ul>
 */
@Component
public class LogonUserDAO {

    @Autowired
    private LogonUserRepository logonUserRepository;

    /**
     * Finds {@link LogonUser} records matching the given key field.
     *
     * <p>Only {@code fieldName = UUID} is currently used by callers.
     * Other field names return an empty list.</p>
     *
     * @param keyValue  the value to match
     * @param fieldName the field to match against
     * @param nodeName  ignored — kept for API compatibility with legacy DAO
     * @return matching users cast as {@link ServiceEntityNode}
     */
    public List<ServiceEntityNode> getEntityNodeListByKey(
            String keyValue, String fieldName, String nodeName) {

        if (keyValue == null || fieldName == null) {
            return List.of();
        }

        if (IServiceEntityNodeFieldConstant.UUID.equals(fieldName)) {
            return logonUserRepository.findById(keyValue)
                    .map(user -> (ServiceEntityNode) user)
                    .map(List::of)
                    .orElse(List.of());
        }

        return List.of();
    }
}
