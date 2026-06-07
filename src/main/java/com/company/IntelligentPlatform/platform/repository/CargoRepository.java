package com.company.IntelligentPlatform.platform.repository;

import com.company.IntelligentPlatform.platform.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, String> {
}
