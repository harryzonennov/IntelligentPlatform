package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredProductRepository extends JpaRepository<RegisteredProduct, String> {
}
