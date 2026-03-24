package com.company.IntelligentPlatform.common.controller;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.*;

@Service
public class DocUIModelExtensionBuilder {

    protected Class<? extends ServiceEntityNode> docModelClass;

    protected Class<? extends SEUIComModel> docUiModelClass;

    protected Class<? extends ServiceEntityNode> itemModelClass;

    protected Class<? extends SEUIComModel> itemUiModelClass;

    protected Class<? extends ServiceEntityNode> docAttachmentClass;

    protected Class<? extends ServiceEntityNode> docInvolvePartyClass;

    protected Class<? extends ServiceEntityNode> itemInvolvePartyClass;

    protected Class<? extends ServiceEntityNode> itemAttachmentClass;

    protected Class<? extends ServiceEntityNode> docActionClass;

    protected Class<? extends ServiceEntityNode> itemActionClass;

    protected String convDocUIToMethod;

    protected String convDocToUIMethod;

    protected String convItemUIToMethod;

    protected String convItemToUIMethod;

    protected String itemToParentDocMethod;

    protected boolean defaultItemToParentDoc;

    protected ServiceEntityManager serviceEntityManager;

    protected Object itemLogicManager;

    protected String nodeInstId;

    protected Object logicManager;

    protected List<InvolvePartyBuilderInputPara> docInvolvePartyBuilderInputParaList;

    protected List<InvolvePartyBuilderInputPara> itemInvolvePartyBuilderInputParaList;

    protected List<DocActionNodeBuilderInputPara> docActionNodeBuildInputParaList;

    protected List<DocUIModelExtensionBuilder> childUIModelExtensionBuilderList;

    protected Map<String, List<UIModelNodeMapConfigureBuilder>> uiModelConfigureBuilderListMap = new HashMap<>();

    protected DocUIModelExtensionFactory docUIModelExtensionFactory;

    protected static String PARA_GROUP_ID_DOC = "doc";

    protected static String PARA_GROUP_ID_ITEM = "item";

    public DocUIModelExtensionBuilder docModelClass(Class<? extends ServiceEntityNode> docModelClass) {
        this.docModelClass = docModelClass;
        return this;
    }

    public DocUIModelExtensionBuilder docUiModelClass(Class<? extends SEUIComModel> docUiModelClass) {
        this.docUiModelClass = docUiModelClass;
        return this;
    }

    public DocUIModelExtensionBuilder itemModelClass(Class<? extends ServiceEntityNode> itemModelClass) {
        this.itemModelClass = itemModelClass;
        return this;
    }

    public DocUIModelExtensionBuilder itemUiModelClass(Class<? extends SEUIComModel> itemUiModelClass) {
        this.itemUiModelClass = itemUiModelClass;
        return this;
    }

