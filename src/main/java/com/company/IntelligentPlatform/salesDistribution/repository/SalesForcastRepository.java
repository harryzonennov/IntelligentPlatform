package com.company.IntelligentPlatform.salesDistribution.repository;

import com.company.IntelligentPlatform.salesDistribution.model.SalesForcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesForcastRepository extends JpaRepository<SalesForcast, String>,
        JpaSpecificationExecutor<SalesForcast> {

    List<SalesForcast> findByClient(String client);
    List<SalesForcast> findByClientAndStatus(String client, int status);

}
