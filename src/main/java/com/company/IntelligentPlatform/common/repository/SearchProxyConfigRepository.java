package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchProxyConfigRepository extends JpaRepository<SearchProxyConfig, String> {
}
