package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.CorporateCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Replaces: ThorsteinPlatform CorporateCustomerDAO (Hibernate)
 */
@Repository
public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, String>,
        JpaSpecificationExecutor<CorporateCustomer> {

    List<CorporateCustomer> findByClient(String client);

    List<CorporateCustomer> findByStatus(int status);

}
