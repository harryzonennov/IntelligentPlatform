package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
}
