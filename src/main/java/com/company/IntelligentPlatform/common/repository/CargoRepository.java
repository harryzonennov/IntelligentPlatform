package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, String> {
}
