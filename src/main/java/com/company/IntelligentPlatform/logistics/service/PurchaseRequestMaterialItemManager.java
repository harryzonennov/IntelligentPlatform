package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.PurchaseRequestMaterialItemUIModel;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequest;
import com.company.IntelligentPlatform.logistics.model.PurchaseRequestMaterialItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class PurchaseRequestMaterialItemManager {

    public static final String METHOD_ConvPurchaseRequestMaterialItemToUI = "convPurchaseRequestMaterialItemToUI";

    public static final String METHOD_ConvUIToPurchaseRequestMaterialItem = "convUIToPurchaseRequestMaterialItem";

    public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected PurchaseRequestManager purchaseRequestManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(PurchaseRequestMaterialItemManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), PurchaseRequest.NODENAME,
                        request.getUuid(), PurchaseRequestMaterialItem.NODENAME, purchaseRequestManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<PurchaseRequest>) purchaseRequest -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(purchaseRequest, null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<PurchaseRequestMaterialItem>) (purchaseRequestMaterialItem, pageHeaderModel) ->
                        docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(purchaseRequestMaterialItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convPurchaseRequestMaterialItemToUI(
            PurchaseRequestMaterialItem purchaseRequestMaterialItem,
            PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel)
            throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        convPurchaseRequestMaterialItemToUI(purchaseRequestMaterialItem, purchaseRequestMaterialItemUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convPurchaseRequestMaterialItemToUI(
            PurchaseRequestMaterialItem purchaseRequestMaterialItem,
            PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (purchaseRequestMaterialItem != null) {
            docFlowProxy.convDocMatItemToUI(purchaseRequestMaterialItem, purchaseRequestMaterialItemUIModel, logonInfo);
            purchaseRequestMaterialItemUIModel.setItemStatus(purchaseRequestMaterialItem
                    .getItemStatus());
            if (logonInfo != null) {
                Map<Integer, String> itemStatusMap = purchaseRequestManager.initStatus(logonInfo
                        .getLanguageCode());
                purchaseRequestMaterialItemUIModel.setItemStatusValue(itemStatusMap
                        .get(purchaseRequestMaterialItem.getItemStatus()));
            }
            purchaseRequestMaterialItemUIModel.setId(purchaseRequestMaterialItem.getId());
            purchaseRequestMaterialItemUIModel.setCurrencyCode(purchaseRequestMaterialItem
                    .getCurrencyCode());
        }
    }

    public void convUIToPurchaseRequestMaterialItem(PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel, PurchaseRequestMaterialItem rawEntity) {
        if(purchaseRequestMaterialItemUIModel != null && rawEntity != null){
            docFlowProxy.convUIToDocMatItem(purchaseRequestMaterialItemUIModel, rawEntity);
            if (!ServiceEntityStringHelper
                    .checkNullString(purchaseRequestMaterialItemUIModel.getId())) {
                rawEntity.setId(purchaseRequestMaterialItemUIModel.getId());
            }
            if(purchaseRequestMaterialItemUIModel.getItemStatus() > 0){
                rawEntity.setItemStatus(purchaseRequestMaterialItemUIModel.getItemStatus());
            }
            rawEntity.setRefUUID(purchaseRequestMaterialItemUIModel
                    .getRefMaterialSKUUUID());
        }
    }

    public void convParentDocToItemUI(PurchaseRequest purchaseRequest,
                                        PurchaseRequestMaterialItemUIModel purchaseRequestMaterialItemUIModel,
                                      LogonInfo logonInfo) {
        if (purchaseRequest != null) {
            docFlowProxy.convParentDocToItemUI(purchaseRequest, purchaseRequestMaterialItemUIModel, logonInfo);
            if(purchaseRequest.getPlanExecutionDate() != null){
                purchaseRequestMaterialItemUIModel
                        .setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(purchaseRequest.getPlanExecutionDate()));
            }
        }
    }

}
