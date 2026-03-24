package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;

@Service
public class ProductionInboundDeliveryManager {

    @Autowired
    protected InboundDeliveryManager inboundDeliveryManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    /**
     * Provide default logic to copy source doc material item instance as
     * previous item to target in-bound item
     *
     * @param inboundItem
     * @param docMatItemNode
     * @param sourceDocType
     * @return
     */
    @Deprecated
    public InboundItem copyPrevDocMatItemToInboundItem(
            DocMatItemNode docMatItemNode, int sourceDocType,
            InboundItem inboundItem,
            MaterialStockKeepUnit materialStockKeepUnit, SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (inboundItem != null && docMatItemNode != null) {
            docFlowProxy.buildItemPrevNextRelationship(docMatItemNode, inboundItem, null,serialLogonInfo);
            inboundItem.setActualAmount(docMatItemNode.getAmount());
            if (sourceDocType > 0) {
                inboundItem.setPrevDocType(sourceDocType);
            }
            if (materialStockKeepUnit != null) {
                inboundItem.setRefMaterialSKUId(materialStockKeepUnit
                        .getId());
                inboundItem
                        .setRefMaterialSKUName(materialStockKeepUnit.getName());
            }
        }
        return inboundItem;
    }
}
