package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.DocInvolvePartySearchModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy.*;
import static com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF;

/**
 * Helper class to generate default Search Node Configure for Doc & Doc Mat Item
 */
@Service
public class SearchDocConfigHelper {

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    protected Logger logger = LoggerFactory.getLogger(SearchDocConfigHelper.class);

    public static final String NODE_ID_CREATEDBY = "createdBy";

    public static final String NODE_ID_LASTUPDATEDBY = "lastUpdateBy";

    public static final String NODE_ID_PREV_PROF_DOC = "prevProfDoc";

    public static final String NODE_ID_PREV_DOC = "prevDoc";

    public static final String NODE_ID_NEXT_PROF_DOC = "nextProfDoc";

    public static final String NODE_ID_NEXT_DOC = "nextDoc";

    public static final String NODE_ID_RESERVED_BY_DOC = "reservedByDoc";

    public static final String NODE_ID_RESERVED_TARGET_DOC = "reservedTargetDoc";

    public static class SearchDocMatItemConfigureUnion {

        protected String parentNodeInstId;

        protected String homeNodeName;

        protected String homeSeName;

        protected String homeNodeInstId;

        public SearchDocMatItemConfigureUnion() {

        }

        public SearchDocMatItemConfigureUnion(String parentNodeInstId, String homeSeName, String homeNodeName,
                                              String homeNodeInstId) {
            this.parentNodeInstId = parentNodeInstId;
            this.homeSeName = homeSeName;
            this.homeNodeName = homeNodeName;
            this.homeNodeInstId = homeNodeInstId;
        }

        public String getHomeSeName() {
            return homeSeName;
        }

        public void setHomeSeName(String homeSeName) {
            this.homeSeName = homeSeName;
        }

        public String getParentNodeInstId() {
            return parentNodeInstId;
        }

        public void setParentNodeInstId(String parentNodeInstId) {
            this.parentNodeInstId = parentNodeInstId;
        }

        public String getHomeNodeName() {
            return homeNodeName;
        }

        public void setHomeNodeName(String homeNodeName) {
            this.homeNodeName = homeNodeName;
        }

        public String getHomeNodeInstId() {
            return homeNodeInstId;
        }

        public void setHomeNodeInstId(String homeNodeInstId) {
            this.homeNodeInstId = homeNodeInstId;
        }
    }

    /**
     * [Internal Class] For manage the initial configure for Action Node
     */
    public static class SearchDocActionNodeConfigUnion {

        protected int actionCode;

        protected String parentNodeInstId;

        protected String homeSeName;

        protected String homeNodeName;

        protected String homeNodeInstId;

        public SearchDocActionNodeConfigUnion() {

        }

        public SearchDocActionNodeConfigUnion(int actionCode, String parentNodeInstId, String homeSeName,
                                              String homeNodeName,
                                              String homeNodeInstId) {
            this.actionCode = actionCode;
            this.parentNodeInstId = parentNodeInstId;
            this.homeSeName = homeSeName;
            this.homeNodeName = homeNodeName;
            this.homeNodeInstId = homeNodeInstId;
        }

        public int getActionCode() {
            return actionCode;
        }

        public void setActionCode(int actionCode) {
            this.actionCode = actionCode;
        }

        public String getParentNodeInstId() {
            return parentNodeInstId;
        }

        public void setParentNodeInstId(String parentNodeInstId) {
            this.parentNodeInstId = parentNodeInstId;
        }

        public String getHomeSeName() {
            return homeSeName;
        }

        public void setHomeSeName(String homeSeName) {
            this.homeSeName = homeSeName;
        }

        public String getHomeNodeName() {
            return homeNodeName;
        }

        public void setHomeNodeName(String homeNodeName) {
            this.homeNodeName = homeNodeName;
        }

        public String getHomeNodeInstId() {
            return homeNodeInstId;
        }

        public void setHomeNodeInstId(String homeNodeInstId) {
            this.homeNodeInstId = homeNodeInstId;
        }
    }

    /**
     * [Internal Class] For manage the initial configure for Doc Party
     */
    public static class SearchDocPartyConfigUnion {

        protected String parentNodeInstId;

        protected String homeSeName;

        protected String homeNodeName;

        protected String homeNodeInstId;

        protected String targetPartyInstId;

        protected String targetContactInstId;

