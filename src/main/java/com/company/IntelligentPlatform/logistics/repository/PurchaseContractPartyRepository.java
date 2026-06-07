package com.company.IntelligentPlatform.logistics.repository;

import com.company.IntelligentPlatform.logistics.model.PurchaseContractParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseContractPartyRepository extends JpaRepository<PurchaseContractParty, String> {

    List<PurchaseContractParty> findByRootNodeUUID(String rootNodeUUID);

    Optional<PurchaseContractParty> findByRootNodeUUIDAndPartyRole(String rootNodeUUID, int partyRole);
}
