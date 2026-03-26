package com.company.IntelligentPlatform.production.service;
import com.company.IntelligentPlatform.production.service.ProdPickingExtendAmountModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.company.IntelligentPlatform.production.dto.ProdPickingRefMaterialItemUIModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class ProdPickingRefOrderltemManager {

    @Autowired
    protected ProdPickingOrderManager prodPickingOrderManager;

    @Autowired
    protected ProductionOrderManager productionOrderManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

    public List<PageHeaderModel> getPageHeaderModelList(
            ProdPickingRefOrderItem prodPickingRefOrderItem, String client) throws ServiceEntityConfigureException {
        ProdPickingOrder prodPickingOrder = (ProdPickingOrder) prodPickingOrderManager
                .getEntityNodeByKey(prodPickingRefOrderItem.getParentNodeUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        ProdPickingOrder.NODENAME, client, null);
        int index = 0;
        List<PageHeaderModel> resultList = new ArrayList<>();
        if (prodPickingOrder != null) {
            List<PageHeaderModel> pageHeaderModelList =
					prodPickingOrderManager.getPageHeaderModelList(prodPickingOrder, client);
            if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
                resultList.addAll(pageHeaderModelList);
                index = pageHeaderModelList.size();
            }
            PageHeaderModel itemHeaderModel = getPageHeaderModel(prodPickingRefOrderItem, index);
            if (itemHeaderModel != null) {
                resultList.add(itemHeaderModel);
            }
        }
        return resultList;
    }

    protected PageHeaderModel getPageHeaderModel(
            ProdPickingRefOrderItem prodPickingRefOrderItem, int index) throws ServiceEntityConfigureException {
        if (prodPickingRefOrderItem == null) {
            return null;
        }
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("prodPickingRefOrderItemPageTitle");
        pageHeaderModel.setNodeInstId(ProdPickingRefOrderItem.NODENAME);
        pageHeaderModel.setUuid(prodPickingRefOrderItem.getUuid());
        ProductionOrder productionOrder = (ProductionOrder) productionOrderManager
                .getEntityNodeByKey(prodPickingRefOrderItem.getRefProdOrderUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME,
                        prodPickingRefOrderItem.getClient(), null);
        if (productionOrder != null) {
            pageHeaderModel.setHeaderName(productionOrder.getId());
        }
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    /**
     * Using Async way to update production order post tasks.
     *
     * @param prodPickingRefOrderItemServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     * @throws ServiceModuleProxyException
     */
    public List<ProdPickingExtendAmountModel> postUpdatePickingRefOrderItemAsyncWrapper(ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel,
                                                                                         String logonUserUUID,
																						 String organizationUUID) throws ServiceEntityConfigureException, MaterialException, ServiceModuleProxyException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture<ServiceEntityConfigureException> configureExceptionFuture = new CompletableFuture<>();
        CompletableFuture<ServiceModuleProxyException> serviceModuleExceptionFuture = new CompletableFuture<>();
        CompletableFuture<MaterialException> materialExceptionFuture = new CompletableFuture<>();
        CompletableFuture<DocActionException> docActionExceptionFuture = new CompletableFuture<>();
        CompletableFuture<List<ProdPickingExtendAmountModel>> future = CompletableFuture.supplyAsync(() -> {
            try {
                List<ProdPickingExtendAmountModel> rawProdPickingExtendAmountModelList = postUpdateRefOrderItem(prodPickingRefOrderItemServiceModel);
                prodPickingOrderManager.updateServiceModule(ProdPickingRefOrderItemServiceModel.class,
						prodPickingRefOrderItemServiceModel, logonUserUUID, organizationUUID);
                return rawProdPickingExtendAmountModelList;
            } catch (ServiceEntityConfigureException e) {
                configureExceptionFuture.complete(e);
                throw new CompletionException(e);
            } catch (MaterialException e) {
                materialExceptionFuture.complete(e);
                throw new CompletionException(e);
            } catch (ServiceModuleProxyException e) {
                serviceModuleExceptionFuture.complete(e);
                throw new CompletionException(e);
            } catch (DocActionException e) {
                docActionExceptionFuture.complete(e);
                throw new CompletionException(e);
            }
        }, executor);
        List<ProdPickingExtendAmountModel> resultList = null;
        try {
            resultList = future.join();
        } catch (CompletionException ex) {
            if (configureExceptionFuture.isDone()) {
                throw configureExceptionFuture.join();
            }
            if (materialExceptionFuture.isDone()) {
                throw materialExceptionFuture.join();
            }
            if (serviceModuleExceptionFuture.isDone()) {
                throw serviceModuleExceptionFuture.join();
            }
            throw ex;
        }
        return resultList;
    }

    public List<ProdPickingExtendAmountModel> postUpdateRefOrderItem(ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel) throws ServiceEntityConfigureException, MaterialException, DocActionException {
        List<ServiceEntityNode> prodPickingRefMaterialItemList =
                ProdPickingOrderManager.pluckToRefMaterialItemList(prodPickingRefOrderItemServiceModel.getProdPickingRefMaterialItemList());
        List<ProdPickingExtendAmountModel> rawProdPickingExtendAmountModelList =
                prodPickingRefMaterialItemManager.getPickingItemAmountListWrapper(prodPickingRefMaterialItemList);
        refreshPickingRefOrderItem(prodPickingRefOrderItemServiceModel, rawProdPickingExtendAmountModelList);
        return rawProdPickingExtendAmountModelList;
    }

    public void updateRefMaterialItemWithAmountModel(List<ProdPickingExtendAmountModel> rawProdPickingExtendAmountModelList,
                                                     List<ProdPickingRefMaterialItemUIModel> prodPickingRefMaterialItemUIModelList) throws ServiceEntityConfigureException, MaterialException {
        if(ServiceCollectionsHelper.checkNullList(rawProdPickingExtendAmountModelList) || ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemUIModelList)){
            return;
        }
        for(ProdPickingRefMaterialItemUIModel prodPickingRefMaterialItemUIModel: prodPickingRefMaterialItemUIModelList){
            ProdPickingExtendAmountModel prodPickingExtendAmountModel =
                    ServiceCollectionsHelper.filterOnline(rawProdPickingExtendAmountModelList, amountModel->{
                        ProdPickingRefMaterialItem prodPickingRefMaterialItem =
                                amountModel.getProdPickingRefMaterialItem();
                        return prodPickingRefMaterialItem.getUuid().equals(prodPickingRefMaterialItemUIModel.getUuid());
                    });
            if(prodPickingExtendAmountModel != null){
                String client = prodPickingExtendAmountModel.getProdPickingRefMaterialItem().getClient();
                prodPickingRefMaterialItemUIModel.setInProcessAmount(prodPickingExtendAmountModel.getInProcessAmount().getAmount());
                prodPickingRefMaterialItemUIModel.setInProcessAmountLabel(getAmountLabelUnion(prodPickingExtendAmountModel.getInProcessAmount(),
                        client));
                prodPickingRefMaterialItemUIModel.setInStockAmount(prodPickingExtendAmountModel.getInStockAmount().getAmount());
                prodPickingRefMaterialItemUIModel.setInStockAmountLabel(getAmountLabelUnion(prodPickingExtendAmountModel.getInStockAmount(),
                        client));
                prodPickingRefMaterialItemUIModel.setPickedAmount(prodPickingExtendAmountModel.getPickedAmount().getAmount());
                prodPickingRefMaterialItemUIModel.setPickedAmountLabel(getAmountLabelUnion(prodPickingExtendAmountModel.getPickedAmount(),
                        client));
                prodPickingRefMaterialItemUIModel.setToPickAmount(prodPickingExtendAmountModel.getToPickAmount().getAmount());
                prodPickingRefMaterialItemUIModel.setToPickAmountLabel(getAmountLabelUnion(prodPickingExtendAmountModel.getToPickAmount(),
                        client));
            }
        }
    }

    private String getAmountLabelUnion(StorageCoreUnit storageCoreUnit, String client) throws MaterialException, ServiceEntityConfigureException {
        return  materialStockKeepUnitManager.getAmountLabel(storageCoreUnit.getRefMaterialSKUUUID(),
                        storageCoreUnit.getRefUnitUUID(),
                        storageCoreUnit.getAmount(), client);
    }

    /**
     * Logic to refresh update ref Order item
     *
     * @param prodPickingRefOrderItemServiceModel
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public void refreshPickingRefOrderItem(ProdPickingRefOrderItemServiceModel prodPickingRefOrderItemServiceModel, List<ProdPickingExtendAmountModel> rawProdPickingExtendAmountModelList)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
        List<ServiceEntityNode> prodPickingRefMaterialItemList =
                ProdPickingOrderManager.pluckToRefMaterialItemList(prodPickingRefOrderItemServiceModel.getProdPickingRefMaterialItemList());
        if (!ServiceCollectionsHelper.checkNullList(prodPickingRefMaterialItemList)) {
            for (ServiceEntityNode seNode : prodPickingRefMaterialItemList) {
                ProdPickingRefMaterialItem prodPickingRefMaterialItem = (ProdPickingRefMaterialItem) seNode;
                prodPickingRefMaterialItemManager.refreshPickingRefMaterialItem(prodPickingRefMaterialItem, rawProdPickingExtendAmountModelList);
            }
        }
    }

}
