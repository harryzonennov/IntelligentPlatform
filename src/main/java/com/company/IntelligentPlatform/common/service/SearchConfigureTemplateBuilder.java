package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;

public class SearchConfigureTemplateBuilder {

    public static class SearchConfigureTemplateNode {

        protected Class<? extends ServiceEntityNode> nodeClass;

        protected String nodeInstId;

        protected int nodeInstCode;

        protected int nodeCategory;

        protected String baseNodeInstId;

        public SearchConfigureTemplateNode() {
        }

        public SearchConfigureTemplateNode(Class<? extends ServiceEntityNode> nodeClass) {
            this.nodeClass = nodeClass;
        }

        public SearchConfigureTemplateNode(Class<? extends ServiceEntityNode> nodeClass, String nodeInstId, int nodeInstCode, int nodeCategory) {
            this.nodeClass = nodeClass;
            this.nodeInstId = nodeInstId;
            this.nodeInstCode = nodeInstCode;
            this.nodeCategory = nodeCategory;
        }

        public Class<? extends ServiceEntityNode> getNodeClass() {
            return nodeClass;
        }

        public void setNodeClass(Class<? extends ServiceEntityNode> nodeClass) {
            this.nodeClass = nodeClass;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public int getNodeInstCode() {
            return nodeInstCode;
        }

        public void setNodeInstCode(int nodeInstCode) {
            this.nodeInstCode = nodeInstCode;
        }

        public int getNodeCategory() {
            return nodeCategory;
        }

        public void setNodeCategory(int nodeCategory) {
            this.nodeCategory = nodeCategory;
        }

        public String getBaseNodeInstId() {
            return baseNodeInstId;
        }

        public void setBaseNodeInstId(String baseNodeInstId) {
            this.baseNodeInstId = baseNodeInstId;
        }
    }

    protected Class<? extends ServiceEntityNode> nodeClass;

    protected String nodeInstId;

    protected int nodeInstCode;

    protected int nodeCategory;

    protected String baseNodeInstId;

    public Class<? extends ServiceEntityNode> getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(Class<? extends ServiceEntityNode> nodeClass) {
        this.nodeClass = nodeClass;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public int getNodeInstCode() {
        return nodeInstCode;
    }

    public void setNodeInstCode(int nodeInstCode) {
        this.nodeInstCode = nodeInstCode;
    }

    public int getNodeCategory() {
        return nodeCategory;
    }

    public void setNodeCategory(int nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    public String getBaseNodeInstId() {
        return baseNodeInstId;
    }

    public void setBaseNodeInstId(String baseNodeInstId) {
        this.baseNodeInstId = baseNodeInstId;
    }

    public SearchConfigureTemplateBuilder nodeClass(Class<? extends ServiceEntityNode> nodeClass) {
        this.nodeClass = nodeClass;
        return this;
    }

    public SearchConfigureTemplateBuilder nodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
        return this;
    }

    public SearchConfigureTemplateBuilder nodeInstCode(int nodeInstCode) {
        this.nodeInstCode = nodeInstCode;
        return this;
    }

    public SearchConfigureTemplateBuilder nodeCategory(int nodeCategory) {
        this.nodeCategory = nodeCategory;
        return this;
    }

    public SearchConfigureTemplateBuilder baseNodeInstId(String baseNodeInstId) {
        this.baseNodeInstId = baseNodeInstId;
        return this;
    }

    public SearchConfigureTemplateNode build() throws SearchConfigureException {
        if (ServiceEntityStringHelper.checkNullString(getNodeInstId())) {
            try {
                String seName = BSearchNodeComConfigureBuilder.calculateRefSEName(this.getNodeClass());
                String nodeName = BSearchNodeComConfigureBuilder.calculateRefNodeName(this.getNodeClass());
                String nodeInstId = ServiceEntityStringHelper.getDefModelId(seName, nodeName);
                setNodeInstId(nodeInstId);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        SearchConfigureTemplateNode searchConfigureTemplateNode = new SearchConfigureTemplateNode(
            this.getNodeClass()
        );
        if (this.getNodeCategory() == 0) {
            this.setNodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_DOC);
        }
        searchConfigureTemplateNode.setNodeInstId(this.getNodeInstId());
        searchConfigureTemplateNode.setNodeInstCode(this.getNodeInstCode());
        searchConfigureTemplateNode.setBaseNodeInstId(this.getBaseNodeInstId());
        searchConfigureTemplateNode.setNodeCategory(this.getNodeCategory());
        return searchConfigureTemplateNode;
    }

    public static List<SearchConfigureTemplateNode> filterTemplateByBaseNodeId(List<SearchConfigureTemplateNode> searchConfigureTemplateNodeList, String baseNodeInstId) {
        return ServiceCollectionsHelper.filterListOnline(searchConfigureTemplateNodeList, searchConfigureTemplateNode -> searchConfigureTemplateNode.getBaseNodeInstId() == baseNodeInstId, false);
    }

    public static List<SearchConfigureTemplateNode> filterTemplateByNodeCategoryList(List<SearchConfigureTemplateNode> searchConfigureTemplateNodeList, int nodeCategory) {
        return ServiceCollectionsHelper.filterListOnline(searchConfigureTemplateNodeList, searchConfigureTemplateNode -> searchConfigureTemplateNode.getNodeCategory() == nodeCategory, false);
    }

    public static SearchConfigureTemplateNode filterTemplateByNodeCategory(List<SearchConfigureTemplateNode> searchConfigureTemplateNodeList, int nodeCategory) {
        List<SearchConfigureTemplateNode> resultTemplateList = filterTemplateByNodeCategoryList(searchConfigureTemplateNodeList, nodeCategory);
        return  ServiceCollectionsHelper.checkNullList(resultTemplateList) ? null: resultTemplateList.get(0);
    }
}
