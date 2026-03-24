package com.company.IntelligentPlatform.sales.service;

import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.sales.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.sales.dto.SalesReturnMaterialItemUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


@Service
public class SalesReturnMaterialItemManager {

    public static final String METHOD_ConvSalesReturnMaterialItemToUI = "convSalesReturnMaterialItemToUI";

    public static final String METHOD_ConvUIToSalesReturnMaterialItem = "convUIToSalesReturnMaterialItem";

    public static final String METHOD_ConvSalesContractToItemUI = "convSalesContractToItemUI";

    public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

    public static final String METHOD_ConvSalesContractMatItemToItemUI = "convSalesContractMatItemToItemUI";

    @Autowired
    protected SalesReturnOrderManager salesReturnOrderManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesReturnMaterialItemManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SalesReturnOrder.NODENAME,
                        request.getUuid(), SalesReturnMaterialItem.NODENAME, salesReturnOrderManager);
        docPageHeaderInputPara.setGenBaseModelList(
                (DocPageHeaderModelProxy.GenBaseModelList<SalesReturnOrder>) salesReturnOrder -> {
                    // How to get the base page header model list
                    return docPageHeaderModelProxy.getDocPageHeaderModelList(salesReturnOrder,  null);
                });
        docPageHeaderInputPara.setGenHomePageModel(
                (DocPageHeaderModelProxy.GenHomePageModel<SalesReturnMaterialItem>) (salesReturnMaterialItem, pageHeaderModel) ->
                        docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(salesReturnMaterialItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convSalesReturnMaterialItemToUI(
            SalesReturnMaterialItem salesReturnMaterialItem,
            SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel)
            throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        convSalesReturnMaterialItemToUI(salesReturnMaterialItem, salesReturnMaterialItemUIModel, null);
    }


    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convSalesReturnMaterialItemToUI(
            SalesReturnMaterialItem salesReturnMaterialItem,
            SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException{
        if (salesReturnMaterialItem != null) {
            docFlowProxy.convDocMatItemToUI(salesReturnMaterialItem, salesReturnMaterialItemUIModel, logonInfo);
            salesReturnMaterialItemUIModel.setItemStatus(salesReturnMaterialItem
                    .getItemStatus());
            if (logonInfo != null) {
                Map<Integer, String> itemStatusMap = salesReturnOrderManager.initStatus(logonInfo
                        .getLanguageCode());
                salesReturnMaterialItemUIModel.setItemStatusValue(itemStatusMap
                        .get(salesReturnMaterialItem.getItemStatus()));
            }
            salesReturnMaterialItemUIModel.setId(salesReturnMaterialItem.getId());
            if (salesReturnMaterialItem.getCreatedTime() != null) {
                salesReturnMaterialItemUIModel
                        .setCreatedDate(DefaultDateFormatConstant.DATE_FORMAT
                                .format(salesReturnMaterialItem.getCreatedTime()));
            }
            salesReturnMaterialItemUIModel.setCurrencyCode(salesReturnMaterialItem
                    .getCurrencyCode());
        }
    }

    public void convParentDocToItemUI(SalesReturnOrder salesReturnOrder, SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel, LogonInfo logonInfo) {
        docFlowProxy.convParentDocToItemUI(salesReturnOrder, salesReturnMaterialItemUIModel, logonInfo);
    }

    public void convSalesContractToItemUI(SalesContract salesContract,
                                          SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel) {
        convSalesContractToItemUI(salesContract, salesReturnMaterialItemUIModel, null);
    }

    public void convSalesContractToItemUI(SalesContract salesContract,
                                             SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel,
                                             LogonInfo logonInfo) {
        if (salesContract != null) {
            salesReturnMaterialItemUIModel.setPrevProfDocId(salesContract
                    .getId());
            salesReturnMaterialItemUIModel.setPrevProfDocName(salesContract
                    .getName());
            salesReturnMaterialItemUIModel.setPrevProfDocStatus(salesContract
                    .getStatus());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> statusMap = salesContractManager.initStatus(logonInfo.getLanguageCode());
                    salesReturnMaterialItemUIModel
                            .setPrevProfDocStatusValue(statusMap
                                    .get(salesContract.getStatus()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convSalesContractToItemUI"));
                }
            }
        }
    }

    public void convSalesContractMatItemToItemUI(SalesContractMaterialItem salesContractMaterialItem,
                                                 SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel) {
        if (salesReturnMaterialItemUIModel != null) {
            salesReturnMaterialItemUIModel.setPrevProfAmount(salesContractMaterialItem.getAmount());
            salesReturnMaterialItemUIModel.setPrevProfRefUnitUUID(salesContractMaterialItem.getRefUnitUUID());
            try {
                String amountLabel =
                        materialStockKeepUnitManager.getAmountLabel(salesContractMaterialItem.getRefMaterialSKUUUID(),
                        salesContractMaterialItem.getRefUnitUUID(), salesContractMaterialItem.getAmount(),
                                salesContractMaterialItem.getClient());
                salesReturnMaterialItemUIModel.setPrevProfAmountLabel(amountLabel);
            } catch (ServiceEntityConfigureException | MaterialException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"));
            }
        }
    }


    public void convUIToSalesReturnMaterialItem(SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel, SalesReturnMaterialItem rawEntity) {
        if(salesReturnMaterialItemUIModel != null && rawEntity != null){
            docFlowProxy.convUIToDocMatItem(salesReturnMaterialItemUIModel, rawEntity);
            if (!ServiceEntityStringHelper
                    .checkNullString(salesReturnMaterialItemUIModel.getId())) {
                rawEntity.setId(salesReturnMaterialItemUIModel.getId());
            }
            if(salesReturnMaterialItemUIModel.getItemStatus() > 0){
                rawEntity.setItemStatus(salesReturnMaterialItemUIModel.getItemStatus());
            }
            rawEntity
                    .setRootNodeUUID(salesReturnMaterialItemUIModel.getRootNodeUUID());
            rawEntity.setRefUUID(salesReturnMaterialItemUIModel
                    .getRefMaterialSKUUUID());
            rawEntity.setAmount(salesReturnMaterialItemUIModel.getAmount());
            rawEntity.setRefUnitUUID(salesReturnMaterialItemUIModel.getRefUnitUUID());
        }
    }

}