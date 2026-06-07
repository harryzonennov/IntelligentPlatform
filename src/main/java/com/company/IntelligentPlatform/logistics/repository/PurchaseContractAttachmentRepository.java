package com.company.IntelligentPlatform.logistics.repository;

import com.company.IntelligentPlatform.logistics.model.PurchaseContractAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseContractAttachmentRepository extends JpaRepository<PurchaseContractAttachment, String> {

    List<PurchaseContractAttachment> findByParentNodeUUID(String parentNodeUUID);
}
