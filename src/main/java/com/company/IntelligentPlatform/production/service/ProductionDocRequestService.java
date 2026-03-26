package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.controller.ProductionDocBatchRequest;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.service.SalesContractException;
import com.company.IntelligentPlatform.sales.service.SalesContractManager;
import com.company.IntelligentPlatform.sales.service.SalesForcastException;
import com.company.IntelligentPlatform.sales.service.SalesForcastManager;
import com.company.IntelligentPlatform.sales.model.SalesContract;
import com.company.IntelligentPlatform.sales.model.SalesContractMaterialItem;
import com.company.IntelligentPlatform.sales.model.SalesForcast;
import com.company.IntelligentPlatform.sales.model.SalesForcastMaterialItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.text.ParseException;
import java.util.*;

@Service
public class ProductionDocRequestService {

    @Autowired
    protected SalesContractManager salesContractManager;

    @Autowired
    protected SalesForcastManager salesForcastManager;

    @Autowired
    protected ProductionPlanManager productionPlanManager;

    @Autowired
    protected SalesForcastMaterialItemServiceUIModelExtension salesForcastMaterialItemServiceUIModelExtension;

    @Autowired
    protected SalesContractMaterialItemServiceUIModelExtension salesContractMaterialItemServiceUIModelExtension;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected SalesItemToCrossProdPlanProxy salesItemToCrossProdPlanProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    public List<ServiceEntityNode> searchDocMatItemList(ProductionDocRequestItemSearchModel productionDocRequestItemSearchModel, LogonInfo logonInfo)
            throws SearchConfigureException, ServiceEntityInstallationException, ServiceEntityConfigureException, AuthorizationException, LogonInfoException {
        int documentType = productionDocRequestItemSearchModel.getParentDocType();
        convertSearchModelFormat(productionDocRequestItemSearchModel);
        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT) {
            // in case need to search sales contract only
            return searchSalesContractMatItemList(productionDocRequestItemSearchModel, logonInfo);
        }
        if (documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST) {
            // in case need to search sales contract only
            return searchSalesForcastMatItemList(productionDocRequestItemSearchModel, logonInfo);
        }
        // set constant properties
        DocEmbedMaterialSKUSearchModel itemMaterialSKU = new DocEmbedMaterialSKUSearchModel();
        itemMaterialSKU.setSupplyType(Material.SUPPLYTYPE_SELF_PROD);
        productionDocRequestItemSearchModel.setItemMaterialSKU(itemMaterialSKU);

