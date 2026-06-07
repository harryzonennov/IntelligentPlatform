package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.ServiceAccountDuplicateCheckResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAccountDuplicateCheckResourceRepository extends JpaRepository<ServiceAccountDuplicateCheckResource, String> {
}
