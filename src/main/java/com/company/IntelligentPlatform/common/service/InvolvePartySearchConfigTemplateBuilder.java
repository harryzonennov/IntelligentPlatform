package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class InvolvePartySearchConfigTemplateBuilder extends SearchConfigureTemplateBuilder{


    public static class InvolvePartySearchConfigTemplate extends SearchConfigureTemplateNode {

        Class<? extends ServiceEntityNode> targetPartyClass;

        Class<? extends ServiceEntityNode> targetContactClass;

        public InvolvePartySearchConfigTemplate() {

        }

        public InvolvePartySearchConfigTemplate(Class<? extends ServiceEntityNode> nodeClass, Class<? extends ServiceEntityNode> targetPartyClass, Class<? extends ServiceEntityNode> targetContactClass) {
            super(nodeClass);
            this.targetPartyClass = targetPartyClass;
            this.targetContactClass = targetContactClass;
        }

        public InvolvePartySearchConfigTemplate(Class<? extends ServiceEntityNode> nodeClass, String nodeInstId, int nodeInstCode, int docNodeCategory, Class<? extends ServiceEntityNode> targetPartyClass, Class<? extends ServiceEntityNode> targetContactClass) {
            super(nodeClass, nodeInstId, nodeInstCode, docNodeCategory);
            this.targetPartyClass = targetPartyClass;
            this.targetContactClass = targetContactClass;
        }

        public Class<? extends ServiceEntityNode> getTargetPartyClass() {
            return targetPartyClass;
        }

        public void setTargetPartyClass(Class<? extends ServiceEntityNode> targetPartyClass) {
            this.targetPartyClass = targetPartyClass;
        }

        public Class<? extends ServiceEntityNode> getTargetContactClass() {
            return targetContactClass;
        }

        public void setTargetContactClass(Class<? extends ServiceEntityNode> targetContactClass) {
            this.targetContactClass = targetContactClass;
        }
    }


    Class<? extends ServiceEntityNode> targetPartyClass;

    Class<? extends ServiceEntityNode> targetContactClass;

    public InvolvePartySearchConfigTemplateBuilder targetPartyClass(Class<? extends ServiceEntityNode> targetPartyClass) {
        this.targetPartyClass = targetPartyClass;
        return this;
    }

    public InvolvePartySearchConfigTemplateBuilder targetContactClass(Class<? extends ServiceEntityNode> targetContactClass) {
        this.targetContactClass = targetContactClass;
        return this;
    }

    public Class<? extends ServiceEntityNode> getTargetPartyClass() {
        return targetPartyClass;
    }

    public void setTargetPartyClass(Class<? extends ServiceEntityNode> targetPartyClass) {
        this.targetPartyClass = targetPartyClass;
    }

    public Class<? extends ServiceEntityNode> getTargetContactClass() {
        return targetContactClass;
    }

    public void setTargetContactClass(Class<? extends ServiceEntityNode> targetContactClass) {
        this.targetContactClass = targetContactClass;
    }

    public InvolvePartySearchConfigTemplate build() throws SearchConfigureException {
        SearchConfigureTemplateNode searchConfigureTemplateNode = super.build();
        return new InvolvePartySearchConfigTemplate(
                searchConfigureTemplateNode.getNodeClass(),
                searchConfigureTemplateNode.getNodeInstId(),
                searchConfigureTemplateNode.getNodeInstCode(),
                searchConfigureTemplateNode.getNodeCategory(),
                this.getTargetPartyClass(),
                this.getTargetContactClass()
        );
    }

}
