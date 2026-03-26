package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.LogonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Replaces: ThorsteinPlatform LogonUserDAO (Hibernate)
 */
@Repository
public interface LogonUserRepository extends JpaRepository<LogonUser, String> {

    Optional<LogonUser> findByName(String name);

    boolean existsByName(String name);

    /**
     * Looks up a LogonUser by their business ID and client tenant code.
     *
     * <p>Used by the JWT auth layer to authenticate login requests: the user
     * submits their business {@code id} (e.g. "U001") plus the {@code client}
     * tenant discriminator, which together uniquely identify an account.</p>
     *
     * @param id     the user's business ID (matches {@code ServiceEntityNode.id})
     * @param client the tenant/client code (matches {@code ServiceEntityNode.client})
     * @return an {@link Optional} containing the matching user, or empty if none found
     */
    Optional<LogonUser> findByIdAndClient(String id, String client);

}
