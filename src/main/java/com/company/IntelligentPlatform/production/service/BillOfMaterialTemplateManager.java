package com.company.IntelligentPlatform.production.service;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialOrderServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateServiceUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateUIModel;
import com.company.IntelligentPlatform.production.repository.BillOfMaterialTemplateRepository;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceJSONRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Logic Manager CLASS FOR Service Entity [BillOfMaterialTemplate]
 *
 * @author
 * @date Fri May 28 19:15:35 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Service
@Transactional
public class BillOfMaterialTemplateManager extends ServiceEntityManager {

    @Autowired
    protected BillOfMaterialTemplateRepository billOfMaterialTemplateDAO;

    @Autowired
    protected BillOfMaterialTemplateConfigureProxy billOfMaterialTemplateConfigureProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected BillOfMaterialTemplateIdHelper billOfMaterialTemplateIdHelper;

    @Autowired
    protected BillOfMaterialTemplateItemIdHelper billOfMaterialTemplateItemIdHelper;

    @Autowired
    protected BillOfMaterialTemplateItemManager billOfMaterialTemplateItemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected BillOfMaterialTemplateCrossProxy billOfMaterialTemplateCrossProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected BillOfMaterialTemplateServiceUIModelExtension billOfMaterialTemplateServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialOrderServiceUIModelExtension billOfMaterialOrderServiceUIModelExtension;

    @Autowired
    protected BillOfMaterialTemplateSearchProxy billOfMaterialTemplateSearchProxy;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    public static final String METHOD_ConvBillOfMaterialTemplateToUI = "convBillOfMaterialTemplateToUI";

    public static final String METHOD_ConvUIToBillOfMaterialTemplate = "convUIToBillOfMaterialTemplate";

    public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

    public static final String METHOD_ConvProcessRouteOrderToUI = "convProcessRouteOrderToUI";

    public static final String METHOD_ConvProdWorkCenterToUI = "convProdWorkCenterToUI";

    protected Map<String, BillOfMaterialTemplate> billOfMaterialTemplateMap = new HashMap<>();

    protected Map<Integer, String> itemViewTypeMap;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Map<Integer, String>> statusMapLan = new HashMap<>();

    protected Map<String, Map<Integer, String>> leadTimeCalModeMap = new HashMap<>();

