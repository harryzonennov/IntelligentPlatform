package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InventoryCheckItemUIModel;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class InventoryCheckItemManager {

    public static final String METHOD_ConvInventoryCheckItemToUI = "convInventoryCheckItemToUI";

    public static final String METHOD_ConvUIToInventoryCheckItem = "convUIToInventoryCheckItem";

    public static final String METHOD_ConvItemWarehouseAreaToUI = "convItemWarehouseAreaToUI";

    public static final String METHOD_ConvWarehouseStoreItemToUI = "convWarehouseStoreItemToUI";

    public static final String METHOD_ConvCheckOrderToItemUI = "convCheckOrderToItemUI";

    @Autowired
    protected InventoryCheckOrderManager inventoryCheckOrderManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    protected Logger logger = LoggerFactory.getLogger(InventoryTransferItemManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), InventoryCheckOrder.NODENAME,
                        request.getUuid(), InventoryCheckItem.NODENAME, inventoryCheckOrderManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<InventoryCheckOrder>) inventoryCheckItem -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(inventoryCheckItem, null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<InventoryCheckItem>) (inventoryCheckItem, pageHeaderModel) ->
                        docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(inventoryCheckItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }


    public void convInventoryCheckItemToUI(
            InventoryCheckItem inventoryCheckItem,
            InventoryCheckItemUIModel inventoryCheckItemUIModel)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, MaterialException {
        convInventoryCheckItemToUI(inventoryCheckItem, inventoryCheckItemUIModel, null);
    }

    public void convInventoryCheckItemToUI(
            InventoryCheckItem inventoryCheckItem,
            InventoryCheckItemUIModel inventoryCheckItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, MaterialException {
        if (inventoryCheckItem != null) {
            docFlowProxy.convDocMatItemToUI(inventoryCheckItem, inventoryCheckItemUIModel, logonInfo);
            inventoryCheckItemUIModel
                    .setRefWarehouseStoreItemUUID(inventoryCheckItem
                            .getRefWarehouseStoreItemUUID());
            inventoryCheckItemUIModel.setDeclaredValue(inventoryCheckItem
                    .getDeclaredValue());
            inventoryCheckItemUIModel.setResultAmount(inventoryCheckItem
                    .getResultAmount());
            String resultAmountLabel = materialStockKeepUnitManager
                    .getAmountLabel(inventoryCheckItem.getRefMaterialSKUUUID(),
                            inventoryCheckItem.getResultUnitUUID(),
                            inventoryCheckItem.getResultAmount(),
                            inventoryCheckItem.getClient());
            inventoryCheckItemUIModel.setResultAmountLabel(resultAmountLabel);
            inventoryCheckItemUIModel.setResultUnitUUID(inventoryCheckItem
                    .getResultUnitUUID());
            inventoryCheckItemUIModel.setRefUnitUUID(inventoryCheckItem
                    .getRefUnitUUID());
            inventoryCheckItemUIModel.setResultDeclaredValue(inventoryCheckItem
                    .getResultDeclaredValue());
            if(logonInfo != null){
                Map<Integer, String> inventoryCheckResultMap = inventoryCheckOrderManager.initInventoryCheckResultMap(logonInfo.getLanguageCode());
                inventoryCheckItemUIModel
                        .setInventCheckResultValue(inventoryCheckResultMap
                                .get(inventoryCheckItem.getInventCheckResult()));
            }
            inventoryCheckItemUIModel.setInventCheckResult(inventoryCheckItem
                    .getInventCheckResult());
            String updateAmountLabel = materialStockKeepUnitManager
                    .getAmountLabel(inventoryCheckItem.getRefMaterialSKUUUID(),
                            inventoryCheckItem.getUpdateUnitUUID(),
                            inventoryCheckItem.getUpdateAmount(),
                            inventoryCheckItem.getClient());
            inventoryCheckItemUIModel.setUpdateAmountLabel(updateAmountLabel);
            inventoryCheckItemUIModel.setUpdateDeclaredValue(inventoryCheckItem
                    .getUpdateDeclaredValue());
            inventoryCheckItemUIModel.setUpdateUnitUUID(inventoryCheckItem
                    .getUpdateUnitUUID());
            inventoryCheckItemUIModel.setUpdateAmount(inventoryCheckItem
                    .getUpdateAmount());
        }
    }

    public void convMaterialStockKeepUnitToItemUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            InventoryCheckItemUIModel inventoryCheckItemUIModel) {
        if (materialStockKeepUnit != null) {
            inventoryCheckItemUIModel
                    .setRefMaterialSKUName(materialStockKeepUnit.getName());
            inventoryCheckItemUIModel.setRefMaterialSKUId(materialStockKeepUnit
                    .getId());
        }
    }

    public void convWarehouseStoreItemToItemUI(
            WarehouseStoreItem warehouseStoreItem,
            Map<String, String> materialUnitMap,
            InventoryCheckItemUIModel inventoryCheckItemUIModel) {
        if (warehouseStoreItem != null) {
            inventoryCheckItemUIModel
                    .setRefWarehouseStoreItemUUID(warehouseStoreItem.getUuid());
            inventoryCheckItemUIModel.setDeclaredValue(warehouseStoreItem
                    .getDeclaredValue());
            inventoryCheckItemUIModel.setAmount(ServiceEntityDoubleHelper
                    .trancateDoubleScale4(warehouseStoreItem.getAmount()));
            inventoryCheckItemUIModel.setRefUnitUUID(warehouseStoreItem
                    .getRefUnitUUID());
            if (warehouseStoreItem.getAmount() != 0) {
                String refUnitLabel = materialUnitMap.get(warehouseStoreItem
                        .getRefUnitUUID());
                inventoryCheckItemUIModel.setAmountLabel(warehouseStoreItem
                        .getAmount() + refUnitLabel);
            } else {
                inventoryCheckItemUIModel.setAmountLabel(warehouseStoreItem
                        .getAmount() + "");
            }
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:inventoryCheckItem
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToInventoryCheckItem(
            InventoryCheckItemUIModel inventoryCheckItemUIModel,
            InventoryCheckItem rawEntity) {
        docFlowProxy.convUIToDocMatItem(inventoryCheckItemUIModel, rawEntity);
        rawEntity.setInventCheckResult(inventoryCheckItemUIModel
                .getInventCheckResult());
        rawEntity.setUpdateDeclaredValue(inventoryCheckItemUIModel
                .getUpdateDeclaredValue());
        rawEntity.setResultAmount(inventoryCheckItemUIModel.getResultAmount());
        rawEntity.setResultUnitUUID(inventoryCheckItemUIModel
                .getResultUnitUUID());
        rawEntity.setResultDeclaredValue(inventoryCheckItemUIModel
                .getResultDeclaredValue());
        rawEntity.setUpdateAmount(inventoryCheckItemUIModel.getUpdateAmount());
        rawEntity.setUpdateUnitUUID(inventoryCheckItemUIModel
                .getUpdateUnitUUID());
        rawEntity.setRefMaterialSKUUUID(inventoryCheckItemUIModel
                .getRefMaterialSKUUUID());
        rawEntity.setAmount(inventoryCheckItemUIModel.getAmount());
        rawEntity
                .setDeclaredValue(inventoryCheckItemUIModel.getDeclaredValue());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convItemWarehouseAreaToUI(WarehouseArea itemWarehouseArea,
                                          InventoryCheckItemUIModel inventoryCheckItemUIModel) {
        if (itemWarehouseArea != null) {

        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convWarehouseStoreItemToUI(
            WarehouseStoreItem warehouseStoreItem,
            InventoryCheckItemUIModel inventoryCheckItemUIModel) {
        if (warehouseStoreItem != null) {

        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convCheckOrderToItemUI(InventoryCheckOrder inventoryCheckOrder,
                                       InventoryCheckItemUIModel inventoryCheckItemUIModel) {
        convCheckOrderToItemUI(inventoryCheckOrder, inventoryCheckItemUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convCheckOrderToItemUI(InventoryCheckOrder inventoryCheckOrder,
                                       InventoryCheckItemUIModel inventoryCheckItemUIModel, LogonInfo logonInfo) {
        if (inventoryCheckOrder != null) {
            docFlowProxy.convParentDocToItemUI(inventoryCheckOrder, inventoryCheckItemUIModel, logonInfo);
            inventoryCheckItemUIModel.setGrossCheckResult(inventoryCheckOrder
                    .getGrossCheckResult());
            inventoryCheckItemUIModel.setOrderStatus(inventoryCheckOrder
                    .getStatus());
            try {
                if (logonInfo != null) {
                    Map<Integer, String> statusMap = inventoryCheckOrderManager.initStatusMap(logonInfo.getLanguageCode());
                    inventoryCheckItemUIModel.setOrderStatusValue(statusMap
                            .get(inventoryCheckOrder.getStatus()));
                }
            } catch (ServiceEntityInstallationException ex) {
                // continue;
                String errorMessage = ServiceEntityStringHelper
                        .genDefaultErrorMessage(ex,
                                inventoryCheckItemUIModel.getUuid());
                logger.error(errorMessage);
            }
            try {
                if (logonInfo != null) {
                    Map<Integer, String> checkResultMap = inventoryCheckOrderManager.initInventoryCheckResultMap(logonInfo.getLanguageCode());
                    inventoryCheckItemUIModel
                            .setGrossCheckResultValue(checkResultMap
                                    .get(inventoryCheckOrder.getGrossCheckResult()));
                }
            } catch (ServiceEntityInstallationException e) {
                // continue;
                String errorMessage = ServiceEntityStringHelper
                        .genDefaultErrorMessage(e,
                                inventoryCheckItemUIModel.getUuid());
                logger.error(errorMessage);
            }

        }
    }


}
