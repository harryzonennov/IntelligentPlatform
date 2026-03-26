package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.dto.SalesForcastMaterialItemUIModel;
import com.company.IntelligentPlatform.sales.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class SalesForcastMaterialItemManager {

    public static final String METHOD_ConvSalesForcastMaterialItemToUI = "convSalesForcastMaterialItemToUI";

    public static final String METHOD_ConvUIToSalesForcastMaterialItem = "convUIToSalesForcastMaterialItem";

    public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

    @Autowired
    protected SalesForcastManager salesForcastManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(SalesForcastMaterialItemManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SalesForcast.NODENAME,
                        request.getUuid(), SalesForcastMaterialItem.NODENAME, salesForcastManager);
        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<SalesForcast>) salesForcast -> {
            // How to get the base page header model list
            return docPageHeaderModelProxy.getDocPageHeaderModelList(salesForcast,  null);
        });
        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<SalesForcastMaterialItem>) (salesForcastMaterialItem, pageHeaderModel) ->
                docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(salesForcastMaterialItem, pageHeaderModel));
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convParentDocToItemUI(SalesForcast salesForcast,
                                      SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel, LogonInfo logonInfo){
        docFlowProxy.convParentDocToItemUI(salesForcast, salesForcastMaterialItemUIModel, logonInfo);
        if(salesForcast.getPlanExecutionDate() != null){
            salesForcastMaterialItemUIModel
                    .setPlanExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
                            .format(salesForcast.getPlanExecutionDate()));
        }
    }

    public void convSalesForcastMaterialItemToUI(
            SalesForcastMaterialItem salesForcastMaterialItem,
            SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel)
            throws ServiceEntityInstallationException, ServiceEntityConfigureException {
        convSalesForcastMaterialItemToUI(salesForcastMaterialItem, salesForcastMaterialItemUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     * @throws ServiceEntityInstallationException
     * @throws ServiceEntityConfigureException
     */
    public void convSalesForcastMaterialItemToUI(
            SalesForcastMaterialItem salesForcastMaterialItem,
            SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel,
            LogonInfo logonInfo) throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (salesForcastMaterialItem != null) {
            docFlowProxy.convDocMatItemToUI(salesForcastMaterialItem, salesForcastMaterialItemUIModel, logonInfo);
            salesForcastMaterialItemUIModel.setItemStatus(salesForcastMaterialItem
                    .getItemStatus());
            if(logonInfo != null){
                Map<Integer, String> statusMap = salesForcastManager.initStatus(logonInfo.getLanguageCode());
                salesForcastMaterialItemUIModel.setItemStatusValue(statusMap.get(salesForcastMaterialItem
                        .getItemStatus()));
            }
            salesForcastMaterialItemUIModel.setId(salesForcastMaterialItem.getId());
            salesForcastMaterialItemUIModel.setCurrencyCode(salesForcastMaterialItem
                    .getCurrencyCode());
        }
    }

    public void convUIToSalesForcastMaterialItem(SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel, SalesForcastMaterialItem rawEntity) {
        if(salesForcastMaterialItemUIModel != null && rawEntity != null){
            docFlowProxy.convUIToDocMatItem(salesForcastMaterialItemUIModel, rawEntity);
            if (!ServiceEntityStringHelper
                    .checkNullString(salesForcastMaterialItemUIModel.getId())) {
                rawEntity.setId(salesForcastMaterialItemUIModel.getId());
            }
            if(salesForcastMaterialItemUIModel.getItemStatus() > 0){
                rawEntity.setItemStatus(salesForcastMaterialItemUIModel.getItemStatus());
            }
            rawEntity
                    .setRootNodeUUID(salesForcastMaterialItemUIModel.getRootNodeUUID());
            rawEntity.setRefUUID(salesForcastMaterialItemUIModel
                    .getRefMaterialSKUUUID());
        }
    }

}