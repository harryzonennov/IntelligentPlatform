package com.company.IntelligentPlatform.production.service;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.MessageTemplateHandler;
import com.company.IntelligentPlatform.common.service.MessageTemplateServiceModel;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProdOrderPickingToOutboundHandler extends MessageTemplateHandler {

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected ProductionOrderItemManager productionOrderItemManager;

    protected Logger logger = LoggerFactory.getLogger(ProdOrderPickingToPickingHandler.class);

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<ServiceEntityNode> postProcessSEDataList(MessageTemplateServiceModel messageTemplateServiceModel,
                                                         List<ServiceEntityNode> rawSEList, LogonInfo logonInfo) {
        if (ServiceCollectionsHelper.checkNullList(rawSEList)) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawSEList) {
            ProductionOrderItem productionOrderItem = (ProductionOrderItem) seNode;
            try {
                List<String> warehouseUUIDList =
                        productionOrderManager.getWarehouseUUIDList(productionOrderItem.getParentNodeUUID(),
                        productionOrderItem.getClient());
                ProductionOrderItemServiceModel productionOrderItemServiceModel =
                        (ProductionOrderItemServiceModel) productionOrderManager
                        .loadServiceModule(ProductionOrderItemServiceModel.class, productionOrderItem);
                List<ProdPickingExtendAmountModel> pickingExtendAmountModelList =
                        productionOrderItemManager
                        .refreshOrderItemFromPickingItem(productionOrderItemServiceModel, warehouseUUIDList);
                if (productionOrderItem.getInStockAmount() > 0) {
                    ProductionOrder productionOrder =
                            (ProductionOrder) ServiceCollectionsHelper.filterOnline(resultList, se -> {
                                return productionOrderItem.getRootNodeUUID().equals(se.getUuid());
                            });
                    if (productionOrder == null) {
                        productionOrder =
                                (ProductionOrder) productionOrderManager.getEntityNodeByKey(productionOrderItem.getRootNodeUUID(),
                                        IServiceEntityNodeFieldConstant.UUID, ProductionOrder.NODENAME,
                                        productionOrderItem.getClient(), null);
                        ServiceCollectionsHelper.mergeToList(resultList,
                                productionOrder);
                    }
                }
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | MaterialException |
                     ServiceComExecuteException | DocActionException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, productionOrderItem.getUuid()));
            }
        }
        return resultList;
    }

}
