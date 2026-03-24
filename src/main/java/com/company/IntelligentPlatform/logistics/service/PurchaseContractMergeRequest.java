package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.PurchaseContract;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class PurchaseContractMergeRequest extends DocSplitMergeRequest<PurchaseContract, PurchaseContractMaterialItem> {

    @Autowired
    protected PurchaseContractSpecifier purchaseContractSpecifier;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseContractMergeRequest.class);

    @Override
    public void setDefFilterDocToMerge() {
        setFilterDocToMerge((targetPurchaseContract, oldPurchaseContract) -> {
            try {
                PurchaseContractParty oldPurchaseContractPartyB = (PurchaseContractParty) docInvolvePartyProxy.getDocInvolvePartyWrapper(PurchaseContractParty.ROLE_PARTYB,
                        PurchaseContractParty.NODENAME, purchaseContractManager,
                        oldPurchaseContract.getUuid(), oldPurchaseContract.getClient());
                PurchaseContractParty targetPurchaseContractPartyB =
                        (PurchaseContractParty) docInvolvePartyProxy.getDocInvolvePartyWrapper(PurchaseContractParty.ROLE_PARTYB,
                        PurchaseContractParty.NODENAME, purchaseContractManager,
                                targetPurchaseContract.getUuid(), targetPurchaseContract.getClient());
                boolean checkResult = preCheckForMerge(targetPurchaseContract, targetPurchaseContractPartyB,
                        oldPurchaseContract,
                        oldPurchaseContractPartyB);
                if(checkResult){
                    return oldPurchaseContract;
                }else{
                    return null;
                }
            } catch (ServiceEntityConfigureException | PurchaseContractException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
            return null;
        });
    }

    public boolean preCheckForMerge(PurchaseContract purchaseContract, PurchaseContractParty purchaseContractPartyB,
                                  PurchaseContract oldPurchaseContract, PurchaseContractParty oldPurchaseContractPartyB)
            throws PurchaseContractException {
        if (!ServiceEntityStringHelper.checkNullString(purchaseContractPartyB.getRefUUID()) &&
                !ServiceEntityStringHelper.checkNullString(oldPurchaseContractPartyB.getRefUUID())) {
            if (!purchaseContractPartyB.getRefUUID().equals(oldPurchaseContractPartyB.getRefUUID())) {
                return false;
            }
        }
        if (oldPurchaseContract.getStatus() != PurchaseContract.STATUS_INITIAL) {
            return false;
        }
        return true;
    }

    @Override
    public void setDefTargetDocItem() {
        setSetTargetDocItem(new ISetTargetDocItem<PurchaseContract, PurchaseContractMaterialItem>() {
            @Override
            public PurchaseContractMaterialItem execute(PurchaseContractMaterialItem docItemToMerge, PurchaseContract docToMerge) {
                return null;
            }
        });
    }
}