        protected String targetPartySEName;

        protected String targetContactSEName;

        public SearchDocPartyConfigUnion() {

        }

        public SearchDocPartyConfigUnion(String homeSeName, String parentNodeInstId, String homeNodeName,
                                         String homeNodeInstId,
                                         String targetPartyInstId, String targetContactInstId,
                                         String targetPartySEName, String targetContactSEName) {
            this.parentNodeInstId = parentNodeInstId;
            this.homeNodeName = homeNodeName;
            this.homeSeName = homeSeName;
            this.homeNodeInstId = homeNodeInstId;
            this.targetPartyInstId = targetPartyInstId;
            this.targetContactInstId = targetContactInstId;
            this.targetPartySEName = targetPartySEName;
            this.targetContactSEName = targetContactSEName;
        }

        public String getHomeSeName() {
            return homeSeName;
        }

        public void setHomeSeName(String homeSeName) {
            this.homeSeName = homeSeName;
        }

        public String getHomeNodeName() {
            return homeNodeName;
        }

        public void setHomeNodeName(String homeNodeName) {
            this.homeNodeName = homeNodeName;
        }

        public String getHomeNodeInstId() {
            return homeNodeInstId;
        }

        public void setHomeNodeInstId(String homeNodeInstId) {
            this.homeNodeInstId = homeNodeInstId;
        }

        public String getParentNodeInstId() {
            return parentNodeInstId;
        }

        public void setParentNodeInstId(String parentNodeInstId) {
            this.parentNodeInstId = parentNodeInstId;
        }

        public String getTargetPartyInstId() {
            return targetPartyInstId;
        }

        public void setTargetPartyInstId(String targetPartyInstId) {
            this.targetPartyInstId = targetPartyInstId;
        }

        public String getTargetContactInstId() {
            return targetContactInstId;
        }

        public void setTargetContactInstId(String targetContactInstId) {
            this.targetContactInstId = targetContactInstId;
        }

        public String getTargetPartySEName() {
            return targetPartySEName;
        }

        public void setTargetPartySEName(String targetPartySEName) {
            this.targetPartySEName = targetPartySEName;
        }

        public String getTargetContactSEName() {
            return targetContactSEName;
        }

        public void setTargetContactSEName(String targetContactSEName) {
            this.targetContactSEName = targetContactSEName;
        }
    }

    /**
     * Generate Default Search Node Configure List to contain party node, target Party Node and Contact Node
     *
     * @param searchDocPartyConfigUnion
     * @return
     */
    @Deprecated
    // Migrate to static: genInvolvePartySearchNodeConfigureList
    public List<BSearchNodeComConfigure> genInvolvePartySearchNodeConfigureList(SearchDocPartyConfigUnion searchDocPartyConfigUnion) {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node1: Home InvolveParty node
        BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
        searchNodeConfig1.setSeName(searchDocPartyConfigUnion.getHomeSeName());
        searchNodeConfig1.setNodeName(searchDocPartyConfigUnion.getHomeNodeName());
        searchNodeConfig1.setNodeInstID(searchDocPartyConfigUnion.getHomeNodeInstId());
        searchNodeConfig1.setStartNodeFlag(false);
        searchNodeConfig1
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        searchNodeConfig1
                .setBaseNodeInstID(searchDocPartyConfigUnion.getParentNodeInstId());
        searchNodeConfigList.add(searchNodeConfig1);

        // Node2: Target Party node
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(searchDocPartyConfigUnion.getTargetPartySEName());
        // Default Target Party Node Name: ROOT
        searchNodeConfig2.setNodeName(ServiceEntityNode.NODENAME_ROOT);
        searchNodeConfig2.setNodeInstID(searchDocPartyConfigUnion.getTargetPartyInstId());
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
        searchNodeConfig2.setSubNodeInstId(DocInvolvePartySearchModel.NODE_INST_TARGETPARTY);
        searchNodeConfig2
                .setBaseNodeInstID(searchDocPartyConfigUnion.getHomeNodeInstId());
        searchNodeConfigList.add(searchNodeConfig2);

        // Node3: Target Contact node
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(searchDocPartyConfigUnion.getTargetContactSEName());
        // Default Target Contact Node Name: ROOT
        searchNodeConfig3.setNodeName(ServiceEntityNode.NODENAME_ROOT);
        searchNodeConfig3.setNodeInstID(searchDocPartyConfigUnion.getTargetContactInstId());
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig3.setMapBaseFieldName("refContactUUID");
        searchNodeConfig3.setSubNodeInstId(DocInvolvePartySearchModel.NODE_INST_PARTYCONTACT);
        searchNodeConfig3
                .setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
        searchNodeConfig3
                .setBaseNodeInstID(searchDocPartyConfigUnion.getTargetPartyInstId());
        searchNodeConfigList.add(searchNodeConfig3);
        return searchNodeConfigList;
    }

