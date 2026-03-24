package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseReturnMaterialItemUIModel;
import com.company.IntelligentPlatform.logistics.model.PurchaseContractMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnMaterialItem;
import com.company.IntelligentPlatform.logistics.model.PurchaseReturnOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


@Service
public class PurchaseReturnMaterialItemManager {

    public static final String METHOD_ConvPurchaseReturnMaterialItemToUI = "convPurchaseReturnMaterialItemToUI";

    public static final String METHOD_ConvUIToPurchaseReturnMaterialItem = "convUIToPurchaseReturnMaterialItem";

    public static final String METHOD_ConvPurchaseContractMatItemToItemUI = "convPurchaseContractMatItemToItemUI";

    @Autowired
    protected PurchaseReturnOrderManager purchaseReturnOrderManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected PurchaseContractManager purchaseContractManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseReturnMaterialItemManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), PurchaseReturnOrder.NODENAME,
                        request.getUuid(), PurchaseReturnMaterialItem.NODENAME, purchaseReturnOrderManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<PurchaseReturnOrder>) purchaseReturnOrder -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(purchaseReturnOrder,  null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<PurchaseReturnMaterialItem>) (purchaseReturnMaterialItem, pageHeaderModel) ->
                        docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(purchaseReturnMaterialItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convPurchaseReturnMaterialItemToUI(
            PurchaseReturnMaterialItem purchaseReturnMaterialItem,
            PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException{
        if (purchaseReturnMaterialItem != null) {
            docFlowProxy.convDocMatItemToUI(purchaseReturnMaterialItem, purchaseReturnMaterialItemUIModel, logonInfo);
            purchaseReturnMaterialItemUIModel.setItemStatus(purchaseReturnMaterialItem
                    .getItemStatus());
            purchaseReturnMaterialItemUIModel.setRefStoreItemUUID(purchaseReturnMaterialItem.getRefStoreItemUUID());
            if (logonInfo != null) {
                Map<Integer, String> itemStatusMap = purchaseReturnOrderManager.initItemStatus(logonInfo
                        .getLanguageCode());
                purchaseReturnMaterialItemUIModel.setItemStatusValue(itemStatusMap
                        .get(purchaseReturnMaterialItem.getItemStatus()));
            }
            purchaseReturnMaterialItemUIModel.setId(purchaseReturnMaterialItem.getId());
            if (purchaseReturnMaterialItem.getCreatedTime() != null) {
                purchaseReturnMaterialItemUIModel
                        .setCreatedDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(purchaseReturnMaterialItem.getCreatedTime()));
            }
            purchaseReturnMaterialItemUIModel.setCurrencyCode(purchaseReturnMaterialItem
                    .getCurrencyCode());
        }
    }

    public void convPurchaseContractMatItemToItemUI(PurchaseContractMaterialItem purchaseContractMaterialItem,
                                                    PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel) {
        if (purchaseReturnMaterialItemUIModel != null) {
            purchaseReturnMaterialItemUIModel.setPrevProfAmount(purchaseContractMaterialItem.getAmount());
            purchaseReturnMaterialItemUIModel.setPrevProfRefUnitUUID(purchaseContractMaterialItem.getRefUnitUUID());
            try {
                String amountLabel =
                        materialStockKeepUnitManager.getAmountLabel(purchaseContractMaterialItem.getRefMaterialSKUUUID(),
                        purchaseContractMaterialItem.getRefUnitUUID(), purchaseContractMaterialItem.getAmount(),
                                purchaseContractMaterialItem.getClient());
                purchaseReturnMaterialItemUIModel.setPrevProfAmountLabel(amountLabel);
            } catch (ServiceEntityConfigureException | MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"));
            }
        }
    }


    public void convUIToPurchaseReturnMaterialItem(PurchaseReturnMaterialItemUIModel purchaseReturnMaterialItemUIModel, PurchaseReturnMaterialItem rawEntity) {
        if(purchaseReturnMaterialItemUIModel != null && rawEntity != null){
            docFlowProxy.convUIToDocMatItem(purchaseReturnMaterialItemUIModel, rawEntity);
            if (!ServiceEntityStringHelper
                    .checkNullString(purchaseReturnMaterialItemUIModel.getId())) {
                rawEntity.setId(purchaseReturnMaterialItemUIModel.getId());
            }
            if(purchaseReturnMaterialItemUIModel.getItemStatus() > 0){
                rawEntity.setItemStatus(purchaseReturnMaterialItemUIModel.getItemStatus());
            }
            rawEntity
                    .setRootNodeUUID(purchaseReturnMaterialItemUIModel.getRootNodeUUID());
            rawEntity.setRefUUID(purchaseReturnMaterialItemUIModel
                    .getRefMaterialSKUUUID());
            rawEntity.setAmount(purchaseReturnMaterialItemUIModel.getAmount());
            rawEntity.setRefUnitUUID(purchaseReturnMaterialItemUIModel.getRefUnitUUID());
        }
    }

}