        // in case need to search all types of document
        List<ServiceEntityNode> resultList = new ArrayList<>();
        ServiceCollectionsHelper.mergeToList(resultList, searchSalesContractMatItemList(productionDocRequestItemSearchModel, logonInfo));
        ServiceCollectionsHelper.mergeToList(resultList, searchSalesForcastMatItemList(productionDocRequestItemSearchModel, logonInfo));
        return resultList;
    }

    private void convertSearchModelFormat(
            ProductionDocRequestItemSearchModel productionDocRequestItemSearchModel) {
        try {
            if (!ServiceEntityStringHelper
                    .checkNullString(productionDocRequestItemSearchModel
                            .getPlanExecutionDateStrLow())) {
                productionDocRequestItemSearchModel
                        .setPlanExecutionDateLow(DefaultDateFormatConstant.DATE_FORMAT
                                .parse(productionDocRequestItemSearchModel
                                        .getPlanExecutionDateStrLow()));
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(productionDocRequestItemSearchModel
                            .getPlanExecutionDateStrHigh())) {
                productionDocRequestItemSearchModel
                        .setPlanExecutionDateHigh(DefaultDateFormatConstant.DATE_FORMAT
                                .parse(productionDocRequestItemSearchModel
                                        .getPlanExecutionDateStrHigh()));
            }
        } catch (ParseException e) {
            // do nothing
        }
    }

    public List<ProductionDocRequestUIModel> getModuleListCore(
            List<ServiceEntityNode> rawList, LogonInfo logonInfo)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException, ServiceModuleProxyException {
        List<ProductionDocRequestUIModel> productionDocRequestUIModelList =
                new ArrayList<>();
        for (ServiceEntityNode rawNode : rawList) {
            ProductionDocRequestUIModel productionDocRequestUIModel = new ProductionDocRequestUIModel();
            if(rawNode instanceof SalesForcastMaterialItem){
                SalesForcastMaterialItem salesForcastMaterialItem = (SalesForcastMaterialItem) rawNode;
                if(!ServiceEntityStringHelper.checkNullString(salesForcastMaterialItem.getNextDocMatItemUUID())){
                    continue;
                }
                SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel = (SalesForcastMaterialItemUIModel) salesForcastManager
                        .genUIModelFromUIModelExtension(
                                SalesForcastMaterialItemUIModel.class,
                                salesForcastMaterialItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                salesForcastMaterialItem, logonInfo, null
                                );
                productionDocRequestUIModel = convToProductionDocRequestUIModel(salesForcastMaterialItemUIModel);
                productionDocRequestUIModel.setParentDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST);
            }

            if(rawNode instanceof SalesContractMaterialItem){
                SalesContractMaterialItem salesContractMaterialItem = (SalesContractMaterialItem) rawNode;
                if(!ServiceEntityStringHelper.checkNullString(salesContractMaterialItem.getNextDocMatItemUUID())){
                    continue;
                }
                SalesContractMaterialItemUIModel salesContractMaterialItemUIModel =
                        (SalesContractMaterialItemUIModel) salesForcastManager
                        .genUIModelFromUIModelExtension(
                                SalesContractMaterialItemUIModel.class,
                                salesContractMaterialItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                salesContractMaterialItem, logonInfo, null
                        );
                productionDocRequestUIModel = convToProductionDocRequestUIModel(salesContractMaterialItemUIModel);
                productionDocRequestUIModel.setParentDocType(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
            }
            convCommonToUIModel(productionDocRequestUIModel, logonInfo.getLanguageCode());
            productionDocRequestUIModelList.add(productionDocRequestUIModel);
        }
        return productionDocRequestUIModelList;
    }

    private List<ServiceEntityNode> searchSalesContractMatItemList(ProductionDocRequestItemSearchModel productionDocRequestItemSearchModel, LogonInfo logonInfo)
            throws SearchConfigureException, ServiceEntityInstallationException,
            ServiceEntityConfigureException, AuthorizationException, LogonInfoException {
        SalesContractMaterialItemSearchModel salesContractMaterialItemSearchModel =
                convToSalesContractMaterialItemSearchModel(productionDocRequestItemSearchModel);
        salesContractMaterialItemSearchModel.getParentDocHeaderModel().setStatus(SalesContract.STATUS_APPROVED);
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(salesContractMaterialItemSearchModel);
        return salesContractManager.getSearchProxy().searchItemList(searchContextBuilder.build()).getResultList();
    }

    private List<ServiceEntityNode> searchSalesForcastMatItemList(ProductionDocRequestItemSearchModel productionDocRequestItemSearchModel, LogonInfo logonInfo)
            throws SearchConfigureException, ServiceEntityInstallationException,
            ServiceEntityConfigureException, AuthorizationException, LogonInfoException {
        SalesForcastMaterialItemSearchModel salesForcastMaterialItemSearchModel =
                convToSalesForcastMaterialItemSearchModel(productionDocRequestItemSearchModel);
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(salesForcastMaterialItemSearchModel);
        salesForcastMaterialItemSearchModel.getParentDocHeaderModel().setStatus(SalesForcast.STATUS_APPROVED);
        return salesForcastManager.getSearchProxy().searchItemList(searchContextBuilder.build()).getResultList();
    }

    private List<ProductionPlanInitModel> parseToProdPlanInitModelList(ProductionDocBatchRequest genRequest,
                                                            LogonInfo logonInfo) throws ServiceEntityConfigureException, ProductionPlanException, ServiceComExecuteException {
        /*
         * [Step1] Group and find all relative doc mat item list
         */
        Map<Integer, List<String>> docUUIDMap = parseToDocUUIDMap(genRequest);
        if(docUUIDMap == null){
            return null;
        }
        List<ServiceEntityNode> docMatItemList = getDocMatItemListWrapper(docUUIDMap, logonInfo.getClient());
        if(ServiceCollectionsHelper.checkNullList(docMatItemList)){
            return null;
        }
        List<ProductionPlanInitModel> productionPlanInitModelList = new ArrayList<>();
        for(ServiceEntityNode serviceEntityNode: docMatItemList){
            DocMatItemNode docMatItemNode = (DocMatItemNode) serviceEntityNode;
            ProductionPlanInitModel productionPlanInitModel = new ProductionPlanInitModel();
            if(genRequest.getPlanStartTime() == null){
                throw new ProductionPlanException(ProductionPlanException.PARA_NO_START_TIME, "");
            }
            productionPlanInitModel.setPlanStartPrepareDate(genRequest.getPlanStartTime());
            productionPlanInitModel.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
            productionPlanInitModel.setAmount(docMatItemNode.getAmount());
            productionPlanInitModel.setPrevDocType(docMatItemNode.getHomeDocumentType());
            productionPlanInitModel.setPrevDocMatItemUUID(docMatItemNode.getUuid());
            productionPlanInitModel.setProductionBatchNumber(genRequest.getProductionBatchNumber());
            productionPlanInitModel.setRefUnitUUID(docMatItemNode.getRefUnitUUID());
            // Set BOM Order
            BillOfMaterialOrder billOfMaterialOrder =
                    billOfMaterialOrderManager.getDefaultBOMBySKU(docMatItemNode.getRefMaterialSKUUUID(),
                            logonInfo.getClient());
            if(billOfMaterialOrder == null){
                MaterialStockKeepUnit materialStockKeepUnit =
                        materialStockKeepUnitManager.getMaterialSKUWrapper(docMatItemNode.getRefMaterialSKUUUID(),
                                docMatItemNode.getClient(), null);
                throw new ProductionPlanException(ProductionPlanException.PARA_NO_BOM, materialStockKeepUnit.getId());
            }
            productionPlanInitModel.setRefBillOfMaterialUUID(billOfMaterialOrder.getUuid());
            productionPlanInitModelList.add(productionPlanInitModel);
        }
        return productionPlanInitModelList;
    }

    /**
     * Get all possible prev doc mat item list from current plan
     * @param planUUID
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllPrevRequestMatItemByPlan(String planUUID, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> resultList = new ArrayList<>();
        // Try to get from sales forcast
        List<ServiceEntityNode> salesForcastMatItemList = salesForcastManager.getEntityNodeListByKey(planUUID,
                IServiceEntityCommonFieldConstant.NEXTDOCMATITEMUUID,
                SalesForcastMaterialItem.NODENAME, client, null);
        ServiceCollectionsHelper.mergeToList(resultList, salesForcastMatItemList);
        // Try to get from sales contract
        List<ServiceEntityNode> salesContractMatItemList = salesContractManager.getEntityNodeListByKey(planUUID,
                IServiceEntityCommonFieldConstant.NEXTDOCMATITEMUUID,
                SalesContractMaterialItem.NODENAME, client, null);
        ServiceCollectionsHelper.mergeToList(resultList, salesContractMatItemList);
        return resultList;
    }

    public synchronized List<ServiceEntityNode> execBatchCreateProdPlan(ProductionDocBatchRequest genRequest, int category,
                                                          LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ProductionPlanException, MaterialException,
            ServiceModuleProxyException, SalesForcastException, SalesContractException, ServiceComExecuteException,
            DocActionException {
        Map<Integer, List<String>> docUUIDMap = parseToDocUUIDMap(genRequest);
        if(docUUIDMap == null){
            return null;
        }
        List<ServiceEntityNode> docMatItemList = getDocMatItemListWrapper(docUUIDMap, logonInfo.getClient());
        List<ProductionPlanInitModel> productionPlanInitModelList = parseToProdPlanInitModelList(genRequest,
                logonInfo);
        if(ServiceCollectionsHelper.checkNullList(productionPlanInitModelList)){
            return null;
        }
        List<ServiceEntityNode> productionPlanList = new ArrayList<>();
        for(ProductionPlanInitModel productionPlanInitModel:productionPlanInitModelList){
            ProductionPlanServiceModel productionPlanServiceModel =
                    productionPlanManager.newProductionPlanServiceModel(productionPlanInitModel,
                    category, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID(), logonInfo.getClient());
            if(productionPlanServiceModel != null){
                ProductionPlan productionPlan = productionPlanServiceModel.getProductionPlan();
                productionPlanList.add(productionPlan);
                if(!ServiceEntityStringHelper.checkNullString(productionPlanInitModel.getPrevDocMatItemUUID())){
                    DocMatItemNode docMatItemNode =
                            (DocMatItemNode) ServiceCollectionsHelper.filterOnline(docMatItemList,
                                    serviceEntityNode -> productionPlanInitModel.getPrevDocMatItemUUID().equals(serviceEntityNode.getUuid()));
                    if(docMatItemNode != null){
                        // Let prev doc mat item pointing to plan root node, later will point to order target item
                        docMatItemNode.setNextDocMatItemUUID(productionPlan.getUuid());
                        docMatItemNode.setNextDocType(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN);
                        docFlowProxy.updateDocItemNode(docMatItemNode, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID() );
                    }
                }
            }
        }
        if(ServiceCollectionsHelper.checkNullList(productionPlanList)){
            throw new ProductionPlanException(ProductionPlanException.PARA_NO_PRODPLAN);
        }

        // Batch set sales document in plan status
        salesItemToCrossProdPlanProxy.batchInPlanSalesDocList(docMatItemList,
                LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return productionPlanList;
    }

    private List<ServiceEntityNode> getDocMatItemListWrapper(Map<Integer, List<String>> docUUIDMap, String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> resultList = new ArrayList<>();
        Set<Integer> keySet = docUUIDMap.keySet();
        for(Integer docType:keySet){
            if(docType == IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST){
                List<ServiceEntityNode> salesForcastMatItemList = getSalesForcastMatItemList(docUUIDMap.get(docType),
                        client);
                if(!ServiceCollectionsHelper.checkNullList(salesForcastMatItemList)){
                    ServiceCollectionsHelper.mergeToList(resultList, salesForcastMatItemList);
                }
            }
            if(docType == IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT){
                List<ServiceEntityNode> salesContractMatItemList = getSalesContractMatItemList(docUUIDMap.get(docType),
                        client);
                if(!ServiceCollectionsHelper.checkNullList(salesContractMatItemList)){
                    ServiceCollectionsHelper.mergeToList(resultList, salesContractMatItemList);
                }
            }
        }
        return resultList;
    }

    private List<ServiceEntityNode> getSalesContractMatItemList(List<String> uuidList, String client) throws ServiceEntityConfigureException {
        return salesContractManager.getEntityNodeListByMultipleKey(uuidList, IServiceEntityNodeFieldConstant.UUID,
                SalesContractMaterialItem.NODENAME, client, null);
    }

    private List<ServiceEntityNode> getSalesForcastMatItemList(List<String> uuidList, String client) throws ServiceEntityConfigureException {
        return salesForcastManager.getEntityNodeListByMultipleKey(uuidList, IServiceEntityNodeFieldConstant.UUID,
                SalesForcastMaterialItem.NODENAME, client, null);
    }

    private Map<Integer, List<String>> parseToDocUUIDMap(DocumentMatItemBatchGenRequest genRequest){
        Map<Integer, List<String>> docUUIDMap = new HashMap<>();
        List<String> rawUUIDList = genRequest.getUuidList();
        if(ServiceCollectionsHelper.checkNullList(rawUUIDList)){
            return null;
        }
        for(String rawUUID: rawUUIDList){
            ServiceFlowRuntimeEngine.FlowBusinessKey flowBusinessKey = ServiceFlowRuntimeEngine.decodeBusinessKey(rawUUID);
            if(docUUIDMap.containsKey(flowBusinessKey.getDocumentType())){
                List<String> tempUUIDList = docUUIDMap.get(flowBusinessKey.getDocumentType());
                tempUUIDList.add(flowBusinessKey.getUuid());
            }else{
                docUUIDMap.put(flowBusinessKey.getDocumentType(), ServiceCollectionsHelper.asList(flowBusinessKey.getUuid()));
            }
        }
        return docUUIDMap;
    }

    public Map<Integer, String> initDocumentTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        Map<Integer, String> rawDocTypeMap = serviceDocumentComProxy.getDocumentTypeMap(true, languageCode);
        Map<Integer, String> resultDocTypeMap = new HashMap<>();
        resultDocTypeMap.put(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT,
                rawDocTypeMap.get(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT));
        resultDocTypeMap.put(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST,
                rawDocTypeMap.get(IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST));
        return resultDocTypeMap;
    }

    public SalesForcastMaterialItemSearchModel convToSalesForcastMaterialItemSearchModel(
            ProductionDocRequestItemSearchModel productionDocRequestItemSearchModel) {
        SalesForcastMaterialItemSearchModel salesForcastMaterialItemSearchModel =
                new SalesForcastMaterialItemSearchModel();
        salesForcastMaterialItemSearchModel.setItemMaterialSKU(productionDocRequestItemSearchModel.getItemMaterialSKU());
        salesForcastMaterialItemSearchModel.setItemStatus(productionDocRequestItemSearchModel.getItemStatus());
        salesForcastMaterialItemSearchModel.getParentDocHeaderModel().setId(productionDocRequestItemSearchModel.getParentDocId());
        salesForcastMaterialItemSearchModel.getParentDocHeaderModel().setName(productionDocRequestItemSearchModel.getParentDocName());
        salesForcastMaterialItemSearchModel.getParentDocHeaderModel().setStatus(productionDocRequestItemSearchModel.getParentDocStatus());
        salesForcastMaterialItemSearchModel.getParentDocHeaderModel().setUuid(productionDocRequestItemSearchModel.getParentDocUUID());
        salesForcastMaterialItemSearchModel.setPlanExecutionDateHigh(productionDocRequestItemSearchModel.getPlanExecutionDateHigh());
        salesForcastMaterialItemSearchModel.setPlanExecutionDateLow(productionDocRequestItemSearchModel.getPlanExecutionDateLow());
        salesForcastMaterialItemSearchModel.setPlanExecutionDateStrLow(productionDocRequestItemSearchModel.getPlanExecutionDateStrLow());
        salesForcastMaterialItemSearchModel.setPlanExecutionDateStrHigh(productionDocRequestItemSearchModel.getPlanExecutionDateStrHigh());
        return salesForcastMaterialItemSearchModel;
    }

    public SalesContractMaterialItemSearchModel convToSalesContractMaterialItemSearchModel(
            ProductionDocRequestItemSearchModel productionDocRequestItemSearchModel) {
        SalesContractMaterialItemSearchModel salesContractMaterialItemSearchModel =
                new SalesContractMaterialItemSearchModel();
        salesContractMaterialItemSearchModel.setItemMaterialSKU(productionDocRequestItemSearchModel.getItemMaterialSKU());
        salesContractMaterialItemSearchModel.setItemStatus(productionDocRequestItemSearchModel.getItemStatus());
        salesContractMaterialItemSearchModel.getParentDocHeaderModel().setUuid(productionDocRequestItemSearchModel.getParentDocId());
        salesContractMaterialItemSearchModel.getParentDocHeaderModel().setName(productionDocRequestItemSearchModel.getParentDocName());
        salesContractMaterialItemSearchModel.getParentDocHeaderModel().setStatus(productionDocRequestItemSearchModel.getParentDocStatus());
        salesContractMaterialItemSearchModel.getParentDocHeaderModel().setUuid(productionDocRequestItemSearchModel.getParentDocUUID());
        salesContractMaterialItemSearchModel.setPlanExecutionDateHigh(productionDocRequestItemSearchModel.getPlanExecutionDateHigh());
        salesContractMaterialItemSearchModel.setPlanExecutionDateLow(productionDocRequestItemSearchModel.getPlanExecutionDateLow());
        salesContractMaterialItemSearchModel.setPlanExecutionDateStrLow(productionDocRequestItemSearchModel.getPlanExecutionDateStrLow());
        salesContractMaterialItemSearchModel.setPlanExecutionDateStrHigh(productionDocRequestItemSearchModel.getPlanExecutionDateStrHigh());
        return salesContractMaterialItemSearchModel;
    }

    public ProductionDocRequestUIModel convToProductionDocRequestUIModel(SalesForcastMaterialItemUIModel salesForcastMaterialItemUIModel) {
        ProductionDocRequestUIModel productionDocRequestUIModel = new ProductionDocRequestUIModel();
        DocFlowProxy.copyDocMatItemUIModelMutual(salesForcastMaterialItemUIModel, productionDocRequestUIModel, true);
        productionDocRequestUIModel.setPlanExecutionDate(salesForcastMaterialItemUIModel.getPlanExecutionDate());
        productionDocRequestUIModel.setParentDocId(salesForcastMaterialItemUIModel.getParentDocId());
        productionDocRequestUIModel.setParentDocName(salesForcastMaterialItemUIModel.getParentDocName());
        productionDocRequestUIModel.setParentDocStatus(salesForcastMaterialItemUIModel.getParentDocStatus());
        productionDocRequestUIModel.setParentDocStatusValue(salesForcastMaterialItemUIModel.getParentDocStatusValue());
        return productionDocRequestUIModel;
    }

    public ProductionDocRequestUIModel convToProductionDocRequestUIModel(SalesContractMaterialItemUIModel salesContractMaterialItemUIModel) {
        ProductionDocRequestUIModel productionDocRequestUIModel = new ProductionDocRequestUIModel();
        DocFlowProxy.copyDocMatItemUIModelMutual(salesContractMaterialItemUIModel, productionDocRequestUIModel, true);
        productionDocRequestUIModel.setPlanExecutionDate(salesContractMaterialItemUIModel.getPlanExecutionDate());
        productionDocRequestUIModel.setParentDocId(salesContractMaterialItemUIModel.getParentDocId());
        productionDocRequestUIModel.setParentDocName(salesContractMaterialItemUIModel.getParentDocName());
        productionDocRequestUIModel.setParentDocStatus(salesContractMaterialItemUIModel.getParentDocStatus());
        productionDocRequestUIModel.setParentDocStatusValue(salesContractMaterialItemUIModel.getParentDocStatusValue());
        return productionDocRequestUIModel;
    }

    public void convCommonToUIModel(ProductionDocRequestUIModel productionDocRequestUIModel,
                                                  String languageCode) throws ServiceEntityInstallationException {
        String businessKey =
                ServiceFlowRuntimeEngine.encodeBusinessKey(productionDocRequestUIModel.getParentDocType(),
                        productionDocRequestUIModel.getUuid());
        productionDocRequestUIModel.setBusinessKey(businessKey);
        Map<Integer, String> documentTypeMap = serviceDocumentComProxy.getDocumentTypeMap(true,
                languageCode);
        productionDocRequestUIModel.setParentDocTypeValue(documentTypeMap.get(productionDocRequestUIModel.getParentDocType()));
    }

}
