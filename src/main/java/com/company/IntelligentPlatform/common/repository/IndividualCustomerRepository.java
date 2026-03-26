package com.company.IntelligentPlatform.common.repository;

import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, String> {
}
