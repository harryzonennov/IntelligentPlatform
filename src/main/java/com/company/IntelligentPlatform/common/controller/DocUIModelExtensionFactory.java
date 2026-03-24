package com.company.IntelligentPlatform.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class DocUIModelExtensionFactory {

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected WarehouseManager warehouseManager;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    public DocUIModelExtensionBuilder getBuilder(Class<? extends ServiceEntityNode> docModelClass,
                                                         Class<? extends SEUIComModel> docUiModelClass,
                                                         ServiceEntityManager serviceEntityManager) {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = new DocUIModelExtensionBuilder();
        docUIModelExtensionBuilder.docModelClass(docModelClass);
        docUIModelExtensionBuilder.docUiModelClass(docUiModelClass);
        docUIModelExtensionBuilder.serviceEntityManager(serviceEntityManager);
        docUIModelExtensionBuilder.setDocUIModelExtensionFactory(this);
        return docUIModelExtensionBuilder;
    }

    public DocFlowProxy getDocFlowProxy() {
        return docFlowProxy;
    }

    public void setDocFlowProxy(DocFlowProxy docFlowProxy) {
        this.docFlowProxy = docFlowProxy;
    }

    public DocAttachmentProxy getDocAttachmentProxy() {
        return docAttachmentProxy;
    }

    public void setDocAttachmentProxy(DocAttachmentProxy docAttachmentProxy) {
        this.docAttachmentProxy = docAttachmentProxy;
    }

    public DocInvolvePartyProxy getDocInvolvePartyProxy() {
        return docInvolvePartyProxy;
    }

    public void setDocInvolvePartyProxy(DocInvolvePartyProxy docInvolvePartyProxy) {
        this.docInvolvePartyProxy = docInvolvePartyProxy;
    }

    public DocActionNodeProxy getDocActionNodeProxy() {
        return docActionNodeProxy;
    }

    public void setDocActionNodeProxy(DocActionNodeProxy docActionNodeProxy) {
        this.docActionNodeProxy = docActionNodeProxy;
    }

    /**
     * Add Specific Target document Node Map configure list to docUIModelExtensionBuilder, with simplified input configuration: uiModelNodeMapConfigureBuilder
     * it is for the case, need to build connection from current item node to target doc item node, and to target doc root node
     * @param nodeInstId
     * @param documentType
     * @param docUIModelExtensionBuilder
     * @param uiModelNodeMapConfigureBuilder
     * @throws DocActionException
     */
    /**
     * Configures and adds node mappings for a target document to the UI model extension builder.
     *
     * <p>This method establishes connections between the current item node and the target document's
     * item node, as well as the target document's root node. It simplifies the configuration process
     * by accepting a streamlined {@code UIModelNodeMapConfigureBuilder} and deriving necessary
     * metadata from the target document type.
     *
     * @throws DocActionException if the document type is invalid or configuration generation fails
     * @throws AssertionError if the document content specifier cannot be resolved for the given type
     * @throws NullPointerException if any required parameter is null
     */
    public void addSpecDocNodeMapConfigureList(DocUIModelExtensionBuilder docUIModelExtensionBuilder, SpecDocConfigureUnion specDocConfigureUnion) throws DocActionException {
        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier = null;
        int documentType = specDocConfigureUnion.getDocumentType();
        if (documentType > 0) {
            documentContentSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
        }
        assert documentContentSpecifier != null;
        DocumentContentSpecifier.DocMetadata targetDocMetadata = documentContentSpecifier.getDocMetadata();
        Class<? extends SEUIComModel> baseUiModelClass = specDocConfigureUnion.getBaseUIModelClass();
        baseUiModelClass = baseUiModelClass == null ? docUIModelExtensionBuilder.getItemUiModelClass():baseUiModelClass;
        String baseNodeInstId = specDocConfigureUnion.getBaseNodeInstId();
        try {
            // Add target doc item node
            String itemNodeInstId = ServiceEntityStringHelper.checkNullString(specDocConfigureUnion.getItemNodeInstId()) ?
                    ServiceUIModelExtensionHelper.getDefNodeInstIdByModelClass(targetDocMetadata.getItemModelClass()): specDocConfigureUnion.getItemNodeInstId();
            UIModelNodeMapConfigureBuilder targetDocModelNodeMapConfigureBuilder = ServiceUIModelExtensionHelper.genUIConfBuilder(targetDocMetadata.getItemModelClass(), baseUiModelClass)
                    .serviceEntityManager(documentContentSpecifier.getDocumentManager()).logicManager(specDocConfigureUnion.getLogicManager())
                    .baseNodeInstId(baseNodeInstId).nodeInstId(itemNodeInstId);
            targetDocModelNodeMapConfigureBuilder.addConnectionCondition(specDocConfigureUnion.getSourceFieldName(), specDocConfigureUnion.getTargetFieldName());
            docUIModelExtensionBuilder.addMapConfigureBuilder(baseNodeInstId,
                    targetDocModelNodeMapConfigureBuilder);
            // Add target doc root node
            docUIModelExtensionBuilder.addMapConfigureBuilder(baseNodeInstId, ServiceUIModelExtensionHelper.genUIConfBuilder(targetDocMetadata.getDocModelClass(),
                            baseUiModelClass).logicManager(specDocConfigureUnion.getLogicManager())
                    .serviceEntityManager(documentContentSpecifier.getDocumentManager())
                    .toBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD).convToUIMethod(specDocConfigureUnion.getConvToUIMethod()).
                    baseNodeInstId(itemNodeInstId));
        } catch (SearchConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    public void addWarehouseMapConfigureList(DocUIModelExtensionBuilder docUIModelExtensionBuilder, WarehouseMapConfigureUnion warehouseMapConfigureUnion) throws DocActionException {
        String baseNodeInstId = warehouseMapConfigureUnion.getBaseNodeInstId();
        Class<? extends SEUIComModel> baseUiModelClass = warehouseMapConfigureUnion.getBaseUIModelClass();
        baseUiModelClass = baseUiModelClass == null ? docUIModelExtensionBuilder.getUiModelClass():baseUiModelClass;
        String mapFieldWarehouse = warehouseMapConfigureUnion.getMapFieldWarehouse();
        mapFieldWarehouse = ServiceEntityStringHelper.checkNullString(mapFieldWarehouse) ? "refWarehouseUUID": mapFieldWarehouse;
        String mapFieldWarehouseArea = warehouseMapConfigureUnion.getMapFieldWarehouseArea();
        mapFieldWarehouseArea = ServiceEntityStringHelper.checkNullString(mapFieldWarehouseArea) ? "refWarehouseAreaUUID": mapFieldWarehouseArea;
        String baseWarehouseNodeInstId = warehouseMapConfigureUnion.getBaseWarehouseNodeInstId();
        baseWarehouseNodeInstId = ServiceEntityStringHelper.checkNullString(baseWarehouseNodeInstId) ? baseNodeInstId: baseWarehouseNodeInstId;
        String baseAreaNodeInstId = warehouseMapConfigureUnion.getBaseAreaNodeInstId();
        baseAreaNodeInstId = ServiceEntityStringHelper.checkNullString(baseAreaNodeInstId) ? baseNodeInstId: baseAreaNodeInstId;
        docUIModelExtensionBuilder.addMapConfigureBuilder(baseNodeInstId, ServiceUIModelExtensionHelper.genUIConfBuilder(Warehouse.class, baseUiModelClass)
                .serviceEntityManager(warehouseManager).logicManager(warehouseMapConfigureUnion.getLogicManager())
                .addConnectionCondition(mapFieldWarehouse).convToUIMethod(warehouseMapConfigureUnion.getConvWarehouseToUIMethod()).baseNodeInstId(baseWarehouseNodeInstId));
        if (!ServiceEntityStringHelper.checkNullString(warehouseMapConfigureUnion.getConvWarehouseAreaToUIMethod())) {
            docUIModelExtensionBuilder.addMapConfigureBuilder(baseAreaNodeInstId, ServiceUIModelExtensionHelper.genUIConfBuilder(WarehouseArea.class, baseUiModelClass)
                    .serviceEntityManager(warehouseManager).logicManager(warehouseMapConfigureUnion.getLogicManager())
                    .addConnectionCondition(mapFieldWarehouseArea).convToUIMethod(warehouseMapConfigureUnion.getConvWarehouseAreaToUIMethod()).
                    baseNodeInstId(baseAreaNodeInstId));
        }
    }

    public static SpecDocConfigureUnion genSpecDocConfig(int documentType, String baseNodeInstId) {
        return new SpecDocConfigureUnion(documentType, baseNodeInstId);
    }

    public static SpecDocConfigureUnion genSpecDocConfig(String baseNodeInstId) {
        return new SpecDocConfigureUnion(0, baseNodeInstId);
    }

    public static class SpecDocConfigureUnion {

        protected int documentType;

        protected String baseNodeInstId;

        protected String convToUIMethod;

        protected Object logicManager;

        protected String itemNodeInstId;

        protected Class<? extends SEUIComModel> baseUIModelClass;

        protected UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback;

        protected UIModelNodeMapConfigure.IGetSENode docGetCallback;

        protected String sourceFieldName = IServiceEntityNodeFieldConstant.UUID;

        protected String targetFieldName = IServiceEntityNodeFieldConstant.UUID;

        public SpecDocConfigureUnion(int documentType, String baseNodeInstId) {
            this.documentType = documentType;
            this.baseNodeInstId = baseNodeInstId;
        }

        public SpecDocConfigureUnion sourceFieldName(String sourceFieldName) {
            this.sourceFieldName = sourceFieldName;
            return this;
        }

        public SpecDocConfigureUnion convToUIMethod(String convToUIMethod) {
            this.convToUIMethod = convToUIMethod;
            return this;
        }

        public SpecDocConfigureUnion logicManager(Object logicManager) {
            this.logicManager = logicManager;
            return this;
        }

        public SpecDocConfigureUnion itemNodeInstId(String itemNodeInstId) {
            this.itemNodeInstId = itemNodeInstId;
            return this;
        }

        public SpecDocConfigureUnion docMatItemGetCallback(UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback) {
            this.docMatItemGetCallback = docMatItemGetCallback;
            return this;
        }

        public SpecDocConfigureUnion docGetCallback(UIModelNodeMapConfigure.IGetSENode docGetCallback) {
            this.docGetCallback = docGetCallback;
            return this;
        }

        public SpecDocConfigureUnion baseUIModelClass(Class<? extends SEUIComModel> baseUIModelClass) {
            this.baseUIModelClass = baseUIModelClass;
            return this;
        }

        public Class<? extends SEUIComModel> getBaseUIModelClass() {
            return baseUIModelClass;
        }

        public void setBaseUIModelClass(Class<? extends SEUIComModel> baseUIModelClass) {
            this.baseUIModelClass = baseUIModelClass;
        }

        public int getDocumentType() {
            return documentType;
        }

        public void setDocumentType(int documentType) {
            this.documentType = documentType;
        }

        public String getConvToUIMethod() {
            return convToUIMethod;
        }

        public void setConvToUIMethod(String convToUIMethod) {
            this.convToUIMethod = convToUIMethod;
        }

        public String getBaseNodeInstId() {
            return baseNodeInstId;
        }

        public void setBaseNodeInstId(String baseNodeInstId) {
            this.baseNodeInstId = baseNodeInstId;
        }

        public String getSourceFieldName() {
            return sourceFieldName;
        }

        public void setSourceFieldName(String sourceFieldName) {
            this.sourceFieldName = sourceFieldName;
        }

        public String getTargetFieldName() {
            return targetFieldName;
        }

        public void setTargetFieldName(String targetFieldName) {
            this.targetFieldName = targetFieldName;
        }

        public UIModelNodeMapConfigure.IGetSENode getDocMatItemGetCallback() {
            return docMatItemGetCallback;
        }

        public void setDocMatItemGetCallback(UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback) {
            this.docMatItemGetCallback = docMatItemGetCallback;
        }

        public UIModelNodeMapConfigure.IGetSENode getDocGetCallback() {
            return docGetCallback;
        }

        public void setDocGetCallback(UIModelNodeMapConfigure.IGetSENode docGetCallback) {
            this.docGetCallback = docGetCallback;
        }

        public Object getLogicManager() {
            return logicManager;
        }

        public void setLogicManager(Object logicManager) {
            this.logicManager = logicManager;
        }

        public String getItemNodeInstId() {
            return itemNodeInstId;
        }

        public void setItemNodeInstId(String itemNodeInstId) {
            this.itemNodeInstId = itemNodeInstId;
        }
    }

    public static WarehouseMapConfigureUnion genWarehouseConfig(String baseNodeInstId) {
        return new WarehouseMapConfigureUnion(baseNodeInstId);
    }

    public static class WarehouseMapConfigureUnion {

        protected String baseNodeInstId;

        protected String baseWarehouseNodeInstId;

        protected String baseAreaNodeInstId;

        protected String convWarehouseToUIMethod;

        protected String convWarehouseAreaToUIMethod;

        protected Object logicManager;

        protected Class<? extends SEUIComModel> baseUIModelClass;

        protected String mapFieldWarehouse;

        protected String mapFieldWarehouseArea;

        public WarehouseMapConfigureUnion() {
        }

        public WarehouseMapConfigureUnion(String baseNodeInstId) {
            this.baseNodeInstId = baseNodeInstId;
        }

        public WarehouseMapConfigureUnion baseWarehouseNodeInstId(String baseWarehouseNodeInstId) {
            this.baseWarehouseNodeInstId = baseWarehouseNodeInstId;
            return this;
        }

        public WarehouseMapConfigureUnion baseAreaNodeInstId(String baseAreaNodeInstId) {
            this.baseAreaNodeInstId = baseAreaNodeInstId;
            return this;
        }

        public WarehouseMapConfigureUnion convWarehouseToUIMethod(String convWarehouseToUIMethod) {
            this.convWarehouseToUIMethod = convWarehouseToUIMethod;
            return this;
        }

        public WarehouseMapConfigureUnion convWarehouseAreaToUIMethod(String convWarehouseAreaToUIMethod) {
            this.convWarehouseAreaToUIMethod = convWarehouseAreaToUIMethod;
            return this;
        }

        public WarehouseMapConfigureUnion logicManager(Object logicManager) {
            this.logicManager = logicManager;
            return this;
        }

        public WarehouseMapConfigureUnion baseUIModelClass(Class<? extends SEUIComModel> baseUIModelClass) {
            this.baseUIModelClass = baseUIModelClass;
            return this;
        }

        public String getBaseNodeInstId() {
            return baseNodeInstId;
        }

        public void setBaseNodeInstId(String baseNodeInstId) {
            this.baseNodeInstId = baseNodeInstId;
        }

        public String getBaseWarehouseNodeInstId() {
            return baseWarehouseNodeInstId;
        }

        public void setBaseWarehouseNodeInstId(String baseWarehouseNodeInstId) {
            this.baseWarehouseNodeInstId = baseWarehouseNodeInstId;
        }

        public String getBaseAreaNodeInstId() {
            return baseAreaNodeInstId;
        }

        public void setBaseAreaNodeInstId(String baseAreaNodeInstId) {
            this.baseAreaNodeInstId = baseAreaNodeInstId;
        }

        public String getConvWarehouseToUIMethod() {
            return convWarehouseToUIMethod;
        }

        public void setConvWarehouseToUIMethod(String convWarehouseToUIMethod) {
            this.convWarehouseToUIMethod = convWarehouseToUIMethod;
        }

        public String getConvWarehouseAreaToUIMethod() {
            return convWarehouseAreaToUIMethod;
        }

        public void setConvWarehouseAreaToUIMethod(String convWarehouseAreaToUIMethod) {
            this.convWarehouseAreaToUIMethod = convWarehouseAreaToUIMethod;
        }

        public Object getLogicManager() {
            return logicManager;
        }

        public void setLogicManager(Object logicManager) {
            this.logicManager = logicManager;
        }

        public Class<? extends SEUIComModel> getBaseUIModelClass() {
            return baseUIModelClass;
        }

        public void setBaseUIModelClass(Class<? extends SEUIComModel> baseUIModelClass) {
            this.baseUIModelClass = baseUIModelClass;
        }

        public String getMapFieldWarehouse() {
            return mapFieldWarehouse;
        }

        public void setMapFieldWarehouse(String mapFieldWarehouse) {
            this.mapFieldWarehouse = mapFieldWarehouse;
        }

        public String getMapFieldWarehouseArea() {
            return mapFieldWarehouseArea;
        }

        public void setMapFieldWarehouseArea(String mapFieldWarehouseArea) {
            this.mapFieldWarehouseArea = mapFieldWarehouseArea;
        }
    }
}
