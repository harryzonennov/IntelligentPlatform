package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Replaces: ThorsteinPlatform OrganizationDAO (Hibernate)
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String>,
        JpaSpecificationExecutor<Organization> {

    List<Organization> findByParentOrganizationUUID(String parentOrganizationUUID);

    List<Organization> findByClient(String client);

}