    public DocUIModelExtensionBuilder nodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
        return this;
    }

    public DocUIModelExtensionBuilder docAttachmentClass(Class<? extends ServiceEntityNode> docAttachmentClass) {
        this.docAttachmentClass = docAttachmentClass;
        return this;
    }

    public DocUIModelExtensionBuilder itemAttachmentClass(Class<? extends ServiceEntityNode> itemAttachmentClass) {
        this.itemAttachmentClass = itemAttachmentClass;
        return this;
    }

    public DocUIModelExtensionBuilder docInvolvePartyClass(Class<? extends ServiceEntityNode> docInvolvePartyClass) {
        this.docInvolvePartyClass = docInvolvePartyClass;
        return this;
    }

    public DocUIModelExtensionBuilder itemInvolvePartyClass(Class<? extends ServiceEntityNode> itemInvolvePartyClass) {
        this.itemInvolvePartyClass = itemInvolvePartyClass;
        return this;
    }

    public DocUIModelExtensionBuilder docActionClass(Class<? extends ServiceEntityNode> docActionClass) {
        this.docActionClass = docActionClass;
        return this;
    }

    public DocUIModelExtensionBuilder itemActionClass(Class<? extends ServiceEntityNode> itemActionClass) {
        this.itemActionClass = itemActionClass;
        return this;
    }

    public DocUIModelExtensionBuilder logicManager(Object logicManager) {
        this.logicManager = logicManager;
        return this;
    }

    public DocUIModelExtensionBuilder convDocUIToMethod(String convDocUIToMethod) {
        this.convDocUIToMethod = convDocUIToMethod;
        return this;
    }

    public DocUIModelExtensionBuilder convDocToUIMethod(String convDocToUIMethod) {
        this.convDocToUIMethod = convDocToUIMethod;
        return this;
    }

    public DocUIModelExtensionBuilder convItemToUIMethod(String convItemToUIMethod) {
        this.convItemToUIMethod = convItemToUIMethod;
        return this;
    }

    public DocUIModelExtensionBuilder convItemUIToMethod(String convItemUIToMethod) {
        this.convItemUIToMethod = convItemUIToMethod;
        return this;
    }

    public DocUIModelExtensionBuilder itemToParentDocMethod(String itemToParentDocMethod) {
        this.itemToParentDocMethod = itemToParentDocMethod;
        return this;
    }

    public DocUIModelExtensionBuilder defaultItemToParentDoc(boolean defaultItemToParentDoc) {
        this.defaultItemToParentDoc = defaultItemToParentDoc;
        return this;
    }

    public DocUIModelExtensionBuilder serviceEntityManager(ServiceEntityManager serviceEntityManager) {
        this.serviceEntityManager = serviceEntityManager;
        return this;
    }

    public DocUIModelExtensionBuilder itemLogicManager(Object itemLogicManager) {
        this.itemLogicManager = itemLogicManager;
        return this;
    }

    public DocUIModelExtensionBuilder docInvolvePartyBuilderInputParaList(List<InvolvePartyBuilderInputPara> docInvolvePartyBuilderInputParaList) {
        this.docInvolvePartyBuilderInputParaList = docInvolvePartyBuilderInputParaList;
        return this;
    }

    public DocUIModelExtensionBuilder itemInvolvePartyBuilderInputParaList(List<InvolvePartyBuilderInputPara> itemInvolvePartyBuilderInputParaList) {
        this.itemInvolvePartyBuilderInputParaList = itemInvolvePartyBuilderInputParaList;
        return this;
    }

    public DocUIModelExtensionBuilder docActionNodeBuildInputParaList(List<DocActionNodeBuilderInputPara> docActionNodeBuildInputParaList) {
        this.docActionNodeBuildInputParaList = docActionNodeBuildInputParaList;
        return this;
    }

    public DocUIModelExtensionBuilder childUIModelExtensionBuilderList(List<DocUIModelExtensionBuilder> ChildUIModelExtensionBuilderList) {
        this.childUIModelExtensionBuilderList = ChildUIModelExtensionBuilderList;
        return this;
    }

    /**
     * Builds the default Service UI model extension for the DOC header.
     *
     * @return A fully initialized {@link ServiceUIModelExtension} instance containing
     * the DOC-specific logic and its child extensions.
     */
    public ServiceUIModelExtension buildDoc() throws DocActionException {
        try {
            ServiceUIModelExtension serviceUIModelExtension = buildExtension(PARA_GROUP_ID_DOC, serviceUIModelUnionBuilder -> {
                // Trigger build doc extension union
                try {
                    String docNodeInstId = ServiceEntityStringHelper.checkNullString(getNodeInstId()) ?
                            ServiceUIModelExtensionHelper.getDefNodeInstIdByModelClass(getModelClass()): getNodeInstId();
                    DocFlowProxy docFlowProxy = getDocUIModelExtensionFactory().getDocFlowProxy();
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDocDefCreateUpdateNodeMapConfigureList(docNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevProfDocMapConfigureList(docNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevDocMapConfigureList(docNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextProfDocMapConfigureList(docNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextDocMapConfigureList(docNodeInstId));
                } catch (SearchConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            });
            List<ServiceUIModelExtension> childUIModelExtentsionList = Optional.ofNullable(serviceUIModelExtension.getChildUIModelExtensions())
                    .orElseGet(ArrayList::new);
            if (this.getItemModelClass() != null) {
                childUIModelExtentsionList.add(buildDocItem());
            }
            serviceUIModelExtension.setChildUIModelExtensions(childUIModelExtentsionList);
            return serviceUIModelExtension;
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * Builds the default Service UI model extension for the Normal root node header.
     *
     * @return A fully initialized {@link ServiceUIModelExtension} instance containing
     * the DOC-specific logic and its child extensions.
     */
    public ServiceUIModelExtension buildRootNode() throws DocActionException {
        try {
            ServiceUIModelExtension serviceUIModelExtension = buildExtension(PARA_GROUP_ID_DOC, null);
            List<ServiceUIModelExtension> childUIModelExtentsionList = Optional.ofNullable(serviceUIModelExtension.getChildUIModelExtensions())
                    .orElseGet(ArrayList::new);
            if (this.getItemModelClass() != null) {
                childUIModelExtentsionList.add(buildItem());
            }
            serviceUIModelExtension.setChildUIModelExtensions(childUIModelExtentsionList);
            return serviceUIModelExtension;
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * Builds the default Service UI model extension for the DOC Material item.
     *
     * @return A fully initialized {@link ServiceUIModelExtension} instance containing
     * the DOC-specific logic and its child extensions.
     */
    public ServiceUIModelExtension buildDocItem() throws DocActionException {
            ServiceUIModelExtension serviceUIModelExtension = buildExtension(PARA_GROUP_ID_ITEM, serviceUIModelUnionBuilder -> {
                String itemNodeInstId = null;
                try {
                    itemNodeInstId = ServiceUIModelExtensionHelper.getDefNodeInstIdByModelClass(getItemModelClass());
                    // Item node logic: set the parent doc to conversion logic.
                    Class<?>[] convParentDocToUIMethodParas = {getDocModelClass(), getItemUiModelClass()};
                    DocFlowProxy docFlowProxy = getDocUIModelExtensionFactory().getDocFlowProxy();
                    if (getDefaultItemToParentDoc()) {
                        serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefParentDocMapConfigureList(itemNodeInstId));
                    } else {
                        serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefParentDocMapConfigureList(itemNodeInstId,
                                getItemToParentDocMethod(),
                                getItemLogicManager(), convParentDocToUIMethodParas));
                    }
                    // Item node logic: set the parent doc to conversion logic.
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefMaterialNodeMapConfigureList(itemNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevNodeMapConfigureList(itemNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextNodeMapConfigureList(itemNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefNextProfNodeMapConfigureList(itemNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefPrevProfNodeMapConfigureList(itemNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefReservedNodeMapConfigureList(itemNodeInstId));
                    serviceUIModelUnionBuilder.addMapConfigureList(docFlowProxy.getDefCreateUpdateNodeMapConfigureList(itemNodeInstId));
                } catch (SearchConfigureException e) {
                    throw new RuntimeException(e);
                }
            });
            return serviceUIModelExtension;
    }

    /**
     * Builds the default Service UI model extension for the Normal Item level node.
     *
     * @return A fully initialized {@link ServiceUIModelExtension} instance containing
     * the DOC-specific logic and its child extensions.
     */
    public ServiceUIModelExtension buildItem() throws DocActionException {
        return buildExtension(PARA_GROUP_ID_ITEM, null);
    }

    /**
     * Builds the default Service UI model extension for Any sub node under Doc Root node.
     *
     * @return A fully initialized {@link ServiceUIModelExtension} instance containing
     * the DOC-specific logic and its child extensions.
     */
    public ServiceUIModelExtension buildSubNode(String nodeInstId) throws DocActionException {
        DocUIModelExtensionBuilder subNodeUIModelExtensionBuilder = null;
        List<DocUIModelExtensionBuilder> childDocUIModelExtensionBuilderList = getChildUIModelExtensionBuilderList();
        if (!ServiceCollectionsHelper.checkNullList(childDocUIModelExtensionBuilderList)) {
            for (DocUIModelExtensionBuilder tmpChildUIModelExtensionBuilder : childDocUIModelExtensionBuilderList) {
                String childNodeInstId = Optional.ofNullable(tmpChildUIModelExtensionBuilder.getNodeInstId()).orElseGet(() ->
                {
                    try {
                        return ServiceUIModelExtensionHelper.getDefNodeInstIdByModelClass(tmpChildUIModelExtensionBuilder.getModelClass());
                    } catch (SearchConfigureException e) {
                        return null;
                    }
                });
                if (nodeInstId.equals(childNodeInstId)) {
                    subNodeUIModelExtensionBuilder = tmpChildUIModelExtensionBuilder;
                    break;
                }
            }
        }
        if (subNodeUIModelExtensionBuilder != null) {
            return subNodeUIModelExtensionBuilder.buildExtension(PARA_GROUP_ID_DOC, null);
        }
        return null;
    }

    /**
     * Builds the header section of the Service UI Model Union. It involves initializing the header Map Configure instance.
     * as well as adding the standard post Map configure list belongs to the same Service UI Model union by calling method: `buildPostsUIModelConfigureBuilderList`.
     *
     * @param paraGroupId The ID of the parameter group.
     * @return A configured {@link ServiceUIModelUnionBuilder} instance representing the header part of the union.
     * @throws DocActionException If an error occurs during the union-building process
     *                            due to invalid configuration or system errors.
     */
    private ServiceUIModelUnionBuilder buildExtensionHeader(String paraGroupId) throws DocActionException {
        try {
            // Step 1: Builds the header section of the Service UI Model Union. It involves initializing the header Map Configure instance.
            BuilderParaGroup builderParaGroup = getBuildParaGroup(paraGroupId);
            assert builderParaGroup != null;
            ServiceUIModelUnionBuilder docServiceUIModelUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(builderParaGroup.getModelClass(),
                    builderParaGroup.getUiModelClass(), uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(builderParaGroup.convToUIMethod)
                            .convUIToMethod(builderParaGroup.getConvUIToMethod()).logicManager(builderParaGroup.getLogicManager()));
            String nodeInstId = ServiceEntityStringHelper.checkNullString(getNodeInstId()) ?
                    ServiceUIModelExtensionHelper.getDefNodeInstIdByModelClass(builderParaGroup.getModelClass()) : getNodeInstId();
            docServiceUIModelUnionBuilder.nodeInstId(nodeInstId);
            // Step 2: Builds standard post Map Configure list belongs to the same Service UI Model union .
            buildPostsUIModelConfigureBuilderList(nodeInstId, docServiceUIModelUnionBuilder);
            return docServiceUIModelUnionBuilder;
        } catch (SearchConfigureException | ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * The framework method to build Service UI Model Extension.
     * Constructs a {@link ServiceUIModelExtension} instance by generating configurations optionally applying custom logic via a callback.
     * This framework method builds the DOC header extension, post UI model configurations, and child UI model extensions as part of the Service UI model extension.
     *
     * @param paraGroupId                        The parameter group ID
     * @param serviceUIModelUnionBuilderCallback An optional callback to apply custom logic for
     *                                           configuring the {@link ServiceUIModelUnionBuilder}.
     *                                           If null, no additional logic is executed.
     * @return A fully populated {@link ServiceUIModelExtension} instance,
     * containing the header and child model extensions.
     */
    private ServiceUIModelExtension buildExtension(String paraGroupId, IServiceUIModelUnionBuilderCallback serviceUIModelUnionBuilderCallback) throws DocActionException {
        try {
            ServiceUIModelExtension serviceUIModelExtension = new ServiceUIModelExtension();
            // Build Doc header as well as the post UI model map configure
            ServiceUIModelUnionBuilder docServiceUIModelUnionBuilder = buildExtensionHeader(paraGroupId);
            if (serviceUIModelUnionBuilderCallback != null) {
                serviceUIModelUnionBuilderCallback.execute(docServiceUIModelUnionBuilder);
            }
            // Trigger build doc extension union
            serviceUIModelExtension.setUIModelExtensionUnion(ServiceCollectionsHelper.asList(docServiceUIModelUnionBuilder.build()));

            // Build children UI Model extension union.
            List<ServiceUIModelExtension> childUIModelExtensionList = ServiceCollectionsHelper.directMergeLists(
                    buildCommonChildrenModelExtensionList(paraGroupId), buildChildrenModelExtensionList(PARA_GROUP_ID_DOC));
            serviceUIModelExtension.setChildUIModelExtensions(childUIModelExtensionList);
            return serviceUIModelExtension;
        } catch (NoSuchFieldException |
                 IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    private BuilderParaGroup getBuildParaGroup(String paraGroupId) {
        if (PARA_GROUP_ID_DOC.equals(paraGroupId)) {
            return new BuilderParaGroup(getDocModelClass(), getDocUiModelClass(), getDocAttachmentClass(), getDocInvolvePartyClass(),
                    getDocActionClass(), getDocInvolvePartyBuilderInputParaList(), getConvDocToUIMethod(), getConvDocUIToMethod(), getServiceEntityManager(),
                    getNodeInstId(), getLogicManager());
        }
        if (PARA_GROUP_ID_ITEM.equals(paraGroupId)) {
            return new BuilderParaGroup(getItemModelClass(), getItemUiModelClass(), getItemAttachmentClass(), getItemInvolvePartyClass(),
                    getItemActionClass(), getItemInvolvePartyBuilderInputParaList(), getConvItemToUIMethod(), getConvItemUIToMethod(), getServiceEntityManager(),
                    null, getItemLogicManager());
        }
        return null;
    }

    /**
     * Common logic to build the child model extension list, such as the standard child extension, like: attachment, involve party, action node.
     * What's more, build the custom child extension by the list of child extension builder: childUIModelExtensionBuilderList.
     *
     * @param paraGroupId
     * @return
     * @throws DocActionException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private List<ServiceUIModelExtension> buildCommonChildrenModelExtensionList(String paraGroupId) throws DocActionException, NoSuchFieldException, IllegalAccessException {
        BuilderParaGroup builderParaGroup = getBuildParaGroup(paraGroupId);
        assert builderParaGroup != null;
        List<ServiceUIModelExtension> childUIModelExtensionList = new ArrayList<>();

        Class<? extends ServiceEntityNode> attachmentClass = builderParaGroup.getAttachmentClass();
        if (attachmentClass != null) {
            DocAttachmentProxy docAttachmentProxy = getDocUIModelExtensionFactory().getDocAttachmentProxy();
            String attachmentSeName = UIModelNodeMapConfigureBuilder.calculateRefSEName(attachmentClass);
            String attachmentNodeName = UIModelNodeMapConfigureBuilder.calculateRefNodeName(attachmentClass);
            childUIModelExtensionList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
                    attachmentSeName,
                    attachmentNodeName,
                    attachmentNodeName
            )));
        }

        Class<? extends ServiceEntityNode> involvePartyClass = builderParaGroup.getInvolvePartyClass();
        List<InvolvePartyBuilderInputPara> involvePartyBuilderInputParaList = builderParaGroup.getInvolvePartyBuilderInputParaList();
        if (involvePartyClass != null) {
            ServiceCollectionsHelper.traverseListInterrupt(involvePartyBuilderInputParaList, involvePartyBuilderInputPara -> {
                try {
                    String partySeName = UIModelNodeMapConfigureBuilder.calculateRefSEName(involvePartyClass);
                    String partyNodeName = UIModelNodeMapConfigureBuilder.calculateRefNodeName(involvePartyClass);
                    DocInvolvePartyProxy docInvolvePartyProxy = getDocUIModelExtensionFactory().getDocInvolvePartyProxy();
                    childUIModelExtensionList.add(docInvolvePartyProxy.genDefServiceUIModelExtension(new DocInvolvePartyProxy.DocInvolvePartyInputPara(
                            partySeName,
                            partyNodeName,
                            involvePartyBuilderInputPara.getPartyNodeId(),
                            getServiceEntityManager(),
                            involvePartyBuilderInputPara.getPartyRole(),
                            involvePartyBuilderInputPara.getTargetPartyType(),
                            involvePartyBuilderInputPara.getTargetContactType()
                    )));
                    return true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            });
        }

        Class<? extends ServiceEntityNode> actionClass = builderParaGroup.getActionClass();
        if (actionClass != null) {
            ServiceCollectionsHelper.traverseListInterrupt(getDocActionNodeBuildInputParaList(), docActionNodeBuilderInputPara -> {
                try {
                    String actionNodeSeName = UIModelNodeMapConfigureBuilder.calculateRefSEName(actionClass);
                    String actionNodeName = UIModelNodeMapConfigureBuilder.calculateRefNodeName(actionClass);
                    DocActionNodeProxy docActionNodeProxy = getDocUIModelExtensionFactory().getDocActionNodeProxy();
                    childUIModelExtensionList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
                            actionNodeSeName,
                            actionNodeName,
                            docActionNodeBuilderInputPara.getNodeInstId(),
                            getServiceEntityManager(), docActionNodeBuilderInputPara.getDocActionCode()
                    )));
                    return true;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            });
        }

        return childUIModelExtensionList;
    }

    private List<ServiceUIModelExtension> buildChildrenModelExtensionList(String paraGroupId) throws DocActionException, NoSuchFieldException, IllegalAccessException {
        BuilderParaGroup builderParaGroup = getBuildParaGroup(paraGroupId);
        assert builderParaGroup != null;
        List<ServiceUIModelExtension> childUIModelExtensionList = new ArrayList<>();
        ServiceCollectionsHelper.traverseListInterrupt(getChildUIModelExtensionBuilderList(), childUIModelExtensionBuilder -> {
            childUIModelExtensionList.add(childUIModelExtensionBuilder.buildExtension(paraGroupId, null));
            return true;
        });
        return childUIModelExtensionList;
    }

    /**
     * Adds the UIModelNodeMapConfigureBuilder to the internal map:uiModelConfigureBuilderListMap, where each key represents a
     * unique node instance ID, and the value is a list of uiModelConfigure builders which will be placed as post List of
     * UIModelConfigure list of one specific Service UI Model Union by the node instance ID.
     *
     * @param nodeInstId                     the unique identifier for the node instance to which the builder applies
     * @param uiModelNodeMapConfigureBuilder the UIModelNodeMapConfigureBuilder instance
     * @see #buildPostsUIModelConfigureBuilderList(String, ServiceUIModelUnionBuilder)
     */
    public void addMapConfigureBuilder(String nodeInstId, UIModelNodeMapConfigureBuilder uiModelNodeMapConfigureBuilder) {
        if (uiModelConfigureBuilderListMap == null) {
            uiModelConfigureBuilderListMap = new HashMap<>();
        }
        List<UIModelNodeMapConfigureBuilder> docUIModelNodeMapConfigureBuilderList = uiModelConfigureBuilderListMap.get(nodeInstId);
        if (ServiceCollectionsHelper.checkNullList(docUIModelNodeMapConfigureBuilderList)) {
            uiModelConfigureBuilderListMap.put(nodeInstId, ServiceCollectionsHelper.asList(uiModelNodeMapConfigureBuilder));
        } else {
            docUIModelNodeMapConfigureBuilderList.add(uiModelNodeMapConfigureBuilder);
        }
    }

    /**
     * build and generate a list of uiModelConfigure which are placed as post List of UIModelConfigure list of one specific
     * Service UI Model Union by the node instance ID.by the list of associated builders (previously added via {@link #addMapConfigureBuilder}).
     *
     * @param nodeInstId                 the unique identifier of the node instance whose builders will be applied
     * @param serviceUIModelUnionBuilder the target builder to which the map configure builders will be added
     * @throws DocActionException if builder configuration fails or any error occurs while processing the builders
     */
    private void buildPostsUIModelConfigureBuilderList(String nodeInstId, ServiceUIModelUnionBuilder serviceUIModelUnionBuilder) throws DocActionException {
        List<UIModelNodeMapConfigureBuilder> docUIModelNodeMapConfigureBuilderList = uiModelConfigureBuilderListMap.get(nodeInstId);
        ServiceCollectionsHelper.traverseListInterrupt(docUIModelNodeMapConfigureBuilderList, uiModelNodeMapConfigureBuilder -> {
            try {
                serviceUIModelUnionBuilder.addMapConfigureBuilder(uiModelNodeMapConfigureBuilder);
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
            return true;
        });
    }

    public DocUIModelExtensionFactory getDocUIModelExtensionFactory() {
        return docUIModelExtensionFactory;
    }

    public void setDocUIModelExtensionFactory(DocUIModelExtensionFactory docUIModelExtensionFactory) {
        this.docUIModelExtensionFactory = docUIModelExtensionFactory;
    }

    public Class<? extends ServiceEntityNode> getModelClass() {
        return docModelClass;
    }

    public void setModelClass(Class<? extends ServiceEntityNode> docModelClass) {
        this.docModelClass = docModelClass;
    }

    public Class<? extends SEUIComModel> getUiModelClass() {
        return docUiModelClass;
    }

    public void setUiModelClass(Class<? extends SEUIComModel> docUiModelClass) {
        this.docUiModelClass = docUiModelClass;
    }

    public Class<? extends ServiceEntityNode> getDocModelClass() {
        return docModelClass;
    }

    public void setDocModelClass(Class<? extends ServiceEntityNode> docModelClass) {
        this.docModelClass = docModelClass;
    }

    public Class<? extends SEUIComModel> getDocUiModelClass() {
        return docUiModelClass;
    }

    public void setDocUiModelClass(Class<? extends SEUIComModel> docUiModelClass) {
        this.docUiModelClass = docUiModelClass;
    }

    public Class<? extends ServiceEntityNode> getItemModelClass() {
        return itemModelClass;
    }

    public void setItemModelClass(Class<? extends ServiceEntityNode> itemModelClass) {
        this.itemModelClass = itemModelClass;
    }

    public Class<? extends SEUIComModel> getItemUiModelClass() {
        return itemUiModelClass;
    }

    public void setItemUiModelClass(Class<? extends SEUIComModel> itemUiModelClass) {
        this.itemUiModelClass = itemUiModelClass;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public String getConvDocUIToMethod() {
        return convDocUIToMethod;
    }

    public void setConvDocUIToMethod(String convDocUIToMethod) {
        this.convDocUIToMethod = convDocUIToMethod;
    }

    public String getConvDocToUIMethod() {
        return convDocToUIMethod;
    }

    public void setConvDocToUIMethod(String convDocToUIMethod) {
        this.convDocToUIMethod = convDocToUIMethod;
    }

    public String getConvItemUIToMethod() {
        return convItemUIToMethod;
    }

    public Object getLogicManager() {
        return logicManager;
    }

    public void setLogicManager(Object logicManager) {
        this.logicManager = logicManager;
    }

    public void setConvItemUIToMethod(String convItemUIToMethod) {
        this.convItemUIToMethod = convItemUIToMethod;
    }

    public String getConvItemToUIMethod() {
        return convItemToUIMethod;
    }

    public void setConvItemToUIMethod(String convItemToUIMethod) {
        this.convItemToUIMethod = convItemToUIMethod;
    }

    public String getItemToParentDocMethod() {
        return itemToParentDocMethod;
    }

    public void setItemToParentDocMethod(String itemToParentDocMethod) {
        this.itemToParentDocMethod = itemToParentDocMethod;
    }

    public boolean getDefaultItemToParentDoc() {
        return defaultItemToParentDoc;
    }

    public void setDefaultItemToParentDoc(boolean defaultItemToParentDoc) {
        this.defaultItemToParentDoc = defaultItemToParentDoc;
    }

    public List<InvolvePartyBuilderInputPara> getDocInvolvePartyBuilderInputParaList() {
        return docInvolvePartyBuilderInputParaList;
    }

    public void setDocInvolvePartyBuilderInputParaList(List<InvolvePartyBuilderInputPara> docInvolvePartyBuilderInputParaList) {
        this.docInvolvePartyBuilderInputParaList = docInvolvePartyBuilderInputParaList;
    }

    public List<InvolvePartyBuilderInputPara> getItemInvolvePartyBuilderInputParaList() {
        return itemInvolvePartyBuilderInputParaList;
    }

    public void setItemInvolvePartyBuilderInputParaList(List<InvolvePartyBuilderInputPara> itemInvolvePartyBuilderInputParaList) {
        this.itemInvolvePartyBuilderInputParaList = itemInvolvePartyBuilderInputParaList;
    }

    public Class<? extends ServiceEntityNode> getItemAttachmentClass() {
        return itemAttachmentClass;
    }

    public void setItemAttachmentClass(Class<? extends ServiceEntityNode> itemAttachmentClass) {
        this.itemAttachmentClass = itemAttachmentClass;
    }

    public Class<? extends ServiceEntityNode> getDocInvolvePartyClass() {
        return docInvolvePartyClass;
    }

    public void setDocInvolvePartyClass(Class<? extends ServiceEntityNode> docInvolvePartyClass) {
        this.docInvolvePartyClass = docInvolvePartyClass;
    }

    public Class<? extends ServiceEntityNode> getItemInvolvePartyClass() {
        return itemInvolvePartyClass;
    }

    public void setItemInvolvePartyClass(Class<? extends ServiceEntityNode> itemInvolvePartyClass) {
        this.itemInvolvePartyClass = itemInvolvePartyClass;
    }

    public Class<? extends ServiceEntityNode> getItemActionClass() {
        return itemActionClass;
    }

    public void setItemActionClass(Class<? extends ServiceEntityNode> itemActionClass) {
        this.itemActionClass = itemActionClass;
    }

    public Class<? extends ServiceEntityNode> getDocActionClass() {
        return docActionClass;
    }

    public void setDocActionClass(Class<? extends ServiceEntityNode> docActionClass) {
        this.docActionClass = docActionClass;
    }

    public Class<? extends ServiceEntityNode> getDocAttachmentClass() {
        return docAttachmentClass;
    }

    public void setDocAttachmentClass(Class<? extends ServiceEntityNode> docAttachmentClass) {
        this.docAttachmentClass = docAttachmentClass;
    }

    public List<DocActionNodeBuilderInputPara> getDocActionNodeBuildInputParaList() {
        return docActionNodeBuildInputParaList;
    }

    public void setDocActionNodeBuildInputParaList(List<DocActionNodeBuilderInputPara> docActionNodeBuildInputParaList) {
        this.docActionNodeBuildInputParaList = docActionNodeBuildInputParaList;
    }

    public ServiceEntityManager getServiceEntityManager() {
        return serviceEntityManager;
    }

    public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
        this.serviceEntityManager = serviceEntityManager;
    }

    public Object getItemLogicManager() {
        return itemLogicManager;
    }

    public void setItemLogicManager(Object itemLogicManager) {
        this.itemLogicManager = itemLogicManager;
    }

    public List<DocUIModelExtensionBuilder> getChildUIModelExtensionBuilderList() {
        return childUIModelExtensionBuilderList;
    }

    public void setChildUIModelExtensionBuilderList(List<DocUIModelExtensionBuilder> ChildUIModelExtensionBuilderList) {
        this.childUIModelExtensionBuilderList = ChildUIModelExtensionBuilderList;
    }

    public static class DocActionNodeBuilderInputPara {

        protected int docActionCode;

        protected String nodeInstId;

        public DocActionNodeBuilderInputPara(int docActionCode, String nodeInstId) {
            this.docActionCode = docActionCode;
            this.nodeInstId = nodeInstId;
        }

        public int getDocActionCode() {
            return docActionCode;
        }

        public void setDocActionCode(int docActionCode) {
            this.docActionCode = docActionCode;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

    }

    public static class InvolvePartyBuilderInputPara {

        protected int partyRole;

        protected String partyNodeId;

        protected Class<?> targetPartyType;

        protected Class<?> targetContactType;

        public InvolvePartyBuilderInputPara(int partyRole, String partyNodeId, Class<?> targetPartyType, Class<?> targetContactType) {
            this.partyRole = partyRole;
            this.partyNodeId = partyNodeId;
            this.targetPartyType = targetPartyType;
            this.targetContactType = targetContactType;
        }

        public int getPartyRole() {
            return partyRole;
        }

        public void setPartyRole(int partyRole) {
            this.partyRole = partyRole;
        }

        public Class<?> getTargetPartyType() {
            return targetPartyType;
        }

        public void setTargetPartyType(Class<?> targetPartyType) {
            this.targetPartyType = targetPartyType;
        }

        public Class<?> getTargetContactType() {
            return targetContactType;
        }

        public void setTargetContactType(Class<?> targetContactType) {
            this.targetContactType = targetContactType;
        }

        public String getPartyNodeId() {
            return partyNodeId;
        }

        public void setPartyNodeId(String partyNodeId) {
            this.partyNodeId = partyNodeId;
        }
    }

    public static interface IServiceUIModelUnionBuilderCallback {
        void execute(ServiceUIModelUnionBuilder serviceUIModelUnionBuilder) throws DocActionException;
    }

    public static class BuilderParaGroup {

        protected Class<? extends ServiceEntityNode> modelClass;

        protected Class<? extends SEUIComModel> uiModelClass;

        protected Class<? extends ServiceEntityNode> attachmentClass;

        protected Class<? extends ServiceEntityNode> involvePartyClass;

        protected Class<? extends ServiceEntityNode> actionClass;

        protected String convUIToMethod;

        protected String convToUIMethod;

        protected ServiceEntityManager serviceEntityManager;

        protected String nodeInstId;

        protected Object logicManager;
        
        protected List<InvolvePartyBuilderInputPara> involvePartyBuilderInputParaList;

        public BuilderParaGroup(Class<? extends ServiceEntityNode> modelClass,
                                Class<? extends SEUIComModel> uiModelClass,
                                Class<? extends ServiceEntityNode> attachmentClass,
                                Class<? extends ServiceEntityNode> involvePartyClass,
                                Class<? extends ServiceEntityNode> actionClass,
                                List<InvolvePartyBuilderInputPara> involvePartyBuilderInputParaList,
                                String convToUIMethod,
                                String convUIToMethod,
                                ServiceEntityManager serviceEntityManager,
                                String nodeInstId,
                                Object logicManager) {
            this.modelClass = modelClass;
            this.uiModelClass = uiModelClass;
            this.attachmentClass = attachmentClass;
            this.involvePartyClass = involvePartyClass;
            this.actionClass = actionClass;
            this.involvePartyBuilderInputParaList = involvePartyBuilderInputParaList;
            this.convToUIMethod = convToUIMethod;
            this.convUIToMethod = convUIToMethod;
            this.serviceEntityManager = serviceEntityManager;
            this.nodeInstId = nodeInstId;
            this.logicManager = logicManager;
        }

        public BuilderParaGroup() {
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

        public Class<? extends ServiceEntityNode> getAttachmentClass() {
            return attachmentClass;
        }

        public void setAttachmentClass(Class<? extends ServiceEntityNode> attachmentClass) {
            this.attachmentClass = attachmentClass;
        }

        public Class<? extends ServiceEntityNode> getInvolvePartyClass() {
            return involvePartyClass;
        }

        public void setInvolvePartyClass(Class<? extends ServiceEntityNode> involvePartyClass) {
            this.involvePartyClass = involvePartyClass;
        }

        public Class<? extends ServiceEntityNode> getActionClass() {
            return actionClass;
        }

        public void setActionClass(Class<? extends ServiceEntityNode> actionClass) {
            this.actionClass = actionClass;
        }

        public String getConvUIToMethod() {
            return convUIToMethod;
        }

        public void setConvUIToMethod(String convUIToMethod) {
            this.convUIToMethod = convUIToMethod;
        }

        public String getConvToUIMethod() {
            return convToUIMethod;
        }

        public void setConvToUIMethod(String convToUIMethod) {
            this.convToUIMethod = convToUIMethod;
        }

        public ServiceEntityManager getServiceEntityManager() {
            return serviceEntityManager;
        }

        public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
            this.serviceEntityManager = serviceEntityManager;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public Object getLogicManager() {
            return logicManager;
        }

        public void setLogicManager(Object logicManager) {
            this.logicManager = logicManager;
        }

        public List<InvolvePartyBuilderInputPara> getInvolvePartyBuilderInputParaList() {
            return involvePartyBuilderInputParaList;
        }

        public void setInvolvePartyBuilderInputParaList(List<InvolvePartyBuilderInputPara> involvePartyBuilderInputParaList) {
            this.involvePartyBuilderInputParaList = involvePartyBuilderInputParaList;
        }

    }
}
