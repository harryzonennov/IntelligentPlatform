package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.ServiceAccountDuplicateCheckResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAccountDuplicateCheckResourceRepository extends JpaRepository<ServiceAccountDuplicateCheckResource, String> {
}
