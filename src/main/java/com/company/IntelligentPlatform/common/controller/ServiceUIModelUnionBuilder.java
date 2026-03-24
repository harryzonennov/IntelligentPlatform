package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

public class ServiceUIModelUnionBuilder {

    protected Class<? extends ServiceEntityNode> modelClass;

    protected Class<? extends SEUIComModel> uiModelClass;

    protected List<UIModelNodeMapConfigureBuilder> uiModelNodeMapConfigureBuilderList = new ArrayList<>();

    protected List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList = new ArrayList<>();

    protected String seName;

    protected String nodeName;

    protected String nodeInstId;

    protected UIModelNodeMapConfigure toParentModelNodeMapConfigure;

    public ServiceUIModelUnionBuilder modelClass(Class<? extends ServiceEntityNode> modelClass) {
        this.modelClass = modelClass;
        return this;
    }

    public ServiceUIModelUnionBuilder uiModelClass(Class<? extends SEUIComModel> uiModelClass) {
        this.uiModelClass = uiModelClass;
        return this;
    }

    public ServiceUIModelUnionBuilder nodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
        return this;
    }

    public ServiceUIModelUnionBuilder toParentModelNodeMapConfigure(UIModelNodeMapConfigure toParentModelNodeMapConfigure) {
        this.toParentModelNodeMapConfigure = toParentModelNodeMapConfigure;
        return this;
    }

    public void addMapConfigureBuilder(UIModelNodeMapConfigureBuilder uiModelNodeMapConfigureBuilder) throws ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(this.uiModelNodeMapConfigureBuilderList)) {
            setUiModelNodeMapConfigureBuilderList(ServiceCollectionsHelper.asList(uiModelNodeMapConfigureBuilder));
        } else {
            uiModelNodeMapConfigureBuilder.setNotification();
            String nodeInstId = uiModelNodeMapConfigureBuilder.getNodeInstId();
            UIModelNodeMapConfigureBuilder existedNodeMapConfigureBuilder = ServiceCollectionsHelper.filterOnline(
                    this.uiModelNodeMapConfigureBuilderList,
                    tmpNodeMapConfigureBuilder -> {
                        try {
                            tmpNodeMapConfigureBuilder.setNotification();
                        } catch (ServiceEntityConfigureException e) {
                            throw new RuntimeException(e);
                        }
                        return nodeInstId.equals(tmpNodeMapConfigureBuilder.getNodeInstId());
                    });
            if (existedNodeMapConfigureBuilder != null) {
                this.uiModelNodeMapConfigureBuilderList.remove(existedNodeMapConfigureBuilder);
            }
            this.uiModelNodeMapConfigureBuilderList.add(uiModelNodeMapConfigureBuilder);
        }
    }

    public void addMapConfigureList(List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList)  {
        if (ServiceCollectionsHelper.checkNullList(this.uiModelNodeMapConfigureList)) {
            setUiModelNodeMapConfigureList(uiModelNodeMapConfigureList);
        } else {
            List<UIModelNodeMapConfigure> updatedMapConfigureList = this.getUiModelNodeMapConfigureList();
            updatedMapConfigureList.addAll(uiModelNodeMapConfigureList);
            this.setUiModelNodeMapConfigureList(updatedMapConfigureList);
        }
    }

    public ServiceUIModelExtensionUnion build() throws ServiceEntityConfigureException {
        ServiceUIModelExtensionUnion serviceUIModelExtensionUnion = new ServiceUIModelExtensionUnion();
        if (ServiceEntityStringHelper.checkNullString(getSeName())) {
            try {
                setSeName(UIModelNodeMapConfigureBuilder.calculateRefSEName(this.modelClass));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getMessage());
            }
        }
        if (ServiceEntityStringHelper.checkNullString(getNodeName())) {
            try {
                setNodeName(UIModelNodeMapConfigureBuilder.calculateRefNodeName(this.modelClass));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getMessage());
            }
        }
        if (ServiceEntityStringHelper.checkNullString(getNodeInstId())) {
            String nodeInstId = ServiceEntityStringHelper.getDefModelId(getSeName(), getNodeName());
            setNodeInstId(nodeInstId);
        }
        List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList = new ArrayList<>();
        try {
            ServiceCollectionsHelper.traverseListInterrupt(getUiModelNodeMapConfigureBuilderList(), uiModelNodeMapConfigureBuilder -> {
                try {
                    uiModelNodeMapConfigureList.add(uiModelNodeMapConfigureBuilder.build());
                } catch (ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
                return true;
            });
        } catch (DocActionException e) {
            throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
        uiModelNodeMapConfigureList.addAll(this.getUiModelNodeMapConfigureList());
        serviceUIModelExtensionUnion.setNodeName(getNodeName());
        serviceUIModelExtensionUnion.setNodeInstId(getNodeInstId());
        serviceUIModelExtensionUnion.setToParentModelNodeMapConfigure(getToParentModelNodeMapConfigure());
        serviceUIModelExtensionUnion.setUiModelNodeMapList(uiModelNodeMapConfigureList);
        return serviceUIModelExtensionUnion;
    }

    public Class<? extends ServiceEntityNode> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<? extends ServiceEntityNode> modelClass) {
        this.modelClass = modelClass;
    }

    public Class<? extends SEUIComModel> getUiModelClass() {
        return uiModelClass;
    }

    public void setUiModelClass(Class<? extends SEUIComModel> uiModelClass) {
        this.uiModelClass = uiModelClass;
    }

    public List<UIModelNodeMapConfigureBuilder> getUiModelNodeMapConfigureBuilderList() {
        return uiModelNodeMapConfigureBuilderList;
    }

    public void setUiModelNodeMapConfigureBuilderList(List<UIModelNodeMapConfigureBuilder> uiModelNodeMapConfigureBuilderList) {
        this.uiModelNodeMapConfigureBuilderList = uiModelNodeMapConfigureBuilderList;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public UIModelNodeMapConfigure getToParentModelNodeMapConfigure() {
        return toParentModelNodeMapConfigure;
    }

    public void setToParentModelNodeMapConfigure(UIModelNodeMapConfigure toParentModelNodeMapConfigure) {
        this.toParentModelNodeMapConfigure = toParentModelNodeMapConfigure;
    }

    public List<UIModelNodeMapConfigure> getUiModelNodeMapConfigureList() {
        return uiModelNodeMapConfigureList;
    }

    public void setUiModelNodeMapConfigureList(List<UIModelNodeMapConfigure> uiModelNodeMapConfigureList) {
        this.uiModelNodeMapConfigureList = uiModelNodeMapConfigureList;
    }
}
