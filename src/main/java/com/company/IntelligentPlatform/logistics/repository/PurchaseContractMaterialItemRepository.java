package com.company.IntelligentPlatform.logistics.repository;

import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseContractMaterialItemRepository extends JpaRepository<PurchaseContractMaterialItem, String> {

    List<PurchaseContractMaterialItem> findByParentNodeUUID(String parentNodeUUID);

}
