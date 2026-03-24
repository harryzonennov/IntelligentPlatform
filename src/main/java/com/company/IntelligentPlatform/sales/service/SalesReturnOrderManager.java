package com.company.IntelligentPlatform.sales.service;

import java.util.*;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.sales.dto.*;
import com.company.IntelligentPlatform.sales.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.sales.repository.SalesReturnOrderRepository;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [SalesReturnOrder]
 *
 * @author
 * @date Mon Nov 09 15:17:08 CST 2015
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
public class SalesReturnOrderManager extends ServiceEntityManager {

    public static final String METHOD_ConvSalesReturnOrderToUI = "convSalesReturnOrderToUI";

    public static final String METHOD_ConvUIToSalesReturnOrder = "convUIToSalesReturnOrder";

    @Autowired
    protected SalesReturnOrderRepository salesReturnOrderDAO;

    @Autowired
    protected SalesReturnOrderConfigureProxy salesReturnOrderConfigureProxy;

    @Autowired
    protected SalesReturnOrderIdHelper salesReturnOrderIdHelper;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected StandardPriorityProxy standardPriorityProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected SalesReturnMaterialItemServiceUIModelExtension salesReturnMaterialItemServiceUIModelExtension;

    @Autowired
    protected SalesReturnOrderServiceUIModelExtension salesReturnOrderServiceUIModelExtension;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected SalesReturnOrderSearchProxy salesReturnOrderSearchProxy;

    @Autowired
    protected ServiceUIMetaProxy serviceUIMetaProxy;

    @Autowired
    protected SystemConfigureCategoryManager systemConfigureCategoryManager;

    protected Logger logger = LoggerFactory.getLogger(SalesReturnOrderManager.class);

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    public Map<Integer, String> initPriorityCode(String languageCode)
            throws ServiceEntityInstallationException {
        return standardPriorityProxy.getPriorityMap(languageCode);
    }

