package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class DocBatchConvertProxy {

    public static class DocBatchConvertConfig {

        private String targetMatItemNodeName;

        private String targetDocNodeName;

        private ServiceEntityManager targetManager;

        private int targetDocType;

        private Function<ServiceEntityNode, Boolean> filterDocCallback;

        public DocBatchConvertConfig() {
        }

        public DocBatchConvertConfig(String targetMatItemNodeName, String targetDocNodeName,
                                     ServiceEntityManager targetManager, int targetDocType,
                                     Function<ServiceEntityNode, Boolean> filterDocCallback) {
            this.targetMatItemNodeName = targetMatItemNodeName;
            this.targetDocNodeName = targetDocNodeName;
            this.targetManager = targetManager;
            this.targetDocType = targetDocType;
            this.filterDocCallback = filterDocCallback;
        }

        public String getTargetMatItemNodeName() {
            return targetMatItemNodeName;
        }

        public void setTargetMatItemNodeName(String targetMatItemNodeName) {
            this.targetMatItemNodeName = targetMatItemNodeName;
        }

        public String getTargetDocNodeName() {
            return targetDocNodeName;
        }

        public void setTargetDocNodeName(String targetDocNodeName) {
            this.targetDocNodeName = targetDocNodeName;
        }

        public ServiceEntityManager getTargetManager() {
            return targetManager;
        }

        public void setTargetManager(ServiceEntityManager targetManager) {
            this.targetManager = targetManager;
        }

        public Function<ServiceEntityNode, Boolean> getFilterDocCallback() {
            return filterDocCallback;
        }

        public void setFilterDocCallback(Function<ServiceEntityNode, Boolean> filterDocCallback) {
            this.filterDocCallback = filterDocCallback;
        }

        public int getTargetDocType() {
            return targetDocType;
        }

        public void setTargetDocType(int targetDocType) {
            this.targetDocType = targetDocType;
        }
    }

    public List<ServiceEntityNode> getExistTargetDocForCreationBatch(
            DocBatchConvertConfig docBatchConvertConfig,
            List<ServiceEntityNode> allDocMaterialItemList)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> targetDocMatItemList = getExistDocItemListForCreationBatch(
                allDocMaterialItemList, docBatchConvertConfig.getTargetMatItemNodeName(),
                docBatchConvertConfig.getTargetDocType(), docBatchConvertConfig.getTargetManager());
        if(ServiceCollectionsHelper.checkNullList(targetDocMatItemList)){
            return null;
        }
        String client = targetDocMatItemList.get(0).getClient();
        List<ServiceEntityNode> rawDocList = new ArrayList<>();
        List<String> rootUUIDList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(targetDocMatItemList)) {
            targetDocMatItemList.forEach(rawSENode -> {
                if (!rootUUIDList.contains(rawSENode.getRootNodeUUID())) {
                    rootUUIDList.add(rawSENode.getRootNodeUUID());
                }
            });
            rawDocList = docBatchConvertConfig.getTargetManager()
                    .getEntityNodeListByMultipleKey(rootUUIDList,
                            IServiceEntityNodeFieldConstant.UUID,
                            docBatchConvertConfig.targetDocNodeName,
                            client, null);
        }

        /*
         * [Step3] Traverse and process each production plan and only filter initial status
         */
        List<ServiceEntityNode> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(rawDocList)) {
            for (ServiceEntityNode rawSENode : rawDocList) {
                Boolean filterResult = docBatchConvertConfig.filterDocCallback.apply(rawSENode);
                if(filterResult){
                    resultList.add(rawSENode);
                }
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            return resultList;
        }
        return resultList;
    }

    /**
     * Logic to get the Existed & Proper Source Doc Material Item list for
     * batch Target order creation.
     * <p>
     *
     * @param allDocMaterialItemList
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getExistDocItemListForCreationBatch(
            List<ServiceEntityNode> allDocMaterialItemList, String targetNodeName, int targetDocType,
            ServiceEntityManager targetManager)
            throws ServiceEntityConfigureException {
        List<String> targetDocItemUUIDList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(allDocMaterialItemList)){
            return null;
        }
        String client = allDocMaterialItemList.get(0).getClient();
        for (ServiceEntityNode seNode : allDocMaterialItemList) {
            DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
            if (!ServiceEntityStringHelper
                    .checkNullString(docMatItemNode
                            .getNextDocMatItemUUID())
                    && docMatItemNode.getNextDocType() == targetDocType) {
                targetDocItemUUIDList.add(docMatItemNode
                        .getNextDocMatItemUUID());
            }
        }
        /*
         * [Step2]:Get the quality material item list
         */
        List<ServiceEntityNode> targetDocMatItemList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(targetDocItemUUIDList)) {
            targetDocMatItemList = targetManager
                    .getEntityNodeListByMultipleKey(targetDocItemUUIDList,
                            IServiceEntityNodeFieldConstant.UUID,
                            targetNodeName,
                            client, null);
        }
        return targetDocMatItemList;
    }

}