    public Map<Integer, String> initStatusMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.statusMapLan, BillOfMaterialTemplateUIModel.class, "status");
    }

    // TODO to initialize with lanugage code ?
    public Map<Integer, String> initItemViewTypeMap()
            throws ServiceEntityInstallationException {
        if (this.itemViewTypeMap == null) {
            this.itemViewTypeMap = serviceDropdownListHelper.getUIDropDownMap(
                    BillOfMaterialTemplateUIModel.class, "itemViewType");
        }
        return this.itemViewTypeMap;
    }

    public Map<Integer, String> initLeadTimeCalModeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
                this.leadTimeCalModeMap, BillOfMaterialTemplateUIModel.class, "leadTimeCalMode");
    }

    public BillOfMaterialTemplateManager() {
        super.seConfigureProxy = new BillOfMaterialTemplateConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new BillOfMaterialTemplateDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(billOfMaterialTemplateDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(billOfMaterialTemplateConfigureProxy);
    }

    @Override
    public ServiceEntityNode newRootEntityNode(String client)
            throws ServiceEntityConfigureException {
        BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) super
                .newRootEntityNode(client);
        String billOfMaterialID = billOfMaterialTemplateIdHelper
                .genDefaultId(client);
        billOfMaterialTemplate.setId(billOfMaterialID);
        return billOfMaterialTemplate;
    }

    /**
     * Logic of generate billOfMaterial Default ID
     *
     * @param billOfMaterialTemplate
     * @return
     * @throws ServiceEntityConfigureException
     */
    public String newBomItemId(BillOfMaterialTemplate billOfMaterialTemplate)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> rawBomItemList = getEntityNodeListByKey(
                billOfMaterialTemplate.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                BillOfMaterialTemplateItem.NODENAME, null);
        String billOfMaterialTemplateItemID = billOfMaterialTemplateItemIdHelper
                .genDefaultIdOnline(rawBomItemList,
                        billOfMaterialTemplate.getClient());
        billOfMaterialTemplateItemID = billOfMaterialTemplate.getId() + "-"
                + billOfMaterialTemplateItemID;
        return billOfMaterialTemplateItemID;
    }

    /**
     * Logic to get relative BOM Order from production order If production order
     * binding to some BOM item, then return parent BOM Order
     *
     * @param refBOMUUID
     * @return
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     */
    public BillOfMaterialTemplate getRefBillOfMaterialTemplateWrapper(
            String refBOMUUID, String client)
            throws ServiceEntityConfigureException, BillOfMaterialException {
        BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) getEntityNodeByKey(
                refBOMUUID, IServiceEntityNodeFieldConstant.UUID,
                BillOfMaterialTemplate.NODENAME, client, null);
        if (billOfMaterialTemplate == null) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) getEntityNodeByKey(
                    refBOMUUID, IServiceEntityNodeFieldConstant.UUID,
                    BillOfMaterialTemplateItem.NODENAME, client, null);
            if (billOfMaterialTemplateItem != null) {
                billOfMaterialTemplate = (BillOfMaterialTemplate) getEntityNodeByKey(
                        billOfMaterialTemplateItem.getRootNodeUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        BillOfMaterialTemplate.NODENAME, client, null);
            } else {
                throw new BillOfMaterialException(
                        BillOfMaterialException.PARA_NO_BOMOrder, refBOMUUID);
            }
        }
        return billOfMaterialTemplate;
    }

    /**
     * "temporary code" Get default bill of material order by SKU
     *
     * @param skuUUID
     * @param logonUser
     * @return
     * @throws ServiceEntityConfigureException
     */
    public BillOfMaterialTemplate getDefaultBOMBySKU(String skuUUID,
                                                     LogonUser logonUser) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> resultList = getBOMOrderListBySKU(skuUUID,
                logonUser.getClient());
        if (resultList == null || resultList.size() == 0) {
            return null;
        }
        return (BillOfMaterialTemplate) resultList.get(0);
    }

    public List<ServiceEntityNode> getBOMOrderListBySKU(String skuUUID,
                                                        String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> resultList = getEntityNodeListByKey(skuUUID,
                "refMaterialSKUUUID", BillOfMaterialTemplate.NODENAME, client,
                null);
        return resultList;
    }

    /**
     * Core Logic to archive BOM Order: set status to 'Retired'.
     *
     * @param billOfMaterialTemplate
     * @param logonUserUUID
     * @param organizationUUID
     */
    public void archiveBOMOrder(BillOfMaterialTemplate billOfMaterialTemplate,
                                String logonUserUUID, String organizationUUID) {
        BillOfMaterialTemplate billOfMaterialTemplateBack = (BillOfMaterialTemplate) billOfMaterialTemplate
                .clone();
        billOfMaterialTemplate.setStatus(BillOfMaterialTemplate.STATUS_RETIRED);
        updateSENode(billOfMaterialTemplate, billOfMaterialTemplateBack,
                logonUserUUID, organizationUUID);
    }


    /**
     * Core Logic to approve billOfMaterialTemplate and update to DB
     *
     * @param billOfMaterialTemplateServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void activeService(
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        String uuid = billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate().getUuid();
        String client = billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate().getClient();
        this.executeActionCore(billOfMaterialTemplateServiceModel,
                ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_SUBMITTED),
                BillOfMaterialTemplate.STATUS_INUSE,
                BillOfMaterialTemplateActionNode.DOC_ACTION_REJECT_APPROVE, null, logonUserUUID, organizationUUID);
        // Archive previous BOM Order
        billOfMaterialOrderManager.batchArchiveOldOrders(uuid, logonUserUUID, organizationUUID, client);
        // Generate new template to BOM Order
        BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel =
                billOfMaterialTemplateCrossProxy.cloneToBOMOrder(billOfMaterialTemplateServiceModel);
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderServiceModel.getBillOfMaterialOrder();
        billOfMaterialOrder.setStatus(BillOfMaterialOrder.STATUS_INUSE);
        billOfMaterialOrderManager.updateServiceModuleWithDelete(
                BillOfMaterialOrderServiceModel.class,
                billOfMaterialOrderServiceModel, logonUserUUID, organizationUUID, BillOfMaterialOrder.SENAME,
                billOfMaterialOrderServiceUIModelExtension);

    }


    /**
     * Core Logic to approve billOfMaterialTemplate and update to DB
     *
     * @param billOfMaterialTemplateServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void rejectApproveService(
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        this.executeActionCore(billOfMaterialTemplateServiceModel,
                ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_SUBMITTED),
                BillOfMaterialTemplate.STATUS_REJECT_APPROVAL,
                BillOfMaterialTemplateActionNode.DOC_ACTION_REJECT_APPROVE, null, logonUserUUID, organizationUUID);
    }


    /**
     * Core Logic to approve billOfMaterialTemplate and update to DB
     *
     * @param billOfMaterialTemplateServiceModel
     * @param logonUserUUID
     * @param organizationUUID
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public void executeActionCore(
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel, List<Integer> curStatusList,
            int targetStatus,
            int actionCode,
            Function<BillOfMaterialTemplateItemServiceModel, BillOfMaterialTemplateItemServiceModel> updateItemCallback,
            String logonUserUUID, String organizationUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        BillOfMaterialTemplate billOfMaterialTemplate = billOfMaterialTemplateServiceModel
                .getBillOfMaterialTemplate();
        if (!DocActionNodeProxy.checkCurStatus(curStatusList, billOfMaterialTemplate.getStatus())) {
            return;
        }
        billOfMaterialTemplate.setStatus(targetStatus);
        docActionNodeProxy.updateDocActionWrapper(actionCode,
                BillOfMaterialTemplateActionNode.NODENAME, null,
                IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALTEMPLATE, this,
                billOfMaterialTemplateServiceModel,
                billOfMaterialTemplate,
                logonUserUUID,
                organizationUUID);
        // update action node
        if (!ServiceCollectionsHelper
                .checkNullList(billOfMaterialTemplateServiceModel.getBillOfMaterialTemplateItemList())) {
            for (BillOfMaterialTemplateItemServiceModel billOfMaterialTemplateMaterialItemServiceModel :
                    billOfMaterialTemplateServiceModel
                            .getBillOfMaterialTemplateItemList()) {
                if (updateItemCallback != null) {
                    updateItemCallback.apply(billOfMaterialTemplateMaterialItemServiceModel);
                }
            }
        }
        updateServiceModuleWithDelete(
                BillOfMaterialTemplateServiceModel.class,
                billOfMaterialTemplateServiceModel, logonUserUUID, organizationUUID, BillOfMaterialTemplate.SENAME,
                billOfMaterialTemplateServiceUIModelExtension);
    }


    /**
     * Core Logic to Initialize BOM Order: set status to 'Initial'.
     *
     * @param billOfMaterialTemplateServiceModel
     * @param serialLogonInfo
     */
    public void reInitBOMTemplate(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
                               SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        this.executeActionCore(billOfMaterialTemplateServiceModel,
                ServiceCollectionsHelper.asList(BillOfMaterialTemplate.STATUS_INUSE),
                BillOfMaterialTemplate.STATUS_INITIAL,
                BillOfMaterialTemplateActionNode.DOC_ACTION_REINIT, null, serialLogonInfo.getRefUserUUID(),
                serialLogonInfo.getResOrgUUID());
        /*
         * [Step2] Increase version number
         */
        BillOfMaterialOrder activeBOMOrder =
                billOfMaterialOrderManager.getRecentBOMOrder(billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate().getUuid(),
        serialLogonInfo.getClient());
        if(activeBOMOrder != null){
            increseVersionNumber(billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate(), activeBOMOrder);
            updateSENode(billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate(),
                    serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        }
    }

    /**
     * Core Logic to Initialize BOM Order: set status to 'Initial'.
     *
     * @param billOfMaterialTemplateServiceModel
     * @param serialLogonInfo
     */
    public void revertToBOMOrder(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
                                 BillOfMaterialOrder billOfMaterialOrder,
                                  SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, BillOfMaterialException {

        resetToBOMOrder(billOfMaterialOrder, billOfMaterialTemplateServiceModel, serialLogonInfo);
    }

    /**
     * Core Logic to Initialize BOM Order: set status to 'Initial'.
     *
     * @param billOfMaterialTemplateServiceModel
     * @param serialLogonInfo
     */
    public void revertToRecentBOMOrder(BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
                                 SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException, BillOfMaterialException {
        String templateUUID = billOfMaterialTemplateServiceModel.getBillOfMaterialTemplate().getUuid();
        BillOfMaterialOrder billOfMaterialOrder = billOfMaterialOrderManager.getRecentBOMOrder(templateUUID,
                serialLogonInfo.getClient());
        resetToBOMOrder(billOfMaterialOrder, billOfMaterialTemplateServiceModel, serialLogonInfo);
    }

    private void resetToBOMOrder(BillOfMaterialOrder billOfMaterialOrder,
                                 BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel,
                                 SerialLogonInfo serialLogonInfo) throws ServiceEntityConfigureException,
            ServiceModuleProxyException {
        BillOfMaterialOrderServiceModel billOfMaterialOrderServiceModel =
                (BillOfMaterialOrderServiceModel) billOfMaterialOrderManager.loadServiceModule(BillOfMaterialOrderServiceModel.class,
                        billOfMaterialOrder);
        billOfMaterialTemplateServiceModel =
                billOfMaterialTemplateCrossProxy.cloneToBOMTemplateServiceModel(billOfMaterialOrderServiceModel,
                        billOfMaterialTemplateServiceModel);
        // Update refreshed template Service Model into DB
        this.updateServiceModuleWithDelete(BillOfMaterialTemplateServiceModel.class,
                billOfMaterialTemplateServiceModel, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
    }

    public void increseVersionNumber(BillOfMaterialTemplate billOfMaterialTemplate, BillOfMaterialOrder activeBOMOrder){
        int versionNumber = activeBOMOrder.getVersionNumber();
        int step = 1;
        versionNumber = versionNumber + step;
        billOfMaterialTemplate.setVersionNumber(versionNumber);
        billOfMaterialTemplate.setPatchNumber(activeBOMOrder.getPatchNumber());
    }

    /**
     * Generate the productive BOM model by calculating all the embedded BOM
     * model and generate the compound BOM for direct production
     *
     * @param billOfMaterialTemplate
     * @return
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     * @throws MaterialException
     */
    @Deprecated
    public List<ServiceEntityNode> genProductiveBOMModelCore(
            BillOfMaterialTemplate billOfMaterialTemplate)
            throws ServiceEntityConfigureException, BillOfMaterialException,
            MaterialException {
        /**
         * [Step1] get the root BOM model and BOM Item list
         */

        if (billOfMaterialTemplate == null) {
            throw new BillOfMaterialException(
                    BillOfMaterialException.PARA_NO_BOMOrder);
        }
        List<ServiceEntityNode> bomItemList = getEntityNodeListByKey(
                billOfMaterialTemplate.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                BillOfMaterialTemplateItem.NODENAME, billOfMaterialTemplate.getClient(),
                null);
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        if (bomItemList != null && bomItemList.size() > 0) {
            for (ServiceEntityNode seNode : bomItemList) {
                ProductiveBOMItem productiveBOMItem =
                        generateInitProductiveBOMItem((BillOfMaterialTemplateItem) seNode);
                resultList.add(productiveBOMItem);
            }
        }
        /**
         * [Step2] Navigate the first BOM Layer
         */
        for (ServiceEntityNode seNode : bomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            if (billOfMaterialTemplateItem.getRefSubBOMUUID() != null) {
                BillOfMaterialTemplate subBOMOrder = (BillOfMaterialTemplate) getEntityNodeByKey(
                        billOfMaterialTemplateItem.getRefSubBOMUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        BillOfMaterialTemplate.NODENAME,
                        billOfMaterialTemplate.getClient(), null);
                if (subBOMOrder != null) {
                    StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
                    storageCoreUnit1.setAmount(billOfMaterialTemplateItem.getAmount());
                    storageCoreUnit1.setRefMaterialSKUUUID(billOfMaterialTemplateItem
                            .getRefMaterialSKUUUID());
                    storageCoreUnit1.setRefUnitUUID(billOfMaterialTemplateItem
                            .getRefUnitUUID());
                    StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
                    storageCoreUnit2.setAmount(subBOMOrder.getAmount());
                    storageCoreUnit2.setRefMaterialSKUUUID(subBOMOrder
                            .getRefMaterialSKUUUID());
                    storageCoreUnit2.setRefUnitUUID(subBOMOrder
                            .getRefUnitUUID());
                    double ratio = materialStockKeepUnitManager
                            .getStorageUnitRatio(storageCoreUnit1,
                                    storageCoreUnit2, seNode.getClient());
                    List<ServiceEntityNode> subBOMList = getSubBOMOrderListForProduction(
                            billOfMaterialTemplateItem, subBOMOrder, ratio);
                    if (subBOMList != null && subBOMList.size() > 0) {
                        resultList.addAll(subBOMList);
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public ServiceEntityNode getEntityNodeByKey(Object keyValue,
                                                String keyName, String nodeName, String client,
                                                List<ServiceEntityNode> rawSEList)
            throws ServiceEntityConfigureException {
        if (IServiceEntityNodeFieldConstant.UUID.equals(keyName)
                && BillOfMaterialTemplate.NODENAME.equals(nodeName)) {
            if (this.billOfMaterialTemplateMap.containsKey(keyValue)) {
                return this.billOfMaterialTemplateMap.get(keyValue);
            }
            // In case not find, then find from persistence
            BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) super
                    .getEntityNodeByKey(keyValue, keyName, nodeName, client,
                            rawSEList);
            if (billOfMaterialTemplate != null) {
                this.billOfMaterialTemplateMap.put(keyName, billOfMaterialTemplate);
            }
            return billOfMaterialTemplate;
        } else {
            return super.getEntityNodeByKey(keyValue, keyName, nodeName,
                    client, rawSEList);
        }
    }

    @Override
    public void updateBuffer(ServiceEntityNode serviceEntityNode) {
        if (serviceEntityNode != null
                && LogonUser.SENAME.equals(serviceEntityNode
                .getServiceEntityName())) {
            BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) serviceEntityNode;
            this.billOfMaterialTemplateMap.put(billOfMaterialTemplate.getUuid(),
                    billOfMaterialTemplate);
        }
    }

    /**
     * model and generate the compound BOM for direct production
     *
     * @param billOfMaterialTemplate
     * @return
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     * @throws MaterialException
     */
    public List<ServiceEntityNode> genProductiveBOMModel(
            BillOfMaterialTemplate billOfMaterialTemplate)
            throws ServiceEntityConfigureException, BillOfMaterialException,
            MaterialException {
        /**
         * [Step1] get the root BOM model and BOM Item list
         */

        if (billOfMaterialTemplate == null) {
            throw new BillOfMaterialException(
                    BillOfMaterialException.PARA_NO_BOMOrder);
        }
        List<ServiceEntityNode> allBomItemList = getEntityNodeListByKey(
                billOfMaterialTemplate.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                BillOfMaterialTemplateItem.NODENAME, billOfMaterialTemplate.getClient(),
                null);
        List<ServiceEntityNode> firstLayerBomItemList = filterSubBOMItemList(
                billOfMaterialTemplate.getUuid(), allBomItemList);
        List<ServiceEntityNode> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(firstLayerBomItemList)) {
            for (ServiceEntityNode seNode : firstLayerBomItemList) {
                ProductiveBOMItem productiveBOMItem =
                        generateInitProductiveBOMItem((BillOfMaterialTemplateItem) seNode);
                resultList.add(productiveBOMItem);
            }
        }
        /**
         * [Step2] Navigate the first BOM Layer
         */
        for (ServiceEntityNode seNode : firstLayerBomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            List<ServiceEntityNode> subBOMList = generateSubProductiveBomModel(
                    billOfMaterialTemplate, billOfMaterialTemplateItem, allBomItemList);
            if (subBOMList != null && subBOMList.size() > 0) {
                resultList.addAll(subBOMList);
            }
        }
        return resultList;
    }


    /**
     * Recursively to generate sub productive BOM model
     *
     * @param billOfMaterialTemplateItem
     * @param allBomItemList
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    private List<ServiceEntityNode> generateSubProductiveBomModel(
            BillOfMaterialTemplate billOfMaterialTemplate,
            BillOfMaterialTemplateItem billOfMaterialTemplateItem,
            List<ServiceEntityNode> allBomItemList) throws MaterialException,
            ServiceEntityConfigureException {
        List<ServiceEntityNode> resultList = new ArrayList<>();
        List<ServiceEntityNode> directChildBomItemList = filterSubBOMItemList(
                billOfMaterialTemplateItem.getUuid(), allBomItemList);
        /*
         * [Step1] In case this node has sub nodes
         */
        if (!ServiceCollectionsHelper.checkNullList(directChildBomItemList)) {
            for (ServiceEntityNode seNode : directChildBomItemList) {
                BillOfMaterialTemplateItem childBomItem = (BillOfMaterialTemplateItem) seNode;

                ProductiveBOMItem productiveBOMItem = generateInitProductiveBOMItem(childBomItem);
                // Set Route Order UUID
                productiveBOMItem.setRefRouteOrderUUID(billOfMaterialTemplate
                        .getRefRouteOrderUUID());
                productiveBOMItem.setLeadTimeCalMode(billOfMaterialTemplate
                        .getLeadTimeCalMode());
                resultList.add(productiveBOMItem);

                // Recursive call this method to get sub productive BOM item
                List<ServiceEntityNode> subProductiveBomList = generateSubProductiveBomModel(
                        billOfMaterialTemplate, childBomItem, allBomItemList);
                if (!ServiceCollectionsHelper
                        .checkNullList(subProductiveBomList)) {
                    resultList.addAll(subProductiveBomList);
                }
            }
        }
        /*
         * [Step2] In case this bom item has reference BOM order
         */
        if (billOfMaterialTemplateItem.getRefSubBOMUUID() != null) {
            BillOfMaterialTemplate subBOMOrder = (BillOfMaterialTemplate) getEntityNodeByKey(
                    billOfMaterialTemplateItem.getRefSubBOMUUID(),
                    IServiceEntityNodeFieldConstant.UUID,
                    BillOfMaterialTemplate.NODENAME,
                    billOfMaterialTemplate.getClient(), null);
            if (subBOMOrder != null) {
                StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
                storageCoreUnit1.setAmount(billOfMaterialTemplateItem.getAmount());
                storageCoreUnit1.setRefMaterialSKUUUID(billOfMaterialTemplateItem
                        .getRefMaterialSKUUUID());
                storageCoreUnit1.setRefUnitUUID(billOfMaterialTemplateItem
                        .getRefUnitUUID());
                StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
                storageCoreUnit2.setAmount(subBOMOrder.getAmount());
                storageCoreUnit2.setRefMaterialSKUUUID(subBOMOrder
                        .getRefMaterialSKUUUID());
                storageCoreUnit2.setRefUnitUUID(subBOMOrder.getRefUnitUUID());
                double ratio = materialStockKeepUnitManager
                        .getStorageUnitRatio(storageCoreUnit1,
                                storageCoreUnit2,
                                billOfMaterialTemplate.getClient());
                List<ServiceEntityNode> subBOMList = getSubBOMOrderListForProduction(
                        billOfMaterialTemplateItem, subBOMOrder, ratio);
                if (subBOMList != null && subBOMList.size() > 0) {
                    resultList.addAll(subBOMList);
                }
            }
        }
        return resultList;
    }

    public ProductiveBOMOrder genInitProductiveBomOrder(
            BillOfMaterialTemplate billOfMaterialTemplate) {
        ProductiveBOMOrder productiveBOMOrder = new ProductiveBOMOrder();
        productiveBOMOrder.setUuid(billOfMaterialTemplate.getUuid());
        productiveBOMOrder.setId(billOfMaterialTemplate.getId());
        productiveBOMOrder.setName(billOfMaterialTemplate.getName());
        productiveBOMOrder.setNote(billOfMaterialTemplate.getNote());
        productiveBOMOrder.setRefMaterialSKUUUID(billOfMaterialTemplate
                .getRefMaterialSKUUUID());
        productiveBOMOrder.setRefUnitUUID(billOfMaterialTemplate.getRefUnitUUID());
        productiveBOMOrder.setAmount(billOfMaterialTemplate.getAmount());
        productiveBOMOrder.setRootNodeUUID(billOfMaterialTemplate
                .getRootNodeUUID());
        productiveBOMOrder.setParentNodeUUID(billOfMaterialTemplate
                .getParentNodeUUID());
        productiveBOMOrder.setItemCategory(billOfMaterialTemplate
                .getItemCategory());
        productiveBOMOrder.setClient(billOfMaterialTemplate.getClient());
        productiveBOMOrder.setRefRouteOrderUUID(billOfMaterialTemplate
                .getRefRouteOrderUUID());
        productiveBOMOrder.setLeadTimeCalMode(billOfMaterialTemplate
                .getLeadTimeCalMode());
        return productiveBOMOrder;
    }

    /**
     * Generate the initial productive BOM Item from standard BOM Item
     *
     * @param billOfMaterialTemplateItem
     * @return
     */
    public ProductiveBOMItem generateInitProductiveBOMItem(
            BillOfMaterialTemplateItem billOfMaterialTemplateItem) {
        ProductiveBOMItem productiveBOMItem = new ProductiveBOMItem();
        productiveBOMItem.setUuid(billOfMaterialTemplateItem.getUuid());
        productiveBOMItem.setId(billOfMaterialTemplateItem.getId());
        productiveBOMItem.setName(billOfMaterialTemplateItem.getName());
        productiveBOMItem.setNote(billOfMaterialTemplateItem.getNote());
        productiveBOMItem.setRefMaterialSKUUUID(billOfMaterialTemplateItem
                .getRefMaterialSKUUUID());
        productiveBOMItem.setRefParentItemUUID(billOfMaterialTemplateItem
                .getRefParentItemUUID());
        productiveBOMItem.setRefUnitUUID(billOfMaterialTemplateItem.getRefUnitUUID());
        productiveBOMItem.setAmount(billOfMaterialTemplateItem.getAmount());
        productiveBOMItem.setRefSubBOMUUID(billOfMaterialTemplateItem
                .getRefSubBOMUUID());
        productiveBOMItem.setLayer(billOfMaterialTemplateItem.getLayer());
        productiveBOMItem.setRootNodeUUID(billOfMaterialTemplateItem.getRootNodeUUID());
        productiveBOMItem.setParentNodeUUID(billOfMaterialTemplateItem
                .getParentNodeUUID());
        productiveBOMItem.setItemCategory(billOfMaterialTemplateItem.getItemCategory());
        productiveBOMItem.setTheoLossRate(billOfMaterialTemplateItem.getTheoLossRate());
        productiveBOMItem.setLeadTimeOffset(billOfMaterialTemplateItem
                .getLeadTimeOffset());
        productiveBOMItem.setClient(billOfMaterialTemplateItem.getClient());
        return productiveBOMItem;
    }

    /**
     * Logic to binding the sub BOM model to parent BillOfMaterial Item
     *
     * @param parentBOMItem
     * @param billOfMaterialTemplate
     * @param ratio
     * @return
     * @throws ServiceEntityConfigureException
     * @throws MaterialException
     */
    public List<ServiceEntityNode> getSubBOMOrderListForProduction(
            BillOfMaterialTemplateItem parentBOMItem,
            BillOfMaterialTemplate billOfMaterialTemplate, double ratio)
            throws ServiceEntityConfigureException, MaterialException {
        /*
         * [Step1] Get all BOM Item list
         */
        List<ServiceEntityNode> bomItemList = getEntityNodeListByKey(
                billOfMaterialTemplate.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                BillOfMaterialTemplateItem.NODENAME, billOfMaterialTemplate.getClient(),
                null);
        if (bomItemList == null || bomItemList.size() == 0) {
            return null;
        }
        /*
         * [Step2] Reset parentNode UUID for first layer, point to parentBOMItem
         */
        List<ServiceEntityNode> firstBomLayerList = filterBOMItemListByLayer(1,
                bomItemList);
        if (firstBomLayerList == null || firstBomLayerList.size() == 0) {
            return null;
        }
        /*
         * [Step4] Reset the layer and ratio for all the list, and point to
         * parentBOMItem's root node
         */
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : bomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            ProductiveBOMItem productiveBOMItem = generateInitProductiveBOMItem(billOfMaterialTemplateItem);
            productiveBOMItem.setAmount(billOfMaterialTemplateItem.getAmount() * ratio);
            productiveBOMItem.setRootNodeUUID(parentBOMItem.getRootNodeUUID());
            productiveBOMItem.setLayer(billOfMaterialTemplateItem.getLayer()
                    + parentBOMItem.getLayer());
            if (billOfMaterialTemplate.getUuid().equals(
                    billOfMaterialTemplateItem.getParentNodeUUID())) {
                // In case this item is in direct 1st layer, pointing to
                // parentBOMItem
                productiveBOMItem.setParentNodeUUID(parentBOMItem.getUuid());
                productiveBOMItem.setRefParentItemUUID(parentBOMItem.getUuid());
            }
            // Set Route Order UUID
            productiveBOMItem.setRefRouteOrderUUID(billOfMaterialTemplate
                    .getRefRouteOrderUUID());
            productiveBOMItem.setLeadTimeCalMode(billOfMaterialTemplate
                    .getLeadTimeCalMode());
            resultList.add(productiveBOMItem);
            if (billOfMaterialTemplateItem.getRefSubBOMUUID() != null) {
                BillOfMaterialTemplate subBOMOrder = (BillOfMaterialTemplate) getEntityNodeByKey(
                        billOfMaterialTemplateItem.getRefSubBOMUUID(),
                        IServiceEntityNodeFieldConstant.UUID,
                        BillOfMaterialTemplate.NODENAME,
                        billOfMaterialTemplateItem.getClient(), null);
                if (subBOMOrder != null) {
                    StorageCoreUnit storageCoreUnit1 = new StorageCoreUnit();
                    storageCoreUnit1.setAmount(productiveBOMItem.getAmount());
                    storageCoreUnit1.setRefMaterialSKUUUID(productiveBOMItem
                            .getRefMaterialSKUUUID());
                    storageCoreUnit1.setRefUnitUUID(productiveBOMItem
                            .getRefUnitUUID());

                    StorageCoreUnit storageCoreUnit2 = new StorageCoreUnit();
                    storageCoreUnit2.setAmount(subBOMOrder.getAmount());
                    storageCoreUnit2.setRefMaterialSKUUUID(subBOMOrder
                            .getRefMaterialSKUUUID());
                    storageCoreUnit2.setRefUnitUUID(subBOMOrder
                            .getRefUnitUUID());
                    double subRatio = materialStockKeepUnitManager
                            .getStorageUnitRatio(storageCoreUnit1,
                                    storageCoreUnit2,
                                    billOfMaterialTemplateItem.getClient());
                    List<ServiceEntityNode> subBOMList = getSubBOMOrderListForProduction(
                            billOfMaterialTemplateItem, subBOMOrder, subRatio);
                    if (subBOMList != null && subBOMList.size() > 0) {
                        resultList.addAll(subBOMList);
                    }
                }
            }
        }
        return resultList;
    }


    /**
     * Filter the online BOM list by next layer
     *
     * @param parentItemUUID
     * @param rawBomItemList
     * @return
     */
    public List<ServiceEntityNode> filterSubBOMItemList(String parentItemUUID,
                                                        List<ServiceEntityNode> rawBomItemList) {
        if (rawBomItemList == null || rawBomItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawBomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            if (billOfMaterialTemplateItem.getRefParentItemUUID()
                    .equals(parentItemUUID)) {
                resultList.add(billOfMaterialTemplateItem);
            }
        }
        return resultList;
    }



    /**
     * Filter the online BOM list by layer
     *
     * @param layer
     * @param rawBomItemList
     * @return
     */
    public List<ServiceEntityNode> filterBOMItemListByLayer(int layer,
                                                            List<ServiceEntityNode> rawBomItemList) {
        if (rawBomItemList == null || rawBomItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : rawBomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            if (billOfMaterialTemplateItem.getLayer() == layer) {
                resultList.add(billOfMaterialTemplateItem);
            }
        }
        return resultList;
    }

    /**
     * [Internal method] Get the list of buttom BOM item list, these item is raw
     * material which doesn't has sub BOM sub material or has other BOM model
     * related
     *
     * @param rawBomItemList : Raw, all of the BOM Item list, [pay attention]: these BOM
     *                       list should already finished BOM list merge job
     * @return
     */
    public List<ServiceEntityNode> getFooterBomItemList(
            List<ServiceEntityNode> rawBomItemList) {
        if (rawBomItemList == null || rawBomItemList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : rawBomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            List<ServiceEntityNode> directSubBomItemList = filterSubBOMItemList(
                    billOfMaterialTemplateItem.getUuid(), rawBomItemList);
            if (directSubBomItemList == null
                    || directSubBomItemList.size() == 0) {
                resultList.add(billOfMaterialTemplateItem);
            }
        }
        return resultList;
    }

    /**
     * Filter the online BOM Item by UUID
     *
     * @param uuid
     * @param rawBomItemList
     * @return
     */
    public BillOfMaterialTemplateItem filterBOMItemByUUID(String uuid,
                                                          List<ServiceEntityNode> rawBomItemList) {
        if (ServiceCollectionsHelper.checkNullList(rawBomItemList)) {
            return null;
        }
        for (ServiceEntityNode seNode : rawBomItemList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            if (uuid.equals(billOfMaterialTemplateItem.getUuid())) {
                return billOfMaterialTemplateItem;
            }
        }
        return null;
    }

    /**
     * Filter out all the upstream BOM item list in one path until the first
     * layer
     *
     * @param billOfMaterialTemplateItem
     * @param rawBomItemList
     * @return
     */
    public List<ServiceEntityNode> filterUpstreamBOMItemList(
            BillOfMaterialTemplateItem billOfMaterialTemplateItem,
            List<ServiceEntityNode> rawBomItemList) {
        if (rawBomItemList == null || rawBomItemList.size() == 0) {
            return null;
        }
        if (billOfMaterialTemplateItem.getLayer() <= 1) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        BillOfMaterialTemplateItem parentItem = filterBOMItemByUUID(
                billOfMaterialTemplateItem.getRefParentItemUUID(), rawBomItemList);
        if (parentItem != null) {
            resultList.add(parentItem);
            List<ServiceEntityNode> parentItemList = filterUpstreamBOMItemList(
                    parentItem, rawBomItemList);
            if (parentItemList != null && parentItemList.size() > 0) {
                resultList.addAll(parentItemList);
            }
        }
        return resultList;
    }

    public List<ServiceEntityNode> getFirstLayerSubItemList(String baseUUID,
                                                            String client) throws ServiceEntityConfigureException {
        List<ServiceBasicKeyStructure> keyList = new ArrayList<ServiceBasicKeyStructure>();
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
        key1.setKeyValue(baseUUID);
        keyList.add(key1);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure();
        key2.setKeyName("layer");
        key2.setKeyValue(1);
        keyList.add(key2);
        return getEntityNodeListByKeyList(
                keyList, BillOfMaterialTemplateItem.NODENAME, client, null);
    }

    public List<ServiceEntityNode> filterFirstLayerSubItemListOnline(
            List<ServiceEntityNode> rawList)
            throws ServiceEntityConfigureException {
        if (rawList == null || rawList.size() == 0) {
            return null;
        }
        List<ServiceEntityNode> resultList = new ArrayList<ServiceEntityNode>();
        for (ServiceEntityNode seNode : rawList) {
            BillOfMaterialTemplateItem billOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            if (billOfMaterialTemplateItem != null
                    && billOfMaterialTemplateItem.getLayer() == 1) {
                resultList.add(billOfMaterialTemplateItem);
            }
        }
        return resultList;
    }

    protected void throwMaterialNestedUnion(String skuUUID, String client)
            throws ServiceEntityConfigureException, BillOfMaterialException {
        MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
                .getEntityNodeByKey(skuUUID,
                        IServiceEntityNodeFieldConstant.UUID,
                        MaterialStockKeepUnit.NODENAME, client, null);
        if (materialStockKeepUnit != null) {
            throw new BillOfMaterialException(
                    BillOfMaterialException.PARA_NESTEDITEM_UPLAYER,
                    materialStockKeepUnit.getId() + "-"
                            + materialStockKeepUnit.getName());
        } else {
            throw new BillOfMaterialException(
                    BillOfMaterialException.PARA_NO_MATERIAL, skuUUID);
        }
    }

    /**
     * Logic to check BOM Material Nested problem from current Item To root
     *
     * @param billOfMaterialTemplateItem
     * @param rawBOMItemList
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     */
    public void checkItemMaterialNestedUpstream(
            BillOfMaterialTemplateItem billOfMaterialTemplateItem,
            List<ServiceEntityNode> rawBOMItemList)
            throws ServiceEntityConfigureException, BillOfMaterialException {
        if (billOfMaterialTemplateItem.getLayer() == 0) {
            return;
        }
        BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) getEntityNodeByKey(
                billOfMaterialTemplateItem.getRootNodeUUID(),
                IServiceEntityNodeFieldConstant.UUID,
                BillOfMaterialTemplate.NODENAME, billOfMaterialTemplateItem.getClient(),
                null);
        // Compare with root material and item material
        if (billOfMaterialTemplateItem.getRefMaterialSKUUUID() == null) {
            throw new BillOfMaterialException(
                    BillOfMaterialException.PARA_NO_MATERIAL,
                    billOfMaterialTemplateItem.getId());
        }
        if (billOfMaterialTemplate.getRefMaterialSKUUUID() == null) {
            throw new BillOfMaterialException(
                    BillOfMaterialException.PARA_NO_MATERIAL,
                    billOfMaterialTemplate.getId());
        }
        if (billOfMaterialTemplate.getRefMaterialSKUUUID().equals(
                billOfMaterialTemplate.getRefMaterialSKUUUID())) {
            throwMaterialNestedUnion(
                    billOfMaterialTemplate.getRefMaterialSKUUUID(),
                    billOfMaterialTemplateItem.getClient());
        }
        if (billOfMaterialTemplateItem.getLayer() > 1) {
            List<ServiceEntityNode> upStreamList = filterUpstreamBOMItemList(
                    billOfMaterialTemplateItem, rawBOMItemList);
            if (upStreamList != null && upStreamList.size() > 0) {
                for (ServiceEntityNode seNode : upStreamList) {
                    BillOfMaterialTemplateItem upItem = (BillOfMaterialTemplateItem) seNode;
                    if (upItem.getRefMaterialSKUUUID() == null) {
                        throw new BillOfMaterialException(
                                BillOfMaterialException.PARA_NO_MATERIAL,
                                upItem.getId());
                    }
                    if (billOfMaterialTemplate.getRefMaterialSKUUUID().equals(
                            upItem.getRefMaterialSKUUUID())) {
                        throwMaterialNestedUnion(
                                upItem.getRefMaterialSKUUUID(),
                                upItem.getClient());
                    }
                }
            }
        }
    }

    /**
     * Logic to check BOM Material Nested problem from current Item To root by
     * checking all the sub material from this SUB BOM model
     *
     * @param billOfMaterialTemplateItem
     * @param rawBOMItemList
     * @throws ServiceEntityConfigureException
     * @throws BillOfMaterialException
     */
    public void checkItemSubBomNestedUpstream(
            BillOfMaterialTemplateItem billOfMaterialTemplateItem,
            List<ServiceEntityNode> rawBOMItemList)
            throws ServiceEntityConfigureException, BillOfMaterialException {
        if (billOfMaterialTemplateItem.getLayer() == 0) {
            return;
        }
        BillOfMaterialTemplate subBOMOrder = (BillOfMaterialTemplate) getEntityNodeByKey(
                billOfMaterialTemplateItem.getRefSubBOMUUID(),
                IServiceEntityNodeFieldConstant.UUID,
                BillOfMaterialTemplate.NODENAME, billOfMaterialTemplateItem.getClient(),
                null);
        if (subBOMOrder == null) {
            throw new BillOfMaterialException(
                    BillOfMaterialException.TYPE_SYSTEM_WRONG);
        }
        List<ServiceEntityNode> allSubBOMItemList = getEntityNodeListByKey(
                subBOMOrder.getUuid(),
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                BillOfMaterialTemplateItem.NODENAME, billOfMaterialTemplateItem.getClient(),
                null);
        if (allSubBOMItemList == null || allSubBOMItemList.size() == 0) {
            return;
        }
        BillOfMaterialTemplateItem dummyBillOfMaterialTemplateItem =
                (BillOfMaterialTemplateItem) billOfMaterialTemplateItem
                .clone();
        for (ServiceEntityNode seNode : allSubBOMItemList) {
            BillOfMaterialTemplateItem subBillOfMaterialTemplateItem = (BillOfMaterialTemplateItem) seNode;
            dummyBillOfMaterialTemplateItem.setRefMaterialSKUUUID(subBillOfMaterialTemplateItem
                    .getRefMaterialSKUUUID());
            checkItemMaterialNestedUpstream(dummyBillOfMaterialTemplateItem,
                    rawBOMItemList);
        }
    }

    public void convBillOfMaterialTemplateToUI(
            BillOfMaterialTemplate billOfMaterialTemplate,
            BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        convBillOfMaterialTemplateToUI(billOfMaterialTemplate, billOfMaterialTemplateUIModel, null);
    }

    public void convBillOfMaterialTemplateToUI(
            BillOfMaterialTemplate billOfMaterialTemplate,
            BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException,
            ServiceEntityConfigureException {
        if (billOfMaterialTemplate != null) {
            docFlowProxy.convDocumentToUI(billOfMaterialTemplate, billOfMaterialTemplateUIModel, logonInfo);
            billOfMaterialTemplateUIModel.setNote(billOfMaterialTemplate.getNote());
            billOfMaterialTemplateUIModel.setItemCategory(billOfMaterialTemplate
                    .getItemCategory());
            billOfMaterialTemplateUIModel.setLeadTimeCalMode(billOfMaterialTemplate
                    .getLeadTimeCalMode());
            if (logonInfo != null) {
                Map<Integer, String> statusMap = this.initStatusMap(logonInfo.getLanguageCode());
                billOfMaterialTemplateUIModel.setStatusValue(statusMap
                        .get(billOfMaterialTemplate.getStatus()));
            }
            billOfMaterialTemplateUIModel.setVersionNumber(billOfMaterialTemplate.getVersionNumber());
            billOfMaterialTemplateUIModel.setPatchNumber(billOfMaterialTemplate.getPatchNumber());
            billOfMaterialTemplateUIModel.setGenVersion(BillOfMaterialOrderManager.formatVersion(billOfMaterialTemplate.getVersionNumber(),
                    billOfMaterialTemplate.getPatchNumber()));
            billOfMaterialTemplateUIModel.setStatus(billOfMaterialTemplate
                    .getStatus());
            billOfMaterialTemplateUIModel
                    .setRefMaterialSKUUUID(billOfMaterialTemplate
                            .getRefMaterialSKUUUID());
            billOfMaterialTemplateUIModel.setAmount(ServiceEntityDoubleHelper
                    .trancateDoubleScale4(billOfMaterialTemplate.getAmount()));
            billOfMaterialTemplateUIModel.setRefUnitUUID(billOfMaterialTemplate
                    .getRefUnitUUID());
            billOfMaterialTemplateUIModel.setRefWocUUID(billOfMaterialTemplate
                    .getRefWocUUID());
            try {
                String refUnitName = materialStockKeepUnitManager
                        .getRefUnitName(
                                billOfMaterialTemplate.getRefMaterialSKUUUID(),
                                billOfMaterialTemplate.getRefUnitUUID(),
                                billOfMaterialTemplate.getClient());
                billOfMaterialTemplateUIModel.setRefUnitName(refUnitName);
            } catch (MaterialException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
            } catch (ServiceEntityConfigureException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
            }
            try {
                String amountLabel = materialStockKeepUnitManager
                        .getAmountLabel(
                                billOfMaterialTemplate.getRefMaterialSKUUUID(),
                                billOfMaterialTemplate.getRefUnitUUID(),
                                billOfMaterialTemplate.getAmount(),
                                billOfMaterialTemplate.getClient());
                billOfMaterialTemplateUIModel.setAmountLabel(amountLabel);
            } catch (MaterialException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"));
            }
        }
    }

    public void convUIToBillOfMaterialTemplate(
            BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel,
            BillOfMaterialTemplate rawEntity) {
        docFlowProxy.convUIToDocument(billOfMaterialTemplateUIModel, rawEntity);
        rawEntity.setNote(billOfMaterialTemplateUIModel.getNote());
        rawEntity.setRefMaterialSKUUUID(billOfMaterialTemplateUIModel
                .getRefMaterialSKUUUID());
        rawEntity.setId(billOfMaterialTemplateUIModel.getId());
        rawEntity.setNote(billOfMaterialTemplateUIModel.getNote());
        rawEntity.setAmount(billOfMaterialTemplateUIModel.getAmount());
        rawEntity.setRefUnitUUID(billOfMaterialTemplateUIModel.getRefUnitUUID());
        rawEntity.setLeadTimeCalMode(billOfMaterialTemplateUIModel
                .getLeadTimeCalMode());
        rawEntity.setRefRouteOrderUUID(billOfMaterialTemplateUIModel
                .getRefRouteOrderUUID());
        rawEntity.setRefWocUUID(billOfMaterialTemplateUIModel.getRefWocUUID());
    }

    public void convMaterialStockKeepUnitToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel) {
        convMaterialStockKeepUnitToUI(materialStockKeepUnit, billOfMaterialTemplateUIModel, null);
    }

    public void convMaterialStockKeepUnitToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel, LogonInfo logonInfo) {
        if (materialStockKeepUnit != null) {
            billOfMaterialTemplateUIModel
                    .setRefMaterialSKUId(materialStockKeepUnit.getId());
            billOfMaterialTemplateUIModel
                    .setRefMaterialSKUName(materialStockKeepUnit.getName());
            billOfMaterialTemplateUIModel.setSupplyType(materialStockKeepUnit
                    .getSupplyType());
            billOfMaterialTemplateUIModel
                    .setRefPackageStandard(materialStockKeepUnit
                            .getPackageStandard());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager
                            .initSupplyTypeMap(logonInfo.getLanguageCode());
                    billOfMaterialTemplateUIModel.setSupplyTypeValue(supplyTypeMap
                            .get(materialStockKeepUnit.getSupplyType()));
                } catch (ServiceEntityInstallationException e) {
                    // log error and continue
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                }
            }

        }
    }

    public void convProcessRouteOrderToUI(ProcessRouteOrder processRouteOrder,
                                          BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel) {
        if (processRouteOrder != null) {
            billOfMaterialTemplateUIModel.setRefRouteOrderUUID(processRouteOrder
                    .getUuid());
            billOfMaterialTemplateUIModel.setRefRouteOrderId(processRouteOrder
                    .getId());
            billOfMaterialTemplateUIModel.setRefRouteOrderName(processRouteOrder
                    .getId());
        }
    }

    public void convProdWorkCenterToUI(ProdWorkCenter prodWorkCenter,
                                       BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel) {
        if (prodWorkCenter != null) {
            billOfMaterialTemplateUIModel.setRefWocName(prodWorkCenter.getName());
            billOfMaterialTemplateUIModel.setRefWocId(prodWorkCenter.getId());
        }
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return billOfMaterialTemplateSearchProxy;
    }

    public boolean checkBlockExecutionByDocflow(int actionCode, String uuid, ServiceJSONRequest serviceJSONRequest,
                                                SerialLogonInfo serialLogonInfo) {
        if (actionCode == BillOfMaterialTemplateActionNode.DOC_ACTION_APPROVE) {
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if (actionCode == BillOfMaterialTemplateActionNode.DOC_ACTION_REJECT_APPROVE) {
            return serviceFlowRuntimeEngine.defCheckBlockAndDoneTask(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid, serviceJSONRequest, serialLogonInfo,
                    actionCode);
        }
        if (actionCode == BillOfMaterialTemplateActionNode.DOC_ACTION_REVOKE_SUBMIT) {
            serviceFlowRuntimeEngine.clearInvolveProcessIns(
                    IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid);
            return true;
        }
        return true;
    }

    public void submitFlow(BillOfMaterialTemplateServiceUIModel billOfMaterialTemplateServiceUIModel,
                           SerialLogonInfo serialLogonInfo) throws ServiceFlowRuntimeException {
        String uuid = billOfMaterialTemplateServiceUIModel.getBillOfMaterialTemplateUIModel().getUuid();
        ServiceFlowRuntimeEngine.ServiceFlowInputPara serviceFlowInputPara =
                new ServiceFlowRuntimeEngine.ServiceFlowInputPara(billOfMaterialTemplateServiceUIModel,
                        IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER, uuid,
                        BillOfMaterialTemplateActionNode.DOC_ACTION_APPROVE, serialLogonInfo);
        serviceFlowRuntimeEngine.submitFlow(serviceFlowInputPara);
    }


    @Override
    public void exeFlowActionEnd(int documentType, String uuid, int actionCode, ServiceJSONRequest serviceJSONRequest
            , SerialLogonInfo serialLogonInfo) {
        try {
            BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) getEntityNodeByKey(uuid,
                    IServiceEntityNodeFieldConstant.UUID,
                    BillOfMaterialTemplate.NODENAME, serialLogonInfo.getClient(), null);
            BillOfMaterialTemplateServiceModel billOfMaterialTemplateServiceModel =
                    (BillOfMaterialTemplateServiceModel) loadServiceModule(BillOfMaterialTemplateServiceModel.class,
                    billOfMaterialTemplate, billOfMaterialTemplateServiceUIModelExtension);
            billOfMaterialTemplateServiceModel.setServiceJSONRequest(serviceJSONRequest);
            if (actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE) {
                this.activeService(billOfMaterialTemplateServiceModel, serialLogonInfo.getRefUserUUID(),
                        serialLogonInfo.getHomeOrganizationUUID());
            }
            if (actionCode == SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE) {
                this.rejectApproveService(billOfMaterialTemplateServiceModel, serialLogonInfo.getRefUserUUID(),
                        serialLogonInfo.getHomeOrganizationUUID());
            }
        } catch (ServiceEntityConfigureException | ServiceModuleProxyException e) {
            e.printStackTrace();
        }
    }
}