    public Map<Integer, String> initStatus(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, SalesReturnOrderUIModel.class, IDocumentNodeFieldConstant.STATUS);
    }

    public SalesReturnOrderManager() {
        super.seConfigureProxy = new SalesReturnOrderConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new SalesReturnOrderDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(salesReturnOrderDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(salesReturnOrderConfigureProxy);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        SalesReturnOrder salesReturnOrder = (SalesReturnOrder) super
                .newRootEntityNode(client);
        String salesReturnOrderId = salesReturnOrderIdHelper
                .genDefaultId(client);
        salesReturnOrder.setId(salesReturnOrderId);
        return salesReturnOrder;
    }

    public void convSalesReturnOrderToUI(SalesReturnOrder salesReturnOrder,
                                         SalesReturnOrderUIModel salesReturnOrderUIModel)
            throws ServiceEntityInstallationException {
        convSalesReturnOrderToUI(salesReturnOrder, salesReturnOrderUIModel,
                null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convSalesReturnOrderToUI(SalesReturnOrder salesReturnOrder,
                                         SalesReturnOrderUIModel salesReturnOrderUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (salesReturnOrder != null) {
            docFlowProxy.convDocumentToUI(salesReturnOrder,
                    salesReturnOrderUIModel, logonInfo);
            salesReturnOrderUIModel.setBarcode(salesReturnOrder.getBarcode());
            salesReturnOrderUIModel.setPriorityCode(salesReturnOrder
                    .getPriorityCode());
            salesReturnOrderUIModel.setProductionBatchNumber(salesReturnOrder.getProductionBatchNumber());
            if (logonInfo != null) {
                Map<Integer, String> priorityCodeMap = initPriorityCode(logonInfo
                        .getLanguageCode());
                salesReturnOrderUIModel.setPriorityCodeValue(priorityCodeMap
                        .get(salesReturnOrder.getPriorityCode()));
            }
            salesReturnOrderUIModel.setStatus(salesReturnOrder.getStatus());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = initStatus(logonInfo
                        .getLanguageCode());
                salesReturnOrderUIModel.setStatusValue(statusMap
                        .get(salesReturnOrder.getStatus()));
            }
            salesReturnOrderUIModel.setTaxRate(salesReturnOrder.getTaxRate());
            salesReturnOrderUIModel.setGrossPrice(salesReturnOrder
                    .getGrossPrice());
            salesReturnOrderUIModel.setGrossPriceDisplay(salesReturnOrder
                    .getGrossPriceDisplay());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:salesReturnOrder
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToSalesReturnOrder(
            SalesReturnOrderUIModel salesReturnOrderUIModel,
            SalesReturnOrder rawEntity) {
        docFlowProxy.convUIToDocument(salesReturnOrderUIModel, rawEntity);
        rawEntity.setBarcode(salesReturnOrderUIModel.getBarcode());
        rawEntity.setPriorityCode(salesReturnOrderUIModel.getPriorityCode());
        rawEntity.setTaxRate(salesReturnOrderUIModel.getTaxRate());
        rawEntity.setNote(salesReturnOrderUIModel.getNote());
        rawEntity.setGrossPrice(salesReturnOrderUIModel.getGrossPrice());
        rawEntity.setGrossPriceDisplay(salesReturnOrderUIModel.getGrossPriceDisplay());
    }


    public ServiceDocumentExtendUIModel convSalesReturnOrderToDocExtUIModel(
            SalesReturnOrderUIModel salesReturnOrderUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel.setRefUIModel(salesReturnOrderUIModel);
        serviceDocumentExtendUIModel.setUuid(salesReturnOrderUIModel.getUuid());
        serviceDocumentExtendUIModel.setParentNodeUUID(salesReturnOrderUIModel
                .getParentNodeUUID());
        serviceDocumentExtendUIModel.setRootNodeUUID(salesReturnOrderUIModel
                .getRootNodeUUID());
        serviceDocumentExtendUIModel.setId(salesReturnOrderUIModel.getId());
        serviceDocumentExtendUIModel.setStatus(salesReturnOrderUIModel
                .getStatus());
        serviceDocumentExtendUIModel.setStatusValue(salesReturnOrderUIModel
                .getStatusValue());
        serviceDocumentExtendUIModel
                .setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER);
        if(logonInfo != null){
            serviceDocumentExtendUIModel
                    .setDocumentTypeValue(serviceDocumentComProxy
                            .getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER,
                                    logonInfo.getLanguageCode()));
        }
        // Logic of reference Date
        String referenceDate = salesReturnOrderUIModel.getCreatedDate();
        if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
            referenceDate = salesReturnOrderUIModel.getCreatedDate();
        }
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    public ServiceDocumentExtendUIModel convSalesReturnMatItemToDocExtUIModel(
            SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
        serviceDocumentExtendUIModel
                .setRefUIModel(salesReturnMaterialItemUIModel);
        docFlowProxy.convDocMatItemUIToDocExtUIModel(salesReturnMaterialItemUIModel,
                serviceDocumentExtendUIModel, logonInfo,
                IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER);
        serviceDocumentExtendUIModel.setId(salesReturnMaterialItemUIModel
                .getParentDocId());
        serviceDocumentExtendUIModel
                .setStatus(salesReturnMaterialItemUIModel.getParentDocStatus());
        serviceDocumentExtendUIModel
                .setStatusValue(salesReturnMaterialItemUIModel
                        .getParentDocStatusValue());
        // Logic of reference Date
        String referenceDate = salesReturnMaterialItemUIModel
                .getCreatedDate();
        serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
        return serviceDocumentExtendUIModel;
    }

    @Override
    public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
            ServiceEntityNode seNode, LogonInfo logonInfo) {
        if (seNode == null) {
            return null;
        }
        if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
            SalesReturnOrder salesReturnOrder = (SalesReturnOrder) seNode;
            try {
                SalesReturnOrderUIModel salesReturnOrderUIModel =
                        (SalesReturnOrderUIModel) genUIModelFromUIModelExtension(
                                SalesReturnOrderUIModel.class,
                                salesReturnOrderServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                salesReturnOrder, logonInfo, null);
                return convSalesReturnOrderToDocExtUIModel(salesReturnOrderUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, SalesReturnOrder.SENAME));
            }
        }
        if (SalesReturnMaterialItem.NODENAME.equals(seNode.getNodeName())) {
            SalesReturnMaterialItem salesReturnMaterialItem = (SalesReturnMaterialItem) seNode;
            try {
                SalesReturnMaterialItemUIModel salesReturnMaterialItemUIModel =
                        (SalesReturnMaterialItemUIModel) genUIModelFromUIModelExtension(
                                SalesReturnMaterialItemUIModel.class,
                                salesReturnMaterialItemServiceUIModelExtension
                                        .genUIModelExtensionUnion().get(0),
                                salesReturnMaterialItem, logonInfo, null);
                return convSalesReturnMatItemToDocExtUIModel(salesReturnMaterialItemUIModel, logonInfo);
            } catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
                        e, SalesReturnMaterialItem.NODENAME));
            }
        }
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return IServiceModelConstants.SalesReturnOrder;
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return salesReturnOrderSearchProxy;
    }

}