    /**
     * Generate Default Search Node Configure List to contain party node, target Party Node and Contact Node
     *
     * @param
     * @return
     */
    public static List<BSearchNodeComConfigure> genInvolvePartySearchNodeConfigureList(
            Class<? extends ServiceEntityNode> involvePartyClass, Class<? extends ServiceEntityNode> targetPartyClass,
            Class<? extends ServiceEntityNode> targetContactClass, int partyRole,  String involvePartyInstId,
            String parentNodeInstId)
            throws SearchConfigureException {
        String targetPartyInstId =
                involvePartyInstId + SearchModelConfigHelper.getDefNodeInstIdByModelClass(targetPartyClass);
        String targetContactId =
                involvePartyInstId + SearchModelConfigHelper.getDefNodeInstIdByModelClass(targetContactClass);
        return genInvolvePartySearchNodeConfigureList(involvePartyClass, targetPartyClass, targetContactClass,partyRole,
                involvePartyInstId, targetPartyInstId, targetContactId, null, parentNodeInstId);
    }

    public static List<BSearchNodeComConfigure> genInvolvePartySearchNodeConfigureList(
            Class<? extends ServiceEntityNode> involvePartyClass, Class<? extends ServiceEntityNode> targetPartyClass,
            Class<? extends ServiceEntityNode> targetContactClass, int partyRole,  String involvePartyInstId, String targetPartyInstId,
            String targetContactId, BSearchNodeComConfigureBuilder hostNodeBuilder, String parentNodeInstId)
            throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node1: Home InvolveParty node
        if (hostNodeBuilder == null) {
            hostNodeBuilder =
                    SearchModelConfigHelper.genBuilder().toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        }
        hostNodeBuilder.addPreCondition(partyRole, DocInvolveParty.FIELD_PARTYROLE, null);
        if (!ServiceEntityStringHelper.checkNullString(parentNodeInstId)) {
            hostNodeBuilder.baseNodeInstId(parentNodeInstId);
            hostNodeBuilder.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        }
        String localInvolvePartyInstId = SearchModelConfigHelper.getNodeInstIdWrapper(involvePartyClass,
                involvePartyInstId);
        searchNodeConfigList.add(hostNodeBuilder.modelClass(involvePartyClass).nodeInstId(localInvolvePartyInstId).build());
        // Node2: Target Party node
        String localTargetPartyInstId = SearchModelConfigHelper.getNodeInstIdWrapper(targetPartyClass,
                targetPartyInstId);
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(targetPartyClass).subNodeInstId(DocInvolvePartySearchModel.NODE_INST_TARGETPARTY).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(localInvolvePartyInstId).nodeInstId(localTargetPartyInstId).build());
        // Node3: Target Contact node
        String localTargetContactInstId = SearchModelConfigHelper.getNodeInstIdWrapper(targetContactClass,
                targetContactId);
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(targetContactClass).subNodeInstId(DocInvolvePartySearchModel.NODE_INST_PARTYCONTACT).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).baseNodeInstId(localTargetPartyInstId).mapFieldUUID("refContactUUID").nodeInstId(localTargetContactInstId).build());
        return searchNodeConfigList;
    }



    /**
     * Generate Default Search Node Configure List to contain party node, target Party Node and Contact Node
     *
     * @param
     * @return
     */
    public static List<BSearchNodeComConfigure> genCreatedUpdatedBySearchNodeConfigureList(
            String parentNodeInstId)
            throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        BSearchNodeComConfigureBuilder createdNodeBuilder = SearchModelConfigHelper.genBuilder().
                baseNodeInstId(parentNodeInstId).nodeInstId(NODE_ID_CREATEDBY).subNodeInstId(NODE_ID_CREATEDBY).modelClass(LogonUser.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(IServiceEntityNodeFieldConstant.CREATEDBY);
        searchNodeConfigList.add(createdNodeBuilder.build());
        BSearchNodeComConfigureBuilder lastUpdateNodeBuilder = SearchModelConfigHelper.genBuilder().
                baseNodeInstId(parentNodeInstId).nodeInstId(NODE_ID_LASTUPDATEDBY).subNodeInstId(NODE_ID_LASTUPDATEDBY).modelClass(LogonUser.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(IServiceEntityNodeFieldConstant.LASTUPDATEBY);
        searchNodeConfigList.add(lastUpdateNodeBuilder.build());
        return searchNodeConfigList;
    }

    /**
     * Get doc flow node inst id in the search configuration
     * @param docFlowDirection
     * @return
     */
    public static String getTargetDocFlowSearchNodeId(int docFlowDirection) {
        if (docFlowDirection == DOCFLOW_DIREC_PREV) {
            return NODE_ID_PREV_DOC;
        }
        if (docFlowDirection == DOCFLOW_DIREC_PREV_PROF) {
            return NODE_ID_PREV_PROF_DOC;
        }
        if (docFlowDirection == DOCFLOW_DIREC_NEXT) {
            return NODE_ID_NEXT_DOC;
        }
        if (docFlowDirection == DOCFLOW_DIREC_NEXT_PROF) {
            return NODE_ID_NEXT_PROF_DOC;
        }
        if (docFlowDirection == DOCFLOW_TO_RESERVED_BY) {
            return NODE_ID_RESERVED_BY_DOC;
        }
        if (docFlowDirection == DOCFLOW_TO_RESERVE_TARGET) {
            return NODE_ID_RESERVED_TARGET_DOC;
        }
        return NODE_ID_PREV_DOC;
    }

    public static String getTargetDocFlowSearchItemNodeId(String docNodeId) {
        return docNodeId + "Item";
    }

    /**
     * Generate Default Search Node Configure List to contain party node, target Party Node and Contact Node
     *
     * @param
     * @return
     */
    public List<BSearchNodeComConfigure> genDocFlowNodeBySearchNodeConfigureList(
            String parentNodeInstId, int docFlowDirection, DocFlowNodeSearchModel docFlowNodeSearchModel)
            throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        DocFlowContextProxy.FieldNameUnit fieldNameUnit = DocFlowContextProxy.getFieldNameUnitByFlowDirection(docFlowDirection);
        if (fieldNameUnit == null) {
            return null;
        }
        if (docFlowNodeSearchModel == null) {
            return null;
        }
        int targetDocType = docFlowNodeSearchModel.getTargetDocType();
        try {
            DocumentContentSpecifier<?, ?, ?> targetDocContentSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(targetDocType);
            if (targetDocContentSpecifier == null) {
                return null;
            }
            String targetDocId = getTargetDocFlowSearchNodeId(docFlowDirection);
            String targetDocMatItemId = getTargetDocFlowSearchItemNodeId(targetDocId);
            Class<? extends ServiceEntityNode> matItemNodeClass = targetDocContentSpecifier.getDocMetadata().getItemModelClass();
            // Home Doc Mat Item -> Target Doc Mat Item
            BSearchNodeComConfigureBuilder targetDocMatItemBuilder = SearchModelConfigHelper.genBuilder().
                    baseNodeInstId(parentNodeInstId).nodeInstId(targetDocMatItemId).
                    subNodeInstId(targetDocMatItemId).modelClass(matItemNodeClass).
                    toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(fieldNameUnit.getDocMatItemUUIDFieldName());
            searchNodeConfigList.add(targetDocMatItemBuilder.build());
            // Target Doc Mat Item -> Target Doc
            Class<? extends ServiceEntityNode> docNodeClass = targetDocContentSpecifier.getDocMetadata().getDocModelClass();
            BSearchNodeComConfigureBuilder prevDocNodeBuilder = SearchModelConfigHelper.genBuilder().
                    baseNodeInstId(targetDocMatItemId).nodeInstId(targetDocId).subNodeInstId(targetDocId).modelClass(docNodeClass).
                    toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
            searchNodeConfigList.add(prevDocNodeBuilder.build());
        } catch (DocActionException e) {
            throw new RuntimeException(e);
        }
        return searchNodeConfigList;
    }

    /**
     * Utility method to generate or process the HostNodeBuilder, set this host node to parent relationship and
     * parentNodeId when input parameter: parentNodeInstId with none-empty value
     *
     * @param hostNodeBuilder
     * @param parentNodeInstId
     * @return
     */
    public static BSearchNodeComConfigureBuilder genHostNodeBuilder(BSearchNodeComConfigureBuilder hostNodeBuilder,
                                                                    String parentNodeInstId) {
        if (hostNodeBuilder == null) {
            hostNodeBuilder =
                    SearchModelConfigHelper.genBuilder().toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        }
        if (!ServiceEntityStringHelper.checkNullString(parentNodeInstId)) {
            hostNodeBuilder.baseNodeInstId(parentNodeInstId);
            hostNodeBuilder.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
        }
        return hostNodeBuilder;
    }

    public static List<BSearchNodeComConfigure> genActionNodeSearchNodeConfigureList(
            Class<? extends ServiceEntityNode> actionNodeClass, String actionNodeInstId,
            int actionCode, BSearchNodeComConfigureBuilder hostNodeBuilder, String parentNodeInstId)
            throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node1: Action node
        hostNodeBuilder = genHostNodeBuilder(hostNodeBuilder, parentNodeInstId);
        hostNodeBuilder.addPreCondition(actionCode, DocActionNode.FIELD_DOCACTIONCODE, null);
        String localActionNodeInstId = SearchModelConfigHelper.getNodeInstIdWrapper(actionNodeClass,
                actionNodeInstId);
        searchNodeConfigList.add(hostNodeBuilder.modelClass(actionNodeClass).nodeInstId(localActionNodeInstId).build());
        // Node2: Executed by user node
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(LogonUser.class).subNodeInstId(LogonUser.SENAME).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(DocActionNode.FIELD_EXECUTEBY_UUID).baseNodeInstId(localActionNodeInstId).
                nodeInstId(actionNodeInstId + "ExecutedBy").build());
        return searchNodeConfigList;
    }

    public static List<BSearchNodeComConfigure> genWarehouseSearchNodeConfigureList(List<BSearchNodeComConfigure> baseSearchNodeConfigList, String parentNodeInstId)
            throws SearchConfigureException {
        return genWarehouseSearchNodeConfigureList(baseSearchNodeConfigList, parentNodeInstId, null, null, null, null);
    }

    public static List<BSearchNodeComConfigure> genWarehouseSearchNodeConfigureList(List<BSearchNodeComConfigure> baseSearchNodeConfigList, String parentNodeInstId,
                                                                                    String refWarehouseInstId, String refWarehouseAreaInstId, String warehouseUuidField, String warehouseAreaUuidField)
            throws SearchConfigureException {
        // Node Warehouse search node
        String localWarehouseInstId = SearchModelConfigHelper.getNodeInstIdWrapper(Warehouse.class, refWarehouseInstId);
        String localWarehouseUuidField = ServiceEntityStringHelper.checkNullString(warehouseUuidField) ?
                "refWarehouseUUID" : warehouseUuidField;
        String localWarehouseAreaInstId = SearchModelConfigHelper.getNodeInstIdWrapper(Warehouse.class, refWarehouseAreaInstId);
        String localWarehouseAreaUuidField = ServiceEntityStringHelper.checkNullString(warehouseAreaUuidField) ?
                "refWarehouseAreaUUID" : warehouseAreaUuidField;
        baseSearchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Warehouse.class).baseNodeInstId(parentNodeInstId).nodeInstId(localWarehouseInstId)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(localWarehouseUuidField).subNodeInstId(Warehouse.SENAME).build());
        baseSearchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(WarehouseArea.class).baseNodeInstId(parentNodeInstId).nodeInstId(localWarehouseAreaInstId)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(localWarehouseAreaUuidField).subNodeInstId(WarehouseArea.NODENAME).build());
        return baseSearchNodeConfigList;
    }

    public void convertActionNodeSearchFormatBatch(
            List<DocActionNodeSearchModel> docActionNodeSearchModelList) {
        if (ServiceCollectionsHelper.checkNullList(docActionNodeSearchModelList)) {
            return;
        }
        for (DocActionNodeSearchModel docActionNodeSearchModel : docActionNodeSearchModelList) {
            if (docActionNodeSearchModel == null) {
                continue;
            }
            convertActionNodeSearchFormat(docActionNodeSearchModel);
        }
    }

    private void convertActionNodeSearchFormat(
            DocActionNodeSearchModel docActionNodeSearchModel) {
        if (docActionNodeSearchModel == null) return;
        if (!ServiceEntityStringHelper
                .checkNullString(docActionNodeSearchModel.getExecutionTimeStrLow())) {
            try {
                docActionNodeSearchModel
                        .setExecutionTimeLow(DefaultDateFormatConstant.DATE_FORMAT
                                .parse(docActionNodeSearchModel
                                        .getExecutionTimeStrLow()));
            } catch (ParseException e) {
                // record and do nothing
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "executionTimeStrLow"));
            }
        }
        if (!ServiceEntityStringHelper
                .checkNullString(docActionNodeSearchModel.getExecutionTimeStrHigh())) {
            try {
                docActionNodeSearchModel
                        .setExecutionTimeHigh(DefaultDateFormatConstant.DATE_FORMAT
                                .parse(docActionNodeSearchModel
                                        .getExecutionTimeStrHigh()));
            } catch (ParseException e) {
                // record and do nothing
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "executionTimeStrHigh"));
            }
        }
    }


    /**
     * Generate Default Search Node Configure List to contain Doc Mat Item, Material and Registered Product
     *
     * @param searchDocMatItemConfigureUnion
     * @return
     */
    public List<BSearchNodeComConfigure> genDocMatItemSearchNodeConfigureList(SearchDocMatItemConfigureUnion searchDocMatItemConfigureUnion) {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node1: Home InvolveParty node
        String homeNodeInstId = searchDocMatItemConfigureUnion.getHomeNodeInstId();
        if (ServiceEntityStringHelper.checkNullString(homeNodeInstId)) {
            homeNodeInstId = searchDocMatItemConfigureUnion.getHomeNodeName();
        }
        BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
        searchNodeConfig1.setSeName(searchDocMatItemConfigureUnion.getHomeSeName());
        searchNodeConfig1.setNodeName(searchDocMatItemConfigureUnion.getHomeNodeName());
        searchNodeConfig1.setNodeInstID(homeNodeInstId);
        if (ServiceEntityStringHelper.checkNullString(searchDocMatItemConfigureUnion.getParentNodeInstId())) {
            searchNodeConfig1.setStartNodeFlag(true);
            searchNodeConfigList.add(searchNodeConfig1);
        } else {
            searchNodeConfig1.setStartNodeFlag(false);
            searchNodeConfig1.setBaseNodeInstID(searchDocMatItemConfigureUnion.getParentNodeInstId());
            searchNodeConfig1.setToBaseNodeType(BSearchNodeComConfigure.TOBASENODE_TO_PARENT);
            searchNodeConfigList.add(searchNodeConfig1);
        }
        // Add Material node Array
        searchNodeConfigList.addAll(genMaterialSearchNodeConfigureList(homeNodeInstId));
        return searchNodeConfigList;
    }

    public static List<BSearchNodeComConfigure> genDocMatItemSearchNodeConfigureList(
            Class<? extends ServiceEntityNode> docMatItemClass, String parentNodeInstId)
            throws SearchConfigureException {
        return genDocMatItemSearchNodeConfigureList(docMatItemClass, null, null, parentNodeInstId);
    }

    public static List<BSearchNodeComConfigure> genDocMatItemSearchNodeConfigureList(
            Class<? extends ServiceEntityNode> docMatItemClass, String docMatItemNodeInstId,
            BSearchNodeComConfigureBuilder hostNodeBuilder, String parentNodeInstId)
            throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node1: Action node
        hostNodeBuilder = genHostNodeBuilder(hostNodeBuilder, parentNodeInstId);
        String localDocMatItemInstId = SearchModelConfigHelper.getNodeInstIdWrapper(docMatItemClass,
                docMatItemNodeInstId);
        searchNodeConfigList.add(hostNodeBuilder.modelClass(docMatItemClass).nodeInstId(localDocMatItemInstId).build());
        searchNodeConfigList.addAll(genMatSearchNodeConfigureList(localDocMatItemInstId));
        return searchNodeConfigList;
    }


    /**
     * Generate Default Search Node Configure List to contain MaterialItem
     *
     * @param prevInstId
     * @return
     */
    public List<BSearchNodeComConfigure> genMaterialSearchNodeConfigureList(String prevInstId) {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node2: MaterialSKU node
        BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
        searchNodeConfig2.setSeName(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setNodeName(MaterialStockKeepUnit.NODENAME);
        searchNodeConfig2.setNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfig2.setStartNodeFlag(false);
        searchNodeConfig2
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig2.setMapBaseFieldName(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
        searchNodeConfig2
                .setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
        searchNodeConfig2.setBaseNodeInstID(prevInstId);
        searchNodeConfigList.add(searchNodeConfig2);

        // Node3: Registered product node
        BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
        searchNodeConfig3.setSeName(RegisteredProduct.SENAME);
        searchNodeConfig3.setNodeName(RegisteredProduct.NODENAME);
        searchNodeConfig3.setNodeInstID(RegisteredProduct.SENAME);
        searchNodeConfig3.setStartNodeFlag(false);
        searchNodeConfig3
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig3.setMapBaseFieldName(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
        searchNodeConfig3
                .setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
        searchNodeConfig3.setSubNodeInstId(RegisteredProduct.SENAME);
        searchNodeConfig3.setBaseNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfigList.add(searchNodeConfig3);

        // Node4: Basic Material
        BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
        searchNodeConfig4.setSeName(Material.SENAME);
        searchNodeConfig4.setNodeName(Material.NODENAME);
        searchNodeConfig4.setNodeInstID(Material.SENAME);
        searchNodeConfig4.setStartNodeFlag(false);
        searchNodeConfig4
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig4.setSubNodeInstId(MaterialType.SENAME);
        searchNodeConfig4.setMapBaseFieldName(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
        searchNodeConfig4
                .setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
        searchNodeConfig4.setBaseNodeInstID(MaterialStockKeepUnit.SENAME);
        searchNodeConfigList.add(searchNodeConfig4);

        // Node4: Material Type
        BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
        searchNodeConfig5.setSeName(MaterialType.SENAME);
        searchNodeConfig5.setNodeName(MaterialType.NODENAME);
        searchNodeConfig5.setNodeInstID(MaterialType.SENAME);
        searchNodeConfig5.setStartNodeFlag(false);
        searchNodeConfig5
                .setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
        searchNodeConfig5.setMapBaseFieldName("refMaterialType");
        searchNodeConfig5
                .setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
        searchNodeConfig5.setBaseNodeInstID(Material.SENAME);
        searchNodeConfigList.add(searchNodeConfig5);
        return searchNodeConfigList;
    }

    /**
     * Generate Default Search Node Configure List to contain MaterialItem
     *
     * @param prevInstId
     * @return
     */
    public static List<BSearchNodeComConfigure> genMatSearchNodeConfigureList(String prevInstId)
            throws SearchConfigureException {
        List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
        // Node2: MaterialSKU node
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialStockKeepUnit.class).baseNodeInstId(prevInstId)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID).build());
        // Node3: Registered product node
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(RegisteredProduct.class).baseNodeInstId(MaterialStockKeepUnit.SENAME)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID).build());
        // Node4: Basic Material
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(RegisteredProduct.class).baseNodeInstId(MaterialStockKeepUnit.SENAME)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID).build());
        // Node5: MaterialSKU -> Material
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Material.class).baseNodeInstId(MaterialStockKeepUnit.SENAME)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(MaterialStockKeepUnit.FIELD_REF_MATERIALUUID).build());
        // Node5: RegisteredProduct -> Material
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Material.class).baseNodeInstId(RegisteredProduct.SENAME)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(MaterialStockKeepUnit.FIELD_REF_MATERIALUUID).build());
        // Node6: Material Type
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialType.class).baseNodeInstId(Material.SENAME)
                .toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refMaterialType").build());
        return searchNodeConfigList;
    }

    public static String[] genDefaultMaterialFieldNameArray() {
        return new String[]{"refMaterialSKUName", "refMaterialSKUId", "refMaterialSKUUUID",
                "serialId", "packageStandard", "uuid", "status"};
    }


    public static String[] genDefDocMatItemFieldNameArray() {
        return new String[]{"refMaterialSKUName", "refMaterialSKUId", "refMaterialSKUUUID",
                "serialId", "packageStandard", "uuid", "status"};
    }
}
